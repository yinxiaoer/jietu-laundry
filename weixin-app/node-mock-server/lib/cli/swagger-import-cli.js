/* eslint no-console: 0 */

var SwaggerImport = require('../SwaggerImport');

function swaggerImportCli(options) {
	var swaggerImporter = new SwaggerImport(options.swaggerImport);
	swaggerImporter.doImport();
}

module.exports = swaggerImportCli;
