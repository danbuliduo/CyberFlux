<template>
  <main style="padding-top: 10%;">
    <n-result status="500" title="500 Server Internal Error." description="说明该雇更多程序员了" size="huge">
      <template #footer>
        <n-button @click="() => { router.push('/') }">散财消灾</n-button>
      </template>
    </n-result>
  </main>
</template>

<script lang="ts" setup>
import { useRouter } from 'vue-router';
import { NResult, NButton } from 'naive-ui';
const router = useRouter()
</script>
