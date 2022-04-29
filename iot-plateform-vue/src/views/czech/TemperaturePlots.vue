<template>
  <div>
    <div class="container">
<!--      <el-tabs v-model="temperaturePlots" @tab-click="handleClick">-->
<!--        <el-tab-pane label="Spindle Temperature" name="spindle">-->
<!--          <div id="spindle" style="border:1px solid blue;height:600px;width:100%"></div>-->
<!--        </el-tab-pane>-->
<!--        <el-tab-pane label="Air Shower Temperature" name="airShower">-->
<!--          <div id="airShower" style="border:1px solid blue;height:600px;width:100%"></div>-->
<!--        </el-tab-pane>-->
<!--        <el-tab-pane label="Bearing Temperature" name="bearing">-->
<!--          <div id="bearing" style="border:1px solid blue;height:600px;width:100%"></div>-->
<!--        </el-tab-pane>-->
<!--        <el-tab-pane label="Motor Temperature" name="motor">-->
<!--          <div id="motor" style="border:1px solid blue;height:600px;width:100%"></div>-->
<!--        </el-tab-pane>-->
<!--      </el-tabs>-->
      <div id="spindle" style="border:1px solid blue;height:600px;width:100%"></div>
      <div id="airShower" style="border:1px solid blue;height:600px;width:100%"></div>
      <div id="bearing" style="border:1px solid blue;height:600px;width:100%"></div>
      <div id="motor" style="border:1px solid blue;height:600px;width:100%"></div>

    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import {
  getSpindleTemperature, getAirTemperature,
  getBearingTemperature,getMotorTemperature
} from "@/api/czech/floorPlan";
export default {
  name: "temperaturePlots",
  props: {
    machineNo: String,
  },
  data() {
    return {
      temperaturePlots: 'spindle',
      machineName: '',
      // airShowerPlot: '',
      // spindlePlot: '',
      // bearingPlot: '',
      // motorPlot: '',
      spindleDate: [],
      spindleTemperature: [],
      airDate: [],
      airTemperature: [],
      bearingDate: [],
      bearingTemperature: [],
      motorDate: [],
      motorTemperature: []
    }
  },
  created() {
    this.machineName = this.machineNo;

  },
  mounted() {
    this.getAirInfo();
    this.getSpindleInfo();
    this.getBearingInfo();
    this.getMotorInfo();
  },

  methods: {
    getFormatDate(date) {
      var seperator1 = "-";
      var seperator2 = ":";
      var month = date.getMonth() + 1;
      var strDate = date.getDate();
      var hour = date.getHours();
      var minutes = date.getMinutes();
      if(month >= 1 && month <= 9) {
        month = "0" + month;
      }
      if(strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
      }
      if(hour >= 0 && hour <= 9) {
        hour = "0" + hour;
      }
      if(minutes >= 0 && minutes <= 9) {
        minutes = "0" + hour;
      }
      var formatDate = date.getFullYear() + seperator1 + month + seperator1 + strDate
      + " " + hour + seperator2 + minutes + seperator2 + date.getSeconds();
      return formatDate
    },
    getSpindleInfo() {
      var endTime = this.getFormatDate(new Date());
      // var minutes = parseInt("370");
      // var interTimes = minutes*60*1000;
      // var startTime = this.getFormatDate(new Date(Date.parse(new Date()) - parseInt(interTimes)));
      var front12hour = new Date(new Date().getTime() - 12 * 60 * 60 * 1000);
      var startTime = this.getFormatDate(front12hour);
      console.log(startTime);
      console.log(endTime);
      getSpindleTemperature(startTime, endTime, this.machineName).then((response) => {
            const responseData = response.data
            if (responseData.code === '000000') {
              responseData.data.forEach(item => {
                this.spindleDate.push(item.time);
                this.spindleTemperature.push(item.temperature);
              })

              this.drawSpindlePlot(this.machineName)
            }
      })

    },
    getAirInfo() {
      var endTime = this.getFormatDate(new Date());
      // var minutes = parseInt("370");
      // var interTimes = minutes*60*1000;
      // var startTime = this.getFormatDate(new Date(Date.parse(new Date()) - parseInt(interTimes)));
      var front12hour = new Date(new Date().getTime() - 12 * 60 * 60 * 1000);
      var startTime = this.getFormatDate(front12hour);
      getAirTemperature(startTime, endTime, this.machineName).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          responseData.data.forEach(item => {
            this.airDate.push(item.time);
            this.airTemperature.push(item.temperature);
          })
          this.drawAirShowerPlot(this.machineName)
        }
      })
    },
    getBearingInfo() {
      var endTime = this.getFormatDate(new Date());
      // var minutes = parseInt("370");
      // var interTimes = minutes*60*1000;
      // var startTime = this.getFormatDate(new Date(Date.parse(new Date()) - parseInt(interTimes)));
      var front12hour = new Date(new Date().getTime() - 12 * 60 * 60 * 1000);
      var startTime = this.getFormatDate(front12hour);
      getBearingTemperature(startTime, endTime, this.machineName).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          responseData.data.forEach(item => {
            this.bearingDate.push(item.time);
            this.bearingTemperature.push(item.temperature);
          })
          this.drawBearingPlot(this.machineName)
        }
      })
    },
    getMotorInfo() {
      var endTime = this.getFormatDate(new Date());
      // var minutes = parseInt("370");
      // var interTimes = minutes*60*1000;
      // var startTime = this.getFormatDate(new Date(Date.parse(new Date()) - parseInt(interTimes)));
      var front12hour = new Date(new Date().getTime() - 12 * 60 * 60 * 1000);
      var startTime = this.getFormatDate(front12hour);
      getMotorTemperature(startTime, endTime, this.machineName).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          responseData.data.forEach(item => {
            this.motorDate.push(item.time);
            this.motorTemperature.push(item.temperature);
          })
          this.drawMotorPlot(this.machineName)
        }
      })
    },
    // handleClick() {
    //   if(this.temperaturePlots == 'airShower') {
    //     this.$nextTick(() => {
    //       this.drawAirShowerPlot(this.machineName);
    //     });
    //   }
    //   if(this.temperaturePlots == 'bearing') {
    //     this.$nextTick(() => {
    //       this.drawBearingPlot(this.machineName);
    //     });
    //   }
    //   if(this.temperaturePlots == 'motor') {
    //     this.$nextTick(() => {
    //       this.drawMotorPlot(this.machineName);
    //     });
    //   }
    //   if(this.temperaturePlots == 'spindle') {
    //     this.$nextTick(() => {
    //       this.drawSpindlePlot(this.machineName);
    //     });
    //   }
    // },
    drawAirShowerPlot(machineName) {
      var chartDom = document.getElementById('airShower');
      var airShowerPlot = echarts.init(chartDom);
      var option;

      option = {
        title: {
          text: machineName + ' Air Shower',
          textAlign: 'center',
          x: 'center',
          y: 'top'
        },
        tooltip: {
          trigger: 'axis',
        },
        legend: {
          data: ['airTemperature']
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
          data: this.airDate,
          axisLabel: {
            margin: 10,
            interval: 100000,
            showMinLabel: true,
            showMaxLabel: true,
          },
        },
        yAxis: {
          type: 'value',
          min: 21.375,
          max: 21.425,
          interval: 0.002
        },
        series: [
          {
            name: 'Air Shower',
            type: 'line',
            data: this.airTemperature
          }
        ]
      };
      airShowerPlot.setOption(option);
    },
    drawSpindlePlot(machineName) {
      var chartDom = document.getElementById('spindle');
      var spindlePlot = echarts.init(chartDom);
      var option;
      option = {
        title: {
          text: machineName + ' spindle',
          textAlign: 'center',
          x: 'center',
          y: 'top'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['Temperature']
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
          data: this.spindleDate,
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
          max: 21.015,
          interval: 0.002
        },
        series: [
          {
            name: 'Spindle',
            type: 'line',
            data: this.spindleTemperature
          }
        ]
      };
      spindlePlot.setOption(option);
    },
    drawBearingPlot(machineName) {
      var chartDom = document.getElementById('bearing');
      var bearingPlot = echarts.init(chartDom);
      var option;
      option = {
        title: {
          text: machineName + ' Bearing',
          textAlign: 'center',
          x: 'center',
          y: 'top'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['Temperature']
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
          data: this.bearingDate,
          axisLabel: {
            margin: 10,
            interval: 100000,
            showMinLabel: true,
            showMaxLabel: true,
          }
        },
        yAxis: {
          type: 'value',
          min: 20.965,
          max: 21.035,
          interval: 0.002
        },
        series: [
          {
            name: 'Bearing',
            type: 'line',
            data: this.bearingTemperature
          }
        ]
      };
      bearingPlot.setOption(option);
    },
    drawMotorPlot(machineName) {
      console.log(this.motorTemperature)
      var chartDom = document.getElementById('motor');
      var motorPlot = echarts.init(chartDom);
      var option;
      option = {
        title: {
          text: machineName + ' Motor',
          textAlign: 'center',
          x: 'center',
          y: 'top'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['Temperature']
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
          data: this.motorDate,
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
          max: 21.035,
          interval: 0.002
        },
        series: [
          {
            name: 'Motor',
            type: 'line',
            data: this.motorTemperature
          }
        ]
      };
      motorPlot.setOption(option);
    },

  }
}
</script>

<style scoped>

</style>
