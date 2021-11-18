<template>
  <div>
    <div>
      <!--搜索-->
      <el-row class="searchBox">
        <el-col :xl="4" :lg="4" :md="11" :sm="11" :xs="24">
          <el-input size="mini" placeholder="编号" v-model="id"></el-input>
        </el-col>
        <el-col :xl="4" :lg="4" :md="11" :sm="11" :xs="24">
          <el-input size="mini" placeholder="参数值" v-model="value"></el-input>
        </el-col>
        <el-col :xl="4" :lg="4" :md="11" :sm="11" :xs="24">
           <el-select size="mini" v-model="typeId" placeholder="请选择">
            <el-option v-for="item in systemParameterTypeList" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-col>
        <el-col :xl="4" :lg="4" :md="24" :sm="24" :xs="24" >
          <el-button-group>
            <el-button type="default" @click="searchData" icon="el-icon-search" size="mini">查询</el-button>
            <el-button type="default" @click="resetSearch" icon="el-icon-refresh" size="mini">重置</el-button>
          </el-button-group>

        </el-col>
      </el-row>
      <!--操作-->
      <el-row class="operationBox">
        <el-button  v-if="roleButton.systemParameter_save" type="primary" icon="el-icon-plus" @click="formDialogVisible=true"   size="mini">添加</el-button>
      </el-row>
      <el-table :data="systemParameterList" v-loading="loading" @select-all="selectAll"
                @select="select" stripe style="width: 100%" reserve-selection="true">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="编号" ></el-table-column>
        <el-table-column prop="value" label="参数值" ></el-table-column>
        <el-table-column prop="description" label="描述" ></el-table-column>
        <el-table-column prop="seq" label="排序" ></el-table-column>
        <el-table-column prop="typeId" label="参数类型"></el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button-group>
                <el-button v-if="roleButton.systemParameter_update" @click="handleEdit(scope.$index, scope.row)" type="default" icon="el-icon-edit"  size="mini">编辑</el-button>
                <el-button  v-if="roleButton.systemParameter_delete" @click="handleDel(scope.$index, scope.row)" type="default" icon="el-icon-delete"   size="mini">删除</el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </el-table>
    </div>
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
    <el-dialog title="系统参数类型" :before-close="closeDialog" :visible.sync="formDialogVisible" :close-on-click-modal="false">
      <el-form :model="systemParameter" ref="systemParameter" :rules="rules">
        <el-form-item label="编号：" prop="id" label-width="120px"  v-if="!editFlag">
          <el-input size="mini" v-model="systemParameter.id"></el-input>
        </el-form-item>
        <el-form-item label="编号：" prop="id" label-width="120px" v-if="editFlag">
          <el-input size="mini" v-model="systemParameter.id" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="参数值：" prop="value" label-width="120px">
          <el-input size="mini" v-model="systemParameter.value"></el-input>
        </el-form-item>
        <el-form-item label="描述：" prop="description" label-width="120px">
          <el-input size="mini" v-model="systemParameter.description"></el-input>
        </el-form-item>
        <el-form-item label="排序：" prop="seq" label-width="120px">
          <el-input size="mini" v-model="systemParameter.seq"></el-input>
        </el-form-item>
        <el-form-item label="参数类型：" prop="seq" label-width="120px">
           <el-select size="mini" v-model="systemParameter.typeId" placeholder="请选择">
            <el-option v-for="item in systemParameterTypeList" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeDialog()">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {list, save, update, deleteById, getAllType} from '@/api/system/systemParameter'
  import {findByRoleButton } from '@/api/system/system'
  export default {
    name: "sytemParameter",
    data() {
      return {
        roleButton : {
          systemParameter_save : false,
          systemParameter_update : false,
          systemParameter_delete : false,
        },
        editFlag : false,
        id : null,
        value : null,
        typeId : null,
        loading : false,
        formDialogVisible : false,
        systemParameterList:[],
        systemParameterTypeList:[],
        page : {
          currentPage:1,
          pageSizes:[10,20,30,50],
          pageSize:10,
          total:0,
          order:'',
          sort:null
        },
        systemParameter : {
          id : null,
          value : null,
          description : null,
          seq : null,
          typeId : null,
        },
        rules: {
          id: [
            { required: true, message: '请输入编号', trigger: 'blur' },
          ],
          value: [
            { required: true, message: '请输入参数值', trigger: 'blur' },
          ],
        }
      }
    },
    created() {
      //查询用户按钮
      findByRoleButton({resourceId : "systemParameter"}).then(response => {
        var buttonList = response.data.obj;
        for(var i=0; i < buttonList.length; i++){
          this.roleButton[buttonList[i].id] = buttonList[i].hasPermission;
        }
      });
      //this.getSystemParameterList();
      getAllType().then(response => {
        var data = response.data;
        if(data.status == 200){
          this.systemParameterTypeList = data.obj;
        }
      });
    },
    methods: {
      getSystemParameterList(){
        this.loading=true;
        var formData={
          id : this.id,
          value : this.value,
          typeId : this.typeId,
          page:this.page.currentPage,
          limit:this.page.pageSize,
          order:this.page.order,
          sort:this.page.sort,
        };
        list(formData).then(response => {
          this.systemParameterList=response.data.obj.data;
          this.page.pageSize=formData.limit;
          this.page.currentPage=formData.page;
          this.page.total=response.data.obj.count;
          this.loading=false;
        });
      },
      searchData(){
        this.loading=true;
        var formData={
          id : this.id,
          value : this.value,
          typeId : this.typeId,
          page:this.page.currentPage,
          limit:this.page.pageSize,
          order:this.page.order,
          sort:this.page.sort,
        };
        list(formData).then(response => {
          this.systemParameterList=response.data.obj.data;
          this.page.pageSize=formData.limit;
          this.page.currentPage=formData.page;
          this.page.total=response.data.obj.count;
          this.loading=false;
        });
      },
      saveOrUpdate(){//保存或者更新系统参数类型
        this.$refs['systemParameter'].validate((valid) => {
          if (valid) {
            this.formDialogVisible=false;
            this.loading=true;
            var formData={
              id : this.systemParameter.id,
              value : this.systemParameter.value,
              description : this.systemParameter.description,
              seq : this.systemParameter.seq,
              typeId : this.systemParameter.typeId,
            };
            if(this.editFlag){
              update(formData).then(response => {
                var data = response.data;
                if(data.status == 200 ){
                  this.$notify.success({
                    title: '信息',
                    message: data.message,
                    position: 'bottom-right'
                  });
                  this.resetFormData();
                  this.getSystemParameterList()
                }else{
                  this.loading=false;
                  this.formDialogVisible=true;
                }
              });
            }else{
              save(formData).then(response => {
                var data = response.data;
                if(data.status == 200 ){
                  this.$notify.success({
                    title: '信息',
                    message: data.message,
                    position: 'bottom-right'
                  });
                  this.resetFormData();
                  this.getSystemParameterList()
                }else{
                  this.loading=false;
                  this.formDialogVisible=true;
                }
              });
            }
          } else {
            return false;
          }
        });
      },
      handleEdit(index,row){ //编辑
        //显示原信息
        this.systemParameter.id=row.id;
        this.systemParameter.value=row.value;
        this.systemParameter.description=row.description;
        this.systemParameter.seq=row.seq;
        this.systemParameter.typeId=row.typeId;
        this.editFlag=true;
        this.formDialogVisible=true;
      },
      handleDel(index,row){ // 删除此列 单个删除  需发送请求
        this.$confirm('确认删除吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading=true
          //console.log(this.tableData[index].id)
          // data为发送请求的参数
          var data={
            id:this.systemParameterList[index].id
          }
          deleteById(data).then(response=>{
            if(response.data.status==200){
              this.systemParameterList.splice(index,1)
              this.$notify.success({
                title: '信息',
                message: response.data.message,
                position: 'bottom-right'
              });
              this.loading=false
            }else{
              //3.2删除失败
              this.loading=false
            }
            // 清空选择状态
            this.$refs.table.clearSelection()
          })
        });
      },
      resetSearch(){//重置搜索参数
        this.id=null;
        this.value=null;
        this.typeId=null;
      },
      resetFormData(){//重置表单参数
        this.systemParameter.id=null;
        this.systemParameter.value=null;
        this.systemParameter.description=null;
        this.systemParameter.seq=null;
        this.systemParameter.typeId=null;
        this.editFlag=false;
      },
      closeDialog(){//关闭dialog
        this.resetFormData();
        this.formDialogVisible=false;
        this.editFlag=false;
      },
      selectAll(selection){ // 全选
        this.checkList=selection
      },
      select(selection, row){ //选中某一个
        this.checkList=selection
      },
      handleSizeChange(val) { // 修改每页显示条数 需要发送请求
        this.page.pageSize=val;
        this.getSystemParameterList();//重新获取当前页数据
      },
      handleCurrentChange(val) { // 修改当前页 需要发送请求
        this.page.currentPage=val;
        this.getSystemParameterList();//重新获取当前页数据
      }
    }

  }
</script>

<style scoped>
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
