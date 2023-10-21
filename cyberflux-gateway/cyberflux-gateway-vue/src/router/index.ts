import type { App } from 'vue';
import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";
import { RouteModuleType } from './types';
import { createRouterGuard } from './guard';
import { IndexRoute, LoginRoute, RedirectRoute } from './common';

const moudels = import.meta.glob<RouteModuleType>(
  './modules/**/*.ts', { eager: true }
);

const routeRecordList: any[] = [];

Object.keys(moudels).forEach( key => {
  const moudel = moudels[key].default || {}
  const moudelArray = Array.isArray(moudel) ? [...moudel] : [moudel]
  routeRecordList.push(...moudelArray)
})

routeRecordList.sort((a : any, b: any): number => {
  return (a.meta?.sort || 0) - (b.meta?.sort || 0)
})

export const asyncRoutes = [...routeRecordList];

export const constantRoutes: RouteRecordRaw[] = [
  LoginRoute, IndexRoute, RedirectRoute
];

const router = createRouter({
  history: createWebHashHistory(),
  routes: constantRoutes,
  strict: true
});

export function setupRouter(app: App) {
  app.use(router);
  createRouterGuard(router);
}

export default router;
