<template>
  <n-config-provider :theme="darkTheme">
    <n-global-style />
    <n-dialog-provider>
      <n-notification-provider>
        <n-message-provider>
          <slot></slot>
        </n-message-provider>
      </n-notification-provider>
    </n-dialog-provider>
  </n-config-provider>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

import {
  darkTheme,
  lightTheme,
  NGlobalStyle,
  NConfigProvider,
  NDialogProvider,
  NNotificationProvider,
  NMessageProvider
} from 'naive-ui'


export default defineComponent({
  name: 'NaiveContainer',
  components: {
    NDialogProvider,
    NGlobalStyle,
    NNotificationProvider,
    NMessageProvider,
    NConfigProvider,
  },
  setup() {
    return {
      darkTheme, lightTheme
    }
  }
});
</script>
