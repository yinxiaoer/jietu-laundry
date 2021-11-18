
module.exports = {
	'refs': [
		'EntryDTO',
		'CardDTO',
		'UserDTO'
	],
	'attributes': [
		{
			'name': 'name',
			'nameCamel': 'name',
			'nameCapitalize': 'Name',
			'type': 'string'
		},
		{
			'name': 'address',
			'nameCamel': 'address',
			'nameCapitalize': 'Address',
			'type': 'Object'
		},
		{
			'name': 'address.street',
			'nameCamel': 'addressStreet',
			'nameCapitalize': 'AddressStreet',
			'type': 'string'
		},
		{
			'name': 'address.postalCode',
			'nameCamel': 'addressPostalCode',
			'nameCapitalize': 'AddressPostalCode',
			'type': 'number'
		},
		{
			'name': 'address.primaryAddress',
			'nameCamel': 'addressPrimaryAddress',
			'nameCapitalize': 'AddressPrimaryAddress',
			'type': 'boolean'
		},
		{
			'name': 'address.company',
			'nameCamel': 'addressCompany',
			'nameCapitalize': 'AddressCompany',
			'type': 'Object'
		},
		{
			'name': 'address.company.name',
			'nameCamel': 'addressCompanyName',
			'nameCapitalize': 'AddressCompanyName',
			'type': 'string'
		},
		{
			'name': 'entries',
			'nameCamel': 'entries',
			'nameCapitalize': 'Entries',
			'type': 'Array',
			'subType': 'ref',
			'subRef': 'EntryDTO',
			'subRefDecapitalize': 'entryDTO'
		},
		{
			'name': 'card',
			'nameCamel': 'card',
			'nameCapitalize': 'Card',
			'type': 'ref',
			'ref': 'CardDTO'
		},
		{
			'name': 'user',
			'nameCamel': 'user',
			'nameCapitalize': 'User',
			'type': 'ref',
			'ref': 'UserDTO'
		},
		{
			'name': 'posts',
			'nameCamel': 'posts',
			'nameCapitalize': 'Posts',
			'type': 'Array',
			'subType': 'Object',
			'attributes': [
				{
					'name': 'words',
					'nameCamel': 'words',
					'nameCapitalize': 'Words',
					'type': 'string'
				}
			]
		}
	]
}
;
