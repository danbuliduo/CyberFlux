<template>
  <div>
    <n-table :single-line="true">
      <thead class="title">
            <tr>
              <th>设备ID</th>
              <th>设备名称</th>
              <th>设备状态</th>
              <th>IP地址</th>
              <th>心跳间隔</th>
              <th>所在区域</th>
              <th>运行状态</th>
              <th>设备断连</th>
              <th>设备详情</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(rows, index) in tableItems">
              <td v-for="item in rows">
                {{ item }}
              </td>
              <td>
                <n-button strong secondary type="success">
                  良好
                </n-button>
              </td>
              <td>
                <n-button strong secondary type="error">
                  删除
                </n-button>
              </td>
              <td>
                  <n-button  type="info" @click="showModalRef = true">
                    查看
                  </n-button>
                </td>
            </tr>
      </tbody>
    </n-table>
    <n-modal
      v-model:show="showModalRef"
      :mask-closable="false"
      preset="dialog"
      title="物联网设备"
      positive-text="确认"
      negative-text="算了"
      @positive-click="onPositiveClick"
      @negative-click="onNegativeClick"
    >
    ID: xyzq-mqtt-001<br>
    IP: 10.218.17.167
    设备名: PH探针1号<br>
    设备组: 默认分组<br>
    设备类型: ESP32<br>
    注册时间: 2023-06-01 18:54:36<br>
    状态: 正常运行
    备注: 湖北民族大学化学实验室PH混合电机E20001<br>
    </n-modal>
  </div>
</template>


<script lang="ts" setup>

let tableItems = [
  ['xyzq-mqtt-001', 'PH探针1号', '已连接', '10.218.17.167:18000', '160s', '东南一区',],
  ['xyzq-mqtt-002', '温度探针1号', '已连接', '10.218.17.167:18001', '160s', '东南二区',],
  ['xyzq-mqtt-003', '氨氮探针1号', '已连接', '10.218.17.167:18002', '160s', '东南三区',],
  ['xyzq-mqtt-004', '溶氧探针1号', '已连接', '10.218.17.167:18003', '160s', '中心一区',],
  ['xyzq-mqtt-005', '浊度探针1号', '已连接', '10.218.17.167:18004', '160s', '中心二区',],
  ['xyzq-mqtt-006', 'PH探针2号', '已连接', '10.218.17.167:18005', '160s', '东南一区',],
  ['xyzq-mqtt-007', '温度探针2号', '已连接', '10.218.17.167:18006', '160s', '东南二区',],
  ['xyzq-mqtt-008', '氨氮探针2号', '已连接', '10.218.17.167:18007', '160s', '东南三区',],
  ['xyzq-mqtt-009', '溶氧探针2号', '已连接', '10.218.17.167:18008', '160s', '中心一区',],
  ['xyzq-mqtt-010', '浊度探针2号', '已连接', '10.218.17.167:18009', '160s', '中心二区',],
  ['xyzq-mqtt-011', '溶氧机-01', '已连接', '10.218.17.167:18010', '240s', '东南一区',],
  ['xyzq-mqtt-012', '溶氧机-02', '已连接', '10.218.17.167:18011', '240s', '东南二区',],
  ['xyzq-mqtt-013', '溶氧机-03', '已连接', '10.218.17.167:18012', '240s', '东南三区',],
  ['xyzq-mqtt-014', '溶氧机-04', '已连接', '10.218.17.167:18013', '240s', '中心一区',],
  ['xyzq-mqtt-015', '溶氧机-05', '已连接', '10.218.17.167:18014', '240s', '中心二区',],
]

const showModalRef = ref(false)

function  onNegativeClick() {
  showModalRef.value = false
}

function  onPositiveClick() {
  showModalRef.value = false
}
</script>
