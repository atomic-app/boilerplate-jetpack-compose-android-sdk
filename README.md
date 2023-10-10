# boilerplate-jetpack-compose-android-sdk
This is a boiler-plate app that can be forked to get you started with the Atomic SDK for Android and jetpack compose.

The code is based around the documentation and designed to get you up and running as quickly as possible, not necessarily as best practice.

The app won't run out of the box, you will need to add your own values to BoilerPlateViewModel in the companion object.

```
companion object {
        /** Hardcoded for boiler plate...
         * In your own app you to get these:
         * Open the Atomic Workbench, and navigate to the Configuration area.
         * Under the 'SDK' header, your API host is in the 'API Host' section,
         * your API key is in the 'API Keys' section,
         * and your environment ID is at the top of the page under 'Environment ID'. */

        const val containerId = ""
        const val apiHost = ""
        const val apiKey = "r"
        const val environmentId = ""

        /* This is the hard coded JWT for the purposes of this app. You would normally get via
         your authentication process */
        const val requestTokenStr = ""
```

Instructions are as follows

    - Open the [Atomic Workbench] (https://workbench.atomic.io/), and navigate to the Configuration area.
    - Under the 'SDK' header, your API host is in the 'API Host' section, your API key is in the 'API Keys' section
    - your environment ID is at the top of the page under 'Environment ID'.


## Generating JWT
Before generating JWT, you will need to get the user id of your test account.
You can find the the user IDs from [Atomic Workbench Test Accounts secton](https://workbench.atomic.io) 
by navigating to the Configuration area. Under 'Test Accounts', look for the test account you need and click the 'Copy ID' link.
Paste the user Id on the sub field. Please follow the complete instruction [here](https://documentation.atomic.io/sdks/auth-SDK) on how to 
generate JWT.

```
const fs = require("fs-extra");
const jwt = require("jsonwebtoken");
const path = require("path");

const PRIVATE_KEY_PATH = path.join(__dirname,"jwtRS512.key")
const privateKey = fs.readFileSync(PRIVATE_KEY_PATH, "utf8");

const token = jwt.sign(
    {
        sub: "<paste your user id here>"
    },
    privateKey,
    {
        expiresIn: "3d",
        algorithm: "RS512",
    }
);
console.log("your generated JWT:", token)
```


## Runtime Variables

For an example of how to set runtime variables in your code, see `MainActivity` and the code starting
at `applyHandlers`

## Notifications

An example of how to receive and create in app notifications can be found in `BoilerplateFirebaseMessaging`
You will have to supply your own google-services.json file from your own app on the Firebase console.
Information for the whole process can be found [here](https://documentation.atomic.io/sdks/android#notifications)
including the Firebase setup documentation. Also make sure that you emulator or device has permission to accept push notifications
from the boiler plate app.
