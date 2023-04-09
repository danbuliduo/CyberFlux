<template>
  <div>
    引入naive-ui图标库
  </div>
  <div>
    <n-button>naive-ui</n-button>
  </div>
  <n-icon size="40" color="green" depth="4" :component="GameControllerOutline" />
</template>

<script setup lang="ts">
import { NButton } from 'naive-ui'
import { GameControllerOutline } from '@vicons/ionicons5'
</script>


