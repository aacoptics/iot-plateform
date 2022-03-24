<template>
  <div>
    <div class="container">
      <el-row>
        <el-col :span="6">
          <el-card style="border:1px solid blue;margin:5px;" class="cz_room_card" :body-style="{ padding: '0px', height:'255px'}">
            <p style="text-align: center;font-weight: bold;font-size: 24px">{{this.machineInfo.name}}</p>
            <el-row v-if="this.machineInfo.status === 'Maintenance'" style="text-align: center;height:30px; font-weight: bold;font-size: 16px;">
              <el-col :span="24">
                <div :style="'background-color:grey;height:30px;line-height:30px'">{{this.machineInfo.status}}</div>

              </el-col>
            </el-row>
            <el-row v-else style="text-align: center;height:30px; font-weight: bold;font-size: 16px;">
              <el-col :span="5">
                <div :style="'background-color:yellow;height:30px;line-height:30px;cursor:pointer'" @click="onTemperatureClick(this.machineInfo.name)">
                  {{this.machineInfo.temperature}}℃
                </div>
              </el-col>
              <el-col :span="15">
                <div  v-if="this.machineInfo.status === 'Running'" :style="'background-color:green;height:30px;line-height:30px'">
                  {{this.machineInfo.status}}
                </div>
                <div  v-else-if="this.machineInfo.status === 'Failure'" :style="'background-color:red;height:30px;line-height:30px'">
                  {{this.machineInfo.status}}
                </div>
                <div  v-else-if="this.machineInfo.status === 'Idle'" :style="'background-color:orange;height:30px;line-height:30px'">
                  {{this.machineInfo.status}}
                </div>
              </el-col>
              <el-col :span="4">
                <div :style="'background-color:yellow;height:30px;line-height:30px'">
                  {{this.machineInfo.priority}}
                </div>
              </el-col>
            </el-row>
            <el-row>
              <el-col>
                <div style="font-weight: bold">
                  <p style="margin-top: 5px;margin-left: 5px">Project: {{this.machineInfo.name}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">Mold: {{this.machineInfo.mold}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">Side: {{this.machineInfo.side}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">Process: {{this.machineInfo.process}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">ML: {{this.machineInfo.ml}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">OP id: {{this.machineInfo.opid}}</p>
                  <p style="margin-top: 5px;margin-left: 5px">
                    <el-button type="primary" @click="addNote(this.machineInfo.name)">Add Notes</el-button>
                  </p>
                </div>
              </el-col>
            </el-row>
          </el-card>
        </el-col>
      </el-row>

      <el-row>
        <el-col>
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
        </el-col>
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
export default {
  name: "MachineDetail",
  data() {
    return {
      machineInfo: {
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
      machineNotes: ''
    }
  },
  methods: {
    addNote(machineName) {
      console.log(machineName);
      this.dialogVisible = true;
    }
  }

}
</script>

<style scoped>

</style>