/* eslint no-console: 0 */

var Utils = require('./../Utils');
var utils = new Utils();
var packageData = JSON.parse(utils.readFile(__dirname + '/../../package.json'));

function versionCli() {
	console.log('node-laundry version ' + packageData.version);
}

module.exports = versionCli;
