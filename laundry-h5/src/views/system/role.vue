<template>
  <div>
    <!--操作-->
    <el-row class="operationBox">
      <el-button type="primary" icon="el-icon-plus" @click="handleAdd()"  size="mini">添加</el-button>
    </el-row>
    <!--数据表格sadsd-->
    <el-table :data="tableData" style="width: 100%" v-loading="loading" ref="table" @sort-change="updateTableData" reserve-selection="true">
     <el-table-column width="20"></el-table-column>
       <el-table-column prop="id" label="角色ID"  sortable></el-table-column>
      <el-table-column prop="name" label="角色名称"  sortable></el-table-column>
      <el-table-column prop="description" label="角色描述" sortable></el-table-column>
      <el-table-column label="操作"  align="center">
        <template slot-scope="scope">
        <el-button-group>
          <el-button  @click="handleEdit(scope.$index, scope.row)" type="default" icon="el-icon-edit"  size="mini">编辑</el-button>
          <el-button  @click="handleDel(scope.$index, scope.row)" type="default" icon="el-icon-delete"   size="mini">删除</el-button>
          <el-button  @click="show_Permission_config(scope.$index, scope.row)" type="default" icon="el-icon-edit-outline"   size="mini">配置权限</el-button>
        </el-button-group>
        </template>
      </el-table-column>
    </el-table>
    <!--弹窗-->
    <!--添加角色弹窗dialogAddVisible-->
    <el-dialog title="添加角色" :visible.sync="dialogAddVisible" :close-on-click-modal="false">
      <el-form :model="addForm">
        <el-form-item label="角色ID" label-width="120px">
          <el-input size="mini" v-model="addForm.id" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色名称" label-width="120px">
          <el-input size="mini" v-model="addForm.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色描述" label-width="120px">
          <el-input size="mini" v-model="addForm.description" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAddVisible = false" size="mini">取 消</el-button>
        <el-button type="primary" @click="saveUpdateRole()" size="mini">保存</el-button>
      </div>
    </el-dialog>
    <!--编辑角色dialogEditVisible-->
     <el-dialog title="编辑角色" :visible.sync="dialogEditVisible" :close-on-click-modal="false">
       <el-form :model="editForm">
        <el-form-item label="角色名称" label-width="120px">
          <el-input size="mini" v-model="editForm.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色描述" label-width="120px">
          <el-input size="mini" v-model="editForm.description" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogEditVisible = false" size="mini">取 消</el-button>
        <el-button type="primary" @click="saveEdit()" size="mini">确 定</el-button>
      </div>
    </el-dialog>
     <!--辑角色权限dialogPermisionConfig-->
     <el-dialog title="配置角色权限" :visible.sync="dialogPermisionConfig" :before-close="closePermisionConfig" :close-on-click-modal="false">
      <el-tree
        :data="permission.data"
        show-checkbox
        @node-click="showBtnPermission"
        node-key="id"
        default-expand-all
        ref="tree"
        :default-checked-keys="permission.checkedKeys"
        :props="permission.defaultProps">
      </el-tree>
       <!--配置中菜单的按钮权限-->
        <el-dialog title="配置菜单按钮权限" width="30%" append-to-body :visible.sync="dialogBtnPermisionConfig" :before-close="closeBtnPermisionConfig" :close-on-click-modal="false">
          <el-tree
            :data="btnPermission.data"
            show-checkbox
            node-key="id"
            ref="btnTree"
            :default-checked-keys="btnPermission.checkedKeys"
            :props="btnPermission.defaultProps">
          </el-tree>
          <div slot="footer" class="dialog-footer">
            <el-button  @click="closeBtnPermisionConfig">取 消</el-button>
            <el-button type="primary" @click="saveBtnPermisionConfig">确 定</el-button>
          </div>
        </el-dialog>
      <div slot="footer" class="dialog-footer">
        <el-button  @click="closePermisionConfig" size="mini">取 消</el-button>
        <el-button type="primary" @click="savePermisionConfig" size="mini">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import waves from '@/directive/waves' // 水波纹指令
import {roleList, roleSave, roleUpdate,roleDeleteById,saveRoleResource,roleFindByRoleMenuTree,getResBtnList,findByRoleButtonTree } from '@/api/system/system'

export default {
  name: 'role',
  directives: {
    waves
  },
  data() {
    return {
      tableData: [],
      paginationData:{
         order:'',
         sort:null
       },
      dialogAddVisible:false, // 是否显示添加弹窗
      dialogEditVisible:false, // 是否显示编辑弹窗
      dialogPermisionConfig:false, // 是否显示修改角色权限,
      dialogBtnPermisionConfig:false,
      loading:false, // 是否显示loading
      addForm:{// 添加form
        id:'',
        name: '',
        description: ''
      },
      editForm:{ //编辑form
        id:'',
        name: '',
        description: ''
      },
      permission:{// 权限管理
        data: [],
          defaultProps: {
            children: 'children',
            label: 'label'
          },
          checkedKeys: [] //默认选中的权限
      },
      btnPermission:{// 权限管理
        data: [],
          defaultProps: {
            children: 'children',
            label: 'label'
          },
          checkedKeys: [] //默认选中的权限
      },
      saveRolePermission:{
        roleId:'',
        resourceType:'menu',
        resourceIds:''
      },
      saveBtnRolePermission:{
        roleId:'',
        parentId:'',
        resourceType:'button',
        resourceIds:''
      },
      }
  },
  created() {
     this.getRoleList()
  },
  methods: {
    updateTableData({ column, prop, order }){//排序条件变化时触发 需发送请求
     var orderkey=(order=="descending"?"desc":"asc")
     this.paginationData.sort=prop;
     this.paginationData.order=orderkey;
     this.getRoleList();
    },
    handleDel(index,row){ // 删除此列 需发送请求
      this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
      }).then(() => {
        this.loading=true;
        // data为发送请求的参数
        var data={
          id:this.tableData[index].id
        }
        roleDeleteById(data).then(reponse=>{
          this.tableData.splice(index,1);
          //如果删除成功
          this.$notify.success({
            title: '信息',
            message: response.data.message,
            position: 'bottom-right'
          });
          this.loading=false;
        }).catch(reason => {
          this.loading=false;
        })
      })
    },
    getRoleList(callback) { // 获取数据
      this.loading=true;
      var formData={
          order:this.paginationData.order,
          sort:this.paginationData.sort
      }
      roleList(formData).then(response=>{
        this.tableData=response.data.obj.data;
        this.paginationData.pageSize=formData.limit;
        this.paginationData.currentPage=formData.page;
        this.paginationData.total=response.data.obj.count;
        this.loading=false;
        callback&&callback(response)
      })

    },
    handleAdd(){
      this.dialogAddVisible=true;
    },
    saveUpdateRole(){
      var dataForm=this.addForm; // 设置请求dataForm
      roleSave(dataForm).then(response=>{
        this.$notify.success({
          title: '信息',
          message: response.data.message,
          position: 'bottom-right'
        });
        this.getRoleList();
        this.dialogAddVisible=false;
        this.addForm.id='';
        this.addForm.name='';
        this.addForm.description='';
      })
    },
    handleEdit(index,row){ //编辑角色
      this.editForm.id=row.id;
      this.editForm.name=row.name;
      this.editForm.description=row.description;
      this.dialogEditVisible=true;
    },
    saveEdit(){
      var dataForm=this.editForm; // 设置请求dataForm
      roleUpdate(dataForm).then(response=>{
        this.$notify.success({
          title: '信息',
          message: response.data.message,
          position: 'bottom-right'
        });
        this.getRoleList();
        this.dialogEditVisible=false;
        this.editForm.id='';
        this.editForm.name='';
        this.editForm.description='';
      })
    },
    show_Permission_config(index,row){ // 批量配置用户权限
      this.permission.checkedKeys = [];
      this.saveRolePermission.roleId=row.id;
      this.saveBtnRolePermission.roleId=row.id
      var postData = {
        roleId : row.id
      }
      roleFindByRoleMenuTree(postData).then((response)=>{
        this.permission.data = response.data.obj;
      for(var i=0;i<response.data.obj.length;i++){
        var children = response.data.obj[i].children;
        for( var j=0;j<children.length;j++){
         if(children[j].hasPermission){
           this.permission.checkedKeys.push(children[j].id)
         }
        }
      }
      })
      this.dialogPermisionConfig=true
    },
    closePermisionConfig(){
      this.dialogPermisionConfig=false;
      this.permission.checkedKeys = [];
      this.permission.data = [];
      this.$refs.tree.setCheckedKeys([], false);
      this.saveRolePermission.resourceIds = null;
    },
    showBtnPermission(data){
      this.btnPermission.checkedKeys = [];
      this.btnPermission.data = [];
      this.dialogBtnPermisionConfig=true;
      this.saveBtnRolePermission.parentId=data.id;
      var formData={
        roleId:this.saveBtnRolePermission.roleId,
        resourceId:data.id
      }
      findByRoleButtonTree(formData).then(response=>{
        this.btnPermission.data=response.data.obj;
         var obj = response.data.obj;
        for(var i=0;i<obj.length;i++){
         if(obj[i].hasPermission == true){
           this.btnPermission.checkedKeys.push(obj[i].id)
         }
      }
      })
    },
    savePermisionConfig(){
      this.saveRolePermission.resourceIds=this.$refs.tree.getCheckedKeys().join(',');
      //获取 permission
     var formData=this.saveRolePermission;
     saveRoleResource(formData).then((response)=>{
       this.$notify.success({
         title: '信息',
         message: response.data.message,
         position: 'bottom-right'
       });
       this.closePermisionConfig();
       this.dialogPermisionConfig=false;
     })
    },
    closeBtnPermisionConfig(){
      this.dialogBtnPermisionConfig=false;
      this.btnPermission.checkedKeys = [];
      this.btnPermission.data = [];
      this.saveBtnRolePermission.resourceIds = null;
      this.$refs.btnTree.setCheckedKeys([], false);
    },
    saveBtnPermisionConfig(){
      this.saveBtnRolePermission.resourceIds=this.$refs.btnTree.getCheckedKeys().join(',');
      var formData=this.saveBtnRolePermission;
      saveRoleResource(formData).then((response)=>{
       this.$notify.success({
         title: '信息',
         message: response.data.message,
         position: 'bottom-right'
       });
       this.closeBtnPermisionConfig();
       this.dialogBtnPermisionConfig=false;
      });
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
