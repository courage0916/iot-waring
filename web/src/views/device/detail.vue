<template>


  <div v-for="(item,index) in datas" :key="index">
    <el-row style="margin-top:10px;">

      <el-col :span="8">
        <el-alert :title="item.createTime" type="success" :closable="false" />
      </el-col>
      <el-col :span="14">
        <el-alert :title="item.content" type="success" :closable="false" />
      </el-col>
      <el-link></el-link>
    </el-row>
  </div>

</template>

<script lang="ts" setup>
import { ElMessage, FormInstance, FormRules, UploadProps } from 'element-plus';
import {reactive, ref} from 'vue';
import {heartbeat} from '@/api/device';

let datas = ref([]);

const props = defineProps({
	data: {
		type: Object,
		required: true
	}
});

console.log(props.data)
heartbeat(props.data.deviceId).then(res=>{
  console.log("----------")
  console.log(res)
  datas.value = res.data;
})
</script>
