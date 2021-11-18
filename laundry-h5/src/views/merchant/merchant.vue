<template>
  <div>
    <!--搜索-->
    <el-card style="margin: 10px;">
      <el-row class="searchBox">
        <el-col :xl="4" :lg="4" :md="11" :sm="11" :xs="24">
          <el-input size="mini" placeholder="请输入名称" v-model="name"></el-input>
        </el-col>
        <el-col :xl="4" :lg="4" :md="11" :sm="11" :xs="24">
          <el-input size="mini" placeholder="请输入商户联系人" v-model="contactName"></el-input>
        </el-col>
        <el-col :xl="4" :lg="4" :md="11" :sm="11" :xs="24">
          <el-input size="mini" placeholder="请输入商户联系人手机号" v-model="contactMobile"></el-input>
        </el-col>
        <el-col :xl="4" :lg="4" :md="24" :sm="24" :xs="24" >
          <el-button-group>
            <el-button type="default" @click="getList" icon="el-icon-search"  size="mini">查询</el-button>
            <el-button type="default" @click="resetSearchForm" icon="el-icon-refresh"  size="mini">重置</el-button>
          </el-button-group>
        </el-col>
      </el-row>
    </el-card>
    <el-card style="margin: 10px;">
    <!--操作-->
    <el-row class="operationBox">
      <el-button v-if="roleButton.merchant_save" type="primary" icon="el-icon-plus" @click="showFormDialog()" size="mini">添加</el-button>
    </el-row>
    <!--数据表格-->
    <el-table :data="tableData" style="width: 100%" @select-all="selectAll" @select="select" v-loading="loading"
              ref="table" @sort-change="updateTableData" reserve-selection="true" >
      <el-table-column type="selection" width="55" ></el-table-column>
      <el-table-column label="商户名称" sortable>
        <template slot-scope="scope">
          <router-link :to="'/merchant/merchantDetails/' + scope.row.id">
            <el-button type="text">{{scope.row.name}}</el-button>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column prop="contactName" label="联系人名称"></el-table-column>
      <el-table-column prop="contactMobile" label="联系人手机号"></el-table-column>
      <el-table-column prop="contactMail" label="联系人邮箱"></el-table-column>
      <el-table-column label="操作" fixed="right" align="center">
        <template slot-scope="scope">
          <el-button-group>
            <el-tooltip v-if="roleButton.merchant_update" content="编辑" placement="top">
              <el-button @click="handleEdit(scope.$index, scope.row)" type="default" icon="el-icon-edit" size="mini"></el-button>
            </el-tooltip>
            <el-tooltip v-if="roleButton.merchant_delete" content="删除" placement="top">
              <el-button @click="handleDel(scope.$index, scope.row)" type="default" icon="el-icon-delete" size="mini"></el-button>
            </el-tooltip>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
    <!--分页-->
    <div class="block" v-if="!loading">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="paginationData.currentPage"
        :background="true"
        :page-sizes="paginationData.pageSizes"
        :page-size="paginationData.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="paginationData.total">
      </el-pagination>
    </div>
    </el-card>
    <!--弹窗-->
    <el-dialog title="商户" :visible.sync="formDialogVisible" :before-close="closeFormDialog" :close-on-click-modal="false" width="70%">
      <el-form :model="merchant" ref="merchant" :rules="rules">
        <el-form-item label="商户名称" prop="name" label-width="20%">
          <el-input size="mini" v-model="merchant.name" maxlength="100" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品主图：" prop="picUrl" label-width="20%">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-upload drag :multiple="false" ref="uploadLogo" :data="imageUploadParam" :limit="1"
                         :action="uploadUrl" :auto-upload="true"
                         :on-success="uploadLogoSuccess" :on-error="uploadLogoError">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
              </el-upload>
            </el-col>
            <el-col :span="8">
              <div v-if="!isInsert && merchant.logo != null && merchant.logo != ''" class="grid-content">
                <img :src="fileBaseUrl + merchant.logo" style="max-height: 300px; max-width: 300px;"/>
              </div>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="商户地址" prop="address" label-width="20%">
          <el-input size="mini" v-model="merchant.address" maxlength="100" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="商户营业执照编号" prop="businessLicenseCode" label-width="20%">
          <el-input size="mini" v-model="merchant.businessLicenseCode"maxlength="32" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="坐标经度" prop="longitude" label-width="20%">
          <el-input size="mini" v-model="merchant.longitude" maxlength="15" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="坐标纬度" prop="latitude" label-width="20%">
          <el-input size="mini" v-model="merchant.latitude"maxlength="15" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系人名称" prop="contactName" label-width="20%">
          <el-input size="mini" v-model="merchant.contactName" maxlength="30" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系人手机号" prop="contactMobile" label-width="20%">
          <el-input size="mini" v-model="merchant.contactMobile" maxlength="15" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系人邮箱" prop="contactMail" label-width="20%">
          <el-input size="mini" v-model="merchant.contactMail" maxlength="100" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="服务范围" prop="scopeOfServices" label-width="20%">
          <el-input-number v-model="merchant.scopeOfServices" :precision="0" :step="1" :min="0" :max="20000"></el-input-number>
        </el-form-item>
        <el-form-item label="营业时间" prop="businessHours" label-width="20%">
          <el-time-select placeholder="起始时间" v-model="merchant.businessHoursStart" :picker-options="{start: '05:00',step: '01:00',end: '24:00'}"></el-time-select>
          <el-time-select placeholder="结束时间" v-model="merchant.businessHoursEnd" :picker-options="{ start: '05:00', step: '01:00', end: '24:00',minTime: merchant.businessHoursStart}"></el-time-select>
        </el-form-item>
        <el-form-item label="商户简介" prop="info" label-width="20%">
          <el-input size="mini" type="textarea" v-model="merchant.info" maxlength="260" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeFormDialog()"  size="mini">取 消</el-button>
        <el-button type="primary" @click="handleSave()"  size="mini">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import waves from '@/directive/waves' // 水波纹指令
  import {ajax} from '@/api/ajax'

  export default {
    name: 'merchant',
    directives: {
      waves
    },
    data() {
      return {
        roleButton : {
          merchant_save : false,
          merchant_update : false,
          merchant_delete : false,
        },
        baseUrl : process.env.BASE_API,
        fileBaseUrl : process.env.FILE_BASE_URL,
        uploadUrl: process.env.BASE_API + "/fileUpload",
        tableData: [],
        paginationData:{
          currentPage:1,
          pageSizes:[10,20,30,50],
          pageSize:10,
          total:0,
          order:'',
          sort:null
        },
        checkList:[],
        formDialogVisible:false, // 是否显示弹窗
        isInsert : true,
        loading:false, // 是否显示loading
        name :null,
        contactName:null,
        contactMobile:null,
        merchant:{// 编辑form
          id : '',
          name:'',
          info:'',
          address:'',
          businessLicenseCode:'',
          contactName:'',
          contactMobile:'',
          contactMail:'',
          longitude:'',
          latitude:'',
          logo:'',
          scopeOfServices:'',
          businessHoursStart:'',
          businessHoursEnd:''
        },
        rules: {
          name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
          areaData: [{ required: true, message: '请选择地址', trigger: 'change' }],
          address: [{ required: true, message: '请输入地址', trigger: 'blur' }],
          businessLicenseCode: [{ required: true, message: '请输入营业执照编号', trigger: 'blur' }],
          contactName: [{ required: true, message: '请输入联系人名称', trigger: 'blur' }],
          contactMobile: [{ required: true, message: '请输入联系人手机号', trigger: 'blur' }],
          longitude: [{ required: true, message: '请输入坐标经度', trigger: 'blur' }],
          latitude: [{ required: true, message: '请输入坐标纬度', trigger: 'blur' }],
        },
        areaData : null,
        imageUploadParam: {
          uploadPath: 'merchant/logo'
        },
      }
    },
    created() {
      //查询用户按钮
      ajax("/resource/findByRoleButton", {resourceId : "merchantList"}).then(response => {
        var buttonList = response.data.obj;
        for(var i=0; i < buttonList.length; i++){
          this.roleButton[buttonList[i].id] = buttonList[i].hasPermission;
        }
      });
      this.getList();
    },
    methods: {
      updateTableData({ column, prop, order }){//排序条件变化时触发 需发送请求
        var orderkey=(order=="descending"?"desc":"asc");
        this.paginationData.sort=prop;
        this.paginationData.order=orderkey;
        this.getList();
      },
      selectAll(selection){ // 全选
        this.checkList=selection
      },
      select(selection, row){ //选中某一个
        this.checkList=selection
      },
      resetSearchForm(){
        this.name=null;
        this.contactName=null;
        this.contactMobile=null;
      },
      handleDel(index,row){ // 删除此列 单个删除  需发送请求
        this.$confirm('是否删除商户?','提示',{
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading=true;
          var data={
            id:this.tableData[index].id
          };
          ajax("merchant/delete", data).then(reponse=>{
            this.getList();
            this.$notify.success({
              title: '信息',
              message: reponse.data.message,
              position: 'bottom-right'
            });
            this.loading=false;
            this.$refs.table.clearSelection()
          }).catch(reason => {
            this.loading=false;
          })
        })
      },
      showFormDialog(){
        this.formDialogVisible=true;
      },
      closeFormDialog(){
        this.formDialogVisible=false;
        this.merchant.id='';
        this.merchant.name='';
        this.merchant.info='';
        this.merchant.address='';
        this.merchant.businessLicenseCode='';
        this.merchant.contactName='';
        this.merchant.contactMobile='';
        this.merchant.contactMail='';
        this.merchant.longitude='';
        this.merchant.latitude='';
        this.merchant.logo='';
        this.merchant.scopeOfServices='';
        this.merchant.businessHoursStart='';
        this.merchant.businessHoursEnd='';
        this.$refs['merchant'].clearValidate();
        this.$refs.uploadLogo.clearFiles();
      },
      handleSave: function () {
        this.$refs['merchant'].validate(valid => {
          if (valid) {
            if (this.isInsert) {
              this.save();
            } else {
              this.update();
            }
          } else {
            return false;
          }
        });
      },
      save(){
        this.formDialogVisible=false;
        this.loading=true;
        ajax("merchant/save", this.merchant).then(response=>{
          this.closeFormDialog();
          this.getList(()=>{
            this.$notify.success({
              title: '信息',
              message: response.data.message,
              position: 'bottom-right'
            });
          });
          this.loading=false;
        }).catch(reason => {
          this.formDialogVisible=true;
          this.loading=false;
        })

      },
      getList(callback) { // 获取数据 需发送请求
        this.loading=true;
        var formData={
          name : this.name,
          contactName:this.contactName,
          contactMobile : this.contactMobile,
          page:this.paginationData.currentPage,
          limit:this.paginationData.pageSize,
          order:this.paginationData.order,
          sort:this.paginationData.sort,
        };
        ajax("merchant/list", formData).then(response=>{
          this.tableData=response.data.obj.data;
          this.paginationData.pageSize=formData.limit;
          this.paginationData.currentPage=formData.page;
          this.paginationData.total=response.data.obj.count;
          this.loading=false;
          callback&&callback(response)
        })
      },
      handleEdit(index,row){ //编辑
        this.loading=true;
        this.merchant.id=row.id;
        this.merchant.name=row.name;
        this.merchant.info=row.info;
        this.merchant.address=row.address;
        this.merchant.businessLicenseCode=row.businessLicenseCode;
        this.merchant.contactName=row.contactName;
        this.merchant.contactMobile=row.contactMobile;
        this.merchant.contactMail=row.contactMail;
        this.merchant.longitude=row.longitude;
        this.merchant.latitude=row.latitude;
        this.merchant.logo=row.logo;
        this.merchant.scopeOfServices=row.scopeOfServices;
        this.merchant.businessHoursStart=row.businessHoursStart;
        this.merchant.businessHoursEnd=row.businessHoursEnd;
        this.formDialogVisible=true;
        this.loading=false;
        this.isInsert = false;
      },
      update(){ // 保存修改
        this.formDialogVisible=false;
        this.loading=true;
        ajax("merchant/update", this.merchant).then(response=>{
          this.$notify.success({
            title: '信息',
            message: response.data.message,
            position: 'bottom-right'
          });
          this.getList();
          this.closeFormDialog();
          this.loading=false;
        }).catch(reason => {
          this.formDialogVisible=true;
          this.loading=false;
        })
      },
      handleSizeChange(val) { // 修改每页显示条数 需要发送请求
        this.paginationData.pageSize=val;
        this.getUserList();//重新获取当前页数据
      },
      handleCurrentChange(val) { // 修改当前页 需要发送请求
        this.paginationData.currentPage=val;
        this.getUserList();//重新获取当前页数据
      },
      uploadLogoSuccess(response){
        this.merchant.logo = response.obj;
      },
      uploadLogoError(){
        console.error(response.data);
        this.$notify.error({
          title: '错误',
          message: "文件上传失败",
          position: 'bottom-right'
        });
      },
    }
  }
</script>
<style>
  .table-expand {
    font-size: 0;
  }
  .table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
  .el-table .del-row {
    background: #f5f7fa;
    opacity:0.5;
  }
  .searchBox{
    margin: 0 15px;
  }
  .searchBox .el-col{
    margin:10px;
  }
  .operationBox{
    margin-left:20px;
  }
  .operationBox .el-button{
    margin:10px 10px 10px 0;
  }
  .block{
    margin-top:20px;
  }
</style>
