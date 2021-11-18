
module.exports = {

	price: function (price, currency) {

		price = price || 0;
		currency = currency || '€';

		return JSON.stringify({
			currency: currency,
			price: {
				value: price
			}
		});
	}

};