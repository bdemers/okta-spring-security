okta-spring-security
====================

This repo is still a little raw, and will be cleaned up before merging to master.
Of interest:

- okta-spring-security-oauth is a Spring Boot starter use to configure an implicit flow access token validation.

- Example (is an example...)
  -  example is a Spring-Boot example backend (based off a previous examples)
  -  example/client is Angular front end

- Requires a custom Authorization Server

Build it:

From the root: `mvn install`

Run the backend:
``` bash
cd example/
mvn spring-boot:run -Drun.arguments=\
"--okta.oauth2.issuer=https://dev-123456.oktapreview.com/oauth2/ausar5cbq5TRooicu812,\
--okta.oauth2.audience=your-authorization-server-audience,\
--okta.oauth2.rolesClaim=custom-group-claim"
```

**Note:** `okta.oauth2.rolesClaim` defaults to `groups`, so in your custom Authorization Server define a custom claim:
- Name: 'groups'
- Value Type: 'Groups'
- Filter: Regex - `.*`

Run the example client:
``` bash
cd example/client
npm install
npm start
```

Browse to: http://localhost:4200

Up for review:
code in: okta-spring-security-oauth2


TODO: Needs tests (obviously)
