<template>
  <div>
    <!--搜索-->
    <el-card style="margin: 10px;">
      <el-row class="searchBox">
        <el-col :xl="4" :lg="4" :md="11" :sm="11" :xs="24">
          <el-input size="mini" placeholder="请输入名称" v-model="name"></el-input>
        </el-col>
        <el-col v-if="isShowMerchant" :xl="4" :lg="4" :md="11" :sm="11" :xs="24">
          <el-select size="mini" style="width: 100%" v-model="merchantId" filterable placeholder="请选择商户">
            <el-option></el-option>
            <el-option v-for="merchant in merchantList" :key="merchant.id" filterable
                       :label="merchant.name" :value="merchant.id">
            </el-option>
          </el-select>
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
      <el-button v-if="roleButton.goodsCategory_save" type="primary" icon="el-icon-plus" @click="showFormDialog()" size="mini">添加</el-button>
    </el-row>
    <!--数据表格-->
    <el-table :data="tableData" style="width: 100%" @select-all="selectAll" @select="select" v-loading="loading"
              ref="table" @sort-change="updateTableData" reserve-selection="true" >
      <el-table-column type="selection" width="55" ></el-table-column>
      <el-table-column prop="name" label="类型名称" sortable></el-table-column>
      <el-table-column prop="desc" label="类型描述"></el-table-column>
      <el-table-column prop="sortOrder" label="排序" sortable></el-table-column>
      <el-table-column prop="merchantName" label="商户" sortable></el-table-column>
      <el-table-column label="操作" fixed="right" align="center">
        <template slot-scope="scope">
          <el-button-group>
            <el-tooltip v-if="roleButton.goodsCategory_update" content="编辑" placement="top">
              <el-button  @click="handleEdit(scope.$index, scope.row)" type="default" icon="el-icon-edit" size="mini"></el-button>
            </el-tooltip>
            <el-tooltip v-if="roleButton.goodsCategory_delete" content="删除" placement="top">
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
    <el-dialog title="商品类型" :visible.sync="formDialogVisible" :before-close="closeFormDialog" :close-on-click-modal="false" width="70%">
      <el-form :model="goodsCategory" ref="goodsCategory" :rules="rules">
        <el-form-item label="类型名称" prop="name" label-width="20%">
          <el-input size="mini" v-model="goodsCategory.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="类型描述" prop="desc" label-width="20%">
          <el-input size="mini" v-model="goodsCategory.desc" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="排序" prop="sortOrder" label-width="20%">
          <el-input-number v-model="goodsCategory.sortOrder" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item v-if="isShowMerchant" label="商户：" prop="merchantId" label-width="20%">
          <el-select style="width: 100%" v-model="goodsCategory.merchantId" filterable placeholder="请选择商户">
            <el-option value=""></el-option>
            <el-option v-for="merchant in merchantList" :key="merchant.id" filterable
                       :label="merchant.name" :value="merchant.id">
            </el-option>
          </el-select>
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
    name: 'goodsCategory',
    directives: {
      waves
    },
    data() {
      return {
        roleButton : {
          goodsCategory_save : false,
          goodsCategory_update : false,
          goodsCategory_delete : false,
        },
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
        merchantId :null,
        goodsCategory:{// 编辑form
          id : '',
          name:'',
          desc:'',
          sortOrder:'',
          merchantId:'',
        },
        rules: {
          name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        },
        merchantList : [],
        isShowMerchant : false
      }
    },
    created() {
      //查询用户按钮
      ajax("/resource/findByRoleButton", {resourceId : "goodsCategory"}).then(response => {
        var buttonList = response.data.obj;
        for(var i=0; i < buttonList.length; i++){
          this.roleButton[buttonList[i].id] = buttonList[i].hasPermission;
        }
      });
      ajax("/goods/isShowMerchant").then(response => {
        if(response.data.obj != null){
          this.isShowMerchant = true;
          this.merchantList = response.data.obj;
        }
      });
      this.getList();
    },
    methods: {
      updateTableData({ column, prop, order }){//排序条件变化时触发 需发送请求
        var orderkey=(order=="descending"?"desc":"asc");
        if(prop == "merchantName"){
          prop="merchantId";
        }
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
        this.merchantId=null;
      },
      handleDel(index,row){ // 删除此列 单个删除  需发送请求
        this.$confirm('是否删除商品类型?','提示',{
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading=true;
          var data={
            id:this.tableData[index].id
          };
          ajax("goodsCategory/delete", data).then(reponse=>{
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
        this.goodsCategory.id='';
        this.goodsCategory.name='';
        this.goodsCategory.desc='';
        this.goodsCategory.sortOrder='';
        this.goodsCategory.merchantId='';
        this.$refs['goodsCategory'].clearValidate();
      },
      handleSave: function () {
        this.$refs['goodsCategory'].validate(valid => {
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
        ajax("goodsCategory/save", this.goodsCategory).then(response=>{
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
          merchantId:this.merchantId,
          page:this.paginationData.currentPage,
          limit:this.paginationData.pageSize,
          order:this.paginationData.order,
          sort:this.paginationData.sort,
        };
        ajax("goodsCategory/list", formData).then(response=>{
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
        this.goodsCategory.id=row.id;
        this.goodsCategory.name=row.name;
        this.goodsCategory.desc=row.desc;
        this.goodsCategory.sortOrder=row.sortOrder;
        this.goodsCategory.merchantId=row.merchantId;
        this.formDialogVisible=true;
        this.isInsert = false;
        this.loading=false;
      },
      update(){ // 保存修改
        this.formDialogVisible=false;
        this.loading=true;
        ajax("goodsCategory/update", this.goodsCategory).then(response=>{
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
