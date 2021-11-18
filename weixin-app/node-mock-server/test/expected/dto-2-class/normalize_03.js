
module.exports = {
	'refs':	[
		'CountryWsDTO',
		'ImageDTO'
	],
	'attributes':	[
		{
			'name':	'list',
			'nameCamel':	'list',
			'nameCapitalize':	'List',
			'type':	'Array',
			'attributes':	[
				{
					'name':	'country',
					'nameCamel':	'country',
					'nameCapitalize':	'Country',
					'type':	'ref',
					'ref':	'CountryWsDTO'
				},
				{
					'name':	'email',
					'nameCamel':	'email',
					'nameCapitalize':	'Email',
					'type':	'string'
				}
			],
			'subType':	'Object'
		},
		{
			'name':	'items',
			'nameCamel':	'items',
			'nameCapitalize':	'Items',
			'type':	'Array',
			'subType':	'ref',
			'subRef':	'ImageDTO',
			'subRefDecapitalize':	'imageDTO'
		},
		{
			'name':	'parameters',
			'nameCamel':	'parameters',
			'nameCapitalize':	'Parameters',
			'type':	'Object'
		},
		{
			'name':	'parameters.additional',
			'nameCamel':	'parametersAdditional',
			'nameCapitalize':	'ParametersAdditional',
			'type':	'string'
		}
	]
};
