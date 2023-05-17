<template>
  <section>
    <n-space vertical :size="32">
      <n-grid layout-shift-disabled :x-gap="24" :cols="2">
        <n-grid-item>
          <n-card style="height: 208px;">

          </n-card>
        </n-grid-item>
        <n-grid-item>
          <n-card >
            <n-space justify="space-around" size="large">
              <div class="col-items">
                <img style="width: 48px;" src="@/assets/image/icon/connections.png"/>
                <h2>连接量</h2>
                <h2>1000000</h2>
              </div>
              <div class="col-items">
                <img style="width: 48px;"  src="@/assets/image/icon/topics.png"/>
                <h2>主题量</h2>
                <h2>1000000</h2>
              </div>
              <div class="col-items">
                <img style="width: 48px;"  src="@/assets/image/icon/subscriptions.png"/>
                <h2>订阅量</h2>
                <h2>1000000</h2>
              </div>
            </n-space>
          </n-card>
        </n-grid-item>
      </n-grid>

      <n-card title="节点信息" content-style="padding: 16px" :segmented="{content: true}">
        <n-grid layout-shift-disabled :x-gap="48" :cols="3" style="height: 300px;">
          <n-grid-item>
            <hex-mesh v-if="applicationsInitialized"
              :class-for-item="classForApplication"
              :items="applications"
              @click="select"
            >
              <template #item="{ item: application }">
                <div :key="application.name" class="hex__body node">
                  <div class="node-status-indicator" ></div>
                  <div class="node-header node-time-ago is-muted">
                    <!--sba-time-ago :date="application.statusTimestamp" /-->
                    gd
                  </div>
                  <div class="node-body">
                    <h1 class="node-name">
                      {{ application.name }}
                    </h1>
                  </div>
                  <h2 class="node-footer node-version">
                    META
                  </h2>
                </div>
              </template>
            </hex-mesh>
          </n-grid-item>

          <n-grid-item class="info-pane">
            <p>集群空间:</p>
            <p>节点角色:</p>
            <p>节点标识:</p>
            <p>节点名称:</p>
            <p>运行时长:</p>
          </n-grid-item>

          <n-grid-item class="info-pane">
            <p>版本信息:</p>
            <p>连 接 量:</p>
            <p>订 阅 量:</p>
            <p>主 题 量:</p>
          </n-grid-item>
        </n-grid>
      </n-card>
    </n-space>
  </section>
</template>

<script lang="ts">
import { HexMesh } from '@/components/common';
import { NodeStatus } from '@/enums'
import {
  NImage,
  NButton,
  NCard,
  NSpace,
  NTabs,
  NTabPane,
  NGrid,
  NGridItem,
} from 'naive-ui'
export default {
  components: {
    HexMesh,
    NImage,
    NButton,
    NCard,
    NSpace,
    NTabs,
    NTabPane,
    NGrid,
    NGridItem,
  },
  setup() {
    const applicationsInitialized = true;
    const applications = [
      {
        name: "Node-1",
      },
      {
        name: "Node-2",
      },
      {
        name: "Node-3",
      },

    ]

    //const { applications, applicationsInitialized, error } = useApplicationStore();
    return { applications, applicationsInitialized };
  },
  methods: {
    classForApplication(application: any) {
      return 'up';
      if (!application) {
        return null;
      }

      if (application.status === NodeStatus.UP) {
        return 'up';
      }
      if (application.status === NodeStatus.RESTRICTED) {
        return 'restricted';
      }
      if (application.status === NodeStatus.DOWN) {
        return 'down';
      }
      if (application.status === NodeStatus.OUT_OF_SERVICE) {
        return 'down';
      }
      if (application.status === NodeStatus.OFFLINE) {
        return 'down';
      }
      if (application.status === NodeStatus.UNKNOWN) {
        return 'unknown';
      }
      return '';
    },
    select(application: any) {
      if (application.instances.length === 1) {
        this.$router.push({
          name: 'instances/details',
          params: { instanceId: application.instances[0].id },
        });
      } else {
        this.$router.push({
          name: 'applications',
          params: { selected: application.name },
        });
      }
    },
  },
  /*install({ viewRegistry }) {
    viewRegistry.addView({
      path: '/wallboard',
      name: 'wallboard',
      label: 'wallboard.label',
      order: -100,
      component: this,
    });
  },*/
};

</script>

<style lang="scss" scoped>

.col-items {
  text-align: center;
  div, img {
    margin: auto;
  }
}


.info-pane p {
  font-size: 16px;
}

.node {
  color: #f5f5f5;
  font-size: 1em;
  font-weight: 400;
  line-height: 1;
  text-align: center;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.node-name {
  width: 100%;
  padding: 2.5%;
  color: #fff;
  font-size: 2em;
  font-weight: 600;
  line-height: 1.125;
}

.node-version {
  color: #f5f5f5;
  font-size: 1.25em;
  line-height: 1.25;
}

.node-header {
  width: 90%;
  margin-bottom: 0.5em;
}

.node-footer {
  width: 90%;
  margin-top: 0.5em;
}
</style>
<style lang="scss">
.up > polygon {
  stroke: #4ade80;
  fill: #4ade80;
}
.unknown > polygon {
  stroke: #808080;
  fill: #808080;
}

.down > polygon,
.offline > polygon {
  stroke: #f87171;
  fill: #f87171;
  stroke-width: 2;
}

.restricted > polygon {
  stroke: #f5e365;
  fill: #f5e365;
}

.hex .hex__body::after {
  display: flex;
  justify-content: center;
  align-content: center;
  font-size: 15em;
  position: absolute;
  z-index: -1;
  width: 100%;
}

.hex .hex__body {
  position: fixed;
  z-index: 10;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.hex.down .hex__body::after {
  content: '!';
  color: #f87171;
}

.hex.unknown .hex__body::after {
  content: '?';
  color: #808080;
}
</style>
