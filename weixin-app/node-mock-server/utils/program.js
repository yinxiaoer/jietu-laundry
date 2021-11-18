const program = require('commander');
program
	.version("1.3.2")
	.option('-p, --port <port>', 'Listening port number')
	.option('-a, --address <ip>', 'Listening host name or ip')
	.option('-d, --develop <develop>', 'develop path')
	.parse(process.argv);
module.exports = program
