import type { RouteLocationRaw } from 'vue-router'
import { isNavigationFailure, Router } from 'vue-router'

import { RouterPath } from '@/enums'

const whitePathList = [RouterPath.LOGIN]

export function createRouterGuard(router: Router) {
  router.onError(error => {
    console.log(error, 'RouterError');
  })
}
