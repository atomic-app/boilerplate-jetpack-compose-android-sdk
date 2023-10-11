package io.atomic.sdk.components

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy

class ComposeFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        addView(
            ComposeView(context).apply {

                setViewCompositionStrategy(
                    ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
                )
                setContent {
                    val toast = Toast.makeText(
                        LocalContext.current,
                        "You have just interacted with a composable",
                        Toast.LENGTH_LONG
                    )

                    Column {
                        Row() {
                            MaterialTheme {
                                CardDetails(
                                    title = "Composable inside a FrameLayout",
                                    description = "This is a sample code where you " +
                                            "can create a composable which act as a normal view." +
                                            "You can add this to your XML layout like a regular FrameLayout",
                                    onClick = { toast.show() },
                                    buttonLabel = "Click me"
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}