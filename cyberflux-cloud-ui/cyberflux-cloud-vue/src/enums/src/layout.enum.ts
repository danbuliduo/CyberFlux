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

const screenMap = new Map<Size, number>()

screenMap.set(Size.XS, Screen.XS)
screenMap.set(Size.SM, Screen.SM)
screenMap.set(Size.MD, Screen.MD)
screenMap.set(Size.LG, Screen.LG)
screenMap.set(Size.XL, Screen.XL)
screenMap.set(Size.XXL, Screen.XXL)

export { screenMap }
