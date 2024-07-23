package com.pthw.food.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pthw.food.theme.Dimens
import com.pthw.food.theme.Shapes

/**
 * Created by P.T.H.W on 23/07/2024.
 */

@Composable
fun <T> RadioSelectionDialog(
    items: List<T>,
    onDismissRequest: (item: T?) -> Unit,
    content: @Composable (item: T) -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest(null) }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = Shapes.medium,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = Dimens.MARGIN_MEDIUM_2),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items.forEach {
                    content(it)
                }
            }
        }
    }
}