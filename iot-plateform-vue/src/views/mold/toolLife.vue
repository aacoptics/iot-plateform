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
        <el-col :span="10">
          <el-row style="padding: 10px">
            <el-input style="width: 200px;margin-right: 10px" v-model="monitorNo" placeholder="请输入监控号"></el-input>
            <el-button style="margin-right: 10px" type="primary" icon="el-icon-search" @click="getByMonitorNo(null)">查询
            </el-button>
            <el-button style="margin-right: 10px" type="success" :loading="saveBtnLoading" icon="el-icon-check"
                       @click="saveEditInfo()">保存
            </el-button>
          </el-row>
          <el-row style="padding: 10px">
            <el-select v-model="machineName" filterable placeholder="请选择机台号">
              <el-option
                  v-for="item in machineNameList"
                  :key="item.fequipName"
                  :label="item.fequipName"
                  :value="item.fequipName"
              >
              </el-option>
            </el-select>
          </el-row>
          <el-row style="padding: 10px;float: left">

          </el-row>
        </el-col>
        <el-col :span="14">
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
          v-loading="toolLifeLoading"
          @cell-click="tabClick">
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
            <span v-if="machineName.length === 0">{{scope.row.machineNo}}</span>
            <span v-else>{{machineName}}</span>
          </template>
        </el-table-column>
        <el-table-column :width=120 fixed="left" prop="handleCode" label="刀柄编码">
          <template v-slot="scope">
            <el-select-v2
                size="mini"
                v-if="scope.row.isSelected"
                v-model="scope.row.matInfo"
                filterable
                :options="options"
                value-key="handleCode"
                @change="checkDiameter(scope.row, scope.row.toolDiameter)"
                @blur="cellEvent(scope.row)"
                placeholder="请选择"/>
            <span  v-else :style="getFontColor(scope.row.isCheck)">{{scope.row.matInfo.handleCode}}</span>
          </template>
        </el-table-column>
        <el-table-column :width=120 fixed="left" prop="toolCode" label="刀具编码">
          <template v-slot="scope">
            <span :style="getFontColor(scope.row.isCheck)">{{ scope.row.matInfo.toolCode }}</span>
          </template>
        </el-table-column>
        <el-table-column :width=130 fixed="left" prop="matCode" label="物料号">
          <template v-slot="scope">
            <span :style="getFontColor(scope.row.isCheck)">{{ scope.row.matInfo.matCode }}</span>
          </template>
        </el-table-column>
        <el-table-column :width=230 fixed="left" prop="matName" label="物料名称">
          <template v-slot="scope">
            <span :style="getFontColor(scope.row.isCheck)">{{ scope.row.matInfo.matName }}</span>
          </template>
        </el-table-column>
        <el-table-column :width=200 prop="createDateTime" label="创建时间"></el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import {uploadExcel, getByMonitorNo, updateToolInfo, getMachineList, getMatInfoList} from "@/api/iot/mold";

export default {
  name: "toolLife",
  created() {
    this.getAllMachine()
    this.getAllMatInfo()
  },
  data() {
    return {
      rules: {
        diameter: {}
      },
      selectLoading: false,
      machineName: "",
      machineNameList: [],
      matInfoList: [],
      matInfo: {},
      monitorNo: "",
      moldToolLifeSheet: [],
      toolLifeLoading: false,
      saveBtnLoading: false
    }
  },
  computed: {
    tableMaxHeight() {
      return window.innerHeight - 370 + 'px';
    },
    options(){
      const list = [];
      for (let i = 0; i < this.matInfoList.length; i++) {
        list.push({label: this.matInfoList[i].handleCode, value: this.matInfoList[i]})
      }
      return list
    }
  },
  methods: {
    cellEvent (row) {
      row.isSelected = !row.isSelected
    },
    tabClick(row, column) {
      switch (column.label) {
        case '刀柄编码':
          this.cellEvent(row)
          break
        default: return
      }
    },
    beforeUpload(file) {
      const isExcel = file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' || file.type === 'application/vnd.ms-excel'
      if (!isExcel) {
        this.$message.error('只能上传xlsx, xls格式的文件！')
        return false
      }
    },
    saveEditInfo() {
      for (let i = 0; i < this.moldToolLifeSheet.length; i++) {
        if (this.moldToolLifeSheet[i].isCheck != null && !this.moldToolLifeSheet[i].isCheck) {
          this.$message.error('保存失败，存在直径不匹配的刀柄（表格已标红）')
          return;
        }
      }
      if (this.machineName.length > 0) {
        for (let i = 0; i < this.moldToolLifeSheet.length; i++) {
          this.moldToolLifeSheet[i].machineNo = this.machineName
        }
      }
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
          const monitorNo = responseData.data;
          this.getByMonitorNo(monitorNo)
          this.$message.success('上传成功！')
        } else {
          this.$message.error('上传失败！' + responseData.msg)
        }
      }).catch((err) => {
        this.$message.error(err)
      })
    },
    getByMonitorNo(monitorNo) {
      this.toolLifeLoading = true
      if(monitorNo == null){
        monitorNo = this.monitorNo;
      }
      getByMonitorNo(monitorNo).then((response) => {
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
    },
    getAllMachine() {
      getMachineList().then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          this.machineNameList = responseData.data;
        } else {
          this.$message.error('获取失败！' + responseData.msg)
        }
      }).catch((err) => {
        this.$message.error(err)
      })
    },
    getAllMatInfo() {
      getMatInfoList().then((response) => {
        const responseData = response.data
        if (responseData.code === '000000') {
          this.matInfoList = responseData.data;
        } else {
          this.$message.error('获取失败！' + responseData.msg)
        }
      }).catch((err) => {
        this.$message.error(err)
      })
    },
    checkDiameter(row, diameter) {
      const reg = new RegExp('(D|E)(([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9]))');
      const value = row.matInfo.matName.match(reg)[0]
      const matDiameter = value.substring(1, value.length)
      if (matDiameter !== diameter) {
        row.isCheck = false
        this.$message.error('直径不匹配，请重新选择！')
      } else {
        row.isCheck = true
      }
      this.cellEvent(row)
    },
    getFontColor(isCheck) {
      return isCheck == null || isCheck ? 'color:black' : 'color:red'
    }
  },
};
</script>
