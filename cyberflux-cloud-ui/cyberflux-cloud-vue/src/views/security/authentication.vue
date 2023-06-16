<template>
  <div>
      <n-button type="success" @click="showModalRef = true">
        +注册设备
      </n-button>
<div style="height: 16px; width: 12px;"></div>
    <n-table :single-line="true">
      <thead class="title">
        <tr>
          <th>设备ID</th>
          <th>设备名称</th>
          <th>设备类型</th>
          <th>设备组</th>
          <th>设备注册时间</th>
          <th>设备权限</th>
          <th>注销设备</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(rows, index) in tableItems">
          <td v-for="item in rows">
            {{ item }}
          </td>
          <td>
            <n-button strong secondary type="success">
              允许访问
            </n-button>
          </td>
          <td>
            <n-button strong secondary type="error" @click="handleConfirm">
              删除
            </n-button>
          </td>
        </tr>
      </tbody>
    </n-table>
     <n-modal
        v-model:show="showModalRef"
        :mask-closable="false"
        preset="dialog"
        title="物联网设备注册"
        positive-text="确认"
        @positive-click="onPositiveClick"
        @negative-click="onNegativeClick"
      >
      <n-card >
          <n-form>
            <n-form-item-row label="设备名">
              <n-input />
            </n-form-item-row>
            <n-form-item-row label="设备组">
              <n-input />
            </n-form-item-row>
            <n-form-item-row label="设备类型">
              <n-input />
            </n-form-item-row>
            <n-form-item-row label="备注说明">
                <n-input
        type="textarea"
      />
              </n-form-item-row>
          </n-form>
          <n-button type="primary" block secondary strong>
            注册
          </n-button>


    </n-card>
      </n-modal>
  </div>
</template>


<script lang="ts" setup>

let tableItems = [
  ['xyzq-mqtt-002', '温度探针1号', 'ESP8266', '默认组', '2023-06-01'],
  ['xyzq-mqtt-003', '氨氮探针1号', 'ESP8266', '默认组', '2023-06-01'],
  ['xyzq-mqtt-004', '溶氧探针1号', 'ESP8266', '默认组', '2023-06-01'],
  ['xyzq-mqtt-005', '浊度探针1号', 'ESP8266', '默认组', '2023-06-01'],
  ['xyzq-mqtt-006', 'PH探针2号', 'ESP32', '重案八组', '2023-04-11'],
  ['xyzq-mqtt-007', '温度探针2号', 'ESP8266', '重案八组', '2023-04-11'],
  ['xyzq-mqtt-008', '氨氮探针2号', 'ESP8266', '重案八组', '2023-04-11'],
  ['xyzq-mqtt-009', '溶氧探针2号', 'ESP8266', '重案八组', '2023-04-11'],
  ['xyzq-mqtt-010', '浊度探针2号', 'ESP8266', '重案八组', '2023-04-11'],
  ['xyzq-mqtt-011', '溶氧机-01', 'NB-IoT', '扩展A组', '2023-01-01'],
  ['xyzq-mqtt-012', '溶氧机-02', 'NB-IoT', '扩展A组', '2023-01-01'],
  ['xyzq-mqtt-013', '溶氧机-03', 'NB-IoT', '扩展A组', '2023-01-01'],
  ['xyzq-mqtt-014', '溶氧机-04', 'NB-IoT', '扩展A组', '2023-01-01'],
  ['xyzq-mqtt-015', '溶氧机-05', 'NB-IoT', '扩展A组', '2023-01-01'],
  ['new-mqtt-016', '无敌的物联网虚拟设备', 'LoRa', '默认组', '2023-06-05'],
]

function handleConfirm() {
  window.$dialog.warning({
    title: '警告',
    content: '确定要注销设备: xyzq-mqtt-001?',
    positiveText: '确定',
    negativeText: '取消',
  })
}

const showModalRef = ref(false)

function onNegativeClick() {
  showModalRef.value = false
}

function onPositiveClick() {
  showModalRef.value = false
}

</script>
