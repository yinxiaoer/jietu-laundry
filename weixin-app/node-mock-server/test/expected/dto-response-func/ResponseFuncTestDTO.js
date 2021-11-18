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

	importedResponseFuncTestDTO: function (loopArr) {

		var found1;

        if (!(loopArr instanceof Array)) {
        	loopArr = [];
        }

        found1 = loopArr.indexOf('importedResponseFuncTestDTO');

        if (found1 >= 0) {
        	if (loopArr.indexOf('importedResponseFuncTestDTO', found1 + 1) >= 0) {
        		return '{}';
        	}
        }

        loopArr.push('importedResponseFuncTestDTO');

		return JSON.stringify({
  "list": _getArray(function () {return {  "country": _getRes('CountryWsDTO', loopArr),  "email": faker.internet.email()};}),
  "items": _getArray(function () {return _getRes('CountryWsDTO', loopArr);}),
  "image": _getRes('CountryWsDTO', loopArr),
  "exampleString": faker.lorem.word(),
  "exampleNumber": faker.random.number(),
  "exampleInteger": faker.random.number(),
  "name": faker.name.findName(),
  "firstName": faker.name.firstName(),
  "lastName": faker.name.lastName(),
  "country": _getRes('CountryWsDTO', loopArr),
  "countryIsocode": faker.random.arrayElement(["CH","DE","AT","FR","UK","US","JP"]),
  "email": faker.internet.email(),
  "phone": faker.phone.phoneNumber(),
  "postalCode": faker.address.zipCode(),
  "streetName": faker.address.streetName(),
  "streetNumber": faker.random.number(),
  "title": faker.name.prefix(),
  "titleCode": faker.random.arrayElement(["mr","ms"]),
  "town": faker.address.city(),
  "parameters": {
    "additional": faker.lorem.word()
  }
}, null, 2);
	}

};

/* jshint ignore:end */
/* eslint-enable */