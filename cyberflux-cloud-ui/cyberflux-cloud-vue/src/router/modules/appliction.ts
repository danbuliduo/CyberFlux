import { RouteRecordRaw } from 'vue-router';
import { ActiveLayout  } from '@/router/constant';
import { RouterPath, RouterName } from '@/enums'
import { renderIcon } from '@/utils'
import { AppstoreAddOutlined } from '@vicons/antd'

/**
 * @param name 路由名称, 必须设置,且不能重名
 * @param meta 路由元信息（路由附带扩展信息）
 * @param redirect 重定向地址, 访问这个路由时,自定进行重定向
 * @param meta.disabled 禁用整个菜单
 * @param meta.title 菜单名称
 * @param meta.icon 菜单图标
 * @param meta.keepAlive 缓存该路由
 * @param meta.sort 排序越小越排前
 * */

const routes: Array<RouteRecordRaw> = [
  {
    path: '/application',
    name: 'Application',
    redirect: '/application/fisheries',
    component: ActiveLayout,
    meta: {
      title: '应用管理',
      icon: renderIcon(AppstoreAddOutlined),
      sort: 0,
    },
    children: [
      {
        path: 'fisheries',
        name: 'application-fisheries',
        meta: {
          title: '智慧渔业',
          affix: true,
        },
        component: () => import('@/views/apps/fisheries.vue'),
      },
      {
        path: 'agriculture',
        name: 'application-agriculture',
        meta: {
          title: '智慧农业',
          affix: true,
        },
        component: () => import('@/views/apps/agriculture.vue'),
      },
      {
        path: 'monitor',
        name: 'application-monitor',
        meta: {
          title: '智慧监测',
          affix: true,
        },
        component: () => import('@/views/apps/monitor.vue'),
      },
    ]
  }
]

export default routes;
