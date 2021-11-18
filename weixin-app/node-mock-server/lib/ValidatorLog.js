/* global _validatorLog */
/* eslint no-use-before-define: 0 */
/* eslint block-scoped-var: 0 */
'use strict';

var log = require('chip')();

if (!_validatorLog) {
	var _validatorLog = [];
}

/**
 * @function _getDateTime
 * @returns {string} dateTime
 * @private
 */
function _getDateTime() {
	return new Date()
		.toISOString()
		.replace(/T/, ' ')
		.replace(/\..+/, '') + ' GMT+0000';
}

/**
 * @function _getTime
 * @returns {string} time
 * @private
 */
function _getTime() {
	return new Date()
		.toISOString()
		.replace(/T/, ' ')
		.replace(/\..+/, '')
		.split(' ')[1];
}

/**
 * @function _consoleLog
 * @param {object} msg
 * @param {string} msg.type ENUM(success|warn|error)
 * @param {string} msg.msg
 * @param {string} msg.time
 * @returns {void}
 * @private
 */
function _consoleLog(msg) {

	var type;

	if (process.env.NODE_ENV === 'test') {
		return;
	}

	type = msg.type;
	type = (type === 'neutral') ? 'log' : type;
	type = (type === 'success') ? 'info' : type;

	if (log && log[type]) {
		log[type](msg.time + ': ' + _getWithoutHtml(msg.msg));
	}
}

/**
 * @method _getWithoutHtml
 * @param {string} value
 * @returns {string}
 * @private
 */
function _getWithoutHtml(value) {
	if (typeof value !== 'string') {
		return '';
	}
	return value.replace(/<(.|\n)*?>/g, '');
}

/**
 * @function _push
 * @param {object} msg
 * @param {string} msg.type ENUM(neutral|success|warn|error)
 * @param {string} msg.msg
 * @returns {void}
 * @private
 */
function _push(msg) {

	var obj = {
		type: msg.type || 'neutral',
		msg: msg.msg,
		dateTime: _getDateTime(),
		time: _getTime(),
	};

	_validatorLog.push(obj);
	_consoleLog(obj);
}

var validatorLog = {

	/**
	 * @function error
	 * @param {string} msg
	 * @returns {void}
	 * @public
	 */
	error: function (msg) {
		_push({
			msg: msg,
			type: 'error',
		});
	},

	/**
	 * @function warn
	 * @param {string} msg
	 * @returns {void}
	 * @public
	 */
	warn: function (msg) {
		_push({
			msg: msg,
			type: 'warn',
		});
	},

	/**
	 * @function success
	 * @param {string} msg
	 * @returns {void}
	 * @public
	 */
	success: function (msg) {
		_push({
			msg: msg,
			type: 'success',
		});
	},

	/**
	 * @function neutral
	 * @param {string} msg
	 * @returns {void}
	 * @public
	 */
	neutral: function (msg) {
		_push({
			msg: msg,
			type: 'neutral',
		});
	},

	/**
	 * @function get
	 * @returns {Array}
	 * @public
	 */
	get: function () {
		return _validatorLog;
	},

	/**
	 * @function clear
	 * @returns {Array}
	 * @public
	 */
	clear: function () {
		_validatorLog = [];
	},

};

module.exports = validatorLog;
