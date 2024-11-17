package com.example.chaton.feature.auth.chat

import androidx.lifecycle.ViewModel
import com.example.chaton.model.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {

    private val _message = MutableStateFlow<List<Message>>(emptyList())
    val message = _message.asStateFlow()
    private val db = Firebase.database


    fun sendMessage(channelId: String, messageText: String){
        val message = Message(
            id = db.reference.push().key?: UUID.randomUUID().toString(),
            senderId = Firebase.auth.currentUser?.uid?: "",
            message = messageText,
            createdAt = System.currentTimeMillis(),
            senderName = Firebase.auth.currentUser?.displayName?: "" ,
            senderImage = null,
            imageUrl = null
        )

        val key = db.getReference("message").child(channelId).push().setValue(message)

    }

    fun listenForMessage(channelId: String) {
        db.getReference("message").child(channelId).child("message").orderByChild("createdAt")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<Message>()
                    snapshot.children.forEach { data->
                        val message = data.getValue(Message::class.java)
                        message?.let {
                            list.add(message)
                        }
                    }
                    _message.value = list
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

}