<template>
  <div>
    <!--搜索-->
    <el-card style="margin: 10px;">
      <el-row class="searchBox">
        <el-col :xl="4" :lg="4" :md="11" :sm="11" :xs="24">
          <el-input size="mini"  placeholder="名称" v-model="name"></el-input>
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
            <el-button size="mini" type="default" @click="getList" icon="el-icon-search">查询</el-button>
            <el-button size="mini" type="default" @click="resetSearch" icon="el-icon-refresh">重置</el-button>
          </el-button-group>
        </el-col>
      </el-row>
    </el-card>
    <!--操作-->
    <el-card style="margin: 10px;">
      <el-row class="operationBox">
        <router-link v-if="roleButton.goods_save" :to="'/goods/goodsEdit/0'">
          <el-button type="primary" icon="el-icon-plus" size="mini" >添加</el-button>
        </router-link>
      </el-row>
      <el-row>
        <div>
          <el-table :data="list" v-loading="loading" @select-all="selectAll"
                    @select="select" stripe style="width: 100%" reserve-selection="true">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column label="名称" sortable>
              <template slot-scope="scope">
                <router-link :to="'/goods/goodsDetails/' + scope.row.id">
                  <el-button type="text">{{scope.row.name}}</el-button>
                </router-link>
              </template>
            </el-table-column>
            <el-table-column prop="categoryName" label="种类"></el-table-column>
            <el-table-column prop="price" label="价格"></el-table-column>
            <el-table-column prop="onSale" label="是否上架" :formatter="onSaleFormat"></el-table-column>
            <el-table-column prop="sortOrder" label="排序"></el-table-column>
            <el-table-column label="操作" fixed="right" align="center">
              <template slot-scope="scope">
                <el-button-group>
                  <router-link :to="'/goods/goodsEdit/' + scope.row.id">
                    <el-tooltip v-if="roleButton.goods_update" content="编辑" placement="top">
                      <el-button type="default" icon="el-icon-edit" size="mini"></el-button>
                    </el-tooltip>
                  </router-link>
                  <el-tooltip content="提交上架审核申请" placement="top">
                    <el-button @click="" type="default" icon="el-icon-upload2" size="mini"></el-button>
                  </el-tooltip>
                  <el-tooltip v-if="roleButton.goods_delete" content="删除" placement="top">
                    <el-button @click="handleDel(scope.$index, scope.row)" type="default" icon="el-icon-delete" size="mini"></el-button>
                  </el-tooltip>
                </el-button-group>
              </template>
            </el-table-column>
          </el-table>
          <div class="block" v-if="!loading">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="page.currentPage"
              :background="true"
              :page-sizes="page.pageSizes"
              :page-size="page.pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="page.total">
            </el-pagination>
          </div>
        </div>
      </el-row>
    </el-card>
  </div>
</template>

<script>
  import {ajax} from '@/api/ajax'
  export default {
    name: "goodsList",
    data(){
      return {
        baseUrl : process.env.BASE_API,
        loading : false,
        list : [],
        roleButton:{
          goods_save : false,
          goods_update : false,
          goods_delete : false,
        },
        page : {
          currentPage:1,
          pageSizes:[10,20,30,50],
          pageSize:20,
          total:0,
          order:'',
          sort:null
        },
        name : null,
        merchantId : null,
        categoryId : null,
        merchantList : [],
        isShowMerchant : false
      }
    },
    created() {
      ajax("/resource/findByRoleButton", {resourceId: "goodsList"}).then(response => {
        var buttonList = response.data.obj;
        for (var i = 0; i < buttonList.length; i++) {
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
      getList(){
        this.loading=true;
        var formData={
          name : this.name,
          merchantId : this.merchantId,
          page:this.page.currentPage,
          limit:this.page.pageSize,
          order:this.page.order,
          sort:this.page.sort,
        };
        ajax("goods/list", formData).then(response => {
          this.list=response.data.obj.data;
          this.page.pageSize=formData.limit;
          this.page.currentPage=formData.page;
          this.page.total=response.data.obj.count;
        }).finally(() => {
          this.loading=false;
        });
      },
      resetSearch(){//重置搜索参数
        this.name=null;
        this.merchantId=null;
      },
      selectAll(selection){ // 全选
        this.checkList=selection
      },
      select(selection, row){ //选中某一个
        this.checkList=selection
      },
      handleSizeChange(val) { // 修改每页显示条数 需要发送请求
        this.page.pageSize=val;
        this.getList();
      },
      handleCurrentChange(val) { // 修改当前页 需要发送请求
        this.page.currentPage=val;
        this.getList();
      },
      onSaleFormat(val){//是否上架格式化
        if(val.onSale){
          return "是";
        } else {
          return "否";
        }
      },
    }
  }
</script>

<style scoped>
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
  .searchBox .el-select{
    width : 80%
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
  #formDialog .el-input{
    width: 130%;
  }
  #formDialog .el-select{
    width: 120%;
  }
</style>

