/* eslint-disable */
/* jshint ignore:start */
'use strict';

var faker = require('faker');

function _getArray(getData) {
	var out = [],
		len = faker.random.number(10) + 1,
		i;

	for (i = 0; i < len; i += 1) {
		out.push(getData());
	}

	return out;
}


function _getRes(name, loopArr) {
	try {
		return JSON.parse(require(__dirname + '/' + name + '.js')['imported' + name](loopArr.slice()));
	} catch (err) {}

	return {};
}

module.exports = {

	<%=funcName%>: function (loopArr) {

		var found1;

        if (!(loopArr instanceof Array)) {
        	loopArr = [];
        }

        found1 = loopArr.indexOf('<%=funcName%>');

        if (found1 >= 0) {
        	if (loopArr.indexOf('<%=funcName%>', found1 + 1) >= 0) {
        		return '{}';
        	}
        }

        loopArr.push('<%=funcName%>');

		return JSON.stringify(<%=data%>, null, 2);
	}

};

/* jshint ignore:end */
/* eslint-enable */