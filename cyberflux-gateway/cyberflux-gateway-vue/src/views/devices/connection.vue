<template>
  <mian>
    <n-data-table style="margin-top: 12px; margin-bottom: 12px;"
        :columns="tableColumns"
        :data="tableData"
        :pagination="pagination"
      />
  </mian>
</template>
<script setup lang="ts">
import type { DataTableColumns } from 'naive-ui';
import { NDataTable, NTag } from 'naive-ui';
import { MqttChannel }  from '@/store/modules/channel'
import { ChannelStore } from '@/store'
import { parseTime } from '@/utils'


const pagination = reactive({
  pageSize: 20,
  //showSizePicker: true,
})

const tableColumns: DataTableColumns<MqttChannel> = [
  { title: 'Client ID', key: 'clientId' },
  { title: 'Address', key: 'address' },
  {
    title: 'Name', key: 'username', render(item) {
      return item.username ? item.username : 'undefined'
    }
  },
  {
    title: 'Status', key: 'online', render(item) {
      let on = item.online
      return h(NTag, {
        size: 'small',
        type: on ? 'success' : 'error',
        round: true,
        bordered: false,
      }, { default: () => on ? '在线' : '离线' })
    }
  },
  { title: 'Keep Alive', key: 'keepAlive', },
  {
    title: 'CleanStart', key: 'cleanSession', render(item) {
      return item.cleanSession.toString()
    }
  },
  { title: 'SessionExpiry', key: 'sessionExpiry', render(item) {
      return 0
    }
  },
  { title: 'ConnectTime', key: 'connectTime', render(item) {
      const connectTime = item.connectTime
      return parseTime(connectTime, '')
    }
  },
]

const tableData = ref<MqttChannel[]>(ChannelStore.collectByType<MqttChannel>('MQTT'))


function update() {
  tableData.value = ChannelStore.collectByType<MqttChannel>('MQTT')
}


onBeforeUpdate(() => {
  update();
})
</script>
