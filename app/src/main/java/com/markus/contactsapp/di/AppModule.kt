package com.markus.contactsapp.di

import android.app.Application
import androidx.room.Room
import com.markus.contactsapp.feature_contact.data.data_source.ContactDatabase
import com.markus.contactsapp.feature_contact.data.repository.ContactRepositoryImpl
import com.markus.contactsapp.feature_contact.domain.repository.ContactRepository
import com.markus.contactsapp.feature_contact.domain.use_case.AddContactUseCase
import com.markus.contactsapp.feature_contact.domain.use_case.ContactUseCases
import com.markus.contactsapp.feature_contact.domain.use_case.DeleteContactUseCase
import com.markus.contactsapp.feature_contact.domain.use_case.GetContactUseCase
import com.markus.contactsapp.feature_contact.domain.use_case.GetContactsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContactDatabase(app: Application): ContactDatabase {
        return Room.databaseBuilder(
            app,
            ContactDatabase::class.java,
            ContactDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideContactRepository(db: ContactDatabase): ContactRepository {
        return ContactRepositoryImpl(db.contactDao)
    }

    @Provides
    @Singleton
    fun provideContactUseCases(repository: ContactRepository): ContactUseCases {
        return ContactUseCases(
            getContacts = GetContactsUseCase(repository),
            deleteContact = DeleteContactUseCase(repository),
            addContact = AddContactUseCase(repository),
            getContact = GetContactUseCase(repository)
        )
    }
}