<template>
  <div>
    <div class="container">
      <mqtt-client ref="mqttClient" :value="lenspackerTopic" @messageArrived="messageArrived"/>
      <div style="margin-bottom: 20px">
        <div v-for="(item, index) of statusRadio" :key="index" class="status_radio_type"
             :style="'background-color:' + getStatusRadioColor(item) + ';text-align:center'"
             @click="onStatusRadioClick(item)">
          {{ item + '(' + this.statusCount[item] + ')' }}
        </div>
      </div>
      <el-card v-for="(val, key, index) in LensPackerMachineInfo" :key="index"
               :body-style="{ padding: '0px', height:'120px'}"
               shadow="hover"
               class="lenspacker_card_type"
               style="cursor: pointer">

        <el-row>
          <el-col>
            <div style="font-weight: bold">
              <el-row
                  style="text-align: center;height:30px; font-weight: bold;font-size: 16px;border-bottom: 1px solid cornflowerblue">
                <el-col :span="24">
                  <p style="text-align: center;font-weight: bold;color: #008000;font-size: 24px">
                    {{ key.substr(4) }}</p>
                </el-col>
                <!--                <el-col :span="16">-->
                <!--                    {{ getMachineStatus(singleMachineInfo.status) }}-->
                <!--                  <div :style="'background-color:' + statusType[getMachineStatus(singleMachineInfo.status)] + ';height:30px;line-height:30px'">-->
                <!--                  </div>-->
                <!--                </el-col>-->
              </el-row>
              <el-row style="text-align: center;height:30px; font-weight: bold;font-size: 16px;">
                <el-col :span="24">
                  <!--                  <div v-if="singleMachineInfo.status === 2" :style="'background-color:' + statusType[getMachineStatus(singleMachineInfo.status)] + ';height:30px;line-height:30px'">-->
                  <!--                    {{ getMachineStatus(singleMachineInfo.status) + ':' + singleMachineInfo.alarmInfo }}-->
                  <!--                  </div>-->
                  <div v-if="val.Status === 2"
                       :style="'background-color:' + statusType[getMachineStatus(val.Status)] + ';height:30px;line-height:30px'">

                    {{ val.AlarmInfo }}
                  </div>
                  <div v-else
                       :style="'background-color:' + statusType[getMachineStatus(val.Status)] + ';height:30px;line-height:30px'">
                    {{ getMachineStatus(val.Status) }}
                  </div>
                </el-col>
              </el-row>
              <el-row style="height: 30px;line-height: 30px">
                <el-col :span="12">产能：{{ val.OutputQty }}</el-col>
                <el-col :span="12">节拍：{{ val.MachineCT.toFixed(2) }}</el-col>
              </el-row>
              <el-row style="height: 30px;line-height: 30px">
                <el-col :span="12" v-if="val.status !== 0">
                  类型：{{ val.CavityNums === 24 ? 24 : 16 }}穴
                </el-col>
                <el-col :span="12" v-else>类型：</el-col>
                <el-col :span="12">握手信号：{{ val.IsComplete }}</el-col>
              </el-row>
            </div>
          </el-col>
        </el-row>
      </el-card>
    </div>
  </div>
</template>

<script>
import {getMachineStatus, getMachineAlarms} from "@/api/iot/lenspacker";
import MqttClient from "@/components/MqttClient";

export default {
  name: "CoatingMachineMonitor",
  components: {
    MqttClient
  },
  created() {
    //this.getLensPackerStatus()
    this.timer = setInterval(() => {
      //this.getLensPackerStatus()
    }, 10000)
  },
  computed: {
    lenspackerTopic() {
      return [
        {
          topic: 'LensPacker/status/' + this.floorInfo + '/+',
          qos: 0
        }
      ]
    },
    LensPackerMachineInfo() {
      return this.getLensPackerMachineInfo()
    }
  },
  methods: {
    getLensPackerMachineInfo(){
      const pages = []
      this.setDefaultCount()
      const position = this.$route.query.position;
      if (this.$route.params.status) {
        this.setStatusRadioValue(this.$route.params.status);
        this.refreshPage()
      }
      for (const item in this.LensPackerInfoList) {
        const statusName = this.getMachineStatus(this.LensPackerInfoList[item].Data.Status)
        if (item.indexOf(position) === 0) {
          if (this.statusRadioValue.indexOf(statusName) > -1) {
            pages[item] = this.LensPackerInfoList[item].Data
          }
          this.statusCount[statusName]++
        }
      }
      return this.sortObjByKey(pages)
    },
    sortObjByKey(obj) {
      const keys = Object.keys(obj).sort();
      const newObj = {};
      for (let i = 0; i < keys.length; i++) {
        const index = keys[i];
        newObj[index] = obj[index];
      }
      return newObj;
    },
    initConnect() {
      this.$nextTick(() => {
        this.$refs.mqttClient.createMqttConnection();
      });
    },
    //接收消息
    messageArrived(msg) {
      switch (msg.Message) {
        case 'status':
          this.LensPackerInfoList[msg.ClientId] = msg
          break;
      }
    },
    //断开连接
    disconnect() {
      this.$refs.mqttClient.destroyMqttConnection();
    },
    refreshPage() {
      this.$router.push(this.$route.fullPath)
    },
    setStatusRadioValue(status) {
      this.statusRadioValue = []
      this.statusRadioValue.push(status)
    },
    setAllStatusRadioValue() {
      this.statusRadioValue = ['正常运行', '报警', '设备离线']
    },
    getMachineStatus(statusCode) {
      if (statusCode === 0) {
        return '设备离线'
      } else if (statusCode === 1) {
        return '正常运行'
      } else if (statusCode === 2) {
        return '报警'
      }
    },
    getStatusRadioColor(statusCode) {
      if (this.statusRadioValue.indexOf(statusCode) > -1)
        return this.statusType[statusCode]
      else
        return "rgba(235,235,235,1)"
    },
    setDefaultCount() {
      this.statusCount = {
        '设备离线': 0,
        '正常运行': 0,
        '报警': 0
      }
    },
    onStatusRadioClick(statusCode) {
      const idx = this.statusRadioValue.indexOf(statusCode)
      if (idx > -1)
        this.statusRadioValue.splice(idx, 1)
      else
        this.statusRadioValue.push(statusCode)
    },
    getLensPackerStatus() {
      getMachineStatus().then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          this.LensPackerInfoList = responseData.data;
        }
      })
      getMachineAlarms().then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          this.LensPackerAlarmInfo = responseData.data;
        }
      })
    }
  },
  mounted() {
    let _this = this;
    setTimeout(function() {
      const position = _this.$route.query.position
      _this.floorInfo = position.substring(2, position.length)
      _this.siteInfo = _this.$route.query.site
      _this.initConnect()
    }, 100);
  },
  beforeUnmount() {
    clearInterval(this.timer);
  },
  data() {
    return {
      floorInfo: '',
      siteInfo: '',
      client: {
        connected: false,
      },
      LensPackerInfoList: {},
      // statusType: {
      //   '设备离线': 'item_coating_offline',
      //   '正常运行': 'item_coating_running',
      //   '上料预警': 'item_coating_alarm'
      // }
      LensPackerAlarmInfo: [],
      statusType: {
        '设备离线': "gray",
        '正常运行': "rgba(59,162,114,1)",
        '报警': "rgba(238,102,102,1)"
      },
      statusRadio: ['正常运行', '报警', '设备离线'],
      statusRadioValue: ['正常运行', '报警', '设备离线'],
      statusCount: {
        '设备离线': 0,
        '正常运行': 0,
        '报警': 0
      }
    }
  }
  ,
  watch: {
    $route: {
      handler() {
        let _this = this;
        setTimeout(function () {
          _this.floorInfo = _this.$route.query.position.substring(2, _this.$route.query.position.length)
          _this.siteInfo = _this.$route.query.site
          _this.initConnect()
        }, 100);
      },
      deep: true,
    }
  }
};
</script>
