<template>
  <div>
    <n-tabs type="line" animated>
    <n-tab-pane name="main" tab="监控系统">
      <n-card hoverable>
        <div class="title">避雷器在线监测系统</div>
        <div class="time"> {{ date }} </div>

        <div class="card">
          <div class="titles">雷击次数</div>
          <div class="value">{{ tableItems.length }} </div>
        </div>
      </n-card>
      <div style="height: 24px; width: 100%;"></div>
      <n-table :single-line="true">
        <thead class="title">
          <tr>
            <th>注册ID</th>
            <th>设备名称</th>
            <th>所在区域</th>
            <th>雷击时间</th>
            <th>修复状态</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(rows, index) in $tableItems">
            <td v-for="item in rows">
              {{ item }}
            </td>
            <td>
              <n-button strong secondary type="success">
                已修复
              </n-button>
            </td>
          </tr>
        </tbody>
      </n-table>
    </n-tab-pane>

      <n-tab-pane name="the beatles" tab="设备连接">
        <n-table :single-line="true">
        <thead class="title">
          <tr>
            <th>注册ID</th>
            <th>设备名称</th>
            <th>设备状态</th>
            <th>IP地址</th>
            <th>心跳间隔</th>
            <th>所在区域</th>
            <th>健康监控</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(rows, index) in tableItems">
            <td v-for="item in rows">
              {{ item }}
            </td>
            <td>
              <n-button strong secondary :type="hetype(healthItems[index])">
                {{ heshow(healthItems[index]) }}
              </n-button>
            </td>
          </tr>
        </tbody>
      </n-table>
      </n-tab-pane>


      <n-tab-pane name="datadfdf" tab="数据感知">
       <Subscription/>
      </n-tab-pane>


    <n-tab-pane name="oasis" tab="卫星图">
    <label>
      <input type="checkbox" v-model="mapSetting.enableScrollWheelZoom" />
      鼠标缩放
    </label>
    <label>
      <input type="checkbox" v-model="mapSetting.enableDragging" />
      拖拽
    </label>
    <label>
      <input type="checkbox" v-model="mapSetting.enableInertialDragging" />
      惯性拖拽
    </label>
    <label>
      <input type="checkbox" v-model="mapSetting.enablePinchToZoom" />
      双指缩放地图
    </label>
    <label>
      <input type="checkbox" v-model="mapSetting.enableKeyboard" />
      键盘操作
    </label>
    <label>
      <input type="checkbox" v-model="mapSetting.enableDoubleClickZoom" />
      双击缩放，左键双击放大、右键双击缩小
    </label>
    <label>
      <input type="checkbox" v-model="mapSetting.enableContinuousZoom" />
      双击平滑缩放效果
    </label>
    <label>
      <input type="checkbox" v-model="mapSetting.enableTraffic" />
      显示交通路况
    </label>
    <label>
      <input type="checkbox" v-model="show" />
      显示信息
    </label>
    地图类型：
    <select class="mySelect" name="" id="" v-model="type">
      <option value="BMAP_NORMAL_MAP">常规地图</option>
      <option value="BMAP_EARTH_MAP">地球模式</option>
      <option value="BMAP_SATELLITE_MAP">卫星图</option>
    </select>
    <br />
    <b-map ak="4pajVFTMP9iQYgHdyF2Wj5YPZ7CZO43o"
      :plugins="['TrackAnimation']"
      :heading="64.5"
      :tilt="73"
      :center="{ lng: 109.509443, lat: 30.301281 }"
      :zoom="16"
      :minZoom="4"
      :mapType="type"
      :enableDragging="mapSetting.enableDragging"
      :enableInertialDragging="mapSetting.enableInertialDragging"
      :enableScrollWheelZoom="mapSetting.enableScrollWheelZoom"
      :enableContinuousZoom="mapSetting.enableContinuousZoom"
      :enableDoubleClickZoom="mapSetting.enableDoubleClickZoom"
      :enableKeyboard="mapSetting.enableKeyboard"
      :enablePinchToZoom="mapSetting.enablePinchToZoom"
      :enableTraffic="mapSetting.enableTraffic">
      <b-circle stroke-style="dashed" :center="{ lng: 109.509443, lat: 30.301281 }" :radius="800"/>
      <!--BMarker icon="red1" :position="{ lat: 30.301281, lng: 109.509443 }" />
      <BInfoWindow-- v-model="show" :position="{ lat: 30.301281, lng: 109.509443 }" title="设备ID:STM32-A001">
        雷击次数统计: 1 <br />
        雷击时间: 2023-05-02
      </BInfoWindow-->
      <b-scale />
      <b-city-list />
      <b-navigation3d />
    </b-map>
    </n-tab-pane>


  </n-tabs>
  </div>
</template>

<script setup lang="ts">

import {
  MapType,
  MapProps,
  BCircle,
  BCityList,
  BNavigation3d,
  BMap,
  BScale,
  BMarker,
  BInfoWindow
} from 'vue3-baidu-map-gl'

import {
  NTabs,
  NTabPane,
  NTable,
} from 'naive-ui'

import Subscription from '@/views/devices/subscription.vue'

const show = ref<boolean>(false)

const type = ref<MapType>('BMAP_EARTH_MAP')

const mapSetting = ref<MapProps>({
  enableDragging: true,
  enableInertialDragging: true,
  enableScrollWheelZoom: true,
  enableContinuousZoom: true,
  enableResizeOnCenter: true,
  enableDoubleClickZoom: false,
  enableKeyboard: true,
  enablePinchToZoom: true,
  enableAutoResize: true,
  enableTraffic: false
})


let $tableItems = [
  ['client-mqtt-A001', 'STM32', '立人片区', '2022-07-07'],
  ['client-mqtt-A001', 'STM32', '立人片区', '2023-03-06'],
  ['client-mqtt-A004', 'STM32', '立人片区', '2023-03-06'],
  ['client-mqtt-B001', 'STM32', '博学片区', '2023-03-12'],
  ['client-mqtt-C002', 'STM32', '达人片区', '2023-04-04'],
  ['client-mqtt-C003', 'STM32', '达人片区', '2023-05-02'],
]
let tableItems = [
  ['client-mqtt-A001', 'STM32', '已连接', '10.212.13.167:8000', '60s', '立人片区',],
  ['client-mqtt-A002', 'STM32', '已连接', '10.212.13.167:8001', '60s', '立人片区',],
  ['client-mqtt-A003', 'STM32', '已连接', '10.212.13.167:8002', '60s', '立人片区',],
  ['client-mqtt-A004', 'STM32', '已连接', '10.212.13.167:8003', '60s', '立人片区',],
  ['client-mqtt-B001', 'STM32', '已连接', '10.212.13.167:8004', '60s', '博学片区',],
  ['client-mqtt-B002', 'STM32', '已连接', '10.212.13.167:8005', '60s', '博学片区',],
  ['client-mqtt-C001', 'STM32', '已连接', '10.212.13.167:8006', '60s', '达人片区',],
  ['client-mqtt-C002', 'STM32', '已连接', '10.212.13.167:8007', '60s', '达人片区',],
  ['client-mqtt-C003', 'STM32', '已连接', '10.212.13.167:8008', '60s', '达人片区',],
]

let healthItems = [
  1, 0, 1, 1, -1, 1, 0, 1, 1
]

function heshow(value: number): string {
  if (value === 1) {
    return '良 好'
  } else if (value === 0) {
    return '异 常'
  }
  return '损 坏'
}

function hetype(value: number): 'success' | 'warning' | 'error' {
  if (value === 1) {
    return 'success'
  } else if (value === 0) {
    return 'warning'
  }
  return 'error'
}

const timer = ref<NodeJS.Timer>()
var date = ref(new Date().toLocaleString())

onMounted(() => {
  timer.value = setInterval(() => {
    date.value = new Date().toLocaleString()
  }, 1000)
})


onBeforeUnmount(() => {
  clearInterval(timer.value)
})

</script>
<style lang="scss" scoped>
.lable {
  color: #333;
  font-size: 8px;
}
.title {
  th {
    font-weight: 800;
  }
}

.n-card {
  max-width: 94%;
  margin: auto;

  .title {
    position: absolute;
    margin-left: 520px;
    font-size: 200%;
  }

  .time {
    position: absolute;
    margin-left: 480px;
    margin-top: 72px;
    font-size: 200%;
  }

  .status {
    float: right;
  }
}
.card {
  position: relative;
  width: 240px;
  height: 160px;
  border-radius: 8px;
  background: linear-gradient(45deg, #ec008c, #fc6767);

  .titles {
    position: absolute;
    top: 20%;
    left: 50%;
    transform: translate(-50%,-50%);
    font-weight: 800;
    font-size: 150%;
  }

  .value {
    position: absolute;
    top: 60%;
    left: 50%;
    transform: translate(-50%,-50%);
    font-weight: 800;
    font-size: 180%;
  }
}
</style>
