<template>
  <n-layout position='absolute' :native-scrollbar="false">
    <n-layout-header bordered>
      <app-header v-model:collapsed="collapsed"/>
    </n-layout-header>

    <n-layout style="margin-top: 65px; margin-bottom: 25px;" has-sider position='absolute'>
      <n-layout-sider bordered
        show-trigger="bar"
        collapse-mode="width"
        :collapsed="collapsed"
        :collapsed-width="64"
        :inverted="true"
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
      <div style="height: 24px;"></div>
    </n-layout-footer>

  </n-layout>

</template>
<script lang="ts" setup>

import { AppContent }  from './content'
import { AppHeader } from './header'
import { AppSider } from './sider'

import {
  NLayout, NLayoutHeader, NLayoutSider, NLayoutContent, NLayoutFooter
} from 'naive-ui';

const collapsed = ref<boolean>(false)

</script>


