import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

/**
 * easing: 动画方式
 * speed: 递增进度条的速度
 * showSpinner: 是否显示加载ico
 * trickleSpeed: 自动递增间隔
 * minimum: 更改启动时使用的最小百分比
 */
NProgress.configure({
  easing: 'ease',
  speed: 500,
  showSpinner: false,
  trickleSpeed: 100,
  minimum: 0.2
})


/**
 * @brief 开启进度条
 */
function openProgressBar(): void {
  if(!NProgress.isStarted()) {
    NProgress.start()
  }
}

/**
 * @brief 关闭进度条
 */
function closeProgressBar(): void {
  NProgress.done()
}

export {
  openProgressBar,
  closeProgressBar,
}