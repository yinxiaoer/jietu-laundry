
import CountryWsDTO from './CountryWsDTO.class';
import ImageDTO from './ImageDTO.class';

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
 * @constructor complexDTO
 */
class ComplexDTO {

	/**
	 * @param {Object} complexDTO - initial data
	 * @returns {void}
	 * @public
	 */
	constructor(complexDTO) {

		complexDTO = complexDTO || {};
		complexDTO.parameters = complexDTO.parameters || {};

		this.list = complexDTO.list;
		this.items = _array(complexDTO.items).map((imageDTO) => new ImageDTO(imageDTO));
		this.parametersAdditional = complexDTO.parameters.additional;
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
	 * @returns {Array<ImageDTO>} returns items
	 * @public
	 */
	get items() {
		return this.items;
	}

	/**
	 * @param {Array<ImageDTO>} value - the set value
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

	/**
	 * @returns {string} returns parametersAdditional
	 * @public
	 */
	get parametersAdditional() {
		return this.parametersAdditional;
	}

	/**
	 * @param {string} value - the set value
	 * @returns {void}
	 * @public
	 */
	set parametersAdditional(value) {
		this.parametersAdditional = value;
	}

	/**
	 * @returns {boolean} returns true if ParametersAdditional is valid
	 * @public
	 */
	validParametersAdditional() {
		try {
			const v = this.parameters.additional;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}

}

export default ComplexDTO;
