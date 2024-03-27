<template>
  <div>
    <div class="container">
      <div class="search-box">
        <el-input v-model="searchStr" placeholder="设备ID/手机号" class="search-input mr10" clearable></el-input>
        <el-button type="primary" :icon="Search" @click="handleSearch($event)">搜索</el-button>
        <el-button type="success" :icon="CirclePlusFilled" @click="addBtn($event)" v-if="show">新增</el-button>
      </div>
      <el-table :data="tableData"  class="table" ref="multipleTable" header-cell-class-name="table-header">
        <el-table-column prop="deviceId" label="卡号" width="205" align="center"></el-table-column>
        <el-table-column prop="phone" label="主要通知电话" align="center"></el-table-column>
        <el-table-column prop="phone2" label="次要通知电话" align="center"></el-table-column>
        <el-table-column prop="phone3" label="备用通知电话" align="center"></el-table-column>
        <el-table-column prop="remark" label="设备备注" align="center"></el-table-column>
        <el-table-column  label="短信通知" align="center">
          <template #default="scope">
            <el-switch v-model="scope.row.smsNotice" disabled />
          </template>
        </el-table-column>
        <el-table-column prop="transfer" label="电话通知" align="center">
          <template #default="scope">
            <el-switch v-model="scope.row.phoneNotice" disabled />
          </template>
        </el-table-column>

        <el-table-column label="操作" width="380" align="center" >
          <template #default="scope">
            <el-button
                type="success"
                size="small"
                :icon="View"
                @click="detail(scope.row.id, scope.row,$event)"
                v-permiss="15"
            >
              查看最近三次告警
            </el-button>


            <el-button
                type="primary"
                size="small"
                :icon="Edit"
                @click="handleEdit(scope.row.id, scope.row,$event)"
                v-permiss="15"
                v-if="show"
            >
              编辑
            </el-button>
            <el-button
                type="danger"
                size="small"
                :icon="Delete"
                @click="handleDelete(scope.row.id,$event)"
                v-permiss="16"
                v-if="show"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
            background
            layout="total, prev, pager, next"
            :current-page="pageData.currentPage"
            :page-size="pageData.pageSize"
            :total="pageTotal"
            @current-change="handlePageChange"
        ></el-pagination>
      </div>
    </div>

    <el-dialog
        :title="idEdit ? '编辑设备' : '新增设备'"
        v-model="visible"
        width="700px"
        destroy-on-close
        :close-on-click-modal="false"
        @close="closeDialog"
    >
      <TableEdit :data="rowData" :edit="idEdit" :update="updateData" />
    </el-dialog>

    <el-dialog title="查看最近三次告警" v-model="idDetailVisible" width="700px" destroy-on-close>
      <TableDetail :data="rowData" />
    </el-dialog>



  </div>
</template>

<script setup lang="ts" name="basetable">
import { ref, reactive } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { Delete, Edit, Search, CirclePlusFilled, View } from '@element-plus/icons-vue';
import TableEdit from '@/views/device/edit.vue';
import TableDetail from '@/views/device/detail.vue';

import {page,del} from '@/api/device';

const username: string | null = JSON.parse(localStorage.getItem('ms_user') + "").username;

const show = username != "admin";


interface TableItem {
  id: number;
  deviceId: string;
  phone: string;
  phone2: string;
  phone3: string;
  remark: string;
  status:string;
  smsNotice: boolean;
  phoneNotice: boolean;
}


interface PageFormat {
  currentPage:number
  pageSize:number
  total:number
}

const pageData:PageFormat = reactive({
  currentPage: 1,
  pageSize: 6,
  total: 0
});


let searchStr = ref('');

const tableData = ref<TableItem[]>([]);
const pageTotal = ref(0);
// 获取表格数据
const getData = async () => {
  await page(reactive({
    curr: pageData.currentPage,
    size: pageData.pageSize,
    navigatePages: 7
  }),reactive({
    deviceId:searchStr,
    phone:searchStr
  })).then(res=>{
    tableData.value = res.data.list;
    pageTotal.value = parseInt(res.data.total);

  })
};

getData();

// 查询操作
const handleSearch = (evt) => {
  getData();
  let target = evt.target
  if (target.nodeName === 'I') {
    target = evt.target.parentNode
  }
  target.blur()
};
const addBtn = (evt) => {
  visible.value = true;
  let target = evt.target
  if (target.nodeName === 'I') {
    target = evt.target.parentNode
  }
  target.blur()
};
// 分页导航
const handlePageChange = (val: number) => {

  pageData.currentPage = val;
  getData();
};

// 删除操作
const handleDelete = (id: number,evt) => {
  // 二次确认删除
  ElMessageBox.confirm('确定要删除吗？', '提示', {   confirmButtonText: '删除',
    cancelButtonText: '取消',
    type: 'warning'
  })
      .then(() => {
        del(id).then(function (){
          getData();

          ElMessage.success('删除成功');

        });



      })
      .catch(() => {});

  let target = evt.target
  if (target.nodeName === 'I') {
    target = evt.target.parentNode
  }
  target.blur()
};
const idDetailVisible = ref(false);
let visible = ref(false);
let idx: number = -1;
const idEdit = ref(false);
const rowData = ref({});
const handleEdit = (index: number, row: TableItem,evt) => {
  idx = index;
  rowData.value = row;
  idEdit.value = true;
  visible.value = true;

  let target = evt.target
  if (target.nodeName === 'I') {
    target = evt.target.parentNode
  }
  target.blur()
};
const detail = (index: number, row: TableItem,evt) => {
  idx = index;
  rowData.value = row;

  idDetailVisible.value = true;

  let target = evt.target
  if (target.nodeName === 'I') {
    target = evt.target.parentNode
  }
  target.blur()
};
const updateData =  (row: TableItem) => {

  console.log(tableData.value);

  getData();
  closeDialog();

};

const closeDialog = () => {
  visible.value = false;
  idEdit.value = false;
};

const visible1 = ref(false);
const handleView = (row: TableItem) => {
  rowData.value = row;
  visible1.value = true;
};
</script>

<style scoped>
.search-box {
  margin-bottom: 20px;
}

.search-input {
  width: 200px;
}

.mr10 {
  margin-right: 10px;
}
.table-td-thumb {
  display: block;
  margin: auto;
  width: 40px;
  height: 40px;
}
</style>
