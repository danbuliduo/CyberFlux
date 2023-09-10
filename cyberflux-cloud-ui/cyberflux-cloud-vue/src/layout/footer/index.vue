<template>
  <div class="layout-footer">
    <div class="layout-footer-item left">
      <n-button @click="openUrlEvent(urlOptios.nodejs)" text size="tiny">
        <template #icon>
          <n-icon :size="16">
            <logo-nodejs/>
          </n-icon>
        </template>
        Node.js
      </n-button>
      <n-divider vertical />
      <n-button @click="openUrlEvent(urlOptios.vuejs)" text size="tiny">
        <template #icon>
          <n-icon :size="16">
            <logo-vue/>
          </n-icon>
        </template>
        Vue
      </n-button>
      <n-divider vertical />
      <n-button @click="openUrlEvent(urlOptios.naiveui)" text size="tiny">
        <template #icon>
        <n-icon :size="16">
          <logo-naive-ui/>
        </n-icon>
        </template>
        NaiveUI
      </n-button>
    </div>
    <div class="layout-footer-item right">
      <div class="text-version" >
        CyberFlux:&nbsp;{{ pack.version }}
      </div>
      <n-button @click="openUrlEvent(urlOptios.github)" text size="tiny">
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
import pack from '@/store/setting/package.setting'

const urlOptios = {
  nodejs: "https://nodejs.org",
  vuejs: "https://vuejs.org/",
  naiveui: "https://www.naiveui.com/",
  github: "https://github.com/Teng-Ming/CyberFlux",
}

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
    //gap: 12px;
  }
  &-item.right {
    width: auto;
    margin-right: 16px;
    display: flex;
    align-items: center;
    gap: 12px;
    .text-version {
      font-weight:800;
      font-size: 10px;
      color: #808080;
      text-align: center;
    }
  }
}
</style>
