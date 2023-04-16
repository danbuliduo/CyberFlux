<template>
  <n-menu
    :options="menuItems"
    :collapsed="collapsed"
  >
  </n-menu>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import { bool } from 'vue-types'
import { useRoute, useRouter  } from 'vue-router';
import { createMenu, createMixMenu } from '@/utils'
import { useAsyncRouteStore } from '@/store/modules/async-route'
import {
  NMenu
} from 'naive-ui'

export default defineComponent({
  name: 'SiderBar',
  props: {
    collapsed: bool().def(false)
  },
  setup() {
    const route = useRoute();
    const router = useRouter();
    const asyncRouteStore = useAsyncRouteStore();
    const menuItems = ref<any[]>([])

    function updateMenu() {
      if (true) {
        menuItems.value = createMenu(asyncRouteStore.getMenus);
        console.log(menuItems.value)
      } else {
        //混合菜单
        const firstRouteName: string = (route.matched[0].name as string) || '';
        //menuItems.value = createMixMenu(asyncRouteStore.getMenus, firstRouteName, props.location);
        //const activeMenu: string = currentRoute?.matched[0].meta?.activeMenu as string;
        //headerMenuSelectKey.value = (activeMenu ? activeMenu : firstRouteName) || '';
      }
    }
    onMounted(() => {
      console.debug("updateMenu")
      updateMenu();
    });

    return {
      menuItems
    }
  }
})

</script>
