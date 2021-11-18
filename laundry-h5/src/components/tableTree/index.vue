<template>
    <ul class="role-table col-xs-offset-2 col-md-offset-1 col-sm-offset-2">
        <li class="header">
            <div class="left">菜单列表</div>
            <div class="right">功能权限</div>
        </li>
        <div class="vertical-line"></div>
        <li  v-for="(item,index) in dataList" :key="item.id">
            <div class="left h40"  @click="fold(item)">
                <i :class="{'el-icon-caret-right':item.folded,'el-icon-caret-bottom':!item.folded}"
                v-cloak v-if="item.second"
                class="item-icon"></i>
                <el-checkbox @click.native="handleOneCheckAll($event,item)" v-cloak v-if="!item.second" :indeterminate="item.isIndeterminate" v-model="item.checkAll"> {{item.title}}</el-checkbox>
                    <span v-if="item.second" v-cloak>{{item.title}}</span>
            </div>
            <div class="right h40">
                <el-checkbox v-if="item.second" :indeterminate="item.isIndeterminate" @change="checkSecondAll(item)" v-model="item.firstCheckAll"> 所有</el-checkbox>

                <el-checkbox-group v-model="item.checkedCities" @change="handleOneCheckedCitiesChange(item)" v-else>
                    <el-checkbox  v-for="m in item.list" :label="m.id" :key="m.id" v-cloak> {{m.name}}</el-checkbox>
                </el-checkbox-group>
            </div>
            <div class="line"></div>
            <ul v-show="item.second&&!item.folded">
                <li class="h40" v-for="(second,cur) in item.second" :key="second.id">
                    <div class="left">
                        <el-checkbox   v-model="second.checkAll" @change="handleCheckAllChange($event,item,second)" v-cloak>
                            {{second.title}}
                        </el-checkbox>
                    </div>
                    <div class="right">
                        <el-checkbox-group v-model="second.checkedCities" @change="handleCheckedCitiesChange(item,second)">
                            <el-checkbox v-for="p in second.list" :label="p.id" :key="p.id" v-cloak >
                                {{p.name}}
                            </el-checkbox>
                        </el-checkbox-group>
                    </div>
                    <div class="line"></div>
                </li>
            </ul>
        </li>
    </ul>
</template>
<script>
export default{
    name:'table-tree',
    data(){
        return {
            dataList: [
                {
                    title: "概况",
                    id: "0",
                    checkedCities:[],
                    list: [
                        {
                            name: "浏览",
                            id: "10001"
                        },
                        {
                            name: "修改",
                            id: "11111"
                        }
                    ]
                },
                {
                    title: "会员管理",
                    id: "1",
                    second: [
                        {
                            title: "会员列表",
                            id: "11",
                            list: [
                                {
                                    name: "新增",
                                    id: "10002"
                                },
                                {
                                    name: "修改",
                                    id: "10003"
                                }
                            ],
                            checkedCities: []
                        },
                        {
                            title: "会员等级",
                            id: "12",
                            list: [
                                {
                                    name: "新增",
                                    id: "10004"
                                },
                                {
                                    name: "修改",
                                    id: "10005"
                                },
                                {
                                    name: "删除",
                                    id: "1006"
                                }

                            ],
                            checkedCities: []
                        }
                    ]
                },
                {
                    title: "菜品管理",
                    id: "2",
                    second: [
                        {
                            title: "菜品列表",
                            id: "21",
                            list: [
                                {
                                    name: "新增",
                                    id: "10007"
                                },
                                {
                                    name: "修改",
                                    id: "10008"
                                }
                            ],
                            checkedCities: []
                        },
                        {
                            title: "会员等级",
                            id: "22",
                            list: [
                                {
                                    name: "浏览",
                                    id: "10009"
                                },
                                {
                                    name: "新增",
                                    id: "10010"
                                },
                                {
                                    name: "修改",
                                    id: "10011"
                                },
                                {
                                    name: "删除",
                                    id: "10012"
                                }
                            ],
                            checkedCities: []
                        }
                    ]
                }
            ],
     }
    },
     methods: {
        fold: function (item) {
            if (typeof item.folded === "undefined") {
                this.$set(item, "folded", true);
            } else {
                item.folded = !item.folded;
            }
        },
        // 全选一项
        handleCheckAllChange($event, item, second) {
            let arr = [];
            for (let a = 0; a < second.list.length; a++) {
                arr.push(second.list[a].id)
            }
            if (typeof second.checkedCities === "undefined") {
                this.$set(second, "checkedCities", arr);
            }
            second.checkedCities = $event ? arr : [];
            // if (typeof second === "undefined") {
            //     this.$set(second, "isIndeterminate", false)
            // }
            // second.isIndeterminate = false;
            if (typeof second.checkAll === "undefined") {
                this.$set(second, "checkAll", true);
            }
            if (typeof item.isIndeterminate === "undefined") {
                this.$set(item, "isIndeterminate", true);
            }
            for (let a = 0; a < item.second.length; a++) {
                if (!item.second[a].checkAll) {
                    item.isIndeterminate = true;
                    for (let a = 0; a < item.second.length; a++) {
                        if (item.second[a].checkAll) {
                            break
                        } else {
                            item.isIndeterminate = false;
                            item.firstCheckAll = false;
                        }
                    }
                    break
                } else {
                    item.isIndeterminate = false;
                    item.firstCheckAll = true;
                }
            }

        },
        // 单选
        handleCheckedCitiesChange(item, second) {
            let checkedCount = second.checkedCities.length;
            if (typeof second.checkAll === "undefined") {
                this.$set(second, "checkAll", false);
            }
            if (typeof second.isIndeterminate === "undefined") {
                this.$set(second, "isIndeterminate", false);
            }
            if (typeof item.isIndeterminate === "undefined") {
                this.$set(item, "isIndeterminate", true);
            }
            second.isIndeterminate = checkedCount > 0 && checkedCount < second.list.length;
            second.checkAll = checkedCount === second.list.length;
            if (checkedCount === 0) {
                second.isIndeterminate = false
            }
            for (let a = 0; a < item.second.length; a++) {
                if (!item.second[a].checkAll) {
                    item.isIndeterminate = true;
                    for (let b = 0; b < item.second.length; b++) {
                        if (item.second[b].checkedCities.length > 0) {
                            break
                        } else {
                            item.isIndeterminate = false;
                            item.firstCheckAll = false;
                        }
                    }
                    break
                } else {
                    item.isIndeterminate = false;
                    item.firstCheckAll = true;
                }
            }

        },
        // 点击所有
        checkSecondAll: function (item) {
            if (typeof item.firstCheckAll === "undefined") {
                this.$set(item, "firstCheckAll", true);
            }
            for (let a = 0; a < item.second.length; a++) {
                this.checkItemAll(item.firstCheckAll, item.second[a])
            }
            item.isIndeterminate=false
        },
        checkItemAll: function (flag, item) {
            let arr = [];
            for (let a = 0; a < item.list.length; a++) {
                arr.push(item.list[a].id)
            }
            if (typeof item.checkedCities === "undefined") {
                this.$set(item, "checkedCities", arr);
            }
            item.checkedCities = flag ? arr : [];
            item.checkAll = flag;
        },
        
        // 没有二级菜单
        handleOneCheckedCitiesChange:function (item) {
            let checkedCount = item.checkedCities.length;
            if (typeof item.isIndeterminate === "undefined") {
                this.$set(item, "isIndeterminate", false);
            }
            if (typeof item.checkAll === "undefined") {
                this.$set(item, "checkAll", false);
            }
            item.isIndeterminate = checkedCount > 0 && checkedCount < item.list.length;
            item.checkAll = checkedCount === item.list.length;
        },
        handleOneCheckAll:function ($event,item) {
            let arr = [];
            for (let a = 0; a < item.list.length; a++) {
                arr.push(item.list[a].id)
            }

            item.checkedCities = $event? arr : [];
        }
    }
}
</script>
<style>
.role-table {
    border: 1px solid #e0e0e0;
    border-bottom: none;
    padding: 0;
    position: relative;
}

.header {
    height: 40px;
    line-height: 40px;
    border-bottom: 1px solid #e7e7e7;
    background: #F8F8F9;
    text-align: center;
}

.vertical-line {
    width: 1px;
    height: 100%;
    background: #ddd;
    position: absolute;
    left: 30%;
    top: 0
}

.left {
    width: 30%;
    float: left;
    padding-left: 10px;
    user-select: none;
    cursor: pointer;
}

.one {
    padding-left: 20px;
}

.right {
    width: 70%;
    float: left;
    padding-left: 10px;
}

.item-icon {
    margin-left: -5px;
    padding: 5px;
}

.line {
    clear: both;
    width: 100%;
    height: 1px;
    background: #e0e0e0;
}
.h40{
    height: 39px;
    line-height: 39px;
}
[v-cloak] {
    display: none;
}
.role-table li{
    list-style:none;
}
</style>