import type { App } from 'vue';
import * as Pinia from 'pinia';
import { useAccountStore } from '@/store/modules/account';
import { useChannelStore } from '@/store/modules/channel';
import { useEngineStore } from '@/store/modules/engine';
import { useSubscriptionStore } from '@/store/modules/subscription';

import.meta.glob('./modules/**/*.ts', { eager: true });

const store = Pinia.createPinia();

export const AccountStore = useAccountStore(store);
export const ChannelStore = useChannelStore(store);
export const EngineStore = useEngineStore(store);
export const SubscriptionStore = useSubscriptionStore(store);

export function setupStore(app: App) {
  app.use(store);
};

export default store;
