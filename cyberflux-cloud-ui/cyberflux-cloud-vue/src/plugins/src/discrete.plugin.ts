import * as NaiveUI from 'naive-ui'

/**
 * 挂载 Naive-ui 脱离上下文的 API
 * 如果你想在 setup 外使用 useDialog、useMessage、useNotification、useLoadingBar，可以通过 createDiscreteApi 来构建对应的 API。
 * https://www.naiveui.com/zh-CN/dark/components/discrete
 */

export function setupDiscreteApi() {
 // const designStore = useDesignSettingWithOut();
  const configProviderPropsRef = computed(() => ({
    theme: NaiveUI.darkTheme,
    themeOverrides: {
      common: {

      },
      LoadingBar: {
        //colorLoading: "#F811B2"
      }
    }
  }))

  const { message, dialog, notification, loadingBar } = NaiveUI.createDiscreteApi(
    ['message', 'dialog', 'notification', 'loadingBar'],
    {
      configProviderProps: configProviderPropsRef,
    }
  )

  window.$message = message;
  window.$dialog = dialog;
  window.$notification = notification;
  window.$loading = loadingBar;
}
