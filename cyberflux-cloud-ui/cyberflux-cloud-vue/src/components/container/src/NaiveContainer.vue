<template>
  <n-config-provider :theme="theme">
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
import type { BuiltInGlobalTheme } from 'naive-ui/es/themes/interface'
import { darkTheme, lightTheme } from 'naive-ui';

import {
  NGlobalStyle,
  NConfigProvider,
  NDialogProvider,
  NNotificationProvider,
  NLoadingBarProvider,
  NMessageProvider
} from 'naive-ui'


const theme: BuiltInGlobalTheme = darkTheme
</script>


