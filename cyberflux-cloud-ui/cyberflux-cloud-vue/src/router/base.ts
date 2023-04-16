import { RouteRecordRaw } from 'vue-router'
import type { AppRouteRecordRaw } from '@/router/types'
import { ErrorPage404, ActiveLayout } from '@/router/constant'

export const IndexRoute: RouteRecordRaw = {
  path: '/',
  name: 'Index',
  redirect: '/dashboard',
  meta: {
    title: 'Index',
  }
}

export const ErrorPageRoute: AppRouteRecordRaw = {
  path: '/:path(.*)*',
  name: 'ErrorPage',
  component: ActiveLayout,
  meta: {
    title: 'ErrorPage',
    hideBreadcrumb: true,
  },
  children: [
    {
      path: '/:path(.*)*',
      name: 'SubErrorPage',
      component: ErrorPage404,
      meta: {
        title: 'ErrorPage',
        hideBreadcrumb: true,
      },
    }
  ]
}
