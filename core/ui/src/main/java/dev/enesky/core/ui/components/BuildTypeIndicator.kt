package dev.enesky.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Created by Enes Kamil YILMAZ on 24/02/2025
 */

@Composable
fun BoxScope.BuildTypeIndicator(
    modifier: Modifier = Modifier,
    text: String,
) {
    Box(
        modifier = modifier
            .align(Alignment.BottomStart)
            .padding(top = 32.dp)
    ) {
        Text(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.error,
                    shape = RoundedCornerShape(
                        topEnd = 8.dp,
                        bottomEnd = 8.dp,
                    ),
                )
                .padding(horizontal = 8.dp),
            text = text,
            color = MaterialTheme.colorScheme.onError,
            style = MaterialTheme.typography.bodySmall,
        )
    }

}

@Preview
@Composable
fun BuildTypeIndicatorPreview() {
    Box {
         BuildTypeIndicator(
            text = "Debug"
        )
    }
}
