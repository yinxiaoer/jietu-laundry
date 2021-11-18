<template>
  <div>
    <!--搜索-->
    <el-row class="searchBox">
      <el-col :xl="4" :lg="4" :md="11" :sm="11" :xs="24">
        <el-input size="mini" placeholder="请输入用户名" v-model="inputUser">
        </el-input>
      </el-col>
      <el-col :xl="4" :lg="4" :md="11" :sm="11" :xs="24">
        <el-input size="mini" placeholder="请输入真实姓名" v-model="inputUserName">
        </el-input>
      </el-col>
      <el-col :xl="4" :lg="4" :md="11" :sm="11" :xs="24">
        <el-input size="mini" placeholder="请输入联系电话" v-model="inputPhone">
        </el-input>
      </el-col>
      <el-col :xl="4" :lg="4" :md="11" :sm="11" :xs="24">
        <el-input size="mini" placeholder="请输入邮箱" v-model="inputEmail">
        </el-input>
      </el-col>
      <el-col :xl="4" :lg="4" :md="24" :sm="24" :xs="24" >
      <el-button-group>
        <el-button type="default" @click="searchData" icon="el-icon-search"  size="mini">查询</el-button>
        <el-button type="default" @click="resetInput" icon="el-icon-refresh"  size="mini">重置</el-button>
      </el-button-group>

      </el-col>
    </el-row>
    <!--操作-->
    <el-row class="operationBox">
      <el-button v-if="roleButton.user_save" type="primary" icon="el-icon-plus" @click="showAddBox()"   size="mini">添加</el-button>
    </el-row>
    <!--数据表格-->
    <el-table :data="tableData" style="width: 100%" @select-all="selectAll" @select="select" v-loading="loading"
      ref="table" @sort-change="updateTableData" reserve-selection="true" >
      <el-table-column type="selection" width="55" ></el-table-column>
      <el-table-column prop="username" label="用户名" sortable></el-table-column>
      <el-table-column prop="realname" label="真实姓名" sortable></el-table-column>
      <el-table-column prop="email" label="邮箱" sortable></el-table-column>
      <el-table-column prop="phone" label="联系电话" sortable></el-table-column>
      <el-table-column prop="roleId" label="角色" sortable></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
        <el-button-group>
          <el-button v-if="roleButton.user_update"  @click="handleEdit(scope.$index, scope.row)" type="default" icon="el-icon-edit"  size="mini">编辑</el-button>
          <el-button v-if="roleButton.user_delete" @click="handleDel(scope.$index, scope.row)" type="default" icon="el-icon-delete"   size="mini">删除</el-button>
          <el-button v-if="roleButton.user_resetPassword" @click="handleResPwd(scope.$index, scope.row)" type="default" icon="el-icon-document"  size="mini">重置密码</el-button>
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
    <!--弹窗-->
    <!--添加用户弹窗dialogAddVisible-->
    <el-dialog title="添加用户"
    :visible.sync="dialogAddVisible"
    :close-on-click-modal="false">
      <el-form :model="addForm">
        <el-form-item label="用户名" label-width="120px" required>
          <el-input size="mini" v-model="addForm.username" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" label-width="120px" required>
          <el-input size="mini" type="password" v-model="addForm.password" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名" label-width="120px" required>
          <el-input size="mini" v-model="addForm.realname" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" label-width="120px">
          <el-input size="mini" v-model="addForm.phone" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" label-width="120px">
          <el-input size="mini" v-model="addForm.email" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色" label-width="120px" required>
          <el-select size="mini" v-model="addForm.roleId" placeholder="请选择">
            <el-option
              v-for="item in addForm.roleList"
              :key="item.id"
              :label="item.description"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancleAdd()"  size="mini">取 消</el-button>
        <el-button type="primary" @click="addUser()"  size="mini">确 定</el-button>
      </div>
    </el-dialog>
    <!--编辑用户弹窗dialogEditVisible-->
     <el-dialog title="编辑用户资料"
     :visible.sync="dialogEditVisible"
     :close-on-click-modal="false">
      <el-form :model="editForm">
       <el-form-item label="用户名" label-width="120px" required>
          <el-input size="mini" v-model="editForm.username"  :disabled="true" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名" label-width="120px" required>
          <el-input size="mini" v-model="editForm.realname" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" label-width="120px" required>
          <el-input size="mini" v-model="editForm.phone" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" label-width="120px">
          <el-input size="mini" v-model="editForm.email" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色" label-width="120px" required>
          <el-select size="mini" v-model="editForm.roleId" placeholder="请选择">
            <el-option v-for="item in editForm.roleList" :key="item.id" :label="item.description" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogEditVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit()">确 定</el-button>
      </div>
    </el-dialog>
    <!--重置密码弹窗dialogResetPassVisible-->
    <el-dialog title="重置密码"
    :visible.sync="dialogResetPassVisible"
    :close-on-click-modal="false">
      <el-form :model="resetPsdForm">
        <el-form-item label="新密码" label-width="120px">
          <el-input size="mini" type="password" v-model="resetPsdForm.password" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="重新输入密码" label-width="120px">
          <el-input size="mini" type="password" v-model="resetPsdForm.relPassword" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancleResPwd()"  size="mini">取 消</el-button>
        <el-button type="primary" @click="saveResPwd()"  size="mini">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import waves from '@/directive/waves' // 水波纹指令
import {userList,roleList,saveUser,deleteById,updateUser,updatePwd,resetPwd,findByRoleButton } from '@/api/system/system'

export default {
  name: 'user',
  directives: {
    waves
  },
  data() {
    return {
      roleButton : {
        user_save : false,
        user_update : false,
        user_delete : false,
        user_resetPassword : false
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
      inputUser:'',//搜索框 输入框 用户名
      inputUserName:'',//搜索框 输入框真实姓名
      inputPhone:'',// 搜索框输入 框联系电话
      inputEmail:'',//搜索框 输入的邮箱
      dialogAddVisible:false, // 是否显示添加弹窗
      dialogEditVisible:false, // 是否显示编辑弹窗
      dialogResetPassVisible:false, //是否显示重置密码弹窗,
      loading:false, // 是否显示loading
      editForm:{// 编辑form
        username:'',
        realname:'',
        phone:'',
        email:'',
        id:'',
        roleId:'',
        roleList:[]
      },
      addForm:{ //添加form
       username:'',
       password:'',
       realname:'',
       phone:'',
       email:'',
       roleId:'',
       roleList:[]
      },
      resetPsdForm:{ // 重置密码form
        userId:'',
        password:'', // 新密码
        relPassword:'' // 重新输入的密码
      }
      }
  },
  created() {
    //查询用户按钮
    findByRoleButton({resourceId : "user"}).then(response => {
      var buttonList = response.data.obj;
      for(var i=0; i < buttonList.length; i++){
        this.roleButton[buttonList[i].id] = buttonList[i].hasPermission;
      }
    });
    this.getUserList();
  },
  methods: {
    updateTableData({ column, prop, order }){//排序条件变化时触发 需发送请求
      //排序条件 prop:根据该值进行排序  order：根据该值进行排序的方式 descending降序 ascending：升序
      //4.发送请求获取当前排序方式的数据
      //更新数据tableData
     var orderkey=(order=="descending"?"desc":"asc");
     this.paginationData.sort=prop;
     this.paginationData.order=orderkey;
     this.getUserList();
    },
    selectAll(selection){ // 全选
      this.checkList=selection
    },
    select(selection, row){ //选中某一个
      this.checkList=selection
    },
    handleDel(index,row){ // 删除此列 单个删除  需发送请求
      this.$confirm('此操作将永久删除该用户, 是否继续?','提示',{
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading=true;
        //console.log(this.tableData[index].id)
        // data为发送请求的参数
        var data={
          id:this.tableData[index].id
        }
        deleteById(data).then(reponse=>{
          this.getUserList();
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
    searchData(){ // 搜索 需发送请求
      this.getUserList((response)=>{
        //更加response的结果来做出具体的页面响应
        // this.inputUser=''
        // this.inputUserName=''
        // this.inputPhone=''
        // this.inputEmail=''
      })
    },
    resetInput(){ // 重置搜索框
      this.inputUser='';
      this.inputUserName='';
      this.inputPhone='';
      this.inputEmail='';
    },
    showAddBox(){  // 显示添加框 需发送请求
      //获取角色列表
      this.loading=true;
      roleList().then(response=>{
        this.loading=false;
        this.addForm.roleList=response.data.obj.data;
        this.dialogAddVisible=true
      })
    },
    cancleAdd(){
      this.dialogAddVisible=false;
      this.addForm.username='';
      this.addForm.password='';
      this.addForm.realname='';
      this.addForm.phone='';
      this.addForm.email='';
      this.addForm.roleId='';
      this.addForm.roleList=[];
    },
    addUser(){ // 添加用户 需发送请求
      this.dialogAddVisible=false;
      this.loading=true;
      this.addForm.roleList=null;
      saveUser(this.addForm).then(response=>{
          var msg=response.data.message;
           //初始化表单
          this.addForm.username='';
          this.addForm.password='';
          this.addForm.realname='';
          this.addForm.phone='';
          this.addForm.email='';
          this.addForm.roleId='';
          //添加成功，重新获取数据
          this.getUserList(()=>{
            this.$notify.success({
              title: '信息',
              message: msg,
              position: 'bottom-right'
            });
          });
          this.loading=false;
      }).catch(reason => {
        this.dialogAddVisible=true;
        this.loading=false;
      })

    },
     delBatch(){ // 批量删除 需发送请求
      if(this.checkList.length==0){
         this.$message({
          type: 'error',
          message: '请选择要删除的用户'
        });
      }else{
        this.$confirm('此操作将永久删除所选中的用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
         this.loading=true;
        //设置发送批量删除请求的参数
        var dataArray=[] //请求参数的数组形式
        for(var i=0;i<this.checkList.length;i++){
          dataArray.push(this.checkList[i].id)
        }
        var formData={
          ids:dataArray.join(',')
        }
        deleteByIds(formData).then(response=>{
          this.getUserList();
         this.loading=false;
         this.$notify.success({
           title: '信息',
           message: response.data.message,
           position: 'bottom-right'
         });
          // 清空选择状态
          this.$refs.table.clearSelection();
        }).catch(reason => {
          this.loading=false;
        })

      })
      }
    },
    getUserList(callback) { // 获取数据 需发送请求
     this.loading=true;
     var formData={
        page:this.paginationData.currentPage,
        limit:this.paginationData.pageSize,
        order:this.paginationData.order,
        sort:this.paginationData.sort,
        username:this.inputUser,
        realname:this.inputUserName,
        phone:this.inputPhone,
        email:this.inputEmail
     }
     userList(formData).then(response=>{
      this.tableData=response.data.obj.data;

      this.paginationData.pageSize=formData.limit;
      this.paginationData.currentPage=formData.page;
      this.paginationData.total=response.data.obj.count;
      this.loading=false;
      callback&&callback(response)
     })
    },
    handleEdit(index,row){ //编辑用户
      //显示当前编辑用户的原信息
      this.loading=true;
      roleList().then(response=>{
        this.loading=false;
        this.editForm.roleList=response.data.obj.data
      })
      this.editForm.username=row.username;
      this.editForm.realname=row.realname;
      this.editForm.phone=row.phone;
      this.editForm.email=row.email;
      this.editForm.id=row.id;
      this.editForm.roleId=row.roleId;
      this.dialogEditVisible=true;
    },
    saveEdit(){ // 保存修改
      this.dialogEditVisible=false;
      this.loading=true;
      //设置编辑用户formData
      this.editForm.roleList=null;
      var formData = this.editForm;
      updateUser(formData).then(response=>{
      //初始化当前编辑表单
        //修改成功
        this.$notify.success({
          title: '信息',
          message: response.data.message,
          position: 'bottom-right'
        });
        this.getUserList();
        this.editForm.username='';
        this.editForm.realname='';
        this.editForm.phone='';
        this.editForm.email='';
        this.loading=false;
      }).catch(reason => {
        this.dialogEditVisible=true;
        this.loading=false;
      })
    },
    handleResPwd(index,row){ // 显示修改密码框
      this.dialogResetPassVisible=true;
      this.resetPsdForm.userId=row.id;
    },
    cancleResPwd(){
      this.resetPsdForm.userId='';
      this.resetPsdForm.password='';
      this.resetPsdForm.relPassword='';
      this.dialogResetPassVisible=false;
    },
    saveResPwd(){ //修改密码
      var dataForm=this.resetPsdForm; // 设置请求dataForm
      resetPwd(dataForm).then(response=>{
          this.$notify.success({
            title: '信息',
            message: response.data.message,
            position: 'bottom-right'
          });
          this.dialogResetPassVisible=false;
          this.resetPsdForm.userId='';
          this.resetPsdForm.password='';
          this.resetPsdForm.relPassword='';
      });
    },
    handleSizeChange(val) { // 修改每页显示条数 需要发送请求
      this.paginationData.pageSize=val;
      this.getUserList();//重新获取当前页数据
    },
    handleCurrentChange(val) { // 修改当前页 需要发送请求
      this.paginationData.currentPage=val;
      this.getUserList();//重新获取当前页数据
    }
  }
}
</script>
<style>
 .el-table .del-row {
    background: #f5f7fa;
    opacity:0.5;
  }
  .searchBox{
    margin: 0 15px;
    border-bottom:1px dashed #ccc;
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
