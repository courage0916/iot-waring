<template>
  <div>
    <el-row :gutter="20">

      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <div class="clearfix">
              <span>通知配置</span>
            </div>
          </template>
          <el-form ref="formRef"  :model="form" label-width="120px" :rules="rules" >
            <el-form-item label="通知手机号：" prop="phone">
              <el-input v-model="form.phone" placeholder="手机号" class="search-input mr10" clearable></el-input>
            </el-form-item>

            <el-form-item label="短信通知：">
              <el-switch v-model="form.smsNotice" disabled />
            </el-form-item>
            <el-form-item label="电话通知：">
              <el-switch v-model="form.phoneNotice"  />
            </el-form-item>

            <el-form-item label="提醒时间：" v-for="(item,index) in form.times" :key="item">
              <el-time-picker value-format="HH:mm:ss" format="HH:mm:ss" v-model="form.times[index]" placeholder="提醒时间" />
              <el-button type="success" style="margin-left: 20px;" @click="addTime($event)">新增</el-button>
              <el-button type="danger"  @click="delTime($event,index)">删除</el-button>
            </el-form-item>


            <el-form-item>
              <el-button id="manyou" type="primary" @click="onSubmit(formRef)">保存</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <div class="clearfix">
              <span>注意事项</span>
            </div>
          </template>
          <el-form label-width="20px">
            <el-form-item>
              <el-alert title="任何配置改动必须点击保存按钮才有效果" type="warning" />
            </el-form-item>
            <el-form-item>
              <el-alert title="短信通知是默认的通知方式，不可取消" type="warning" />
            </el-form-item>
            <el-form-item>
              <el-alert title="通知手机号不支持座机号码" type="warning" />
            </el-form-item>
            <el-form-item>
              <el-alert title="00:00:00时间也可以设置，不过多个重复的时间只会保持一个" type="warning" />
            </el-form-item>
            <el-form-item>
              <el-alert title="按钮点击后灰色，只要悬浮上去不是禁止标识，还是可以点击的" type="warning" />
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>

  </div>
</template>

<script setup lang="ts" name="user">
import { reactive, ref } from 'vue';
import 'cropperjs/dist/cropper.css';
import {ElMessage, ElMessageBox, FormInstance, FormRules} from 'element-plus';
import {setting, getSetting, update, add} from '@/api/user';
const formRef = ref<FormInstance>();

const form = reactive({
  "phone":"",
"smsNotice":true,
  "phoneNotice":false,
  "times":['']
})

const rules: FormRules = {
  phone:[
    { required: true, message: '请输入手机号码', trigger: 'blur' },
    {
      pattern:/^[1][3,4,5,7,8,9][0-9]{9}$/,
      message: "请输入正确的手机号",
      trigger: "blur",
    },
  ]
};

const smsNotice = ref(true);

let phoneNotice = ref(false);

getData();

function getData(){
  getSetting().then(res=>{
    form.phoneNotice = res.data.phoneNotice;

    form.phone = res.data.phone;
    form.times = res.data.times;
  })
}


const num = ref(1);
let num2 = ref(1);
const handleChange = (value: number) => {
  console.log(value)
  times.value.push('');
}
const phone = ref('');

const addTime = (evt) => {
  if(form.times.length<8){
    form.times.push('');
  }else{
    ElMessage.error('最多8个提示')
  }
  let target = evt.target
  if (target.nodeName === 'I') {
    target = evt.target.parentNode
  }
  target.blur()
}
const delTime = (evt,index_:number) => {
  console.log(form.times.length)
  if(form.times.length>1){

    if(isNaN(index_)||index_>form.times.length){return false;}
    form.times.splice(index_,1);
  }else{
    ElMessage.error('最少1个提示')
  }
  let target = evt.target
  if (target.nodeName === 'I') {
    target = evt.target.parentNode
  }
  target.blur()
}
const value2 = ref()
const name = localStorage.getItem('ms_username');
const times = ref(['']);
const onSubmit = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate(valid => {

    if (!valid) return false;

    setting( reactive({
      "phone": form.phone,
      "times": form.times,
      "smsNotice": form.smsNotice,
      "phoneNotice": form.phoneNotice
    })).then(res => {
      getData();

      ElMessage.success('刷新成功')
    });
  })
};


</script>

<style scoped>
.info {
  text-align: center;
  padding: 35px 0;
}
.info-image {
  position: relative;
  margin: auto;
  width: 100px;
  height: 100px;
  background: #f8f8f8;
  border: 1px solid #eee;
  border-radius: 50px;
  overflow: hidden;
}

.info-edit {
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  opacity: 0;
  transition: opacity 0.3s ease;
}
.info-edit i {
  color: #eee;
  font-size: 25px;
}
.info-image:hover .info-edit {
  opacity: 1;
}
.info-name {
  margin: 15px 0 10px;
  font-size: 24px;
  font-weight: 500;
  color: #262626;
}
.crop-demo-btn {
  position: relative;
}
.crop-input {
  position: absolute;
  width: 100px;
  height: 40px;
  left: 0;
  top: 0;
  opacity: 0;
  cursor: pointer;
}
</style>
