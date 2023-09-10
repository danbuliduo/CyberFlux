<template>
  <div class="logo">
    <img src="@/assets/logo.svg" alt="CyberFlux" :class="{ 'mr-2': !collapsed }">
    <h2 v-show="!collapsed" class="title">CyberFlux</h2>
  </div>
</template>

<script setup lang="ts">
defineProps({
  collapsed: {
    type: Boolean,
    default: false
  }
})
</script>

<style lang="scss" scoped>
.logo {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 64px;
  line-height: 64px;
  overflow: hidden;
  white-space: nowrap;
  img {
    width: auto;
    height: 36px;
  }
  .title {
    margin: 16px;
  }
}
</style>
