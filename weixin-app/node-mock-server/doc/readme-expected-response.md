# Expected response

- Use the UI to configure the expected response for each call
- Use the get param "_expected"
- Use the request header "_expected"
- If an dynamic path param is empty or in placeholder format an "400 bad request" will be the response
- To simulate responses with status like "500" you have to add an "error.json" into the response folder.
- To simulate responses with specific status like "400" you have to add an "error-401.json" into the response folder.
