import { defineStore } from 'pinia';
import { RouteRecordRaw } from 'vue-router';
import { asyncRoutes, constantRoutes } from '@/router';

export interface RouteState {
  items: RouteRecordRaw[];
  routers: RouteRecordRaw[];
  addRouters: RouteRecordRaw[];
  keepAliveComponents: string[];
  dynamicRouteFlag: boolean;
}

interface TreeHelper {
  id: string
  pid: string
  children: string
}

const defaultTreeHelper: TreeHelper = {
  id: "id",
  pid: "pid",
  children: "children",
};

function filter<T>(tree: T[], func: (n: T) => boolean, config: Partial<TreeHelper> = {}): T[] {
  config = Object.assign({}, defaultTreeHelper, config);
  const children = config.children as string;
  function listFilter(list: T[]) {
    return list.map((node: any) => ({ ...node }))
      .filter(node => {
        node[children] = node[children] && listFilter(node[children]);
        return func(node) || (node[children] && node[children].length);
      });
  }
  return listFilter(tree);
}

export const useRouteStore = defineStore('route', () => {
  var state: RouteState = reactive({
    items: [],
    routers: constantRoutes,
    addRouters: [],
    keepAliveComponents: [],
    dynamicRouteFlag: false,
  });

  function items(): RouteRecordRaw[] {
    return state.items;
  }

  function isDynamicRouteFlag(): boolean {
    return state.dynamicRouteFlag;
  }

  function setDynamicRouteFlag(flag: boolean) {
    state.dynamicRouteFlag = flag;
  }

  function routers() {
    return toRaw(state.addRouters)
  }

  function addRouters(routers: RouteRecordRaw[]) {
    state.addRouters = routers
    state.routers = constantRoutes.concat(routers)
  }

  function setItems(items: RouteRecordRaw[]) {
    state.items = items
  }

  function setKeepAliveComponents(components: string[]) {
    state.keepAliveComponents = components
  }

  async function generateRoutes(data: any) {
      let accessedRouters: RouteRecordRaw[] = []
      //const permissionsList = data.permissions || []

      const routeFilter = (route: RouteRecordRaw) => {
        //const { meta } = route
        //const { permissions } = meta || {}
        return true;
        /*return permissions ? permissionsList.some(
          (item: any) => permissions.includes(item.value)
        ) : true*/
      };
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
      addRouters(accessedRouters)
      setItems(accessedRouters)
      return toRaw(accessedRouters)
    }
  return {
    items,
    routers,
    isDynamicRouteFlag,
    setDynamicRouteFlag,
    addRouters,
    setItems,
    setKeepAliveComponents,
    generateRoutes,
  };

})
