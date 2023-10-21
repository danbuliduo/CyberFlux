import {RouteRecordRaw} from 'vue-router';
import {ActiveLayout} from '@/router/constant';
import {renderIcon} from '@/utils'
import { Dashboard } from '@/components/icons'

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
    path:'/dashboard',
    name: 'dashboard',
    redirect: '/dashboard/overview',
    component: ActiveLayout,
    meta: {
      title: '仪表盘',
      icon: renderIcon(Dashboard),
      permissions: [],
      sort: 0,
    },
    children: [
      {
        path: 'overview',
        name: 'dashboard-overview',
        meta: {
          title: '概览',
          permissions: [],
          affix: true,
        },
        component: () => import('@/views/dashboard/overview.vue'),
      },
      {
        path: 'monitor',
        name: 'dashboard-monitor',
        meta: {
          title: '监控',
          permissions: [],
          affix: true,
        },
        component: () => import('@/views/dashboard/monitor.vue'),
      }
    ]
  }
]

export default routes;
