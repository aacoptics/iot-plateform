<template>
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
