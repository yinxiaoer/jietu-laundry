/**
 *
 * 日期转换（转换为时间戳）
 */
function padNumber(num, digits, trim, negWrap) {
  var neg = '';
  if (num < 0 || (negWrap && num <= 0)) {
    if (negWrap) {
      num = -num + 1;
    } else {
      num = -num;
      neg = '-';
    }
  }
  num = '' + num;
  while (num.length < digits) num = ZERO_CHAR + num;
  if (trim) {
    num = num.substr(num.length - digits);
  }
  return neg + num;
}
function dateGetter(name, size, offset, trim, negWrap) {
  offset = offset || 0;
  return function(date) {
    var value = date['get' + name]();
    if (offset > 0 || value > -offset) {
      value += offset;
    }
    if (value === 0 && offset == -12) value = 12;
    return padNumber(value, size, trim, negWrap);
  };
}
function dateStrGetter(name, shortForm, standAlone) {
  return function(date, formats) {
    var value = date['get' + name]();
    var propPrefix = (standAlone ? 'STANDALONE' : '') + (shortForm ? 'SHORT' : '');
    var get = (propPrefix + name).toUpperCase();

    return formats[get][value];
  };
}
function concat(array1, array2, index) {
  return array1.concat([].slice.call(array2, index));
}
const
  DATETIME_FORMATS = {
    "AMPMS": [
      "AM",
      "PM"
    ],
      "DAY": [
      "Sunday",
      "Monday",
      "Tuesday",
      "Wednesday",
      "Thursday",
      "Friday",
      "Saturday"
    ],
      "ERANAMES": [
      "Before Christ",
      "Anno Domini"
    ],
      "ERAS": [
      "BC",
      "AD"
    ],
      "FIRSTDAYOFWEEK": 6,
      "MONTH": [
      "January",
      "February",
      "March",
      "April",
      "May",
      "June",
      "July",
      "August",
      "September",
      "October",
      "November",
      "December"
    ],
      "SHORTDAY": [
      "Sun",
      "Mon",
      "Tue",
      "Wed",
      "Thu",
      "Fri",
      "Sat"
    ],
      "SHORTMONTH": [
      "Jan",
      "Feb",
      "Mar",
      "Apr",
      "May",
      "Jun",
      "Jul",
      "Aug",
      "Sep",
      "Oct",
      "Nov",
      "Dec"
    ],
      "STANDALONEMONTH": [
      "January",
      "February",
      "March",
      "April",
      "May",
      "June",
      "July",
      "August",
      "September",
      "October",
      "November",
      "December"
    ],
      "WEEKENDRANGE": [
      5,
      6
    ],
      "fullDate": "EEEE, MMMM d, y",
      "longDate": "MMMM d, y",
      "medium": "MMM d, y h:mm:ss a",
      "mediumDate": "MMM d, y",
      "mediumTime": "h:mm:ss a",
      "short": "M/d/yy h:mm a",
      "shortDate": "M/d/yy",
      "shortTime": "h:mm a"
  },
  DATE_FORMATS = {
    yyyy: dateGetter('FullYear', 4, 0, false, true),
    yy: dateGetter('FullYear', 2, 0, true, true),
    y: dateGetter('FullYear', 1, 0, false, true),
    MMMM: dateStrGetter('Month'),
    MMM: dateStrGetter('Month', true),
    MM: dateGetter('Month', 2, 1),
    M: dateGetter('Month', 1, 1),
    LLLL: dateStrGetter('Month', false, true),
    dd: dateGetter('Date', 2),
    d: dateGetter('Date', 1),
    HH: dateGetter('Hours', 2),
    H: dateGetter('Hours', 1),
    hh: dateGetter('Hours', 2, -12),
    h: dateGetter('Hours', 1, -12),
    mm: dateGetter('Minutes', 2),
    m: dateGetter('Minutes', 1),
    ss: dateGetter('Seconds', 2),
    s: dateGetter('Seconds', 1),
    // while ISO 8601 requires fractions to be prefixed with `.` or `,`
    // we can be just safely rely on using `sss` since we currently don't support single or two digit fractions
    sss: dateGetter('Milliseconds', 3),
    EEEE: dateStrGetter('Day'),
    EEE: dateStrGetter('Day', true)
  },
  DATE_FORMATS_SPLIT = /((?:[^yMLdHhmsaZEwG']+)|(?:'(?:[^']|'')*')|(?:E+|y+|M+|L+|d+|H+|h+|m+|s+|a|Z|G+|w+))(.*)/;
const DATE_UTIL = {
  /**
   * 获取两个日期之间日期
   * @param startDate
   * @param endDate
   * @param formatStr
   */
  getBetweenDays(startDate , endDate , formatStr){
    let
      date = [],
      start = new Date(startDate).getTime(),
      end = new Date(endDate).getTime(),
      i = start;
    do{
      date.push(new Date(i));
      i += 86400000;
    }while (i <= end);
    return date;
  },
  /**
   * 获取今天的时间戳
   */
  getTodayTt(){
    let day = new Date();
    day.setHours(0);
    day.setMinutes(0);
    day.setSeconds(0);
    day.setMilliseconds(0);
    return day.getTime();
  },
  /**
   * 获取当前时间的时间戳
   */
  getNowTt(){
    let day = new Date();
    return day.getTime();
  },
  /**
   * 获取今天的时间戳
   * @param date 时间对象
   * @param addNum 增加的天数
   */
  addDate(date , addNum = 0){
    let time = date.getTime() , addTime = addNum * 86400000;
    return new Date(time + addTime);
  },
  /**
   * 日期格式化
   */
  formatDate(date = new Date(), formatStr = "yyyy-MM-dd HH:mm:ss"){
    let
      str = formatStr;
    str=str.replace(/yyyy|YYYY/,date.getFullYear());
    str=str.replace(/yy|YY/,(date.getYear() % 100)>9?(date.getYear() % 100).toString():'0' + (date.getYear() % 100));
    str=str.replace(/MM/,date.getMonth() + 1 > 9 ? date.getMonth() + 1 : '0' + (date.getMonth() + 1));
    str=str.replace(/M/g,date.getMonth());
    //str=str.replace(/w|W/g,Week[date.getDay()]);

    str=str.replace(/dd|DD/,date.getDate()>9?date.getDate().toString():'0' + date.getDate());
    str=str.replace(/d|D/g,date.getDate());

    str=str.replace(/hh|HH/,date.getHours()>9?date.getHours().toString():'0' + date.getHours());
    str=str.replace(/h|H/g,date.getHours());
    str=str.replace(/mm/,date.getMinutes()>9?date.getMinutes().toString():'0' + date.getMinutes());
    str=str.replace(/m/g,date.getMinutes());

    str=str.replace(/ss|SS/,date.getSeconds()>9?date.getSeconds().toString():'0' + date.getSeconds());
    str=str.replace(/s|S/g,date.getSeconds());
    return str;
  },
  dateFilter(date, format){
    var text = '',
      parts = [],
      fn, match;

    format = format || 'mediumDate';
    while (format) {
      match = DATE_FORMATS_SPLIT.exec(format);
      if (match) {
        parts = concat(parts, match, 1);
        format = parts.pop();
      } else {
        parts.push(format);
        format = null;
      }
    }
    parts.forEach(function(value) {
      fn = DATE_FORMATS[value];
      text += fn ? fn(date, DATETIME_FORMATS)
        : value === "''" ? "'" : value.replace(/(^'|'$)/g, '').replace(/''/g, "'");
    });

    return text;
  }
};
export default DATE_UTIL
