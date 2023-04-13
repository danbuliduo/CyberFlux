import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import i18n from './lang'

async function run() {
  const app = createApp(App);
  app.use(store)
  app.use(router)
  app.use(i18n)
  app.mount('#app', true)
}

run().then(() => {
  console.log(process.env.NODE_ENV)
})
