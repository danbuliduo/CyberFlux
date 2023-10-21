<template>
  <div class="view">
    <header class="view-header">
      <div class="view-header-item left">
        <banner />
      </div>
      <div class="view-header-item center">

      </div>
      <div class="view-header-item right">
        <n-space>
          <n-dropdown
            trigger="hover"
            :show-arrow="true"
            :options="langOptions"
            @select="langSwitchEvent"
          >
            <n-button quaternary>
              <template #icon>
                <n-icon><language/></n-icon>
              </template>
              {{ $t('label') }}
            </n-button>
          </n-dropdown>
        </n-space>
      </div>
    </header>

    <div class="view-login">
      <div class="view-login-container">
        <n-card :bordered="false" class="view-login-card" size="huge">
          <banner style="transform: scale(1.6)"/>
          <div class="view-login-slogan" style="margin-top: 12px;">
            连接不止是连接
          </div>
          <n-form ref="loginFromRef" :model="reactor">
            <n-form-item-row>
              <n-input
                ref="nameInputRef"
                v-model:value="reactor.username"
                @keyup.enter="pawdInputFocusEvent"
                placeholder="请输入用户名"
              >
                <template #prefix>
                  <n-icon :size=18><person-outline/></n-icon>
                </template>
              </n-input>
            </n-form-item-row>

            <n-form-item-row>
              <n-input
                ref = "pawdInputRef"
                v-model:value="reactor.password"
                @keyup.enter="loginEvent"
                type="password"
                show-password-on="mousedown"
                placeholder="请输入密码"
              >
                <template #prefix>
                  <n-icon :size=18><lock-closed-outline/></n-icon>
                </template>
              </n-input>
            </n-form-item-row>

            <n-form-item :show-label="false">
              <n-checkbox>自动登录</n-checkbox>
              <a href="" style="margin-left: auto;">忘记密码</a>
            </n-form-item>

            <n-button class="flux-colorful-btn"
              @click="loginEvent"
              block
              secondary
              strong
              type="primary"
              :loading="loading"
            >
              登录
            </n-button>
          </n-form>
        </n-card>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { useRoute, useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import {
  NButton,
  NCard,
  NCheckbox,
  NDropdown,
  NForm,
  NFormItem,
  NFormItemRow,
  NInput,
  NIcon,
  NSpace,
  useMessage,
} from 'naive-ui'

import {
  PersonOutline,
  LockClosedOutline,
  Language
} from '@vicons/ionicons5'

import { Banner } from '@/components/common'
import { storage } from '@/utils'
import { langOptions } from '@/langs'
import { RouterName }  from '@/enums/router'
import { ResponseCode } from '@/enums/restful'
import { AccountStore } from '@/store'
import {I18N_LANG_KEY, WEB_TOKEN_KEY} from '@/constant'
import { Lang } from '@/enums/lang'

onMounted(async () => {
  console.log(route.name)
    const response = await AccountStore.reverseauth();
    if(response.code === ResponseCode.ACCE) {
        const toPath = decodeURIComponent((route.query?.redirect as string || '/'))
        await router.replace(toPath)
    }
})

onBeforeUnmount(() => {

})

const route = useRoute()
const router = useRouter()
const message = useMessage()
const { locale, t } = useI18n()
const loginFromRef = ref()
const nameInputRef = ref()
const pawdInputRef = ref()
const loading = ref(false)

const reactor = reactive({
  username: '',
  password: '',
})


function pawdInputFocusEvent(event: Event) {
  pawdInputRef.value.focus()
}

function langSwitchEvent(key: Lang): void {
  locale.value = key
  storage.set(I18N_LANG_KEY, key)
  message.success(t('action'))
}


function loginEvent(event: Event): void {
  event.preventDefault()
  loginFromRef.value.validate(async (error: any) => {
    loading.value = true
    try {
      const { username, password } = reactor
      const account = { username: username, password: password }
      const { code } = await AccountStore.login(account)
      if (ResponseCode.ACCE === code) {
        message.success(`欢迎登陆: ${username}`)
        const toPath = decodeURIComponent((route.query?.redirect || '/') as string)
        route.name === RouterName.LOGIN ? await router.replace('/') : await router.replace(toPath)
      } else {
        message.error('登陆失败')
      }
    } finally {
      loading.value = false
    }
  })
}




</script>

<style lang="scss" scoped>
.view {
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: auto;
  background: #101014;
  &-header {
    z-index: 999;
    width: 100%;
    height: $header-height;
    display: flex;
    align-items: center;
    justify-content: space-between;
    &-item.left {
      width: auto;
      margin-left: 16px;
    }
    &-item.right {
      width: auto;
      margin-right: 16px;
    }
  }
  &-login {
    display: flex;
    flex-direction: column;
    height: auto;
    overflow: auto;
    &-container {
      flex: 1;
      align-items: center;
      padding: 96px 12px;
      min-width: 348px;
      margin: auto;
    }
    &-slogan {
      text-align: center;
      font-weight: 800;
      letter-spacing: 12px;
      color: #afafaf;
    }
    &-card {
      background: rgba(0, 0, 0, 0);
    }
  }
}

@media (min-width: 768px) {
  .view{
    background-image: url('@/assets/image/background/login.svg');
    background-repeat: no-repeat;
    background-position: 50%;
    background-size: 100%;
    &-header {
      z-index: 999;
      width: 100%;
      height: 72px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      &-item.left {
        margin-left: 32px;
      }
      &-item.right {
        margin-right: 32px;
      }
    }
    &-login {
      display: flex;
      flex-direction: column;
      height: auto;
      overflow: auto;
      &-container {
        flex: 1;
        padding: 96px 12px;
        min-width: 420px;
        margin: auto;
      }
    }
  }
}
</style>
