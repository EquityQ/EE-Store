import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue';
import router from "./Vue-router/RouterConfig"
import axios from 'axios';
import Cookies from 'js-cookie';
axios.defaults.withCredentials = true;
const app = createApp(App);
app.config.globalProperties.$axios = axios;
app.config.globalProperties.$cookies = Cookies;
app.use(ElementPlus);
app.use(router);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);
}
app.mount('#app');