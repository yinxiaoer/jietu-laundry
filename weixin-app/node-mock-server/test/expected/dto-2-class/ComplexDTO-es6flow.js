// @flow

import CountryWsDTO from './CountryWsDTO.class';
import ImageDTO from './ImageDTO.class';

const _isValidType = (type: string, value: any): boolean => {
	switch (type) {
		case 'Array': return (value instanceof Array);
		default: return (typeof value === type.toLowerCase());
	}
};
const _array = (arr: Array<any>): Array<any> => arr || [];

class ComplexDTO {

	list: Array<Object>;
	items: Array<ImageDTO>;
	parameters: Object;
	parametersAdditional: string;

	constructor(complexDTO: ComplexDTO) {

		complexDTO = complexDTO || {};
		complexDTO.parameters = complexDTO.parameters || {};

		this.list = complexDTO.list;
		this.items = _array(complexDTO.items).map(this.mapItems);
		this.parametersAdditional = complexDTO.parameters.additional;
	}

	mapItems = (imageDTO: ImageDTO): ImageDTO => new ImageDTO(imageDTO);

	get list(): Array<Object> {
		return this.list;
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

	get items(): Array<ImageDTO> {
		return this.items;
	}

	set items(value: Array<ImageDTO>) {
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

	get parametersAdditional(): string {
		return this.parametersAdditional;
	}

	set parametersAdditional(value: string) {
		this.parametersAdditional = value;
	}

	validParametersAdditional(): boolean {
		try {
			const v:any = this.parameters.additional;
			return (v && _isValidType('string', v));
		} catch (err) {
			return false;
		}
	}
}

export default ComplexDTO;
