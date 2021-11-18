
# DTO to class converter

If there are DTO's (data transfer objects) you can use them to create classes from the given schema. Therefor you have to start the mock-server and open one "DTO detail" modal. Select for example "ECMAScript 2015 (ES6) + Flow" and press generate. A new browser tab should be open. Please take a look into the source code of the new tab. As you can see there is a javascript class representation of your schema.

These classes are helpful, for example if you are using MobX. As a result of the usage of these classes the structure in you client application is close to the API structure. What is healthy for the maintenance of you application.

In case of you need to change the given class template define the "options.customDTOToClassTemplate" config with the path to your custom template. After a restart of your mock-server you can select "Custom Template" in the "DTO detail" modal.

Default templates:
- [dto_es6.ejs](../src/templates/dto_es6.ejs)
- [dto_es6flow.ejs](../src/templates/dto_es6flow.ejs)
