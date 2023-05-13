<template>
  <div class="logo">
    <img src="@/assets/logo.svg" alt="CyberFlux" :class="{ 'mr-2': !collapsed }">
    <h2 v-show="!collapsed" class="title">CyberFlux</h2>
  </div>
</template>

<script lang="ts">
import { bool } from 'vue-types'
export default {
  props: {
    collapsed: bool().def(false)
  }
}
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
