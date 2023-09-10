<template>
  <n-space>
    <n-button @click="createMessage">
      打开
    </n-button>
    <n-button @click="removeMessage">
      关闭
    </n-button>
  </n-space>
</template>

<script setup lang="ts">
import { defineComponent, onBeforeUnmount } from 'vue'
import { useMessage } from 'naive-ui'
import { useAccountStore } from '@/store/modules/account'
const account = useAccountStore()
import type { MessageReactive } from 'naive-ui'
import { login, queryAll } from '@/service/api/account.api'
import axios  from 'axios'
const message = useMessage()
let messageReactive: MessageReactive | null = null
import qs from 'qs'
const removeMessage = () => {
  //message.destroyAll()
  if (messageReactive) {
    messageReactive.destroy()
    messageReactive = null
  }
}

async function createMessage() {

  /*const post = axios.create({
    baseURL: '/api',
    method: 'POST',
    auth: {
      username: 'xxx',
      password: 'xxx'
    }
  })
  post.post("/api/login").then(r=>console.log(r.data))*/

  const response =  await queryAll({token: "sdfzsfs" }) //await login({username: 'admin', password: 'cyberflux'});
  const { code}  = response
  console.log(response, account.getWebToken)

}
</script>

