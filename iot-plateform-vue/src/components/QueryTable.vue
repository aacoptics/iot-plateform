<template>
  <div>
    <!--表格栏-->
    <el-table ref="queryTable" :data="data.records" :highlight-current-row="highlightCurrentRow"
              @selection-change="selectionChange" :row-class-name="rowClassName"
              @current-change="handleCurrentChange" v-loading="loading" :element-loading-text="action.loading"
              :border="border" :stripe="stripe"
              :show-overflow-tooltip="showOverflowTooltip" :max-height="maxHeight" :height="height" :size="size"
              :align="align" style="width:100%;">
      <el-table-column v-for="column in columns" header-align="left" align="left"
                       :prop="column.prop" :label="column.label" :width="column.width" :min-width="column.minWidth"
                       :fixed="column.fixed" :key="column.prop" :type="column.type" :formatter="column.formatter"
                       :sortable="column.sortable==null?true:column.sortable">
      </el-table-column>
      <slot name="custom-column"></slot>
    </el-table>
    <!--分页栏-->
    <div class="toolbar" style="padding:10px;">
      <el-pagination layout="total, prev, pager, next, jumper" @current-change="refreshPageRequest"
                     :current-page="pageRequest.current" :page-size="pageRequest.size" :total="data.total"
                     style="float:right;">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: 'QueryTable',

  props: {
    columns: Array, // 表格列配置
    data: Object, // 表格分页数据
    permsEdit: String,  // 编辑权限标识
    permsDelete: String,  // 删除权限标识
    size: { // 尺寸样式
      type: String,
      default: 'mini'
    },
    align: {  // 文本对齐方式
      type: String,
      default: 'left'
    },
    maxHeight: {  // 表格最大高度
      type: Number,
      default: 500
    },
    height: {  // 表格最大高度
      type: Number,
      default: 400
    },
    showOperation: {  // 是否显示操作组件
      type: Boolean,
      default: true
    },
    border: {  // 是否显示边框
      type: Boolean,
      default: false
    },
    stripe: {  // 是否显示斑马线
      type: Boolean,
      default: true
    },
    highlightCurrentRow: {  // // 是否高亮当前行
      type: Boolean,
      default: true
    },
    showOverflowTooltip: {  // 是否单行显示
      type: Boolean,
      default: true
    },

    rowClassName: [String, Function]
  },
  data() {
    return {
      action: {
        operation: "操作",
        loading: "拼命加载中",

      },
      // 分页信息
      pageRequest: {
        current: 1,
        size: 10
      },
      loading: false,  // 加载标识
      selections: []  // 列表选中列
    }
  },
  methods: {
    // 分页查询
    findPage: function () {
      this.loading = true
      let callback = () => {
        this.loading = false
      }
      this.$emit('findPage', {pageRequest: this.pageRequest, callback: callback})
    },
    // 选择切换
    selectionChange: function (selections) {
      this.selections = selections
      this.$emit('selectionChange', {selections: selections})
    },
    // 选择切换
    handleCurrentChange: function (val) {
      this.$emit('handleCurrentChange', {val: val})
    },
    // 换页刷新
    refreshPageRequest: function (pageNum) {
      this.pageRequest.current = pageNum
      this.findPage()
    },

    handleClearSelection: function () {
      this.$refs.queryTable.setCurrentRow(null);
    },
    
  },
  mounted() {
    // this.refreshPageRequest(1)
  }
}
</script>
