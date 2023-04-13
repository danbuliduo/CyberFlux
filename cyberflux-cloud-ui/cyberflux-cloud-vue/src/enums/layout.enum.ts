export enum Size {
  XS  = 'XS',
  SM  = 'SM',
  MD  = 'MD',
  LG  = 'LG',
  XL  = 'XL',
  XXL = 'XXL',
}

export enum Screen {
  XS  = 480,
  SM  = 576,
  MD  = 768,
  LG  = 992,
  XL  = 1200,
  XXL = 1600,
}

const ScreenMap = new Map<Size, number>();

ScreenMap.set(Size.XS, Screen.XS);
ScreenMap.set(Size.SM, Screen.SM);
ScreenMap.set(Size.MD, Screen.MD);
ScreenMap.set(Size.LG, Screen.LG);
ScreenMap.set(Size.XL, Screen.XL);
ScreenMap.set(Size.XXL, Screen.XXL);

export { ScreenMap };