<template>
  <div class="crumbs">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item>
        <i class="el-icon-lx-calendar"></i> 注塑机监控
      </el-breadcrumb-item>
      <el-breadcrumb-item>新能源</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
  <div class="container" style="border: 0;padding: 0;height: 100%;width: 100%;overflow: hidden">
    <iframe height='99%'
            width='100%'
            :src=urlAddress
            style="border: 0;padding: 0;">
    </iframe>
  </div>
</template>
<script>

export default {
  name: "WebFrame",
  computed: {
    urlAddress() {
      return this.$route.query.url
    }
  }
}
</script>
