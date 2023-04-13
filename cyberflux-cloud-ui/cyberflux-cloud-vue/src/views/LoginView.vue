<template>

  <n-button>naive-ui</n-button>
  <button>
    {{ $t('common.home') }}
  </button>
   <button @click="selectLang('ja-JP')">
    {{ $t('action') }}
  </button>
  <button @click="selectLang('ru-RU')">
    {{ $t('action') }}
  </button>
  <button @click="selectLang('zh-CN')">
    {{ $t('action') }}
  </button>
  <button @click="selectLang('zh-TC')">
    {{ $t('action') }}
  </button>
</template>

<script lang="ts">
import {
  computed,
  defineComponent,
  ref
} from 'vue'
import {
  NButton,
  createDiscreteApi,
  ConfigProviderProps,
  darkTheme,
  lightTheme
} from 'naive-ui'

const themeRef = ref<'light' | 'dark'>('dark')

const configProviderPropsRef = computed<ConfigProviderProps>(() => ({
  theme: themeRef.value === 'light' ? lightTheme : darkTheme
}))

const { message } = createDiscreteApi(
  [
    'message'
  ],
  {
    configProviderProps: configProviderPropsRef
  }
)
import { Field } from '@/enums/lang.enum'
import { I18N_LANG_KEY } from '@/enums/storage.enum'


export default defineComponent({
  name: 'LoginView',
  components: {
    NButton
  },
  setup() {
    return {
      message,
      selectLang(value: string): void {
        this.$i18n.locale = value
        localStorage.setItem(I18N_LANG_KEY, value)
        message.success(this.$t(Field.ACTION))
      }
    }
  }
})

</script>
