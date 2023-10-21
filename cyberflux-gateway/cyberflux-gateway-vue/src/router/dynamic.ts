import { RouteRecordRaw } from "vue-router";
import { ActiveLayout } from "@/router/constant";

export function careatApplicationRoute(): RouteRecordRaw {
  return {
    path: "/application",
    name: "application",
    component: ActiveLayout,
    meta: {
      title: "Application",
      hideBreadcrumb: true,
    },
    children: [
    {
      path: "/:id",
      name: "application",
      component: () => import("@/views/dynamic/application.vue"),
      meta: {
        title: "Application",
        hideBreadcrumb: true,
      },
    },
  ],}
};
