<template>
  <div>
    <div class="container">
      <el-row>
        <el-col :span="24">
          <p style="text-align: center">Room #1 - MC</p>
        </el-col>
      </el-row>
<!--      <el-row>-->
<!--        <el-col :span="3">-->
<!--          <el-card style="border:1px solid blue;margin:5px;" class="cz_room_card" :body-style="{ padding: '0px', height:'255px'}">-->
<!--            <p style="text-align: center;font-weight: bold;font-size: 24px">FG 116</p>-->
<!--            <el-row style="text-align: center;height:30px; font-weight: bold;font-size: 16px;">-->
<!--              <el-col :span="24">-->
<!--                <div :style="'background-color:grey;height:30px;line-height:30px'">-->
<!--                  Maintenance-->
<!--                </div>-->
<!--              </el-col>-->
<!--            </el-row>-->
<!--            <el-row>-->
<!--              <el-col>-->
<!--                <div style="font-weight: bold">-->
<!--                  <p style="margin-top: 5px;margin-left: 5px">Project: N/A</p>-->
<!--                  <p style="margin-top: 5px;margin-left: 5px">Mold: N/A</p>-->
<!--                  <p style="margin-top: 5px;margin-left: 5px">Side: N/A</p>-->
<!--                  <p style="margin-top: 5px;margin-left: 5px">Process: xxxx</p>-->
<!--                  <p style="margin-top: 5px;margin-left: 5px">ML: N/A</p>-->
<!--                  <p style="margin-top: 5px;margin-left: 5px">OP id: xx</p>-->
<!--                  <p style="margin-top: 5px;margin-left: 5px">-->
<!--                    <el-button type="primary" @click="addNote">Notes</el-button>-->
<!--                  </p>-->
<!--                </div>-->
<!--              </el-col>-->
<!--            </el-row>-->
<!--          </el-card>-->
<!--        </el-col>-->
<!--        <el-col :span="3">-->
<!--          <el-card style="border:1px solid blue;margin:5px;" class="cz_room_card" :body-style="{ padding: '0px', height:'255px'}">-->
<!--            <p style="text-align: center;font-weight: bold;font-size: 24px">FG 114</p>-->
<!--            <el-row style="text-align: center;height:30px; font-weight: bold;font-size: 16px;">-->
<!--              <el-col :span="4">-->
<!--                <div :style="'background-color:yellow;height:30px;line-height:30px'">-->
<!--                  10℃-->
<!--                </div>-->
<!--              </el-col>-->
<!--              <el-col :span="16">-->
<!--                <div :style="'background-color:green;height:30px;line-height:30px'">-->
<!--                  Running-->
<!--                </div>-->
<!--              </el-col>-->
<!--              <el-col :span="4">-->
<!--                <div :style="'background-color:yellow;height:30px;line-height:30px'">-->
<!--                  2-->
<!--                </div>-->
<!--              </el-col>-->
<!--            </el-row>-->
<!--            <el-row>-->
<!--              <el-col>-->
<!--                <div style="font-weight: bold">-->
<!--                  <p style="margin-top: 5px;margin-left: 5px">Project: N/A</p>-->
<!--                  <p style="margin-top: 5px;margin-left: 5px">Mold: N/A</p>-->
<!--                  <p style="margin-top: 5px;margin-left: 5px">Side: N/A</p>-->
<!--                  <p style="margin-top: 5px;margin-left: 5px">Process: xxxx</p>-->
<!--                  <p style="margin-top: 5px;margin-left: 5px">ML: N/A</p>-->
<!--                  <p style="margin-top: 5px;margin-left: 5px">OP id: xx</p>-->
<!--                  <p style="margin-top: 5px;margin-left: 5px">-->
<!--                    <el-button type="primary" @click="addNote">Notes</el-button>-->
<!--                  </p>-->
<!--                </div>-->
<!--              </el-col>-->
<!--            </el-row>-->
<!--          </el-card>-->
<!--        </el-col>-->
<!--        <el-col :span="3">-->
<!--          <el-card style="border:1px solid blue;margin:5px;" class="cz_room_card" :body-style="{ padding: '0px', height:'255px'}"></el-card>-->
<!--        </el-col>-->
<!--        <el-col :span="3">-->
<!--          <el-card style="border:1px solid blue;margin:5px;" class="cz_room_card" :body-style="{ padding: '0px', height:'255px'}"></el-card>-->
<!--        </el-col>-->
<!--        <el-col :span="3">-->
<!--          <el-card style="border:1px solid blue;margin:5px;" class="cz_room_card" :body-style="{ padding: '0px', height:'255px'}"></el-card>-->
<!--        </el-col>-->
<!--        <el-col :span="3">-->
<!--          <el-card style="border:1px solid blue;margin:5px;" class="cz_room_card" :body-style="{ padding: '0px', height:'255px'}"></el-card>-->
<!--        </el-col>-->
<!--        <el-col :span="3">-->
<!--          <el-card style="border:1px solid blue;margin:5px;" class="cz_room_card" :body-style="{ padding: '0px', height:'255px'}"></el-card>-->
<!--        </el-col>-->
<!--        <el-col :span="3">-->
<!--          <el-card style="border:1px solid blue;margin:5px;" class="cz_room_card" :body-style="{ padding: '0px', height:'255px'}"></el-card>-->
<!--        </el-col>-->
<!--      </el-row>-->
<!--      <el-row>-->
<!--        <el-col :span="3">-->
<!--          <el-card style="border:1px solid blue;margin:5px;" class="cz_room_card" :body-style="{ padding: '0px', height:'255px'}"></el-card>-->
<!--        </el-col>-->
<!--        <el-col :span="3">-->
<!--          <el-card style="border:1px solid blue;margin:5px;" class="cz_room_card" :body-style="{ padding: '0px', height:'255px'}"></el-card>-->
<!--        </el-col>-->
<!--        <el-col :span="3">-->
<!--          <el-card style="border:1px solid blue;margin:5px;" class="cz_room_card" :body-style="{ padding: '0px', height:'255px'}"></el-card>-->
<!--        </el-col>-->
<!--        <el-col :span="3">-->
<!--          <el-card style="border:1px solid blue;margin:5px;" class="cz_room_card" :body-style="{ padding: '0px', height:'255px'}"></el-card>-->
<!--        </el-col>-->
<!--        <el-col :span="3">-->
<!--          <el-card style="border:1px solid blue;margin:5px;" class="cz_room_card" :body-style="{ padding: '0px', height:'255px'}"></el-card>-->
<!--        </el-col>-->
<!--        <el-col :span="3">-->
<!--          <el-card style="border:1px solid blue;margin:5px;" class="cz_room_card" :body-style="{ padding: '0px', height:'255px'}"></el-card>-->
<!--        </el-col>-->
<!--        <el-col :span="3">-->
<!--          <el-card style="border:1px solid blue;margin:5px;" class="cz_room_card" :body-style="{ padding: '0px', height:'255px'}"></el-card>-->
<!--        </el-col>-->
<!--        <el-col :span="3">-->
<!--          <el-card style="border:1px solid blue;margin:5px;" class="cz_room_card" :body-style="{ padding: '0px', height:'255px'}"></el-card>-->
<!--        </el-col>-->
<!--      </el-row>-->
      <el-row>
        <el-col v-for="(machineInfo, index) of machineInfoByRoom(9)" :span="3" :key="index">
          <el-card style="border:1px solid blue;margin:5px;" class="cz_room_card" :body-style="{ padding: '0px', height:'255px'}">
            <p style="text-align: center;font-weight: bold;font-size: 24px;cursor: pointer" @click="onClick(machineInfo.name)">{{machineInfo.name}}</p>
            <el-row v-if="machineInfo.status === 'Maintenance'" style="text-align: center;height:30px; font-weight: bold;font-size: 16px;">
              <el-col :span="24">
                <div :style="'background-color:grey;height:30px;line-height:30px'">{{machineInfo.status}}</div>

              </el-col>
            </el-row>
            <el-row v-else style="text-align: center;height:30px; font-weight: bold;font-size: 16px;">
              <el-col :span="5">
                <div :style="'background-color:yellow;height:30px;line-height:30px;cursor:pointer'" @click="onTemperatureClick(machineInfo.name)">
                  {{machineInfo.temperature}}℃
                </div>
              </el-col>
              <el-col :span="15">
                <div  v-if="machineInfo.status === 'Running'" :style="'background-color:green;height:30px;line-height:30px'">
                  {{machineInfo.status}}
                </div>
                <div  v-else-if="machineInfo.status === 'Failure'" :style="'background-color:red;height:30px;line-height:30px'">
                  {{machineInfo.status}}
                </div>
                <div  v-else-if="machineInfo.status === 'Idle'" :style="'background-color:orange;height:30px;line-height:30px'">
                  {{machineInfo.status}}
                </div>
              </el-col>
              <el-col :span="4">
                <div :style="'background-color:yellow;height:30px;line-height:30px'">
                  {{machineInfo.priority}}
                </div>
              </el-col>
            </el-row>
            <el-row>
              <el-col>
                <div style="font-weight: bold">
                  <p style="margin-top: 5px;margin-left: 5px">Project: {{machineInfo.name}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">Mold: {{machineInfo.mold}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">Side: {{machineInfo.side}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">Process: {{machineInfo.process}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">ML: {{machineInfo.ml}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">OP id: {{machineInfo.opid}}</p>
<!--                  <p style="margin-top: 5px;margin-left: 5px">-->
<!--                    <el-button type="primary" @click="addNote(machineInfo.name)">Notes</el-button>-->
<!--                  </p>-->
                </div>
              </el-col>
            </el-row>
          </el-card>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <div id="machineChart" style="border:1px solid blue;height:600px"></div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
export default {
  name: "RoomDetail.vue",
  data() {
    return {
      machineList: ['FG101', 'FG102', 'FG103', 'FG104', 'FG105', 'FG106'],
      processList: [
        {name: 'IQC', color: '#7b9ce1'},
        {name: 'Grid', color: '#bd6d6c'},
        {name: 'MCR', color: '#75d874'},
        {name: 'MCF', color: '#e0bc78'},
        {name: 'GRC', color: '#dc77dc'},
        {name: 'GRF', color: '#72b362'}
      ]
    }
  },
  mounted() {
    this.drawMachineChart();
  },
  methods: {
    drawMachineChart() {
      var chartDom = document.getElementById('machineChart');
      var myChart = echarts.init(chartDom);
      var option;
      var data = [];
      var dataCount = 6;
      var startTime = +new Date();
      var processListNew = [
        {name: 'IQC', color: '#7b9ce1'},
        {name: 'Grid', color: '#bd6d6c'},
        {name: 'MCR', color: '#75d874'},
        {name: 'MCF', color: '#e0bc78'},
        {name: 'GRC', color: '#dc77dc'},
        {name: 'GRF', color: '#72b362'}
      ];
      this.machineList.forEach(function (machine, index) {
        var baseTime = startTime;
        for(var i = 0; i < dataCount; i++) {
          var processItem = processListNew[i];
          var duration = Math.round(Math.random() * 10000);
          data.push({
            name: processItem.name,
            value: [index, baseTime, (baseTime += duration), duration], //index为一组数据的索引，第二个参数为起始时间，第三个参数为结束时间，第四个参数为持续时间
            itemStyle: {
              normal: {
                color: processItem.color
              }
            }
          });
          baseTime += Math.round(Math.random() * 2000);
        }
      });
      console.log(data);

      option = {
        tooltip: {
          formatter: function (params) {
            return params.marker + params.name + ': ' + params.value[3] + ' ms';
          }
        },
        title: {
          text: 'Utilization in time',
          left: 'center'
        },
        dataZoom: [
          {
            type: 'slider',
            filterMode: 'weakFilter',
            showDataShadow: false,
            top: 400,
            labelFormatter: ''
          },
          {
            type: 'inside',
            filterMode: 'weakFilter'
          }
        ],
        grid: {
          height: 300
        },
        xAxis: {
          // min: startTime,
          // scale: true,
          // axisLabel: {
          //   formatter: function (val) {
          //     return Math.max(0, val - startTime) + ' ms';
          //   }
          // }
          type: 'time',
          axisLabel: {
            formatter: function(value) {
              return new Date(value).toLocaleTimeString();
            }
          },
        },
        yAxis: {
          data: this.machineList
        },
        series: [
          {
            type: 'custom',
            renderItem: this.renderItem,
            itemStyle: {
              opacity: 0.8
            },
            encode: {
              x: [1, 2],
              y: 0
            },
            data: data
          }
        ]
      };
      option && myChart.setOption(option);
    },
    renderItem(params, api) {
      var categoryIndex = api.value(0);
      var start = api.coord([api.value(1), categoryIndex]);
      var end = api.coord([api.value(2), categoryIndex]);
      var height = api.size([0, 1])[1] * 0.6;
      var rectShape = echarts.graphic.clipRectByRect(
          {
            x: start[0],
            y: start[1] - height /2,
            width: end[0] - start[0],
            height: height
          },
          {
            x: params.coordSys.x,
            y: params.coordSys.y,
            width: params.coordSys.width,
            height: params.coordSys.height
          }
      );
      return (
          rectShape && {
            type: 'rect',
            transition: ['shape'],
            shape: rectShape,
            style: api.style()
          }
      );
    },
    onTemperatureClick(machineName) {
      this.$router.push({name: 'temperaturePlots', query: {machineName: machineName}});
    },
    machineInfoByRoom(roomNum) {
      const res = []
      if(roomNum == 9) {
        res.push({
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx',
              'status': 'Maintenance',
              'temperature': '',
              'priority': ''
            },
            {
              'name': 'FG902',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx',
              'status': 'Idle',
              'temperature': '10',
              'priority': '3'
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx',
              'status': 'Failure',
              'temperature': '15',
              'priority': '4'
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx',
              'status': 'Running',
              'temperature': '20',
              'priority': '2'
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx',
              'status': 'Maintenance',
              'temperature': '',
              'priority': ''
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx',
              'status': 'Maintenance',
              'temperature': '',
              'priority': ''
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx',
              'status': 'Maintenance',
              'temperature': '',
              'priority': ''
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx',
              'status': 'Maintenance',
              'temperature': '',
              'priority': ''
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx',
              'status': 'Maintenance',
              'temperature': '',
              'priority': ''
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx',
              'status': 'Maintenance',
              'temperature': '',
              'priority': ''
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx',
              'status': 'Maintenance',
              'temperature': '',
              'priority': ''
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx',
              'status': 'Maintenance',
              'temperature': '',
              'priority': ''
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx',
              'status': 'Maintenance',
              'temperature': '',
              'priority': ''
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx',
              'status': 'Maintenance',
              'temperature': '',
              'priority': ''
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx',
              'status': 'Maintenance',
              'temperature': '',
              'priority': ''
            },
            {
              'name': 'FG901',
              'mold': 'AP4-000',
              'side': 'S1',
              'process': 'Fine',
              'ml': '02',
              'opid': 'xx',
              'status': 'Maintenance',
              'temperature': '',
              'priority': ''
            });
      }
      return res
    },
    // addNote(projectName) {
    //   console.log(projectName)
    // }
    onClick(machineName) {
      this.$router.push({name: 'machineDetail', query: {machineName: machineName}});
    }
  }
}
</script>

<style scoped>

</style>