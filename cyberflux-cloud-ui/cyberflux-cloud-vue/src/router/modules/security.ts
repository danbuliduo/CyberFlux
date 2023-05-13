import { RouteRecordRaw } from 'vue-router';
import { ActiveLayout  } from '@/router/constant';
import { RouterPath, RouterName } from '@/enums'
import { renderIcon } from '@/utils'
import { SecurityAccess } from '@/components/icons'



const routes: Array<RouteRecordRaw> = [
  {
    path: '/security',
    name: 'Security',
    redirect: '/security/authentication',
    component: ActiveLayout,
    meta: {
      title: '访问控制',
      icon: renderIcon(SecurityAccess),
      permissions: ['security-authentication'],
      sort: 0,
    },
    children: [
      {
        path: 'authentication',
        name: 'security-authentication',
        meta: {
          title: '认证',
          affix: true,
        },
        component: () => import('@/views/security/authentication.vue'),
      },
      {
        path: 'authorization',
        name: 'security-authorization',
        meta: {
          title: '授权',
          affix: true,
        },
        component: () => import('@/views/security/authorization.vue'),
      },
      {
        path: 'interception',
        name: 'security-interception',
        meta: {
          title: '拦截',
          affix: true,
        },
        component: () => import('@/views/security/interception.vue'),
      }
    ]
  }
]

export default routes;
