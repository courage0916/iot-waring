<template>
  <div class="sidebar">
    <el-menu
        class="el-menu-vertical-demo"
        :collapse="sidebar.collapse"
        @open="handleOpen"
        @close="handleClose"
        :default-active="onRoutes"
        active-text-color="#20a0ff"
        unique-opened
        router
    >
    <VNavLeft :navMenus="items"></VNavLeft>
    </el-menu>
  </div>
</template>

<script setup lang="ts">
import {computed, ref, reactive, defineComponent} from 'vue';
import { useSidebarStore } from '@/store/sidebar';
import { useRoute } from 'vue-router';
import VNavLeft from "@/components/NavLeft.vue";
import NavLeft from "@/components/NavLeft.vue";
defineComponent({
  name: "VNavLeft",
});

let items = ref([
  {
    path: "/device",
    icon: 'Odometer',
    name: '设备管理'
  }
]);


let user = JSON.parse(localStorage.getItem('ms_user') + "");
if(user.username === "admin"){
  items.value.push({
    path: "/user",
    icon: 'House',
    name: '账号管理'
  });
}

const route = useRoute();
const onRoutes = computed(() => {
  return route.path;
});

const sidebar = useSidebarStore();

const handleOpen = (key: string, keyPath: string[]) => {
  //console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  //console.log(key, keyPath)
}
</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 250px;
}
.sidebar {
  display: block;
  position: absolute;
  left: 0;
  top: 70px;
  bottom: 0;
  overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
  width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
  width: 250px;
}
.sidebar > ul {
  height: 100%;
}
</style>
