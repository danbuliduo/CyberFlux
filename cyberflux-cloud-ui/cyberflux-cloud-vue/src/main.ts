import Appliaction from './Application.vue';
import timeago from 'vue-timeago3'
import { setupDiscreteApi } from './plugins';
import { setupI18n } from './langs';
import { setupStore } from './store';
import router, { setupRouter } from './router';
import { useHighCharts } from './hooks';
import 'vfonts/Lato.css'
import '~/tailwind.css';

async function run(): Promise<void> {
  const app = createApp(Appliaction)
  setupDiscreteApi()
  setupI18n(app)
  setupStore(app)
  setupRouter(app)
  await router.isReady()
  app.use(timeago)
  app.mount('#app', true)
}

run().then(() => {
  console.info("NODE-ENV:", process.env.NODE_ENV)
  useHighCharts()
}).catch(() => {
  console.info("Run Error.")
})
