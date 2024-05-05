package com.markus.contactsapp.feature_contact.presentation.add_edit_contact

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markus.contactsapp.feature_contact.domain.model.Contact
import com.markus.contactsapp.feature_contact.domain.model.InvalidContactException
import com.markus.contactsapp.feature_contact.domain.use_case.ContactUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditContactViewModel @Inject constructor(
    val contactUseCases: ContactUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _state = mutableStateOf(ContactNameState())
    val state: State<ContactNameState> = _state

    val visibleExitDialog = mutableStateOf("")

    private val _contactFirstName = mutableStateOf(
        ContactTextFieldState(
            hint = "First Name"
        )
    )
    val contactFirstName: State<ContactTextFieldState> = _contactFirstName

    private val _contactLastName = mutableStateOf(
        ContactTextFieldState(
            hint = "Last Name"
        )
    )
    val contactLastName: State<ContactTextFieldState> = _contactLastName

    private val _contactPhone1 = mutableStateOf(
        ContactTextFieldState(
            hint = "Phone"
        )
    )
    val contactPhone1: State<ContactTextFieldState> = _contactPhone1

    private val _contactPhone2 = mutableStateOf(
        ContactTextFieldState(
            hint = "Phone"
        )
    )
    val contactPhone2: State<ContactTextFieldState> = _contactPhone2

    private val _contactEmail = mutableStateOf(
        ContactTextFieldState(
            hint = "Email"
        )
    )
    val contactEmail: State<ContactTextFieldState> = _contactEmail

    private val _contactLabel1 = mutableStateOf(Contact.contactLabels.toString())
    val contactLabel1: State<String> = _contactLabel1

    private val _contactLabel2 = mutableStateOf(Contact.contactLabels.toString())
    val contactLabel2: State<String> = _contactLabel2

    private val _contactLabel3 = mutableStateOf(Contact.contactLabels.toString())
    val contactLabel3: State<String> = _contactLabel3

    private val _contactName = mutableStateOf(
        ContactNameState()
    )
    val contactName: State<ContactNameState> = _contactName

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentContactId: Int? = null

    init {
        savedStateHandle.get<Int>("contactId")?.let { contactId ->
            if (contactId != -1) {
                viewModelScope.launch {
                    contactUseCases.getContact(contactId)?.also { contact ->
                        currentContactId = contact.id
                        _contactFirstName.value = contactFirstName.value.copy(
                            text = contact.firstName,
                            isHintVisible = contact.firstName.isBlank()
                        )
                        _contactLastName.value = contactLastName.value.copy(
                            text = contact.lastName,
                            isHintVisible = contact.lastName.isBlank()
                        )
                        _contactPhone1.value = contactPhone1.value.copy(
                            text = contact.phone1,
                            isHintVisible = contact.phone1.isBlank()
                        )
                        _contactPhone2.value = contactPhone2.value.copy(
                            text = contact.phone2,
                            isHintVisible = contact.phone2.isBlank()
                        )
                        _contactEmail.value = contactEmail.value.copy(
                            text = contact.email,
                            isHintVisible = contact.email.isBlank()
                        )
                        _contactLabel1.value = contact.label1

                        _contactLabel2.value = contact.label2

                        _contactLabel3.value = contact.label3

                        _contactName.value = contactName.value.copy(
                            contactName = contact.name
                        )
                    }
                }
            }
        }
    }

    fun onExitResult(
        name: String
    ) {
        if (name.isNotBlank()) {
            visibleExitDialog
        }
    }

    fun onEvent(event: AddEditContactEvent) {
        when (event) {
            is AddEditContactEvent.EnteredFirstName -> {
                _contactFirstName.value = contactFirstName.value.copy(
                    text = event.value
                )
            }

            is AddEditContactEvent.ChangeFirstNameFocus -> {
                _contactFirstName.value = contactFirstName.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            contactFirstName.value.text.isBlank()
                )
            }

            is AddEditContactEvent.EnteredLastName -> {
                _contactLastName.value = contactLastName.value.copy(
                    text = event.value
                )
            }

            is AddEditContactEvent.ChangeLastNameFocus -> {
                _contactLastName.value = contactLastName.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            contactLastName.value.text.isBlank()
                )
            }

            is AddEditContactEvent.EnteredPhone1 -> {
                _contactPhone1.value = contactPhone1.value.copy(
                    text = event.value
                )
            }

            is AddEditContactEvent.ChangePhone1Focus -> {
                _contactPhone1.value = contactPhone1.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            contactPhone1.value.text.isBlank()
                )
            }

            is AddEditContactEvent.EnteredPhone2 -> {
                _contactPhone2.value = contactPhone2.value.copy(
                    text = event.value
                )
            }

            is AddEditContactEvent.ChangePhone2Focus -> {
                _contactPhone2.value = contactPhone2.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            contactPhone2.value.text.isBlank()
                )
            }

            is AddEditContactEvent.EnteredEmail -> {
                _contactEmail.value = contactEmail.value.copy(
                    text = event.value
                )
            }

            is AddEditContactEvent.ChangeEmailFocus -> {
                _contactEmail.value = contactEmail.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            contactEmail.value.text.isBlank()
                )
            }

            is AddEditContactEvent.SelectedLabel1 -> {
                _contactLabel1.value = contactLabel1.value
            }

            is AddEditContactEvent.SelectedLabel2 -> {
                _contactLabel2.value = contactLabel2.value
            }

            is AddEditContactEvent.SelectedLabel3 -> {
                _contactLabel3.value = contactLabel3.value
            }

            is AddEditContactEvent.SaveContact -> {
                viewModelScope.launch {
                    try {
                        contactUseCases.addContact(
                            Contact(
                                firstName = contactFirstName.value.text,
                                lastName = contactLastName.value.text,
                                label1 = contactLabel1.value,
                                phone1 = contactPhone1.value.text,
                                label2 = contactLabel2.value,
                                phone2 = contactPhone2.value.text,
                                label3 = contactLabel3.value,
                                email = contactEmail.value.text,
                                id = currentContactId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveContact)
                    } catch (e: InvalidContactException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save contact"
                            )
                        )
                    }
                }
            }

            is AddEditContactEvent.Exit -> {
                _state.value = state.value.copy(
                    isExitDialogVisible = !state.value.isExitDialogVisible
                )
            }

            is AddEditContactEvent.DeleteContact -> {
                viewModelScope.launch {
                    contactUseCases.deleteContact(event.contact)
                }
            }

            else -> {}
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data object SaveContact : UiEvent()
    }
}
