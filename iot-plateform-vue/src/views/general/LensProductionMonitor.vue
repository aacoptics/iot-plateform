<template>
  <div>
    <div class="container">

      <el-form :inline="true" label-width="80px">
        <el-form-item label="厂区" prop="site">
          <el-select v-model="site" placeholder="请选择厂区" style="width:100%">
            <el-option
                v-for="item in siteList"
                :key="item.code"
                :label="item.name"
                :value="item.code"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <el-row style="height: 30px;margin-bottom: 20px;margin-top: 20px" align="middle">
        <span class="status-legend" style="background-color: rgba(59,162,114,1)"></span>
        <span class="status-legend-text">自动运转</span>
        <span class="status-legend"
              style="background-color: rgba(238,102,102,1)"></span>
        <span class="status-legend-text">报警</span>
        <span class="status-legend"
              style="background-color: rgba(252,132,82,1)"></span>
        <span class="status-legend-text">其他</span>
        <span class="status-legend"
              style="background-color: gray"></span>
        <span class="status-legend-text">离线</span>
      </el-row>

      <el-row class="row-common-style" align="middle">
        <el-col class="col-header-style" :span="4">工段</el-col>
        <el-col class="col-header-style" :span="12">注塑</el-col>
        <el-col class="col-header-style" :span="4">镀膜</el-col>
        <el-col class="col-header-style" :span="4">组装</el-col>
      </el-row>
      <el-row class="row-common-style" align="middle">
        <el-col class="col-header-style" :span="4">设备</el-col>
        <el-col class="col-header-style" :span="4">注塑机</el-col>
        <el-col class="col-header-style" :span="4">剪切机</el-col>
        <el-col class="col-header-style" :span="4">包料机</el-col>
        <el-col class="col-header-style" :span="4">镀膜机</el-col>
        <el-col class="col-header-style" :span="4">组装机</el-col>
      </el-row>
      <el-row class="row-common-style" align="middle">
        <el-col class="col-header-style" :span="4">采集率</el-col>
        <el-col class="col-common-style" :span="4">90%</el-col>
        <el-col class="col-common-style" :span="4">90%</el-col>
        <el-col class="col-common-style" :span="4">90%</el-col>
        <el-col class="col-common-style" :span="4">90%</el-col>
        <el-col class="col-common-style" :span="4">90%</el-col>
      </el-row>
      <el-row class="row-common-style" align="middle">
        <el-col class="col-header-style" :span="4">设备状态</el-col>
        <el-col class="col-common-style" :span="4">
          <el-row>
            <el-col :span="6">
                <span class="typeItem_orderNum"
                      style="background-color: rgba(59,162,114,1)">12</span>
            </el-col>
            <el-col :span="6">
                <span class="typeItem_orderNum"
                      style="background-color: rgba(238,102,102,1)">12</span>
            </el-col>
            <el-col :span="6">
                <span class="typeItem_orderNum"
                      style="background-color: rgba(252,132,82,1)">12</span>
            </el-col>
            <el-col :span="6">
                <span class="typeItem_orderNum"
                      style="background-color: gray">12</span>
            </el-col>
          </el-row>
        </el-col>
        <el-col class="col-common-style" :span="4">
          <el-row>
            <el-col :span="6">
                <span class="typeItem_orderNum"
                      style="background-color: rgba(59,162,114,1)">12</span>
            </el-col>
            <el-col :span="6">
                <span class="typeItem_orderNum"
                      style="background-color: rgba(238,102,102,1)">12</span>
            </el-col>
            <el-col :span="6">
                <span class="typeItem_orderNum"
                      style="background-color: rgba(252,132,82,1)">12</span>
            </el-col>
            <el-col :span="6">
                <span class="typeItem_orderNum"
                      style="background-color: gray">12</span>
            </el-col>
          </el-row>
        </el-col>
        <el-col class="col-common-style" :span="4">
          <el-row>
            <el-col :span="6">
                <span class="typeItem_orderNum"
                      style="background-color: rgba(59,162,114,1)">12</span>
            </el-col>
            <el-col :span="6">
                <span class="typeItem_orderNum"
                      style="background-color: rgba(238,102,102,1)">12</span>
            </el-col>
            <el-col :span="6">
                <span class="typeItem_orderNum"
                      style="background-color: rgba(252,132,82,1)">12</span>
            </el-col>
            <el-col :span="6">
                <span class="typeItem_orderNum"
                      style="background-color: gray">12</span>
            </el-col>
          </el-row>
        </el-col>
        <el-col class="col-common-style" :span="4">
          <el-row>
            <el-col :span="6">
                <span class="typeItem_orderNum"
                      style="background-color: rgba(59,162,114,1)">12</span>
            </el-col>
            <el-col :span="6">
                <span class="typeItem_orderNum"
                      style="background-color: rgba(238,102,102,1)">12</span>
            </el-col>
            <el-col :span="6">
                <span class="typeItem_orderNum"
                      style="background-color: rgba(252,132,82,1)">12</span>
            </el-col>
            <el-col :span="6">
                <span class="typeItem_orderNum"
                      style="background-color: gray">12</span>
            </el-col>
          </el-row>
        </el-col>
        <el-col class="col-common-style" :span="4">
          <el-row>
            <el-col :span="6">
                <span class="typeItem_orderNum"
                      style="background-color: rgba(59,162,114,1)">12</span>
            </el-col>
            <el-col :span="6">
                <span class="typeItem_orderNum"
                      style="background-color: rgba(238,102,102,1)">12</span>
            </el-col>
            <el-col :span="6">
                <span class="typeItem_orderNum"
                      style="background-color: rgba(252,132,82,1)">12</span>
            </el-col>
            <el-col :span="6">
                <span class="typeItem_orderNum"
                      style="background-color: gray">12</span>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <el-row class="row-common-style" align="middle">
        <el-col class="col-header-style" :span="4">运行时长</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
      </el-row>
      <el-row class="row-common-style" align="middle">
        <el-col class="col-header-style" :span="4">停机时长-手动</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
      </el-row>
      <el-row class="row-common-style" align="middle">
        <el-col class="col-header-style" :span="4">停机时长-故障</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
      </el-row>
      <el-row class="row-common-style" align="middle">
        <el-col class="col-header-style" :span="4">停机占比</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
      </el-row>
      <el-row class="row-common-style" align="middle">
        <el-col class="col-header-style" :span="4">故障次数</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
        <el-col class="col-common-style" :span="4">123</el-col>
      </el-row>
    </div>
  </div>
</template>

<script>

export default {
  name: "dashboard",
  data() {
    return {
      site: 2210,
      siteList:[
        {
          name: '新能源',
          code: 2210
        },
        {
          name: '新北',
          code: 5060
        },
        {
          name: '苏州',
          code: 2090
        }
      ],
      dataInfo: []
    };
  },
  created() {

  },
  beforeUnmount() {
  },
  mounted() {
  },
  computed: {

  },
  methods: {

  }
}
</script>

<style scoped>

.row-common-style {
  font-family: 楷体, serif;
  text-align: center;
  font-size: 18px;
  height: 50px;
}

.col-common-style {
  border: 1px solid #ebeef5;
  line-height: 50px;
}

.col-header-style {
  border: 1px solid #ebeef5;
  line-height: 50px;
  font-weight: bold;
  background-color: #f5f7fa;
  color: #303133;
}

.status-legend {
  width: 14px;
  height: 14px;
  border-radius: 50%;
  line-height: 30px;
}

.status-legend-text {
  margin-left: 5px;
  margin-right: 20px;
  font-family: 楷体,serif
}
</style>
