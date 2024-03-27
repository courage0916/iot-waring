<template>
  <div>
    <div class="container">
      <div class="search-box">
        <el-input v-model="searchStr" placeholder="用户名" class="search-input mr10" clearable></el-input>
        <el-button type="primary" :icon="Search" @click="handleSearch($event)">搜索</el-button>
        <el-button type="success" :icon="CirclePlusFilled" @click="addBtn($event)">新增</el-button>
      </div>
      <el-table :data="tableData"  class="table" ref="multipleTable" header-cell-class-name="table-header">
        <el-table-column prop="id" label="用户ID" width="280" align="center"></el-table-column>
        <el-table-column prop="username" label="用户名" align="center"></el-table-column>
        <el-table-column prop="type" label="用户类别" align="center"></el-table-column>
        <el-table-column label="操作" width="280" align="center">
          <template #default="scope">
            <el-button
                type="primary"
                size="small"
                :icon="Edit"
                @click="handleEdit(scope.$index, scope.row,$event)"
                v-permiss="15"
            >
              编辑
            </el-button>
            <el-button
                type="danger"
                size="small"
                :icon="Delete"
                @click="handleDelete(scope.row.id,$event)"
                v-permiss="16"
                v-if="scope.row.username!='admin'"
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
            :current-page="pageData.pageIndex"
            :page-size="pageData.pageSize"
            :total="pageTotal"
            @current-change="handlePageChange"
        ></el-pagination>
      </div>

    </div>
    <el-dialog
        :title="idEdit ? '编辑用户' : '新增用户'"
        v-model="visible"
        width="500px"
        destroy-on-close
        :close-on-click-modal="false"
        @close="closeDialog"
    >
      <TableEdit :data="rowData" :edit="idEdit" :update="updateData" />
    </el-dialog>

  </div>
</template>

<script setup lang="ts" name="basetable">
import { ref, reactive } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { Delete, Edit, Search, CirclePlusFilled, View } from '@element-plus/icons-vue';
import TableEdit from '@/views/user/edit.vue';
import {page,del} from '@/api/user';



const addBtn = (evt) => {
  visible.value = true;
  let target = evt.target
  if (target.nodeName === 'I') {
    target = evt.target.parentNode
  }
  target.blur()
};
interface TableItem {
  id: number;
  username: string;
  password:string;
  type:string;
}
const activeName = ref('first');
const query = reactive({
  address: '',
  name: '',
  pageIndex: 1,
  pageSize: 10
});
const tableData = ref<TableItem[]>([]);
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
const pageTotal = ref(0);
// 获取表格数据
const getData = async () => {
  await page(reactive({
    curr: pageData.currentPage,
    size: pageData.pageSize,
    navigatePages: 7
  }),reactive({
    username:searchStr
  })).then(res=>{
    tableData.value = res.data.list;
    pageTotal.value = parseInt(res.data.total);

  })
};
getData();

// 查询操作
const handleSearch = (evt) => {
  query.pageIndex = 1;
  getData();

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
  ElMessageBox.confirm('确定要删除该用户吗？账号下的设备也会被一起删除，如果需要使用设备要重新录入', '提示', {  confirmButtonText: '删除',
    cancelButtonText: '取消',
    type: 'warning'
  })
      .then(() => {
        del(id).then(function (){
          getData();

          ElMessage.success('删除成功');
          let target = evt.target
          if (target.nodeName === 'I') {
            target = evt.target.parentNode
          }
          target.blur()
        });

      })
      .catch(() => {});
};

const visible = ref(false);
let idx: number = -1;
const idEdit = ref(false);
const rowData = ref({});
const handleEdit = (index: number, row: TableItem,evt) => {
  idx = index;
  row.password = "";
  rowData.value = row;
  idEdit.value = true;
  visible.value = true;
  let target = evt.target
  if (target.nodeName === 'I') {
    target = evt.target.parentNode
  }
  target.blur()
};
const updateData = (row: TableItem) => {
  idEdit.value ? (tableData.value[idx] = row) : tableData.value.unshift(row);
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
