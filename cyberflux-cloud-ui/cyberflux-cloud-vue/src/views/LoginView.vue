<template>
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
import { LangKey } from '@/lang'

export default {
  name: 'LoginView',
  setup() {

    return {

    }
  },
  methods: {
    selectLang(value : string) : void {
      this.$i18n.locale = value
      localStorage.setItem(LangKey, value)
      alert(this.$t('action'))
    }
  },
  mounted() {

  },
}

</script>