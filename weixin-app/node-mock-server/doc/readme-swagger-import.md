
# Swagger import

Please make sure every required option is configured in options (See options.swaggerImport in the [options](./readme-options.md) object). Afterwards you can start the server, open the "swagger import" modal and click run import. After a few seconds the swagger import should be completed. Now reload the UI and check the new available API endpoints and DTO's.

It's recommended to run the ["validate all"](./readme-response-validation.md) script before running the "swagger import". After a successful import run the ["validate all"](./readme-response-validation.md) script again. It seems the API has an breaking change since last import, if something is not valid.

After an "swagger import" you will find a bunch of [mock-functions](./readme-mock-functions.md), if "options.swaggerImport.responseFuncPath" is defined. These "mock-functions" will be overwritten for each import, to make sure you can use them please copy the needed ones into your usual "mock-function" (options.funcPath) directory. Which means you shouldn't add the "options.swaggerImport.responseFuncPath" to the "options.funcPath" to prevent conflicts.

If you are behind a corporate proxy and want to reach an external swagger, you can configure a http-proxy-agent (see options.agent in the [options](./readme-options.md) object and a [usage example](./readme-usage-examples.md)).
