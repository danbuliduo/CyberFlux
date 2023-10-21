<template>
  <div>
    <n-space justify="end">
      <n-button class="flux-colorful-btn"
        @click="showInfoModalEvent">
          编辑信息
        <template #icon>
          <n-icon>
            <person-circle-outline />
          </n-icon>
        </template>
      </n-button>
      <n-button class="flux-colorful-btn"
        @click="showPawdModalEvent">
          修改密码
        <template #icon>
          <n-icon>
            <finger-print-outline />
          </n-icon>
        </template>
      </n-button>
      <n-button class="flux-colorful-btn"
        @click="showCreateDrawerEvent">
        创建用户
        <template #icon>
          <n-icon>
            <add-outline />
          </n-icon>
        </template>
      </n-button>
    </n-space>
    <n-data-table style="margin-top: 12px;"
      :columns="tableColumns"
      :data="tableData"
      :pagination="false"
    />
    <n-modal v-model:show="showInfoModal" preset="dialog" title="编辑信息">
      <n-form style="margin-top: 24px;">
        <n-form-item-row label="用户名">
          <n-input/>
        </n-form-item-row>
        <n-form-item-row label="备注">
          <n-input/>
        </n-form-item-row>
      </n-form>
      <template #action>
        <n-button type="success" secondary strong>
          提交
        </n-button>
      </template>
    </n-modal>

    <n-modal v-model:show="showPawdModal" preset="dialog" title="修改密码">
      <n-form style="margin-top: 24px;">
        <n-form-item-row label="旧密码">
          <n-input/>
        </n-form-item-row>
        <n-form-item-row label="新密码">
          <n-input/>
        </n-form-item-row>
        <n-form-item-row label="确认密码">
          <n-input/>
        </n-form-item-row>
      </n-form>
      <template #action>
        <n-button type="success" secondary strong>
          提交
        </n-button>
      </template>
    </n-modal>

    <n-modal v-model:show="showEditModal" preset="dialog" title="编辑用户">
      <n-form :model="accountModel" style="margin-top: 24px;">
        <n-form-item-row  label="用户名">
          <n-input :value="accountModel.username" disabled />
        </n-form-item-row>
        <n-form-item-row label="备注">
          <n-input v-model:value="accountModel.remarks"/>
        </n-form-item-row>
      </n-form>
      <template #action>
        <n-button type="success" secondary strong>
          提交
        </n-button>
      </template>
    </n-modal>

    <n-drawer v-model:show="showCreateDrawer" :width="380">
      <n-drawer-content closable>
        <template #header>
          创建新用户
        </template>
        <n-form :model="accountModel">
          <n-form-item-row label="用户名">
            <n-input v-model:value="accountModel.username"/>
          </n-form-item-row>
          <n-form-item-row label="密码">
            <n-input v-model:value="accountModel.password"/>
          </n-form-item-row>
          <n-form-item-row label="备注">
            <n-input v-model:value="accountModel.remarks" />
          </n-form-item-row>
          <n-form-item-row label="授权">
            <n-transfer ref="transfer"
              virtual-scroll
              v-model:value="accountModel.permissions"
              :options="PermissionOptions" />
          </n-form-item-row>
        </n-form>
        <template #footer>
          <n-button @click="postAccountEvent"
            class="flux-colorful-btn" block secondary strong>
            注册
          </n-button>
        </template>
      </n-drawer-content>
    </n-drawer>
  </div>
</template>

<script setup lang="ts">
import type { DataTableColumns } from 'naive-ui';
import {
  NDataTable,
  NButton,
  NSpace,
  NIcon,
  NDrawer,
  NModal,
  NInput,
  NForm,
  NFormItemRow,
  NDrawerContent,
  NTransfer
} from 'naive-ui';
import { AddOutline, PersonCircleOutline ,FingerPrintOutline } from '@vicons/ionicons5'
import  * as AccountAPI from '@/service/api/account';
import { PermissionOptions, Permissions } from '@/enums/account';
import { ResponseCode } from '@/enums/restful';
import { Account } from '@/store/modules/account';
import { AccountStore } from '@/store'


const showCreateDrawer = ref(false)
const showInfoModal = ref(false)
const showEditModal = ref(false)
const showPawdModal = ref(false)
const tableData = ref<Account[]>()


const accountModel = reactive({
  username: '',
  password: '',
  remarks: '',
  permissions: [Permissions.UNIVERSAL as string],
})

const hStyle = {
  marginRight: '8px'
}

const tableColumns: DataTableColumns<Account> = [
  { title: '用户', key: 'username' },
  { title: '备注', key: 'remarks' },
  { title: '权限', key: 'permissions' },
  {
    title: '操作',
    key: 'permissions',
    width: 256,
    render(item: Account) {
      const isSelf = AccountStore.user.username === item.username
      return [
        h(NButton, {
          style: hStyle,
          type: 'success',
          strong: true,
          secondary: true,
          size: 'small',
          onClick: () => {
            let { username, remarks, permissions } = item
            accountModel.username = username
            accountModel.remarks = remarks
            accountModel.permissions = permissions
            showEditModalEvent()
          }
        },{ default: () => '编辑' }),
        h(NButton, {
          style: hStyle,
          disabled: isSelf,
          type: 'info',
          strong: true,
          secondary: true,
          size: 'small',
          onClick: () => {

          }
        }, { default: () => '授权' }),
        h(NButton, {
          style: hStyle,
          disabled: isSelf,
          type: 'error',
          strong: true,
          secondary: true,
          size: 'small',
          onClick: () => {
            window.$dialog.success({
              title: '用户操作',
              content: '确认删除该用户?',
              positiveText: '确认',
              negativeText: '取消',
              maskClosable: false,
              onPositiveClick: async () => {
                await AccountAPI.delByUsername(item.username).then(res => {
                  if (res.code === ResponseCode.ACCE) {
                    window.$message.success("操作成功")
                    AccountStore.state.delete(item.username)
                    tableData.value = Array.from(AccountStore.state.values())
                  } else {
                    window.$message.error("操作失败")
                  }
                });
              },
              onNegativeClick: () => {},
            })
          }
        }, { default: () => '删除' }),
      ]
    }
  }
]

function showCreateDrawerEvent() {
  showCreateDrawer.value = true
}

function showPawdModalEvent() {
  showPawdModal.value = true
}
function showEditModalEvent() {
  showEditModal.value = true
}
function showInfoModalEvent() {
  showInfoModal.value = true
}


async function postAccountEvent() {
  await AccountAPI.post(accountModel).then(res => {
    showCreateDrawer.value = false
    if(res.code === ResponseCode.ACCE) {
      window.$message.success("操作成功")
      const account = Object.assign({}, accountModel);
      AccountStore.state.set(account.username, account)
    } else {
      window.$message.error("操作失败")
    }
  });
}

function clearAccountModel() {
  accountModel.username = ''
  accountModel.password = ''
  accountModel.remarks = ''
  accountModel.permissions = []
}

function update() {
  console.log("###########")
  tableData.value = Array.from(AccountStore.state.values())
}

onBeforeMount(() => {
  update();
})

onBeforeUpdate(() => {
  update();
})

</script>
~/src/api/account
