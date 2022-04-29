<template>
  <div>
    <div class="container">
      <el-row>
        <el-col :span="6">
          <el-card style="border:1px solid blue;margin:5px" class="cz_room_card" :body-style="{ padding: '0px', height:'255px'}">
            <p style="text-align: center;font-weight: bold;font-size: 24px">FG{{this.machineInfo.machineNo}}</p>
            <el-row v-if="machineInfo.showStatus === 'Maintenance'" style="text-align: center;height:30px; font-weight: bold;font-size: 16px;">
              <el-col :span="24">
                <div :style="'background-color:grey;height:30px;line-height:30px'">{{machineInfo.showStatus}}</div>

              </el-col>
            </el-row>
            <el-row v-else style="text-align: center;height:30px; font-weight: bold;font-size: 16px;background-color: grey">
              <el-col :span="12">
                <div :style="'background-color:rgba(250,200,88,1);height:30px;line-height:30px;cursor:pointer'" @click="onTemperatureClick(machineInfo.machineNo)">
                  {{machineInfo.temperature}}℃
                </div>
              </el-col>
              <el-col :span="12">
                <div  v-if="machineInfo.showStatus === 'Running'" :style="'background-color:rgba(59,162,114,1);height:30px;line-height:30px'">
                  {{machineInfo.showStatus}}
                </div>
                <div  v-else-if="machineInfo.showStatus === 'Idle'" :style="'background-color:rgba(252,132,82,1);height:30px;line-height:30px'">
                  {{machineInfo.showStatus}}
                </div>
                <div v-else-if="machineInfo.showStatus === 'Failure'" :style="'background-color:rgba(255,0,0,1);height:30px;line-height:30px'">
                  {{machineInfo.showStatus}}
                </div>
              </el-col>
              <!--              <el-col :span="4">-->
              <!--                <div :style="'background-color:yellow;height:30px;line-height:30px'">-->
              <!--                  {{}}-->
              <!--                </div>-->
              <!--              </el-col>-->
            </el-row>
            <el-row>
              <el-col>
                <div style="font-weight: bold">
                  <p style="margin-top: 5px;margin-left: 5px">Project: {{this.machineInfo.project}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">Mold: {{this.machineInfo.mold}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">Side: {{this.machineInfo.side}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">Process: {{this.machineInfo.process}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">ML: {{this.machineInfo.ml}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">OP id: {{}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">
                    <el-button type="primary" @click="addNote(this.machineInfo.machineNo)">Add Notes</el-button>
                  </p>
                </div>
              </el-col>
            </el-row>
          </el-card>
        </el-col>
      </el-row>
      <el-row>
          <div class="block" style="margin-top:20px">
            <h3>Machine notes</h3>
            <el-timeline :reverse="reverse">
              <el-timeline-item
                  v-for="(activity, index) in activities"
                  :key="index"
                  :timestamp="activity.timestamp">
                {{activity.content}}
              </el-timeline-item>
            </el-timeline>
          </div>
      </el-row>
      <el-row>
          <div id="statusChart" style="border:1px solid blue;height:600px;width:100%"></div>
      </el-row>
      <el-dialog title="Machine Note" v-model="dialogVisible" width="30%">
        <el-input
            type="textarea"
            :rows="2"
            placeholder="Please fill in machine notes here"
            v-model="machineNotes">
        </el-input>
        <div style="margin-top:10px">
          <el-button @click="dialogVisible = false">cancel</el-button>
          <el-button type="primary" @click="dialogVisible = false">confirm</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import {
  getMachineInfoByMachineNumber,getStatusInfoByMachineNumber
} from "@/api/czech/floorPlan";
export default {
  name: "MachineDetail",
  props: {
    machineNo: String,
  },
  data() {
    this.myChart = null
    return {
      machineInfo: {},
      reverse: true,
      activities: [{
        content: 'note3',
        timestamp: '2022-03-15'
      }, {
        content: 'note2',
        timestamp: '2022-03-13'
      }, {
        content: 'note1',
        timestamp: '2022-03-11'
      }],
      dialogVisible: false,
      machineNotes: '',
      statusList: [],
      timeList: [],
    }
  },
  created() {
    this.getMachineInfo()
  },
  mounted() {
    this.$nextTick(() => {
      this.drawStatusPlot()
    })
  },
  beforeUnmount() {
    console.log('beforeUnmount')
    if (!this.myChart) {
      return
    }
    // window.removeEventListener('resize', this.__resizeHandler)
    this.myChart.dispose()
    this.myChart = null
  },
  computed:{
    machineNumber(){
      return this.machineNo
    }
  },
  methods: {
    addNote(machineName) {
      console.log(machineName);
      this.dialogVisible = true;
    },
    getMachineInfo() {
      getMachineInfoByMachineNumber(this.machineNumber).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          this.machineInfo = responseData.data
        }
      })
    },
    drawStatusPlot() {
      getStatusInfoByMachineNumber(this.machineNumber).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          responseData.data.forEach(item => {
            this.statusList.push(item.status);
            this.timeList.push(item.time);
          });
          const chartDom = document.getElementById('statusChart');
          if (!chartDom)
            return
          this.myChart = echarts.init(chartDom);
          let option= {
            // title: {
            //   text: machineNumber + ' Utilization in time',
            //   textAlign: 'center',
            //   x: 'center',
            //   y: 'top'
            // },
            tooltip: {
              trigger: 'axis'
            },
            legend: {
              data: ['Machine Status']
            },
            grid: {
              left: '3%',
              right: '4%',
              bottom: '3%',
              containLabel: true
            },
            xAxis: {
              type: 'category',
              boundaryGap: false,
              data: this.timeList,
              axisLabel: {
                margin: 10,
                interval: 100000,
                showMinLabel: true,
                showMaxLabel: true,
              },
            },
            yAxis: {
              type: 'category',
              boundaryGap: false,
              data: ['BD', 'HMIOff', 'idle', 'IPF', 'IPU', 'IPZ', 'LoJ', 'LoT', 'MB',
                      'Normal', 'OMW', 'PM', 'SR', 'TB']
            },
            series: [
              {
                name: 'Machine Status',
                type: 'line',
                data: this.statusList
              }
            ]

          };
          this.myChart.setOption(option)
        }
      });
    }
  }

}
</script>

<style scoped>

</style>
