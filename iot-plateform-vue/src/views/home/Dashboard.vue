<template>
  <div>

    <div class="container" style="margin:0; padding:0;border: 0;height:auto;width: 100%;overflow: auto">
      <iframe id='webFrame'
              hight='auto'
              width='100%'
              src="http://10.69.76.50:8082/webroot/decision/view/form?viewlet=iot%252Fiot_001_dashboard.frm&ref_t=design"
              frameborder='0'
              scrolling='auto'
      ></iframe>
    </div>
  </div>
</template>
<script>

export default {
  name: 'nacos',
  mounted() {
    this.resizeFrame('webFrame')
  },
  methods: {
    resizeFrame(id) {
      const oIframe = document.getElementById(id);
      const deviceHeight = document.documentElement.clientHeight;
      oIframe.style.height = (Number(deviceHeight) - 125) + 'px'; //数字是页面布局高度差
    }
  }
}
</script>
