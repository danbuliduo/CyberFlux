import { createPinia } from 'pinia'

const moudels = import.meta.glob(
  './modules/**/*.ts', { eager: true }
)


const store = createPinia()

export default store
