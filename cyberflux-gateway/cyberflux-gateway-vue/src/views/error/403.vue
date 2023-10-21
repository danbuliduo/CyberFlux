<template>
  <main style="padding-top: 10%;">
    <n-result status="403" title="403 Forbidden Access." description="总有些门是对你关闭的" size="huge">
      <template #footer>
        <n-button @click="() => { router.push('/') }">放轻松</n-button>
      </template>
    </n-result>
  </main>
</template>

<script lang="ts" setup>
import { useRouter } from 'vue-router';
import { NResult, NButton } from 'naive-ui';
const router = useRouter()
</script>
