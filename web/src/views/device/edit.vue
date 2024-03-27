<template>
	<el-form ref="formRef" :model="form" :rules="rules" label-width="150px">
		<el-form-item label="卡号" prop="deviceId">
			<el-input v-model="form.deviceId"></el-input>
		</el-form-item>
		<el-form-item label="主要负责人电话" prop="phone">
			<el-input v-model="form.phone"></el-input>
		</el-form-item>
    <el-form-item label="次要负责人电话" prop="phone2">
      <el-input v-model="form.phone2"></el-input>
    </el-form-item>
    <el-form-item label="备用负责人电话1" prop="phone3">
      <el-input v-model="form.phone3"></el-input>
    </el-form-item>

    <el-form-item label="备用负责人电话2" prop="phone4">
      <el-input v-model="form.phone4"></el-input>
    </el-form-item>
    <el-form-item label="备用负责人电话3" prop="phone5">
      <el-input v-model="form.phone5"></el-input>
    </el-form-item>
    <el-form-item label="备用负责人电话4" prop="phone6">
      <el-input v-model="form.phone6"></el-input>
    </el-form-item>
    <el-form-item label="备用负责人电话5" prop="phone7">
      <el-input v-model="form.phone7"></el-input>
    </el-form-item>
    <el-form-item label="备用负责人电话6" prop="phone8">
      <el-input v-model="form.phone8"></el-input>
    </el-form-item>
    <el-form-item label="备用负责人电话7" prop="phone9">
      <el-input v-model="form.phone9"></el-input>
    </el-form-item>
    <el-form-item label="备用负责人电话8" prop="phone10">
      <el-input v-model="form.phone10"></el-input>
    </el-form-item>

		<el-form-item label="设备备注" prop="remark">
			<el-input v-model="form.remark"></el-input>
		</el-form-item>
    <el-form-item label="短信通知" prop="smsNotice">
      <el-switch v-model="form.smsNotice" disabled/>
    </el-form-item>
    <el-form-item label="电话通知" prop="phoneNotice">
      <el-switch v-model="form.phoneNotice" />
    </el-form-item>
		<el-form-item>
			<el-button type="primary" @click="saveEdit(formRef)">保 存</el-button>
		</el-form-item>
	</el-form>
</template>

<script lang="ts" setup>
import { ElMessage, FormInstance, FormRules, UploadProps } from 'element-plus';
import { ref } from 'vue';
import {add,update} from '@/api/device';
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
	deviceId: '',
  phone: '',
  phone2: '',
  phone3: '',

  phone4: '',
  phone5: '',
  phone6: '',
  phone7: '',
  phone8: '',
  phone9: '',
  phone10: '',
	remark: '',
	smsNotice: true,
	phoneNotice: false
};

const form = ref({ ...(props.edit ? props.data : defaultData) });

const rules: FormRules = {
	deviceId: [{ required: true, message: '设备ID', trigger: 'blur' }],
  phone: [{ required: true, message: '手机号', trigger: 'blur' },
    {
      pattern:/^[1][3,4,5,7,8,9][0-9]{9}$/,
      message: "请输入正确的手机号",
      trigger: "blur",
    }],
 /* phone2: [{ required: true, message: '手机号', trigger: 'blur' },
    {
      pattern:/^[1][3,4,5,7,8,9][0-9]{9}$/,
      message: "请输入正确的手机号",
      trigger: "blur",
    }],
  phone3: [{ required: true, message: '手机号', trigger: 'blur' },
    {
      pattern:/^[1][3,4,5,7,8,9][0-9]{9}$/,
      message: "请输入正确的手机号",
      trigger: "blur",
    }],

  phone4: [{ required: true, message: '手机号', trigger: 'blur' },
    {
      pattern:/^[1][3,4,5,7,8,9][0-9]{9}$/,
      message: "请输入正确的手机号",
      trigger: "blur",
    }],
  phone5: [{ required: true, message: '手机号', trigger: 'blur' },
    {
      pattern:/^[1][3,4,5,7,8,9][0-9]{9}$/,
      message: "请输入正确的手机号",
      trigger: "blur",
    }],
  phone6: [{ required: true, message: '手机号', trigger: 'blur' },
    {
      pattern:/^[1][3,4,5,7,8,9][0-9]{9}$/,
      message: "请输入正确的手机号",
      trigger: "blur",
    }],
  phone7: [{ required: true, message: '手机号', trigger: 'blur' },
    {
      pattern:/^[1][3,4,5,7,8,9][0-9]{9}$/,
      message: "请输入正确的手机号",
      trigger: "blur",
    }],
  phone8: [{ required: true, message: '手机号', trigger: 'blur' },
    {
      pattern:/^[1][3,4,5,7,8,9][0-9]{9}$/,
      message: "请输入正确的手机号",
      trigger: "blur",
    }],
  phone9: [{ required: true, message: '手机号', trigger: 'blur' },
    {
      pattern:/^[1][3,4,5,7,8,9][0-9]{9}$/,
      message: "请输入正确的手机号",
      trigger: "blur",
    }],
  phone10: [{ required: true, message: '手机号', trigger: 'blur' },
    {
      pattern:/^[1][3,4,5,7,8,9][0-9]{9}$/,
      message: "请输入正确的手机号",
      trigger: "blur",
    }],*/
  remark: [{ required: true, message: '备注', trigger: 'blur' }],

};

async function u(d) {

  await update(d)
}

async function a(d) {

  await add(d)
}
const formRef = ref<FormInstance>();
const saveEdit = (formEl: FormInstance | undefined) => {
	if (!formEl) return;
	formEl.validate(valid => {
		if (!valid) return false;

		if(props.edit){
      props.update(u(form.value).then(res=>{
        ElMessage.success('保存成功！');
      }));
    }else{
      props.update(a(form.value).then(res=>{
        ElMessage.success('保存成功！');
      }));
    }




	});
};

const handleAvatarSuccess: UploadProps['onSuccess'] = (response, uploadFile) => {
	form.value.thumb = URL.createObjectURL(uploadFile.raw!);
};


</script>

<style>
.avatar-uploader .el-upload {
	border: 1px dashed var(--el-border-color);
	border-radius: 6px;
	cursor: pointer;
	position: relative;
	overflow: hidden;
	transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
	border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
	font-size: 28px;
	color: #8c939d;
	width: 178px;
	height: 178px;
	text-align: center;
}
</style>
