<template style="overflow:auto;">
    <div style="margin: 10px 50px 10px 50px;overflow:auto;">
      <el-row>
        <el-col :span="24">
            <el-form :inline="true" >
              <el-form-item label="计划主键" :label-width="100">
                <el-input v-model="input.planKey" placeholder="" :width="200" />
              </el-form-item>
              <el-form-item label="计划名称" >
                <el-input v-model="input.planName" placeholder="" />
              </el-form-item>
              <el-form-item  label="消息类型" >
                <el-select v-model="input.msgType" placeholder="" clearable>
                  <el-option
                      v-for="item in options.msgTypeList"
                      :key="item"
                      :label="item"
                      :value="item"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item  label="通知方式" v-if="input.msgType == 'DingTalk' || input.msgType == 'FeiShu' ">
                <el-select v-model="input.notifyType" placeholder="" clearable>
                  <el-option
                      v-for="item in options.notifyTypeList"
                      :key="item"
                      :label="item"
                      :value="item"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="机器人" :label-width="100" v-if="input.notifyType == 'Robot' ">
                <el-input v-model="input.notifyRobot" placeholder="" style="width:900px;" clearable/>
              </el-form-item>
              <br>
              <el-form-item label="SIGN" :label-width="100" v-if="input.notifyType == 'Robot' ">
                <el-input v-model="input.sign" placeholder="" style="width:600px;" clearable/>
              </el-form-item>
              <br>
              <el-form-item  label="触发时间" :label-width="100">
                <el-select v-model="input.triggerType" placeholder="">
                  <el-option
                      v-for="item in options.triggerTypeList"
                      :key="item"
                      :label="item"
                      :value="item"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item :label-width="100" label="月份" v-if="input.triggerType == '按时间'">
                <el-select @change="initPlanCron()" v-model="input.planMonth" placeholder="" style="width:200px;">
                  <el-option
                      v-for="item in options.monthList"
                      :key="item"
                      :label="item"
                      :value="item"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item  label="日期" v-if="input.triggerType == '按时间'">
                <el-select @change="initPlanCron()" v-model="input.planDay" placeholder="" style="width:80px;">
                  <el-option
                      v-for="item in options.dayList"
                      :key="item"
                      :label="item"
                      :value="item"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item  label="时间" v-if="input.triggerType == '按时间'">
                <el-select @change="initPlanCron()" v-model="input.planHour" placeholder="" style="width:80px;">
                  <el-option
                      v-for="item in options.hourList"
                      :key="item"
                      :label="item"
                      :value="item"
                  >
                  </el-option>
                </el-select>
                <span>:</span>
                <el-select @change="initPlanCron()" v-model="input.planMinute" placeholder="" style="width:80px;">
                  <el-option
                      v-for="item in options.minuteList"
                      :key="item"
                      :label="item"
                      :value="item"
                  >
                  </el-option>
                </el-select>
                <span>:</span>
                <el-select @change="initPlanCron()" v-model="input.planSecond" placeholder="" style="width:80px;">
                  <el-option
                      v-for="item in options.minuteList"
                      :key="item"
                      :label="item"
                      :value="item"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item  :label-width="100" label="时间间隔(min)" v-if="input.triggerType == '按周期'">
                <el-input @change="initPlanCron()" v-model="input.timePeriod" placeholder="" />
              </el-form-item>
              <el-form-item label="Cron表达式" :label-width="100">
                <el-input v-model="input.planCron" placeholder="" :disabled="true"/>
              </el-form-item>
              <el-form-item label="启用" :label-width="100">
                <el-checkbox v-model="input.statusFlag"></el-checkbox>
              </el-form-item>
              <br>
              <el-form-item label="消息内容" :label-width="100">
                <el-input
                  v-model="input.msgExpr"
                  :rows="4"
                  type="textarea"
                  placeholder="" 
                  style="width:900px;"
                />
              </el-form-item>
              <br>
              <el-form-item>
                <el-button type="primary" @click="queryPlanData()">
                  查询
                </el-button>
                <el-button type="primary" @click="savePlanData('insert')">
                  新增
                </el-button>
                <el-button type="primary" @click="savePlanData('update')">
                  编辑
                </el-button>
                <el-button type="primary" @click="resetInput()">
                  重置
                </el-button>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <el-button type="primary" @click="manualExcutePlan()">
                  推送
                </el-button>
              </el-form-item>
            </el-form>
        </el-col>
      </el-row>
      <el-row>
        <el-table
            id="planTable"
            :data="planData"
            border 
            :key="1"
            header-row-class-name="tableHead" 
            highlight-current-row 
            style="width: 100%;margin-top: 10px"
            @current-change="handleCurrentRow"
            >
          <el-table-column :width=100 prop="planKey" label="计划主键"></el-table-column>
          <el-table-column prop="planName" label="计划名称"></el-table-column>
          <el-table-column prop="msgType" label="消息类型"></el-table-column>
          <el-table-column prop="msgType" label="消息类型"></el-table-column>
          <el-table-column prop="notifyType" label="通知类型"></el-table-column>
          <el-table-column prop="triggerType" label="触发时间"></el-table-column>
          <el-table-column prop="planMonth" label="计划月份"></el-table-column>
          <el-table-column prop="planDay" label="计划日期"></el-table-column>
          <el-table-column prop="planHour" label="时"></el-table-column>
          <el-table-column prop="planMinute" label="分"></el-table-column>
          <el-table-column prop="planSecond" label="秒"></el-table-column>
          <el-table-column prop="timePeriod" label="时间间隔(S)"></el-table-column>
          <el-table-column prop="planCron" label="Cron表达式"></el-table-column>
        </el-table>
      </el-row>
      <br>
      <el-row>
        <el-col :span="10">
          <el-row>
            <el-form :inline="true" >
              <el-form-item label="姓名" >
                <el-input v-model="input.userName" placeholder="" />
              </el-form-item>
              <el-form-item>
                <el-button  type="primary" @click="queryContactData()">
                  查询
                </el-button>
                <el-button  type="primary" @click="savePlanContact()">
                  保存
                </el-button>
              </el-form-item>
            </el-form>
          </el-row>
          <el-row>
            <el-table
                id="contactTable1"
                :data="contactData1"
                border
                :key="1"
                header-row-class-name="tableHead" 
                style="width: 100%;margin-top: 10px;" 
                @selection-change="handleCurrentRow1" 
                :height="400"
                >
              <el-table-column type="selection" width="55" />
              <el-table-column prop="userNo" label="工号"></el-table-column>
              <el-table-column prop="userName" label="姓名"></el-table-column>
            </el-table>
          </el-row>
        </el-col>
        <el-col :span="4">
          <el-row>
              <el-button type="primary" style="margin-top: 75px;margin-left:20px;" @click="selectPlanContact()">
                》
              </el-button>
          </el-row>
          <el-row>
              <el-button type="primary" style="margin-top: 10px;margin-left:20px;" @click="removePlanContact()">
              《
              </el-button>
          </el-row>
        </el-col>
        <el-col :span="10">
          <el-table 
                id="contactTable2"
                :data="contactData2"
                border
                :key="1"
                header-row-class-name="tableHead" 
                style="width: 100%;margin-top: 75px;" 
                @selection-change="handleCurrentRow2" 
                :height="400"
                >
              <el-table-column type="selection" width="55" />
              <el-table-column prop="userNo" label="工号"></el-table-column>
              <el-table-column prop="userName" label="姓名"></el-table-column>
            </el-table>
        </el-col>
      </el-row>
    </div>
</template>

<script>

import {insertPlanData, insertPlanContact, updatePlanData, filterPlanData, filterPlanContact, filterContactData, excutePlan} from "@/api/message/sendMsg";

export default {
  name: "sendMsgConfig",
  created() {
  },
  data() {
    return {
      input: {
        planKey: '',
        planName: '',
        planCron: '',
        msgType: '',
        notifyType: '',
        notifyRobot: '',
        sign: '',
        msgExpr: '',
        statusFlag: true,
        timePeriod: 60,
        planMonth: '*',
        planDay: '*',
        planHour: '*',
        planMinute: '*',
        planSecond: '*',
        triggerType: '按时间',
        userName: ''
      },
      options: {
        monthList: ['*', '1','2','3','4','5','6','7','8','9','10','11','12'],
        dayList: ['*','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15'
                ,'16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31'],
        hourList: ['*', '0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15'
                ,'16','17','18','19','20','21','22','23','24'],
        minuteList: ['*', '0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15'
                ,'16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31'
                ,'32','33','34','35','36','37','38','39','40','41','42','43','44','45','46','47'
                ,'48','49','50','51','52','53','54','55','56','57','58','59','60'],
        msgTypeList: ['DingTalk', 'Email', 'Message', 'FeiShu'],
        notifyTypeList: ['Notify', 'Robot'],
        triggerTypeList: ['按时间', '按周期']
      },
      planData: [],
      contactData1: [],
      contactData2: [],
      selectContact1: [],
      selectContact2: []
    }
  },
  computed: {
    tableMaxHeight() {
      return window.innerHeight - 370 + 'px';
    }
  },
  methods: {
    resetInput()
    {
        this.input.planKey = '';
        this.input.planName = '';
        this.input.planCron = '';
        this.input.msgType = '';
        this.input.msgExpr = '';
        this.input.statusFlag = true;
        this.input.timePeriod = 60;
        this.input.planMonth = '*';
        this.input.planDay = '*';
        this.input.planHour = '*';
        this.input.planMinute = '*';
        this.input.planSecond = '*';
        this.input.triggerType = '按时间';
        this.input.userName = '';
        this.input.notifyRobot = '';
        this.input.sign = '';

    },
    queryPlanData()
    {
      var planKey = this.input.planKey + '';
      var planName = this.input.planName + '';

      this.planData = [];
      this.contactData1 = [];
      this.contactData2 = [];
      this.selectContact1 = [];
      this.selectContact2 = [];

      filterPlanData(planKey, planName).then((res) => {
        const responseData = res.data
        if (responseData.code === '000000') {
          this.planData = responseData.data;
          this.queryContactData();
        }
      }).catch(() => {
        this.planData = [];
      });
    },
    manualExcutePlan()
    {
        var planKey = this.input.planKey + '';
        excutePlan(planKey).then((res) => {
          const responseData = res.data
          if (responseData.code === '000000') {
            this.$message({message: responseData.msg, type: 'success'});
          }
        }).catch(() => {
          this.$message({message: '计划' + planKey + '手动执行失败！', type: 'error'});
        });
    },
    queryContactData()
    {
      var userName = this.input.userName + '';

      this.contactData1 = [];
      
      filterContactData(userName).then((res) => {
        const responseData = res.data
        if (responseData.code === '000000') {
          this.contactData1 = responseData.data;
        }
      }).catch(() => {
        this.contactData1 = [];
      });
    },

    savePlanData(operation)
    {
      var planKey = this.input.planKey + '';
      var planName = this.input.planName + '';
      var planCron = this.input.planCron + '';
      var msgType = this.input.msgType + '';
      var notifyType = this.input.notifyType + '';
      var notifyRobot = this.input.notifyRobot + '';
      var sign = this.input.sign + '';
      var msgExpr = this.input.msgExpr + '';
      var triggerType = this.input.triggerType + '';
      var planMonth = this.input.planMonth + '';
      var planDay = this.input.planDay + '';
      var planHour = this.input.planHour + '';
      var planMinute = this.input.planMinute + '';
      var planSecond = this.input.planSecond + '';
      var timePeriod = this.input.timePeriod + '';
      var statusFlag = this.input.statusFlag + '';

      var user = this.$route.query.user + '';

      if(planKey == '')
      {
        this.$message({message: '请输入计划主键！', type: 'warning'});
        return;
      }
      if(planName == '')
      {
        this.$message({message: '请输入计划名称！', type: 'warning'});
        return;
      }
      if(planCron == '')
      {
        this.$message({message: 'Cron表达式不能为空！', type: 'warning'});
        return;
      }
      if(msgType == '')
      {
        this.$message({message: '请选择消息类型！', type: 'warning'});
        return;
      }
      if((msgType == 'DingTalk' ||  msgType == 'FeiShu') && notifyType == '')
      {
        this.$message({message: '请选择通知类型！', type: 'warning'});
        return;
      }
      if(notifyType == 'Robot' && notifyRobot == '')
      {
        this.$message({message: '请填写机器人！', type: 'warning'});
        return;
      }
      if(triggerType == '')
      {
        this.$message({message: '请选择触发类型！', type: 'warning'});
        return;
      }
      if(triggerType != '按周期')
      {
        if(planMonth == '')
        {
          this.$message({message: '请选择月份！', type: 'warning'});
          return;
        }
        if(planDay == '')
        {
          this.$message({message: '请选择日期！', type: 'warning'});
          return;
        }
        if(planHour == '' || planMinute == '' || planSecond == '')
        {
          this.$message({message: '请选择有效的时间！', type: 'warning'});
          return;
        }
      }
      else
      {
        if(timePeriod == '' || timePeriod == null || timePeriod == undefined)
        {
          this.$message({message: '请选择有效的时间间隔！', type: 'warning'});
          return;
        }
      }

      var params = {};
      params.planKey = planKey;
      params.planName = planName;
      params.planCron = planCron;
      params.msgType = msgType;
      params.notifyType = notifyType;
      params.notifyRobot = notifyRobot;
      params.sign = sign;
      params.msgExpr = encodeURI(msgExpr);
      params.triggerType = triggerType;
      params.planMonth = planMonth;
      params.planDay = planDay;
      params.planHour = planHour;
      params.planMinute = planMinute;
      params.planSecond = planSecond;
      params.timePeriod = timePeriod;
      params.user = user;
      params.statusFlag = statusFlag;
      
      if(operation == 'insert')
      {
        insertPlanData(params).then((res) => {
          const responseData = res.data
          if (responseData.code === '000000') {
            this.$message({message: responseData.msg, type: 'success'});
            this.resetInput();
            this.queryPlanData();
          }
          else
          {
            this.$message({message: responseData.msg, type: 'error'});
          }
        }).catch(() => {
          this.$message({message: '保存数据发生异常！', type: 'error'});
        });
      }
      else
      {
        updatePlanData(params).then((res) => {
          const responseData = res.data
          if (responseData.code === '000000') {
            this.$message({message: responseData.msg, type: 'success'});
            this.resetInput();
            this.queryPlanData();
          }
          else
          {
            this.$message({message: responseData.msg, type: 'error'});
          }
        }).catch(() => {
          this.$message({message: '保存数据发生异常！', type: 'error'});
        });
      }

    },

    savePlanContact()
    {
      if(this.input.planKey == '' || this.input.planKey == null || this.input.planKey == undefined)
      {
        this.$message({message: '计划主键不能为空！', type: 'warning'});
        return;
      }
      if(this.contactData2 == null || this.contactData2 == undefined || this.contactData2.length == 0)
      {
        this.$message({message: '消息推送联系人不能为空！', type: 'warning'});
        return;
      }
      var userNoList = '';
      for(var i=0;i<this.contactData2.length;i++)
      {
        userNoList += (this.contactData2[i].userNo + ',')
      }

      userNoList = userNoList.substring(0, userNoList.length - 1);

      insertPlanContact(this.input.planKey, userNoList).then((res) => {
          const responseData = res.data
          if (responseData.code === '000000') {
            this.$message({message: responseData.msg, type: 'success'});
          }
          else
          {
            this.$message({message: responseData.msg, type: 'error'});
          }
        }).catch(() => {
          this.$message({message: '保存数据发生异常！', type: 'error'});
        });
    },

    handleCurrentRow(selectRow)
    {
      this.input.planKey = selectRow.planKey;
      this.input.planName = selectRow.planName;
      this.input.msgType = selectRow.msgType;
      this.input.notifyType = selectRow.notifyType;

      this.input.notifyRobot = selectRow.notifyRobot;
      this.input.sign = selectRow.sign;

      this.input.msgExpr = selectRow.msgExpr;
      this.input.planCron = selectRow.planCron;
      this.input.triggerType = selectRow.triggerType;
      this.input.planMonth = selectRow.planMonth;
      this.input.planDay = selectRow.planDay;
      this.input.planHour = selectRow.planHour;
      this.input.planMinute = selectRow.planMinute;
      this.input.planSecond = selectRow.planSecond;
      this.input.timePeriod = selectRow.timePeriod;
      this.input.statusFlag = selectRow.status == '1'?true:false;

      if(this.input.planKey != '')
      {
        filterPlanContact(this.input.planKey).then((res) => {
          const responseData = res.data
          if (responseData.code === '000000') {
            this.contactData2 = responseData.data;
          }
        }).catch(() => {
          this.contactData2 = [];
        });
      }
    },

    handleCurrentRow1(selectRows)
    {
      this.selectContact1 = selectRows;
    },

    handleCurrentRow2(selectRows)
    {
      this.selectContact2 = selectRows;
    },

    selectPlanContact()
    {
      /** if(this.$refs.contactTable1.selection == null || this.$refs.contactTable1.selection == undefined || this.$refs.contactTable1.selection.length == 0)
      {
        this.$message({message: '没有可选择的联系人数据！', type: 'warning'});
        return;
      } **/

      this.contactData2 = this.contactData2.concat(this.selectContact1);
      let newContactArray = this.array_diff(this.contactData1, this.selectContact1);
      
      this.contactData1 = newContactArray;
    },
    removePlanContact()
    {
      /** if(this.$refs.contactTable2.selection == null || this.$refs.contactTable2.selection == undefined || this.$refs.contactTable2.selection.length == 0)
      {
        this.$message({message: '没有可移除的联系人数据！', type: 'warning'});
        return;
      } **/

      this.contactData1 = this.contactData1.concat(this.selectContact2);
      let newContactArray = this.array_diff(this.contactData2, this.selectContact2);
      
      this.contactData2 = newContactArray;
    },
    array_diff(a, b) {
        for (var i = 0; i < b.length; i++) {
            for (var j = 0; j < a.length; j++) {
                if (a[j].userNo == b[i].userNo) {
                    a.splice(j, 1);
                    j = j - 1;
                }
            }
        }
        return a;
    },
    initPlanCron() {
      if(this.input.triggerType == '按时间')
      {
        this.input.planCron = this.input.planSecond + ' ' + this.input.planMinute  + ' ' + this.input.planHour
                            + ' ' + this.input.planDay 
                            + ' ' + this.input.planMonth 
                            + ' ?';
      }
      else
      {
        this.input.planCron = '0 ' + '*/' + this.input.timePeriod + ' * * * ?';
      }
    }
  },
};
</script>
