const mobileReg = new RegExp("(\\d{3})(\\d{4})(\\d{4})");

export const mobile = (mobile) => mobile.replace(mobileReg, '$1****$3')
