<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          模具IoT
        </el-breadcrumb-item>
        <el-breadcrumb-item>刀具寿命管控</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div style="margin-bottom: 20px">
        <div v-for="(item, index) of statusRadio" :key="index" class="status_radio_type"
             :style="'background-color:' + statusInfo[item].color + ';text-align:center'">
          {{ statusInfo[item].desc + '(' + this.statusCount[item] + ')' }}
        </div>
      </div>
      <el-card v-for="(area, index) of areaCode" :key="index" class="box-card" style="margin-top: 10px">
        <template #header>
          <div class="card-header">
            <span>{{ area }}</span>
          </div>
        </template>
        <el-card v-for="(singleMachineInfo, index) of moldDataByArea(area)" :key="index"
                 :body-style="{ padding: '0px', height:'50px'}"
                 shadow="hover"
                 class="mold_card_type"
                 :style="'background-color:' +
                 statusInfo[singleMachineInfo.CncNode.State].color + ';cursor: pointer'">
          <div v-if="getMaintainStatus(singleMachineInfo.CncBaseInfo.monitorNo, singleMachineInfo.CncNode.State)"
               style="height:30px;line-height:40px;text-align: center;font-weight: bold">
            <el-badge is-dot class="item" style="height: 30px;line-height:40px">
              {{ singleMachineInfo.MachineNo }}
            </el-badge>
          </div>
          <div v-else style="height:50px;line-height:50px;text-align: center;font-weight: bold">
            {{ singleMachineInfo.MachineNo }}
          </div>
        </el-card>
      </el-card>
      <el-card style="margin-top: 20px">
        <template #header>
          <div class="card-header">
            <span>时间段：{{ timeArea.startTime }} 至 {{ timeArea.endTime }}</span>
          </div>
        </template>
        <el-row :gutter="20">
          <el-col :span="4">
            <div
                style="font-size: 50px;font-weight: bold;height: 180px;line-height: 180px;text-align: center;background-color: #409EFF;color: white">
              OEE
            </div>
          </el-col>
          <el-col :span="6">
            <el-row v-for="(area, index) of areaCode" :key="index">
              <div style="height: 60px;line-height: 60px">
                <div
                    style="float:left;font-size: xx-large;color: #222222;width: 90px; margin-right: 10px;font-weight: bold">
                  {{ area }}：
                </div>
                <div style="float:left;font-family: 'led regular';font-size: xxx-large;color: green">
                  {{ lastDayTotalTime[area] ? lastDayTotalTime[area] : '0.00' }}%
                </div>
              </div>
            </el-row>
          </el-col>
          <el-col :span="4">
            <div
                style="font-size: 30px;font-weight: bold;height: 180px;line-height: 180px;text-align: center;background-color: #409EFF;color: white">
              符合寿命占比
            </div>
          </el-col>
          <el-col :span="10" style="line-height: 60px;">
            <el-row>
              <div style="float:left;width: 160px;font-weight: bold;font-size: xx-large">报废数量：</div>
              <div style="float:left;font-family: 'led regular';font-size: xxx-large;color: green">
                {{ this.lastDayScrapRate.scrapCount }}
              </div>
            </el-row>
            <el-row>
              <div style="float:left;width: 160px;font-weight: bold;font-size: xx-large">退库数量：</div>
              <div style="float:left;font-family: 'led regular';font-size: xxx-large;color: green">
                {{ this.lastDayScrapRate.outCount }}
              </div>
            </el-row>
            <el-row>
              <div style="float:left;width: 160px;font-weight: bold;font-size: xx-large">占比：</div>
              <div style="float:left;font-family: 'led regular';font-size: xxx-large;color: green">
                {{ this.lastDayScrapRate.rate }}%
              </div>
            </el-row>
          </el-col>
        </el-row>
      </el-card>
    </div>
  </div>
</template>

<script>
import Stomp from 'stompjs';
import {MQTT_PASSWORD, MQTT_SERVICE, MQTT_TOPIC_TOOL_LIFE, MQTT_USERNAME} from '@/utils/msgConfig'
import {getAreaInfo, getLastDayScrapCount, getLastDayTotalTime, getToolMaintainStatus} from "@/api/iot/mold";

export default {
  name: "index",
  data() {
    return {
      client: Stomp.client(MQTT_SERVICE),
      moldData: [],
      statusInfo: {
        0: {
          desc: "离线",
          color: "gray"
        },
        2: {
          desc: "空闲",
          color: "rgba(250,200,88,1)"
        },
        3: {
          desc: "调机",
          color: "rgba(252,132,82,1)"
        },
        4: {
          desc: "报警",
          color: "rgba(238,102,102,1)"
        },
        5: {
          desc: "加工中",
          color: "rgba(59,162,114,1)"
        },
        6: {
          desc: "手轮模式",
          color: "rgba(84,112,198,1)"
        }
      },
      statusCount: {0: 0, 2: 0, 3: 0, 4: 0, 5: 0, 6: 0},
      areaInfo: {},
      areaCode: [],
      maintainStatus: {},
      lastDayTotalTime: [],
      lastDayScrapRate: {
        scrapCount: 0,
        outCount: 0,
        rate: '0.00'
      },
      statusRadio: [5, 2, 3, 6, 4, 0],
      timeArea: {
        startTime: '',
        endTime: ''
      }
    }
  },
  computed: {},
  mounted() {
    this.getMachineAreaInfo();
    this.connect();
    this.getToolMaintainStatus();
  },
  methods: {
    getMaintainStatus(monitorNo, state) {
      return (!this.maintainStatus[monitorNo]) && state === 5;
    },
    moldDataByArea(area) {
      const res = []
      this.moldData.forEach(item => {
        if (this.areaInfo[item.MachineNo] === area) {
          res.push(item)
        }
      })
      return res
    },
    onConnected: function () {
      const headers = {
        'auto-delete': true
      };
      //订阅的频道
      this.client.subscribe(MQTT_TOPIC_TOOL_LIFE, this.responseCallback, headers);
    },
    onFailed: function (msg) {
      console.log(new Date() + "报错:" + msg);
      this.reconnect()
    },
    //成功时的回调函数
    responseCallback: function (msg) {
      //接收消息的处理
      this.moldData = JSON.parse(msg.body)
      this.statusCount = {0: 0, 2: 0, 3: 0, 4: 0, 5: 0, 6: 0}
      this.moldData.forEach(item => {
        if (this.areaInfo[item.MachineNo]) {
          this.statusCount[item.CncNode.State]++;
        }
      })
    },
    //连接
    connect: function () {
      this.client.debug = null
      this.client.connect({
        login: MQTT_USERNAME,
        password: MQTT_PASSWORD
      }, this.onConnected, this.onFailed);
    },
    reconnect() {
      const reconInv = setInterval(() => {
        console.info(new Date() + '重连中...')
        this.client = Stomp.client(MQTT_SERVICE)
        this.client.debug = null
        this.client.connect({
          login: MQTT_USERNAME,
          password: MQTT_PASSWORD
        }, () => {
          console.info(new Date() + '重连成功')
          // 连接成功，清除定时器
          clearInterval(reconInv)
          this.onConnected()
        }, this.onFailed)
      }, 2000)
    },
    disconnect() {
      if (this.client !== null) {
        this.client.disconnect();
        console.log("断开MQ连接");
      }
    },
    getToolMaintainStatus() {
      const monitorNos = []
      this.moldData.forEach(item => {
        if (item.CncNode.State === 5 && item.CncBaseInfo.monitorNo != null && monitorNos.indexOf(item.CncBaseInfo.monitorNo) < 0) {
          monitorNos.push(item.CncBaseInfo.monitorNo)
        }
      })
      getToolMaintainStatus(monitorNos).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          this.maintainStatus = responseData.data;
        }
      })
      this.getTimeArea();
      this.getLastDayOee();
      this.getLastDayScrapRate();
    },
    getLastDayOee() {
      getLastDayTotalTime(this.timeArea.startTime).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          responseData.data.forEach(item => {
            const oee = (item.totalTime * 1.0 / (item.runCount * 24 * 60 * 60) * 100).toFixed(2)
            this.lastDayTotalTime[item.area] = oee
          })
        }
      })
    },
    getTimeArea() {
      const currentTime = new Date();
      let startTime = '';
      let endTime = '';
      let tempTime = new Date(new Date(new Date().toLocaleDateString()).getTime() + 7 * 60 * 60 * 1000 + 30 * 60 * 1000)
      if (currentTime <= tempTime) {
        tempTime.setDate(tempTime.getDate() - 2)
      } else {
        tempTime.setDate(tempTime.getDate() - 1)
      }
      startTime = this.$moment(tempTime).format('YYYY-MM-DD HH:mm:ss');
      tempTime.setDate(tempTime.getDate() + 1)
      endTime = this.$moment(tempTime).format('YYYY-MM-DD HH:mm:ss');
      this.timeArea.startTime = startTime
      this.timeArea.endTime = endTime
    },
    getLastDayScrapRate() {
      getLastDayScrapCount(this.timeArea.startTime).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          const {scrap, out} = responseData.data
          this.lastDayScrapRate.scrapCount = scrap
          this.lastDayScrapRate.outCount = out
          if (out > 0) {
            this.lastDayScrapRate.rate = (scrap * 1.0 / out * 100).toFixed(2)
          } else {
            this.lastDayScrapRate.rate = '0.00'
          }

        }
      })
    },
    getMachineAreaInfo() {
      getAreaInfo().then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          const {areaInfo, areaCode} = responseData.data
          this.areaInfo = areaInfo
          this.areaCode = areaCode
        }
      })
    }
  },
  watch: {},
  created() {
    this.timer = setInterval(() => {
      this.getToolMaintainStatus()
    }, 10000)
  },
  beforeUnmount() {
    clearInterval(this.timer);
    this.disconnect();
  }
}
</script>

<style scoped>
.el-card ::v-deep(.el-card__header) {
  padding: 8px 20px;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  font-weight: bold;
  font-size: 20px;
}


</style>
