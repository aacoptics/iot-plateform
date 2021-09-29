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
      >
        <el-table-column prop="issueKey" label="JIRA代码" sortable width="180"/>
        <el-table-column prop="username" label="姓名" sortable width="150"/>
        <el-table-column prop="jobNumber" label="工号" sortable width="150"/>
        <el-table-column prop="issueType" label="需求类型" sortable width="180"/>
        <el-table-column prop="issueSummary" label="JIRA任务" sortable width="180"/>
        <el-table-column prop="status" label="状态" sortable width="180"/>
        <el-table-column prop="ekpIssueNo" label="需求清单号" sortable width="180"/>
        <el-table-column prop="estimateTime" label="任务时长" sortable width="180" :formatter="timeFormatter"/>
      </el-table>

    </div>
  </div>
</template>

<script>
import {getAllBoards, getIssuesTree} from "@/api/system/jira";
import XLSX from "xlsx";
import FileSaver from 'file-saver'

export default {
  name: "role",
  created() {
    this.getBoards()
  },
  data() {
    return {
      size: 'small',
      filters: {
        code: '',
        status: []
      },
      sprintIssues: [],
      jiraBoards: [],
      tableLoading: false,
      status: []
    }
  },
  computed:{
    filterTable(){
      if(this.filters.status.length > 0){
        const res = []
        this.sprintIssues.forEach(item => {
          if(this.filters.status.indexOf(item.status) > -1){
            res.push(item)
          }
        })
        return res
      }else{
        return this.sprintIssues
      }
    }
  },
  methods: {
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
      this.tableLoading = true
      getIssuesTree(this.filters.code).then((res) => {
        const responseData = res.data
        if (responseData.code === '000000') {
          this.sprintIssues = responseData.data
          this.status = []
          this.sprintIssues.forEach(item => {
            if(this.status.indexOf(item.status) < 0){
              this.status.push(item.status)
            }
          })
        }
        this.tableLoading = false
      }).catch(() => {
        this.tableLoading = false
      })
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
