<template>
  <div v-loading="loading">
    <div>
      <el-card style="margin: 10px;" header="logo">
        <img v-if="merchant.logo != null && merchant.logo != ''" style="max-height: 200px" :src="fileBaseUrl + merchant.logo"/>
      </el-card>
    </div>
    <div>
      <el-card style="margin: 10px;" header="详细信息">
        <table id="merchantData" class="el-table el-table--border el-table-column--selection">
          <tr>
            <th>名称：</th>
            <td colspan="5">{{merchant.name}}</td>
          </tr>
          <tr>
            <th>简介：</th>
            <td colspan="5">{{merchant.info}}</td>
          </tr>
          <tr>
            <th>地址：</th>
            <td colspan="5">{{merchant.address}}</td>
          </tr>
          <tr>
            <th>联系人名称：</th><td>{{merchant.contactName}}</td>
            <th>联系电话：</th><td>{{merchant.contactMobile}}</td>
            <th>联系邮箱：</th><td>{{merchant.contactMail}}</td>
          </tr>
          <tr>
            <th>营业执照编号：</th><td>{{merchant.businessLicenseCode}}</td>
            <th>经度：</th><td>{{merchant.longitude}}</td>
            <th>纬度：</th><td>{{merchant.latitude}}</td>
          </tr>
          <tr>
            <th>评分：</th><td>{{merchant.grade}}</td>
            <th>月销量：</th><td>{{merchant.monthSales}}</td>
            <th>总销量：</th><td>{{merchant.totalSales}}</td>
          </tr>
          <tr>
            <th>服务范围：</th><td>{{merchant.scopeOfServices}}</td>
            <th>营业开始时间：</th><td>{{merchant.businessHoursStart}}</td>
            <th>营业结束时间：</th><td>{{merchant.businessHoursEnd}}</td>
          </tr>
        </table>
      </el-card>
    </div>
    <div >
      <el-card style="margin: 10px;">
        <div slot="header" class="clearfix">
          <span>图片列表</span>
          <el-button style="float: right; padding: 3px 0" @click="showAddImageDialog" type="text">添加图片</el-button>
        </div>
        <el-row>
          <draggable class="list-group" v-model="merchantImageList" :options="{animation: 60,}" @update="datadragEnd">
            <el-col :span="4" :offset="1" v-for="merchantImage in merchantImageList" :key="merchantImage" :id="merchantImage.id">
              <el-card :body-style="{padding: '0px'}">
                <img style="width: 100%; height: 100%" :src="merchantImage.url"/>
              </el-card>
            </el-col>
          </draggable>
        </el-row>
      </el-card>
    </div>
    <el-dialog title="商户图片" :visible.sync="addImageDialogVisible" :before-close="closeFormDialog" :close-on-click-modal="false" width="70%">
      <el-alert
        title="最多上传6张商户图片"
        type="error"
        :closable="false">
      </el-alert><br/>
      <el-upload :action="uploadUrl" list-type="picture-card" :file-list="merchantImageList" :data="imageUploadRequestParams" limit="6"
                 :on-success="saveMerchantImage" :on-preview="handlePictureCardPreview" :on-remove="handleRemove">
        <i class="el-icon-plus"></i><br/>
      </el-upload>
      <el-dialog :visible.sync="imageDialogVisible">
        <img width="100%" :src="dialogImageUrl" alt="商户图片">
      </el-dialog>
    </el-dialog>
  </div>
</template>

<script>
  import {ajax} from '@/api/ajax';
  import draggable from 'vuedraggable';
    export default {
        name: "merchant-details",
      data(){
        return {
          loading:false,
          addImageDialogVisible : false,
          baseUrl : process.env.BASE_API,
          fileBaseUrl : process.env.FILE_BASE_URL,
          uploadUrl: process.env.BASE_API + "/fileUpload",
          id : null,
          merchant:{
            id : null,
            name : null,
            info : null,
            address : null,
            businessLicenseCode : null,
            contactName : null,
            contactMobile : null,
            contactMail : null,
            longitude : null,
            latitude : null,
            logo : null,
            monthSales : null,
            totalSales : null,
            grade : null,
            scopeOfServices : null,
            businessHoursStart : null,
            businessHoursEnd : null,
          },
          merchantImageList : [],
          imageUploadRequestParams : {uploadPath: 'merchant/info'},
          dialogImageUrl : null,
          imageDialogVisible : false
        }
      },
      created(){
        this.loading=true;
        this.getParams();
        //获取商品信息
        ajax("/merchant/getById",{id : this.id}).then(response => {
          this.merchant = response.data.obj;
          this.loading=false;
        }).catch(reason => {
          this.loading=false;
        });
        this.getMerchantImageList();
      },
      components: {
        draggable,
      },
      methods: {
        getParams () {
          // 取到路由带过来的参数
          this.id = this.$route.params.id;
        },
        datadragEnd (event) {
          console.info(event);
          var srcId = event.clone.id;
          var tarId = event.item.id;
          var oldIndex = event.oldIndex;
          var newIndex = event.newIndex;
          ajax("/merchant/updateMerchantImageSeq",{
            srcId : srcId,
            tarId : tarId,
            oldIndex : oldIndex,
            newIndex : newIndex,
          });
        },
        showAddImageDialog(){
          this.addImageDialogVisible = true;
        },
        closeFormDialog(){
          this.addImageDialogVisible = false;
        },
        handleRemove(file, fileList) {
          console.log(file, fileList);
          ajax("/merchant/deleteMerchantImage", {id : file.id}).then(response=>{
            this.getMerchantImageList();
            this.$notify.success({title: '信息', message: "删除图片成功", position: 'bottom-right'});
          });
        },
        handlePictureCardPreview(file) {
          this.dialogImageUrl = file.url;
          this.imageDialogVisible = true;
        },
        saveMerchantImage(response){
          ajax("/merchant/saveMerchantImage", {
            merchantId : this.id,
            url : response.obj
          }).then(response => {
            this.getMerchantImageList();
            this.$notify.success({title: '信息', message: "保存图片成功", position: 'bottom-right'});
          })
        },
        getMerchantImageList(){
          ajax("/merchant/getMerchantImageList", {
            merchantId : this.id
          }).then(response => {
            var imgs = response.data.obj;
            if(imgs != null){
              for(var i=0; i< imgs.length;i++){
                imgs[i].url = this.fileBaseUrl + imgs[i].url
              }
              this.merchantImageList = imgs;
            }
          })
        }
      }
    }
</script>

<style scoped>
  #merchantData tr th {
    width: 10%;
    padding-right: 10px;
    text-align: right;
  }
  #merchantData tr td {
    padding-left: 10px;
  }
</style>
