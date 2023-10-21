interface Window {
  $dialog: any;
  $loading: any;
  $message: any;
  $notification: any;
  $t: any;
  $vue: any;
};

declare interface Result<T = any> {
  code?: number;
  header?: any;
  payload?: T;
}

declare interface Tuple2<T1 = any, T2 = any> {
  t1: T1;
  t2: T2;
}

declare type Nullable<T> = T | null;

declare type NonNullable<T> = T extends null | undefined ? never : T;

declare type Recordable<T = any> = Record<string, T>;
