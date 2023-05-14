package com.example.messagesender.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Contact(val firstName: String, val lastName: String, val phoneNumber: String)
