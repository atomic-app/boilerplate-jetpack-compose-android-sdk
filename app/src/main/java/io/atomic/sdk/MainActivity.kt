package io.atomic.sdk

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.ViewModelProvider
import com.atomic.actioncards.feed.data.model.AACCardInstance
import io.atomic.android_compose_boilerplate.R
import io.atomic.sdk.components.CardDetails
import java.text.DateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BoilerPlateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_layout)

        // Reference the viewModel
        viewModel = ViewModelProvider(this)[BoilerPlateViewModel::class.java]

        // Initialize the container
        viewModel.initContainer()

        // Start the stream container
        viewModel.streamContainer?.start(R.id.stream_container, supportFragmentManager)


        //Reference the composeView element from xml layout
        val composeView = findViewById<ComposeView>(R.id.compose_view)
        //This is where you set your jetpack compose compose contents
        composeView.apply {
            //Dispose of the Composition when the view's LifecycleOwner is destroyed
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )

            setContent {
                // In Compose world
                // Normally this is where you put your compose nav controller configurations
                val toast = Toast.makeText(
                    LocalContext.current,
                    "You have just interacted with a composable",
                    Toast.LENGTH_LONG
                )

                Column {
                    Row() {
                        MaterialTheme {
                          CardDetails(
                              title = "Composable",
                              description = "This is a composable component" ,
                              onClick = { toast.show() },
                              buttonLabel = "Click me"
                          )
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel.streamContainer?.destroy(supportFragmentManager)
    }

    override fun onResume() {
        super.onResume()
        applyHandlers()
    }

    override fun onPause() {
        super.onPause()
        applyHandlers(true)
    }


    /** This is currently only setting runtime variables handler, but you could also setup
     * any handlers for link and submit buttons in here too */
    private fun applyHandlers(shallReset: Boolean = false){
        if (shallReset) {
            viewModel.streamContainer?.cardDidRequestRunTimeVariablesHandler = null
        }

        viewModel.streamContainer?.cardDidRequestRunTimeVariablesHandler = { cards, done ->
            cardDidRequestRunTimeVariablesHandler(cards, done)
        }
    }

    /** here is where we apply runtime variables to a card.
     * Action any you have in your cards here. */
    private fun cardDidRequestRunTimeVariablesHandler(cards: List<AACCardInstance>, done: (cardsWithResolvedVariables: List<AACCardInstance>) -> Unit) {

        for (card in cards) {
            val shortDf: DateFormat = DateFormat.getDateInstance(DateFormat.SHORT)
            val today = Calendar.getInstance().time
            val formattedShortDate = shortDf.format(today)
            val customerName = "Rommel Suarez"

            // Resolve the below runtime variables
            card.resolveVariableWithNameAndValue("customer_name", customerName)
            card.resolveVariableWithNameAndValue("birthdate", formattedShortDate)
        }

        done(cards)
    }
}