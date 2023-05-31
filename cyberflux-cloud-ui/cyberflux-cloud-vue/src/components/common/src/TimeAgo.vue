<template>
  <timeago
    :datetime="date"
    :auto-update="1"
    :converter-options="{
      includeSeconds: true,
      addSuffix: false,
      useStrict: true,
      uint: 'second'
    }"
  />
</template>

<script lang="ts" setup>
defineProps({
  date: {
    type: [String, Number, Date],
    default: new Date()
  }
})
</script>
