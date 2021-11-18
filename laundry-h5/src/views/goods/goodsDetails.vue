<template>
  <div v-loading="loading">
    <div>
    <el-card style="margin: 10px;padding: 10px;" shadow="always">
      <img style="max-height: 300px" :src="fileBaseUrl + goods.picUrl"/>
    </el-card>
    </div>
    <div>
      <el-card style="margin: 10px;" shadow="always">
        <table id="goodsData" class="el-table el-table--border el-table-column--selection">
          <tr>
            <th>名称：</th>
            <td colspan="5">{{goods.name}}</td>
          </tr>
          <tr>
            <th>商户：</th><td>{{goods.merchantId}}</td>
            <th>类型：</th><td>{{goods.categoryId}}</td>
            <th>是否上架：</th><td>{{goods.onSale?'是':'否'}}</td>
          </tr>
          <tr>
            <th>单价：</th><td>{{goods.price}}</td>
            <th>零售价：</th><td>{{goods.retailPrice}}</td>
            <th>市场价：</th><td>{{goods.marketPrice}}</td>
          </tr>
          <tr>
            <th>排序：</th><td>{{goods.sortOrder}}</td>
            <th></th><td></td>
            <th></th><td></td>
          </tr>
          <tr>
            <th>服务说明：</th><td colspan="5">{{goods.serviceDesc}}</td>
          </tr>
          <tr>
            <th>简介：</th><td colspan="5">{{goods.info}}</td>
          </tr>
        </table>
      </el-card>
    </div>
  </div>
</template>

<script>
  import {ajax} from '@/api/ajax';
    export default {
        name: "goods-details",
      data () {
        return {
          loading:false,
          baseUrl : process.env.BASE_API,
          fileBaseUrl : process.env.FILE_BASE_URL,
          uploadUrl: process.env.BASE_API + "/fileUpload",
          dialogImageUrl : null,
          imageDialogVisible : false,
          id: '',
          goods: {
            id : null,
            name : null,
            merchantId : null,
            categoryId : null,
            onSale : null,
            info : null,
            serviceDesc : null,
            details : null,
            sortOrder : null,
            picUrl : null,
            price : null,
            retailPrice : null,
            marketPrice : null,
          },
          imageList : [],
          imageUploadRequestParams : {uploadPath: 'goods/pic'}
        }
      },
      created(){
        this.loading=true;
        this.getParams();
        //获取商品信息
        ajax("/goods/getById",{id : this.id}).then(response => {
          this.goods=response.data.obj;
          this.loading=false;
        }).catch(reason => {
          this.loading=false;
        });
        //获取商品图片
        // ajax("/goods/getGoodsImages",{id : this.id}).then(response => {
        //   var imgs = response.data.obj;
        //   if(imgs != null){
        //     for(var i=0; i< imgs.length;i++){
        //       imgs[i].url = this.fileBaseUrl + "/" + imgs[i].url
        //     }
        //     this.imageList = imgs;
        //   }
        // });
      },
      methods: {
        getParams () {
          // 取到路由带过来的参数
          this.id = this.$route.params.id;
        },
        handleRemove(file, fileList) {
          console.log(file, fileList);
          ajax("/goods/deleteGoodsImage", {id : file.id}).then(response=>{
            this.$notify.success({title: '信息', message: "删除图片成功", position: 'bottom-right'});
          });
        },
        handlePictureCardPreview(file) {
          this.dialogImageUrl = file.url;
          this.imageDialogVisible = true;
        },
        saveGoodsImage(response){
          ajax("/goods/saveGoodsImage", {
            id:this.id,
            url:response.obj
          }).then(response => {
            var imgs = response.data.obj;
            if(imgs != null){
              for(var i=0; i< imgs.length;i++){
                imgs[i].url = this.fileBaseUrl + imgs[i].url
              }
              this.imageList = imgs;
            }
            this.$notify.success({title: '信息', message: "保存图片成功", position: 'bottom-right'});
          })
        }
      }
    }
</script>

<style scoped>
#goodsData tr th {
  width: 10%;
  padding-right: 10px;
  text-align: right;
}
#goodsData tr td {
  padding-left: 10px;
}
</style>
