<template>
  <div class="breadcrumb">
    <el-breadcrumb separator="/" style="height:40px;line-height:40px;font-size:20px;">
      <el-breadcrumb-item v-for="(item, index) in $route.matched" :key="index">{{item.name}}</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router'
import { ref, watch } from 'vue'
const route = useRoute()
//装路由数据的数组
const menus = ref([])

watch(
    ()=> route.matched,
    (newV) => {
      menus.value = newV
    },
    {
      immediate: true
    }
)
</script>