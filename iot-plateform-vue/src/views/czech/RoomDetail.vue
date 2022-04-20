<template>
  <div>
    <div class="container">
      <el-row>
        <el-col :span="24">
          <p style="text-align: center"></p>
        </el-col>
      </el-row>
      <el-row>
        <el-col v-for="(machineInfo, index) of this.floorMachineInfo" :span="3" :key="index">
          <el-card style="border:1px solid blue;margin:5px;" class="cz_room_card" :body-style="{ padding: '0px', height:'255px'}">
            <p style="text-align: center;font-weight: bold;font-size: 24px;cursor: pointer" @click="onClick(machineInfo.machineNo)">FG{{machineInfo.machineNo}}</p>
            <el-row v-if="machineInfo.status === 'Maintenance'" style="text-align: center;height:30px; font-weight: bold;font-size: 16px;">
              <el-col :span="24">
                <div :style="'background-color:grey;height:30px;line-height:30px'">{{machineInfo.status}}</div>

              </el-col>
            </el-row>
            <el-row v-else style="text-align: center;height:30px; font-weight: bold;font-size: 16px;">
              <el-col :span="8">
                <div :style="'background-color:yellow;height:30px;line-height:30px;cursor:pointer'" @click="onTemperatureClick(machineInfo.machineNo)">
                  {{machineInfo.temperature}}℃
                </div>
              </el-col>
              <el-col :span="12">
                <div  v-if="machineInfo.status === 'Normal'" :style="'background-color:green;height:30px;line-height:30px'">
                  {{machineInfo.status}}
                </div>
                <div  v-else-if="machineInfo.status === 'LoJ'" :style="'background-color:red;height:30px;line-height:30px'">
                  {{machineInfo.status}}
                </div>
                <div  v-else-if="machineInfo.status === 'idle'" :style="'background-color:orange;height:30px;line-height:30px'">
                  {{machineInfo.status}}
                </div>
                <div v-else :style="'background-color:blue;height:30px;line-height:30px'">
                  {{machineInfo.status}}
                </div>
              </el-col>
              <el-col :span="4">
                <div :style="'background-color:yellow;height:30px;line-height:30px'">
                  {{}}
                </div>
              </el-col>
            </el-row>
            <el-row>
              <el-col>
                <div style="font-weight: bold">
                  <p style="margin-top: 5px;margin-left: 5px">Project: {{machineInfo.project}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">Mold: {{machineInfo.mold}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">Side: {{machineInfo.side}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">Process: {{machineInfo.process}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">ML: {{machineInfo.ml}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">OP id: {{}}</p>
<!--                  <p style="margin-top: 5px;margin-left: 5px">-->
<!--                    <el-button type="primary" @click="addNote(machineInfo.name)">Notes</el-button>-->
<!--                  </p>-->
                </div>
              </el-col>
            </el-row>
          </el-card>
        </el-col>
      </el-row>
<!--      <el-row>-->
<!--        <el-col :span="24">-->
<!--          <div id="machineChart" style="border:1px solid blue;height:600px"></div>-->
<!--        </el-col>-->
<!--      </el-row>-->
    </div>
  </div>
</template>

<script>
// import * as echarts from 'echarts';
import {
  getMachineInfoByFloorNumber
} from "@/api/czech/floorPlan";
export default {
  name: "RoomDetail.vue",
  data() {
    return {
      // machineList: ['FG101', 'FG102', 'FG103', 'FG104', 'FG105', 'FG106'],
      // processList: [
      //   {name: 'IQC', color: '#7b9ce1'},
      //   {name: 'Grid', color: '#bd6d6c'},
      //   {name: 'MCR', color: '#75d874'},
      //   {name: 'MCF', color: '#e0bc78'},
      //   {name: 'GRC', color: '#dc77dc'},
      //   {name: 'GRF', color: '#72b362'}
      // ],
      floorMachineInfo: [],
      showContent: false
    }
  },
  mounted() {
    // this.drawMachineChart();
    this.getFloorMachineInfo();
  },
  methods: {
    getFloorMachineInfo(){
      var floorNumber = this.$route.params.floorNumber;
      getMachineInfoByFloorNumber(floorNumber).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          responseData.data.forEach(item => {
            this.floorMachineInfo.push(item)
          })
          this.showContent = true
        }
      });
    },
    // drawMachineChart() {
    //   var chartDom = document.getElementById('machineChart');
    //   var myChart = echarts.init(chartDom);
    //   var option;
    //   var data = [];
    //   var dataCount = 6;
    //   var startTime = +new Date();
    //   var processListNew = [
    //     {name: 'IQC', color: '#7b9ce1'},
    //     {name: 'Grid', color: '#bd6d6c'},
    //     {name: 'MCR', color: '#75d874'},
    //     {name: 'MCF', color: '#e0bc78'},
    //     {name: 'GRC', color: '#dc77dc'},
    //     {name: 'GRF', color: '#72b362'}
    //   ];
    //   this.machineList.forEach(function (machine, index) {
    //     var baseTime = startTime;
    //     for(var i = 0; i < dataCount; i++) {
    //       var processItem = processListNew[i];
    //       var duration = Math.round(Math.random() * 10000);
    //       data.push({
    //         name: processItem.name,
    //         value: [index, baseTime, (baseTime += duration), duration], //index为一组数据的索引，第二个参数为起始时间，第三个参数为结束时间，第四个参数为持续时间
    //         itemStyle: {
    //           normal: {
    //             color: processItem.color
    //           }
    //         }
    //       });
    //       baseTime += Math.round(Math.random() * 2000);
    //     }
    //   });
    //   console.log(data);
    //
    //   option = {
    //     tooltip: {
    //       formatter: function (params) {
    //         return params.marker + params.name + ': ' + params.value[3] + ' ms';
    //       }
    //     },
    //     title: {
    //       text: 'Utilization in time',
    //       left: 'center'
    //     },
    //     dataZoom: [
    //       {
    //         type: 'slider',
    //         filterMode: 'weakFilter',
    //         showDataShadow: false,
    //         top: 400,
    //         labelFormatter: ''
    //       },
    //       {
    //         type: 'inside',
    //         filterMode: 'weakFilter'
    //       }
    //     ],
    //     grid: {
    //       height: 300
    //     },
    //     xAxis: {
    //       // min: startTime,
    //       // scale: true,
    //       // axisLabel: {
    //       //   formatter: function (val) {
    //       //     return Math.max(0, val - startTime) + ' ms';
    //       //   }
    //       // }
    //       type: 'time',
    //       axisLabel: {
    //         formatter: function(value) {
    //           return new Date(value).toLocaleTimeString();
    //         }
    //       },
    //     },
    //     yAxis: {
    //       data: this.machineList
    //     },
    //     series: [
    //       {
    //         type: 'custom',
    //         renderItem: this.renderItem,
    //         itemStyle: {
    //           opacity: 0.8
    //         },
    //         encode: {
    //           x: [1, 2],
    //           y: 0
    //         },
    //         data: data
    //       }
    //     ]
    //   };
    //   option && myChart.setOption(option);
    // },
    // renderItem(params, api) {
    //   var categoryIndex = api.value(0);
    //   var start = api.coord([api.value(1), categoryIndex]);
    //   var end = api.coord([api.value(2), categoryIndex]);
    //   var height = api.size([0, 1])[1] * 0.6;
    //   var rectShape = echarts.graphic.clipRectByRect(
    //       {
    //         x: start[0],
    //         y: start[1] - height /2,
    //         width: end[0] - start[0],
    //         height: height
    //       },
    //       {
    //         x: params.coordSys.x,
    //         y: params.coordSys.y,
    //         width: params.coordSys.width,
    //         height: params.coordSys.height
    //       }
    //   );
    //   return (
    //       rectShape && {
    //         type: 'rect',
    //         transition: ['shape'],
    //         shape: rectShape,
    //         style: api.style()
    //       }
    //   );
    // },
    onTemperatureClick(machineNo) {
      this.$router.push({name: 'temperaturePlots', params: {machineNo: machineNo}});
    },
    onClick(machineNo) {
      this.$router.push({name: 'machineDetail', params: {machineNo: machineNo}});
    }
  }
}
</script>

<style scoped>

</style>