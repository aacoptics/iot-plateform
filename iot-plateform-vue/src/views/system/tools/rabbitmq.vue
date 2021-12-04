<template>
  <div>
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
