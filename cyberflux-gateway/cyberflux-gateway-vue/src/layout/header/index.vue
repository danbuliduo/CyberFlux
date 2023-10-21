<template>
  <div class="layout-header">
    <banner class="ml-1" style="margin-left: 16px;"/>
    <n-space :size="4" style="margin-right: 16px; align-items: center;">
      <n-tooltip
        placement="bottom"
        trigger="hover"
      >
        <template #trigger>
          <n-button quaternary size="small" @click="switchThemeEvent">
            <template #icon>
              <n-icon :component="theme === 'light'? Moon : Sunny"/>
            </template>
          </n-button>
        </template>
        切换主题
      </n-tooltip>

      <n-dropdown trigger="hover"
        :show-arrow="true"
        :options="langOptions"
        @select="selectLangEvent"
      >
        <n-button quaternary size="small">
          <template #icon>
            <n-icon :component="Language" />
          </template>
        </n-button>
      </n-dropdown>

      <n-dropdown trigger="hover"
        :show-arrow="true"
        :options="subMenuOptions"
        @select="selectSubMenuEvent"
      >
        <n-tag round :bordered="false" size="large">
        {{ AccountStore.user.username }}
        <template #avatar>
          <n-avatar class="flux-colorful">
            {{ AccountStore.user.username[0]?.toUpperCase() }}
          </n-avatar>
        </template>
        </n-tag>
      </n-dropdown>
    </n-space>
  </div>
</template>


<script setup lang="ts">
import { bool } from 'vue-types';
import { useI18n } from 'vue-i18n';
import useHotKey, { HotKey } from 'vue3-hotkey';
import { NButton, NTag, NTooltip, NSpace, NDropdown, NIcon, NAvatar } from 'naive-ui';
import { Language, Moon, Sunny } from '@vicons/ionicons5';
import router from '@/router';
import { storage } from '@/utils';
import { langOptions } from '@/langs';
import { Banner } from '@/components/common';
import { Lang } from '@/enums/lang';
import { I18N_LANG_KEY } from '@/constant';
import { RouterPath } from '@/enums/router';
import { AccountStore } from '@/store';
import { Account } from '~/src/store/modules/account';

const { locale, t } = useI18n()
// 属性控制
const props = defineProps({
  collapsed: bool().def(false)
})


const theme = ref<'light' | 'dark'>()
const hotkes = ref<HotKey[]>([
  {
    keys: ['ctrl', 'enter'],
    preventDefault: true,
    handler(keys) {
      fullScreenEvent()
    }
  },
])


// reactive
const reactor = reactive({

})

const subMenuOptions = [{
  label: '通知管理',
  key: 0,
},{
  label: '用户管理',
  key: 1,
}, {
  type: 'divider',
},{
  label: '退出登录',
  key: 2,
}]


async function selectSubMenuEvent(key: number) {
  switch (key) {
    case 0: break;
    case 1: router.push(RouterPath.SETTINGS_USER); break;
    case 2: await AccountStore.logout().then(() => {
      router.push(RouterPath.LOGIN)
    }); break;
    default: break;
  }
}

function selectLangEvent(key: Lang) {
  locale.value = key
  storage.set(I18N_LANG_KEY, key)
  window.$message.success(t('action'))
}

// 切换全屏
function fullScreenEvent() {
  if (!document.fullscreenElement) {
    document.documentElement.requestFullscreen();
  } else if (document.exitFullscreen) {
    document.exitFullscreen();
  }
}
function switchThemeEvent() {
  theme.value = theme.value === 'light' ? 'dark' : 'light'
}

onBeforeMount(async () => {
  await AccountStore.reverseauth()
  useHotKey(hotkes.value)
  // 添加全屏事件监听器
  document.addEventListener('fullscreenchange', () => {})
  //
})
</script>


<style lang="scss" scoped>
.layout-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0;
  height: $header-height;
  transition: all 0.2s ease-in-out;
  width: 100%;
}

</style>
