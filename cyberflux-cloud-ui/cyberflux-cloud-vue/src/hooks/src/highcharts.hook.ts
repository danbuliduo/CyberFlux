import HighCharts from 'highcharts'
import setMore from 'highcharts/highcharts-more'
import setDarkTheme from 'highcharts/themes/dark-unica'
import setExporting from 'highcharts/modules/exporting'
import setSolidGauge from 'highcharts/modules/solid-gauge'

setMore(HighCharts)
setDarkTheme(HighCharts)
setExporting(HighCharts)
setSolidGauge(HighCharts)

HighCharts.setOptions({
 // lang: undefined,
  credits: {
    enabled: false
  },
  exporting: {
    enabled: true
  },
  accessibility: {
    enabled: false
  },
  legend: {
    layout: 'horizontal',
    align: 'center',
    verticalAlign: 'top'
  },
  xAxis: {
    type: 'datetime',
    dateTimeLabelFormats: {
      day: '%H%M'
    }
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
