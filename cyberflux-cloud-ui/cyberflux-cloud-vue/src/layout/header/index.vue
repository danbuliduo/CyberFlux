<template>
  <div class="layout-header">
    <banner class="ml-1" style="margin-left: 16px;"/>
    <n-space :size="0" style="margin-right: 16px;">
      <n-dropdown trigger="hover"
        :show-arrow="true"
        :options="langOptions"
         @select="langSwitch"
      >
        <n-button quaternary>
          <template #icon>
            <n-icon>
              <language/>
            </n-icon>
          </template>
          {{ $t('label') }}
        </n-button>
      </n-dropdown>

      <n-button quaternary @click="toggleFullScreen">
        <template #icon>
          <n-icon>
            <component :is="reactor.fullscreenIcon"/>
          </n-icon>
        </template>
        {{ reactor.fullscreenText }}
      </n-button>
    </n-space>
  </div>
</template>

<script setup lang="ts">
import { bool } from 'vue-types'
import { useI18n } from 'vue-i18n'
import { NButton, NSpace, NDropdown, NIcon } from 'naive-ui'
import { Language } from '@vicons/ionicons5'
import { ContractOutline, ExpandOutline } from '@vicons/ionicons5'
import { storage } from '@/utils'
import { langOptions } from '@/langs'
import { I18N_LANG_KEY, Lang, } from '@/enums'
import { Banner } from '@/components/common'

const { locale, t } = useI18n()
// 属性控制
const props = defineProps({
  collapsed: bool().def(false)
})
// reactive
const reactor = reactive({
  fullscreenIcon: ExpandOutline,
  fullscreenText: "全屏模式"
})

function langSwitch(key: Lang): void {
  locale.value = key
  storage.set(I18N_LANG_KEY, key)
  window.$message.success(t('action'))
}

// 切换全屏
function toggleFullScreen() {
  if (!document.fullscreenElement) {
    document.documentElement.requestFullscreen();
  } else if (document.exitFullscreen) {
    document.exitFullscreen()
  }
}
// 添加全屏事件监听器
document.addEventListener('fullscreenchange', () => {
  if(document.fullscreenElement === null) {
    reactor.fullscreenIcon = ExpandOutline
    reactor.fullscreenText = "全屏模式"
  } else {
    reactor.fullscreenIcon = ContractOutline
    reactor.fullscreenText = "退出全屏"
  }
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
