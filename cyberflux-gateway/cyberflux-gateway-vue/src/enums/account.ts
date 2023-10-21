import type { TransferOption } from 'naive-ui'

export enum Permissions {
  ROOT = "ROOT",
  UNIVERSAL = "UNIVERSAL",
}
export const PermissionOptions: TransferOption[] = [
  {
    label: Permissions.ROOT,
    value: Permissions.ROOT,
    disabled: true,
  },
  {
    label: Permissions.UNIVERSAL,
    value: Permissions.UNIVERSAL,
  },
];
