# Jetpack Compose SDK
This is a boiler plate app that can be forked to get you started with the Atomic **Jetpack compose SDK** for Android.

Integrating our stream container into Jetpack Compose projects is seamless, treating it as any other Composable component. 
With our latest Jetpack Compose SDK, the **ComposableStreamContainer** streamlines integration. However, to make it functional, 
we still need to instantiate a **StreamContainer** within our ViewModel. 
This instance must then be passed into the **ComposableStreamContainer**, which serves as a wrapper for the **StreamContainer**.

The following code snippet demonstrates how to display the stream container, occupying the entire screen.

```
Surface(modifier = Modifier
                .fillMaxWidth()) {
   MaterialTheme {
       viewModel?.streamContainer?.let {
            ComposableStreamContainer(modifier = Modifier.fillMaxSize(), streamContainer = it)
       }         
   }
  }
)
```
![test](Screenshot-fullscreen.png)

The following code snippet demonstrates how to display the stream container together with other Composables.
```

            Surface(modifier = Modifier
                .fillMaxWidth()) {
                MaterialTheme {

                    val toast = Toast.makeText(
                        LocalContext.current,
                        "You have just interacted with a composable",
                        Toast.LENGTH_LONG)

                    Column {
                        Row {
                            CardDetails(
                                modifier = Modifier.padding(50.dp),
                                title = resources.getString(R.string.title),
                                description = resources.getString(R.string.description),
                                onClick = { toast.show() },
                                buttonLabel = resources.getString(R.string.button_label)
                            )
                        }
                        Row() {
                            // Pass the stream container into the ComposableStreamContainer
                            viewModel?.streamContainer?.let {
                                ComposableStreamContainer(
                                    modifier = Modifier.fillMaxSize(),
                                    streamContainer = it
                                )
                            }
                        }
                    }
                }
            }

```
![test](Screenshot.png)

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
        const val apiKey = ""
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
const PRIVATE_KEY_PATH = path.join(__dirname,"<this is the path to your jwt private key")

const privateKey = await fs.readFile(PRIVATE_KEY_PATH, "utf8");
const id = "unique identifier for your user copied from workbench test account";
const token = jwt.sign({ sub: id }, privateKey, {
  expiresIn: "3d",
  algorithm: "RS512",
});

```


## Runtime Variables

For an example of how to set runtime variables in your code, see `MainActivity` and the code starting
at `applyHandlers`. This piece of code is updating the runtime variables defined from card.

```
/** here is where we apply runtime variables to a card.
     * Action any you have in your cards here. */
    private fun cardDidRequestRunTimeVariablesHandler(cards: List<AACCardInstance>, done: (cardsWithResolvedVariables: List<AACCardInstance>) -> Unit) {

        for (card in cards) {
            val shortDf: DateFormat = DateFormat.getDateInstance(DateFormat.SHORT)
            val today = Calendar.getInstance().time
            val formattedShortDate = shortDf.format(today)
            val customerName = "Atomic guy!!!"

            // Resolve the below runtime variables
            card.resolveVariableWithNameAndValue("customer_name", customerName)
            card.resolveVariableWithNameAndValue("birthdate", formattedShortDate)
        }

        done(cards)
    }

```
## Notifications

An example of how to receive and create in app notifications can be found in `BoilerplateFirebaseMessaging`
You will have to supply your own google-services.json file from your own app on the Firebase console.
Information for the whole process can be found [here](https://documentation.atomic.io/sdks/android#notifications)
including the Firebase setup documentation. Also make sure that your emulator or device has permission to accept push notifications
from the boiler plate app.
