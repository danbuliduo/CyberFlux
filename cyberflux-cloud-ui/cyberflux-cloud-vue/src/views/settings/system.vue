<template>
  <div>
    <n-grid x-gap="12" y-gap="24" cols="3 960:5">
      <n-grid-item class="item-container">
        <div class="card">
          <div class="title">CO2浓度</div>
          <div class="value">{{ co2Value }}ppm</div>
        </div>
      </n-grid-item>
      <n-grid-item class="item-container">
        <div class="card">
          <div class="title">光照强度</div>
          <div class="value">{{ luxValue }}lux</div>
        </div>
      </n-grid-item>
      <n-grid-item class="item-container">
        <div class="card">
          <div class="title">加热片温度</div>
          <div class="value">{{ hptValue }}°C</div>
        </div>
      </n-grid-item>
      <n-grid-item class="item-container">
        <div class="card">
          <div class="title">空气温度</div>
          <div class="value">{{ temValue }}°C</div>
        </div>
      </n-grid-item>
      <n-grid-item class="item-container">
        <div class="card">
          <div class="title">空气湿度</div>
          <div class="value">{{ humValue }}%</div>
        </div>
      </n-grid-item>
    </n-grid>
    <div style="height:32px; width: 100%;"></div>
    <n-grid item-responsive x-gap="12" y-gap="12" cols="1 512:2">
      <n-grid-item class="item-container">
        <high-charts style="min-width: 50%; margin: auto; height:320px;" :options="options_1" :updateArgs="updateArgs" />
      </n-grid-item>
      <n-grid-item class="item-container">
        <high-charts style="min-width: 50%;  margin: auto; height:320px;" :options="options_2" :updateArgs="updateArgs" />
      </n-grid-item>
    </n-grid>
    <div style="height:32px; width: 100%;"></div>
    <n-space>
      <n-button type="success">温度控制器</n-button>
      <n-button @click="publish(true)">
        升温[+]
      </n-button>
      <n-button @click="publish(false)">
        降温[-]
      </n-button>
      <n-button>{{ sw0Value }}</n-button>
    </n-space>

    <!--n-switch v-model:value="active" @update-value="publish"/-->
  </div>
</template>

<script lang="ts">
import { Chart } from 'highcharts-vue'

import { NCard, NGrid, NGridItem, NGi, NSwitch, NTabs, NTabPane } from 'naive-ui'
import mqtt from 'mqtt'

let client: mqtt.MqttClient

const connection = {
  host: '43.136.133.43',
  port: 8083,
  endpoint: '/mqtt',
  clean: true,
  connectTimeout: 10000,
  reconnectPeriod: 2000,
  clientId: 'mqttjs_3be2c321',
  username: 'emqx_test',
  password: 'emqx_test',
}

export default defineComponent({
  name: "Overview",
  components: {
    HighCharts: Chart,
    NCard,
    NGrid,
    NGridItem,
    NGi,
    NSwitch,
    NTabs,
    NTabPane,
  },
  data() {
    let timestamp: number = 0
    let co2Data: number[][] = []
    let luxData: number[][] = []
    let temData: number[][] = []
    let humData: number[][] = []
    let hptData: number[][] = []
    let updateArgs: any[] = [true, true, { duration: 500 }]
    let co2Value: any = '---'
    let luxValue: any = '---'
    let temValue: any = '---'
    let humValue: any = '---'
    let hptValue: any = '---'
    let sw0Value: any = '---'
    return {
      active: ref(false),
      timestamp,
      co2Value,
      luxValue,
      temValue,
      humValue,
      hptValue,
      sw0Value,
      co2Data,
      luxData,
      temData,
      humData,
      hptData,
      updateArgs,
      options_1: {
        chart: {
          type: 'areaspline'
        },
        title: {
          text: 'CO2浓度与光照强度采集'
        },
        yAxis: [
          {
            title: {
              text: 'PPM'
            },
          },
          {
            title: {
              text: 'LUX'
            },
            opposite: true
          }
        ],
        tooltip: {
          headerFormat: '<b>{series.name}</b><br>',
          pointFormat: '{point.x:%e. %b}: {point.y:.2f}'
        },
        series: [
          {
            name: "CO2浓度",
            data: [],
          },
          {
            name: "光照强度",
            data: [],

          }
        ]
      },

      options_2: {
        chart: {
          type: 'spline'
        },
        title: {
          text: '温湿度采集'
        },
        yAxis: [
          {
            title: {
              text: '°C'
            }
          },
          {
            title: {
              text: 'RH'
            },
            opposite: true
          },
        ],
        tooltip: {
          headerFormat: '<b>{series.name}</b><br>',
          pointFormat: '{point.x:%e. %b}: {point.y:.2f}'
        },
        series: [
          {
            name: "加热片温度",
            tooltip: {
              valueSuffix: '°C'
            },
            data: []
          },
          {
            name: "空气温度",
            tooltip: {
              valueSuffix: '°C'
            },
            data: []
          },
          {
            name: "空气湿度",
            tooltip: {
              valueSuffix: 'RH'
            },
            data: []
          },
        ]
      },
    }
  },

  methods: {

    publish(value: boolean) {
      value ? client.publish("/test", "a") : client.publish("/test", "b")
    },

    createConnection() {
      const { host, port, endpoint, ...options } = connection
      const connectUrl = `ws://${host}:${port}${endpoint}`

      try {
        client = mqtt.connect(connectUrl, options)
        console.info("Connect.....")
      } catch (error) {
        console.error('Connection Filed', error)
      }

      client.on('connect', () => {
        console.info('Connection succeeded!')
        client.subscribe('/test/esp8266/dh1')
      })
      client.on('error', (error: any) => {
        console.log('Connection failed', error)
      })

      client.on('message', (topic: string, message: string) => {
        let data = JSON.parse(message)
        let time = data.ts * 1000
        if (this.temData.length >= 10) {
          this.co2Data.shift()
          this.luxData.shift()
          this.hptData.shift()
          this.humData.shift()
          this.temData.shift()
        }

        this.co2Value = data.co2  // Co2
        this.luxValue = data.lux  // Lux
        this.hptValue = data.hpt  // Htp
        this.humValue = data.hum  // Hum
        this.temValue = data.tem  // Tem
        this.sw0Value = data.sw0

        this.co2Data.push([time, data.co2])
        this.luxData.push([time, data.lux])
        this.hptData.push([time, data.hpt])
        this.humData.push([time, data.hum])
        this.temData.push([time, data.tem])
        console.log(`Received message ${message} from topic ${topic}`)
      })
    },

    setTimer() {
      setInterval(() => {
        this.timestamp = new Date().getTime()
        if (this.co2Data.length >= 10) {
          this.co2Data.shift()
          this.luxData.shift()
          this.hptData.shift()
          this.humData.shift()
          this.temData.shift()
        }
        this.co2Value = Math.floor(Math.random() * 10)
        this.luxValue = Math.floor(Math.random() * 10)
        this.hptValue = Math.floor(Math.random() * 10)
        this.humValue = Math.floor(Math.random() * 10)
        this.temValue = Math.floor(Math.random() * 10)
        this.co2Data.push([this.timestamp, this.co2Value])
        this.luxData.push([this.timestamp, this.luxValue])
        this.hptData.push([this.timestamp, this.hptValue])
        this.humData.push([this.timestamp, this.humValue])
        this.temData.push([this.timestamp, this.temValue])
      }, 1000)
    }
  },

  mounted() {
    //this.setTimer()
    this.createConnection()
  },

  watch: {
    co2Data: {
      handler(value) {
        this.options_1.series[0].data = value
      },
      deep: true
    },
    luxData: {
      handler(value) {
        this.options_1.series[1].data = value
      },
      deep: true
    },
    temData: {
      handler(value) {
        this.options_2.series[1].data = value
      },
      deep: true
    },
    humData: {
      handler(value) {
        this.options_2.series[2].data = value
      },
      deep: true
    },
    hptData: {
      handler(value) {
        this.options_2.series[0].data = value
      },
      deep: true
    }
  },
})
</script>
<style lang="scss" scoped>
.card {
  position: relative;
  width: 240px;
  height: 160px;
  border-radius: 8px;
  background: linear-gradient(45deg, #20e3b2, #0cdacf);

  .title {
    position: absolute;
    top: 20%;
    left: 50%;
    transform: translate(-50%, -50%);
    font-weight: 800;
    font-size: 120%;
  }

  .value {
    position: absolute;
    top: 60%;
    left: 50%;
    transform: translate(-50%, -50%);
    font-weight: 800;
    font-size: 160%;
  }
}
</style>
