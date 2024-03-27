<template>
  <template v-for="item in props.navMenus">
    <template v-if="item.children">
      <el-sub-menu :index="item.path" :key="item.path">
        <template #title>
          <el-icon>
            <component :is="item.icon"></component>
          </el-icon>
          <span>{{item.name }}</span>
        </template>
        <v-nav-left :navMenus="item.children"></v-nav-left>
      </el-sub-menu>
    </template>
    <template v-else>
      <el-menu-item :index="item.path" :key="item.path">
        <el-icon>
          <component :is="item.icon"></component>
        </el-icon>
        <template #title>{{item.name }}</template>
      </el-menu-item>
    </template>
  </template>
</template>

<script lang="ts">
export default {
  name: "VNavLeft"
};
</script>

<script setup lang="ts" name="VNavLeft">

const props = defineProps(['navMenus']);
</script>
