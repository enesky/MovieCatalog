package dev.enesky.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.enesky.core.ui.R
import dev.enesky.core.ui.annotation.PreviewUiMode
import dev.enesky.core.ui.theme.MovieCatalogTheme

@Composable
fun MovieDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmAction: () -> Unit,
    title: String,
    message: String,
    confirmButtonText: String = stringResource(R.string.label_ok),
    dismissButtonText: String = stringResource(R.string.label_cancel),
    icon: @Composable (() -> Unit)? = null
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            MovieDialogContent(
                onDismissRequest = onDismissRequest,
                onConfirmAction = onConfirmAction,
                title = title,
                message = message,
                confirmButtonText = confirmButtonText,
                dismissButtonText = dismissButtonText,
                icon = icon
            )
        }
    }
}

@Composable
fun MovieDialogContent(
    onDismissRequest: () -> Unit,
    onConfirmAction: () -> Unit,
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    confirmButtonText: String = "OK",
    dismissButtonText: String = "Cancel",
    icon: @Composable (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(MovieCatalogTheme.spacing.medium),
        shape = MovieCatalogTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(MovieCatalogTheme.spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            icon?.let {
                Box(
                    modifier = Modifier.padding(bottom = MovieCatalogTheme.spacing.medium)
                ) { it() }
            }
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = MovieCatalogTheme.spacing.medium)
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = MovieCatalogTheme.spacing.large)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(
                    onClick = onDismissRequest,
                    modifier = Modifier.padding(end = MovieCatalogTheme.spacing.xSmall)
                ) {
                    Text(
                        dismissButtonText,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                TextButton(
                    onClick = {
                        onConfirmAction()
                        onDismissRequest()
                    },
                    modifier = Modifier.padding(end = MovieCatalogTheme.spacing.xSmall)
                ) {
                    Text(
                        confirmButtonText,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@PreviewUiMode
@Composable
private fun MovieDialogPreview() {
    val showDialog = remember { mutableStateOf(true) }

    MovieCatalogTheme {
        MovieDialog(
            showDialog = showDialog.value,
            onDismissRequest = { showDialog.value = false },
            onConfirmAction = { /* Confirm action */ },
            title = "Custom Dialog",
            message = "This is a custom dialog example. You can modify this component according to your needs.",
            icon = {
                Icon(
                    modifier = Modifier.size(MovieCatalogTheme.spacing.largeIconSize),
                    imageVector = Icons.Default.Warning,
                    tint = MovieCatalogTheme.colors.red,
                    contentDescription = "Info icon",
                )
            }
        )
    }
}
