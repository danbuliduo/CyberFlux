<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/image/vue.png">
    <HelloWorld msg="Welcome to Your Vue.js App"/>
    <div>
      {{ $t('action') }}
    </div>
  </div>
</template>

<script lang="ts">

import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'HomeView',
  components: {
    HelloWorld
  }
}
</script>
