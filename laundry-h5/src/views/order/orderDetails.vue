<template>
  <div v-loading="loading">
    <div>
      <el-card style="margin: 10px;" header="订单状态">
        <el-steps :active="stepStatus" finish-status="success" simple style="margin-top: 20px">
          <el-step title="待取货" ></el-step>
          <el-step title="配送到商家中" ></el-step>
          <el-step title="洗涤中" ></el-step>
          <el-step title="配送到用户中" ></el-step>
          <el-step title="交易完成" ></el-step>
        </el-steps>
      </el-card>
    </div>
    <div>
      <el-card style="margin: 10px;" header="详细信息">
        <table id="orderData" class="el-table el-table--border el-table-column--selection">
          <tr>
            <th>订单编号：</th><td>{{order.sn}}</td>
            <th>商户名称：</th><td>{{order.merchantName}}</td>
            <th>订单状态：</th><td>{{statusStr}}</td>
          </tr>
          <tr>
            <th>收货人：</th><td>{{order.receiverName}}</td>
            <th>联系电话：</th><td>{{order.receiverMobile}}</td>
            <th>收货地址：</th><td>{{order.receiverAddress}}</td>
          </tr>
          <tr>
            <th>买家备注：</th>
            <td colspan="5">{{order.buyerRemark}}</td>
          </tr>
          <tr>
            <th>商品总金额：</th><td>{{order.goodsTailAmount}}</td>
            <th>邮费：</th><td>{{order.freightFee}}</td>
            <th>支付金额：</th><td>{{order.payAmount}}</td>
          </tr>
          <tr>
            <th>支付方式：</th><td colspan="2">{{payTypeStr}}</td>
            <th>货币类型：</th><td colspan="2">{{currencyCodeStr}}</td>
          </tr>
          <tr>
            <th>取衣开始时间：</th><td colspan="2">{{order.fetchStartDate}}</td>
            <th>取衣结束时间：</th><td colspan="2">{{order.fetchEndDate}}</td>
          </tr>
          <tr>
            <th>送衣开始时间：</th><td colspan="2">{{order.deliverStartDate}}</td>
            <th>送衣结束时间：</th><td colspan="2">{{order.deliverEndDate}}</td>
          </tr>
        </table>
      </el-card>
    </div>
    <div>
      <el-card style="margin: 10px;" header="商品信息">
        <table id="orderGoodsData" class="el-table el-table--border el-table-column--selection">
          <thead>
            <tr>
              <th>商品图片</th>
              <th>名称</th>
              <th>价格</th>
              <th>数量</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="orderGoods in order.orderGoodsList">
              <td><img style="max-height: 100px" :src="fileBaseUrl + orderGoods.picUrl"/></td>
              <td>{{orderGoods.name}}</td>
              <td>{{orderGoods.price}}</td>
              <td>{{orderGoods.number}}</td>
            </tr>
          </tbody>
        </table>
      </el-card>
    </div>
    <div v-if="order.status == 1"><!--已完成订单展示用户评价-->
      <el-card style="margin: 10px;">
        <div slot="header" class="clearfix">
          <span>用户评价</span>
          <el-button v-if="orderComment.merchantReply == null || orderComment.merchantReply == ''" @click="commentReplyDialogVisible = true" style="float: right; padding: 3px 0" type="text">回复评价</el-button>
        </div>
        <div>
          <table id="orderCommentData" class="el-table el-table--border el-table-column--selection">
            <thead>
            <tr>
              <th>头像</th>
              <th>客户昵称</th>
              <th>评价等级</th>
              <th>评价内容</th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td><img v-if="orderComment.customerPortrait != null" style="max-height: 100px" :src="fileBaseUrl + orderComment.customerPortrait"/></td>
              <td>{{orderComment.customerName}}</td>
              <td><el-rate v-model="orderComment.grade" disabled allow-half show-score text-color="#ff9900">{{orderComment.grade}}
              </el-rate></td>
              <td>{{orderComment.comment}}</td>
            </tr>
            <tr>
              <td colspan="4">
                <img v-for="commentImage in orderComment.imageList" style="max-height: 100px;margin-right: 8px" :src="fileBaseUrl + commentImage.url"/>
              </td>
            </tr>
            <tr>
              <td>商家评论：</td>
              <td colspan="3">
                {{orderComment.merchantReply}}
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </el-card>
      <el-dialog title="商家回复" :visible.sync="commentReplyDialogVisible" :close-on-click-modal="true" width="70%">
        <el-form :model="orderComment" ref="orderComment">
          <el-form-item label="商家回复：" label-width="20%">
            <el-input type="textarea" :maxlength="200" v-model="replyContent" auto-complete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="commentReplyDialogVisible = false"  size="mini">取 消</el-button>
          <el-button type="primary" @click="commentReply()"  size="mini">回 复</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  import {ajax} from '@/api/ajax';
    export default {
        name: "order-details",
      data(){
        return {
          loading:false,
          commentReplyDialogVisible : false,
          baseUrl : process.env.BASE_API,
          fileBaseUrl : process.env.FILE_BASE_URL,
          uploadUrl: process.env.BASE_API + "/fileUpload",
          id : null,
          stepStatus : null,
          statusStr : null,
          payTypeStr : null,
          currencyCodeStr : null,
          order:{
            id : null,
            sn : null,
            merchantName : null,
            status : null,
            receiverName : null,
            receiverMobile : null,
            receiverAddress : null,
            buyerRemark : null,
            goodsTailAmount : null,
            freightFee : null,
            discountAmount : null,
            payAmount : null,
            currencyCode : null,
            fetchStartDate : null,
            fetchEndDate : null,
            deliverStartDate : null,
            deliverEndDate : null,
            payType : null,
            orderGoodsList : [],
          },
          orderComment : {
            id : null,
            customerId:null,
            customerName:null,
            customerPortrait:null,
            grade:null,
            comment:null,
            merchantReply:null,
            imageList : [],
          },
          replyContent: null
        }
      },
      created(){
        this.loading=true;
        this.getParams();
        ajax("/order/getById",{id : this.id}).then(response => {
          this.order = response.data.obj;
          var status = this.order.status;
          if(status == 101){
            this.stepStatus = 1;
          } else if(status == 102){
            this.stepStatus = 2;
          } else if(status == 103){
            this.stepStatus = 3;
          }  else if(status == 104){
            this.stepStatus = 3;
          } else if(status == 105){
            this.stepStatus = 4;
          } else if(status == 1){
            this.stepStatus = 5;
          } else if(status == 2){
            this.stepStatus = 5;
          }

          if(status == 101){
            this.statusStr = "待取货"
          } else if(status == 0){
            this.statusStr = "待付款"
          } else if(status == 1){
            this.statusStr = "订单已完成"
          } else if(status == 2){
            this.statusStr = "待评价"
          } else if(status == 3){
            this.statusStr = "订单已取消"
          } else if(status == 4){
            this.statusStr = "订单已失效"
          } else if(status == 102){
            this.statusStr = "配送到商家中"
          } else if(status == 103){
            this.statusStr = "洗涤中"
          } else if(status == 104){
            this.statusStr = "等待配送到用户"
          } else if(status == 105){
            this.statusStr = "配送到用户中"
          } else if(status == 201){
            this.statusStr = "退款中"
          }else if(status == 202){
            this.statusStr = "退款完成"
          }

          var payType = this.order.payType;
          if(payType == 0){
            this.payTypeStr = "余额支付";
          } else if(payType == 1){
            this.payTypeStr = "微信支付";
          }

          var currencyCode = this.order.currencyCode;
          if(currencyCode == "CNY"){
            this.currencyCodeStr = "人民币";
          }
          //订单已完成则查询评价信息
          if(status == 1){
            this.getOrderComment(this.order.id);
          }

          this.loading=false;
        }).catch(reason => {
          this.loading=false;
        });
      },
      methods: {
        getParams () {
          // 取到路由带过来的参数
          this.id = this.$route.params.id;
        },
        getOrderComment(orderId){
          ajax("/order/getOrderComment",{orderId : orderId}).then(response => {
            this.orderComment = response.data.obj;
          });
        },
        commentReply(){//商家回复
          this.commentReplyDialogVisible = false;
          this.loading=true;
          ajax("/order/commentReply",{
            orderCommentId : this.orderComment.id,
            replyContent : this.replyContent
          }).then(response => {
            this.getOrderComment(this.order.id);
            this.replyContent = null;
            this.loading=false;
          }).catch(reason => {
            this.loading=false;
          });
        }
      }
    }
</script>

<style scoped>
  #orderData tr th {
    width: 10%;
    padding-right: 10px;
    text-align: right;
  }
  #orderData tr td {
    padding-left: 10px;
  }
  #orderGoodsData tr th {
    width: 25%;
    padding-left: 10px;
  }
  #orderGoodsData tr td {
    padding-left: 10px;
  }
  #orderCommentData tr th {
    width: 25%;
    padding-left: 10px;
  }
  #orderCommentData tr td {
    padding-left: 10px;
  }
  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }
</style>
