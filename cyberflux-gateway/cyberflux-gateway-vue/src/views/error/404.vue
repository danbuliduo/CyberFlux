<template>
  <main style="padding-top: 10%;">
    <n-result status="404" title="Oops! 404 Not Found." description="生活总归带点荒谬" size="huge">
      <template #footer>
        <n-button @click="() => { router.push('/') }">找点乐子吧</n-button>
      </template>
    </n-result>
  </main>
</template>

<script lang="ts" setup>
import { useRouter } from 'vue-router';
import { NResult , NButton } from 'naive-ui';
const router = useRouter()
</script>
