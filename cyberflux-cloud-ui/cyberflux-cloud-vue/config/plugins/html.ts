/**
 * Plugin to minimize and use ejs template syntax in index.html.
 * https://github.com/anncwb/vite-plugin-html
 */
import { createHtmlPlugin } from 'vite-plugin-html'
import pkg from '../../package.json'

const GLOB_CONFIG_FILE_NAME = 'app.config.js'
const VITE_APP_TITLE = 'CyberFlux Cloud'
const VITE_PUBLIC_PATH ='/'

export function configHtmlPlugin(isBuild: boolean) {

  const path = VITE_PUBLIC_PATH.endsWith('/') ? VITE_PUBLIC_PATH : `${VITE_PUBLIC_PATH}/`;

  const getAppConfigSrc = () => {
    return `${path || '/'}${GLOB_CONFIG_FILE_NAME}?v=${pkg.version}-${new Date().getTime()}`;
  };

  const htmlPlugin = createHtmlPlugin({
    minify: isBuild,
    inject: {
      // Inject data into ejs template
      data: {
        title: VITE_APP_TITLE,
      },
      // Embed the generated app.config.js file
      tags: isBuild ? [{
        tag: 'script',
        attrs: {
          src: getAppConfigSrc(),
        }
      }] : [],
    },
  });
  return htmlPlugin;
}
