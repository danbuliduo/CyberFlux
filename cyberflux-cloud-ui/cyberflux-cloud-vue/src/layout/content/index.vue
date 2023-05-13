<template>
  <router-view v-slot="{ Component, route }">
    <transition name="fade-slide" mode="out-in" appear>
      <component v-if="route.meta.noKeepAlive"
        :is="Component" :key="route.fullPath"/>
      <keep-alive v-else>
        <component :is="Component" :key="route.fullPath"/>
      </keep-alive>
    </transition>
  </router-view>
</template>

<script lang="ts">
export default defineComponent({
  name: "AppContent"
})
</script>
