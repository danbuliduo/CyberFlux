<template>
  <main>
    <!--highlightjs language='javascript' code="const s = new Date().toString()" /-->
    <div>
      <div v-for ="log in logTable">
        <div>
          {{ parseTime(log.timeStamp, '') }} [{{ log.level }}]  [{{ log.threadName}}] - {{log.message}}
        </div>
      </div>
    </div>
  </main>
</template>

<script setup lang="ts">
import { useGatewayEventSource, LogUpdateEvent} from '@/service/sse/gateway';
import { parseTime } from '@/utils';
interface LoggerMessage {
  message: string;
  level: string;
  threadName: string;
  timeStamp: number;
}

const logTable = ref<LoggerMessage[]>([])
function logger() {
  return
}

onMounted(() => {
  if (window.EventSource) {
    useGatewayEventSource().addEventListener(LogUpdateEvent, (event) => {
      const log = JSON.parse(event.data) as LoggerMessage
      logTable.value.push(log)
    })
  }
})
</script>
