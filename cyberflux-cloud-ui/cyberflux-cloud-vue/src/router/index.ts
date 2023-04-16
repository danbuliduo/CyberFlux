import { RouteModuleType } from './types';
import { IndexRoute} from './base';
import {
  createRouter,
  createWebHashHistory,
  RouteRecordRaw
} from 'vue-router';

const moudels = import.meta.glob<RouteModuleType>(
  './modules/**/*.ts', { eager: true }
)

const routeRecordList: RouteRecordRaw[] = []

Object.keys(moudels).forEach( key => {
  const moudel = moudels[key].default || {}
  const moudelArray = Array.isArray(moudel) ? [...moudel] : [moudel]
  routeRecordList.push(...moudelArray)
})

routeRecordList.sort((a : any, b: any): number => {
  return (a.meta?.sort || 0) - (b.meta?.sort || 0)
})

export const asyncRoutes = [...routeRecordList]

export const constantRoutes = [
  IndexRoute, ...routeRecordList
]

const router = createRouter({
  history: createWebHashHistory(),
  routes: constantRoutes,
  strict: true
})

export default router
