<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-calendar"></i> 开发相关
        </el-breadcrumb-item>
        <el-breadcrumb-item>消息队列</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container" style="margin:0; padding:0;border: 0;height:auto;width: 100%;overflow: auto">
      <iframe id='webFrame'
              hight='auto'
              width='100%'
              src="http://iottest.aacoptics.com:15672/"
              frameborder='0'
              scrolling='auto'
     ></iframe>
    </div>
  </div>
</template>
<script>

export default {
  name: 'rabbitmq',
  mounted() {
    this.resizeFrame('webFrame')
  },
  methods: {
    resizeFrame(id) {
      const oIframe = document.getElementById(id);
      const deviceHeight = document.documentElement.clientHeight;
      oIframe.style.height = (Number(deviceHeight) - 158) + 'px'; //数字是页面布局高度差
    }
  }
}
</script>
