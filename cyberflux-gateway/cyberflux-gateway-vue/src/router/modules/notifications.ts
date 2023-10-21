import { RouteRecordRaw } from 'vue-router';
import { ActiveLayout  } from '@/router/constant';
import { renderIcon } from '@/utils'
import { Notification } from '@/components/icons'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/notifications',
    name: 'notifications',
    redirect: '/notifications/event',
    component: ActiveLayout,
    meta: {
      title: '消息通知',
      icon: renderIcon(Notification),
      permissions: [],
      sort: 0,
    },
    children: [
      {
        path: 'alert',
        name: 'notifications-alert',
        meta: {
          title: '警告',
          permissions: [],
          affix: true,
        },
        component: () => import('@/views/notifications/alert.vue'),
      },
      {
        path: 'event',
        name: 'notifications-event',
        meta: {
          title: '事件',
          permissions: [],
          affix: true,
        },
        component: () => import('@/views/notifications/event.vue'),
      },
      {
        path: 'log',
        name: 'notifications-log',
        meta: {
          title: '日志',
          permissions: [],
          affix: true,
        },
        component: () => import('@/views/notifications/log.vue'),
      },
    ]
  }
]

export default routes;
