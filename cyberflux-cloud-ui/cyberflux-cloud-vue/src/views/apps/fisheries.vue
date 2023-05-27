<template>
<div>
  <n-tabs type="line" animated>
    <n-tab-pane name="oasis" tab="系统概览">
        <n-card hoverable>
      <div class="title">[小🐟转🔆] - 智慧渔业检测与防盗安全系统</div>
      <div class="time"> {{ date }} </div>
      <div class="status">
        <n-button strong secondary round type="error">
          数据报警
        </n-button>
        <p style="margin-left: 28px;"> 0 条</p>
      </div>
      <img style="height: 160px;" src="@/assets/image/xyzq.png">
    </n-card>
    <div style="height: 32px; width: 100%;"></div>
    <n-grid x-gap="12" y-gap="24" cols="3 960:5">
      <n-grid-item class="item-container">
        <div class="card">
          <n-icon class="icon" size="40" color="#ffffff">
            <PotentialHydrogen class="icon" />
          </n-icon>
          <div class="title">PH值探针</div>
          <div class="name">PH探针1号</div>
          <div class="value">7.76</div>
        </div>
      </n-grid-item>
      <n-grid-item class="item-container">
        <div class="card">
          <n-icon class="icon" size="40" color="#ffffff">
            <Thermometer class="icon" />
          </n-icon>
          <div class="title">温度探针</div>
          <div class="name">温度探针1号</div>
          <div class="value">18.64°C</div>
        </div>
      </n-grid-item>
      <n-grid-item class="item-container">
        <div class="card">
          <n-icon class="icon" size="40" color="#ffffff">
            <DashboardOutlined class="icon" />
          </n-icon>
          <div class="title">氨氮值探针</div>
          <div class="name">氨氮探针1号</div>
          <div class="value">0.1394 mg/L</div>
        </div>
      </n-grid-item>
      <n-grid-item class="item-container">
        <div class="card">
          <n-icon class="icon" size="40" color="#ffffff">
            <DissolvedOxygen class="icon" />
          </n-icon>
          <div class="title">溶氧值探针</div>
          <div class="name">溶氧探针1号</div>
          <div class="value">6.011 mg/L</div>
        </div>
      </n-grid-item>
      <n-grid-item class="item-container">
        <div class="card">
          <n-icon class="icon" size="40" color="#ffffff">
            <Turbidity class="icon" />
          </n-icon>
          <div class="title">浊度探针</div>
          <div class="name">浊度探针1号</div>
          <div class="value">412度</div>
        </div>
      </n-grid-item>
      <n-grid-item class="item-container">
        <div class="card">
          <n-icon class="icon" size="40" color="#ffffff">
            <PotentialHydrogen class="icon" />
          </n-icon>
          <div class="title">PH值探针</div>
          <div class="name">PH探针2号</div>
          <div class="value">7.12</div>
        </div>
      </n-grid-item>
      <n-grid-item class="item-container">
        <div class="card">
          <n-icon class="icon" size="40" color="#ffffff">
            <Thermometer class="icon" />
          </n-icon>
          <div class="title">温度探针</div>
          <div class="name">温度探针2号</div>
          <div class="value">18.94°C</div>
        </div>
      </n-grid-item>
      <n-grid-item class="item-container">
        <div class="card">
          <n-icon class="icon" size="40" color="#ffffff">
            <DashboardOutlined class="icon" />
          </n-icon>
          <div class="title">氨氮值探针</div>
          <div class="name">氨氮探针2号</div>
          <div class="value">0.1839 mg/L</div>
        </div>
      </n-grid-item>
      <n-grid-item class="item-container">
        <div class="card">
          <n-icon class="icon" size="40" color="#ffffff">
            <DissolvedOxygen class="icon" />
          </n-icon>
          <div class="title">溶氧探针</div>
          <div class="name">溶氧探针2号</div>
          <div class="value">5.42 mg/L</div>
        </div>
      </n-grid-item>
      <n-grid-item class="item-container">
        <div class="card">
          <n-icon class="icon" size="40" color="#ffffff">
            <Turbidity class="icon" />
          </n-icon>
          <div class="title">浊度探针</div>
          <div class="name">浊度探针2号</div>
          <div class="value">398度</div>
        </div>
      </n-grid-item>
    </n-grid>
    </n-tab-pane>

    <n-tab-pane name="the beatles" tab="设备注册">
        <n-table :single-line="true">
        <thead class="title">
          <tr>
            <th>注册ID</th>
            <th>设备名称</th>
            <th>设备状态</th>
            <th>IP地址</th>
            <th>心跳间隔</th>
            <th>所在区域</th>
            <th>运行状态</th>
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
          </tr>
        </tbody>
      </n-table>
    </n-tab-pane>
    <n-tab-pane name="jay chou" tab="数据感知">
      <n-grid item-responsive x-gap="12" y-gap="12" cols="1 512:2">
        <n-grid-item class="item-container">
          <high-charts style="min-width: 50%; margin: auto; height:320px;" :options="options_1" :updateArgs="updateArgs" />
        </n-grid-item>
        <n-grid-item class="item-container">
          <high-charts style="min-width: 50%;  margin: auto; height:320px;" :options="options_2" :updateArgs="updateArgs" />
        </n-grid-item>
        <n-grid-item class="item-container">
          <high-charts style="min-width: 50%;  margin: auto; height:320px;" :options="options_3" :updateArgs="updateArgs" />
        </n-grid-item>
        <n-grid-item class="item-container">
          <high-charts style="min-width: 50%;  margin: auto; height:320px;" :options="options_4" :updateArgs="updateArgs" />
        </n-grid-item>
      </n-grid>
    </n-tab-pane>
  </n-tabs>


</div>
</template>

<script lang="ts">
import { NIcon, NGrid, NGridItem, NCard, NButton } from 'naive-ui'
import { DashboardOutlined } from '@vicons/antd'
import {
  PotentialHydrogen,
  Thermometer,
  DissolvedOxygen,
  Turbidity
} from '@/components/icons'
import { Chart } from 'highcharts-vue'
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
  name: "System",
  components: {
    HighCharts: Chart,
    NCard,
    NIcon,
    NGrid,
    NGridItem,
    NButton,
    DashboardOutlined,
  },
  data() {
    let timer: any;
    let dataArray: any[] = []
    let timestamp: number = 0
    let o2Data: number[][] = []
    let nhData: number[][] = []
    let turData: number[][] = []
    let tdsData: number[][] = []
    let watData: number[][] = []
    let phData: number[][] = []
    let updateArgs: any[] = [true, true, { duration: 500 }]
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
    return {
      timer,
      timestamp,

      o2Data,
      nhData,
      turData,
      tdsData,
      phData,
      watData,
      updateArgs,
      date: new Date().toLocaleString(),
      tableItems,
      options_1: {
        chart: {
          type: 'areaspline'
        },
        title: {
          text: '溶氧量与氨氮值采集'
        },
        yAxis: [
          {
            title: {
              text: '(O)mg/L'
            },
          },
          {
            title: {
              text: '(N)mg/L'
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
            name: "溶氧量",
            data: [],
          },
          {
            name: "氨氮值",
            data: [],

          }
        ]
      },

      options_2: {
        chart: {
          type: 'spline'
        },
        title: {
          text: '浊度与固体溶解量采集'
        },
        yAxis: [
          {
            title: {
              text: 'NTU'
            }
          },
          {
            title: {
              text: 'ppm'
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
            name: "TUR探针",
            tooltip: {
              valueSuffix: ''
            },
            data: []
          },
          {
            name: "TDS探针",
            tooltip: {
              valueSuffix: ''
            },
            data: []
          },
        ]
      },

      options_3: {
        chart: {
          type: 'spline'
        },
        title: {
          text: 'PH值采集'
        },
        yAxis: [
          {
            title: {
              text: '(h+)'
            },
            max: 9.00,
            min: 4.00
          }
        ],
        tooltip: {
          headerFormat: '<b>{series.name}</b><br>',
          pointFormat: '{point.x:%e. %b}: {point.y:.2f}'
        },
        series: [
          {
            name: "PH探针(E-210)",
            tooltip: {
              valueSuffix: ''
            },
            data: []
          }
        ]
      },
      options_4: {
        chart: {
          type: 'spline'
        },
        title: {
          text: '水温采集'
        },
        yAxis: [
          {
            title: {
              text: '°C'
            },
            max: 40.00,
            min: 0.00
          }
        ],
        tooltip: {
          headerFormat: '<b>{series.name}</b><br>',
          pointFormat: '{point.x:%e. %b}: {point.y:.2f}'
        },
        series: [
          {
            name: "DS18B20",
            tooltip: {
              valueSuffix: ''
            },
            data: []
          }
        ]
      }
    }
  },

  methods: {
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
        client.subscribe('/xyzq/collect')
      })
      client.on('error', (error: any) => {
        console.log('Connection failed', error)
      })
      client.on('message', (topic: string, message: string) => {
        let data = JSON.parse(message)
        let timestamp = new Date().getTime()
        if (this.turData.length >= 10) {
          this.turData.shift()
          this.tdsData.shift()
          this.phData.shift()
          this.watData.shift()
        }
        this.turData.push([timestamp, data.TUR])
        this.tdsData.push([timestamp, data.TDS])
        this.phData.push([timestamp, data.PH])
        this.watData.push([timestamp, data.WAT])
        console.log(`Received message ${message} from topic ${topic}`)
      })
    }
  },

  mounted() {
    let that = this
    this.createConnection()
    this.timer = setInterval(function () {
      that.date = new Date().toLocaleString()
      that.timestamp = new Date().getTime()
      if (that.o2Data.length >= 10) {
        that.o2Data.shift()
        that.nhData.shift()
      }
      that.o2Data.push([that.timestamp, Math.random() * 0.2 + 4.8])
      that.nhData.push([that.timestamp, Math.random() * 0.1 + 0.1])
    }, 1000)
  },

  watch: {
    o2Data: {
      handler(value) {
        this.options_1.series[0].data = value
      },
      deep: true
    },
    nhData: {
      handler(value) {
        this.options_1.series[1].data = value
      },
      deep: true
    },
    turData: {
      handler(value) {
        this.options_2.series[0].data = value
      },
      deep: true
    },
    tdsData: {
      handler(value) {
        this.options_2.series[1].data = value
      },
      deep: true
    },
    phData: {
      handler(value) {
        this.options_3.series[0].data = value
      },
      deep: true
    },
    watData: {
      handler(value) {
        this.options_4.series[0].data = value
      },
      deep: true
    }
  },
  // 销毁时清除计时器
  beforeDestroy: function () {
    if (this.timer) {
      clearInterval(this.timer)
    }
  }

})
</script>




<style lang="scss" scoped>
.title {
  th {
    font-weight: 800;
  }
}
.card {
  width: 180px;
  height: 200px;
  border-radius: 16px;
  background: linear-gradient(45deg, #56ccf2, #2f80ed);

  .icon {
    position: absolute;
    margin-top: 8px;
    margin-left: 0px;
  }

  .title {
    position: absolute;
    margin-top: 24px;
    margin-left: 72px;
    font-weight: 800;
  }

  .name {
    position: absolute;
    font-weight: 800;
    font-size: 120%;
    margin-top: 80px;
    margin-left: 48px;
  }

  .value {
    position: absolute;
    margin-top: 150px;
    margin-left: 24px;
    font-weight: 800;
    font-size: 160%;
  }
}

.n-card {
  max-width: 94%;
  margin: auto;

  .title {
    position: absolute;
    margin-left: 360px;
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
</style>
