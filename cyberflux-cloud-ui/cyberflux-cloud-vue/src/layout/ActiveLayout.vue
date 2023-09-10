<template>
  <n-layout position='absolute' :native-scrollbar="false">
    <n-layout-header bordered>
      <app-header v-model:collapsed="collapsed"/>
    </n-layout-header>

    <n-layout has-sider class="layout" position='absolute'>
      <n-layout-sider bordered :builtin-theme-overrides="lightTheme"
        show-trigger="bar"
        collapse-mode="width"
        :collapsed="collapsed"
        :collapsed-width="64"
        :inverted="false"
        :native-scrollbar="false"
        :width="256"
        @collapse="collapsed = true"
        @expand="collapsed = false"
      >
        <app-sider v-model:collapsed="collapsed"/>
      </n-layout-sider>

      <n-layout-content :native-scrollbar="false" content-style="padding: 24px;">
        <app-content/>
      </n-layout-content>

    </n-layout>

    <n-layout-footer bordered position='absolute'>
      <app-footer/>
    </n-layout-footer>

  </n-layout>

</template>
<script setup lang="ts">
import {
  NLayout, NLayoutHeader, NLayoutSider, NLayoutContent, NLayoutFooter, lightTheme
} from 'naive-ui'
import { AppContent }  from '@/layout/content'
import { AppHeader } from '@/layout/header'
import { AppSider } from '@/layout/sider'
import { AppFooter } from '@/layout/footer'
const collapsed = ref<boolean>(false)
</script>

<style lang="scss" scoped>

.layout{
  margin-top: $header-height + 1;
  margin-bottom: $footer-height + 1;
}
</style>
