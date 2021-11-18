const express = require('express');
const router = express.Router();

function setHeaders(res) {
	res.setHeader('Test', "test");
	res.setHeader('Content-Type', options.contentType);
	res.setHeader('Access-Control-Expose-Headers', options.accessControlExposeHeaders);
	res.setHeader('Access-Control-Allow-Origin', options.accessControlAllowOrigin);
	res.setHeader('Access-Control-Allow-Methods', options.accessControlAllowMethods);
	res.setHeader('Access-Control-Allow-Headers', options.accessControlAllowHeaders);
	res.setHeader('Access-Control-Allow-Credentials', options.accessControlAllowCredentials);
}

router.get('/crm', (req, res) => {
	setHeaders(res);
	var response = { tunnelResponse: { success: true, headers: req.headers } };
	res.send(JSON.stringify(response, null, 2));
	res.end();
})

router.post('/checkUser', (req, res) => {
	setHeaders(res);
	var response = { tunnelResponse: { success: true, headers: req.headers } };
	res.send(JSON.stringify(response));
	res.end();
})

module.exports = router;
