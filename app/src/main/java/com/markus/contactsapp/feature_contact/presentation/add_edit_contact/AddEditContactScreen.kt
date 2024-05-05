package com.markus.contactsapp.feature_contact.presentation.add_edit_contact

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.markus.contactsapp.feature_contact.domain.model.Contact
import com.markus.contactsapp.feature_contact.presentation.add_edit_contact.componets.ExitDialog
import com.markus.contactsapp.feature_contact.presentation.add_edit_contact.componets.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditContactScreen(
    modifier: Modifier = Modifier.padding(16.dp),
    navController: NavController,
    contactLabel: String,
    viewModel: AddEditContactViewModel = hiltViewModel(),
) {
    val firstNameState = viewModel.contactFirstName.value
    val lastNameState = viewModel.contactLastName.value
    val phone1State = viewModel.contactPhone1.value
    val phone2State = viewModel.contactPhone2.value
    val emailState = viewModel.contactEmail.value
    val nameState = viewModel.contactName.value


    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val state = viewModel.state.value

    val contact = Contact(
        firstName = firstNameState.text,
        lastName = lastNameState.text,
        phone1 = phone1State.text,
        phone2 = phone2State.text,
        email = emailState.text,
        label1 = "",
        label2 = "",
        label3 = ""
    )
    val content =
        Column {
            Contact.contactLabels.forEach { label ->
                //val label = label
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .shadow(15.dp, CircleShape)
                        .clip(CircleShape)
                        .clickable {
                            scope.launch {
                            }
                            viewModel.onEvent(AddEditContactEvent.ChangeLabel(label))
                        }
                )
            }
        }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditContactViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is AddEditContactViewModel.UiEvent.SaveContact -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = {
                            if (
                                contact.id == null &&
                                firstNameState.text.isBlank() &&
                                lastNameState.text.isBlank() &&
                                phone1State.text.isBlank() &&
                                phone2State.text.isBlank() &&
                                emailState.text.isBlank()
                            ) {
                                navController.navigateUp()
                            } else if (
                                contact.id != null &&
                                firstNameState.text.isBlank() &&
                                lastNameState.text.isBlank() &&
                                phone1State.text.isBlank() &&
                                phone2State.text.isBlank() &&
                                emailState.text.isBlank()
                            ) {
                                viewModel.onEvent(AddEditContactEvent.Exit)
                            } else {
                                viewModel.onEvent(AddEditContactEvent.Exit)
                            }
                        }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Cancel,
                                contentDescription = "exit"
                            )
                        }
                        AnimatedVisibility(
                            visible = state.isExitDialogVisible,
                            enter = fadeIn()
                        ) {
                            ExitDialog(
                                onDismiss = {
                                    navController.navigateUp()
                                },
                                onConfirm = { viewModel.onEvent(AddEditContactEvent.SaveContact) },
                                dialogText = "Your changes may not be saved"
                            )
                        }



                        Text(text = "Edit Contact")

                        Button(
                            onClick = {
                                viewModel.onEvent(AddEditContactEvent.SaveContact)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(text = "Save")
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .then(modifier),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TransparentHintTextField(
                text = firstNameState.text,
                hint = firstNameState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditContactEvent.EnteredFirstName(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditContactEvent.ChangeFirstNameFocus(it))
                },
                isHintVisible = firstNameState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.titleLarge
            )

            TransparentHintTextField(
                text = lastNameState.text,
                hint = lastNameState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditContactEvent.EnteredLastName(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditContactEvent.ChangeLastNameFocus(it))
                },
                isHintVisible = lastNameState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.titleLarge
            )

            TransparentHintTextField(
                text = phone1State.text,
                hint = phone1State.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditContactEvent.EnteredPhone1(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditContactEvent.ChangePhone1Focus(it))
                },
                isHintVisible = phone1State.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.titleLarge
            )

            TransparentHintTextField(
                text = phone2State.text,
                hint = phone2State.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditContactEvent.EnteredPhone2(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditContactEvent.ChangePhone2Focus(it))
                },
                isHintVisible = phone2State.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.titleLarge
            )

            TransparentHintTextField(
                text = emailState.text,
                hint = emailState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditContactEvent.EnteredEmail(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditContactEvent.ChangeEmailFocus(it))
                },
                isHintVisible = emailState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.titleLarge
            )
        }
    }
}