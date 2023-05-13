<template>
  <div style="padding: 10% 0; text-align: center;">
    <n-result status="418" title="418 I'm a Teapot." description="一切尽在不言中" size="huge">
      <template #footer>
        <n-button @click="() => { router.push('/') }">接受真相就是这么简单</n-button>
      </template>
    </n-result>
  </div>
</template>

<script lang="ts" setup>
import { useRouter } from 'vue-router';
import { NResult, NButton } from 'naive-ui';
const router = useRouter()
</script>
