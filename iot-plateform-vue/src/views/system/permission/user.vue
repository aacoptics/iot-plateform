<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-calendar"></i> 系统管理
        </el-breadcrumb-item>
        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="toolbar" style="float:left;padding-top:10px;padding-left:15px;">
        <el-form :inline="true" :size="size">
          <el-form-item>
            <el-input v-model="filters.username" placeholder="用户名"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button icon="fa fa-search" type="primary"
                       @click="findPage(null)">查询
            </el-button>
          </el-form-item>
          <el-form-item>
            <el-button icon="fa fa-plus" type="primary"
                       @click="handleAdd">新增
            </el-button>
          </el-form-item>
        </el-form>
      </div>
      <SysTable :height="220" :highlightCurrentRow="true" :stripe="false"
                :data="pageResult" :columns="columns" :showBatchDelete="false"
                @handleCurrentChange="handleUserSelectChange"
                ref="sysTable"
                @findPage="findPage" @handleEdit="handleEdit" @handleDelete="handleDelete">
      </SysTable>
      <el-dialog :title="operation?'新增':'编辑'" width="80%" v-model="dialogVisible"
                 :close-on-click-modal="false">
        <el-form :model="dataForm" label-width="80px" :rules="dataFormRules" ref="dataForm" :size="size">
          <el-form-item label="Id" prop="id" v-if="false">
            <el-input v-model="dataForm.id" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="用户名" prop="username">
            <el-input v-model="dataForm.username" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input v-model="dataForm.name" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="description">
            <el-input v-model="dataForm.description" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="手机号" prop="mobile">
            <el-input v-model="dataForm.mobile" auto-complete="off"></el-input>
          </el-form-item>
        </el-form>
        <div class="dialog-footer" style="padding-top: 20px;text-align: end">
          <slot name="footer">
            <el-button :size="size" @click="resetSelection">取消</el-button>
            <el-button :size="size" type="primary" @click="submitForm" :loading="editLoading">提交</el-button>
          </slot>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import SysTable from "@/components/SysTable";
import {handleAdd, deleteRole, handleUpdate} from "@/api/system/role";
import {findMenuTree, findRoleMenus} from "@/api/system/menu";
import {findResourceTree, findRoleResource} from "@/api/system/resource";
import {findUserInfoPage, findUserRolesById} from "@/api/system/user";

export default {
  name: "user",
  components: {SysTable},
  data() {
    return {
      roleName: '',
      size: 'small',
      filters: {
        name: ''
      },
      columns: [
        {prop: "username", label: "用户名", minWidth: 80},
        {prop: "name", label: "姓名", minWidth: 100},
        {prop: "mobile", label: "电话", minWidth: 120},
        {prop: "description", label: "描述", minWidth: 120},
        {prop: "updatedBy", label: "更新人", minWidth: 100},
        {prop: "updatedTime", label: "更新时间", minWidth: 120, formatter: this.dateFormat},
        {prop: "createdBy", label: "创建人", minWidth: 120},
        {prop: "createdTime", label: "创建时间", minWidth: 120, formatter: this.dateFormat},
      ],
      pageRequest: {current: 1, size: 10},
      pageResult: {},
      operation: false, // true:新增, false:编辑
      dialogVisible: false, // 新增编辑界面是否显示
      editLoading: false,
      dataFormRules: {
        username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
        name: [{required: true, message: '请输入姓名', trigger: 'blur'}],
      },
      // 新增编辑界面数据
      dataForm: {
        username: '',
        name: '',
        description: '',
        mobile: '',
        isLdap: true,
        password: '',
        checkPass: ''
      },
      selectUser: {},
      menuData: [],
      resourceData: [],
      menuSelections: [],
      menuLoading: false,
      resourceLoading: false,
      authLoading: false,
      menuCheckAll: false,
      resourceCheckAll: false,
      currentUserRoles: [],
      currentRoleResource: [],
      defaultProps: {
        children: 'children',
        label: 'title'
      },
      resourceProps: {
        children: 'children',
        label: 'name'
      }
    }
  },
  methods: {
    // 获取分页数据
    findPage: function (data) {
      if (data !== null) {
        this.pageRequest = data.pageRequest
      }

      this.pageRequest.name = this.filters.name
      findUserInfoPage(this.pageRequest).then((res) => {
        const responseData = res.data
        if (responseData.code === '000000') {
          this.pageResult = responseData.data
          this.findRoleData()
          // this.findResourceTreeData()
        }
      }).then(data != null ? data.callback : '')
    },

    // 批量删除
    handleDelete: function (data) {
      if (data.params.length > 0)
        deleteRole(data.params[0]).then(data.callback)
    },
    // 显示新增界面
    handleAdd: function () {
      this.dialogVisible = true
      this.operation = true
      this.$refs.sysTable.handleClearSelection();
      this.dataForm = {
        id: 0,
        code: '',
        name: '',
        description: ''
      }
    },
    // 显示编辑界面
    handleEdit: function (params) {
      this.dialogVisible = true
      this.operation = false
      this.dataForm = Object.assign({}, params.row)
    },
    // 编辑
    submitForm: function () {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.editLoading = true
            let params = Object.assign({}, this.dataForm)
            let checkedMenuNodes = this.$refs.menuTree.getCheckedNodes(false, true)
            let checkedResourceNodes = this.$refs.resourceTree.getCheckedNodes(true, false)
            let roleMenus = []
            let roleResources = []
            for (let i = 0, len = checkedMenuNodes.length; i < len; i++) {
              roleMenus.push(checkedMenuNodes[i].id)
            }
            for (let i = 0, len = checkedResourceNodes.length; i < len; i++) {
              roleResources.push(checkedResourceNodes[i].id)
            }
            params.resourceIds = roleResources
            params.menuIds = roleMenus
            if (this.operation) {
              handleAdd(params).then((res) => {
                const responseData = res.data
                this.editLoading = false
                if (responseData.code === '000000') {
                  this.$message({message: '操作成功', type: 'success'})
                  this.dialogVisible = false
                  this.$refs['dataForm'].resetFields()
                } else {
                  this.$message({message: '操作失败, ' + responseData.msg, type: 'error'})
                }
                this.findPage(null)
              })
            } else {
              handleUpdate(params).then((res) => {
                const responseData = res.data
                this.editLoading = false
                if (responseData.code === '000000') {
                  this.$message({message: '操作成功', type: 'success'})
                  this.dialogVisible = false
                  this.$refs['dataForm'].resetFields()
                } else {
                  this.$message({message: '操作失败, ' + responseData.msg, type: 'error'})
                }
                this.findPage(null)
              })
            }
          })
        }
      })
    },
    // 获取数据
    findRoleData: function () {
      this.menuLoading = true
      findMenuTree().then((res) => {
        const responseData = res.data
        if (responseData.code === '000000') {
          this.menuData = responseData.data
        }
        this.menuLoading = false
      })
    },
    // 获取数据
    findResourceTreeData: function () {
      this.resourceLoading = true
      findResourceTree().then((res) => {
        const responseData = res.data
        if (responseData.code === '000000') {
          const tempData = {}
          responseData.data.forEach(resource => {
            if (!Object.prototype.hasOwnProperty.call(tempData, resource.type)) {
              tempData[resource.type] = {children: []}
            }
            tempData[resource.type].children.push(resource)
          })
          const resourcesData = []
          for (const key in tempData) {
            const resource = {name: key, children: tempData[key].children}
            resourcesData.push(resource)
          }
          this.resourceData = resourcesData
        }
        this.resourceLoading = false
      })
    },
    // 角色选择改变监听
    handleUserSelectChange(val) {
      if (val == null || val.val == null) {
        this.selectUser = null
        this.$refs.menuTree.setCheckedNodes([])
        this.$refs.resourceTree.setCheckedNodes([])
        return
      }
      this.selectUser = val.val
      findUserRolesById(val.val.id).then((res) => {
        const responseData = res.data
        if (responseData.code === '000000') {
          this.currentUserRoles = responseData.data
          this.$refs.menuTree.setCheckedNodes(responseData.data)
        }
      })
    },
    // 重置选择
    resetSelection() {
      this.menuCheckAll = false
      this.resourceCheckAll = false
      this.$refs.menuTree.setCheckedNodes(this.currentUserRoles)
      this.$refs.resourceTree.setCheckedNodes(this.currentRoleResource)
      this.dialogVisible = false
    },
    // 全选操作
    handleMenuCheckAll() {
      if (this.menuCheckAll) {
        let allMenus = []
        this.checkAll(this.menuData, allMenus)
        this.$refs.menuTree.setCheckedNodes(allMenus)
      } else {
        this.$refs.menuTree.setCheckedNodes([])
      }
    },
    handleResourceCheckAll() {
      if (this.resourceCheckAll) {
        let allResource = []
        this.checkAll(this.resourceData, allResource)
        this.$refs.resourceTree.setCheckedNodes(allResource)
      } else {
        this.$refs.resourceTree.setCheckedNodes([])
      }
    },
    // 递归全选
    checkAll(menuData, allMenus) {
      menuData.forEach(menu => {
        allMenus.push(menu)
        if (menu.children) {
          this.checkAll(menu.children, allMenus)
        }
      });
    },
    // 时间格式化
    dateFormat: function (row, column) {
      return this.$moment(row[column.property]).format('YYYY-MM-DD HH:mm:ss')
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
