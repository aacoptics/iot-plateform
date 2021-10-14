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
        <el-row :gutter="20">
          <el-col :span="2">
            <div
                style="font-size: 50px;font-weight: bold;height: 180px;line-height: 180px;text-align: center;background-color: #409EFF;color: white">
              OEE
            </div>
          </el-col>
          <el-col :span="10">
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
          <el-col :span="8">
            <div style="line-height: 180px;float:left;font-family: 'led regular';font-size: 100px;color: green">
              {{ this.lastDayScrapRate }}%
            </div>
          </el-col>
        </el-row>
      </el-card>
    </div>
  </div>
</template>

<script>
import Stomp from 'stompjs';
import {MQTT_PASSWORD, MQTT_SERVICE, MQTT_TOPIC_TOOL_LIFE, MQTT_USERNAME} from '@/utils/msgConfig'
import {getLastDayScrapCount, getLastDayTotalTime, getToolMaintainStatus} from "@/api/iot/mold";

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
      areaInfo: {
        "CF01": "CF",
        "CF02": "CF",
        "CF03": "CF",
        "CF04": "CF",
        "CF05": "CF",
        "CF06": "CF",
        "CF07": "CF",
        "CF08": "CF",
        "CF09": "CF",
        "CF10": "CF",
        "CF11": "CF",
        "CF12": "CF",
        "CF13": "CF",
        "CF14": "CF",
        "CF15": "CF",
        "CF16": "CF",
        "CF17": "CF",
        "CF18": "CF",
        "CF19": "CF",
        "CF20": "CF",
        "CF21": "CF",
        "CF22": "CF",
        "CF23": "CF",
        "CF24": "CF",
        "CF25": "CF",
        "CF26": "CF",
        "CF27": "CF",
        "CF28": "CF",
        "CLC01": "CF",
        "JD01": "CH",
        "JD02": "CH",
        "JD03": "CH",
        "JD04": "CH",
        "JD05": "CH",
        "JD06": "CH",
        "JD07": "CH",
        "JD08": "CH",
        "JD09": "CH",
        "JD10": "CH",
        "JD11": "CH",
        "JD12": "CH",
        "JD13": "CH",
        "JD14": "CH",
        "JD15": "CH",
        "JD16": "CH",
        "GSX50": "CH",
        "GSX51": "CH",
        "GSX52": "CH",
        "GSX53": "CH",
        "GSX54": "CH",
        "GSX55": "CH",
        "GSX56": "CH",
        "GSX57": "CH",
        "GSX58": "CH",
        "GSX59": "CH",
        "GSX60": "CH",
        "GSX61": "CH",
        "GSX62": "CH",
        "GSX63": "CH",
        "GSX64": "CH",
        "GSX65": "CH",
        "GSX66": "CH",
        "GSX67": "CH",
        "GSX68": "CH",
        "GSX69": "CH",
        "GSX70": "CH",
        "GSX71": "CH",
        "GSX01": "CHP",
        "GSX02": "CHP",
        "GSX03": "CHP",
        "GSX04": "CHP",
        "GSX05": "CHP",
        "GSX06": "CHP",
        "GSX07": "CHP",
        "GSX08": "CHP",
        "GSX09": "CHP",
        "GXS12": "CHP",
        "GXS13": "CHP",
        "GXS14": "CHP",
        "GXS15": "CHP",
        "GXS16": "CHP",
        "GXS17": "CHP",
        "GXS18": "CHP",
        "GXS19": "CHP",
        "GXS20": "CHP",
        "GXS21": "CHP",
        "GSX22": "CHP",
        "GSX23": "CHP",
        "GSX24": "CHP",
        "GSX25": "CHP",
        "GSX26": "CHP",
        "GSX27": "CHP",
        "GSX28": "CHP",
        "GSX29": "CHP",
        "GSX30": "CHP",
        "GSX31": "CHP",
        "GSX32": "CHP",
        "GSX33": "CHP",
        "GSX34": "CHP",
        "GSX35": "CHP",
        "GSX36": "CHP",
        "GSX37": "CHP"
      },
      areaCode: ["CF", "CH", "CHP"],
      maintainStatus: {},
      lastDayTotalTime: [],
      lastDayScrapRate: '0.00',
      statusRadio: [5, 2, 3, 6, 4, 0],
    }
  },
  computed: {},
  mounted() {
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
      //订阅的频道
      this.client.subscribe(MQTT_TOPIC_TOOL_LIFE, this.responseCallback, this.onFailed);
    },
    onFailed: function (msg) {
      console.log("MQ Failed:" + msg);
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
      const headers = {
        login: MQTT_USERNAME,
        password: MQTT_PASSWORD
      };
      this.client.debug = null
      this.client.connect(headers, this.onConnected, this.onFailed);
    },
    reconnect() {
      console.info('in reconnect function')
      const reconInv = setInterval(() => {
        this.client = Stomp.client(MQTT_SERVICE)
        const headers = {
          login: MQTT_USERNAME,
          password: MQTT_PASSWORD
        };
        this.client.debug = null
        this.client.connect(headers, () => {
          console.info('reconnected success')
          // 连接成功，清除定时器
          clearInterval(reconInv)
          this.onConnected()
        }, this.onFailed)
      }, 2000)
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
      this.getLastDayOee();
      this.getLastDayScrapRate();
    },
    getLastDayOee() {
      const currentTime = new Date();
      let tempTime = new Date(new Date(new Date().toLocaleDateString()).getTime() + 7 * 60 * 60 * 1000 + 30 * 60 * 1000)
      if (currentTime <= tempTime) {
        tempTime.setDate(tempTime.getDate() - 2)
      } else {
        tempTime.setDate(tempTime.getDate() - 1)
      }
      tempTime = this.$moment(tempTime).format('YYYY-MM-DD HH:mm:ss');
      getLastDayTotalTime(tempTime).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          responseData.data.forEach(item => {
            const oee = (item.totalTime * 1.0 / (item.runCount * 24 * 60 * 60) * 100).toFixed(2)
            this.lastDayTotalTime[item.area] = oee
          })
        }
      })
    },
    getLastDayScrapRate() {
      const currentTime = new Date();
      let tempTime = new Date(new Date(new Date().toLocaleDateString()).getTime() + 7 * 60 * 60 * 1000 + 30 * 60 * 1000)
      if (currentTime <= tempTime) {
        tempTime.setDate(tempTime.getDate() - 2)
      } else {
        tempTime.setDate(tempTime.getDate() - 1)
      }
      tempTime = this.$moment(tempTime).format('YYYY-MM-DD HH:mm:ss');
      getLastDayScrapCount(tempTime).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          const {scrap, out} = responseData.data
          if (out > 0) {
            this.lastDayScrapRate = (scrap * 1.0 / out * 100).toFixed(2)
          } else {
            this.lastDayScrapRate = '0.00'
          }

        }
      })
    },
  },
  watch: {},
  created() {
    this.timer = setInterval(() => {
      this.getToolMaintainStatus()
    }, 10000)
  },
  beforeUnmount() {
    clearInterval(this.timer);
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
