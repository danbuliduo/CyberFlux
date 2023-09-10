<template>
  <div>
    <n-space vertical :size="32">
      <n-grid responsive="self" cols="1 800:2" :x-gap="24" :y-gap="12">
        <n-grid-item>
          <n-card size="small" title="Cluster Node" style="height: 540px;" :segmented="{ content: true }">
            <hex-mesh v-if="true"
                :class-for-item="classForApplication"
                :items="applications"
                @click="select"
              >
                <template #item="{ item: application }">
                  <div :key="application.name" class="hex__body node">
                    <div class="node-status-indicator" ></div>
                    <div class="node-header node-time-ago is-muted">
                      <time-ago/>
                    </div>
                    <div class="node-body">
                      <h1 class="node-name">
                        {{ application.name }}
                      </h1>
                    </div>
                    <h2 class="node-footer node-version">
                      META
                    </h2>
                  </div>
                </template>
              </hex-mesh>
          </n-card>
        </n-grid-item>
        <n-grid-item>
          <n-card size="small" ref="cardRef" title="Cluster Info" style="height: 540px" :segmented="{ content: true }">
            <n-space justify="space-around" size="large">
              <div class="col-items">
                <img style="width: 48px;" src="@/assets/image/icon/connections.png"/>
                <h3>连接量</h3>
                <h3>1000000</h3>
              </div>
              <div class="col-items">
                <img style="width: 48px;"  src="@/assets/image/icon/topics.png"/>
                <h3>主题量</h3>
                <h3>1000000</h3>
              </div>
              <div class="col-items">
                <img style="width: 48px;"  src="@/assets/image/icon/subscriptions.png"/>
                <h3>订阅量</h3>
                <h3>1000000</h3>
              </div>
            </n-space>
            <chart style="height: 320px;" :options="state.options" :updateArgs="updateArgs"/>
          </n-card>
        </n-grid-item>
      </n-grid>
    </n-space>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { Chart } from 'highcharts-vue'
import { NCard, NSpace, NGrid, NGridItem } from 'naive-ui'
import { NodeStatus } from '@/enums'
import { updateArgs}  from '@/hooks'
import { HexMesh, TimeAgo } from '@/components/common'

let applications = [
  {
    name: "Node-1",
  },
  {
    name: "Node-2",
  },
  {
    name: "Node-3",
  },
]

const router = useRouter()

function classForApplication(application: any): string | null {
  return 'up';
  if (!application) {
    return null;
  }
  if (application.status === NodeStatus.UP) {
    return 'up';
  }
  if (application.status === NodeStatus.RESTRICTED) {
    return 'restricted';
  }
  if (application.status === NodeStatus.DOWN) {
    return 'down';
  }
  if (application.status === NodeStatus.OUT_OF_SERVICE) {
    return 'down';
  }
  if (application.status === NodeStatus.OFFLINE) {
    return 'down';
  }
  if (application.status === NodeStatus.UNKNOWN) {
    return 'unknown';
  }
  return '';
}

function select(application: any) {
  if (application.instances.length === 1) {
    router.push({
      name: 'instances/details',
      params: { instanceId: application.instances[0].id },
    })
  } else {
    router.push({
      name: 'applications',
      params: { selected: application.name },
    })
  }
}

let timestamp: number = 0


const state = reactive<any>({
  chartWidth: 800,
  options: {
    chart: {
      type: 'spline'
    },
    title: {
      text: '节 点 负 载'
    },
    yAxis: {
      max: 100,
      min: 0,
      tickInterval: 20,
      title: {
        text: undefined
      },
    },
    tooltip: {
      headerFormat: '<b>{series.name}</b><br>',
      pointFormat: 'CPU负载: {point.y:.2f}'
    },
    series: [
      {
        name: "Node-1", data: [],
      },
      {
        name: "Node-2", data: [],
      },
      {
        name: "Node-3", data: [],
      }
    ]
  }
})


function setTimer() {
  setInterval(() => {
    timestamp = new Date().getTime()
    if (state.options.series[0].data.length >= 10) {
      state.options.series[0].data.shift()
      state.options.series[1].data.shift()
      state.options.series[2].data.shift()
    }
    state.options.series[0].data.push([timestamp, 50 + Math.floor(Math.random() * 10)])
    state.options.series[1].data.push([timestamp, 70 + Math.floor(Math.random() * 10)])
    state.options.series[2].data.push([timestamp, 40 + Math.floor(Math.random() * 10)])
  }, 1000)
}


onMounted(() => {
  /*watch(() => state.x, (value: any) => {
    state.options.series[0].data = value
  }, { deep: true })*/
  setTimer()
})

</script>

<style lang="scss" scoped>

.col-items {
  text-align: center;
  div, img {
    margin: auto;
  }
}

.node {
  color: #f5f5f5;
  font-size: 1em;
  font-weight: 400;
  line-height: 1;
  text-align: center;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.node-name {
  width: 100%;
  padding: 2.5%;
  color: #fff;
  font-size: 2em;
  font-weight: 600;
  line-height: 1.125;
}

.node-version {
  color: #f5f5f5;
  font-size: 1.25em;
  line-height: 1.25;
}

.node-header {
  width: 90%;
  margin-bottom: 0.5em;
}

.node-footer {
  width: 90%;
  margin-top: 0.5em;
}
</style>

<style lang="scss">
.up > polygon {
  stroke: #4ade80;
  fill: #4ade80;
}
.unknown > polygon {
  stroke: #808080;
  fill: #808080;
}

.down > polygon,
.offline > polygon {
  stroke: #f87171;
  fill: #f87171;
  stroke-width: 2;
}

.restricted > polygon {
  stroke: #f5e365;
  fill: #f5e365;
}

.hex .hex__body::after {
  display: flex;
  justify-content: center;
  align-content: center;
  font-size: 15em;
  position: absolute;
  z-index: -1;
  width: 100%;
}

.hex .hex__body {
  position: fixed;
  z-index: 10;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.hex.down .hex__body::after {
  content: '!';
  color: #f87171;
}

.hex.unknown .hex__body::after {
  content: '?';
  color: #808080;
}
</style>
