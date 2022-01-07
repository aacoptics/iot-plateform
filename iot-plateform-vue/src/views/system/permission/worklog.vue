<template>
    <div class="container">
      <h1 style="text-align: center;margin-bottom: 20px;font-family: 楷体,serif">JIRA工时看板</h1>
      <el-row>
        <div class="toolbar" style="float:left;padding-top:10px;padding-left:15px;">

          <el-form :inline="true" :size="size">
            <el-form-item>
              <el-select v-model="filters.code" placeholder="请选择看板">
                <el-option
                    v-for="item in jiraBoards"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-date-picker
                  v-model="filters.dateRange"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
              >
              </el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button @click="queryIssues()" icon="el-icon-search" type="primary" :loading="tableLoading">
                查询
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-row style="margin-right: 10px;margin-top: 10px">
            <el-col :span="24">
              <h3 style="text-align: center">任务明细</h3>
              <el-table
                  id="topIssues"
                  style="width: 100%;font-size: xx-small"
                  row-key="issueKey"
                  border
                  lazy
                  :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
                  v-loading="tableLoading" 
                  :data="sprintIssues" 
                  height="375px"
              >
                <el-table-column prop="issue" label="任务" sortable width="230"/>
                <el-table-column prop="ekpIssueNo" label="IT应用需求申请单号" sortable width="180"/>
                <el-table-column prop="status" label="状态" sortable width="130"/>
                <el-table-column prop="startTime" label="开始时间" sortable width="160"/>
                <el-table-column prop="endTime" label="结束时间" sortable width="160"/>
                <el-table-column prop="businessCost" label="业务用时" sortable width="120"/>
                <el-table-column prop="devlopCost" label="开发用时" sortable width="120"/>
              </el-table>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </div>
</template>

<script>
import {getJiraIssue} from "@/api/system/worklog";

export default {
  name: "worklog",
  created() {
    //this.getBoards()
  },
  watch: {
  },
  data() {
    return {
      size: 'small',
      filters: {
        code: '',
        dateRange: []
      },

      jiraBoards: [
        {
          "name": "IT Infra Scrum",
          "self": "https://project.aacoptics.com/rest/agile/1.0/board/3183",
          "id": 3183,
          "type": "scrum"
        },
        {
          "name": "IT-开发敏捷看板",
          "self": "https://project.aacoptics.com/rest/agile/1.0/board/3285",
          "id": 3285,
          "type": "scrum"
        },
        {
          "name": "IT-职能敏捷看板",
          "self": "https://project.aacoptics.com/rest/agile/1.0/board/3423",
          "id": 3423,
          "type": "scrum"
        },
        {
          "name": "PRODUTION 看板",
          "self": "https://project.aacoptics.com/rest/agile/1.0/board/3302",
          "id": 3302,
          "type": "scrum"
        },
        {
          "name": "SSC 看板",
          "self": "https://project.aacoptics.com/rest/agile/1.0/board/3342",
          "id": 3342,
          "type": "scrum"
        },
        {
          "id": 3243,
          "self": "https://project.aacoptics.com/rest/agile/1.0/board/3243",
          "name": "光学IT研发组敏捷看板",
          "type": "scrum"
        }
      ],
      tableLoading: false,
      sprintIssues: []
    }
  },

  computed: {
  },

  methods: {
    queryIssues()
    {
      if (!this.filters.code) {
        this.$message({message: '请选择看板', type: 'error'})
        return
      }

      this.tableLoading = true;

      const startTime = this.$moment(this.filters.dateRange[0]).format('YYYY-MM-DD');
      const endTime = this.$moment(this.filters.dateRange[1]).format('YYYY-MM-DD');

      getJiraIssue(this.filters.code, startTime, endTime).then((res) => {
        const responseData = res.data
        if (responseData.code === '000000') {
          this.sprintIssues = responseData.data
        }
        this.tableLoading = false;
      }).catch(() => {
        this.tableLoading = false;
        this.sprintIssues = [];
      })
    }
  }
}
</script>

<style scoped>
.menu-container {
  margin-top: 10px;
}

.menu-header {
  padding-left: 8px;
  padding-bottom: 5px;
  text-align: left;
  font-size: 16px;
  color: rgb(20, 89, 121);

}
</style>
