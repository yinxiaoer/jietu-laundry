'use strict';

Object.defineProperty(exports, "__esModule", {
    value: true
});
exports.default = Component({
    data: {
        current: 'homepage'
    },
    methods: {
        handleChange: function handleChange(_ref) {
            var detail = _ref.detail;

            this.setData({
                current: detail.key
            });
        }
    }
});
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbImZvb3Qud3hjIl0sIm5hbWVzIjpbImRhdGEiLCJjdXJyZW50IiwibWV0aG9kcyIsImhhbmRsZUNoYW5nZSIsImRldGFpbCIsImNvbnNvbGUiLCJsb2ciLCJzZXREYXRhIiwia2V5Il0sIm1hcHBpbmdzIjoiOzs7Ozs7QUFRSUEsVUFBTTtBQUNGQyxpQkFBUztBQURQLEs7QUFHTkMsYUFBUztBQUNMQyxvQkFESyw4QkFDcUI7QUFBQSxnQkFBVkMsTUFBVSxRQUFWQSxNQUFVOztBQUN0QkMsb0JBQVFDLEdBQVIsQ0FBWUYsTUFBWjtBQUNBLGlCQUFLRyxPQUFMLENBQWE7QUFDVE4seUJBQVNHLE9BQU9JO0FBRFAsYUFBYjtBQUdIO0FBTkkiLCJmaWxlIjoiZm9vdC53eGMiLCJzb3VyY2VzQ29udGVudCI6WyJleHBvcnQgZGVmYXVsdCB7XG4gICAgY29uZmlnOiB7XG4gICAgICAgIHVzaW5nQ29tcG9uZW50czoge1xuICAgICAgICAgICAgJ2NhcmQnOiAnLi4vLi4vY29tcG9uZW50cy9jYXJkL2luZGV4Lnd4YycsXG4gICAgICAgICAgICAvLyAnaS10YWItYmFyJzogJy4uLy4uL2NvbXBvbmVudHMvdGFiLWJhci9pbmRleC53eGMnLFxuICAgICAgICAgICAgLy8gJ2ktdGFiLWJhci1pdGVtJzogJy4uLy4uL2NvbXBvbmVudHMvdGFiLWJhci1pdGVtL2luZGV4Lnd4YycsXG4gICAgICAgIH0sXG4gICAgfSxcbiAgICBkYXRhOiB7XG4gICAgICAgIGN1cnJlbnQ6ICdob21lcGFnZSdcbiAgICB9LFxuICAgIG1ldGhvZHM6IHtcbiAgICAgICAgaGFuZGxlQ2hhbmdlICh7IGRldGFpbCB9KSB7XG4gICAgICAgICAgICBjb25zb2xlLmxvZyhkZXRhaWwpO1xuICAgICAgICAgICAgdGhpcy5zZXREYXRhKHtcbiAgICAgICAgICAgICAgICBjdXJyZW50OiBkZXRhaWwua2V5XG4gICAgICAgICAgICB9KTtcbiAgICAgICAgfVxuICAgIH0sXG59Il19
