import { RouteRecordRaw } from 'vue-router'
import type { AppRouteRecordRaw } from '@/router/types'
import { ErrorPage404, ActiveLayout } from '@/router/constant'

export const IndexRoute: RouteRecordRaw = {
  path: '/',
  name: 'index',
  redirect: '/dashboard',
  meta: {
    title: 'Index',
  }
}

export const LoginRoute: RouteRecordRaw = {
  path: '/login',
  name: 'login',
  component: () => import('@/views/login.vue'),
  meta: {
    title: 'Login'
  }
}

export const RedirectRoute: RouteRecordRaw = {
  path: '/redirect',
  name: 'redirect',
  component: ActiveLayout,
  meta: {
    title: 'Redirect',
    hideBreadcrumb: true,
  },
  children: [
    {
      path: '/redirect/:path(.*)',
      name: 'redirect',
      component: () => import('@/views/redirect.vue'),
      meta: {
        title: 'Redirect',
        hideBreadcrumb: true,
      },
    },
  ],
};


export const ErrorPageRoute: RouteRecordRaw = {
  path: '/:path(.*)*',
  name: 'error-page',
  component: ActiveLayout,
  meta: {
    title: 'ErrorPage',
    hideBreadcrumb: true,
  },
  children: [
    {
      path: '/:path(.*)*',
      name: 'error-page-404',
      component: ErrorPage404,
      meta: {
        title: 'ErrorPage',
        hideBreadcrumb: true,
      },
    }
  ]
}
