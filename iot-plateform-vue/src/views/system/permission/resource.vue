<template>
  <div>
    <div class="container">
      <div class="toolbar" style="float:left;padding-top:10px;padding-left:15px;">
        <el-form :inline="true" :size="size">
          <el-form-item label="接口类型" prop="type">
            <el-input v-model="filters.type" placeholder="接口类型" clearable></el-input>
          </el-form-item>
          <el-form-item label="名称" prop="name">
            <el-input v-model="filters.name" placeholder="名称" clearable></el-input>
          </el-form-item>
          <el-form-item label="请求方法" prop="method">
            <el-select v-model="filters.method" clearable placeholder="请求方法">
              <el-option
                v-for="item in methodOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
          <el-form :inline="true" :size="size">
            <el-form-item>
              <el-button icon="el-icon-search" type="primary"
                        @click="findPage(null)">查询
              </el-button>
            </el-form-item>
            <el-form-item>
              <el-button icon="el-icon-plus" type="success"
                        @click="handleAdd">新增
              </el-button>
            </el-form-item>
          </el-form>
      </div>
      <SysTable id="condDataTable" :height="460" :highlightCurrentRow="true" :stripe="false"
                :data="pageResult" :columns="columns" :showBatchDelete="false"
                ref="sysTable"
                @findPage="findPage" @handleEdit="handleEdit" @handleDelete="handleDelete">
      </SysTable>



      <el-dialog :title="operation?'新增':'编辑'" width="40%" v-model="dialogVisible"
                 :close-on-click-modal="false">
        <el-form :model="dataForm" label-width="100px" :rules="dataFormRules" ref="dataForm" :size="size">
          <el-form-item label="Id" prop="id" v-if="false">
            <el-input v-model="dataForm.id" auto-complete="off"></el-input>
          </el-form-item>
          <el-row>
            <el-col :span="12">
              <el-form-item label="接口编码" prop="code">
                <el-input v-model="dataForm.code" auto-complete="off" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
          <el-form-item label="接口类型" prop="type">
            <el-input v-model="dataForm.type" auto-complete="off" clearable></el-input>
          </el-form-item>
           </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="名称" prop="name">
                <el-input v-model="dataForm.name" auto-complete="off" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="URL" prop="url">
                <el-input v-model="dataForm.url" auto-complete="off" clearable></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="请求方法" prop="method">
                <el-select v-model="dataForm.method" clearable placeholder="请求方法" style="width:100%">
                  <el-option
                    v-for="item in methodOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="描述" prop="description">
                <el-input v-model="dataForm.description" auto-complete="off" clearable></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div class="dialog-footer" style="padding-top: 20px;text-align: end">
          <slot name="footer">
            <el-button :size="size" @click="cancel">取消</el-button>
            <el-button :size="size" type="primary" @click="submitForm" :loading="editLoading">提交</el-button>
          </slot>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import SysTable from "@/components/SysTable";
import {
  deleteResource,
  findResourcePage,
  handleAdd,
  handleUpdate,
} from "@/api/system/resource";

export default {
  name: "resource",
  components: { SysTable },
  data() {
    return {
      size: "small",
      filters: {
        type: "",
        name: "",
        method: "",
      },
      columns: [
        { prop: "code", label: "接口编码", minWidth: 110 },
        { prop: "type", label: "接口类型", minWidth: 100 },
        { prop: "name", label: "名称", minWidth: 120 },
        { prop: "url", label: "URL", minWidth: 120 },
        { prop: "method", label: "请求方法", minWidth: 100 },
        { prop: "description", label: "描述", minWidth: 150 },
        { prop: "updatedBy", label: "更新人", minWidth: 100 },
        {
          prop: "updatedTime",
          label: "更新时间",
          minWidth: 120,
          formatter: this.dateTimeFormat,
        },
        { prop: "createdBy", label: "创建人", minWidth: 120 },
        {
          prop: "createdTime",
          label: "创建时间",
          minWidth: 120,
          formatter: this.dateTimeFormat,
        },
      ],
      pageRequest: { current: 1, size: 10 },
      pageResult: {},
      operation: false, // true:新增, false:编辑
      dialogVisible: false, // 新增编辑界面是否显示
      editLoading: false,
      dataFormRules: {
        code: [{ required: true, message: "请输入接口编码", trigger: "blur" }],
        type: [{ required: true, message: "请输入接口类型", trigger: "blur" }],
        name: [
          { required: true, message: "请输入接口名称", trigger: "blur" },
        ],
        url: [
          { required: true, message: "请输入URL", trigger: "blur" },
        ],
        method: [
          { required: true, message: "请输入接口方法", trigger: "blur" },
        ],
        description: [
          { required: true, message: "请输入描述", trigger: "blur" },
        ],

      },
      methodOptions:[
        {
          value:"GET",
          label:"GET",
        },
        {
          value:"POST",
          label:"POST",
        },
        {
          value: "DELETE",
          label:"DELETE",
        },
        {
          value: "PUT",
          label:"PUT",
        },
        {
          value: "HEAD",
          label:"HEAD",
        },
        {
          value: "CONNECT",
          label:"CONNECT",
        },{
          value: "OPTIONS",
          label:"OPTIONS",
        },{
          value: "TRACE",
          label:"TRACE",
        },],
      // 新增编辑界面数据
      dataForm: {
        id: 0,
        code: "",
        type: "",
        name: "",
        url: "",
        method: "",
        description: "",
      },
    };
  },
  methods: {
    // 获取分页数据
    findPage: function (data) {
      if (data !== null) {
        this.pageRequest = data.pageRequest;
      }
      this.pageRequest.type = this.filters.type;
      this.pageRequest.name = this.filters.name;
      this.pageRequest.method = this.filters.method;
      findResourcePage(this.pageRequest)
        .then((res) => {
          const responseData = res.data;
          if (responseData.code === "000000") {
            this.pageResult = responseData.data;
          }
        })
        .then(data != null ? data.callback : "");
    },

    // 批量删除
    handleDelete: function (data) {
      if (data.params.length > 0)
        deleteResource(data.params[0]).then(data.callback);
    },
    // 显示新增界面
    handleAdd: function () {
      this.dialogVisible = true;
      this.operation = true;
      this.$refs.sysTable.handleClearSelection();
      this.dataForm = {
        id: 0,
        code: "",
        type: "",
        name: "",
        url: "",
        method: "",
        description: "",
      };
    },

    // 显示编辑界面
    handleEdit: function (params) {
      this.dialogVisible = true;
      this.operation = false;
      this.dataForm = Object.assign({}, params.row);
    },
    // 编辑
    submitForm: function () {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          this.$confirm("确认提交吗？", "提示", {}).then(() => {
            this.editLoading = true;
            let params = Object.assign({}, this.dataForm);
            if (this.operation) {
              handleAdd(params).then((res) => {
                const responseData = res.data;
                this.editLoading = false;
                if (responseData.code === "000000") {
                  this.$message({ message: "操作成功", type: "success" });
                  this.dialogVisible = false;
                  this.$refs["dataForm"].resetFields();
                } else {
                  this.$message({
                    message:
                      "操作失败, " + responseData.msg + "," + responseData.data,
                    type: "error",
                  });
                }
                this.findPage(null);
              });
            } else {
              handleUpdate(params).then((res) => {
                const responseData = res.data;
                this.editLoading = false;
                if (responseData.code === "000000") {
                  this.$message({ message: "操作成功", type: "success" });
                  this.dialogVisible = false;
                  this.$refs["dataForm"].resetFields();
                } else {
                  this.$message({
                    message:
                      "操作失败, " + responseData.msg + "," + responseData.data,
                    type: "error",
                  });
                }
                this.findPage(null);
              });
            }
          });
        }
      });
    },

    // 取消
    cancel() {
      this.dialogVisible = false;
    },

    // 时间格式化
    dateTimeFormat: function (row, column) {
      return this.$moment(row[column.property]).format("YYYY-MM-DD HH:mm");
    },
  },
};
</script>
