<template>
    <div style="margin: 10px 50px 10px 50px;overflow:auto;">
      <el-row>
        <el-col :span="24">
            <el-form :inline="true" >
              <el-form-item label="计划主键" :label-width="100">
                <el-input v-model="search.planKey" placeholder="" :width="200" />
              </el-form-item>
              <el-form-item label="计划名称" >
                <el-input v-model="search.planName" placeholder="" />
              </el-form-item>
              <el-form-item  label="触发时间" >
                <el-date-picker v-model="search.workDay" type="date" format="YYYY-MM-DD" placeholder="选择1天" />
              </el-form-item>
              <br>
              <el-form-item>
                <el-button icon="el-icon-search" type="primary" @click="queryJobData()">
                  查询
                </el-button>
              </el-form-item>
            </el-form>
        </el-col>
      </el-row>
      <el-row>
        <el-table
            id="jobTable"
            :data="jobData"
            border 
            :key="1"
            header-row-class-name="tableHead" 
            highlight-current-row 
            style="width: 100%;margin-top: 10px;" 
            :height="400"
            >
          <el-table-column :width=100 prop="planKey" label="计划主键"></el-table-column>
          <el-table-column prop="planName" label="计划名称"></el-table-column>
          <el-table-column prop="workDay" label="日期"></el-table-column>
          <el-table-column prop="msg" label="消息内容" :width="300"></el-table-column>
        </el-table>
      </el-row>
    </div>
</template>

<script>

import {filterPlanJOB} from "@/api/message/sendMsg";

export default {
  name: "sendMsgQuery",
  created() {
  },
  data() {
    return {
      search: {
        planKey: '',
        planName: '',
        workDay: ''
      },
      jobData: []
    }
  },
  computed: {
  },
  methods: {
    queryJobData()
    {
      var planKey = this.search.planKey + '';
      var planName = this.search.planName + '';
      var workDay = '';

      if(this.search.workDay != null && this.search.workDay != '')
      {
          workDay = this.$moment(this.search.workDay).format('YYYY-MM-DD');
      }
      else
      {
          workDay = '';
      }

      this.jobData = [];

      filterPlanJOB(planKey, planName, workDay).then((res) => {
        const responseData = res.data
        if (responseData.code === '000000') {
          this.jobData = responseData.data;
        }
      }).catch(() => {
        this.jobData = [];
      });
    },
  },
};
</script>
