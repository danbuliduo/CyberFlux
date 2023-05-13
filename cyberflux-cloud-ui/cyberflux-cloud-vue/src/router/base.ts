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

export const LoginRoute: RouteRecordRaw = {
  path: '/login',
  name: 'Login',
  component: () => import('@/views/login.vue'),
  meta: {
    title: 'Login'
  }
}

export const RedirectRoute: AppRouteRecordRaw = {
  path: '/redirect',
  name: 'Redirect',
  component: ActiveLayout,
  meta: {
    title: 'Redirect',
    hideBreadcrumb: true,
  },
  children: [
    {
      path: '/redirect/:path(.*)',
      name: 'Redirect',
      component: () => import('@/views/redirect.vue'),
      meta: {
        title: 'Redirect',
        hideBreadcrumb: true,
      },
    },
  ],
};


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
      name: 'ErrorPage404',
      component: ErrorPage404,
      meta: {
        title: 'ErrorPage',
        hideBreadcrumb: true,
      },
    }
  ]
}
