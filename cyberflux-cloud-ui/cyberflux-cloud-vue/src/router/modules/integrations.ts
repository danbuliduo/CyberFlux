import { RouteRecordRaw } from 'vue-router';
import { ActiveLayout  } from '@/router/constant';
import { RouterPath, RouterName } from '@/enums'
import { renderIcon } from '@/utils'
import { DataIntegration } from '@/components/icons'



const routes: Array<RouteRecordRaw> = [
  {
    path: '/integrations',
    name: 'Integrations',
    redirect: '/integrations/bridge',
    component: ActiveLayout,
    meta: {
      title: '数据集成',
      icon: renderIcon(DataIntegration),
      permissions: ['integrations-bridge'],
      sort: 0,
    },
    children: [
      {
        path: 'bridge',
        name: 'integrations-bridge',
        meta: {
          title: '数据桥接',
          permissions: ['integrations-bridge'],
          affix: true,
        },
        component: () => import('@/views/integrations/bridge.vue'),
      },
      {
        path: 'rules',
        name: 'integrations-rules',
        meta: {
          title: '规则引擎',
          permissions: ['integrations-rules'],
          affix: true,
        },
        component: () => import('@/views/integrations/rules.vue'),
      },
    ]
  }
]

export default routes;
