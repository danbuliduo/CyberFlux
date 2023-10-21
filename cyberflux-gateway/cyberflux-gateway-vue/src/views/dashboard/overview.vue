<template>
  <main>
    <n-space vertical :size="32">
      <n-grid responsive="self" cols="1 800:2" :x-gap="12" :y-gap="12">
        <n-grid-item >
          <n-card size="small" title="集群节点" style="height: 540px;" :segmented="{ content: true }">
            <hex-mesh v-if="true"
                :class-for-item="classForApplication"
                :items="engineOptions"
                @click="select"
              >
                <template #item="{ item: application }">
                  <div :key="application.name" class="hex__body node">
                    <div class="node-header">
                      <time-ago v-if="application.status !== NodeStatus.UNKNOWN"
                        :date="application.start"
                      />
                      <div v-else>0 second</div>
                    </div>
                    <div>
                      <p class="node-name">
                        {{ application.name?.toUpperCase() }}
                      </p>
                    </div>
                    <p class="node-footer node-version">
                      {{ application.mode }}
                    </p>
                  </div>
                </template>
              </hex-mesh>
          </n-card>
        </n-grid-item>
        <n-grid-item>
          <n-space vertical :size="12">
            <n-card size="small"  title="概览指标" style="height: 220px" :segmented="{ content: true }">
              <n-space justify="space-around" size="large">
                <div class="col-items">
                  <img style="width: 48px;" src="@/assets/image/icon/connections.png" alt=""/>
                  <h3>连接量</h3>
                  <h3>1000000</h3>
                </div>
                <div class="col-items">
                  <img style="width: 48px;"  src="@/assets/image/icon/topics.png" alt=""/>
                  <h3>主题量</h3>
                  <h3>1000000</h3>
                </div>
                <div class="col-items">
                  <img style="width: 48px;"  src="@/assets/image/icon/subscriptions.png" alt=""/>
                  <h3>订阅量</h3>
                  <h3>1000000</h3>
                </div>
              </n-space>
            </n-card>
            <n-card size="small"  title="系统监控" style="height: 308px" :segmented="{ content: true }">
              <n-space justify="space-around">
                <chart style="height: 250px; width: 340px" :options="state.options"/>
              </n-space>
            </n-card>
          </n-space>
        </n-grid-item>
        <n-grid-item >
          <n-card size="small" :title="state.jvmCardTitle" style="height: 180px" :segmented="{ content: true }">
            <template #header-extra>
              <n-icon :component = "LogoJava" :size="26"/>
            </template>
            <n-grid responsive="self" cols="2" :x-gap="0" :y-gap="0">
              <n-grid-item >
                <p>JVM制造商: {{ actuatorOptions?.run?.jvm?.vendor }}</p>
              </n-grid-item>
              <n-grid-item >
                <p>启动时间: {{ parseTime(actuatorOptions?.run?.start) }}</p>
              </n-grid-item>
              <n-grid-item >
                <p>JDK版本: {{ actuatorOptions?.run?.jdk?.version }}</p>
              </n-grid-item>
              <n-grid-item >
                <p>运行线程数: {{ actuatorOptions?.run?.thread?.count }}</p>
              </n-grid-item>
            </n-grid>
          </n-card>
        </n-grid-item >
        <n-grid-item >
          <n-card size="small" :title="state.cpuCardTitle" style="height: 180px" :segmented="{ content: true }">
            <template #header-extra>
              <n-icon :component = "Cpu" :size="28"/>
            </template>
            <n-grid responsive="self" cols="2" :x-gap="12" :y-gap="0">
              <n-grid-item >
                <p>CPU物理核心数: {{ actuatorOptions?.sys?.cpu?.core }}</p>
              </n-grid-item>
              <n-grid-item >
                <p>CPU进程数: {{ actuatorOptions?.sys?.process }}</p>
              </n-grid-item>
              <n-grid-item >
                <p>CPU逻辑核心数: {{ actuatorOptions?.sys?.cpu?.logic }}</p>
              </n-grid-item>
              <n-grid-item >
                <p>CPU线程数: {{ actuatorOptions?.sys?.thread }}</p>
              </n-grid-item>
            </n-grid>

          </n-card>
        </n-grid-item >
      </n-grid>
    </n-space>
  </main>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { Chart } from 'highcharts-vue'
import { NodeStatus } from '@/enums/cluster'
import { HexMesh, TimeAgo } from '@/components/common'
import { Engine } from '@/store/modules/engine'
import { EngineStore } from '@/store'
import { NButton } from 'naive-ui'
import { Cluster, Cpu, LogoJava } from '@/components/icons'
import { parseTime } from "@/utils";
import {  useEngineEventSource, MetricUpdateEvent } from '@/service/sse/engine';
import {  useGatewayEventSource, EngineEventTable } from '@/service/sse/gateway';

const router = useRouter();
const actuatorOptions = ref<any>();
const engineOptions = ref<Engine[]>();

onBeforeMount(() => {
  update()
})

onBeforeUpdate(() => {
  update()
})
onMounted(() => {
  if (window.EventSource) {
    useEngineEventSource().addEventListener(MetricUpdateEvent, (event) => {
      actuatorOptions.value = JSON.parse(event.data)
      state.jvmCardTitle = `JVM: ${actuatorOptions.value?.run?.jvm?.name}`
      state.cpuCardTitle = `CPU: ${actuatorOptions.value?.sys?.cpu?.name}`
      state.options.series[0].data = [
        actuatorOptions.value?.sys?.cpu?.usage * 100,
        actuatorOptions.value?.sys?.mem?.global?.usage * 100,
        actuatorOptions.value?.sys?.disk?.usage * 100,
        actuatorOptions.value?.run?.jvm?.usage * 100,
        actuatorOptions.value?.sys?.mem?.virtual?.usage * 100,
      ]
    })
    EngineEventTable.forEach((value, index) => {
      useGatewayEventSource().addEventListener(value, (event) => {
        const engine = JSON.parse(event.data) as Engine
        EngineStore.state.set(engine.host, engine)
        engineOptions.value = Array.from(EngineStore.state.values())
        let description: string = '';
        let content = '';
        switch (index) {
          case 0: {
            description = `节点 @cyberflux:${engine.host} 加入了集群`;
            content = `id: ${engine.id}\nname: ${engine.name}`;
          } break;
          case 1: {
            description = `节点 @cyberflux:${engine.host} 离开了集群`;
            content = `id: ${engine.id}\nname: ${engine.name}\nmode: ${engine.mode}\nversion: ${engine.version}\n`
          } break;
          case 2: {
            description = `节点 @cyberflux:${engine.host} 断开了连接`;
            content = `id: ${engine.id}\nname: ${engine.name}\nmode: ${engine.mode}\nversion: ${engine.version}\n`
          } break;
          case 3: {
            description = `节点 @cyberflux:${engine.host} 状态已更新`;
            content = `id: ${engine.id}\nname: ${engine.name}\nmode: ${engine.mode}\nversion: ${engine.version}\n`
          } break;
          case 4: {
            description = `节点 @cyberflux:${engine.host} 注册成功`;
            content = `id: ${engine.id}\nname: ${engine.name}\nmode: ${engine.mode}\nversion: ${engine.version}\n`
          }
          break;
        }
        const n = window.$notification.create({
          title: "集群状态更新",
          description: description,
          content: content,
          meta: new Date().toTimeString(),
          duration: 10000,
          action: () => h(NButton, {
            text: true,
            type: 'primary',
            onClick: () => {
              n.destroy()
            }
          }, {
            default: () => '确认'
          }),
        })
      })
   })
  }
});


function classForApplication(application: Engine): string {
  switch (application.status) {
    case NodeStatus.UP: return 'up';
    case NodeStatus.RESTRICTED: return 'restricted';
    case NodeStatus.DOWN: return 'down';
    case NodeStatus.OUT_OF_SERVICE: return 'down';
    case NodeStatus.OFFLINE: return 'down';
    case NodeStatus.UNKNOWN: return 'unknown';
    default: return 'unknown';
  }
}

function select(application: Engine) {
  console.log(application.id)
  if (application.id) {
    router.push({
      path: 'application/:id',
      params: { id: application.id },
    })
  } else {
    router.push({
      path: 'application/:name',
      params: { id: application.name },
    })
  }
}


const state = reactive<any>({
  jvmCardTitle: 'JVM',
  cpuCardTitle: 'CPU',
  options: {
    chart: {
      polar: true,
      type: 'area'
    },
    title: {
      text: undefined,
    },
    pane: {
      size: '70%'
    },
    legend: {
      enabled: false
    },
    xAxis: {
      categories: [  'CPU利用率', '全局内存使用率', '磁盘利用率', 'JVM利用率', '虚拟内存使用率'],
      tickmarkPlacement: 'on',
      lineWidth: 0
    },
    yAxis: {
      gridLineInterpolation: 'polygon',
      lineWidth: 0,
      tickInterval: 20,
      min: 0,
      max: 100,
    },
    tooltip: {
      shared: true,
      pointFormat: '<span style="color:{series.color}">value: <b>{point.y:,.0f}%</b><br/>'
    },
    series: [{
      data: [],
      pointPlacement: 'on'
    }]
  },
})

function update() {
  engineOptions.value = Array.from(EngineStore.state.values())
}



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
  padding: 0px;
}

.node-name {
  width: 100%;
  padding: 0%;
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
  margin-bottom: 0.12em;
}

.node-footer {
  width: 90%;
  margin-top: 0.12em;
}
</style>

<style lang="scss">
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
