<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-calendar"></i> 系统管理
        </el-breadcrumb-item>
        <el-breadcrumb-item>角色管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="toolbar" style="float:left;padding-top:10px;padding-left:15px;">
        <el-form :inline="true" :size="size">
          <el-form-item>
            <el-input v-model="filters.code" placeholder="角色名"></el-input>
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
                @handleCurrentChange="handleRoleSelectChange"
                @findPage="findPage" @handleEdit="handleEdit" @handleDelete="handleDelete">
      </SysTable>
      <el-dialog :title="operation?'新增':'编辑'" width="40%" v-model="dialogVisible"
                 :close-on-click-modal="false">
        <el-form :model="dataForm" label-width="80px" :rules="dataFormRules" ref="dataForm" :size="size">
          <el-form-item label="Id" prop="id" v-if="false">
            <el-input v-model="dataForm.id" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="角色代码" prop="code">
            <el-input v-model="dataForm.code" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="角色名称" prop="name">
            <el-input v-model="dataForm.name" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="角色描述" prop="description">
            <el-input v-model="dataForm.description" auto-complete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button :size="size" @click="dialogVisible = false">取消</el-button>
          <el-button :size="size" type="primary" @click="submitForm" :loading="editLoading">提交</el-button>
        </div>
      </el-dialog>
      <div class="menu-container" :v-if="true" style="padding-top: 10px">
        <div class="menu-header">
          <span><B>角色菜单授权</B></span>
        </div>
        <el-tree :data="menuData"
                 node-key="id"
                 size="mini"
                 show-checkbox
                 :props="defaultProps"
                 style="width: 100%;padding-top:20px;" ref="menuTree"
                 v-loading="menuLoading"
                 element-loading-text="拼命加载中"
                 highlight-current>
          <template #default="{ node, data }">
            <div style="width: 70%;font-weight: bold;font-family: 'Microsoft YaHei',serif">
              <el-row>
                <el-col :span="4">
                  <span style="text-align:center"><i :class="data.icon" style="margin-right: 10px"></i>{{ data.title }}</span>
                </el-col>
                <el-col :span="3">
               <span style="text-align:center">
                 <el-tag :type="data.parentId === '-1' ? 'success' : 'info'" size="small">
                   {{ data.parentId === '-1' ? '顶级菜单' : '菜单' }}
                 </el-tag>
               </span>
                </el-col>
                <el-col :span="6">
                  <span style="text-align:center;color: #20a0ff">{{ data.path ? data.path : '\t' }}</span>
                </el-col>
              </el-row>
            </div>
          </template>
        </el-tree>
        <div style="float:left;padding-left:24px;padding-top:12px;padding-bottom:4px;">
          <el-checkbox v-model="checkAll" @change="handleCheckAll" :disabled="hasCheckRole"><b>全选</b>
          </el-checkbox>
        </div>
        <div style="float:right;padding-right:15px;padding-top:4px;padding-bottom:4px;">
          <el-button type="primary" @click="resetSelection" :disabled="hasCheckRole">重置</el-button>
          <el-button type="primary" @click="submitAuthForm" :loading="authLoading" :disabled="hasCheckRole">提交
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SysTable from "@/components/SysTable";
import {findPage, handleAdd, deleteRole, handleUpdate} from "@/api/system/role";
import {format} from "@/utils/datetime"
import {findMenuTree, findRoleMenus} from "@/api/system/menu";

export default {
  name: "role",
  components: {SysTable},
  computed: {
    hasCheckRole() {
      return this.selectRole.id == null
    }
  },
  data() {
    return {
      roleName: '',
      size: 'small',
      filters: {
        code: ''
      },
      columns: [
        {prop: "code", label: "角色代码", minWidth: 80},
        {prop: "name", label: "角色名称", minWidth: 100},
        {prop: "description", label: "角色描述", minWidth: 120},
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
        code: [
          {required: true, message: '请输入角色代码', trigger: 'blur'}
        ],
        name: [
          {required: true, message: '请输入角色名称', trigger: 'blur'}
        ]
      },
      // 新增编辑界面数据
      dataForm: {
        code: '',
        name: '',
        description: ''
      },
      selectRole: {},
      menuData: [],
      menuSelections: [],
      menuLoading: false,
      authLoading: false,
      checkAll: false,
      currentRoleMenus: [],
      defaultProps: {
        children: 'children',
        label: 'title'
      }
    }
  },
  methods: {
    // 获取分页数据
    findPage: function (data) {
      if (data !== null) {
        this.pageRequest = data.pageRequest
      }
      this.pageRequest.code = this.filters.code
      findPage(this.pageRequest).then((res) => {
        const responseData = res.data
        if (responseData.code === '000000') {
          this.pageResult = responseData.data
          this.findTreeData()
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
      this.dataForm = {
        id: 0,
        name: '',
        remark: ''
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
    findTreeData: function () {
      this.menuLoading = true
      findMenuTree().then((res) => {
        const responseData = res.data
        if (responseData.code === '000000') {
          this.menuData = responseData.data
        }
        this.menuLoading = false
      })
    },
    // 角色选择改变监听
    handleRoleSelectChange(val) {
      if (val == null || val.val == null) {
        return
      }
      this.selectRole = val.val
      findRoleMenus(val.val.id).then((res) => {
        const responseData = res.data
        if (responseData.code === '000000') {
          this.currentRoleMenus = responseData.data
          this.$refs.menuTree.setCheckedNodes(responseData.data)
        }
      })
    },
    // 重置选择
    resetSelection() {
      this.checkAll = false
      this.$refs.menuTree.setCheckedNodes(this.currentRoleMenus)
    },
    // 全选操作
    handleCheckAll() {
      if (this.checkAll) {
        let allMenus = []
        this.checkAllMenu(this.menuData, allMenus)
        this.$refs.menuTree.setCheckedNodes(allMenus)
      } else {
        this.$refs.menuTree.setCheckedNodes([])
      }
    },
    // 递归全选
    checkAllMenu(menuData, allMenus) {
      menuData.forEach(menu => {
        allMenus.push(menu)
        if (menu.children) {
          this.checkAllMenu(menu.children, allMenus)
        }
      });
    },
    // 角色菜单授权提交
    submitAuthForm() {
      let roleId = this.selectRole.id
      if ('Admin' === this.selectRole.name) {
        this.$message({message: '超级管理员拥有所有菜单权限，不允许修改！', type: 'error'})
        return
      }
      this.authLoading = true
      let checkedNodes = this.$refs.menuTree.getCheckedNodes(false, true)
      let roleMenus = []
      for (let i = 0, len = checkedNodes.length; i < len; i++) {
        let roleMenu = {roleId: roleId, menuId: checkedNodes[i].id}
        roleMenus.push(roleMenu)
      }
      this.$api.role.saveRoleMenus(roleMenus).then((res) => {
        if (res.code == 200) {
          this.$message({message: '操作成功', type: 'success'})
        } else {
          this.$message({message: '操作失败, ' + res.msg, type: 'error'})
        }
        this.authLoading = false
      })
    },
    // 时间格式化
    dateFormat: function (row, column, cellValue, index) {
      return format(row[column.property])
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
