<template>
  <div>
    <n-space justify="center">
      <high-charts style="width: 200%; height: 320px;" :options="chartopt"/>
    </n-space>
  </div>
</template>

<script lang="ts">
import { NSpace } from "naive-ui";
import { Chart } from 'highcharts-vue'
export default defineComponent({
  name: "Subscription",
  components: {
    HighCharts: Chart,
    NSpace,
  },
  data() {
    let chartopt = {
      chart: {
        type: 'column'
      },
      title: {
        text: '雷击次数统计'
      },
      xAxis: {
        categories: ['博学片区', '博爱片区', '立人片区', '达人片区']
      },
      yAxis: {
        min: 0,
        title: {
          text: '雷击次数'
        },
        stackLabels: {  // 堆叠数据标签
          enabled: true,
          style: {
            fontWeight: 'bold'
          }
        }
      },
      tooltip: {

      },
      plotOptions: {
        column: {
          stacking: 'normal',
          dataLabels: {
            enabled: true,
           // color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
            style: {
              // 如果不需要数据标签阴影，可以将 textOutline 设置为 'none'
              textOutline: '1px 1px black'
            }
          }
        }
      },
      series: [{
        name: '雷击次数',
        data: [1, 0, 3, 2]
      }]
    }

    return {
      chartopt,
    }
  },
  methods: {

  },
  mounted() {

  },
  // 销毁时清除计时器
  beforeDestroy: function () {

  }
})
</script>

<style lang="scss" scoped>
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
