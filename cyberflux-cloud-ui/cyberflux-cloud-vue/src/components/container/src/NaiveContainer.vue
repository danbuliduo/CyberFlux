<template>
  <n-config-provider :theme="darkTheme">
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

<script lang="ts">
import { darkTheme, lightTheme } from 'naive-ui';
import {
  NGlobalStyle,
  NConfigProvider,
  NDialogProvider,
  NNotificationProvider,
  NLoadingBarProvider,
  NMessageProvider
} from 'naive-ui';

export default defineComponent({
  name: 'NaiveContainer',
  components: {
    NConfigProvider,
    NDialogProvider,
    NGlobalStyle,
    NLoadingBarProvider,
    NMessageProvider,
    NNotificationProvider,
  },
  setup() {
    return {
      darkTheme,
      lightTheme,
    }
  }
});
</script>


