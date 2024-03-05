package io.atomic.sdk

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.atomic.actioncards.feed.data.model.AACCardInstance
import com.io.atomic.jetpackcomposesdk.sdk.ComposableStreamContainer
import io.atomic.sdk.components.CardDetails
import java.text.DateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private var viewModel: BoilerPlateViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            viewModel = viewModel<BoilerPlateViewModel>()

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
                            // Pass the streamcontainer into the ComposableStreamContainer
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
      }
    }

    override fun onDestroy() {
        super.onDestroy()

        // Since were still using fragmentManager under the hood,
        // we need to clean this up as well during destroy
        viewModel?.streamContainer?.destroy(supportFragmentManager)
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
                viewModel?.streamContainer?.cardDidRequestRunTimeVariablesHandler = null
            }

            viewModel?.streamContainer?.cardDidRequestRunTimeVariablesHandler = { cards, done ->
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
            val customerName = "Atomic guy!!!"

            // Resolve the below runtime variables
            card.resolveVariableWithNameAndValue("customer_name", customerName)
            card.resolveVariableWithNameAndValue("birthdate", formattedShortDate)
        }

        done(cards)
    }
}