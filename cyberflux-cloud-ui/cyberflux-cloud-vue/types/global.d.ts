interface Window {
  $dialog: any
  $loading: any
  $message: any
  $notification: any
  $t: any
  $vue: any
}

declare type Nullable<T> = T | null

declare type NonNullable<T> = T extends null | undefined ? never : T

declare type Recordable<T = any> = Record<string, T>
