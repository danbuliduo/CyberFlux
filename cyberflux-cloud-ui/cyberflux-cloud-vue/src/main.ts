import Appliaction from './Application.vue';
import { setupDiscreteApi } from './plugins';
import { setupI18n } from './langs';
import { setupStore } from './store';
import router, { setupRouter } from './router';
import { useHighCharts } from './hooks';
import '~/tailwind.css';

async function run(): Promise<void> {
  const app = createApp(Appliaction)
  setupDiscreteApi()
  setupI18n(app)
  setupStore(app)
  setupRouter(app)
  await router.isReady()
  app.mount('#app', true)
}

run().then(() => {
  console.info("NODE-ENV:", process.env.NODE_ENV)
  useHighCharts()
}).catch(() => {
  console.info("CATCH")
})
