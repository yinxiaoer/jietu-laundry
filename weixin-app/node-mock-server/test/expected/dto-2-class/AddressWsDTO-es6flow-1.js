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

	set country(value: CountryWsDTO) {
		this.country = value;
	}

	get countryIsocode(): string {
		return this.countryIsocode;
	}

	set countryIsocode(value: string) {
		this.countryIsocode = value;
	}

	get country2(): CountryWsDTO {
		return this.country2;
	}

	set country2(value: CountryWsDTO) {
		this.country2 = value;
	}

	get email(): string {
		return this.email;
	}

	set email(value: string) {
		this.email = value;
	}

	get firstName(): string {
		return this.firstName;
	}

	set firstName(value: string) {
		this.firstName = value;
	}

	get id(): string {
		return this.id;
	}

	set id(value: string) {
		this.id = value;
	}

	get lastName(): string {
		return this.lastName;
	}

	set lastName(value: string) {
		this.lastName = value;
	}

	get line1(): string {
		return this.line1;
	}

	set line1(value: string) {
		this.line1 = value;
	}

	get phone(): string {
		return this.phone;
	}

	set phone(value: string) {
		this.phone = value;
	}

	get postalCode(): string {
		return this.postalCode;
	}

	set postalCode(value: string) {
		this.postalCode = value;
	}

	get streetName(): string {
		return this.streetName;
	}

	set streetName(value: string) {
		this.streetName = value;
	}

	get streetNumber(): string {
		return this.streetNumber;
	}

	set streetNumber(value: string) {
		this.streetNumber = value;
	}

	get title(): string {
		return this.title;
	}

	set title(value: string) {
		this.title = value;
	}

	get titleCode(): string {
		return this.titleCode;
	}

	set titleCode(value: string) {
		this.titleCode = value;
	}

	get town(): string {
		return this.town;
	}

	set town(value: string) {
		this.town = value;
	}

	get list(): Array<Object> {
		return this.list;
	}

	set list(value: Array<Object>) {
		this.list = value;
	}

	get items(): Array<CountryWsDTO> {
		return this.items;
	}

	set items(value: Array<CountryWsDTO>) {
		this.items = value;
	}
}

export default AddressWsDTO;
