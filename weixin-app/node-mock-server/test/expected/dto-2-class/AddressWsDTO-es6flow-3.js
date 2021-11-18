// @flow

import CountryWsDTO from './CountryWsDTO.class';

const _isValidType = (type: string, value: any): boolean => {
	switch (type) {
		case 'Array': return (value instanceof Array);
		default: return (typeof value === type.toLowerCase());
	}
};
const _array = (arr: Array<any>): Array<any> => arr || [];

class AddressWsDTO {

	country: CountryWsDTO;
	countryIsocode: string;
	country2: CountryWsDTO;
	email: string;
	firstName: string;
	id: string;
	lastName: string;
	line1: string;
	phone: string;
	postalCode: string;
	streetName: string;
	streetNumber: string;
	title: string;
	titleCode: string;
	town: string;
	list: Array<Object>;
	items: Array<CountryWsDTO>;

	constructor(addressWsDTO: AddressWsDTO) {

		addressWsDTO = addressWsDTO || {};

		this.country = new CountryWsDTO(addressWsDTO.country);
		this.countryIsocode = addressWsDTO.countryIsocode;
		this.country2 = new CountryWsDTO(addressWsDTO.country2);
		this.email = addressWsDTO.email;
		this.firstName = addressWsDTO.firstName;
		this.id = addressWsDTO.id;
		this.lastName = addressWsDTO.lastName;
		this.line1 = addressWsDTO.line1;
		this.phone = addressWsDTO.phone;
		this.postalCode = addressWsDTO.postalCode;
		this.streetName = addressWsDTO.streetName;
		this.streetNumber = addressWsDTO.streetNumber;
		this.title = addressWsDTO.title;
		this.titleCode = addressWsDTO.titleCode;
		this.town = addressWsDTO.town;
		this.list = addressWsDTO.list;
		this.items = _array(addressWsDTO.items).map(this.mapItems);
	}

	mapItems = (countryWsDTO: CountryWsDTO): CountryWsDTO => new CountryWsDTO(countryWsDTO);

	set country(value: CountryWsDTO) {
		this.country = value;
	}

	validCountry(): boolean {
		try {
			const v:any = this.country;
			return (v && _isValidType('ref', v));
		} catch (err) {
			return false;
		}
	}

	set countryIsocode(value: string) {
		this.countryIsocode = value;
	}

	validCountryIsocode(): boolean {
		try {
			const v:any = this.countryIsocode;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	set country2(value: CountryWsDTO) {
		this.country2 = value;
	}

	validCountry2(): boolean {
		try {
			const v:any = this.country2;
			return (v && _isValidType('ref', v));
		} catch (err) {
			return false;
		}
	}

	set email(value: string) {
		this.email = value;
	}

	validEmail(): boolean {
		try {
			const v:any = this.email;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	set firstName(value: string) {
		this.firstName = value;
	}

	validFirstName(): boolean {
		try {
			const v:any = this.firstName;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	set id(value: string) {
		this.id = value;
	}

	validId(): boolean {
		try {
			const v:any = this.id;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	set lastName(value: string) {
		this.lastName = value;
	}

	validLastName(): boolean {
		try {
			const v:any = this.lastName;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	set line1(value: string) {
		this.line1 = value;
	}

	validLine1(): boolean {
		try {
			const v:any = this.line1;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	set phone(value: string) {
		this.phone = value;
	}

	validPhone(): boolean {
		try {
			const v:any = this.phone;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	set postalCode(value: string) {
		this.postalCode = value;
	}

	validPostalCode(): boolean {
		try {
			const v:any = this.postalCode;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	set streetName(value: string) {
		this.streetName = value;
	}

	validStreetName(): boolean {
		try {
			const v:any = this.streetName;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	set streetNumber(value: string) {
		this.streetNumber = value;
	}

	validStreetNumber(): boolean {
		try {
			const v:any = this.streetNumber;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	set title(value: string) {
		this.title = value;
	}

	validTitle(): boolean {
		try {
			const v:any = this.title;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	set titleCode(value: string) {
		this.titleCode = value;
	}

	validTitleCode(): boolean {
		try {
			const v:any = this.titleCode;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	set town(value: string) {
		this.town = value;
	}

	validTown(): boolean {
		try {
			const v:any = this.town;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	set list(value: Array<Object>) {
		this.list = value;
	}

	validList(): boolean {
		try {
			const v:any = this.list;
			return (v && _isValidType('Array', v));
		} catch (err) {
			return false;
		}
	}

	set items(value: Array<CountryWsDTO>) {
		this.items = value;
	}

	validItems(): boolean {
		try {
			const v:any = this.items;
			return (v && _isValidType('Array', v));
		} catch (err) {
			return false;
		}
	}
}

export default AddressWsDTO;
