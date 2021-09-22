<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          B客户
        </el-breadcrumb-item>
        <el-breadcrumb-item>MTF托盘维护</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <el-row>
        <el-col :span="8">
          <div style="margin-bottom: 20px">
            <el-button type="success" icon="el-icon-search">查询</el-button>
            <el-button type="primary" icon="el-icon-edit">修改</el-button>
            <el-button type="warning" icon="el-icon-document-add" @click="mtfTrayNoDialog = true">新增</el-button>
            <el-button type="danger" icon="el-icon-delete">删除</el-button>
          </div>
          <el-dialog
              title="新增MTF托盘二维码"
              v-model="mtfTrayNoDialog"
              width="30%">
            <label style="font-weight: bold">MTF托盘二维码</label>
            <el-input style="margin-top: 10px" v-model="mtfTrayNoDialogForm.mtfTrayNo"
                      placeholder="请输入MTF托盘二维码"></el-input>
            <template #footer>
    <span class="dialog-footer">
      <el-button @click="mtfTrayNoDialog = false">取消</el-button>
      <el-button type="primary" @click="mtfTrayNoDialog = false">确定</el-button>
    </span>
            </template>
          </el-dialog>
          <el-table
              :data="mtfTrayConfigInfo"
              stripe
              border
              style="width: 95%">
            <el-table-column
                type="index"
                width="30">
            </el-table-column>
            <el-table-column
                prop="date"
                label="MTF托盘二维码"
                width="120">
            </el-table-column>
            <el-table-column
                prop="name"
                label="更新时间"
                width="180">
            </el-table-column>
            <el-table-column
                prop="address"
                label="创建用户">
            </el-table-column>
          </el-table>
        </el-col>
        <el-col :span="8">
          <div style="margin-bottom: 20px">
            <el-button type="success" icon="el-icon-search">查询</el-button>
            <el-button type="primary" icon="el-icon-edit">修改</el-button>
            <el-button type="warning" icon="el-icon-document-add">新增</el-button>
            <el-button type="danger" icon="el-icon-delete">删除</el-button>
          </div>
          <el-table
              :data="mtfTrayConfigInfo"
              stripe
              border
              style="width: 95%">
            <el-table-column
                type="index"
                width="30">
            </el-table-column>
            <el-table-column
                prop="date"
                label="MTF托盘二维码"
                width="120">
            </el-table-column>
            <el-table-column
                prop="name"
                label="更新时间"
                width="180">
            </el-table-column>
            <el-table-column
                prop="address"
                label="创建用户">
            </el-table-column>
          </el-table>
        </el-col>
        <el-col :span="8">
          <div style="margin-bottom: 20px">
            <el-button type="success" icon="el-icon-search">查询</el-button>
            <el-button type="primary" icon="el-icon-edit">修改</el-button>
            <el-button type="warning" icon="el-icon-document-add">新增</el-button>
            <el-button type="danger" icon="el-icon-delete">删除</el-button>
          </div>
          <el-table
              :data="mtfTrayConfigInfo"
              stripe
              border
              style="width: 95%">
            <el-table-column
                type="index"
                width="30">
            </el-table-column>
            <el-table-column
                prop="date"
                label="MTF托盘二维码"
                width="120">
            </el-table-column>
            <el-table-column
                prop="name"
                label="更新时间"
                width="180">
            </el-table-column>
            <el-table-column
                prop="address"
                label="创建用户">
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "MtfTrayConfig",
  data() {
    return {
      mtfTrayNoDialogForm: {
        mtfTrayNo: ''
      },
      mtfTrayNoDialog: false,
      mtfTrayConfigInfo: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄'
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1519 弄'
      }, {
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1516 弄'
      }]
    }
  },
  methods: {}
}
</script>

<style scoped>

</style>
