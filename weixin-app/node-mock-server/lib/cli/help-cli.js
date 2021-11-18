/* eslint no-console: 0 */

function helpCli() {
	console.log('');
	console.log('usage: node <script> [--version] [--help] <command> [<args>]');
	console.log('usage with laundry: node laundry/index.js [--version] [--help] <command> [<args>]');
	console.log('');
	console.log('node-laundry commands:');
	console.log('    --version            print node-laundry version');
	console.log('    swagger-import       run a swagger import');
	console.log('    validate             run a validation for all mock data');
	console.log('    collections          lists all available collections');
	console.log('    collections <id>     activate collection');
	console.log('');
}

module.exports = helpCli;
