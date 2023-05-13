<template>
  <div class="layout-header">
    <div class="ml-1">

    </div>

    <div class="layout-header-right">
        <!--div
          class="layout-header-trigger layout-header-trigger-min"
          v-for="item in iconList"
          :key="item.icon.name"
        >
          <n-tooltip placement="bottom">
            <template #trigger>
              <n-icon size="18">
                <component :is="item.icon" v-on="item.eventObject || {}" />
              </n-icon>
            </template>

            <span>{{ item.tips }}</span>
          </n-tooltip>
        </!--div-->

        <!-- 个人中心 -->
        <div class="layout-header-trigger layout-header-trigger-min"
          @click="">
          <n-dropdown trigger="hover" @select="">
            <div class="avatar">
              <n-avatar round size="large" src=''>
                Admin
              </n-avatar>
            </div>
          </n-dropdown>
        </div>
        <!--切换全屏-->
        <div class="layout-header-trigger layout-header-trigger-min"
          @click="toggleFullScreen">
          <n-tooltip placement="bottom">
            <template #trigger>
              <n-icon size="24">
                <component :is="fullscreenIcon"/>
              </n-icon>
            </template>
            <span>全屏</span>
          </n-tooltip>
        </div>
    </div>
  </div>
</template>

<script lang="ts">
import { bool } from 'vue-types';
import {
  NAvatar, NDropdown, NTooltip, NIcon,
} from 'naive-ui';
import {
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  SettingOutlined,
  FullscreenOutlined,
  UserOutlined,
} from '@vicons/antd';
import {
  ContractOutline, ExpandOutline,
} from '@vicons/ionicons5';

export default defineComponent({
  name: "AppHeader",
  components: {
    MenuFoldOutlined,
    MenuUnfoldOutlined,
    SettingOutlined,
    FullscreenOutlined,
    UserOutlined,

    ContractOutline,
    ExpandOutline,

    NIcon,
    NAvatar,
    NDropdown,
    NTooltip,
  },
  props: {
    collapsed: bool().def(false)
  },
  setup(props) {
    // 全屏切换
    const state = reactive({
      fullscreenIcon: 'ExpandOutline'
    })

    const toggleFullscreenIcon = () => (
      state.fullscreenIcon = document.fullscreenElement === null ?
          'ExpandOutline' : 'ContractOutline'
    );

    document.addEventListener('fullscreenchange', toggleFullscreenIcon)

    const toggleFullScreen = () => {
      if (!document.fullscreenElement) {
        document.documentElement.requestFullscreen();
      } else if (document.exitFullscreen) {
        document.exitFullscreen();
      }
    }

    return {
      ...toRefs(state),
      toggleFullScreen,
    }
  }
})
</script>


<style lang="scss" scoped>
@import '@/assets/style/layout/header.scss'
</style>
