<template>
  <div>
    <div class="container">
      <el-row>
        <el-col :span="6">
          <el-card style="border:1px solid blue;margin:5px" class="cz_room_card" :body-style="{ padding: '0px', height:'255px'}">
            <p style="text-align: center;font-weight: bold;font-size: 24px">FG{{this.machineInfo.machineNo}}</p>
            <el-row v-if="machineInfo.showStatus === 'Maintenance'" style="text-align: center;height:30px; font-weight: bold;font-size: 16px;">
              <el-col :span="24">
                <div :style="'background-color:grey;height:30px;line-height:30px'">{{machineInfo.showStatus}} &nbsp; {{machineInfo.status}}</div>

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
                  {{machineInfo.showStatus}} &nbsp; {{machineInfo.status}}
                </div>
                <div  v-else-if="machineInfo.showStatus === 'Idle'" :style="'background-color:rgba(252,132,82,1);height:30px;line-height:30px'">
                  {{machineInfo.showStatus}} &nbsp; {{machineInfo.status}}
                </div>
                <div v-else-if="machineInfo.showStatus === 'Failure'" :style="'background-color:rgba(255,0,0,1);height:30px;line-height:30px'">
                  {{machineInfo.showStatus}} &nbsp; {{machineInfo.status}}
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
<!--            <el-timeline :reverse="reverse">-->
<!--              <el-timeline-item-->
<!--                  v-for="(activity, index) in activities"-->
<!--                  :key="index"-->
<!--                  :timestamp="activity.timestamp">-->
<!--                {{activity.content}}-->
<!--              </el-timeline-item>-->
<!--            </el-timeline>-->
            <ul class="el-timeline"  style="margin-top:15px">
              <li v-for="(remark, index) of this.remarkList" :key="index" style="margin-top:10px">
                <div class="el-timeline-item__node el-timeline-item__node--normal el-timeline-item__node--"></div>
                <div class="el-timeline-item__wrapper">
                  <div class="el-timeline-item__content">{{remark.content}}</div>
                  <div class="el-timeline-item__timestamp is-bottom">{{remark.create_date}}</div>
                  <div>
                    <el-button type="primary" icon="el-icon-edit" size="mini" @click="openModifyDialog(remark)"></el-button>
                    <el-button type="primary" icon="el-icon-delete" size="mini" @click="deleteRemark(remark)"></el-button>
                  </div>
                </div>
              </li>
            </ul>
          </div>
      </el-row>
      <el-row>
          <div id="statusChart" style="border:1px solid blue;height:600px;width:100%;margin-top:20px"></div>
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
          <el-button type="primary" @click="saveRemark">confirm</el-button>
        </div>
      </el-dialog>

      <el-dialog title="Machine Note" v-model="updateDialogVisible" width="30%">
        <el-input
            type="textarea"
            :rows="2"
            v-model="newMachineNotes">
        </el-input>
        <el-input style="display: none" v-model="updateRemarkItem"></el-input>
        <div style="margin-top:10px">
          <el-button @click="updateDialogVisible = false">cancel</el-button>
          <el-button type="primary" @click="updateRemark(this.updateRemarkItem, this.newMachineNotes)">confirm</el-button>
        </div>
      </el-dialog>

      <el-dialog v-model="temperatureDialogVisible" width="80%"  destroy-on-close title="Temperature Plots">
        <temperature-plots ref="temperaturePlots" :machine-no="machineNo"></temperature-plots>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import {
  getMachineInfoByMachineNumber,getStatusInfoByMachineNumber, getRemarkByMachineNumber,
  saveRemark, updateRemark, deleteRemark
} from "@/api/czech/floorPlan";
import temperaturePlots from "@/views/czech/TemperaturePlots";
export default {
  name: "MachineDetail",
  components: {temperaturePlots},
  props: {
    machineNo: String,
  },
  data() {
    this.myChart = null
    return {
      machineInfo: {},
      dialogVisible: false,
      machineNotes: '',
      updateDialogVisible: false,
      newMachineNotes: '',
      updateRemarkItem: {},
      statusList: [],
      timeList: [],
      temperatureDialogVisible: false,
      remarkList: [],
    }
  },
  created() {
    this.getMachineInfo();
    this.getRemarkList();
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
    onTemperatureClick(machineNo) {
      this.machineNumber = machineNo;
      this.temperatureDialogVisible = true;
      //this.$router.push({name: 'temperaturePlots', params: {machineNo: machineNo}});
    },
    getMachineInfo() {
      getMachineInfoByMachineNumber(this.machineNumber).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          this.machineInfo = responseData.data
        }
      })
    },
    getRemarkList() {
      getRemarkByMachineNumber('FG' + this.machineNumber).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          responseData.data.forEach(item => {
            this.remarkList.push(item)
          })
        }
      })
    },
    saveRemark() {
      if(this.machineNotes == '') {
        alert('Machine notes is empty');
        return false;
      }
      saveRemark('FG' + this.machineNumber, this.machineNotes).then((response) => {
        const responseData = response.data
            if (responseData.code === '000000') {
              this.dialogVisible = false
              this.remarkList = []
              this.getRemarkList()
            }
      });
    },
    openModifyDialog(remark) {
      this.updateDialogVisible = true;
      this.updateRemarkItem = remark;
      this.newMachineNotes = remark.content;
    },
    updateRemark(remark, newContent) {
      if(newContent == '') {
        alert('Machine notes is empty');
        return false;
      }
      updateRemark(remark, newContent).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          this.updateDialogVisible = false;
          this.remarkList = []
          this.getRemarkList()
        }
      });
    },
    deleteRemark(remark) {
      deleteRemark(remark).then((response) => {
        const responseData = response.data
            if (responseData.code === '000000') {
              this.remarkList = []
              this.getRemarkList()
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
