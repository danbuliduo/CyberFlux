<template>
  <div>
    <n-data-table style="margin-top: 12px;"
      :columns="tableColumns"
      :data="tableData"
      :pagination="pagination"
    />
  </div>
</template>
<script setup lang="ts">
import type { DataTableColumns } from 'naive-ui';
import { NDataTable } from 'naive-ui';
import { Subscription } from '@/store/modules/subscription'
import { SubscriptionStore } from '@/store'

const pagination = reactive({
  pageSize: 20,
  //showSizePicker: true,
})

const tableColumns: DataTableColumns<Subscription> = [
  { title: 'Client ID', key: 'clientId' },
  { title: 'Topic', key: 'topic' },
  { title: 'QoS', key: 'level' },
  { title: 'No Local', key: '', render(item) {
      return 'false'
    }
  },
  { title: 'Retain As Published', key: '', render(item) {
      return 'false'
    }
  },
  { title: 'Retain Handling', key: '', render(item) {
      return 'false'
    }
  },
]
const tableData = ref<Subscription[]>(SubscriptionStore.toArray())


function update() {
  tableData.value = SubscriptionStore.toArray()
}

onBeforeUpdate(() => {
  update();
})

</script>
