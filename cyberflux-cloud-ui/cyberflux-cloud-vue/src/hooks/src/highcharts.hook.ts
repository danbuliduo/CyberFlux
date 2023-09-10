import HighCharts from 'highcharts'
import setMore from 'highcharts/highcharts-more'
import setDarkTheme from 'highcharts/themes/dark-unica'
import setExporting from 'highcharts/modules/exporting'
import setSolidGauge from 'highcharts/modules/solid-gauge'

setMore(HighCharts)
setDarkTheme(HighCharts)
setExporting(HighCharts)
setSolidGauge(HighCharts)

export const colorOptions = [
  '#2b908f', '#90ee7e', '#f45b5b',
  '#7798BF', '#aaeeee', '#ff0066',
  '#eeaaee', '#55BF3B', '#DF5353',
  '#7798BF', '#aaeeee'
]

export const updateArgs = [
  true, true, { duration: 500 }
]

HighCharts.setOptions({
  colors: colorOptions,
  chart: {
    backgroundColor: 'Transparent',
    style: {
      fontFamily: 'Helvetica'
    }
  },
  credits: {
    enabled: false
  },
  exporting: {
    enabled: false
  },
  accessibility: {
    enabled: false
  },
  legend: {
    layout: 'horizontal', //vertical horizontal
    align: 'center',
    verticalAlign: 'top'
  },
  xAxis: {
    type: 'datetime',
    dateTimeLabelFormats: {
      second: '%H:%M:%S'
    },
  },
  plotOptions: {
    line: {
      dataLabels: {
        enabled: true
      },
      enableMouseTracking: false
    }
  },
})

export function useHighCharts() {
  return HighCharts
}
