<template>
	<el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
		<el-form-item label="用户名" prop="username">
			<el-input v-model="form.username"></el-input>
		</el-form-item>
		<el-form-item label="密码" prop="password" >
			<el-input v-model="form.password" placeholder="修改时不输入密码代表不修改密码，新增时需要输入密码"></el-input>
		</el-form-item>
		<el-form-item>
			<el-button type="primary" @click="saveEdit(formRef)">保 存</el-button>
		</el-form-item>
	</el-form>
</template>

<script lang="ts" setup>
import { ElMessage, FormInstance, FormRules } from 'element-plus';
import { ref } from 'vue';
import {add,update} from '@/api/user';
const props = defineProps({
	data: {
		type: Object,
		required: true
	},
	edit: {
		type: Boolean,
		required: false
	},
	update: {
		type: Function,
		required: true
	}
});

const defaultData = {
	id: '',
  username: '',
  password: ''
};

const form = ref({ ...(props.edit ? props.data : defaultData) });

const rules: FormRules = {
	username: [{ required: true, message: '用户名', trigger: 'blur' }]
};

async function u(d) {

  await update(d).then(function (response) {ElMessage.success('保存成功！');}).catch(function (error) {
    return;
  })

}

async function a(d) {

  await add(d).then(function (response) {ElMessage.success('保存成功！');}).catch(function (error) {
    return ;
  })
}
const formRef = ref<FormInstance>();
const saveEdit = (formEl: FormInstance | undefined) => {
	if (!formEl) return;
	formEl.validate(valid => {
		if (!valid) return false;
    if(props.edit){
      update(form.value).then(function (response)
          {
            props.update(form.value);
            ElMessage.success('保存成功！');
          }).catch(function (error) {})

    }else{
      add(form.value).then(function (response) {
        props.update(form.value);
        ElMessage.success('保存成功！');
      }).catch(function (error) {})
    }
	});
};



</script>

<style>

</style>
