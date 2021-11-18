<template>
  <div>
    <!--搜索-->
    <el-card style="margin: 10px;">
      <el-row class="searchBox">
        <el-col :xl="4" :lg="4" :md="11" :sm="11" :xs="24">
          <el-input size="mini"  placeholder="名称" v-model="name"/>
        </el-col>
        <el-col v-if="isShowMerchant" :xl="4" :lg="4" :md="11" :sm="11" :xs="24">
          <el-select size="mini" style="width: 100%" v-model="merchantId" filterable placeholder="请选择商户">
            <el-option value=""></el-option>
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
      </el-row>
      <el-row>
        <div>
          <el-table :data="list" v-loading="loading" @select-all="selectAll"
                    @select="select" stripe style="width: 100%" reserve-selection="true">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column label="订单号" sortable>
              <template slot-scope="scope">
                <router-link :to="'/order/orderDetails/' + scope.row.id">
                  <el-button type="text">{{scope.row.sn}}</el-button>
                </router-link>
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" :formatter="statusFormat"/>
            <el-table-column prop="payAmount" label="支付金额"/>
            <el-table-column prop="createDate" label="下单时间"/>
            <el-table-column prop="buyerRemark" label="买家备注"/>
            <el-table-column label="操作" fixed="right" align="center">
              <template slot-scope="scope">
                <el-button-group>
                  <el-tooltip v-if="scope.row.status === 101" content="开始配送到商家" placement="top">
                    <el-button @click="startSendMerchant(scope.row)" type="default" icon="el-icon-arrow-down" size="mini"/>
                  </el-tooltip>
                  <el-tooltip v-if="scope.row.status === 102" content="商家确认收货洗涤开始" placement="top">
                    <el-button @click="washingStart(scope.row)" type="default" icon="el-icon-download" size="mini"/>
                  </el-tooltip>
                  <el-tooltip v-if="scope.row.status === 103" content="商家确认洗涤完毕" placement="top">
                    <el-button @click="washingEnd(scope.row)" type="default" icon="el-icon-upload2" size="mini"/>
                  </el-tooltip>
                  <el-tooltip v-if="scope.row.status === 104" content="配送到用户开始" placement="top">
                    <el-button @click="sendCustomerStart(scope.row)" type="default" icon="el-icon-arrow-up" size="mini"/>
                  </el-tooltip>
                  <el-tooltip v-if="scope.row.status === 105" content="配送到用户结束" placement="top">
                    <el-button @click="sendCustomerEnd(scope.row)" type="default" icon="el-icon-check" size="mini"/>
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
    name: "orderList",
    data(){
      return {
        baseUrl : process.env.BASE_API,
        loading : false,
        list : [],
        roleButton:{
          order_save : false,
          order_update : false,
          order_delete : false,
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
      ajax("/resource/findByRoleButton", {resourceId: "orderList"}).then(response => {
        var buttonList = response.data.obj;
        for (var i = 0; i < buttonList.length; i++) {
          this.roleButton[buttonList[i].id] = buttonList[i].hasPermission;
        }
      });
      ajax("/order/isShowMerchant").then(response => {
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
        ajax("order/list", formData).then(response => {
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
      statusFormat(val){//是否上架格式化
        var statusStr = "";
        switch (val.status){
          case 0 : statusStr = "待付款";break;
          case 1 : statusStr = "订单已完成";break;
          case 2 : statusStr = "待评价";break;
          case 3 : statusStr = "订单已取消";break;
          case 4 : statusStr = "订单已失效";break;
          case 101 : statusStr = "待取货";break;
          case 102 : statusStr = "配送到商家中";break;
          case 103 : statusStr = "洗涤中";break;
          case 104 : statusStr = "等待配送到用户";break;
          case 105 : statusStr = "配送到用户中";break;
          case 201 : statusStr = "退款中";break;
          case 202 : statusStr = "退款完成";break;
        }
        return statusStr;
      },
      startSendMerchant(row){
        this.$confirm('确定开始配送到商家吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          this.loading=true;
          var orderId = row.id;
          ajax("order/startSendMerchant", {id : orderId}).then(response => {
            this.$notify.success({
              title: '信息',
              message: response.data.message,
              position: 'bottom-right'
            });
            this.getList();
            this.loading=false;
          }).finally(() => {
            this.loading=false;
          });
        }).catch(() => {

        });
      },
      washingStart(row){
        this.$confirm('确定开始洗涤吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          this.loading=true;
          var orderId = row.id;
          ajax("order/washingStart", {id : orderId}).then(response => {
            this.$notify.success({
              title: '信息',
              message: response.data.message,
              position: 'bottom-right'
            });
            this.getList();
            this.loading=false;
          }).finally(() => {
            this.loading=false;
          });
        }).catch(() => {

        });
      },
      washingEnd(row){
        this.$confirm('确定洗涤已经结束吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          this.loading=true;
          var orderId = row.id;
          ajax("order/washingEnd", {id : orderId}).then(response => {
            this.$notify.success({
              title: '信息',
              message: response.data.message,
              position: 'bottom-right'
            });
            this.getList();
            this.loading=false;
          }).finally(() => {
            this.loading=false;
          });
        }).catch(() => {

        });
      },
      sendCustomerStart(row){
        this.$confirm('确定开始配送到用户吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          this.loading=true;
          var orderId = row.id;
          ajax("order/sendCustomerStart", {id : orderId}).then(response => {
            this.$notify.success({
              title: '信息',
              message: response.data.message,
              position: 'bottom-right'
            });
            this.getList();
            this.loading=false;
          }).finally(() => {
            this.loading=false;
          });
        }).catch(() => {

        });
      },
      sendCustomerEnd(row){
        this.$confirm('确定配送到用户已经结束吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          this.loading=true;
          var orderId = row.id;
          ajax("order/sendCustomerEnd", {id : orderId}).then(response => {
            this.$notify.success({
              title: '信息',
              message: response.data.message,
              position: 'bottom-right'
            });
            this.getList();
            this.loading=false;
          }).finally(() => {
            this.loading=false;
          });
        }).catch(() => {

        });
      }
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

