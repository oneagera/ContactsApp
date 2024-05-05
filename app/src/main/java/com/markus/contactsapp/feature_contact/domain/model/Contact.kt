package com.markus.contactsapp.feature_contact.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    val firstName: String,
    val lastName: String,
    val label1: String,
    val phone1: String,
    val label2: String,
    val phone2: String,
    val label3: String,
    val email: String,
    val name: String = calculateName(firstName, lastName, phone1, phone2, email),
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val contactLabels = listOf("Mobile", "Work", "Home")
    }

    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            name,
            phone1,
            phone2,
            email,
            "{${firstName.first()}${lastName.first()}}",
            "{${firstName.first()} ${lastName.first()}}"
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
            //returns if there is any combination in our list of combinations that matches our query e.g. rk in Mark
        }
    }
}

fun calculateName( //could be a companion object but we can only have 1 per class
    firstName: String,
    lastName: String,
    phone1: String,
    phone2: String,
    email: String
): String {
    return when {
        firstName.isNotBlank() && lastName.isNotBlank() -> "$firstName $lastName"
        firstName.isNotBlank() -> firstName
        lastName.isNotBlank() -> lastName
        phone1.isNotBlank() -> phone1
        phone2.isNotBlank() -> phone2
        email.isNotBlank() -> email
        else -> ""
    }
}

class InvalidContactException(message: String) : Exception(message)