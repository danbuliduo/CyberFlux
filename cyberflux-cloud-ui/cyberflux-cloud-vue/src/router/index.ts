import { createRouter, createWebHashHistory } from 'vue-router'
import { openProgressBar, closeProgressBar } from '@/utils/nprogress'
import HomeView from '@/views/HomeView.vue'
const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    component: () => import('@/views/AboutView.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/LoginView.vue')
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach(() => {
  openProgressBar()
})

router.afterEach(() => {
  closeProgressBar()
})

export default router
