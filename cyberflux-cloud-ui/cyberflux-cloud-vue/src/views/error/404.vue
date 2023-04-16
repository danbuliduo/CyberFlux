<template>
  <div class="flex flex-col justify-center page-container">
    <div class="text-center">
      <img src="@/assets/vue.svg" alt="">
      </div>
    <div class="text-center">
      <h1 class="text-base text-gray-500">抱歉，服务器出错了</h1>
      <button @click="backToHome">回到首页</button>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { useRouter } from 'vue-router'

const router = useRouter();

function backToHome(): void {
  router.push('/')
}
</script>

<style lang="scss" scoped>
@import '@/assets/style/error/style.scss'
</style>
