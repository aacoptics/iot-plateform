<template>
  <div>
    <div class="container">
      <div class="toolbar" style="float:left;padding-top:10px;padding-left:15px;">
        <el-form :inline="true" :size="size" label-width="100px">
          <el-row>
            <el-form-item label="机台号" prop="machineName">
              <el-select v-model="formParam.machineName"
                         class="m-2"
                         placeholder="请选择机台号"
                         :size="size"
                         @change="getWaferIdArray">
                <el-option
                    v-for="item in machineNameArray"
                    :key="item.machineName"
                    :label="item.machineName"
                    :value="item.machineName"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="时间" prop="dateTimePicker">
              <el-date-picker
                  v-model="dateTimePickerValue"
                  type="datetimerange"
                  :shortcuts="shortcuts"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  :size="size"
                  @change="getWaferIdArray">
              </el-date-picker>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="OK模次号" prop="waferId">
              <el-select
                  v-model="okWaferIds"
                  multiple
                  collapse-tags
                  placeholder="请选择OK模次号"
                  :size="size"
                  :v-loading="selectLoading"
                  @change="okChangeSelect"
              >
                <el-checkbox v-model="okAllChecked" @change='okSelectAll'>全选</el-checkbox>
                <el-option
                    v-for="item in waferIdArray"
                    :key="item.waferId"
                    :label="item.waferId"
                    :value="item.waferId"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="NG模次号" prop="waferId">
              <el-select
                  v-model="ngWaferIds"
                  multiple
                  collapse-tags
                  placeholder="请选择NG模次号"
                  :size="size"
                  :v-loading="selectLoading"
                  @change="ngChangeSelect"
              >
                <el-checkbox v-model="ngAllChecked" @change='ngSelectAll'>全选</el-checkbox>
                <el-option
                    v-for="item in waferIdArray"
                    :key="item.waferId"
                    :label="item.waferId"
                    :value="item.waferId"
                />
              </el-select>
            </el-form-item>

            <el-form-item label="参数名" prop="paramName">
              <el-select
                  v-model="paramNames"
                  multiple
                  placeholder="请选择参数"
                  :size="size"
                  :v-loading="selectLoading"
              >
                <!--                <el-option-->
                <!--                    v-for="item in paramNameArray"-->
                <!--                    :key="item.paramName"-->
                <!--                    :label="item.paramName"-->
                <!--                    :value="item.paramName"-->
                <!--                />-->
                <el-option
                    v-for="item in paramNameArray"
                    :key="item"
                    :label="item"
                    :value="item"
                />
              </el-select>
            </el-form-item>

          </el-row>
          <el-row>
            <el-form-item label="对齐阶段" prop="recipePhase">
              <el-select
                  v-model="alignRecipePhase"
                  placeholder="请选择需要对齐的阶段"
                  :size="size"
                  :v-loading="selectLoading"
                  @change="okChangeSelect"
              >
                <el-option
                    v-for="item in allRecipePhase"
                    :key="item"
                    :label="item"
                    :value="item"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="对齐模次号" prop="waferId">
              <el-select
                  v-model="alignWaferId"
                  placeholder="请选择对齐的模次号"
                  :size="size"
                  :v-loading="selectLoading"
              >
                <el-option
                    v-for="item in allWaferIdsIncludeStatus"
                    :key="item.waferId"
                    :label="item.waferId"
                    :value="item.waferId"
                />
              </el-select>
            </el-form-item>
          </el-row>
        </el-form>

        <el-form :inline="true" :size="size">
          <el-form-item>
            <el-button icon="el-icon-search" type="primary"
                       :loading="selectLoading"
                       @click="drawAllChart()">查询
            </el-button>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-s-data" type="success"
                       :loading="selectLoading"
                       @click="alainAllChart">对齐
            </el-button>
          </el-form-item>
        </el-form>

        <!--        <el-row v-for="(val, key, index) in paramNames" :key="index">-->
        <!--          <div :id="val"-->
        <!--               style="margin-top: 10px;height: 600px; width: 1280px"></div>-->
        <!--        </el-row>-->
        <el-row>
          <div id="lineChart"
               style="margin-top: 10px;height: 600px; width: 1280px"></div>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>

import {getMachineName, getMoldParamValue, getWaferIds} from "@/api/wlg/moldingMachineParamData";
import * as echarts from 'echarts';

export default {
  name: "productionPlan",
  computed: {
    allWaferIdsIncludeStatus() {
      const res = []
      this.okWaferIds.forEach((item) => {
        res.push(
            {
              waferId: item,
              status: 'ok'
            }
        )
      })
      this.ngWaferIds.forEach((item) => {
        if (this.okWaferIds.indexOf(item) === -1)
          res.push(
              {
                waferId: item,
                status: 'ng'
              }
          )
      })
      return res;
    },
    allRecipePhase() {
      const res = []
      for (const val in this.paramValue) {
        this.paramValue[val].forEach((item) => {
          if (res.indexOf(item[7]) === -1 && item[7] !== 'recipePhase')
            res.push(item[7])
        })
      }
      return res
    },
    allParamValue() {
      let res = []
      let isFirst = true
      for (const val in this.paramValue) {
        if (!isFirst) {
          const tempValue = Object.assign([], this.paramValue[val])
          tempValue.shift()
          res = res.concat(tempValue)
        } else {
          res = res.concat(this.paramValue[val])
          isFirst = false
        }
      }
      return res
    }
  },
  data() {
    return {
      okAllChecked: false,
      ngAllChecked: false,
      alignRecipePhase: '',
      alignWaferId: '',
      size: 'small',
      machineNameArray: [],
      okWaferIds: [],
      ngWaferIds: [],
      waferIdArray: [],
      paramValue: {},
      paramNameArray: [
        "Customer_Input1_0",
        "customer_temperature_1",
        "customer_temperature_2",
        "customer_temperature_3",
        "customer_temperature_4",
        "customer_temperature_5",
        "customer_temperature_6",
        "customer_temperature_7",
        "customer_temperature_8",
        "exchange_heater_dutycycle_actual_0",
        "exchange_temp_actual_0",
        "exchange_temp_control_enabled_0",
        "exchange_temp_control_setpoint_actual_0",
        "intermediate_heater_dutycycle_actual_0",
        "intermediate_temp_actual_0",
        "intermediate_temp_control_enabled_0",
        "intermediate_temp_control_setpoint_actual_0",
        "lc_forming_valve_open_0",
        "lc_nitrogen_valve_open_0",
        "lc_pressure_actual_0",
        "lc_vacuum_valve_open_0",
        "lc_vent_valve_open_0",
        "lower_guard_temp_actual_0",
        "lower_mold_temp_actual_0",
        "lower_moldcore_section_dutycycle_actual_1",
        "lower_moldcore_section_dutycycle_actual_10",
        "lower_moldcore_section_dutycycle_actual_2",
        "lower_moldcore_section_dutycycle_actual_3",
        "lower_moldcore_section_dutycycle_actual_4",
        "lower_moldcore_section_dutycycle_actual_5",
        "lower_moldcore_section_dutycycle_actual_6",
        "lower_moldcore_section_dutycycle_actual_7",
        "lower_moldcore_section_dutycycle_actual_8",
        "lower_moldcore_section_dutycycle_actual_9",
        "lower_moldcore_section_temp_actual_1",
        "lower_moldcore_section_temp_actual_10",
        "lower_moldcore_section_temp_actual_2",
        "lower_moldcore_section_temp_actual_3",
        "lower_moldcore_section_temp_actual_4",
        "lower_moldcore_section_temp_actual_5",
        "lower_moldcore_section_temp_actual_6",
        "lower_moldcore_section_temp_actual_7",
        "lower_moldcore_section_temp_actual_8",
        "lower_moldcore_section_temp_actual_9",
        "lower_moldcore_temp_control_enabled_0",
        "lower_moldcore_temp_control_setpoint_0",
        "mc_forming_valve_open_0",
        "mc_nitrogen_valve_open_0",
        "mc_pressure_actual_0",
        "mc_pressure_control_enabled_0",
        "mc_pressure_control_setpoint_actual_0",
        "mc_pressure_control_type_0",
        "mc_proportional_valve_actual_0",
        "mc_vacuum_valve_open_0",
        "press_force_actual_0",
        "press_force_control_enabled_0",
        "press_force_control_setpoint_0",
        "press_force_no_deadweight_0",
        "press_force_raw_0",
        "press_position_actual_0",
        "sideforce_counterpressure_actual_0",
        "sideforce_lowerU_setpoint_actual_0",
        "sideforce_lowerV_setpoint_actual_0",
        "sideforce_lowerW_setpoint_actual_0",
        "sideforce_upperU_setpoint_actual_0",
        "sideforce_upperV_setpoint_actual_0",
        "sideforce_upperW_setpoint_actual_0",
        "upper_guard_temp_actual_0",
        "upper_mold_temp_actual_0",
        "upper_moldcore_section_dutycycle_actual_1",
        "upper_moldcore_section_dutycycle_actual_10",
        "upper_moldcore_section_dutycycle_actual_2",
        "upper_moldcore_section_dutycycle_actual_3",
        "upper_moldcore_section_dutycycle_actual_4",
        "upper_moldcore_section_dutycycle_actual_5",
        "upper_moldcore_section_dutycycle_actual_6",
        "upper_moldcore_section_dutycycle_actual_7",
        "upper_moldcore_section_dutycycle_actual_8",
        "upper_moldcore_section_dutycycle_actual_9",
        "upper_moldcore_section_temp_actual_1",
        "upper_moldcore_section_temp_actual_10",
        "upper_moldcore_section_temp_actual_2",
        "upper_moldcore_section_temp_actual_3",
        "upper_moldcore_section_temp_actual_4",
        "upper_moldcore_section_temp_actual_5",
        "upper_moldcore_section_temp_actual_6",
        "upper_moldcore_section_temp_actual_7",
        "upper_moldcore_section_temp_actual_8",
        "upper_moldcore_section_temp_actual_9",
        "upper_moldcore_temp_control_enabled_0",
        "upper_moldcore_temp_control_setpoint_0",
        "vacuumhead_heater_dutycycle_actual_0",
        "vacuumhead_temp_actual_0",
        "vacuumhead_temp_control_enabled_0",
        "vacuumhead_temp_control_setpoint_actual_0"
      ],
      //paramValueArray:[],
      selectLoading: false,
      formParam: {
        waferIds: []
      },
      dateTimePickerValue: [],
      paramNames: [],
      shortcuts: [{
        text: '最近一天',
        value: (() => {
          const end = new Date();
          const start = new Date();
          start.setTime(start.getTime() - 3600 * 1000 * 24);
          return [start, end]
        })()
      }],
    }
  },
  methods: {
    okSelectAll() {
      this.okWaferIds = []
      if (this.okAllChecked) {
        this.waferIdArray.map(item => {
          this.okWaferIds.push(item.waferId)
        })
      } else {
        this.okWaferIds = []
      }
    },
    okChangeSelect(val) {
      if (val.length === this.waferIdArray.length) {
        this.okAllChecked = true
      } else {
        this.okAllChecked = false
      }
    },

    ngSelectAll() {
      this.ngWaferIds = []
      if (this.ngAllChecked) {
        this.waferIdArray.map(item => {
          this.ngWaferIds.push(item.waferId)
        })
      } else {
        this.ngWaferIds = []
      }
    },
    ngChangeSelect(val) {
      if (val.length === this.waferIdArray.length) {
        this.ngAllChecked = true
      } else {
        this.ngAllChecked = false
      }
    },
    getMachineName() {
      getMachineName().then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          this.machineNameArray = responseData.data
        }
      })
    },

    getWaferIdArray() {
      this.selectLoading = true
      if (!this.formParam.machineName || this.formParam.machineName === "") {
        this.$message.error('请先选择机台号！')
        this.selectLoading = false
      }
      const startTime = this.$moment(this.dateTimePickerValue[0]).format('YYYY-MM-DD HH:mm:ss');
      const endTime = this.$moment(this.dateTimePickerValue[1]).format('YYYY-MM-DD HH:mm:ss');
      getWaferIds(this.formParam.machineName, startTime, endTime).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          this.waferIdArray = responseData.data
          this.selectLoading = false
        }
      }).catch((err) => {
        this.$message.error(err.message)
        this.selectLoading = false
      })
    },

    // getParamNames(val) {
    //   // this.selectLoading = true
    //   // getMoldParamName(this.formParam).then((response) => {
    //   //   const responseData = response.data
    //   //   if (responseData.code === '000000') {
    //   //     this.paramNameArray = responseData.data
    //   //     this.selectLoading = false
    //   //   }
    //   // }).catch((err) => {
    //   //   this.$message.error(err.message)
    //   //   this.selectLoading = false
    //   // })
    //   //this.changeSelect(val)
    // },

    getParamValue(paramName) {
      this.selectLoading = true
      this.formParam.paramName = paramName
      const waferIdRes = []
      this.allWaferIdsIncludeStatus.forEach((item) => {
        waferIdRes.push(item.waferId)
      })
      this.formParam.waferIds = waferIdRes
      getMoldParamValue(this.formParam).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          this.paramValue[paramName] = responseData.data
          //this.paramValueArray = responseData.data
          this.drawLineChart(0)
          console.log(this.allParamValue)
          this.selectLoading = false
        }
      }).catch((err) => {
        this.$message.error(err.message)
        this.selectLoading = false
      })
    },

    drawAllChart() {
      this.paramValue = {}
      this.paramNames.forEach(item => {
        this.getParamValue(item)
      })

    },

    alainAllChart() {
      if (!this.alignRecipePhase || this.alignRecipePhase === '' ||
          !this.alignWaferId || this.alignWaferId === '') {
        this.$message.error('请选择对齐的模次号和阶段！')
        return
      }
      const minStampArray = this.alignParamValue()
      let isFirst = true
      this.paramNames.forEach(item => {
        if(isFirst){
          this.drawLineChart(minStampArray[item])
          isFirst = false
        }
      })
    },

    alignParamValue() {
      const alignShiftStamp = {}
      const waferMinStamp = {}
      const waferMinStampArray = {}
      for (const val in this.paramValue) {
        this.paramValue[val].forEach((item) => {
          if (item[7] !== 'recipePhase') {
            if (!alignShiftStamp[val])
              alignShiftStamp[val] = {}
            if (!alignShiftStamp[val][item[7]])
              alignShiftStamp[val][item[7]] = {}
            if (!alignShiftStamp[val][item[7]][item[1]] || alignShiftStamp[val][item[7]][item[1]] > item[5])
              alignShiftStamp[val][item[7]][item[1]] = item[5]
          }
        })
      }

      for (const val in this.paramValue) {
        this.paramValue[val].forEach((item) => {
          if (item[7] !== 'recipePhase') {
            if (item[1] !== this.alignWaferId) {
              item[5] = alignShiftStamp[val][this.alignRecipePhase][this.alignWaferId] - alignShiftStamp[val][this.alignRecipePhase][item[1]] + item[5]
            }
            if (!waferMinStamp[val]) {
              waferMinStamp[val] = {}
            }
            if (!waferMinStamp[val][item[1]] || waferMinStamp[val][item[1]] > item[5]) {
              waferMinStamp[val][item[1]] = item[5]
            }
          }
        })
      }
      for (const val in waferMinStamp) {
        for (const waferVal in waferMinStamp[val]) {
          if (!waferMinStampArray[val] || waferMinStampArray[val] < waferMinStamp[val][waferVal])
            waferMinStampArray[val] = waferMinStamp[val][waferVal]
        }
      }
      return waferMinStampArray
    },

    // drawLineChart(paramName){
    //   const chartDom = document.getElementById(paramName);
    //   const myChart = echarts.init(chartDom);
    //   let option;
    //
    //   run(this.paramValueArray)
    //
    //
    //   function run(_rawData) {
    //     option = {
    //       dataset: [
    //         {
    //           id: 'dataset_raw',
    //           source: _rawData
    //         },
    //         {
    //           id: 'dataset_since_1950_of_germany',
    //           fromDatasetId: 'dataset_raw',
    //           transform: {
    //             type: 'filter',
    //             config: {
    //               and: [
    //                 { dimension: 'plcTimeStamp', gte: 0 },
    //                 { dimension: 'waferId', '=': '3816' }
    //               ]
    //             }
    //           }
    //         },
    //         {
    //           id: 'dataset_since_1950_of_france',
    //           fromDatasetId: 'dataset_raw',
    //           transform: {
    //             type: 'filter',
    //             config: {
    //               and: [
    //                 { dimension: 'plcTimeStamp', gte: 0 },
    //                 { dimension: 'waferId', '=': '3817' }
    //               ]
    //             }
    //           }
    //         }
    //       ],
    //       title: {
    //         text: paramName
    //       },
    //       tooltip: {
    //         trigger: 'axis'
    //       },
    //       xAxis: {
    //         type: 'category',
    //         nameLocation: 'middle'
    //       },
    //       yAxis: {
    //         name: '值'
    //       },
    //       series: [
    //         {
    //           type: 'line',
    //           datasetId: 'dataset_since_1950_of_germany',
    //           showSymbol: false,
    //           encode: {
    //             x: 'plcTimeStamp',
    //             y: 'paramValue',
    //             itemName: 'plcTimeStamp',
    //             tooltip: ['paramValue']
    //           }
    //         },
    //         {
    //           type: 'line',
    //           datasetId: 'dataset_since_1950_of_france',
    //           showSymbol: false,
    //           encode: {
    //             x: 'plcTimeStamp',
    //             y: 'paramValue',
    //             itemName: 'plcTimeStamp',
    //             tooltip: ['paramValue']
    //           }
    //         }
    //       ]
    //     };
    //     myChart.setOption(option);
    //   }
    //
    //   option && myChart.setOption(option);
    // }

    drawLineChart(startStamp) {
      const chartDom = document.getElementById('lineChart');
      const myChart = echarts.init(chartDom);
      let option;

      run(this.allParamValue, this.allWaferIdsIncludeStatus, this.paramNames)


      function run(_rawData, waferIds, paramsInfo) {

        const waferIdRes = [];
        const datasetWithFilters = [];
        const seriesList = [];
        const yAxisList = [];
        const yAxisIndexList = [];
        echarts.util.each(paramsInfo, function (item) {
          yAxisIndexList.push(item + '_value')
          const i = yAxisIndexList.indexOf(item + '_value')
          if (i === 0)
            yAxisList.push({
              type: 'value',
              name: '值',
              yAxisIndex: i,
              alignTicks: true,
              scale: true,
              axisLine: {
                show: true,
                lineStyle: {
                  color: '#5470C6'
                }
              },
              nameTextStyle: {
                fontSize: '16px'
              }
            })
          else {
            yAxisList.push({
              name: '值',
              yAxisIndex: i,
              alignTicks: true,
              scale: true,
              axisLine: {
                show: true,
                lineStyle: {
                  color: '#5470C6'
                }
              },
              position: 'right',
              offset: (i - 1) * 80,
              nameTextStyle: {
                fontSize: '16px'
              }
            })
          }
        });
        echarts.util.each(waferIds, function (waferIdInfo) {
          echarts.util.each(paramsInfo, function (item) {
            const paramWaferIdInfo = waferIdInfo.waferId + '-' + item
            waferIdRes.push(paramWaferIdInfo);
            const datasetId = 'dataset_' + paramWaferIdInfo;
            datasetWithFilters.push({
              id: datasetId,
              fromDatasetId: 'dataset_raw',
              transform: {
                type: 'filter',
                config: {
                  and: [
                    {dimension: 'plcTimeStamp', gte: startStamp},
                    {dimension: 'paramWaferId', '=': paramWaferIdInfo}
                  ]
                }
              }
            });
            seriesList.push({
              type: 'line',
              datasetId: datasetId,
              showSymbol: false,
              yAxisIndex: yAxisIndexList.indexOf(item + '_value'),
              yAxisName: item + '_value',
              itemStyle: {
                normal: {
                  lineStyle: {
                    width: 2,
                    type: waferIdInfo.status === 'ok' ? 'solid' : 'dotted' //'dotted'点型虚线 'solid'实线 'dashed'线性虚线
                  }
                }
              },
              name: paramWaferIdInfo,
              encode: {
                x: 'plcTimeStamp',
                y: 'paramValue',
                label: ['paramWaferId', 'paramValue'],
                itemName: 'plcTimeStamp',
                tooltip: ['paramValue', 'recipePhase']
              }
            });
          })

        });
        option = {
          dataset: [
            {
              id: 'dataset_raw',
              source: _rawData
            },
            ...datasetWithFilters
          ],
          title: {
            text: '模造机参数曲线'
          },
          legend: {
            data: waferIdRes,
            bottom: 0,
            type: 'scroll',
            orient: 'horizontal'
          },
          toolbox: {
            feature: {
              dataZoom: {
                yAxisIndex: 'none'
              },
              restore: {},
              saveAsImage: {}
            }
          },
          tooltip: {
            order: 'valueDesc',
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            },
            confine: true,
            // formatter: function (params) {
            //   if (params instanceof Array) {
            //     let str = '';
            //     str += `${params[0].axisValue}<br/>`;
            //     params.forEach((m, index) => {
            //       str +=  `<span class="chart-tooltip-color" style="display: inline-block; margin-right: 10px; background-color: ${m.color}; width: 10px; height: 10px; border-radius:100%; margin-right: 5px"></span>`;
            //       str += `${m.seriesName}: 参数值：${m.data[3]} 步骤：${m.data[7]}`;
            //       str += `${index % 3 === 0 ? '<br/>' : ''}`; //一排放几个可根据实际情况改变
            //     });
            //     return str;
            //   }
            // }
          },
          xAxis: {
            type: 'category',
            name: '秒',
            nameTextStyle: {
              fontSize: '16px'
            }
          },
          // yAxis: {
          //   name: '参数值',
          //   scale: true,
          //   nameTextStyle: {
          //     fontSize: '16px'
          //   }
          // },
          yAxis: yAxisList,
          grid: {
            right: 140
          },
          series: seriesList
        };
        myChart.setOption(option, true);
      }

      option && myChart.setOption(option, true);
    }
  },
  mounted() {
    this.getMachineName();
  }
}
</script>
