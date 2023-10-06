package io.atomic.android_compose_boilerplate

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
        const val requestTokenStr = "eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJmNjg2ZTZkZi1jYWY4LTUzNTQtYmJhYS1mYzcxYThiZWE0MGUiLCJpYXQiOjE2OTY1NTE3MDEsImV4cCI6MTY5NjgxMDkwMX0.hSpn3451a33RDhtQgtJVCFFhEMtDU54TC6aTyvDrgjkkBivGiJheiwn8Yn5z51oFyVh5W2mmpx6Rj2mEq7TnFgxhQLvlQa65hdYdj4uf9j9KFxEPA76iVHHYmys7Dp5ZMs8dlHDRTKUdLZPx8-pv9TEMTGZMeNDn72RDG4NYK712EGinrEsSPccJdcwXYLD7PYOTl1np8xWxuR3--7GIzHrZuAqbmTuBWlQQKJ1gIcmR54GgzVUyVdtep7QgLsBkB5-kqEi9e8IH8zXoE5lBvHwHVZnIh_0MMItX1uv2hZTri6oS_kLftBXlhzT7tmYKUmy8Vv6mfOSmMc75DnWRY9hmmaCCJTTbBriWQz2RdRD8q2Gd5omMsguwer1bi79QD6Cfwjf8etdzswbbjHLO5cJSomz_NQ08Q3sOt8p1FSRzsWS0xP95L1g-E70rsvt-FEx6yIjDUbgdKez5f_QtxahDtNdBQ1wxRQYCyLkPqz8jZg9ZH1OiJoqvRLLKTp4A2EJc3ipobdmDvcEprejxwjIriInr_zlVpNzPxFL0mPz4e7hTZkItBPIBKFK07OQWm3x1qDdZ2yKxzl9hVIEAc_jygnioYV5EEYPKsoJFQDNfAazgizKP5Z47PMO7TYF3OQEnbDqNabyZ0hG1ESjKHO67r92fU5EtFKa7O9J3J84"
    }
}
