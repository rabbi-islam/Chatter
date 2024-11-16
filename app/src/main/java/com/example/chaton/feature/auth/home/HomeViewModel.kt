package com.example.chaton.feature.auth.home

import androidx.lifecycle.ViewModel
import com.example.chaton.model.Channel
import com.google.firebase.Firebase
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor():ViewModel() {

    private val firebaseDatabase = Firebase.database

    private val _channel = MutableStateFlow<List<Channel>>(emptyList())
    val channel = _channel.asStateFlow()

    init {
        getChannel()
    }

    private fun getChannel(){
        firebaseDatabase.getReference("channel").get().addOnSuccessListener {
            val list = mutableListOf<Channel>()
            it.children.forEach {data->
                val channel = Channel(data.key!!,data.value.toString())
                list.add(channel)
            }
            _channel.value = list
        }.addOnFailureListener {
        }

    }
    fun addChannel(name:String){
        val key = firebaseDatabase.getReference("channel").push().key
        firebaseDatabase.getReference("channel").child(key!!).setValue(name).addOnSuccessListener {
            getChannel()
        }
    }



}