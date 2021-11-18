<template>
  <div>
    <el-row>
      <el-col :span="24" style="margin:20px 20px 0 20px;">
        <el-button type="primary" @click="showAdd" size="mini">添加</el-button>
      </el-col>
    </el-row>
    <div class="menuList">
      <el-table
      :data="dataSource"
      style="width: 100%"
      >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left"  v-if="props.row.children.length>0" inline class="demo-table-expand">
              <el-table :data="props.row.children" style="width: 100%">
                <el-table-column prop="id" width="100" ></el-table-column>
                <el-table-column prop="name" width="100" ></el-table-column>
                <el-table-column prop="icon" width="80"></el-table-column>
                <el-table-column prop="url" ></el-table-column>
                <el-table-column prop="path" ></el-table-column>
                <el-table-column  prop="permission" width="140"></el-table-column>
                <el-table-column  width="300">
                <template slot-scope="scope">
                <el-button-group>
                  <el-button
                    size="mini"
                    @click="delConfirm(scope.$index, scope.row)"  type="default" icon="el-icon-delete" >删除</el-button>
                     <el-button
                    size="mini"
                    @click="showUpdate(scope.$index, scope.row)"  type="default" icon="el-icon-edit">修改</el-button>
                  <el-button
                    size="mini"
                    @click="buttonEdit(scope.$index, scope.row)"  type="default" icon="el-icon-edit">按钮设置</el-button>
                </el-button-group>
                </template>
              </el-table-column>
              </el-table>
          </el-form>
        </template>
      </el-table-column>
        <el-table-column
          label="菜单id"
          prop="id"
          width="100">
        </el-table-column>
      <el-table-column
        label="菜单名称"
        prop="name"
        width="100">
      </el-table-column>
      <el-table-column
        label="图标"
        prop="icon"
        width="100">
      </el-table-column>
      <el-table-column
        label="url"
        prop="url"
        >
      </el-table-column>
      <el-table-column
        label="path"
        prop="path"
        >
      </el-table-column>
      <el-table-column
        label="权限"
        prop="permission"
        width="140">
      </el-table-column>
      <el-table-column width="250" label="操作">
        <template slot-scope="scope">
        <el-button-group>
          <el-button size="mini" @click="delConfirm(scope.$index, scope.row)" icon="el-icon-delete" > 删除</el-button>
          <el-button size="mini" @click="showUpdate(scope.$index, scope.row)" icon="el-icon-edit">修改</el-button></el-button-group>
        </template>
      </el-table-column>
      </el-table>
    </div>
    <!--update-->

   <el-dialog title="菜单编辑" :before-close="closeMenuFromDialog" :close-on-click-modal="false" :visible.sync="updateBox">
    <el-form :model="updateForm">
      <el-form-item label="菜单id" required label-width="120px">
        <el-input size="mini" v-model="updateForm.id" :disabled="true" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="菜单名称" required label-width="120px">
        <el-input size="mini" v-model="updateForm.name" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="菜单等级" required label-width="120px">
        <el-select size="mini" v-model= "menuxiala.value" @change="stateDj" placeholder="请选择菜单等级">
          <el-option label="一级菜单" key="一级菜单" value="一级菜单"></el-option>
          <el-option label="下级菜单" key="下级菜单" value="下级菜单"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="父菜单" label-width="120px" required v-if="menuxiala.value==='下级菜单'">
        <el-select size="mini" v-model="updateForm.parentId"  placeholder="请选择父菜单">
          <el-option
            v-for="item in dataSource"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
       <el-form-item label="权限字符" required label-width="120px">
        <el-input size="mini" v-model="updateForm.permission" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="菜单地址" label-width="120px">
        <el-input size="mini" v-model="updateForm.url" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="path" label-width="120px">
        <el-input size="mini" v-model="updateForm.path" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="菜单图标" label-width="120px">
        <el-input size="mini" v-model="updateForm.icon" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="菜单顺序" label-width="120px">
        <el-input size="mini" v-model="updateForm.seq" auto-complete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="closeMenuFromDialog">取 消</el-button>
      <el-button type="primary" @click="update">确 定</el-button>
    </div>
  </el-dialog>
    <!--add-->
    <el-dialog title="添加菜单" :before-close="closeMenuFromDialog" :close-on-click-modal="false" :visible.sync="addBox">
      <el-form :model="addForm">
        <el-form-item label="菜单id" required label-width="120px">
          <el-input size="mini" v-model="addForm.id" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="菜单名称" required label-width="120px">
          <el-input size="mini" v-model="addForm.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="菜单等级" required label-width="120px">
          <el-select size="mini" v-model= "menuxiala.value"  @change="stateDj" placeholder="请选择菜单等级">
            <el-option label="一级菜单" key="一级菜单" value="一级菜单"></el-option>
            <el-option label="下级菜单" key="下级菜单" value="下级菜单"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="父菜单" required label-width="120px" v-if="menuxiala.value==='下级菜单'">
          <el-select size="mini" v-model="addForm.parentId"  placeholder="请选择父菜单">
            <el-option
              v-for="item in dataSource"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="权限字符" required label-width="120px">
          <el-input size="mini" v-model="addForm.permission" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="菜单地址" label-width="120px">
          <el-input size="mini" v-model="addForm.url" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="path" label-width="120px">
          <el-input size="mini" v-model="addForm.path" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="菜单图标" label-width="120px">
          <el-input size="mini" v-model="addForm.icon" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="菜单顺序" label-width="120px">
          <el-input size="mini" v-model="addForm.seq" auto-complete="off" ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeMenuFromDialog">取 消</el-button>
        <el-button type="primary" @click="add">确 定</el-button>
      </div>
    </el-dialog>
    <!--button_list-->
      <el-dialog title="配置按钮" width="60%" :close-on-click-modal="false" :visible.sync="buttonListBox">
        <div class="menuList">
          <el-button @click="addBtn"  size="mini" type="primary">添加按钮</el-button>
          <el-table :data="dataBtnSource"
          style="width: 100%">
            <el-table-column
              label="按钮id"
              prop="id">
            </el-table-column>
            <el-table-column
              label="操作名称"
              prop="name">
            </el-table-column>
            <el-table-column
              label="权限名称"
              prop="permission">
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  @click="delConfirmBtn(scope.$index, scope.row)">删除</el-button>
                <el-button
                  size="mini"
                  @click="showUpdateBtn(scope.$index, scope.row)">编辑</el-button>
              </template>
            </el-table-column>
            </el-table>
          </div>
      </el-dialog>
      <!--btn add-->
      <el-dialog title="添加按钮"  :before-close="closeButtonFromDialog" :close-on-click-modal="false" :visible.sync="btnAddBox">
        <el-form :model="addBtnForm">
          <el-form-item label="按钮id" required label-width="120px">
            <el-input size="mini" v-model="addBtnForm.id" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="按钮名称" required label-width="120px">
            <el-input size="mini" v-model="addBtnForm.name" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="权限字符" required label-width="120px">
            <el-input size="mini" v-model="addBtnForm.permission" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="按钮顺序" label-width="120px">
            <el-input size="mini" v-model="addBtnForm.seq" auto-complete="off" ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="closeButtonFromDialog">取 消</el-button>
          <el-button type="primary" @click="saveBtn">确 定</el-button>
        </div>
      </el-dialog>
      <!--btn update-->
       <el-dialog title="编辑按钮" :before-close="closeButtonFromDialog" :close-on-click-modal="false" :visible.sync="btnUpdateBox">
          <el-form :model="updateBtnForm">
            <el-form-item label="按钮id" required label-width="120px">
              <el-input size="mini" v-model="updateBtnForm.id" :disabled="true" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="按钮名称" required label-width="120px">
              <el-input size="mini" v-model="updateBtnForm.name" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="权限字符" required label-width="120px">
              <el-input size="mini" v-model="updateBtnForm.permission" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="按钮顺序" label-width="120px">
              <el-input size="mini" v-model="updateBtnForm.seq" auto-complete="off" ></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="closeButtonFromDialog">取 消</el-button>
          <el-button type="primary" @click="updateBtn">确 定</el-button>
        </div>
      </el-dialog>
  </div>
</template>
<script>
import {menuList,deleteResource,updateResource,saveResource,getResBtnList} from '@/api/system/system'
export default {
  name: 'menuTree',
  data () {
    return {
      dataSource: [],
      dataBtnSource:[],
      updateBox:false,
      addBox:false,
      buttonListBox:false,
      btnUpdateBox:false,
      btnAddBox:false,
      menuxiala:{
        value:''
      },
      updateForm: {
        id:'',
        name:'',
        resourceType:'menu',
        permission:'',
        icon:'',
        url:'',
        path:'',
        parentId:'',
        seq:'',
      },
      addForm: {
        id:'',
        name:'',
        resourceType:'menu',
        permission:'',
        icon:'',
        url:'',
        path:'',
        parentId:'',
        seq:'',
      },
      updateBtnForm: {
        id:'',
        name:'',
        resourceType:'button',
        permission:'',
        icon:'',
        url:'',
        parentId:'',
        seq:''
      },
      addBtnForm: {
        id:'',
        name:'',
        resourceType:'button',
        permission:'',
        icon:'',
        url:'',
        parentId:'',
        seq:''
      },
    }
  },
  created(){
    this.getList()
  },
  methods: {
    expand(row, event, column){
      console.log(row.index)
      //判断是否存在子菜单 并且length大于0
      //如果存在，设置hidden为false展开子菜单
      //如果不存在
          //获取当前行id
          //通过id获取子菜单
          //如果子菜单length大于0 把子菜单添加到对应的id下面
      //再次点击菜单，收拢菜单
    },
    getList(){
        menuList().then((res)=>{
          this.dataSource=res.data.obj
          console.log(res)
        })
    },
    delConfirm(index,row){
      this.$confirm('此操作将永久删除该资源, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var formData={
          id:row.id
        }
         deleteResource(formData).then((res)=>{
           if(res.data.status==200){
              this.$message({
                type: 'success',
                message: res.data.message
              });
              this.getList()
           }else{
              this.$message({
                type: 'success',
                message: res.data.message
              });
           }
         })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    stateDj(value){//下拉等级修改
      if(value==="一级菜单"){
        this.addForm.parentId="";
        this.updateForm.parentId="";
      }
    },
    showUpdate(index,row){
      this.updateBox=true;
      if(row.parentId==0){
        this.menuxiala.value='一级菜单';
      }else{
        this.menuxiala.value='下级菜单';
      }
      this.updateForm.name=row.name;
      this.updateForm.resourceType=row.resourceType;
      this.updateForm.id=row.id;
      this.updateForm.permission=row.permission;
      this.updateForm.icon=row.icon;
      this.updateForm.url=row.url;
      this.updateForm.path=row.path;
      this.updateForm.parentId=row.parentId;
      this.updateForm.seq=row.seq;
    },
    showAdd(){
      this.menuxiala.value='';
      this.addBox=true;
    },

    add(){
      if(this.menuxiala.value=='一级菜单'){
        this.addForm.parentId = 0;
      }
      var formData={
        id : this.addForm.id,
        name:this.addForm.name,
        resourceType:this.addForm.resourceType,
        permission:this.addForm.permission,
        icon:this.addForm.icon,
        url:this.addForm.url,
        path:this.addForm.path,
        parentId:this.addForm.parentId,
        seq:this.addForm.seq,
      }
      saveResource(formData).then((res)=>{
        this.getList()
        this.$notify.success({
          title: '信息',
          message: res.data.message,
          position: 'bottom-right'
        });
        this.closeMenuFromDialog();
      })
    },
    update(){
      if(this.menuxiala.value=='一级菜单'){
        this.addForm.parentId = 0;
      }
      var formData={
        id:this.updateForm.id,
        name:this.updateForm.name,
        resourceType:this.updateForm.resourceType,
        permission:this.updateForm.permission,
        icon:this.updateForm.icon,
        url:this.updateForm.url,
        path:this.updateForm.path,
        parentId:this.updateForm.parentId,
        seq:this.updateForm.seq,
      }
      updateResource(formData).then((res)=>{
        this.getList()
        this.$notify.success({
          title: '信息',
          message: res.data.message,
          position: 'bottom-right'
        });
        this.closeMenuFromDialog();
      })
    },
    getBtnList(){
      var formData={
        menuId:this.addBtnForm.parentId
      }
      getResBtnList(formData).then((res)=>{
        this.dataBtnSource=res.data.obj;
      })
    },
    buttonEdit(index,row){
      this.addBtnForm.parentId=row.id;
      var formData={
        menuId:row.id
      };
      getResBtnList(formData).then((res)=>{
        this.dataBtnSource=res.data.obj
      });
      this.buttonListBox=true;
    },
    delConfirmBtn(index,row){
      this.$confirm('此操作将永久删除该资源, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var formData={
          id:row.id
        }
         deleteResource(formData).then((res)=>{
           this.getBtnList();
           this.$notify.success({
             title: '信息',
             message: res.data.message,
             position: 'bottom-right'
           });
         });
      });

    },
    showUpdateBtn(index,row){
      this.updateBtnForm.id=row.id;
      this.updateBtnForm.name=row.name;
      this.updateBtnForm.permission=row.permission;
      this.updateBtnForm.icon=row.icon;
      this.updateBtnForm.seq=row.seq;
      this.btnUpdateBox=true;
    },
    addBtn(){
      this.btnAddBox=true;
    },
    saveBtn(){
      var formData={
        id:this.addBtnForm.id,
        name:this.addBtnForm.name,
        resourceType:this.addBtnForm.resourceType,
        permission:this.addBtnForm.permission,
        icon:this.addBtnForm.icon,
        parentId:this.addBtnForm.parentId,
        seq:this.addBtnForm.seq
      }
      saveResource(formData).then((res)=>{
        this.getBtnList();
        this.$notify.success({
          title: '信息',
          message: res.data.message,
          position: 'bottom-right'
        });
        this.closeButtonFromDialog ();
      })
    },
    updateBtn(){
      var formData={
        id:this.updateBtnForm.id,
        name:this.updateBtnForm.name,
        permission:this.updateBtnForm.permission,
        resourceType:this.updateBtnForm.resourceType,
        icon:this.updateBtnForm.icon,
        parentId:this.addBtnForm.parentId,
        seq:this.updateBtnForm.seq,
      }
      updateResource(formData).then((res)=>{
        this.getBtnList()
        this.$notify.success({
          title: '信息',
          message: res.data.message,
          position: 'bottom-right'
        });
        this.closeButtonFromDialog ();
      })
    },
    closeMenuFromDialog (){
      this.addBox=false;
      this.updateBox=false;
      this.updateForm.id=null;
      this.updateForm.name=null;
      this.updateForm.permission=null;
      this.updateForm.icon=null;
      this.updateForm.url=null;
      this.updateForm.path=null;
      this.updateForm.parentId=null;
      this.updateForm.seq=null;
      this.addForm.id=null;
      this.addForm.name=null;
      this.addForm.permission=null;
      this.addForm.icon=null;
      this.addForm.url=null;
      this.addForm.path=null;
      this.addForm.parentId=null;
      this.addForm.seq=null;

    },
    closeButtonFromDialog (){
      this.btnAddBox=false;
      this.btnUpdateBox=false;
      this.updateBtnForm.id=null;
      this.updateBtnForm.name=null;
      this.updateBtnForm.permission=null;
      this.updateBtnForm.seq=null;
      this.addBtnForm.id=null;
      this.addBtnForm.name=null;
      this.addBtnForm.permission=null;
      this.addBtnForm.seq=null;
    },
  }
}
</script>
<style scoped>
ul{padding:0;}
li{
  list-style:none;

}
</style>
