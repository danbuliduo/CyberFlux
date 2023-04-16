import autoImportPlugin from 'unplugin-auto-import/vite'

export function configAutoImportPlugin() {
  return autoImportPlugin({
    imports: [
      'vue',
    ],
    dts: 'config/types/auto-imports.d.ts',
  })
}
