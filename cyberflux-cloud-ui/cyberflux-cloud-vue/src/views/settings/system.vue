<template>
  <div @mousedown="mousedown" @mouseup="mouseup">
    <n-card>
      <n-space justify="space-between">
        <n-button strong secondary type="success">
          保存
        </n-button>

        <n-button ref="remove" strong secondary type="error">
          
            <template #icon>
              <n-icon :component="Trash" />
            </template>
            {{ placeholder }}
        </n-button>
      </n-space>
    </n-card>
    <div style="height: 16px;"></div>
    <n-grid cols="1 s:2 m:3 l:4 xl:4 2xl:4" class="mt-4 proCard" responsive="screen" :x-gap="12" :y-gap="8">

      <n-grid-item>
        <NCard title="源列表" :segmented="{ content: true, footer: true }" size="small" :bordered="true">
          <template #header-extra>
          <n-button strong secondary type="success" @click="showAddItemFrom">
            添加
            <template #icon>
              <n-icon :component="Add" />
            </template>
          </n-button>
          </template>

          <Draggable ref="draggable" @end="end" @change="log" :move="move" class="draggable-ul" animation="500" :list="list0" group="people" item-Key="id">
            <template #item="{ element }">
              <div class="cursor-move draggable-li">
                <span class="ml-2">ID: {{ element.id }}</span>
              </div>
            </template>
          </Draggable>
        </NCard>
      </n-grid-item>

      <n-grid-item>
        <NCard title="单位一" :segmented="{ content: true, footer: true }" size="small" :bordered="false">
          <template #header-extra>
            <n-tag type="info">公司</n-tag>
          </template>

          <Draggable @change="log" class="draggable-ul" animation="500" :list="list1" group="people" item-Key="id">
            <template #item="{ element }">
              <div class="cursor-move draggable-li">
                <span class="ml-2">ID: {{ element.id }}</span>
              </div>
            </template>
          </Draggable>
        </NCard>
      </n-grid-item>

      <n-grid-item>
        <NCard title="单位二" :segmented="{ content: true, footer: true }" size="small" :bordered="false">
          <template #header-extra>
            <n-tag type="info">个人</n-tag>
          </template>
          <Draggable @change="log" class="draggable-ul" animation="500" :list="list2" group="people" item-Key="id">
            <template #item="{ element }">
              <div class="cursor-move draggable-li">
                <span class="ml-2">ID: {{ element.id }}</span>
              </div>
            </template>
          </Draggable>
        </NCard>
      </n-grid-item>

      <n-grid-item>
        <NCard title="单位三" :segmented="{ content: true, footer: true }" size="small" :bordered="false">
          <template #header-extra>
            <n-tag type="info">个人</n-tag>
          </template>
          <Draggable @change="log" class="draggable-ul" animation="500" :list="list3" group="people" item-Key="id">
            <template #item="{ element }">
              <div class="cursor-move draggable-li">
                <span class="ml-2">ID: {{ element.id }}</span>
              </div>
            </template>
          </Draggable>
        </NCard>
      </n-grid-item>
    </n-grid>
    <n-modal v-model:show="showModal" :show-icon="false" preset="dialog" title="添加元素" style="justify-content: center;">
        <n-form
          :model="formParams"
          ref="formRef"
          label-placement="left"
        >
         <n-form-item label="ID:" path="id">
            <n-input placeholder="请输入ID" v-model:value="formParams.id" />
          </n-form-item>
        </n-form>

        <template #action>
          <n-space>
            <n-button @click="() => (showModal = false)">取消</n-button>
            <n-button type="success"  @click="submitAddItemFrom">确定</n-button>
          </n-space>
        </template>
      </n-modal>
  </div>
</template>

<script lang="ts" setup>
import Draggable from 'vuedraggable'
import {
  NCard, NTag, NGridItem, NGrid, NButton, NSpace, NDialog, useDialog, NModal, NForm, NFormItem,
} from 'naive-ui'
import { Add, Trash } from '@vicons/ionicons5'


const formParams = reactive({
  id: ''
});

const list0 = reactive([
  { id: 'ZXA0001'},
  { id: 'ZXA0002'},
  { id: 'ZXA0003'},
  { id: 'ZXA0004'},
  { id: 'ZXA0005'}
]);

const list1 = reactive([{ id: 'ZYA0001', }]);

const list2 = reactive([{ id: 'ZYA0002',  }]);

const list3 = reactive([{ id: 'ZYA0003', }]);




const showModal = ref(false)
const formRef: any = ref({id: ''})

let currentInstance = null
let isRemove = false
let placeholder = '拖拽到此处删除'
let $refs: any

onMounted(()=> {
  currentInstance = getCurrentInstance()
  $refs = currentInstance?.refs
})

const props = defineProps({
  list: {
    type: Array,
      required: true
  },
  canRemove: {
    type: Boolean,
      required: false,
      default: true
  }
})


function mousedown(e: Event) {
  if (!props.canRemove || !$refs.draggable.$el.contains(e.target) || e.target === $refs.draggable.$el) {
    return
  }
  console.log("isRemove")
  isRemove = true
}

function mouseup() {
    console.log("mouseup",  isRemove)
  if (!props.canRemove) {
    return
  }
  isRemove = false
}

function end() {
  console.log("end")
  if (!props.canRemove) {
    return
  }
  isRemove = false
}

function move(e: any) {
  console.log("##", e.to.className, props.canRemove)
  if (!props.canRemove) {
    return
  }
  if (e.to.className.indexOf('drag-group-remove') !== -1) {
    placeholder = '松开鼠标,即可移除'
  } else {
    placeholder = '拖拽到此处删除'
  }
}

function log() {

}

function showAddItemFrom() {
  showModal.value = true
}

function submitAddItemFrom(e: Event) {
  e.preventDefault()
  formRef.value.validate((errors: any) => {
    if (!errors) {
      list0.push(formParams)
      window['$message'].success(`添加${formParams.id}成功`);
    } else {
      window['$message'].error('请填写完整信息');
    }
  })
  showModal.value = false
}

function removeItem(v: any, item: any) {
  try {
     console.log(v ,item)
  } catch (err) {
    console.log(err)
  }
}

</script>

<style lang="scss" scoped>
.draggable-ul {
  width: 100%;
  overflow: hidden;
  margin-top: -16px;

  .draggable-li {
    width: 100%;
    padding: 16px 10px;
    color: #888;
    border-bottom: 1px solid #ffffff;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}
</style>
