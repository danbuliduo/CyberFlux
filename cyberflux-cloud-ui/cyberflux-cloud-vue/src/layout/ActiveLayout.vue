<template>
  <n-layout class="layout" has-sider>
    <n-layout-sider class="layout-sider"
      show-trigger = "bar"
      @collapse = "collapsed = true"
      @expand = "collapsed = false"
      collapse-mode = "width"
      :collapsed-width = "64"
      :native-scrollbar="false"
    >
      <banner :collapsed="collapsed"/>

      <sider-bar v-model:collapsed="collapsed"/>
    </n-layout-sider>

    <n-layout>
      <n-layout-header>

      </n-layout-header>
      <n-layout-content class="layout-content">
        <div class="main-view">
          <content/>
        </div>
      </n-layout-content>

    </n-layout>
  </n-layout>
</template>

<script lang="ts" setup>

import { Banner } from './banner'
import { Content } from './content'
import { SiderBar } from './siderbar'

import {
  NLayout,
  NLayoutSider,
  NLayoutHeader,
  NLayoutContent
} from 'naive-ui'

const collapsed = ref<boolean>(false)

</script>
<style lang="scss">
.layout-side-drawer {
  background-color: rgb(0, 20, 40);

  .layout-sider {
    min-height: 100vh;
    box-shadow: 2px 0 8px 0 rgb(29 35 41 / 5%);
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
  margin: 0 10px 10px;
  position: relative;
  padding-top: 64px;
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
