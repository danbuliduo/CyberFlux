<template>
  <n-menu style="font-weight: bold;"
    mode="vertical"
    :options="options"
    :collapsed="collapsed"
    :collapsed-width="64"
    :collapsed-icon-size="20"
    :icon-size="20"
    :indent="32"
    :value="getSelectedKeys"
    @update-value="clickMenuItem"
    @update-expanded-keys="expandMenuItem"
  />
</template>

<script setup lang="ts">
import { bool } from 'vue-types'
import { NMenu } from 'naive-ui'
import { useRoute, useRouter  } from 'vue-router'
import { createMenu, createMixMenu } from '@/utils'
import { useRouteStore } from '@/store/modules/route'


onBeforeMount(() => {
  updateMenuItem()
  // 监听路由变化, 更新菜单元素
  watch(() => route.fullPath, async () => updateMenuItem())
})

const props = defineProps({
  collapsed: bool().def(false)
})

const emits = defineEmits([
  'update:collapsed',
  'clickMenuItem'
])

const route = useRoute()
const router = useRouter()
const asyncRouteStore = useRouteStore()

const options = ref<any[]>([])
const selectedKeys = ref<string>(route.name as string)
const matched = route.matched


const state = reactive({
  openKeys: matched && matched.length ? matched.map(item => item.name) : []
})

const getSelectedKeys = computed(() => {
  return unref(selectedKeys)
})

//查找是否存在子路由
function existRouteChildren(key: string): boolean {
  if (!key) return false
  const routeChildren: string[] = []
  for (const { children, key } of unref(options)) {
    if (children && children.length) {
      routeChildren.push(key as string)
    }
  }
  return routeChildren.includes(key)
}


function updateMenuItem(): void {
  options.value = createMenu(asyncRouteStore.items())
  state.openKeys = route.matched.map(item => item.name)
  const activeMenuItem: string = (route.meta?.activeMenu as string) || ''
  selectedKeys.value = activeMenuItem ? activeMenuItem : (route.name as string)
}

// 点击菜单项
function clickMenuItem(key: string): void {
  if (/http(s)?:/.test(key)) {
    window.open(key)
  } else {
    router.push({ name: key })
  }
  emits('clickMenuItem' as any, key)
}

// 展开菜单
function expandMenuItem(openKeys: string[]): void {
  if (!openKeys) return
  const latestOpenKey = openKeys.find(
    key => state.openKeys.indexOf(key) === -1
  )
  if (existRouteChildren(latestOpenKey as string)) {
    state.openKeys = latestOpenKey ? [latestOpenKey] : []
  } else {
    state.openKeys = openKeys
  }
}
</script>
