<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-calendar"></i> 模具IoT
        </el-breadcrumb-item>
        <el-breadcrumb-item>刀具寿命管控</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">

      <el-row>
        <el-col :span="8">
          <el-row style="padding: 10px">
            <el-input style="width: 200px" v-model="monitorNo" placeholder="请输入监控号"></el-input>
          </el-row>
          <el-row style="padding: 10px">
            <el-button type="primary" icon="el-icon-search" @click="getByMonitorNo()">查询</el-button>
          </el-row>
          <el-row style="padding: 10px">
            <el-button type="success" :loading="saveBtnLoading" icon="el-icon-check" @click="saveEditInfo()">保存</el-button>
          </el-row>
        </el-col>
        <el-col :span="16">
          <el-upload
              class="upload-demo"
              :before-upload="beforeUpload"
              accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
              :http-request="uploadExcel"
              action=""
              :multiple="false"
              :show-file-list="false"
              drag>
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将Excel文件拖到此处，或<em>点击上传</em></div>
          </el-upload>
        </el-col>
      </el-row>

      <el-table
          id="ToolLifeTable"
          :data="moldToolLifeSheet"
          border
          :height="tableMaxHeight"
          :key="1"
          header-row-class-name="tableHead"
          style="width: 100%;margin-top: 10px"
          v-loading="toolLifeLoading">
        <el-table-column :width=100 prop="monitorNo" label="监控号"></el-table-column>
        <el-table-column prop="programName" label="程序名"></el-table-column>
        <el-table-column :width=200 prop="workpiece" label="工件名称"></el-table-column>
        <el-table-column prop="material" label="材质"></el-table-column>
        <el-table-column prop="route" label="工序"></el-table-column>
        <el-table-column :width=130 prop="toolName" label="刀具名称"></el-table-column>
        <el-table-column prop="toolDiameter" label="刀具直径"></el-table-column>
        <el-table-column prop="toolNo" label="刀号"></el-table-column>
        <el-table-column prop="type" label="类型"></el-table-column>
        <el-table-column prop="margin" label="余量"></el-table-column>
        <el-table-column :width=100 prop="toolValidLength" label="刀具有效刃长"></el-table-column>
        <el-table-column prop="brand" label="品牌"></el-table-column>
        <el-table-column :width=120 prop="maxDepth" label="最大深度（Z）"></el-table-column>
        <el-table-column prop="workTime" label="加工时间"></el-table-column>
        <el-table-column prop="cutDepth" label="切深"></el-table-column>
        <el-table-column prop="feed" label="进给"></el-table-column>
        <el-table-column prop="remark" label="备注"></el-table-column>
        <el-table-column :width=100 fixed="left" prop="machineNo" label="机台号">
          <template v-slot="scope">
              <el-input v-model="scope.row.machineNo" placeholder="机台号"></el-input>
          </template>
        </el-table-column>
        <el-table-column :width=100 fixed="left" prop="toolPos" label="刀位">
          <template v-slot="scope">
            <el-input v-model="scope.row.toolPos" placeholder="刀位"></el-input>
          </template>
        </el-table-column>
        <el-table-column :width=120 fixed="left" prop="toolCode" label="刀具编码">
          <template v-slot="scope">
            <el-input v-model="scope.row.toolCode" placeholder="刀具编码"></el-input>
          </template>
        </el-table-column>
        <el-table-column :width=200 prop="createDateTime" label="创建时间"></el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import {uploadExcel, getByMonitorNo, updateToolInfo} from "@/api/iot/mold";

export default {
  name: "toolLife",
  data() {
    return {
      monitorNo: "",
      moldToolLifeSheet: [],
      toolLifeLoading: false,
      saveBtnLoading: false
    }
  },
  computed: {
    tableMaxHeight() {
      return window.innerHeight - 370 + 'px';
    }
  },
  methods: {
    beforeUpload(file) {
      const isExcel = file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' || file.type === 'application/vnd.ms-excel'
      if (!isExcel) {
        this.$message.error('只能上传xlsx, xls格式的文件！')
        return false
      }
    },
    saveEditInfo(){
      this.saveBtnLoading = true
      updateToolInfo(this.moldToolLifeSheet).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          this.$message.success('保存成功！')
        } else {
          this.$message.error('保存失败！' + responseData.msg)
        }
        this.saveBtnLoading = false
      }).catch((err) => {
        this.$message.error(err.message)
        this.saveBtnLoading = false
      })
    },
    uploadExcel(params) {
      uploadExcel(params).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          this.$message.success('上传成功！')
        } else {
          this.$message.error('上传失败！' + responseData.msg)
        }
      }).catch((err) => {
        this.$message.error(err.message)
      })
    },
    getByMonitorNo() {
      this.toolLifeLoading = true
      getByMonitorNo(this.monitorNo).then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          this.moldToolLifeSheet = responseData.data;
        } else {
          this.$message.error('获取失败！' + responseData.msg)
        }
        this.toolLifeLoading = false
      }).catch((err) => {
        this.$message.error(err)
        this.toolLifeLoading = false
      })
    }
  },
};
</script>
