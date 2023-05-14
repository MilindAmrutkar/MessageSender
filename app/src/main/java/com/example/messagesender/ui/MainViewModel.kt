package com.example.messagesender.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messagesender.data.models.Contact
import com.example.messagesender.data.models.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.IOException

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>> = _contacts

    init {
        loadContacts()
    }

    private fun loadContacts() {
        viewModelScope.launch {
            _contacts.value = readContactsFromJson()
        }
    }

    private suspend fun readContactsFromJson(): List<Contact> {
        return withContext(Dispatchers.IO) {
            val context = getApplication<Application>().applicationContext
            val jsonString: String

            try {
                jsonString = context.assets.open("contacts.json").bufferedReader().use { it.readText() }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                return@withContext emptyList<Contact>()
            }

            val json = Json {
                ignoreUnknownKeys = true
            }
            val contactList = json.decodeFromString<List<Contact>>(jsonString)
            contactList
        }
    }

    val messages = mutableStateOf(listOf<Message>())
}