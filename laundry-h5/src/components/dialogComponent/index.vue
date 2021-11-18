<!--弹框模版-->
<template>
  <div class="dialog-container">
    <el-dialog   v-dialogDrag   title="弹框模版" :visible.sync="visible" width="700px"  @close="$emit('update:show', false)" :show="show">

    </el-dialog>
  </div>
</template>

<script>

export default {
  data() {
    return {
      visible: this.show,
    }
  },
  props: {
    show: {
      type: Boolean,
      default: false
    },
    list: {
      type: Object,
      default: function() {
        return {}
      }
    }
  },
  created() {

  },
  watch: {
    show() {
      this.visible = this.show;
      if (this.visible) {
       
      }
    }
  },
  methods: {

  }
};
</script>
<style rel="stylesheet/scss" lang="scss" scoped>

</style>