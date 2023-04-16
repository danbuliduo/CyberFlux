<template>
  <div class="logo">
    <img src="@/assets/logo.svg" alt="" :class="{ 'mr-2': !collapsed}">
    <h2 v-show="!collapsed" class="title">CyberFlux Cloud</h2>
  </div>
</template>

<script lang="ts">
import { bool } from 'vue-types'
export default {
  name: 'Banner',
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
    height: 40px;
  }

  .title {
    margin: 16px;
  }
}
</style>
