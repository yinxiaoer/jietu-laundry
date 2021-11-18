
module.exports = {

	image: function (url, alt) {

		url = url || 'http://lorempixel.com/100/100/people';
		alt = alt || 'Alt';

		return JSON.stringify({
			url: url,
			alt: alt
		});
	}

};