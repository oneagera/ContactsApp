package com.markus.contactsapp.feature_contact.presentation.util

sealed class Screen(val route: String) {
    data object ContactScreen: Screen("contacts_screen")
    data object AddEditContactScreen: Screen("add_edit_contact_screen")
}