
import CountryWsDTO from './CountryWsDTO.class';

/**
 * @param {string} type - the typeof the value
 * @param {*} value - the value
 * @returns {boolean} returns true if type is correct
 * @private
 */
const _isValidType = (type, value) => {
	switch (type) {
		case 'Array': return (value instanceof Array);
		default: return (typeof value === type.toLowerCase());
	}
};
const _array = (arr) => arr || [];

/**
 * @constructor addressWsDTO
 */
class AddressWsDTO {

	/**
	 * @param {Object} addressWsDTO - initial data
	 * @returns {void}
	 * @public
	 */
	constructor(addressWsDTO) {

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
		this.items = _array(addressWsDTO.items).map((countryWsDTO) => new CountryWsDTO(countryWsDTO));
	}

	/**
	 * @returns {CountryWsDTO} returns country
	 * @public
	 */
	get country() {
		return this.country;
	}

	/**
	 * @param {CountryWsDTO} value - the set value
	 * @returns {void}
	 * @public
	 */
	set country(value) {
		this.country = value;
	}

	/**
	 * @returns {boolean} returns true if Country is valid
	 * @public
	 */
	validCountry() {
		try {
			const v = this.country;
			return (v && _isValidType('ref', v));
		} catch (err) {
			return false;
		}
	}

	/**
	 * @returns {string} returns countryIsocode
	 * @public
	 */
	get countryIsocode() {
		return this.countryIsocode;
	}

	/**
	 * @param {string} value - the set value
	 * @returns {void}
	 * @public
	 */
	set countryIsocode(value) {
		this.countryIsocode = value;
	}

	/**
	 * @returns {boolean} returns true if CountryIsocode is valid
	 * @public
	 */
	validCountryIsocode() {
		try {
			const v = this.countryIsocode;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	/**
	 * @returns {CountryWsDTO} returns country2
	 * @public
	 */
	get country2() {
		return this.country2;
	}

	/**
	 * @param {CountryWsDTO} value - the set value
	 * @returns {void}
	 * @public
	 */
	set country2(value) {
		this.country2 = value;
	}

	/**
	 * @returns {boolean} returns true if Country2 is valid
	 * @public
	 */
	validCountry2() {
		try {
			const v = this.country2;
			return (v && _isValidType('ref', v));
		} catch (err) {
			return false;
		}
	}

	/**
	 * @returns {string} returns email
	 * @public
	 */
	get email() {
		return this.email;
	}

	/**
	 * @param {string} value - the set value
	 * @returns {void}
	 * @public
	 */
	set email(value) {
		this.email = value;
	}

	/**
	 * @returns {boolean} returns true if Email is valid
	 * @public
	 */
	validEmail() {
		try {
			const v = this.email;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	/**
	 * @returns {string} returns firstName
	 * @public
	 */
	get firstName() {
		return this.firstName;
	}

	/**
	 * @param {string} value - the set value
	 * @returns {void}
	 * @public
	 */
	set firstName(value) {
		this.firstName = value;
	}

	/**
	 * @returns {boolean} returns true if FirstName is valid
	 * @public
	 */
	validFirstName() {
		try {
			const v = this.firstName;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	/**
	 * @returns {string} returns id
	 * @public
	 */
	get id() {
		return this.id;
	}

	/**
	 * @param {string} value - the set value
	 * @returns {void}
	 * @public
	 */
	set id(value) {
		this.id = value;
	}

	/**
	 * @returns {boolean} returns true if Id is valid
	 * @public
	 */
	validId() {
		try {
			const v = this.id;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	/**
	 * @returns {string} returns lastName
	 * @public
	 */
	get lastName() {
		return this.lastName;
	}

	/**
	 * @param {string} value - the set value
	 * @returns {void}
	 * @public
	 */
	set lastName(value) {
		this.lastName = value;
	}

	/**
	 * @returns {boolean} returns true if LastName is valid
	 * @public
	 */
	validLastName() {
		try {
			const v = this.lastName;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	/**
	 * @returns {string} returns line1
	 * @public
	 */
	get line1() {
		return this.line1;
	}

	/**
	 * @param {string} value - the set value
	 * @returns {void}
	 * @public
	 */
	set line1(value) {
		this.line1 = value;
	}

	/**
	 * @returns {boolean} returns true if Line1 is valid
	 * @public
	 */
	validLine1() {
		try {
			const v = this.line1;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	/**
	 * @returns {string} returns phone
	 * @public
	 */
	get phone() {
		return this.phone;
	}

	/**
	 * @param {string} value - the set value
	 * @returns {void}
	 * @public
	 */
	set phone(value) {
		this.phone = value;
	}

	/**
	 * @returns {boolean} returns true if Phone is valid
	 * @public
	 */
	validPhone() {
		try {
			const v = this.phone;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	/**
	 * @returns {string} returns postalCode
	 * @public
	 */
	get postalCode() {
		return this.postalCode;
	}

	/**
	 * @param {string} value - the set value
	 * @returns {void}
	 * @public
	 */
	set postalCode(value) {
		this.postalCode = value;
	}

	/**
	 * @returns {boolean} returns true if PostalCode is valid
	 * @public
	 */
	validPostalCode() {
		try {
			const v = this.postalCode;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	/**
	 * @returns {string} returns streetName
	 * @public
	 */
	get streetName() {
		return this.streetName;
	}

	/**
	 * @param {string} value - the set value
	 * @returns {void}
	 * @public
	 */
	set streetName(value) {
		this.streetName = value;
	}

	/**
	 * @returns {boolean} returns true if StreetName is valid
	 * @public
	 */
	validStreetName() {
		try {
			const v = this.streetName;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	/**
	 * @returns {string} returns streetNumber
	 * @public
	 */
	get streetNumber() {
		return this.streetNumber;
	}

	/**
	 * @param {string} value - the set value
	 * @returns {void}
	 * @public
	 */
	set streetNumber(value) {
		this.streetNumber = value;
	}

	/**
	 * @returns {boolean} returns true if StreetNumber is valid
	 * @public
	 */
	validStreetNumber() {
		try {
			const v = this.streetNumber;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	/**
	 * @returns {string} returns title
	 * @public
	 */
	get title() {
		return this.title;
	}

	/**
	 * @param {string} value - the set value
	 * @returns {void}
	 * @public
	 */
	set title(value) {
		this.title = value;
	}

	/**
	 * @returns {boolean} returns true if Title is valid
	 * @public
	 */
	validTitle() {
		try {
			const v = this.title;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	/**
	 * @returns {string} returns titleCode
	 * @public
	 */
	get titleCode() {
		return this.titleCode;
	}

	/**
	 * @param {string} value - the set value
	 * @returns {void}
	 * @public
	 */
	set titleCode(value) {
		this.titleCode = value;
	}

	/**
	 * @returns {boolean} returns true if TitleCode is valid
	 * @public
	 */
	validTitleCode() {
		try {
			const v = this.titleCode;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	/**
	 * @returns {string} returns town
	 * @public
	 */
	get town() {
		return this.town;
	}

	/**
	 * @param {string} value - the set value
	 * @returns {void}
	 * @public
	 */
	set town(value) {
		this.town = value;
	}

	/**
	 * @returns {boolean} returns true if Town is valid
	 * @public
	 */
	validTown() {
		try {
			const v = this.town;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

	/**
	 * @returns {Array<Object>} returns list
	 * @public
	 */
	get list() {
		return this.list;
	}

	/**
	 * @param {Array<Object>} value - the set value
	 * @returns {void}
	 * @public
	 */
	set list(value) {
		this.list = value;
	}

	/**
	 * @returns {boolean} returns true if List is valid
	 * @public
	 */
	validList() {
		try {
			const v = this.list;
			return (v && _isValidType('Array', v));
		} catch (err) {
			return false;
		}
	}

	/**
	 * @returns {Array<CountryWsDTO>} returns items
	 * @public
	 */
	get items() {
		return this.items;
	}

	/**
	 * @param {Array<CountryWsDTO>} value - the set value
	 * @returns {void}
	 * @public
	 */
	set items(value) {
		this.items = value;
	}

	/**
	 * @returns {boolean} returns true if Items is valid
	 * @public
	 */
	validItems() {
		try {
			const v = this.items;
			return (v && _isValidType('Array', v));
		} catch (err) {
			return false;
		}
	}

}

export default AddressWsDTO;
