import { RouteRecordRaw } from 'vue-router';
import { ActiveLayout  } from '@/router/constant';
import { RouterPath, RouterName } from '@/enums';
import { renderIcon } from '@/utils';
import { Device } from '@/components/icons';

const routes: Array<RouteRecordRaw> = [
  {
    path: '/devices',
    name: 'Devices',
    redirect: '/devices/connection',
    component: ActiveLayout,
    meta: {
      title: '设备管理',
      icon: renderIcon(Device),
      permissions: ['devices-connection'],
      sort: 0,
    },
    children: [
      {
        path: 'connection',
        name: 'devices-connection',
        meta: {
          title: '连接管理',
          permissions: ['devices-connection'],
          affix: true,
        },
        component: () => import('@/views/devices/connection.vue'),
      },
      {
        path: 'subscription',
        name: 'devices-subscription',
        meta: {
          title: '订阅管理',
          permissions: ['devices-subscription'],
          affix: true,
        },
        component: () => import('@/views/devices/subscription.vue'),
      },
    ]
  }
]

export default routes;
