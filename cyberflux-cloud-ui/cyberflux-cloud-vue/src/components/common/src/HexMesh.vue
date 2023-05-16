<template>
  <div class="hex-mesh" ref="root" v-on-resize="onResize">
    <svg xmlns="http://www.w3.org/2000/svg" :height="meshHeight" :width="meshWidth">
      <defs>
        <clipPath id="hex-clip">
          <polygon :points="hexPath" />
        </clipPath>
      </defs>
      <template v-for="row in rows">
        <g v-for="col in cols + (row % 2 ? 0 : -1)"
          class="hex" @click="click($event, col, row)"
          :class="classForItem!(item(col, row))"
          :key="`${col}-${row}`"
          :transform="translate(col, row)">
          <polygon :points="hexPath"/>
          <foreignObject v-if="item(col, row)" x="0" y="0"
            style="pointer-events: none"
            :height="hexHeight"
            :width="hexWidth"
          >
            <slot name="item" :item="item(col, row)" />
          </foreignObject>
        </g>
      </template>
    </svg>
  </div>
</template>

<script lang="ts">
import ResizeObserver from 'resize-observer-polyfill';

const observers = new WeakMap();

const mounted = (el: any, binding: any) => {
  beforeUnmount(el);
  const observer = new ResizeObserver(binding.value);
  observer.observe(el);
  observers.set(el, observer);
};

const beforeUnmount = (el: any) => {
  const observer = observers.get(el);
  if (observer) {
    observer.disconnect();
    observers.delete(el);
  }
};

const onResize = {
  mounted,
  update(el: any, binding: any) {
    if (binding.value === binding.oldValue) {
      return;
    }
    mounted(el, binding);
  },
  beforeUnmount,
};

const tileCount = (cols: number, rows: number) => {
  const shorterRows = Math.floor(rows / 2);
  return rows * cols - shorterRows;
};

const calcSideLength = (width: number, height: number, cols: number, rows: number) => {
  const fitToWidth = width / cols / Math.sqrt(3);
  const fitToHeight = (height * 2) / (3 * rows + 1);
  return Math.min(fitToWidth, fitToHeight);
};

export const calcLayout = (minTileCount: number, width: number, height: number) => {
  let cols = 1, rows = 1;
  let sideLength = calcSideLength(width, height, cols, rows);

  while (minTileCount > tileCount(cols, rows)) {
    const sidelengthExtraCol = calcSideLength(width, height, cols + 1, rows);
    const sidelengthExtraRow = calcSideLength(width, height, cols, rows + 1);
    if (sidelengthExtraCol > sidelengthExtraRow) {
      sideLength = sidelengthExtraCol;
      cols++;
    } else {
      sideLength = sidelengthExtraRow;
      rows++;
    }
  }
  return {
    cols, rows, sideLength,
  };
};

export default {
  directives: { onResize },
  props: {
    items: {
      type: Array,
      default: () => [],
    },
    classForItem: {
      type: Function,
      default: () => void 0,
    },
  },
  emits: ['click'],
  setup() {
    const root = ref<HTMLInputElement>();
    return {
      root,
    };
  },
  data: () => ({
    cols: 1,
    rows: 1,
    sideLength: 1,
  }),
  computed: {
    itemCount() {
      return this.items.length;
    },
    hexPath() {
      return `${this.point(0)} ${this.point(1)} ${this.point(2)}
          ${this.point(3)} ${this.point(4)} ${this.point(5)}`;
    },
    hexHeight() {
      return this.sideLength * 2;
    },
    hexWidth() {
      return this.sideLength * Math.sqrt(3);
    },
    meshWidth() {
      return this.hexWidth * this.cols;
    },
    meshHeight() {
      return this.sideLength * (2 + (this.rows - 1) * 1.5);
    },
  },
  watch: {
    sideLength(newVal) {
      if(this.root) {
        this.root.style.fontSize = `${newVal / 9.5}px`;
      }
    },
    itemCount: {
      handler: 'updateLayout',
      immediate: true,
    },
  },
  methods: {
    translate(col: number, row: number) {
      const x = (col - 1) * this.hexWidth + (row % 2 ? 0 : this.hexWidth / 2);
      const y = (row - 1) * this.sideLength * 1.5;
      return `translate(${x},${y})`;
    },
    item(col: number, row: number) {
      const rowOffset =
        (row - 1) * this.cols - Math.max(Math.floor((row - 1) / 2), 0);
      const index = rowOffset + col - 1;
      return this.items[index];
    },
    point(i: number) {
      const innerSideLength = this.sideLength * 0.95;
      const marginTop = this.hexHeight / 2;
      const marginLeft = this.hexWidth / 2;
      return `${marginLeft + innerSideLength * Math.cos(((1 + i * 2) * Math.PI) / 6)
        },${marginTop + innerSideLength * Math.sin(((1 + i * 2) * Math.PI) / 6)}`;
    },
    click(event: any, col: number, row: number) {
      const item = this.item(col, row);
      if (item) {
        this.$emit('click', item, event);
      }
    },
    updateLayout() {
      if (this.root) {
        const boundingClientRect = this.root.getBoundingClientRect();
        const layout = calcLayout(
          this.itemCount,
          boundingClientRect.width,
          boundingClientRect.height
        );
        this.cols = layout.cols;
        this.rows = layout.rows;
        this.sideLength = layout.sideLength;
      }
    },
    onResize(entries: any) {
      for (let e of entries) {
        if (e.target === this.root) {
          this.updateLayout();
        }
      }
    }
  }
};
</script>

<style>
.hex-mesh {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.hex {
  cursor: pointer;
  fill-opacity: 0.05;
  stroke-width: 0.5;
  stroke-opacity: 0.8;
}

.hex polygon {
  fill: transparent;
  transition: all ease-out 250ms;
}

.hex:hover polygon {
  fill-opacity: 0.25;
  stroke-opacity: 1;
  stroke-width: 2;
}
</style>
