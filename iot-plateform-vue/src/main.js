import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import installElementPlus from './plugins/element'
import './assets/css/icon.css'
import './styles/common.css'
import './assets/css/font.css'
import moment from 'moment'
const app = createApp(App)

app.config.globalProperties.$moment = moment

installElementPlus(app)
app
    .use(store)
    .use(router)
    .mount('#app')
