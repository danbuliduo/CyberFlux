import store from '@/store'
import { defineStore } from 'pinia'
import { RouteRecordRaw } from 'vue-router'
import { asyncRoutes, constantRoutes } from '@/router'

interface TreeHelperConfig {
  id: string
  pid: string
  children: string
}

const defaultConfig: TreeHelperConfig = {
  id: 'id',
  pid: 'pid',
  children: 'children',
}

export interface RouteState {
  items: RouteRecordRaw[]
  routers: any[]
  addRouters: any[]
  keepAliveComponents: string[]
  dynamicRouteFlag: boolean
}

function filter<T = any>(
  tree: T[],
  func: (n: T) => boolean,
  config: Partial<TreeHelperConfig> = {}
): T[] {

  config = Object.assign({}, defaultConfig, config)
  const children = config.children as string

  function listFilter(list: T[]) {
    return list
      .map((node: any) => ({ ...node }))
      .filter((node) => {
        node[children] = node[children] && listFilter(node[children])
        return func(node) || (node[children] && node[children].length)
      })
  }
  return listFilter(tree)
}

export const useRouteStore = defineStore({
  id: 'route',
  state: (): RouteState => ({
    items: [],
    routers: constantRoutes,
    addRouters: [],
    keepAliveComponents: [],
    dynamicRouteFlag: false,
  }),

  getters: {
    getItems(): RouteRecordRaw[] {
      return this.items
    },
    isDynamicRouteFlag(): boolean {
      return this.dynamicRouteFlag
    },
  },

  actions: {
    getRouters() {
      return toRaw(this.addRouters)
    },
    setDynamicRouteFlag(flag: boolean) {
      this.dynamicRouteFlag = flag
    },
    // 设置动态路由
    setRouters(routers: any[]) {
      this.addRouters = routers
      this.routers = constantRoutes.concat(routers)
    },
    // 设置动态路由
    setItems(items: any[]) {
      this.items = items
    },
    // 设置需要缓存的组件
    setKeepAliveComponents(components: string[]) {
      this.keepAliveComponents = components
    },

    async generateRoutes(data: any) {
      let accessedRouters: RouteRecordRaw[] = []
      //const permissionsList = data.permissions || []

      const routeFilter = (route: any) => {
        //const { meta } = route
        //const { permissions } = meta || {}
        return true
        /*return permissions ? permissionsList.some(
          (item: any) => permissions.includes(item.value)
        ) : true*/
      }
      //const { getPermissionMode } = useProjectSetting();
      //const permissionMode = unref(getPermissionMode);
      //if (permissionMode === 'BACK') {
      if(false) {
        // 动态获取菜单
        try {
          //accessedRouters = await createDynamicRouter();
        } catch (error) {
          console.log(error);
        }
      } else {
        try {
          //过滤账户是否拥有某一个权限，并将菜单从加载列表移除
          accessedRouters = filter(asyncRoutes, routeFilter)
        } catch (error) {
          console.log(error);
        }
      }
      accessedRouters = accessedRouters.filter(routeFilter)
      this.setRouters(accessedRouters)
      this.setItems(accessedRouters)
      return toRaw(accessedRouters)
    }
  }

})

/**
 * @tips 需要在setup()之外使用
 */
export function useRouteStoreWidthOut() {
  return useRouteStore(store);
}
