import type { RouteLocationRaw, RouteRecordRaw } from 'vue-router'
import { isNavigationFailure, Router } from 'vue-router'
import { useAsyncRouteStoreWidthOut } from '@/store/modules/async-route'
import { RouterPath, ACCESS_TOKEN_KEY } from '@/enums'
import { ErrorPageRoute } from './base'
import { storage } from '@/utils'

const whitePathList = [RouterPath.LOGIN]

export function createRouterGuard(router: Router) {
  const asyncRouteStore = useAsyncRouteStoreWidthOut()

  router.beforeEach(async (to, from, next) => {
    window.document.title = String(to.meta.title)
    window.$loading.start()

    if(from.path === RouterPath.LOGIN && to.name === "ErrorPage") {
      next(RouterPath.DASHDOARD)
      return
    }

    if(whitePathList.includes(to.path as RouterPath)) {
      next()
      return
    }

    if(!storage.get(ACCESS_TOKEN_KEY)) {
      const redirectData: { path: string; replace: boolean; query?: Recordable<string> } = {
        path: RouterPath.LOGIN,
        replace: true,
      };
      if (to.path) {
        redirectData.query = {
          ...redirectData.query,
          redirect: to.path,
        };
      }
      next(redirectData);
      return;
    }

    if(asyncRouteStore.isDynamicRouteFlag) {
      next()
      return
    }

    const routes = await asyncRouteStore.generateRoutes(undefined)

    routes.forEach(item => {
      router.addRoute(item as unknown as RouteRecordRaw)
    })

    if(router.getRoutes().findIndex(item => item.name === ErrorPageRoute.name) === -1) {
      router.addRoute(ErrorPageRoute as unknown as RouteRecordRaw)
    }

    const redirectPath = (from.query.redirect || to.path) as string
    const redirect = decodeURIComponent(redirectPath)
    asyncRouteStore.setDynamicRouteFlag(true)
    next(to.path === redirect ? { ...to, replace: true } : { path: redirect })

    window.$loading.finish()
  })

  router.afterEach((to, from, failure) => {
    window.$loading.finish()
  })

  router.onError(error => {
    window.$loading.error()
    console.log(error, 'RouterError')
    window.$loading.finish()
  })
}
