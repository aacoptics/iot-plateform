<template>
  <div>
    <div class="container">
      <el-tabs v-model="temperaturePlots" @tab-click="handleClick">
        <el-tab-pane label="Temperature Overview" name="temperatureOverview">
          <div id="temperatureOverview" style="border:1px solid blue;height:600px;width:100%"></div>
        </el-tab-pane>
        <el-tab-pane label="Air Shower" name="airShower">
          <div id="airShower" style="border:1px solid blue;height:600px;width:100%"></div>
        </el-tab-pane>
        <el-tab-pane label="CO2" name="CO2">
          <div id="co2" style="border:1px solid blue;height:600px;width:100%">test</div>
        </el-tab-pane>
        <el-tab-pane label="cAxis" name="cAxis">
          <div id="cAxis" style="border:1px solid blue;height:600px;width:100%"></div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
export default {
  name: "temperaturePlots",
  data() {
    return {
      temperaturePlots: 'temperatureOverview',
      machineName: '',
      airShowerPlot: '',
      co2Plot: '',
      cAxisPlot: '',
      temperatureOverviewPlot: ''
    }
  },
  created() {
    this.machineName = this.$route.query.machineName;
  },
  mounted() {
    // this.drawAirShowerPlot(this.machineName);
    // this.drawCO2Plots(this.machineName);
    // window.addEventListener('resize', () => {
    //   this.airShowerPlot.resize();
    //   this.co2Plot.resize();
    // });

  },
  methods: {
    handleClick() {
      if(this.temperaturePlots == 'airShower') {
        this.$nextTick(() => {
          this.drawAirShowerPlot(this.machineName);
        });
      }
      if(this.temperaturePlots == 'CO2') {
        this.$nextTick(() => {
          this.drawCO2Plots(this.machineName);
        });
      }
      if(this.temperaturePlots == 'cAxis') {
        this.$nextTick(() => {
          this.drawCAxisPlot(this.machineName);
        });
      }
      if(this.temperaturePlots == 'temperatureOverview') {
        this.$nextTick(() => {
          this.drawTemperatureOverviewPlot(this.machineName);
        });
      }
    },
    drawAirShowerPlot(machineName) {
      console.log(machineName);
      var chartDom = document.getElementById('airShower');
      this.airShowerPlot = echarts.init(chartDom);
      var option;

      option = {
        title: {
          text: machineName + ' Air Shower'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: []
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['2022-02-11 16:53:11.000',
      '2022-02-11 16:53:12',
      '2022-02-11 16:53:13',
      '2022-02-11 16:53:14',
      '2022-02-11 16:53:15',
      '2022-02-11 16:53:16',
      '2022-02-11 16:53:17',
      '2022-02-11 16:53:18',
      '2022-02-11 16:53:19'],
          axisLabel: {
            margin: 10,
            interval: 100000,
            showMinLabel: true,
            showMaxLabel: true,
          }
        },
        yAxis: {
          type: 'value',
          min: 21.375,
          max: 21.425
        },
        series: [
          {
            name: 'Air Shower',
            type: 'line',
           // stack: 'Total',
            data: [21.40336,
              21.40334,
              21.40329,
              21.40325,
              21.40335,
              21.40341,
              21.40347,
              21.40357,
              21.40358]
          }
        ]
      };
      this.airShowerPlot.setOption(option);
    },
    drawCO2Plots(machineName) {
      var chartDom = document.getElementById('co2');
      this.co2Plot = echarts.init(chartDom);
      var option;
      option = {
        title: {
          text: machineName + ' CO2'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: []
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['2022-02-11 16:55:47',
      '2022-02-11 16:55:48',
      '2022-02-11 16:55:49',
      '2022-02-11 16:55:50',
      '2022-02-11 16:55:51',
      '2022-02-11 16:55:52',
      '2022-02-11 16:55:53',
      '2022-02-11 16:55:54',
      '2022-02-11 16:55:55'],
          axisLabel: {
            margin: 10,
            interval: 100000,
            showMinLabel: true,
            showMaxLabel: true,
          }
        },
        yAxis: {
          type: 'value',
          min: 20.985,
          max: 21.015
        },
        series: [
          {
            name: 'CO2',
            type: 'line',
            // stack: 'Total',
            data: [20.99767,
              20.99828,
              20.99828,
              20.99859,
              20.99767,
              20.99844,
              20.99859,
              20.99782,
              20.99828]
          }
        ]
      };
      this.co2Plot.setOption(option);
    },
    drawCAxisPlot(machineName) {
      var chartDom = document.getElementById('cAxis');
      this.cAxisPlot = echarts.init(chartDom);
      var option;
      option = {
        title: {
          text: machineName + ' C Axis'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: []
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['2022-02-11 16:55:47',
            '2022-02-11 16:55:48',
            '2022-02-11 16:55:49',
            '2022-02-11 16:55:50',
            '2022-02-11 16:55:51',
            '2022-02-11 16:55:52',
            '2022-02-11 16:55:53',
            '2022-02-11 16:55:54',
            '2022-02-11 16:55:55'],
          axisLabel: {
            margin: 10,
            interval: 100000,
            showMinLabel: true,
            showMaxLabel: true,
          }
        },
        yAxis: {
          type: 'value',
          min: 20.985,
          max: 21.015
        },
        series: [
          {
            name: 'C Axis',
            type: 'line',
            // stack: 'Total',
            data: [20.99767,
              20.99828,
              20.99828,
              20.99859,
              20.99767,
              20.99844,
              20.99859,
              20.99782,
              20.99828]
          }
        ]
      };
      this.cAxisPlot.setOption(option);
    },
    drawTemperatureOverviewPlot(machineName) {
      var chartDom = document.getElementById('temperatureOverview');
      this.temperatureOverviewPlot = echarts.init(chartDom);
      var option;
      option = {
        title: {
          text: machineName + ' Temperature Overview'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: []
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['2022-02-11 16:55:47',
            '2022-02-11 16:55:48',
            '2022-02-11 16:55:49',
            '2022-02-11 16:55:50',
            '2022-02-11 16:55:51',
            '2022-02-11 16:55:52',
            '2022-02-11 16:55:53',
            '2022-02-11 16:55:54',
            '2022-02-11 16:55:55'],
          axisLabel: {
            margin: 10,
            interval: 100000,
            showMinLabel: true,
            showMaxLabel: true,
          }
        },
        yAxis: {
          type: 'value',
          min: 20.985,
          max: 21.015
        },
        series: [
          {
            name: 'C Axis',
            type: 'line',
            // stack: 'Total',
            data: [20.99767,
              20.99828,
              20.99828,
              20.99859,
              20.99767,
              20.99844,
              20.99859,
              20.99782,
              20.99828]
          }
        ]
      };
      this.temperatureOverviewPlot.setOption(option);
    }
  }
}
</script>

<style scoped>

</style>