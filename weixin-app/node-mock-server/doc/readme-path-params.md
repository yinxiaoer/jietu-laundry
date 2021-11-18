# Dynamic path params in mock data

For example call GET "/products/superProductCode/?currentPage=2"
[Config in mock response](/demo/rest/products/%23%7BproductCode%7D/GET/mock/request-data.json#L4)
Response will be:
```
{
	"productCode": "superProductCode"
	...
}
```
