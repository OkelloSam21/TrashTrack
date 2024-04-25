package com.samuelokello.trashtrack.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samuelokello.trashtrack.ui.theme.TrashTrackTheme
import com.samuelokello.trashtrack.ui.theme.seed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WasteReportCard(
    buttonText: String,
    onButtonClick: () -> Unit,
    badgeCount: Int? = null
) {
    BadgedBox(
        badge = {
            if (badgeCount != null) {
                Badge(
                    containerColor = Color.Red,
                    contentColor = Color.White,
                    content = {
                        Text(text = badgeCount.toString())
                    },
                    modifier = Modifier
                        .offset(x = (-20).dp, y = (10).dp)
                        .size(32.dp)
                )
            } else {
                Box {}
            }
        },
    ) {
        Card(
            modifier = Modifier
//                .fillMaxWidth()
                .clickable { onButtonClick() }
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = seed,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.align(Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = buttonText,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun WasteReportCardPre() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        TrashTrackTheme {
            WasteReportCard(
                buttonText = "Report",
                onButtonClick= {},
//            badgeCount = 5
            )
        }
    }

    
}