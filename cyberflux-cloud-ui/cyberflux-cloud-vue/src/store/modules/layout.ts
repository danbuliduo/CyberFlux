import store from '@/store'
import { defineStore } from 'pinia';
import setting from '@/store/setting/layout.setting'

export interface SilderSetting {
  fixed: boolean;
  mixed: boolean;
  collapsed: boolean;
}

export interface LayoutState {
  silder: SilderSetting;
}


export const useLayoutStateStore = defineStore({
  id: 'layout',
  state: (): LayoutState => ({
    silder: setting.sider
  }),
  getters: {
    getSilder(): SilderSetting {
      return this.silder;
    }
  },
})

/**
 * @tips 需要在setup()之外使用
 */
export function useLayoutStateStoreWidthOut() {
  return useLayoutStateStore(store);
}
