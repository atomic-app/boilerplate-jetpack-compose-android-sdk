package io.atomic.sdk

import android.util.Log
import androidx.lifecycle.ViewModel
import com.atomic.actioncards.sdk.AACInterfaceStyle
import com.atomic.actioncards.sdk.AACSDK
import com.atomic.actioncards.sdk.AACStreamContainer
import com.atomic.actioncards.sdk.PresentationMode
import com.atomic.actioncards.sdk.VotingOption
import java.util.EnumSet

class BoilerPlateViewModel : ViewModel() {

    var streamContainer: AACStreamContainer? = null

    init {
        initContainer()
    }

    fun initContainer() {
        if (streamContainer != null) {
            return
        }
        configureSdk()
        streamContainer = AACStreamContainer.Companion.create(containerId)
        streamContainer?.apply {
            cardListTitle = "Demo Stream"
            cardVotingOptions = EnumSet.of(VotingOption.NotUseful, VotingOption.Useful)
            votingUsefulTitle = "Like"
            votingNotUsefulTitle = "Dislike"
            interfaceStyle = AACInterfaceStyle.AUTOMATIC
            presentationStyle = PresentationMode.WITH_ACTION_BUTTON
            cardListFooterMessage = "A Footer Message"
            cardListRefreshInterval = 30L
        }
        registerContainersForNotifications()
    }

    private fun configureSdk() {
        AACSDK.setApiHost(apiHost)
        AACSDK.setEnvironmentId(environmentId)
        AACSDK.setApiKey(apiKey)
        AACSDK.setSessionDelegate { onTokenReceived ->
            Log.d("Atomic Boilerplate", "onTokenReceived")
            onTokenReceived(requestTokenStr)
        }
    }

    /** Register any containers we want to receive notifications for. Also look in
     * BoilerplateFirebaseMessaging for how to intercept messages and send notifs */
    private fun registerContainersForNotifications() {
        val containers = arrayListOf(containerId)

        AACSDK.registerStreamContainersForNotifications(containers)

    }

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

        /* Hard coded for the purposes of this app. You would normally get via
         your authentication process */
        const val requestTokenStr = ""
    }
}
