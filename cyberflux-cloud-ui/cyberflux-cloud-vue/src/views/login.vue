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
          <banner style="transform: scale(1.4)"/>
          <div style="text-align: center; color: #808080; margin-top: 8px;">
            问余何意栖碧山，笑而不答心自闲。
          </div>
          <n-form :ref="loginFromRef" :model="reactor">
            <n-form-item-row>
              <n-input
                v-model:value="reactor.username"
                @keyup.enter="paswdInpudFocusEvent"
                placeholder="请输入用户名"
              >
                <template #prefix>
                  <n-icon :size=18><person-outline/></n-icon>
                </template>
              </n-input>
            </n-form-item-row>

            <n-form-item-row>
              <n-input
                :ref = "pawdInputRef"
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

            <n-button
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
import { RouterName, ServiceCode}  from '@/enums'
import { useAccountStore } from '@/store/modules/account'
import { I18N_LANG_KEY, Lang } from '@/enums'

const accountStore = useAccountStore()
const route = useRoute()
const router = useRouter()
const message = useMessage()
const { locale, t } = useI18n()
const loginFromRef = ref()
const pawdInputRef = ref()
const loading = ref(false)

const reactor = reactive({
  username: undefined,
  password: undefined,
})


function paswdInpudFocusEvent(event: Event) {
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
      const { code } = await accountStore.login(account)
      if (ServiceCode.SUCCEED === code) {
        message.success(`欢迎登陆: ${username}`)
        const toPath = decodeURIComponent((route.query?.redirect || '/') as string)
        route.name === RouterName.LOGIN ? router.replace('/') : router.replace(toPath)
      } else {
        message.error('登陆失败')
      }
    } finally {
      loading.value = false
    }
  })
}


//组件被挂载之前被调用。它在组件已经完成了其响应式状态的设置，但还没有创建 DOM 节点之前触发。
onBeforeMount(() => {

})
//组件挂载完成后执行。此时DOM节点已创建完成。通常用于执行需要访问组件所渲染
// DOM 树相关的副作用，或是在服务端渲染应用中用于确保 DOM 相关代码
onMounted(() => {
})
// @说明：组件被卸载之前被调用。
onBeforeUnmount(() => {

})
// 组件卸载之后调用。通常用于做一下清理工作。
onUnmounted(() => {

})

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
      padding: 96px 12px;
      min-width: 348px;
      margin: auto;
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
~/src/store/modules/account
