package com.markus.contactsapp.feature_contact.presentation.add_edit_contact

import androidx.compose.ui.focus.FocusState
import com.markus.contactsapp.feature_contact.domain.model.Contact

sealed class AddEditContactEvent {
    data class EnteredFirstName(val value: String): AddEditContactEvent()
    data class ChangeFirstNameFocus(val focusState: FocusState): AddEditContactEvent()
    data class EnteredLastName(val value: String): AddEditContactEvent()
    data class ChangeLastNameFocus(val focusState: FocusState): AddEditContactEvent()
    data class EnteredPhone1(val value: String): AddEditContactEvent()
    data class ChangePhone1Focus(val focusState: FocusState): AddEditContactEvent()
    data class EnteredPhone2(val value: String): AddEditContactEvent()
    data class ChangePhone2Focus(val focusState: FocusState): AddEditContactEvent()
    data class EnteredEmail(val value: String): AddEditContactEvent()
    data class ChangeEmailFocus(val focusState: FocusState): AddEditContactEvent()
    data class SelectedLabel1(val value: String): AddEditContactEvent()
    data class ChangeLabel1Focus(val focusState: FocusState): AddEditContactEvent()
    data class SelectedLabel2(val value: String): AddEditContactEvent()
    data class ChangeLabel2Focus(val focusState: FocusState): AddEditContactEvent()
    data class SelectedLabel3(val value: String): AddEditContactEvent()
    data class ChangeLabel3Focus(val focusState: FocusState): AddEditContactEvent()
    data class ChangeLabel(val value: String): AddEditContactEvent()
    data object SaveContact: AddEditContactEvent()
    data object Exit: AddEditContactEvent()
    data class DeleteContact(val contact: Contact) : AddEditContactEvent()
}