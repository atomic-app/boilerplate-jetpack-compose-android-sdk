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

        const val containerId = "PLxpRAyb"
        const val apiHost = "https://50-2.client-api.atomic.io"
        const val apiKey = "rommel_api_key"
        const val environmentId = "qpP18R"

        /* Hard coded for the purposes of this app. You would normally get via
         your authentication process */
        const val requestTokenStr = "eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJmNjg2ZTZkZi1jYWY4LTUzNTQtYmJhYS1mYzcxYThiZWE0MGUiLCJpYXQiOjE2OTY4MTc4NjYsImV4cCI6MTY5NzA3NzA2Nn0.kZYqgbJ9PoThkgLFJZBtBIMa9r2q-3AO2Qp82oYa0Suf3Nna5v7fyoI8yKL0_F4vlHFi6ukC8OzeHt3h-d9zHoZEM3zCG6338Lm6RScMgveuR_1EA1W9vzOI9O4CcBz3YpLL5YExrRDmhg_DDtZksmOPs6nO2-zKFUl3tp2f9MNQYNfY4MhDE4rs-xI5L_LjBb2PZWJbBeN3p85WaiNrFbG2jMUTsOd2Bc4T_H6FQNcjuVFZ3CYRnyQoJbwQcekLF42wvMIxPhLqsXyPNOaoO5qvY6ccC_dYDe0cRCMyxpvxcNwm_zA0Rvfz3mNrgqOrERdM1IMSYt6f_l7hsOt09bQjuJfViejTTkR2hPP8RzwjzlGlXyI6fW8mqlsFoUn6BFgh46XmFVE7KOK6TwdhtacXzezV2fk9m-sgv-65Y-2F3y4uZk6fJl2blwqu0fs_azrX5YVu8ImLYmFdnjGLs86N-Xh5RazxOKocoZURfvxectfG_oScSakSEHbvopSEH-MUqUhKNO5-tKGdcV_EOQgqZ1szLJWaek1Wol1sG56YhNCtI2JGi6ruGEnaGCKhDl7z8ewFLfnUJGBSYJacXhfQLPcLlO6_Mw6DPnTA_ByQ8Y-ASp7ICDBAGBMF35ETSvhHBBVWH4v0ckolnQc67z4v6LYqv5d3RBiq3L-PX7Q"
    }
}
