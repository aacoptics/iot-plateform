<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-calendar"></i> 镀膜机监控
        </el-breadcrumb-item>
        <el-breadcrumb-item>镀膜机{{ this.$route.query.position }}期</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div v-for="(singleMachineInfo, index) of coatingStatusInfo" :key="index"
           class="coating_div_type">
        <div :class="statusType[getMachineStatus(singleMachineInfo.status, singleMachineInfo.isOnline)]">
          <el-row style="margin-bottom: 10px">
            <el-col :span="12">
              <span style="width: 200px">机台号：{{ singleMachineInfo.name }}</span>
            </el-col>
            <el-col :span="12">
              <span>状态：{{ getMachineStatus(singleMachineInfo.status, singleMachineInfo.isOnline) }}</span>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <span>产能：</span>
              <span>{{ singleMachineInfo.totalNums }}</span>
            </el-col>
            <el-col :span="12">
              <span>累计锅次：</span>
              <span>{{ singleMachineInfo.runNo }}</span>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {getStatus} from "@/api/coating";

export default {
  name: "CoatingMachineMonitor",
  created() {
    this.getCoatingMachineInfo()
    this.timer = setInterval(() => {
      this.getCoatingMachineInfo()
    }, 10000)
  },
  computed: {
    coatingStatusInfo() {
      const pages = []
      this.coatingMachineInfoList.forEach((item) => {
        if (this.checkCoatingPhase(item.name)) {
          pages.push(item)
        }
      })
      return pages
    }
  },
  methods: {
    checkCoatingPhase(machineName) {
      if (this.$route.query.position === '1') {
        if (machineName.indexOf("A") === 0 ||
            machineName.indexOf("B") === 0 ||
            machineName.indexOf("C") === 0 ||
            machineName.indexOf("D") === 0 ||
            machineName.indexOf("E") === 0 ||
            machineName.indexOf("F") === 0 ||
            machineName.indexOf("L") === 0) {
          return true;
        } else {
          return false;
        }
      } else if (this.$route.query.position === '2') {
        if (machineName.indexOf("A") === -1 &&
            machineName.indexOf("B") === -1 &&
            machineName.indexOf("C") === -1 &&
            machineName.indexOf("D") === -1 &&
            machineName.indexOf("E") === -1 &&
            machineName.indexOf("F") === -1 &&
            machineName.indexOf("L") === -1) {
          return true;
        } else {
          return false;
        }
      }
    },
    getMachineStatus(statusCode, isOnline) {
      if (!isOnline) {
        return '设备离线'
      } else {
        if (statusCode === 1) {
          return '上料预警'
        } else if (statusCode === 0) {
          return '正常运行'
        } else {
          return '设备离线'
        }
      }
    },
    getCoatingMachineInfo() {
      getStatus().then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          this.coatingMachineInfoList = responseData.data.site;
        }
      })
    }
  },
  beforeUnmount() {
    clearInterval(this.timer);
  },
  data() {
    return {
      coatingMachineInfoList: [],
      statusType: {
        '设备离线': 'item_coating_offline',
        '正常运行': 'item_coating_running',
        '上料预警': 'item_coating_alarm'
      }
    }
  }
};
</script>
