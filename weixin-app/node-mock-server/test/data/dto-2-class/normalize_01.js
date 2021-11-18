module.exports = {
	name: 'string',
	address: {
		'street': 'string',
		'postalCode': 'number',
		'primaryAddress': 'boolean',
		'company': {
			'name': 'string'
		}
	},
	entries: [
		'$ref-EntryDTO'
	],
	card: '$ref-CardDTO',
	user: '$ref-UserDTO',
	posts: [
		{
			words: 'string'
		}
	]
};
