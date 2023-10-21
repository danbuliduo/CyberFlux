import "vfonts/Lato.css";
import "~/tailwind.css";
import timeago from "vue-timeago3";
import Appliaction from "./Application.vue";
import { setupHighLight } from './plugins/highlight';
import { setupHighCharts } from "./plugins/highcharts";
import { setupDiscreteApi } from "./plugins/naiveui";
import { setupI18n } from "./langs";
import { setupStore } from "./store";
import router, { setupRouter } from "./router";



const app = createApp(Appliaction);

async function run(): Promise<void> {
  console.log("env:", process.env.NODE_ENV);
  await setupHighLight(app);
  await setupHighCharts();
  await setupDiscreteApi();
  setupI18n(app);
  setupStore(app);
  setupRouter(app);
  router.isReady().then(() => {
    console.log("router ready!");
  }).finally(() => {
    app.use(timeago);
    app.mount("#app", true);
  })
}

await run();
