<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          系统管理
        </el-breadcrumb-item>
        <el-breadcrumb-item>Jira问题</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="toolbar" style="float:left;padding-top:10px;padding-left:15px;">
        <el-switch
            v-model="byBoard"
            active-text="根据Sprint"
            inactive-text="根据时间"
            :size="size"
            style="margin-bottom: 10px"
        >
        </el-switch>

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
          <el-form-item v-if="!byBoard">
            <el-date-picker
                v-model="dateRange"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-select v-model="filters.status" multiple placeholder="请选择状态">
              <el-option
                  v-for="item in status"
                  :key="item"
                  :label="item"
                  :value="item"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary"
                       @click="findPage(null)">查询
            </el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="success" icon="el-icon-download" size="small"
                       @click="exportExcel('#jiraIssues', 'jiraIssues.xlsx')">导出
            </el-button>
          </el-form-item>
        </el-form>
      </div>
      <el-table
          id="jiraIssues"
          :data="filterTable"
          style="width: 100%; margin-bottom: 20px"
          row-key="issueKey"
          border
          lazy
          :load="load"
          :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
          v-loading="tableLoading"
          height="600px"
      >
        <el-table-column prop="issueKey" label="JIRA代码" sortable width="180"/>
        <el-table-column prop="username" label="姓名" sortable width="150"/>
        <el-table-column prop="jobNumber" label="工号" sortable width="150"/>
        <el-table-column prop="issueType" label="需求类型" sortable width="180"/>
        <el-table-column prop="issueSummary" label="JIRA任务" sortable width="180"/>
        <el-table-column prop="status" label="状态" sortable width="180"/>
        <el-table-column prop="createTime" label="创建时间" sortable width="180"/>
        <el-table-column prop="updateTime" label="更新时间" sortable width="180"/>
        <el-table-column prop="ekpIssueNo" label="需求清单号" sortable width="180"/>
        <el-table-column prop="estimateTime" label="任务时长" sortable width="180" :formatter="timeFormatter"/>
      </el-table>

      <el-row>
        <el-col :span="12">
          <el-card shadow="hover" style="height:400px;" id="pieChart">
          </el-card>

        </el-col>

        <el-col :span="12">
          <el-card shadow="hover" style="height:400px;" id="barChart">
          </el-card>

        </el-col>
      </el-row>


    </div>
  </div>
</template>

<script>
import {getAllBoards, getIssuesTree, getIssuesTreeByTime} from "@/api/system/jira";
import XLSX from "xlsx";
import FileSaver from 'file-saver'
import * as echarts from 'echarts';

export default {
  name: "role",
  created() {
    //this.getBoards()
  },
  watch: {
    filterTable(val) {
      this.userStoryPoints = {}
      this.getUserStoryPoints(val)
      this.pieData = []
      this.barData = {value: [], name: []}
      for (const p in this.userStoryPoints) {
        this.pieData.push({value: this.userStoryPoints[p], name: p})
        this.barData.name.push(p)
        this.barData.value.push(this.userStoryPoints[p])
      }
      this.drawPieChart()
      this.drawBarChart()
    }
  },
  data() {
    return {
      byBoard: true,
      dateRange: [],
      size: 'small',
      filters: {
        code: '',
        status: []
      },
      sprintIssues: [],
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
      status: [],
      pieData: [],
      barData: {value: [], name: []},
      userStoryPoints: {}

    }
  },
  computed: {
    filterTable() {
      if (this.filters.status.length > 0) {
        const res = []
        this.sprintIssues.forEach(item => {
          if (this.filters.status.indexOf(item.status) > -1) {
            res.push(item)
          }
        })
        return res
      } else {
        return this.sprintIssues
      }
    }
  },
  methods: {
    getUserStoryPoints(val) {
      val.forEach(item => {
        if (!this.userStoryPoints[item.username]) {
          this.userStoryPoints[item.username] = 0
        }
        this.userStoryPoints[item.username] += (item.estimateTime / 60 / 60)
        if (item.children) {
          this.getUserStoryPoints(item.children)
        }
      })
    },
    drawBarChart() {
      const chartDom = document.getElementById('barChart');
      const myChart = echarts.init(chartDom);
      let option;

      option = {
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
          orient: 'vertical',
          left: 'left'
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
    getRowKey(row) {
      return row.name;
    },
    // 获取分页数据
    findPage: function () {
      if (!this.filters.code) {
        this.$message({message: '请选择看板', type: 'error'})
        return
      }
      this.tableLoading = true
      if (this.byBoard) {
        getIssuesTree(this.filters.code).then((res) => {
          const responseData = res.data
          if (responseData.code === '000000') {
            this.sprintIssues = responseData.data
            this.status = []
            this.filterTable.forEach(item => {
              if (this.status.indexOf(item.status) < 0) {
                this.status.push(item.status)
              }
            })
          }
          this.tableLoading = false
        }).catch(() => {
          this.tableLoading = false
        })
      } else {
        if (this.dateRange.length === 0) {
          this.$message({message: '请选择时间段', type: 'error'})
          this.tableLoading = false
          return
        }
        const startTime = this.$moment(this.dateRange[0]).format('YYYY-MM-DD');
        const endTime = this.$moment(this.dateRange[1]).format('YYYY-MM-DD');
        getIssuesTreeByTime(this.filters.code, startTime, endTime).then((res) => {
          const responseData = res.data
          if (responseData.code === '000000') {
            this.sprintIssues = responseData.data
            this.status = []
            this.filterTable.forEach(item => {
              if (this.status.indexOf(item.status) < 0) {
                this.status.push(item.status)
              }
            })
          }
          this.tableLoading = false
        }).catch(() => {
          this.tableLoading = false
        })

      }
    },
    getBoards: function () {
      getAllBoards().then((res) => {
        const responseData = res.data
        if (responseData.code === '000000') {
          this.jiraBoards = responseData.data
        }
      })
    },
    load(tree, treeNode, resolve) {
      setTimeout(() => {
        resolve(tree.children)
      }, 100)
    },
    timeFormatter: function (row, column) {
      return (row[column.property] / 60 / 60).toFixed(2)
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
