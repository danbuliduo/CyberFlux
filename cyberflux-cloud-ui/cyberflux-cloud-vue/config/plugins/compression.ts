import compressionPlugin from 'vite-plugin-compression'

export function configCompressionPlugin(deleteOriginFile : boolean = false) {
  return compressionPlugin({
    disable: false,
    verbose: false,
    threshold: 10240,
    algorithm: 'gzip',
    ext: '.gz',
    deleteOriginFile,
  })
}
