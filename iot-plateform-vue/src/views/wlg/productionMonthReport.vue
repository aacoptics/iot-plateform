<template>
  <div>
    <div class="container">
      <div class="toolbar" style="float:left;padding-top:10px;padding-left:15px;">
                <el-form :inline="true" :size="size" label-width="100px">
          <el-row>
              <el-form-item label="项目" prop="projectName">
                <el-input
                    v-model="filters.projectName"
                    placeholder="请输入项目"
                    clearable
                    size="small"
                />
              </el-form-item>
            <el-form-item label="日期 从" prop="producitonDate">
              <el-date-picker v-model="filters.producitonDateStart" type="date" value-format="YYYY-MM-DD" auto-complete="off"></el-date-picker>
            </el-form-item>
            <el-form-item label="到" prop="producitonDate">
              <el-date-picker v-model="filters.producitonDateEnd" type="date" value-format="YYYY-MM-DD" auto-complete="off"></el-date-picker>
            </el-form-item>
            </el-row>
        </el-form>
    
          <el-form :inline="true" :size="size">
            <el-form-item>
              <el-button icon="el-icon-search" type="primary" :loading="queryLoading"
                        @click="findPage(null)">查询
              </el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="success" icon="el-icon-download" size="small" :loading="exportLoading"
                         @click="exportExcelData('月度汇总报表')">导出
              </el-button>
            </el-form-item>
          </el-form>
      </div>
      <QueryAllTable id="condDataTable" :height="550" :highlightCurrentRow="true" :stripe="true"
                :data="pageResult" :columns="columns" 
                ref="queryAllTable"
                @findPage="findPage" >
      </QueryAllTable>
    </div>
  </div>
</template>

<script>

import QueryAllTable from "@/components/QueryAllTable";
import {findProductionReportPage, queryProductionReportTitleByMonth, exportProductionMonthExcel} from "@/api/wlg/productionMonthReport";

export default {
  name: "productionMonthReport",
  components: {QueryAllTable},
  data() {
    return {
      size: 'small',
      queryLoading: false,
      exportLoading: false,

      filters: {
        projectName: '',
        mold: '',
        cycle: '',
        producitonDateStart:'',
        producitonDateEnd:'',
      },
      columns: [
        {prop: "projectName", label: "项目", minWidth: 120},
        {prop: "code", label: "条件代码", minWidth: 110},
        {prop: "name", label: "条件", minWidth: 110},
        {prop: "planValue", label: "数量", minWidth: 120},
      ],
      pageRequest: {},
      pageResult: {
        tableData:[]
      },

      excelUploadDialogVisible: false,
    }
  },
  methods: {
    // 获取分页数据
    findPage: function (data) {
      if (data !== null) {
        this.pageRequest = data.pageRequest
      }
      this.pageRequest.projectName = this.filters.projectName;
      // this.pageRequest.mold = this.filters.mold;
      // this.pageRequest.cycle = this.filters.cycle;
      this.pageRequest.dateStart = this.filters.producitonDateStart;
      this.pageRequest.dateEnd = this.filters.producitonDateEnd;

      this.queryLoading = true;
      queryProductionReportTitleByMonth(this.pageRequest).then((res) => {
        const responseData = res.data
        if (responseData.code === '000000') {
          this.columns = responseData.data
        }
      }).then(data != null ? data.callback : '')


      findProductionReportPage(this.pageRequest).then((res) => {
        const responseData = res.data
        if (responseData.code === '000000') {
          this.pageResult.records = responseData.data
          this.$message.success(responseData.msg)
        }
        else
        {
          this.pageResult.records = [];
          this.$message.error(responseData.msg + "," + responseData.data);
        }
        this.queryLoading = false;
      }).then(data != null ? data.callback : '')
    },
    exportExcelData(excelFileName) {
      this.pageRequest.projectName = this.filters.projectName;
      // this.pageRequest.mold = this.filters.mold;
      // this.pageRequest.cycle = this.filters.cycle;
      this.pageRequest.dateStart = this.filters.producitonDateStart;
      this.pageRequest.dateEnd = this.filters.producitonDateEnd;

      this.exportLoading = true;
      exportProductionMonthExcel(this.pageRequest).then(res => {
          this.exportLoading = false;
          let url = window.URL.createObjectURL(new Blob([res.data],{type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'}));
          let link = document.createElement('a');
          link.style.display = 'none';
          link.href = url;
          link.setAttribute('download', excelFileName + "-" + new Date().getTime() + ".xlsx");
          document.body.appendChild(link);
          link.click();
      });
    },
    // 时间格式化
    dateTimeFormat: function (row, column) {
      return this.$moment(row[column.property]).format('YYYY-MM-DD HH:mm')
    },
    // 时间格式化
    dateFormat: function (row, column) {
      return this.$moment(row[column.property]).format('YYYY-MM-DD')
    },
    getCurrentMonthFirst () {
      var date = new Date()
      date.setDate(1)
      var month = parseInt(date.getMonth() + 1)
      var day = date.getDate()
      if (month < 10)  month = '0' + month
      if (day < 10)  day = '0' + day
      this.filters.producitonDateStart = date.getFullYear() + '-' + month + '-' + day
    },
    getCurrentMonthLast () {
      var date = new Date()
      var month = parseInt(date.getMonth() + 1)
      var day = date.getDate()
      if (month < 10)  month = '0' + month
      if (day < 10)  day = '0' + day
      this.filters.producitonDateEnd = date.getFullYear() + '-' + month + '-' + day
    },
  },
  mounted()
  {
    this.getCurrentMonthFirst();
    this.getCurrentMonthLast();
  }
}
</script>
