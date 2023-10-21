<template>
  <div class="layout-footer">
    <div class="layout-footer-item left">
      <n-button @click="openUrlEvent(WEB_SITE.NODEJS)" text size="tiny">
        <template #icon>
          <n-icon :size="16">
            <logo-nodejs/>
          </n-icon>
        </template>
        node.js
      </n-button>
      <n-divider vertical />
      <n-button @click="openUrlEvent(WEB_SITE.VUEJS)" text size="tiny">
        <template #icon>
          <n-icon :size="16">
            <logo-vue/>
          </n-icon>
        </template>
        vue.js
      </n-button>
      <n-divider vertical />
      <n-button @click="openUrlEvent(WEB_SITE.NAIVEUI)" text size="tiny">
        <template #icon>
        <n-icon :size="16">
          <logo-naive-ui/>
        </n-icon>
        </template>
        naive-ui
      </n-button>
    </div>
    <div class="layout-footer-item right">
      <div class="text-version" >
        CyberFlux:&nbsp;{{ pack.version }}
      </div>
      <n-divider vertical />
      <n-button @click="openUrlEvent(WEB_SITE.GITHUB)" text size="tiny">
        <template #icon>
          <n-icon :size="16">
            <logo-github/>
          </n-icon>
        </template>
        GitHub
      </n-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { NButton, NDivider, NIcon } from 'naive-ui'
import { LogoGithub} from '@vicons/ionicons5'
import { LogoVue, LogoNaiveUi, LogoNodejs } from '@/components/icons'
import pack from '@/setting/package'
import { WEB_SITE } from '@/constant'
function openUrlEvent(url: string) {
  console.log(url)
  window.open(url)
}

</script>
<style lang="scss" scoped>
.layout-footer {
  width: 100%;
  height: $footer-height;
  display: flex;
  align-items: center;
  justify-content: space-between;
  &-item.left {
    width: auto;
    margin-left: 16px;
    display: flex;
    align-items: center;
  }
  &-item.right {
    width: auto;
    margin-right: 16px;
    display: flex;
    align-items: center;
    .text-version {
      font-weight:800;
      font-size: 10px;
      color: #808080;
      text-align: center;
    }
  }
}
</style>
~/src/setting/package.setting
