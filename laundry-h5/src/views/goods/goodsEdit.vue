<template>
  <div  v-loading="loading">
    <el-form :model="goods" ref="goods" :rules="rules">
      <el-card style="margin: 10px;" shadow="always">
        <el-form-item label="商品名称：" prop="name" label-width="20%">
          <el-input v-model="goods.name" maxlength="50" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item v-if="isShowMerchant" label="商户：" prop="merchantId" label-width="20%">
          <el-select style="width: 100%" @change="merchantChange" v-model="goods.merchantId" filterable placeholder="请选择商户">
            <el-option value=""></el-option>
            <el-option v-for="merchant in merchantList" filterable
                       :key="merchant.id" :label="merchant.name" :value="merchant.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="类型：" prop="categoryId" label-width="20%">
          <el-select style="width: 100%" v-model="goods.categoryId" filterable placeholder="请选择类型">
            <el-option value=""></el-option>
            <el-option v-for="goodsCategory in goodsCategoryList" filterable
                       :key="goodsCategory.id" :label="goodsCategory.name" :value="goodsCategory.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序：" prop="sortOrder" label-width="20%">
          <el-input-number v-model="goods.sortOrder" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="单价：" prop="price" label-width="20%">
          <el-input-number v-model="goods.price" :precision="2" :step="0.01" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="零售价格：" prop="retailPrice" label-width="20%">
          <el-input-number v-model="goods.retailPrice" :precision="2" :step="0.01" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="市场价格：" prop="marketPrice" label-width="20%">
          <el-input-number v-model="goods.marketPrice" :precision="2" :step="0.01" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="商品主图：" prop="picUrl" label-width="20%">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-upload drag :multiple="false" ref="uploadPic" :data="imageUploadParam" :limit="1"
                         :action="uploadUrl" :auto-upload="false"
                         :on-success="uploadPicSuccess" :on-error="uploadPicError">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
              </el-upload>
            </el-col>
            <el-col :span="12">
              <div v-if="isEdit && goods.picUrl != null" class="grid-content">
                <img :src="fileBaseUrl + goods.picUrl" style="max-height: 300px; max-width: 300px;"/>
              </div>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="服务说明：" prop="serviceDesc" label-width="20%">
          <el-input type="textarea" maxlength="200" v-model="goods.serviceDesc" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品简介：" prop="info" label-width="20%">
          <el-input type="textarea" maxlength="200" v-model="goods.info" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="" label-width="20%">
          <el-button type="primary" @click="handleSave()"  size="mini">保 存</el-button>
          <el-button @click="clearForm()"  size="mini">重 置</el-button>
        </el-form-item>
      </el-card>
    </el-form>
  </div>
</template>

<script>
  import waves from '@/directive/waves' // 水波纹指令
  import draggable from 'vuedraggable'
  import {ajax} from '@/api/ajax'
  export default {
    name: "goods-edit",
    data () {
      return {
        loading:false,
        id: null,
        isEdit:false,
        isShowMerchant : false,//是否是管理员，管理员显示商户
        baseUrl : process.env.BASE_API,
        fileBaseUrl : process.env.FILE_BASE_URL,
        uploadUrl: process.env.BASE_API + "/fileUpload",
        goods: {
          id : null,
          name : null,
          merchantId : null,
          categoryId : null,
          info : null,
          serviceDesc : null,
          sortOrder : null,
          picUrl : null,
          price : null,
          retailPrice : null,
          marketPrice : null,
        },
        rules: {
          name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
          categoryId: [{ required: true, message: '请选择类型', trigger: 'change' }],
          price: [{ required: true, message: '请输入单价', trigger: 'change' }],
        },
        imageUploadParam: {
          uploadPath: 'goods/pic'
        },
        merchantList : [],
        goodsCategoryList : [],
      }
    },
    created(){
      this.getParams();
      ajax("/goods/isShowMerchant").then(response => {
        if(response.data.obj != null){
          this.isShowMerchant = true;
          this.merchantList = response.data.obj;
        } else {
          ajax("/goodsCategory/findByMerchantId").then(response => {
            this.goodsCategoryList=response.data.obj;
          });
        }
      });
      if(this.id != null && this.id !== '0'){
        this.isEdit = true;
        this.loading=true;
        ajax("/goods/getById",{id : this.id}).then(response => {
          let g = response.data.obj;
          this.goods.id = this.id;
          this.goods.name=g.name;
          this.goods.merchantId=g.merchantId;
          this.goods.categoryId=g.categoryId;
          this.goods.info=g.info;
          this.goods.serviceDesc=g.serviceDesc;
          this.goods.sortOrder=g.sortOrder;
          this.goods.picUrl=g.picUrl;
          this.goods.price=g.price;
          this.goods.retailPrice=g.retailPrice;
          this.goods.marketPrice=g.marketPrice;
          ajax("/goodsCategory/findByMerchantId", {merchantId : this.goods.merchantId}).then(response => {
            this.goodsCategoryList=response.data.obj;
          });
          this.loading=false;
        }).catch(reason => {
          this.loading=false;
        });
      }
    },
    methods: {
      getParams () {
        // 取到路由带过来的参数
        this.id = this.$route.params.id;
      },
      handleSave(){
        this.$refs['goods'].validate(valid => {
          if (valid) {
            if(this.isShowMerchant){
              if(this.goods.merchantId == null || this.goods.merchantId == ''){
                this.$notify.error({
                  title: '错误', message: "请选择商户", position: 'bottom-right'
                });
              }
            }
            this.loading=true;
            console.info(this.$refs.uploadPic.uploadFiles);
            if(this.$refs.uploadPic.uploadFiles.length > 0){
              //上传图片
              this.$refs.uploadPic.submit();
            } else {
              this.saveOrUpdate();
            }
          } else {
            return false;
          }
        });
      },
      uploadPicSuccess(response){
        this.goods.picUrl = response.obj;
        this.saveOrUpdate();
      },
      uploadPicError(){
        console.error(response.data);
        this.$notify.error({
          title: '错误',
          message: "文件上传失败",
          position: 'bottom-right'
        });
      },
      saveOrUpdate(){
        var url = "/goods/save";
        if(this.isEdit){
          url = "/goods/update";
        }
        ajax(url, this.goods).then(response => {
          this.loading=false;
          this.$notify.success({
            title: '信息',
            message: response.data.message,
            position: 'bottom-right'
          });
          if(!this.isEdit){
            this.clearForm();
          } else {
            this.$refs.uploadPic.clearFiles();
          }
        }).catch(reason => {
          this.loading=false;
        });
      },
      clearForm(){
        this.goods.name=null;
        this.goods.merchantId=null;
        this.goods.categoryId=null;
        this.goods.info=null;
        this.goods.serviceDesc=null;
        this.goods.sortOrder=null;
        this.goods.picUrl=null;
        this.goods.price=null;
        this.goods.retailPrice=null;
        this.goods.marketPrice=null;
        this.$refs['goods'].clearValidate();
        this.$refs.uploadPic.clearFiles();
      },
      merchantChange(){
        var merchantId = this.goods.merchantId;
        if(merchantId == null || merchantId == ''){
          this.goodsCategoryList = [];
          return ;
        }
        ajax("/goodsCategory/findByMerchantId", {merchantId : merchantId}).then(response => {
          this.goodsCategoryList=response.data.obj;
        });
      }
    },
  }
</script>

<style scoped>

</style>
