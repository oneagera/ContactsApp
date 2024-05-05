package com.markus.contactsapp.feature_contact.presentation.add_edit_contact

data class ContactTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)