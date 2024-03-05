package io.atomic.sdk.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CardDetails (
               modifier: Modifier,
               title: String,
               description: String,
               onClick: () -> Unit,
               buttonLabel: String) {
    Card(modifier = modifier) {

        Column {
            Row (modifier = Modifier.padding(5.dp)){
                Text(title)
            }

            Row (modifier = Modifier.padding(10.dp)){
                Text(description)
            }

            Row (modifier = Modifier.padding(10.dp)){
                Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
                    Text(buttonLabel)
                }
            }
        }
    }
}