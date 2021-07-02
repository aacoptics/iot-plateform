<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-calendar"></i> 注塑机监控
        </el-breadcrumb-item>
        <el-breadcrumb-item>新能源{{ this.$route.query.position }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <el-row v-for="(page, index) of monitPages" :key="index" style="margin-bottom: 20px">
        <el-col :span="4" v-for="(item, innerIndex) of page" :key="item.monitMcId" :offset="innerIndex > 0 ? 2 : 1">
          <el-card :body-style="{ padding: '0px', height:'80px'}"
                   shadow="hover"
                   style="width: 230px;height: 85px;border:1px solid cornflowerblue;background-color: #f0f0f0;"
                   @click="onCardClick(index, innerIndex)">
            <el-row>
              <el-col :span="17">
                <div>
                  <p style="font-weight: bold;text-align: center">{{ item.monitMcName }}</p>
                  <p style="margin-top: 5px">{{ item.condMoldFileName }}</p>
                  <p v-if="checkMachineStatus(item.monitStatus)">
                    <span style="margin-right: 10px">{{ item.monitCycleCount }}</span>
                    <span>{{ item.monitCycle }}秒</span>
                  </p>
                  <p v-if="checkMachineStatus(item.monitStatus)">
                    <span style="margin-right: 10px">{{ item.monitGoodCount }}</span>
                    <span>{{ item.monitDateTime }}</span>
                  </p>
                </div>
              </el-col>
              <el-col :span="7">
                <div :class="getStatusType(item.monitStatus)">{{ getStatusName(item.monitStatus) }}
                </div>
              </el-col>
            </el-row>
          </el-card>
        </el-col>
      </el-row>

      <el-dialog
          title="注塑机详细"
          v-model="dialogVisible"
          width="98%"
          @opened="showPieChart">
        <el-row style="border: 1px solid #2d8cf0">
          <el-col :span="12" style="border-right: 1px solid #2d8cf0">
            <el-row>
              <div style="font-weight: bold;color: white;background-color: #2d8cf0;width: 100%">设备详细信息</div>
            </el-row>
            <el-row style="margin-bottom: 10px">
              <el-col :span="8">
                <img src="../../assets/img/FanucMachine.jpg" style="width: 100%;height: 100%"/></el-col>
              <el-col :span="10" style="font-weight: bold">
                <el-row style="margin-bottom: 10px">
                  <p>机器名：</p>
                  <p style="color: green">{{ this.dialogMachineName }}</p>
                </el-row>
                <el-row style="margin-bottom: 10px">
                  <p>当前状态：</p>
                  <p style="color: green">{{ this.status[this.fanucDialogData.monitData.Status] }}</p>
                </el-row>
                <el-row style="margin-bottom: 10px">
                  <p>模具文件名：</p>
                  <p style="color: green">{{ this.fanucDialogData.moldFileName }}</p>
                </el-row>
                <el-row style="margin-bottom: 10px">
                  <p>HR模式：</p>
                  <p style="color: green">{{ this.fanucDialogData.condData.cond_hr_mode }}</p>
                </el-row>
                <el-row style="margin-bottom: 10px">
                  <p>实时成型间隔时间(s)：</p>
                  <p style="color: green">{{ this.fanucDialogData.monitData.Cycle }}</p>
                </el-row>
                <el-row style="margin-bottom: 10px">
                  <p>最后一次成型时间(s)：</p>
                  <p style="color: green">{{ this.fanucDialogData.monitData.Time }}</p>
                </el-row>
                <el-row style="margin-bottom: 10px">
                  <p>最大射出速度(mm/s)：</p>
                  <p style="color: green">{{ this.fanucDialogData.condData.cond_inj_speed1 }}</p>
                </el-row>
                <el-row style="margin-bottom: 10px">
                  <p>最大射出压力(kgf/cm2)：</p>
                  <p style="color: green">{{ this.fanucDialogData.condData.cond_max_inj_pres }}</p>
                </el-row>
                <el-row style="margin-bottom: 10px">
                  <p>保压切换模式：</p>
                  <p style="color: green">{{ this.fanucDialogData.condData.cond_trans_mode }}</p>
                </el-row>
              </el-col>
              <el-col :span="6" style="justify-content: flex-end;">
                <el-row style="font-weight: bold;font-size: large">
                  <p>成型次数</p>
                </el-row>
                <el-row style="font-family: 'led regular';font-size: xxx-large;color: green">
                  <p>{{ this.fanucDialogData.monitData.CycleCount }}</p>
                </el-row>
                <el-row style="font-weight: bold;font-size: large">
                  <p>良品数</p>
                </el-row>
                <el-row style="font-family: 'led regular';font-size: xxx-large;color: green">
                  <p>{{ this.fanucDialogData.monitData.GoodCount }}</p>
                </el-row>
                <el-row style="font-weight: bold;font-size: large">
                  <p>良率（%）</p>
                </el-row>
                <el-row style="font-family: 'led regular';font-size: xxx-large;color: green">
                  <p>{{
                      Math.round(this.fanucDialogData.monitData.GoodCount / this.fanucDialogData.monitData.CycleCount * 10000) / 100.00
                    }}%</p>
                </el-row>
              </el-col>
            </el-row>
            <el-row>
              <div style="font-weight: bold;color: white;background-color: #2d8cf0;width: 100%">设备状态</div>
            </el-row>
            <el-row>
              <div id='pieChart'
                   style="width: 98%;
                     height: 300px;
                     background-color: white;
                     margin-top: 10px">
              </div>
            </el-row>
          </el-col>
          <el-col :span="12">
            <el-tabs type="border-card">
              <el-tab-pane label="成型条件">
                <div class="block">
                  <el-date-picker
                      v-model="dateTimePickerValue"
                      type="datetimerange"
                      :shortcuts="shortcuts"
                      range-separator="至"
                      start-placeholder="开始日期"
                      end-placeholder="结束日期"
                      size="small">
                  </el-date-picker>
                  <el-button type="primary" icon="el-icon-search" size="small" style="margin-left: 10px">查询</el-button>
                  <el-button type="success" icon="el-icon-download" size="small">导出</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane label="监控数据">
                <div class="block">
                  <el-date-picker
                      v-model="dateTimePickerValue"
                      type="datetimerange"
                      :shortcuts="shortcuts"
                      range-separator="至"
                      start-placeholder="开始日期"
                      end-placeholder="结束日期"
                      size="small">
                  </el-date-picker>
                  <el-button type="primary" icon="el-icon-search" size="small" style="margin-left: 10px">查询</el-button>
                  <el-button type="success" icon="el-icon-download" size="small">导出</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane label="报警履历">
                <div class="block">
                  <el-date-picker
                      v-model="dateTimePickerValue"
                      type="datetimerange"
                      :shortcuts="shortcuts"
                      range-separator="至"
                      start-placeholder="开始日期"
                      end-placeholder="结束日期"
                      size="small">
                  </el-date-picker>
                  <el-button type="primary" icon="el-icon-search" size="small" style="margin-left: 10px">查询</el-button>
                  <el-button type="success" icon="el-icon-download" size="small">导出</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane label="数据分析">数据分析</el-tab-pane>
            </el-tabs>
          </el-col>
        </el-row>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "FanucMonitor",
  computed: {
    monitPages() {
      const pages = []
      this.fanucMonitorInfo.forEach((item, index) => {
        const page = Math.floor(index / 4)//4代表4条为一行，随意更改
        if (!pages[page]) {
          pages[page] = []
        }
        pages[page].push(item)
      })
      return pages
    }
  },
  methods: {
    checkMachineStatus(status) {
      return status !== '-1';
    },
    onCardClick(idx, innerIdx) {
      this.dialogMachineName = this.monitPages[idx][innerIdx].monitMcName
      this.dialogVisible = true
    },
    getStatusType(status) {
      return Object.prototype.hasOwnProperty.call(this.statusType, status) ? this.statusType[status] : "item_otherStatus";
    },
    getStatusName(status) {
      return Object.prototype.hasOwnProperty.call(this.status, status) ? this.status[status] : "其他";
    },

    showPieChart() {
      const chartDom = document.getElementById('pieChart');
      const myChart = echarts.init(chartDom);
      let option;

      option = {
        tooltip: {
          trigger: 'item',
          formatter: "{a} <br/>{b} : {c} %"

        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name: '状态',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '28',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: this.getMoldData()
          }
        ]
      };

      option && myChart.setOption(option);
    },
    getMoldData() {
      const moldData = [];
      const mold_automate = this.fanucDialogData.moldData.mold_automate.replace('%', '').trim()
      const mold_wait = this.fanucDialogData.moldData.mold_wait.replace('%', '').trim()
      const mold_manual = this.fanucDialogData.moldData.mold_manual.replace('%', '').trim()
      const mold_alarm = this.fanucDialogData.moldData.mold_alarm.replace('%', '').trim()
      const mold_complete = this.fanucDialogData.moldData.mold_complete.replace('%', '').trim()
      const mold_shutdown = this.fanucDialogData.moldData.mold_shutdown.replace('%', '').trim()
      const mold_other = (100.00 - mold_automate - mold_wait - mold_manual - mold_alarm - mold_complete - mold_shutdown).toFixed(2)
      moldData.push({value: mold_automate, name: '自动运转'})
      moldData.push({value: mold_wait, name: '运转待机'})
      moldData.push({value: mold_manual, name: '手动运转'})
      moldData.push({value: mold_alarm, name: '报警'})
      moldData.push({value: mold_complete, name: '生产完成'})
      moldData.push({value: mold_shutdown, name: '停机'})
      moldData.push({value: mold_other, name: '其他'})
      return moldData;
    }
  },
  data() {
    return {
      shortcuts: [{
        text: '最近一周',
        value: (() => {
          const end = new Date();
          const start = new Date();
          start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
          return [start, end]
        })()
      }, {
        text: '最近一个月',
        value: (() => {
          const end = new Date();
          const start = new Date();
          start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
          return [start, end]
        })()
      }, {
        text: '最近三个月',
        value: (() => {
          const end = new Date();
          const start = new Date();
          start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
          return [start, end]
        })()
      }],
      dateTimePickerValue: '',
      dialogMachineName: '4FP02',
      dialogVisible: false,
      fanucMonitorInfo: [
        {
          "monitMcName": "4FM01",
          "monitStatus": "-1",
          "condMoldFileName": null,
          "monitDateTime": null,
          "monitCycleCount": null,
          "monitGoodCount": null,
          "monitCycle": null
        },
        {
          "monitMcName": "4FM02",
          "monitStatus": "-1",
          "condMoldFileName": null,
          "monitDateTime": null,
          "monitCycleCount": null,
          "monitGoodCount": null,
          "monitCycle": null
        },
        {
          "monitMcName": "4FM03",
          "monitStatus": "-1",
          "condMoldFileName": null,
          "monitDateTime": null,
          "monitCycleCount": null,
          "monitGoodCount": null,
          "monitCycle": null
        },
        {
          "monitMcName": "4FM04",
          "monitStatus": "-1",
          "condMoldFileName": null,
          "monitDateTime": null,
          "monitCycleCount": null,
          "monitGoodCount": null,
          "monitCycle": null
        },
        {
          "monitMcName": "4FM05",
          "monitStatus": "-1",
          "condMoldFileName": null,
          "monitDateTime": null,
          "monitCycleCount": null,
          "monitGoodCount": null,
          "monitCycle": null
        },
        {
          "monitMcName": "4FM06",
          "monitStatus": "-1",
          "condMoldFileName": null,
          "monitDateTime": null,
          "monitCycleCount": null,
          "monitGoodCount": null,
          "monitCycle": null
        },
        {
          "monitMcName": "4FM07",
          "monitStatus": "-1",
          "condMoldFileName": null,
          "monitDateTime": null,
          "monitCycleCount": null,
          "monitGoodCount": null,
          "monitCycle": null
        },
        {
          "monitMcName": "4FM08",
          "monitStatus": "-1",
          "condMoldFileName": null,
          "monitDateTime": null,
          "monitCycleCount": null,
          "monitGoodCount": null,
          "monitCycle": null
        },
        {
          "monitMcName": "4FM09",
          "monitStatus": "17",
          "condMoldFileName": "165190A01-N3-P1",
          "monitDateTime": "09:40 ",
          "monitCycleCount": "3641368",
          "monitGoodCount": "43382",
          "monitCycle": "23.62"
        },
        {
          "monitMcName": "4FM10",
          "monitStatus": "02",
          "condMoldFileName": "135137A01-N3-P3",
          "monitDateTime": "16:44 ",
          "monitCycleCount": "2218054",
          "monitGoodCount": "237778",
          "monitCycle": "21.89"
        },
        {
          "monitMcName": "4FM11",
          "monitStatus": "02",
          "condMoldFileName": "134010A02-N5A-P1",
          "monitDateTime": "16:44 ",
          "monitCycleCount": "2375596",
          "monitGoodCount": "956420",
          "monitCycle": "19.52"
        },
        {
          "monitMcName": "4FM12",
          "monitStatus": "00",
          "condMoldFileName": "085086A01-N5-P2",
          "monitDateTime": "16:33 ",
          "monitCycleCount": "3901349",
          "monitGoodCount": "1167888",
          "monitCycle": "5.91"
        },
        {
          "monitMcName": "4FM13",
          "monitStatus": "-1",
          "condMoldFileName": null,
          "monitDateTime": null,
          "monitCycleCount": null,
          "monitGoodCount": null,
          "monitCycle": null
        },
        {
          "monitMcName": "4FM14",
          "monitStatus": "-1",
          "condMoldFileName": null,
          "monitDateTime": null,
          "monitCycleCount": null,
          "monitGoodCount": null,
          "monitCycle": null
        },
        {
          "monitMcName": "4FM15",
          "monitStatus": "-1",
          "condMoldFileName": null,
          "monitDateTime": null,
          "monitCycleCount": null,
          "monitGoodCount": null,
          "monitCycle": null
        },
        {
          "monitMcName": "4FM16",
          "monitStatus": "-1",
          "condMoldFileName": null,
          "monitDateTime": null,
          "monitCycleCount": null,
          "monitGoodCount": null,
          "monitCycle": null
        },
        {
          "monitMcName": "4FN01",
          "monitStatus": "17",
          "condMoldFileName": "085229-N1-P2",
          "monitDateTime": "13:50 ",
          "monitCycleCount": "390542",
          "monitGoodCount": "373187",
          "monitCycle": "25.34"
        },
        {
          "monitMcName": "4FN02",
          "monitStatus": "02",
          "condMoldFileName": "505279-N2-P1",
          "monitDateTime": "16:44 ",
          "monitCycleCount": "346829",
          "monitGoodCount": "29215",
          "monitCycle": "22.15"
        },
        {
          "monitMcName": "4FN03",
          "monitStatus": "17",
          "condMoldFileName": "084065-N1-P2",
          "monitDateTime": "03:34 ",
          "monitCycleCount": "640390",
          "monitGoodCount": "623006",
          "monitCycle": "20.56"
        },
        {
          "monitMcName": "4FN04",
          "monitStatus": "02",
          "condMoldFileName": "085229-N1-P4",
          "monitDateTime": "16:44 ",
          "monitCycleCount": "394854",
          "monitGoodCount": "377502",
          "monitCycle": "20.87"
        },
        {
          "monitMcName": "4FN05",
          "monitStatus": "02",
          "condMoldFileName": "165190A01-N1-P1",
          "monitDateTime": "16:44 ",
          "monitCycleCount": "302346",
          "monitGoodCount": "281058",
          "monitCycle": "29.03"
        },
        {
          "monitMcName": "4FN06",
          "monitStatus": "02",
          "condMoldFileName": "085074A01-N6B-P4",
          "monitDateTime": "16:44 ",
          "monitCycleCount": "1065494",
          "monitGoodCount": "1048111",
          "monitCycle": "19.02"
        },
        {
          "monitMcName": "4FN07",
          "monitStatus": "17",
          "condMoldFileName": "1086121-N1-P3",
          "monitDateTime": "00:31 ",
          "monitCycleCount": "569294",
          "monitGoodCount": "551935",
          "monitCycle": "18.17"
        },
        {
          "monitMcName": "4FN08",
          "monitStatus": "17",
          "condMoldFileName": "486030A01-N27-P2",
          "monitDateTime": "23:36 ",
          "monitCycleCount": "825712",
          "monitGoodCount": "689537",
          "monitCycle": "22.57"
        },
        {
          "monitMcName": "4FN09",
          "monitStatus": "17",
          "condMoldFileName": "508003-N1-P8",
          "monitDateTime": "19:39 ",
          "monitCycleCount": "793657",
          "monitGoodCount": "23524",
          "monitCycle": "39.16"
        },
        {
          "monitMcName": "4FN10",
          "monitStatus": "02",
          "condMoldFileName": "134010A02-N6A-P2",
          "monitDateTime": "16:44 ",
          "monitCycleCount": "945831",
          "monitGoodCount": "572651",
          "monitCycle": "18.87"
        },
        {
          "monitMcName": "4FN11",
          "monitStatus": "02",
          "condMoldFileName": "134010A02-N4A-P3",
          "monitDateTime": "16:44 ",
          "monitCycleCount": "892270",
          "monitGoodCount": "874911",
          "monitCycle": "18.99"
        },
        {
          "monitMcName": "4FN12",
          "monitStatus": "17",
          "condMoldFileName": "085074A01-N3B-P3",
          "monitDateTime": "03:53 ",
          "monitCycleCount": "796971",
          "monitGoodCount": "779614",
          "monitCycle": "20.27"
        },
        {
          "monitMcName": "4FN13",
          "monitStatus": "17",
          "condMoldFileName": "053019-N1-P1",
          "monitDateTime": "14:50 ",
          "monitCycleCount": "2135690",
          "monitGoodCount": "321723",
          "monitCycle": "23.93"
        },
        {
          "monitMcName": "4FN14",
          "monitStatus": "-1",
          "condMoldFileName": null,
          "monitDateTime": null,
          "monitCycleCount": null,
          "monitGoodCount": null,
          "monitCycle": null
        },
        {
          "monitMcName": "4FN21",
          "monitStatus": "-1",
          "condMoldFileName": null,
          "monitDateTime": null,
          "monitCycleCount": null,
          "monitGoodCount": null,
          "monitCycle": null
        },
        {
          "monitMcName": "4FP01",
          "monitStatus": "17",
          "condMoldFileName": "1086085-N1-P4",
          "monitDateTime": "04:03 ",
          "monitCycleCount": "360681",
          "monitGoodCount": "343317",
          "monitCycle": "22.95"
        },
        {
          "monitMcName": "4FP02",
          "monitStatus": "02",
          "condMoldFileName": "486030A01-N22-P5",
          "monitDateTime": "16:44 ",
          "monitCycleCount": "653892",
          "monitGoodCount": "636534",
          "monitCycle": "26.65"
        },
        {
          "monitMcName": "4FP03",
          "monitStatus": "17",
          "condMoldFileName": "505279-N1-P4",
          "monitDateTime": "10:24 ",
          "monitCycleCount": "459064",
          "monitGoodCount": "441677",
          "monitCycle": "19.70"
        },
        {
          "monitMcName": "4FP04",
          "monitStatus": "17",
          "condMoldFileName": "165270-N1-P1",
          "monitDateTime": "05:22 ",
          "monitCycleCount": "454198",
          "monitGoodCount": "34620",
          "monitCycle": "17.08"
        },
        {
          "monitMcName": "4FP05",
          "monitStatus": "00",
          "condMoldFileName": "085086A01-N1C-P2",
          "monitDateTime": "10:38 ",
          "monitCycleCount": "550188",
          "monitGoodCount": "497348",
          "monitCycle": "18.15"
        },
        {
          "monitMcName": "4FP06",
          "monitStatus": "17",
          "condMoldFileName": "134010A02-N13-P2",
          "monitDateTime": "09:03 ",
          "monitCycleCount": "819829",
          "monitGoodCount": "802472",
          "monitCycle": "22.98"
        },
        {
          "monitMcName": "4FP07",
          "monitStatus": "02",
          "condMoldFileName": "085223-N1-P4",
          "monitDateTime": "16:44 ",
          "monitCycleCount": "526105",
          "monitGoodCount": "379057",
          "monitCycle": "23.27"
        },
        {
          "monitMcName": "4FP08",
          "monitStatus": "02",
          "condMoldFileName": "126103-N1-P5",
          "monitDateTime": "16:44 ",
          "monitCycleCount": "265099",
          "monitGoodCount": "247708",
          "monitCycle": "21.10"
        },
        {
          "monitMcName": "4FP09",
          "monitStatus": "17",
          "condMoldFileName": "085086A01-N6-P2",
          "monitDateTime": "15:14 ",
          "monitCycleCount": "910260",
          "monitGoodCount": "892903",
          "monitCycle": "19.88"
        },
        {
          "monitMcName": "4FP10",
          "monitStatus": "02",
          "condMoldFileName": "486030A01-N22-P4",
          "monitDateTime": "16:44 ",
          "monitCycleCount": "874939",
          "monitGoodCount": "857574",
          "monitCycle": "23.27"
        },
        {
          "monitMcName": "4FP11",
          "monitStatus": "17",
          "condMoldFileName": "445209-N1-P2",
          "monitDateTime": "15:19 ",
          "monitCycleCount": "297166",
          "monitGoodCount": "279715",
          "monitCycle": "17.54"
        },
        {
          "monitMcName": "4FP12",
          "monitStatus": "02",
          "condMoldFileName": "486030A01-N20-P5",
          "monitDateTime": "16:44 ",
          "monitCycleCount": "879414",
          "monitGoodCount": "862060",
          "monitCycle": "25.30"
        },
        {
          "monitMcName": "4FP13",
          "monitStatus": "17",
          "condMoldFileName": "085084A02-N4A-P5",
          "monitDateTime": "16:59 ",
          "monitCycleCount": "766529",
          "monitGoodCount": "749253",
          "monitCycle": "22.36"
        },
        {
          "monitMcName": "4FP14",
          "monitStatus": "17",
          "condMoldFileName": "165166A01-N2A-P3",
          "monitDateTime": "11:31 ",
          "monitCycleCount": "782223",
          "monitGoodCount": "764862",
          "monitCycle": "20.21"
        },
        {
          "monitMcName": "4FP15",
          "monitStatus": "17",
          "condMoldFileName": "165166A01-N2A-P4",
          "monitDateTime": "14:04 ",
          "monitCycleCount": "748676",
          "monitGoodCount": "731323",
          "monitCycle": "18.80"
        }
      ],
      fanucDialogData: {
        "monitData": {
          "ProductName": null,
          "CycleCount": "653987",
          "BatchName": null,
          "Date": "210702",
          "Time": "172644",
          "Status": "02",
          "Cycle": "26.64",
          "InjTime": "0.241",
          "RecovTime": "7.14",
          "M_Cushion": "1.78",
          "ExtrdPos": "13.00",
          "PeakPrs": "88.3",
          "ShotCount": "636629",
          "GoodCount": "636629",
          "V_P_Pos": "2.01",
          "Mold1": "",
          "Mold2": "",
          "Nozzle": "270.1",
          "Nozzle2": "",
          "Barrel1": "275.0",
          "Barrel2": "270.0",
          "Barrel3": "265.0",
          "Barrel4": "",
          "FeedTh": "50.0",
          "ExtrdStart": "4.10",
          "ExtrdTorq": "9.57",
          "Mold3": "",
          "Mold4": "",
          "LockupTim": "0.12",
          "PickupTim": "2.11",
          "ResidenceT": "9.19",
          "EjeDevAvTrq": "0.1",
          "Mold5": "",
          "Mold6": "",
          "Mold7": "",
          "Mold8": "",
          "InjStartPos": "13.00",
          "ScrewRevolution": "0.50",
          "PeakT": "0.247",
          "PeakPos": "1.80",
          "EjeDevStTrq": "-1.2",
          "CloseTime": "1.32",
          "V_P_Prs": "83.0",
          "InjPres": "-0.5",
          "V_P_Adj": "0.00",
          "Flwpeak": "1.43",
          "Backflw": "0.67"
        },
        "condData": {
          "cond_mold_file_name": "486030A01-N22-P5    ",
          "cond_mold_id": "0",
          "cond_purge_pressure": "0.0",
          "cond_purge_rotation": "100",
          "cond_auto_die_h_force": "220",
          "cond_eject_start_mode_mold": "开模结束",
          "cond_eject_start_pos_mold": "212.60",
          "cond_inj_step": "1",
          "cond_inj_speed1": "50.0",
          "cond_inj_speed2": "0.0",
          "cond_inj_speed3": "0.0",
          "cond_inj_speed4": "0.0",
          "cond_inj_speed5": "0.0",
          "cond_inj_speed6": "0.0",
          "cond_inj_speed7": "0.0",
          "cond_inj_speed8": "0.0",
          "cond_inj_speed9": "0.0",
          "cond_inj_speed10": "0.0",
          "cond_max_inject_press1": "0.0",
          "cond_max_inject_press2": "150.0",
          "cond_max_inject_press3": "150.0",
          "cond_max_inject_press4": "150.0",
          "cond_max_inject_press5": "150.0",
          "cond_max_inject_press6": "150.0",
          "cond_max_inject_press7": "150.0",
          "cond_max_inject_press8": "150.0",
          "cond_max_inject_press9": "150.0",
          "cond_max_inject_press10": "150.0",
          "cond_inj_switch_pos1": "0.00",
          "cond_inj_switch_pos2": "0.00",
          "cond_inj_switch_pos3": "0.00",
          "cond_inj_switch_pos4": "0.00",
          "cond_inj_switch_pos5": "0.00",
          "cond_inj_switch_pos6": "0.00",
          "cond_inj_switch_pos7": "0.00",
          "cond_inj_switch_pos8": "0.00",
          "cond_inj_switch_pos9": "0.00",
          "cond_inject_mode": "速度优先",
          "cond_trans_mode": "压力切换",
          "cond_trans_position": "4.41",
          "cond_trans_pressure": "83.0",
          "cond_trans_press_step": "1",
          "cond_trans_cav_nzl_prs": "0.0",
          "cond_trans_cavity_step": "1",
          "cond_trans_nozzle_prs": "0.0",
          "cond_trans_nozzle_step": "1",
          "cond_sgnl_transf_step": "1",
          "cond_pack_step": "6",
          "cond_pack_pres1": "63.0",
          "cond_pack_pres2": "58.0",
          "cond_pack_pres3": "53.0",
          "cond_pack_pres4": "48.0",
          "cond_pack_pres5": "43.0",
          "cond_pack_pres6": "50.0",
          "cond_bef_ext_pres": "10.0",
          "cond_pack_time1": "0.100",
          "cond_pack_time2": "0.100",
          "cond_pack_time3": "0.200",
          "cond_pack_time4": "0.300",
          "cond_pack_time5": "2.000",
          "cond_pack_time6": "2.000",
          "cond_bef_ext_time": "5.000",
          "cond_max_inj_pres": "150.0",
          "cond_max_inj_time": "2.000",
          "cond_max_pack_vel": "30.0",
          "cond_hr_mode": "标准",
          "cond_acceleration": "C",
          "cond_accel_time": "40",
          "cond_accel_mode": "加速度恒定",
          "cond_extrd_sw": "ON",
          "cond_extrd_step": "1",
          "cond_back_pres1": "6.0",
          "cond_back_pres2": "6.0",
          "cond_back_pres3": "6.0",
          "cond_back_pres4": "6.0",
          "cond_back_pres5": "6.0",
          "cond_back_pres6": "6.0",
          "cond_screw_rotate1": "30",
          "cond_screw_rotate2": "30",
          "cond_screw_rotate3": "30",
          "cond_screw_rotate4": "30",
          "cond_screw_rotate5": "30",
          "cond_screw_rotate6": "30",
          "cond_extrd_sw_pos_1": "11.00",
          "cond_extrd_sw_pos_2": "11.00",
          "cond_extrd_sw_pos_3": "11.00",
          "cond_extrd_sw_pos_4": "11.00",
          "cond_extrd_sw_pos_5": "11.00",
          "cond_shot_size": "11.00",
          "cond_dcmp_dist": "2.00",
          "cond_dcmp_vel": "10.0",
          "cond_cool_time1": "17.00",
          "cond_cool_time2": "17",
          "cond_nzl1_hold_temp": "150.0",
          "cond_nzl2_hold_temp": "150.0",
          "cond_nzl_adapt_hold_temp": "150.0",
          "cond_brl1_hold_temp": "150.0",
          "cond_brl2_hold_temp": "150.0",
          "cond_brl3_hold_temp": "150.0",
          "cond_brl4_hold_temp": "150.0",
          "cond_brl5_hold_temp": "150.0",
          "cond_brl6_hold_temp": "150.0",
          "cond_nozzle1_high": "10.0",
          "cond_barrel1_high": "10.0",
          "cond_barrel2_high": "10.0",
          "cond_barrel3_high": "10.0",
          "cond_feed_throat_high": "10.0",
          "cond_mold1_high": "10.0",
          "cond_mold2_high": "10.0",
          "cond_mold3_high": "10.0",
          "cond_mold4_high": "10.0",
          "cond_nozzle1_set": "270.0",
          "cond_barrel1_set": "275.0",
          "cond_barrel2_set": "270.0",
          "cond_barrel3_set": "265.0",
          "cond_barrel4_set": "0.0",
          "cond_barrel5_set": "0.0",
          "cond_barrel6_set": "0.0",
          "cond_feed_throat_set": "50.0",
          "cond_mold1_set": "0.0",
          "cond_mold2_set": "0.0",
          "cond_mold3_set": "0.0",
          "cond_mold4_set": "0.0",
          "cond_nozzle1_low": "10.0",
          "cond_barrel1_low": "10.0",
          "cond_barrel2_low": "10.0",
          "cond_barrel3_low": "10.0",
          "cond_feed_throat_low": "10.0",
          "cond_mold1_low": "10.0",
          "cond_mold2_low": "10.0",
          "cond_mold3_low": "10.0",
          "cond_mold4_low": "10.0",
          "cond_close_limit_pos": "0.00",
          "cond_close_sw_pos_1": "180.00",
          "cond_close_sw_pos_2": "180.00",
          "cond_close_sw_pos_3": "180.00",
          "cond_mold_protect": "60.00",
          "cond_mold_touch_pos": "29.41",
          "cond_open_sw_pos_1": "60.00",
          "cond_open_sw_pos_2": "180.00",
          "cond_open_sw_pos_3": "212.60",
          "cond_open_sw_pos_4": "212.60",
          "cond_fully_open_pos": "212.60",
          "cond_eject_start_pos": "34.00",
          "cond_close_vel_1": "280.0",
          "cond_close_vel_2": "50.0",
          "cond_close_vel_3": "50.0",
          "cond_close_vel_4": "300.0",
          "cond_mold_touch_vel": "80.0",
          "cond_open_vel_1": "100.0",
          "cond_open_vel_2": "300.0",
          "cond_open_vel_3": "50.0",
          "cond_open_vel_4": "50.0",
          "cond_fully_open_vel": "280.0",
          "cond_close_step": "4",
          "cond_open_step": "3",
          "cond_mold_protect_1": "5",
          "cond_mold_protect_1_minus": "5",
          "cond_mold_protect_2": "100",
          "cond_mold_protect_2_minus": "100",
          "cond_protect_time_1": "1.00",
          "cond_protect_time_2": "0.00",
          "cond_eject_pattern1": "1段",
          "cond_eject_pulse": "1",
          "cond_eject_start_mode": "任意",
          "cond_eject_retract_pos": "40.00",
          "cond_eject_fully_advance": "47.22",
          "cond_eject_retract_vel": "50.0",
          "cond_eject_advance_vel": "40.0",
          "cond_eject_pattern2": "时间",
          "cond_eject_dwell_in_ret": "0.00",
          "cond_eject_dwell_in_adv": "0.00",
          "cond_proc_moni_item_5": "最小缓冲"
        },
        "moldData": {
          "monit_mc_id": 2,
          "monit_mc_name": "4FP02",
          "mold_start_time": "2021/7/2 8:00:05",
          "mold_end_time": "2021/7/2 17:12:57",
          "mold_amount": "338",
          "mold_good_amount": "338",
          "mold_automate": "27.38 %",
          "mold_wait": "0.02 %",
          "mold_manual": "9.33 %",
          "mold_alarm": "59.60 %",
          "mold_complete": "0.00 %",
          "mold_shutdown": "0.00 %",
          "mold_tolal": "100.00 %",
          "mold_batch_number": "0",
          "mold_container_number": "0",
          "mold_job_code_id": "0",
          "mold_job_code": "",
          "mold_job_code_name": "",
          "mold_job_type_id": "0",
          "mold_job_type": "",
          "mold_energy_per_good_shot": "27.1Wh",
          "mold_energy": "9.17KWh"
        },
        "moldFileName": "486030A01-N22-P5"
      }
      ,
      statusType: {
        '-1': 'item_disconnect',
        '00': 'item_manual',
        '01': 'item_wait',
        '02': 'item_automatic',
        '03': 'item_alarm',
        '17': 'item_hold',
        '50': 'item_semiAuto'
      },
      status: {
        '-1': '离线',
        '00': '手动运转',
        '01': '运转待机',
        '02': '自动运转',
        '03': '报警',
        '17': '低温保持',
        '50': '半自动'
      }

    }
  }
};
</script>
