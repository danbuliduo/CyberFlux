import '~/tailwind.css'
import { createApp } from 'vue'
import vueapp from './Application.vue'
import router from './router'
import store from './store'

/*import {
  naiveui
} from './plugins'*/

async function run() : Promise<void> {
  const app = createApp(vueapp)
  //app.use(naiveui)
  app.use(store)
  app.use(router)
  await router.isReady()
  app.mount('#app')
}

run().then(() => {

})
