
'use strict';

var express = require('express');
var https = require('https');
var openBrowser = require('react-dev-utils/openBrowser');
var jQueryExtend = require('extend');
var bodyParser = require('body-parser');
var cookieParser = require('cookie-parser');
var log = require('chip')();
var defaultOptions = require('../lib/defaults/options-defaults');
var options = require('./options');
var getCertificate = require('../lib/Utils');
var indexRouter = require('./routes/backend');
var config = require('../config')

var app = express();
var server;
options = jQueryExtend(defaultOptions, options);

var logFunc = function () {
	console.log(`访问地址:${config.baseUrl}`)
	if (options.open) {
		openBrowser(config.baseUrl);
	}
};

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cookieParser());
app.use('/backend', indexRouter);
if (options.tunnel.privateKey && options.tunnel.certificate) {
	server = https.createServer({
		key: getCertificate(options.tunnel.privateKey),
		cert: getCertificate(options.tunnel.certificate),
	}, app).listen(options.tunnel.port, logFunc);
} else {
	server = app.listen(options.tunnel.port, logFunc);
}
