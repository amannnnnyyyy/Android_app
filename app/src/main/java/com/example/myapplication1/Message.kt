package com.example.myapplication1

import java.io.Serializable

data class Message(val repliedTo:String?, val originalMessage:String?, val message:String, val type:String="received"): Serializable
