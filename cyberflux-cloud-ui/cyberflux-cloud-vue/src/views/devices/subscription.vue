<template>
  <div>
    <n-table :single-line="true">
      <thead class="title">
        <tr>
          <th>设备ID</th>
          <th>Topic</th>
          <th>QoS</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(rows, index) in tableItems">
          <td v-for="item in rows">
            {{ item }}
          </td>
        </tr>
      </tbody>
    </n-table>
  </div>
</template>


<script lang="ts" setup>

let tableItems = [
  ['xyzq-mqtt-001', '/xyzq-mqtt-001/controller', '0'],
  ['xyzq-mqtt-002', '/xyzq-mqtt-002/controller', '0'],
  ['xyzq-mqtt-003', '/xyzq-mqtt-003/controller', '0'],
  ['xyzq-mqtt-004', '/xyzq-mqtt-004/controller', '0'],
  ['xyzq-mqtt-005', '/xyzq-mqtt-005/controller', '0'],
  ['xyzq-mqtt-006', '/xyzq-mqtt-006/controller', '0'],
  ['xyzq-mqtt-007', '/xyzq-mqtt-007/controller', '0'],
  ['xyzq-mqtt-008', '/xyzq-mqtt-008/controller', '0'],
  ['xyzq-mqtt-009', '/xyzq-mqtt-009/controller', '0'],
  ['xyzq-mqtt-010', '/xyzq-mqtt-0010/controller', '0'],
  ['xyzq-mqtt-011', '/xyzq-mqtt-0011/publish', '2'],
  ['xyzq-mqtt-012', '/xyzq-mqtt-0012/publish', '2'],
  ['xyzq-mqtt-013', '/xyzq-mqtt-0013/publish', '2'],
  ['xyzq-mqtt-014', '/xyzq-mqtt-0015/publish', '2'],
  ['xyzq-mqtt-011', '/xyzq-mqtt-0011/controller', '2'],
  ['xyzq-mqtt-012', '/xyzq-mqtt-0012/controller', '2'],
  ['xyzq-mqtt-013', '/xyzq-mqtt-0013/controller', '2'],
  ['xyzq-mqtt-014', '/xyzq-mqtt-0015/controller', '2'],
]

const showModalRef = ref(false)

function onNegativeClick() {

  showModalRef.value = false
}

function onPositiveClick() {

  showModalRef.value = false
}
</script>
