import * as NaiveUI from 'naive-ui'

export async function setupDiscreteApi() {
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

  window.$message = message
  window.$dialog = dialog
  window.$notification = notification
  window.$loading = loadingBar
}
