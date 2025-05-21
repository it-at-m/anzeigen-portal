# Frontend

The frontend was built using [Vue.js](https://vuejs.org/).
The frontend component of RefArch was utilized for this purpose.

## Prerequisites

- Node.js 22 LTS (`22.11.x` - `22.x.x`)
- Docker (for AppSwitcher)

## Structure

The frontend component is structured into different folders with distinct functions.

### API

This folder contains all functionalities related to API requests to the backend.
This includes the client generated from the Open-Api specification.
The client can be easily regenerated with the following command:

```shell
openapi-generator-cli generate
```

No additional library was used for the generation.
The generated client is based on standard fetch functions.

Additionally, this folder contains functions for requesting:

- `userinfo` endpoint of the SSO
- `info` endpoint of the Spring backend
- `healthcheck` endpoint to check the status of the ApiGateway

### Components

This folder contains all components created in Vue.js for the web application.
...

### Composables

Vue.js describes reusable components as `composables`.
These are stored in this folder. Generally, all API requests are wrapped with composables.
The approach was inspired by `TenStack`.
Each composable provides a call function and dynamic refs to the received data, any errors that may have occurred, and whether the current request is still running.
They are categorized according to the respective controllers in the backend.

The composable `useSnackbar` wraps the use of the built-in snackbar.
To send a message to the user, simply call the composable:

```typescript
snackbar.sendMessage({
  level: Levels.INFO,
  message: "Some Msg",
});
```

Additionally, all event buses used here are registered in `useEventBus`.
An event bus can thus be easily imported and used as a constant.

With `useDownloadFile`, files can be downloaded via browser functionality.
This includes, for example, the terms and conditions file or attached files in an ad.

### Locales

The web application uses `i18n`, which is actually intended for localization or offering different languages.
Currently, only one language (German) is offered.
The tool (`i18n`) serves to outsource all texts of the application into a common file (as long as none were forgotten).

### Plugins

This folder contains all plugins used in Vue.js, each configured in individual files.
The routing information is described in `router`, as well as the theme used by Vuetify in `vuetify`.

For the aforementioned localization and future-proofing, the configuration is in `i18n`.
This also includes the ability to dynamically change the language throughout the application via function.

### Stores

Diese Web-Application nutzt eine State-Mangement (Pinia Store).
Alle verwendeten Stores befinden sich in verschiedenen Dateien.
Die Stores beinhalten verschiedene Zugriffsmöglichkeiten auf die darin enthaltenen Daten.
Manche Stores werden zudem auf "besondere" Art und Weise befüllt mit Daten - hierzu gehören z.B. die Anzeigen selber.
