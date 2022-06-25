<template>
  <div>
    图片比对

  </div>
</template>

<script>
export default {
  methods: {
    readFile: function (file) {
        return new Promise((resolve) => {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.addEventListener("load", function () {
                resolve(reader.result);
            }, false)
        })
    }
  }
}
</script>

<style>

</style>