<template>
  <timeago :datetime="date" />
</template>

<script lang="ts">
import timeago from 'vue-timeago3'
export default {
  components: {
    timeago
  },
  data() {
    return {
      date: new Date()
    }
  }
}
</script>
