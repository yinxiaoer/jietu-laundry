<%

  var _dirname = __dirname,
      _require = require;

  function _getArray(getData) {
    var out = [],
      len = 2,
      i;

    for (i = 0; i < len; i += 1) {
      out.push(getData());
    }

    return JSON.stringify(out, null, 2);
  }

  function _getRef(name) {
    try {
      return JSON.parse(_require(_dirname + '<%funcPath%>/' + name + '.js')['imported' + name]());
    } catch (err) {}

    return {};
  }

%>