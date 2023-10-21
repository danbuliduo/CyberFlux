import { Router } from 'vue-router';
import { useRouteStore } from '@/store/modules/route';
import { AccountStore, ChannelStore, EngineStore, SubscriptionStore } from "@/store";
import {RouterName, RouterPath} from '@/enums/router';
import { ErrorPageRoute } from './common';
import { ResponseCode } from "@/enums/restful";

export function createRouterGuard(router: Router) {
  const whitePathList = [RouterPath.LOGIN];
  const routeStore = useRouteStore()

  router.beforeEach(async (to, from, next) => {
    window.document.title = to.meta.title as string
    window.$loading.start()

    switch(to.path as RouterPath) {
      case RouterPath.DASHDOARD_OVERVIEW: await EngineStore.update(); break;
      case RouterPath.DEVICES_CONNECTION: await ChannelStore.update(); break;
      case RouterPath.DEVICES_SUBSCRIPTION: await SubscriptionStore.update(); break;
      case RouterPath.SETTINGS_USER: await AccountStore.update(); break;
    }

    if(whitePathList.includes(to.path as RouterPath)) {
      next()
      return;
    }

    if (!AccountStore.user.webtoken) {
      const redirectData: { path: string; replace: boolean; query?: Recordable<string> } = {
        path: RouterPath.LOGIN,
        replace: true,
      };
      if (to.path) {
        redirectData.query = { ...redirectData.query, redirect: to.path };
      }
      next(redirectData);
      return;
    }

    if(routeStore.isDynamicRouteFlag()) {
      next()
      return
    }

    const routes = await routeStore.generateRoutes(undefined)

    routes.forEach(item => {
      router.addRoute(item)
    })

    if(router.getRoutes().findIndex(item => item.name === ErrorPageRoute.name) === -1) {
      router.addRoute(ErrorPageRoute)
    }

    const redirectPath = (from.query.redirect || to.path) as string
    const redirect = decodeURIComponent(redirectPath)
    routeStore.setDynamicRouteFlag(true)
    next(to.path === redirect ? { ...to, replace: true } : { path: redirect })

    window.$loading.finish()
  })

  router.afterEach((to, from, failure) => {
    window.$loading.finish()
  })

  router.onError(error => {
    window.$loading.error()
    window.$loading.finish()
  })
}
