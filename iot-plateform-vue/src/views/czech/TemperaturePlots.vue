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
      airShowerPlot: '',
      spindlePlot: '',
      bearingPlot: '',
      motorPlot: '',
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
    // setTimeout(() => {
    //   console.log(this.spindleTemperature)
    //   this.drawSpindlePlot(this.machineName);
    //   this.drawAirShowerPlot(this.machineName);
    //   this.drawBearingPlot(this.machineName);
    //   this.drawMotorPlot(this.machineName);
    // },10000)
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
      var minutes = parseInt("370");
      var interTimes = minutes*60*1000;
      var startTime = this.getFormatDate(new Date(Date.parse(new Date()) - parseInt(interTimes)));
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
      var minutes = parseInt("370");
      var interTimes = minutes*60*1000;
      var startTime = this.getFormatDate(new Date(Date.parse(new Date()) - parseInt(interTimes)));
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
      var minutes = parseInt("370");
      var interTimes = minutes*60*1000;
      var startTime = this.getFormatDate(new Date(Date.parse(new Date()) - parseInt(interTimes)));
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
      var minutes = parseInt("370");
      var interTimes = minutes*60*1000;
      var startTime = this.getFormatDate(new Date(Date.parse(new Date()) - parseInt(interTimes)));
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
      this.airShowerPlot = echarts.init(chartDom);
      var option;
      //console.log(this.airTemperature);
      //console.log(this.airDate)

      option = {
        title: {
          text: machineName + ' Air Shower',
          textAlign: 'center',
          x: 'center',
          y: 'top'
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
          data: this.airDate,
          // data: ['2022-02-11 16:53:11.000',
          //   '2022-02-11 16:53:12',
          //   '2022-02-11 16:53:13',
          //   '2022-02-11 16:53:14',
          //   '2022-02-11 16:53:15',
          //   '2022-02-11 16:53:16',
          //   '2022-02-11 16:53:17',
          //   '2022-02-11 16:53:18',
          //   '2022-02-11 16:53:19'],
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
            // data: [21.40336,
            //   21.40334,
            //   21.40329,
            //   21.40325,
            //   21.40335,
            //   21.40341,
            //   21.40347,
            //   21.40357,
            //   21.40358]
          }
        ]
      };
      this.airShowerPlot.setOption(option);
    },
    drawSpindlePlot(machineName) {
      var chartDom = document.getElementById('spindle');
      this.spindlePlot = echarts.init(chartDom);
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
      this.spindlePlot.setOption(option);
    },
    drawBearingPlot(machineName) {
      var chartDom = document.getElementById('bearing');
      this.bearingPlot = echarts.init(chartDom);
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
          // data: ['2022-02-11 16:55:47',
          //   '2022-02-11 16:55:48',
          //   '2022-02-11 16:55:49',
          //   '2022-02-11 16:55:50',
          //   '2022-02-11 16:55:51',
          //   '2022-02-11 16:55:52',
          //   '2022-02-11 16:55:53',
          //   '2022-02-11 16:55:54',
          //   '2022-02-11 16:55:55'],
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
            // stack: 'Total',
            // data: [20.99767,
            //   20.99828,
            //   20.99828,
            //   20.99859,
            //   20.99767,
            //   20.99844,
            //   20.99859,
            //   20.99782,
            //   20.99828]
            data: this.bearingTemperature
          }
        ]
      };
      this.bearingPlot.setOption(option);
    },
    drawMotorPlot(machineName) {
      console.log(this.motorTemperature)
      var chartDom = document.getElementById('motor');
      this.motorPlot = echarts.init(chartDom);
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
          // data: ['2022-02-11 16:55:47',
          //   '2022-02-11 16:55:48',
          //   '2022-02-11 16:55:49',
          //   '2022-02-11 16:55:50',
          //   '2022-02-11 16:55:51',
          //   '2022-02-11 16:55:52',
          //   '2022-02-11 16:55:53',
          //   '2022-02-11 16:55:54',
          //   '2022-02-11 16:55:55'],
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
            // stack: 'Total',
            // data: [20.99767,
            //   20.99828,
            //   20.99828,
            //   20.99859,
            //   20.99767,
            //   20.99844,
            //   20.99859,
            //   20.99782,
            //   20.99828]
            data: this.motorTemperature
          }
        ]
      };
      this.motorPlot.setOption(option);
    },

  }
}
</script>

<style scoped>

</style>
