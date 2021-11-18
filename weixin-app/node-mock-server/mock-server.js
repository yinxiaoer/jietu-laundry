'use strict';

/* eslint global-require: 0 */
var AppControllerSingleton = require('./lib/controller/AppController');
var versionCli = require('./lib/cli/version-cli');
var helpCli = require('./lib/cli/help-cli');
var swaggerImportCli = require('./lib/cli/swagger-import-cli');
var validateCli = require('./lib/cli/validate-cli');
var collectionsCli = require('./lib/cli/collections-cli');
var createDefinedDirectories = require('./lib/commands/create-defined-directories');

var processVersionIndex = process.argv.indexOf('--version');
var processHelpIndex = process.argv.indexOf('--help');
var processSwaggerImportIndex = process.argv.indexOf('swagger-import');
var processValidateIndex = process.argv.indexOf('validate');
var processCollectionsIndex = process.argv.indexOf('collections');
var doExport;

function runServer(options) {
	var appController = AppControllerSingleton.getInstance(options);

	createDefinedDirectories(appController.options);

	var UiController = require('./lib/controller/UiController');
	var SchemaController = require('./lib/controller/SchemaController');
	var SwaggerImportController = require('./lib/controller/SwaggerImportController');
	var DTOController = require('./lib/controller/DTOController');
	var ResponseController = require('./lib/controller/ResponseController');
	var PreferencesController = require('./lib/controller/PreferencesController');
	var ValidatorController = require('./lib/controller/ValidatorController');
	var CollectionController = require('./lib/controller/CollectionController');
	var FileOpenController = require('./lib/controller/FileOpenController');

	var MockController = require('./lib/controller/MockController');

	return {
		appController: appController,
		uiController: new UiController(),
		schemaController: new SchemaController(),
		swaggerImportController: new SwaggerImportController(),
		dtoController: new DTOController(),
		ResponseController: new ResponseController(),
		preferencesController: new PreferencesController(),
		validatorController: new ValidatorController(),
		collectionController: new CollectionController(),
		fileOpenController: new FileOpenController(),
		mockController: new MockController(),
	};
}

if (processVersionIndex >= 0) {
	doExport = versionCli;
} else if (processHelpIndex >= 0) {
	doExport = helpCli;
} else if (processCollectionsIndex >= 0) {
	doExport = collectionsCli;
} else if (processSwaggerImportIndex >= 0) {
	doExport = swaggerImportCli;
} else if (processValidateIndex >= 0) {
	doExport = validateCli.bind(null, runServer);
} else {
	doExport = runServer;
}

module.exports = doExport;
