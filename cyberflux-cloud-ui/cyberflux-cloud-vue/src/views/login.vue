<template>
  <div class="view-background" ref="viewBackgroundRef">
    <header class="view-header">
        <div class="view-header-item left">
          <n-space style="margin-left: 32px;">
            <banner />
          </n-space>
        </div>
        <div class="view-header-item center">

        </div>
      <div class="view-header-item right">
        <n-space>
          <n-dropdown trigger="hover"
            :show-arrow="true"
            :options="langOptions"
            @select="handleSelect"
          >
            <n-button quaternary  style="width: 120px; margin-right: 32px;">
              <template #icon>
                <n-icon>
                  <language/>
                </n-icon>
              </template>
              {{ $t('label') }}
            </n-button>
          </n-dropdown>
        </n-space>
      </div>
    </header>

    <div class="view-login">
      <div class="view-login-banner">
        <div class="view-login-banner-title">CYBERFLUX</div>
        <div class="view-login-banner-slogan">{{ $t('slogan') }}.</div>
      </div>

      <div class="view-login-container">
        <n-card class="view-login-card" size="huge">
          <banner/>
          <n-tabs default-value="signin" size="large" justify-content="space-evenly">

            <n-tab-pane name="signin" tab="登录" >
              <n-form ref="loginFromRef">
                <n-form-item-row label="用户名">
                  <n-input placeholder="请输入邮箱或账户名">
                    <template #prefix>
                      <n-icon size="18">
                        <person-outline/>
                      </n-icon>
                    </template>
                  </n-input>
                </n-form-item-row>

                <n-form-item-row label="密码">
                  <n-input type="password" show-password-on="mousedown" placeholder="请输入密码"
                    @keyup.enter="handleLogin"
                  >
                    <template #prefix>
                      <n-icon size="18">
                        <lock-closed-outline/>
                      </n-icon>
                    </template>
                  </n-input>
                </n-form-item-row>

                <n-form-item :show-label="false">
                  <n-checkbox>自动登录</n-checkbox>
                  <a href="" style="margin-left: auto;">忘记密码</a>
                </n-form-item>

                <n-button block secondary strong type="primary" @click="handleLogin">
                  登录
                </n-button>

                <n-form-item-row>
                  <n-space>
                    <div>其它登录方式</div>
                    <n-icon size="24">
                      <logo-wechat />
                    </n-icon>
                    <n-icon size="24">
                      <logo-github />
                    </n-icon>
                    <n-icon size="24">
                      <logo-facebook />
                    </n-icon>
                    <n-icon size="24">
                      <logo-linkedin />
                    </n-icon>
                  </n-space>
                </n-form-item-row>
              </n-form>
            </n-tab-pane>

            <n-tab-pane name="signup" tab="注册">
              <n-form>
                <n-form-item-row label="邮箱地址">
                  <n-input placeholder="请输入邮箱地址"/>
                </n-form-item-row>
                <n-form-item-row label="用户名">
                  <n-input placeholder="请输入用户名"/>
                </n-form-item-row>
                <n-form-item-row label="密码">
                  <n-input type="password" placeholder="设置你的密码"/>
                </n-form-item-row>
                <n-form-item-row label="重复密码">
                  <n-input type="password" placeholder="确认你的密码"/>
                </n-form-item-row>
              </n-form>
              <n-button block secondary strong type="primary">
                注册
              </n-button>
            </n-tab-pane>
          </n-tabs>
        </n-card>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import * as THREE from 'three';
import VANTA_NET from 'vanta/src/vanta.net';
import { Router, useRouter } from 'vue-router';
import {
  NButton, NCard, NCheckbox,
  NDropdown,
  NForm, NFormItem, NFormItemRow,
  NInput,
  NTabPane, NTabs,
  NIcon,
  NSpace
} from 'naive-ui';

import {
  PersonOutline,
  LockClosedOutline,
  LogoFacebook,
  LogoGithub,
  LogoLinkedin,
  LogoWechat,
  Language
} from '@vicons/ionicons5';

import { Banner } from '@/components/common';
import { storage } from '@/utils'
import { ACCESS_TOKEN_KEY, I18N_LANG_KEY, Lable, Lang } from '@/enums'
import { useI18n } from "vue-i18n";

const viewBackgroundRef = ref()
const loginFromRef = ref()
const router: Router = useRouter()

let effect: any

function handleLogin(event: Event) {
  event.preventDefault()
  loginFromRef.value.validate(async (errors: any) => {
    if (!errors) {
      storage.set(ACCESS_TOKEN_KEY, 'true')
      window['$message'].success('登陆成功')
      router.push('application/agriculture')
    } else {
      window['$message'].error('登陆失败')
    }
  })
}
const langOptions = [
  {
    label: Lable.EN_US,
    key: Lang.EN_US,
  },
  {
    label: Lable.JA_JP,
    key: Lang.JA_JP,
  },
  {
    label: Lable.RU_RU,
    key: Lang.RU_RU,
  },
  {
    label: Lable.ZH_CN,
    key: Lang.ZH_CN,
  },
  {
    label: Lable.ZH_TC,
    key: Lang.ZH_TC,
  },
]

let { locale, t } = useI18n()


function handleSelect(key: Lang) {
  locale.value = key
  storage.set(I18N_LANG_KEY, key)
  window.$message.success(t('action'))
}

onMounted(() => {

  effect = VANTA_NET({
    el: viewBackgroundRef.value,
    THREE: THREE,
    mouseControls: true,
    touchControls: true,
    gyroControls: false,
    minHeight: 200.00,
    minWidth: 200.00,
    scale: 1.00,
    scaleMobile: 1.00,
    color: 0xfc9f,
    backgroundColor: 0x0000,
    backgroundAlpha: 1.0,
    points: 12.00,
    maxDistance: 18.00,
    spacing: 12.00,
    showDots: true
  })

})

onBeforeUnmount(() => {
  if(effect) {
    effect.destroy()
  }
})
</script>

<style lang="scss" scoped>
.view-background {
  width: 100%;
  height: 100vh;
  overflow: hidden;
  padding: 0;
}
.view-header {
  background: rgba(127, 127, 127, 0.1);
  box-shadow: 0 0 20px 5px rgba(127, 127, 127, 0.1);
  backdrop-filter: blur(8px);
  -webkit-backdrop-filter: blur(8px);
  z-index: 999;
  width: 100%;
  height: 72px;
  display: grid;
  grid-template-columns: repeat(3, 33.3%);
  &-item {
      display: flex;
      align-items: center;
      min-width: 512px;
      &.left {
        justify-content: start;
      }
      &.center {
        justify-content: center;
      }
      &.right {
        justify-content: end;
      }
  }
}
.view-login {
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: auto;
  &-banner {
    position: fixed;
    top: 32%;
    left: 10%;
    &-title {
      font-size: 64px;
      font-weight: bolder;
      font-style: italic;
      color: lightgray;
    }
    &-slogan {
      width: 512px;
      font-size: 28px;
      font-weight: bolder;
      letter-spacing: 0em;
    }
  }
  &-container {
    flex: 1;
    max-width: 420px;
    min-width: 360px;
    margin: 3% 0 0 60%;
  }
  &-card {
    background: rgba(48, 48, 48, 0.4);
    box-shadow: 0 0 20px 5px rgba(48, 48, 48, 0.4);
    backdrop-filter: blur(8px);
    -webkit-backdrop-filter: blur(8px);
  }
  &-other {
    width: 100%;
  }
}
</style>
