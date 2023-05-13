<template>
  <banner class="ground-glass-background" :collapsed="collapsed"/>
  <n-menu
    mode="vertical"
    :options="options"
    :collapsed="collapsed"
    :collapsed-width="64"
    :collapsed-icon-size="20"
    :icon-size="20"
    :indent="32"
    @update-value="clickMenuItem"
    @update-expanded-keys="expandMenuItem"
  />
  <div class="menu-fold ground-glass-background"
    @click="() => $emit('update:collapsed', !collapsed)">
    <n-icon size="20" v-if="collapsed">
      <menu-unfold-outlined />
    </n-icon>
    <n-icon size="20" v-else>
      <menu-fold-outlined />
    </n-icon>
  </div>
</template>

<script lang="ts">
import { bool } from 'vue-types'
import { NIcon, NMenu } from 'naive-ui'
import { useRoute, useRouter  } from 'vue-router'
import { createMenu, createMixMenu } from '@/utils'
import { useAsyncRouteStore } from '@/store/modules/async-route'
import { useLayoutStateStore } from '@/store/modules/layout'
import { Banner }  from '@/components/common'
import {
  MenuFoldOutlined,
  MenuUnfoldOutlined,
} from '@vicons/antd';

export default defineComponent({
  name: 'AppSider',
  components: {
    Banner, NIcon, NMenu,
    MenuFoldOutlined, MenuUnfoldOutlined,
  },
  props: {
    collapsed: bool().def(false)
  },
  emits: [
    'update:collapsed',
    'clickMenuItem'
  ],
  setup(props, { emit }) {
    const route = useRoute();
    const router = useRouter();
    const routeStore = useAsyncRouteStore()
    const layoutStore = useLayoutStateStore()

    const options = ref<any[]>([])
    const selectedKeys = ref<string>(route.name as string)
    const matched = route.matched
    const state = reactive({
      openKeys: matched && matched.length ? matched.map(item => item.name) : []
    })

    //查找是否存在子路由
    function existRouteChildren(key: string): boolean {
      if (!key) return false;
      const routeChildren: string[] = []
      for (const { children, key } of unref(options)) {
        if (children && children.length) {
          routeChildren.push(key as string);
        }
      }
      return routeChildren.includes(key);
    }

    function updateMenuItem(): void {
      if (true) {
        options.value = createMenu(routeStore.getItems)
      } else {
        //混合菜单
        const firstRouteName: string = (route.matched[0].name as string) || '';
        //menuItems.value = createMixMenu(asyncRouteStore.getMenus, firstRouteName, props.location);
        const activeMenu: string = route?.matched[0].meta?.activeMenu as string;
        //headerMenuSelectKey.value = (activeMenu ? activeMenu : firstRouteName) || '';
      }
    }
    // 点击菜单项
    function clickMenuItem(key: string): void {
      if (/http(s)?:/.test(key)) {
        window.open(key)
      } else {
        router.push({ name: key })
      }
      emit('clickMenuItem' as any, key)
    }

    // 展开菜单
    function expandMenuItem(openKeys: string[]): void {
      if (openKeys) {
        const latestOpenKey = openKeys.find(
          key => state.openKeys.indexOf(key) === -1
        )
        if(existRouteChildren(latestOpenKey as string)) {
          state.openKeys = latestOpenKey ? [latestOpenKey] : []
        } else {
          state.openKeys = openKeys
        }
      }
    }

    watch(
      () => layoutStore.silder.mixed,
      () => {
        updateMenuItem()
        if(props.collapsed) {
          emit('update:collapsed', !props.collapsed)
        }
      }
    )

    watch(
      () => route.fullPath,
      () => updateMenuItem()
    )

    onMounted(() => {
      updateMenuItem()
    })

    return {
      options,
      clickMenuItem,
      expandMenuItem,
    }
  }
})
</script>
<style lang="scss" scoped>
.n-menu {
  font-weight: bold;
}
.ground-glass-background {
  background: rgba(255, 255, 255, 0.04);
  box-shadow: inset 0 0 6px rgba(255, 255, 255, 0.04);
  backdrop-filter: blur(8px);
  -webkit-backdrop-filter: blur(8px);
  &:hover {
    background: rgba(255, 255, 255, 0.08);
    box-shadow: inset 0 0 6px rgba(255, 255, 255, 0.08);
  }
}
.menu-fold {
  position: absolute;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 54px;
  //line-height: 54px;
  bottom: 0px;
  left: 0px;
  right: 0px;
  margin: auto;
}
</style>
