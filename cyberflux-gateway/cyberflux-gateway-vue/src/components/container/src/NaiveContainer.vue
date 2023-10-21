<template>
  <n-config-provider
    :hljs="hljs"
    :theme="props.theme === 'light' ? lightTheme : darkTheme"
    :locale="zhCN"
    :date-locale="dateZhCN">
    <n-global-style />
    <n-loading-bar-provider>
      <n-message-provider>
        <n-notification-provider>
          <n-dialog-provider>
            <slot slot="default"></slot>
          </n-dialog-provider>
        </n-notification-provider>
      </n-message-provider>
    </n-loading-bar-provider>
  </n-config-provider>
</template>

<script setup lang="ts">
import { darkTheme, lightTheme, zhCN, dateZhCN } from 'naive-ui';
import hljs from 'highlight.js/lib/core';
import {
  NGlobalStyle,
  NConfigProvider,
  NDialogProvider,
  NNotificationProvider,
  NLoadingBarProvider,
  NMessageProvider
} from 'naive-ui'

interface Props {
  theme: 'light' | 'dark';
}

const props =  withDefaults(defineProps<Props>(), {
  theme: 'dark',
})
</script>


