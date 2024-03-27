import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import { createPinia } from 'pinia';
import './assets/css/icon.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import {permission} from "@/store/permiss"
import "babel-polyfill"

const pinia = createPinia()
const app = createApp(App)
app.use(router)
app.use(pinia)
app.use(ElementPlus)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
permission.forEach((directive) => app.directive(directive.name, directive.value))
app.mount('#app')
