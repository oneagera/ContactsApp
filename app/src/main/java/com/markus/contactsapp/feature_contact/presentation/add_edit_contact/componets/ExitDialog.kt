package com.markus.contactsapp.feature_contact.presentation.add_edit_contact.componets

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ExitDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    dialogText: String
) {
    AlertDialog(
        text = { Text(text = dialogText) },
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                }
            ) {
                Text(text = "Save")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = "Discard")
            }
        }
    )
}