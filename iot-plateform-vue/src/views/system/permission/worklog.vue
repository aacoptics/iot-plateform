<template>
    <div class="container" style="height: 100%;">
      <h1 style="text-align: center;margin-bottom: 20px;font-family: 楷体,serif">JIRA工时看板</h1>
      
      <el-row>
        <div class="toolbar" style="float:left;padding-top:10px;padding-left:15px;">

          <el-form :inline="true" :size="size">
            <el-form-item>
              <el-select v-model="filters.code" placeholder="请选择看板" multiple>
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
              <el-button @click="toggleJIRA()" icon="el-icon-search" type="operation">
                展开/隐藏 JIRA清单
              </el-button>
              <el-button @click="exportExcel('#issueList1', 'IssueList1.xlsx')" icon="el-icon-download" type="primary" >
                导出任务明细
              </el-button>
              <el-button @click="exportExcel('#issueList2', 'IssueList2.xlsx')" icon="el-icon-download" type="primary" >
                导出JIRA清单
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-card shadow="hover" style="height:400px;margin-right: 10px" id="territoryPieChart">
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card shadow="hover" style="height:400px;margin-right: 10px" id="pieChart">
          </el-card>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <h3 style="text-align: center" v-show="topTimeTask.length > 0">时长Top10任务</h3>
          <el-table
              id="topIssues"
              :data="topTimeTask"
              style="width: 100%;font-size: xx-small"
              row-key="issueKey"
              border
              lazy 
              v-loading="tableLoading1"
              height="375px"
          >
            <el-table-column prop="issueKey" label="JIRA代码" sortable width="100"/>
            <el-table-column prop="username" label="姓名" sortable width="120"/>
            <el-table-column prop="territory" label="领域" sortable width="150"/>
            <el-table-column prop="issueType" label="需求类型" sortable width="180"/>
            <el-table-column prop="issueSummary" label="JIRA任务" sortable width="200"/>
            <el-table-column prop="status" label="状态" sortable width="80"/>
            <el-table-column prop="ekpIssueNo" label="需求清单号" sortable width="180"/>
            <el-table-column prop="estimateTime" label="任务时长" sortable width="80" :formatter="timeFormatter"/>
          </el-table>
        </el-col>
        <el-col :span="12">
          <el-card shadow="hover" style="height:400px;margin-right: 10px" id="barChart">
          </el-card>
        </el-col>
      </el-row>
      <el-row 
        <el-col :span="24">
          <el-row style="margin-right: 10px;margin-top: 10px">
            <el-col :span="24">
              <h3 style="text-align: center">任务明细</h3>
              <el-table
                  id="issueList1" 
                  style="width:100%;font-size: xx-small;"
                  row-key="issueKey"
                  border
                  lazy 
                  v-loading="tableLoading" 
                  :data="worklogIssues" 
                  height="600px"
              >
                <el-table-column prop="dashboard" label="看板" sortable width="120"/>
                <el-table-column prop="issue" label="任务" sortable width="180"/>
                <el-table-column prop="issueType" label="需求类型" sortable width="180"/>
                <el-table-column prop="ekpIssueNo" label="IT应用需求申请单号" sortable width="150"/>
                <el-table-column prop="status" label="状态" sortable width="80"/>
                <el-table-column prop="owner" label="人员" sortable width="100"/>
                <el-table-column prop="cost" label="用时" sortable width="100"/>
              </el-table>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <el-row v-if="!hideJIRA">
        <el-col :span="24">
          <el-row style="margin-right: 10px;margin-top: 10px">
            <el-col :span="24">
              <h3 style="text-align: center">JIRA清单</h3>
              <el-table
                  id="issueList2" 
                  style="width:100%;font-size: xx-small;"
                  row-key="issueKey"
                  border
                  lazy 
                  v-loading="tableLoading" 
                  :data="sprintIssues" 
                  height="600px"
              >
                <el-table-column prop="dashboard" label="看板" sortable width="120"/>
                <el-table-column prop="issue" label="任务" sortable width="180"/>
                <el-table-column prop="issueType" label="需求类型" sortable width="180"/>
                <el-table-column prop="ekpIssueNo" label="IT应用需求申请单号" sortable width="150"/>
                <el-table-column prop="status" label="状态" sortable width="80"/>
                <el-table-column prop="startTime" label="开始时间" sortable width="130"/>
                <el-table-column prop="endTime" label="结束时间" sortable width="130"/>
                <el-table-column prop="businessOwner" label="业务人员" sortable width="100"/>
                <el-table-column prop="businessCost" label="业务用时" sortable width="100"/>
                <el-table-column prop="developOwner" label="开发人员" sortable width="100"/>
                <el-table-column prop="developCost" label="开发用时" sortable width="100"/>
              </el-table>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </div>
</template>

<script>
import XLSX from "xlsx";
import FileSaver from 'file-saver';
import {getJiraIssue, getTop10JiraIssue, filterIssues} from "@/api/system/worklog";
import * as echarts from 'echarts';

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
      hideJIRA: true,
      filters: {
        code: [],
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
      tableLoading1: false,

      sprintIssues: [],
      worklogIssues: [],
      pieData: [],
      territoryPieData: [],
      barData: {value: [], name: []},
      topTimeTask: [],
      userStoryPoints: {},
      territoryStoryPoints: {}
    }
  },

  computed: {
  },

  methods: {
    toggleJIRA()
    {
        this.hideJIRA = !this.hideJIRA;
    },
    queryIssues()
    {
      if (this.filters.code == null || this.filters.code.length == 0) {
        this.$message({message: '请选择看板', type: 'error'})
        return
      }
      
      var boardId = '';
      for(var i=0;i<this.filters.code.length;i++)
      {
        if(i != this.filters.code.length -1)
        {
          boardId += this.filters.code[i] + ',';
        }
        else
        {
          boardId += this.filters.code[i]
        }
      }
      this.tableLoading = true;

      const startTime = this.$moment(this.filters.dateRange[0]).format('YYYY-MM-DD');
      const endTime = this.$moment(this.filters.dateRange[1]).format('YYYY-MM-DD');

      this.sprintIssues = [];
      this.worklogIssues = [];

      this.barData = {value: [], name: []};
      this.pieData = [];
      this.territoryPieData = [];

      this.userStoryPoints = {};
      this.territoryStoryPoints = {};

      getJiraIssue(boardId, startTime, endTime).then((res) => {
        const responseData = res.data
        if (responseData.code === '000000') {
          this.sprintIssues = responseData.data;
        }
        this.tableLoading = false;
      }).catch(() => {
        this.tableLoading = false;
        this.sprintIssues = [];
      });

      getTop10JiraIssue(boardId, startTime, endTime).then((res) => {
        const responseData = res.data
        if (responseData.code === '000000') {
          this.topTimeTask = responseData.data;
        }
        this.tableLoading1 = false;
      }).catch(() => {
        this.tableLoading1 = false;
        this.topTimeTask = [];
      });

      filterIssues(boardId, startTime, endTime).then((res) => {
        const responseData = res.data
        if (responseData.code === '000000') {

          this.worklogIssues = responseData.data;

          this.getUserStoryPoints(this.worklogIssues);
          this.getTerritoryStoryPoints(this.worklogIssues);

          for (const p in this.userStoryPoints) {
            this.pieData.push({value: this.userStoryPoints[p], name: p})
            this.barData.name.push(p)
            this.barData.value.push(this.userStoryPoints[p])
          }
          for (const p in this.territoryStoryPoints) {
            this.territoryPieData.push({value: this.territoryStoryPoints[p], name: p})
          }

          this.drawPieChart();
          this.drawBarChart();
          this.drawTerritoryPieChart();
        }
        this.tableLoading = false;
      }).catch(() => {
        this.tableLoading = false;
        this.worklogIssues = [];
      });
    },
    exportExcel(tableId, excelFileName) {
      const wb = XLSX.utils.table_to_book(document.querySelector(tableId));
      const wbOut = XLSX.write(wb, {bookType: 'xlsx', bookSST: true, type: 'array'});
      try {
        FileSaver.saveAs(new Blob([wbOut], {type: 'application/octet-stream'}), excelFileName)
      } catch (e) {
        if (typeof console !== 'undefined') console.log(e, wbOut)
      }
      return wbOut
    },
    getUserStoryPoints(val) {
      val.forEach(item => {
        var username = '';
        var estimateTime = 0.0;
        
        /**if(issueKey.indexOf('DEV-') > -1)
        {
          username = item.developOwner;
          estimateTime = parseFloat(item.developCost + '');
        }
        else
        {
          username = item.businessOwner;
          estimateTime = parseFloat(item.businessCost + '');
        }**/

        username = item.owner;
        estimateTime = parseFloat(item.cost + '');

        if (!this.userStoryPoints[username]) {
          this.userStoryPoints[username] = 0
        }
        this.userStoryPoints[username] += estimateTime;
        
      })
    },
    getTerritoryStoryPoints(val) {
      val.forEach(item => {
        if (!this.territoryStoryPoints[item.territory]) {
          this.territoryStoryPoints[item.territory] = 0
        }
        var estimateTime = 0.0;

        estimateTime = parseFloat(item.cost + '');

        this.territoryStoryPoints[item.territory] += estimateTime;
      })
    },
    drawPieChart() {
      const chartDom = document.getElementById('pieChart');
      const myChart = echarts.init(chartDom);
      let option;
      option = {
        title: {
          text: '任务时间比例',
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{b}: {c}({d}%)'
        },
        legend: {
          orient: 'horizontal',
          top: 'bottom'
        },
        series: [
          {
            name: '姓名',
            type: 'pie',
            radius: '50%',
            data: this.pieData,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      option && myChart.setOption(option);
    },
    drawTerritoryPieChart() {
      const chartDom = document.getElementById('territoryPieChart');
      const myChart = echarts.init(chartDom);
      let option;
      option = {
        title: {
          text: '产品线时间比例',
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{b}: {c}({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          textStyle: {
            fontSize: 16
          }
        },
        series: [
          {
            name: '产品线',
            type: 'pie',
            radius: '50%',
            data: this.territoryPieData,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      option && myChart.setOption(option);
    },
    drawBarChart() {
      const chartDom = document.getElementById('barChart');
      const myChart = echarts.init(chartDom);
      let option;

      option = {
        title: {
          text: '任务时间柱状图',
          left: 'center'
        },
        xAxis: {
          type: 'category',
          data: this.barData.name,
          axisLabel: {
            interval: 0,
            rotate: 30
          }
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: this.barData.value,
            type: 'bar',
            markLine: {
              silent: true,
              lineStyle: {
                normal: {
                  color: 'red'                   // 这儿设置安全基线颜色
                }
              },
              data: [{
                yAxis: 40,
              },
                {
                  yAxis: 48,
                }],
              label: {
                normal: {
                  formatter: '标准基线'           // 这儿设置安全基线
                }
              },
            }
          }
        ]
      };
      option && myChart.setOption(option);
    },
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
