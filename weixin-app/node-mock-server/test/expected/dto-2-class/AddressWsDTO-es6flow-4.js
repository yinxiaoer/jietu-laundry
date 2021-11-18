// @flow

import CountryWsDTO from './CountryWsDTO.class';const _array = (arr: Array<any>): Array<any> => arr || [];

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

	get countryIsocode(): string {
		return this.countryIsocode;
	}

	get country2(): CountryWsDTO {
		return this.country2;
	}

	get email(): string {
		return this.email;
	}

	get firstName(): string {
		return this.firstName;
	}

	get id(): string {
		return this.id;
	}

	get lastName(): string {
		return this.lastName;
	}

	get line1(): string {
		return this.line1;
	}

	get phone(): string {
		return this.phone;
	}

	get postalCode(): string {
		return this.postalCode;
	}

	get streetName(): string {
		return this.streetName;
	}

	get streetNumber(): string {
		return this.streetNumber;
	}

	get title(): string {
		return this.title;
	}

	get titleCode(): string {
		return this.titleCode;
	}

	get town(): string {
		return this.town;
	}

	get list(): Array<Object> {
		return this.list;
	}

	get items(): Array<CountryWsDTO> {
		return this.items;
	}
}

export default AddressWsDTO;
