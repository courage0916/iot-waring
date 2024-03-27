<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">告警平台</div>
      <el-form ref="loginRef" :model="param" :rules="rules" class="ms-content" label-width="0px">
        <el-form-item prop="username">
          <el-input v-model="param.username" placeholder="用户名">
<!--            <template #prepend>
              <el-button :icon="User"></el-button>
            </template>-->
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              v-model="param.password"
              placeholder="密码"
              type="password"
              @keyup.enter="submitForm(loginRef)"
          >
<!--            <template #prepend>
              <el-button :icon="Lock"></el-button>
            </template>-->
          </el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm(loginRef)">登录</el-button>
        </div>
      </el-form>

    </div>
  </div>
</template>

<script lang="ts" setup>
import {reactive, ref} from 'vue';
import {useRouter} from 'vue-router';
import type {FormInstance, FormRules} from 'element-plus';
import {ElMessage} from 'element-plus';
import {login,getUser} from "@/api/user/index";
import {useTagsStore} from "@/store/tags";

interface LoginInfo {
  username: string;
  password: string;
}

const router = useRouter();
const param = reactive<LoginInfo>({
  username: '',
  password: ''
});

const rules: FormRules = {
  username: [
    {
      required: true,
      message: '请输入用户名',
      trigger: 'blur'
    }
  ],
  password: [{required: true, message: '请输入密码', trigger: 'blur'}]
};

const loginRef = ref<FormInstance>();
const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate((valid: boolean) => {
    if (valid) {
      login(param.username, param.password).then(res => {
        localStorage.setItem("token",res.data);
        getUser().then(res=>{
          ElMessage.success('登录成功');
          localStorage.setItem('ms_user', JSON.stringify(res.data));
          if(res.data.username == "admin"){
            router.push('/user');
          }else{
            router.push('/device');
          }

        })
      })
    } else {
      ElMessage.error('登录失败');
      return false;
    }
  });
};


</script>

<style scoped>
.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background-image: url(/src/assets/img/login-bg.jpg);
  background-size: 100%;
}

.ms-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  color: #fff;
  border-bottom: 1px solid #ddd;
}

.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 350px;
  margin: -190px 0 0 -175px;
  border-radius: 5px;
  background: rgba(255, 255, 255, 0.3);
  overflow: hidden;
}

.ms-content {
  padding: 30px 30px;
}

.login-btn {
  text-align: center;
}

.login-btn button {
  width: 100%;
  height: 36px;
  margin-bottom: 10px;
}

.login-tips {
  font-size: 12px;
  line-height: 30px;
  color: #fff;
}
</style>
