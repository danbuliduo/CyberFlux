import { resolve } from 'path'

export function createViteResolve()  {
  return {
    alias: {
      '~': resolve(process.cwd(), '.'),
      '@': resolve(process.cwd(), 'src'),
      '#': resolve(process.cwd(), 'types'),
      '@library': resolve(process.cwd(), 'library'),
    },
    // https://cn.vitejs.dev/config/#resolve-extensions
    extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.vue'],
  }
}
