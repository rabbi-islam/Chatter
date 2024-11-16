package com.example.chaton.feature.auth.chat

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor():ViewModel(){
    private val firebaseDatabase = Firebase.database

}