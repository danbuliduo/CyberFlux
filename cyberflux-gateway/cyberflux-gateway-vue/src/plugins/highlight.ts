import "highlight.js/styles/atom-one-dark.min.css";
import type { App } from "vue";
import hljs from "highlight.js/lib/core";
import javascript from "highlight.js/lib/languages/javascript";
import hljsVuePlugin from "@highlightjs/vue-plugin";

export async function setupHighLight(app: App) {
  hljs.registerLanguage("javascript", javascript);
  app.use(hljsVuePlugin);
}
