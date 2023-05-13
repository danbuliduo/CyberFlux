<template>
  <n-layout has-sider class="layout" position="absolute">
    <n-layout-sider bordered class="layout-sider"
      collapse-mode="width"
      position="absolute"
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

    <n-layout :inverted="true">
      <n-layout-header position="absolute">
        <app-header v-model:collapsed="collapsed"/>
      </n-layout-header>

      <n-layout-content class="layout-content"

      >

        <div class="layout-content-main">
          <div class="main-view"
            :class="{
              'noMultiTabs': true,
              'mt-3': true,
            }"
          >
            <app-content/>
          </div>
        </div>
      </n-layout-content>
    </n-layout>

  </n-layout>
</template>

<script lang="ts" setup>

import { AppContent }  from './content'
import { AppHeader } from './header'
import { AppSider } from './sider'

import {
  NLayout, NLayoutSider, NLayoutHeader, NLayoutContent
} from 'naive-ui';

const collapsed = ref<boolean>(false)

</script>

<style lang="scss">
.layout-side-drawer {
  //background-color: #001428;

  .layout-sider {
    min-height: 100vh;
    //box-shadow: 2px 0 8px 0 rgb(29 35 41 / 5%);
    position: relative;
    z-index: 13;
    transition: all 0.2s ease-in-out;
  }
}
</style>

<style lang="scss" scoped>
.layout {
  display: flex;
  flex-direction: row;
  flex: auto;

  &-default-background {
    background: #f5f7f9;
  }

  .layout-sider {
    min-height: 100vh;
    box-shadow: 2px 0 8px 0 rgb(29 35 41 / 5%);
    position: relative;
    z-index: 13;
    transition: all 0.2s ease-in-out;
  }

  .layout-sider-fix {
    position: fixed;
    top: 0;
    left: 0;
  }

  .ant-layout {
    overflow: hidden;
  }

  .layout-right-fix {
    overflow-x: hidden;
    padding-left: 200px;
    min-height: 100vh;
    transition: all 0.2s ease-in-out;
  }

  .layout-content {
    flex: auto;
    min-height: 100vh;
  }

  .n-layout-header.n-layout-header--absolute-positioned {
    z-index: 11;
  }

  .n-layout-footer {
    background: none;
  }
}

.layout-content-main {
  margin: 0 16px 16px;
  position: relative;
  padding-top: 96px;
}

.layout-content-main-fix {
  padding-top: 64px;
}

.fluid-header {
  padding-top: 0;
}

.main-view-fix {
  padding-top: 44px;
}

.noMultiTabs {
  padding-top: 0;
}
</style>
