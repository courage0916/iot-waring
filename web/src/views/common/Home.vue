<template>
  <div class="home">
    <v-header />
    <v-sidebar />
    <div class="content-box" :class="{ 'content-collapse': sidebar.collapse }">
      <v-tags></v-tags>
      <div class="content">
        <v-breadcrumb></v-breadcrumb>
        <router-view v-slot="{ Component }">
          <transition name="move" mode="out-in">
            <keep-alive :include="tags.nameList">
              <component :is="Component"></component>
            </keep-alive>
          </transition>
        </router-view>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import "element-plus/theme-chalk/el-button.css"
import "element-plus/theme-chalk/base.css"
import { useSidebarStore } from '../../store/sidebar';
import { useTagsStore } from '../../store/tags';
import vHeader from '../../components/Header.vue';
import vSidebar from '../../components/Sidebar.vue';
import vTags from '../../components/Tags.vue';
import vBreadcrumb from '../../components/Breadcrumb.vue';
import {heartbeat} from '@/api/user';

const sidebar = useSidebarStore();
debugger
const tags = useTagsStore();
tags.clearTags();

init();
function init(){

  setInterval(function() {
    heartbeat();
  }, 5000);
}
</script>
