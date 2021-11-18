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

	get country(): CountryWsDTO {
		return this.country;
	}

	validCountry(): boolean {
		try {
			const v:any = this.country;
			return (v && _isValidType('ref', v));
		} catch (err) {
			return false;
		}
	}

	get countryIsocode(): string {
		return this.countryIsocode;
	}

	validCountryIsocode(): boolean {
		try {
			const v:any = this.countryIsocode;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	get country2(): CountryWsDTO {
		return this.country2;
	}

	validCountry2(): boolean {
		try {
			const v:any = this.country2;
			return (v && _isValidType('ref', v));
		} catch (err) {
			return false;
		}
	}

	get email(): string {
		return this.email;
	}

	validEmail(): boolean {
		try {
			const v:any = this.email;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	get firstName(): string {
		return this.firstName;
	}

	validFirstName(): boolean {
		try {
			const v:any = this.firstName;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	get id(): string {
		return this.id;
	}

	validId(): boolean {
		try {
			const v:any = this.id;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	get lastName(): string {
		return this.lastName;
	}

	validLastName(): boolean {
		try {
			const v:any = this.lastName;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	get line1(): string {
		return this.line1;
	}

	validLine1(): boolean {
		try {
			const v:any = this.line1;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	get phone(): string {
		return this.phone;
	}

	validPhone(): boolean {
		try {
			const v:any = this.phone;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	get postalCode(): string {
		return this.postalCode;
	}

	validPostalCode(): boolean {
		try {
			const v:any = this.postalCode;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	get streetName(): string {
		return this.streetName;
	}

	validStreetName(): boolean {
		try {
			const v:any = this.streetName;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	get streetNumber(): string {
		return this.streetNumber;
	}

	validStreetNumber(): boolean {
		try {
			const v:any = this.streetNumber;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	get title(): string {
		return this.title;
	}

	validTitle(): boolean {
		try {
			const v:any = this.title;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	get titleCode(): string {
		return this.titleCode;
	}

	validTitleCode(): boolean {
		try {
			const v:any = this.titleCode;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	get town(): string {
		return this.town;
	}

	validTown(): boolean {
		try {
			const v:any = this.town;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	get list(): Array<Object> {
		return this.list;
	}

	validList(): boolean {
		try {
			const v:any = this.list;
			return (v && _isValidType('Array', v));
		} catch (err) {
			return false;
		}
	}

	get items(): Array<CountryWsDTO> {
		return this.items;
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
