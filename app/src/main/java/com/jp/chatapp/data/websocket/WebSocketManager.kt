package com.jp.chatapp.data.websocket

import com.jp.chatapp.data.utils.MessageStatus
import com.jp.chatapp.domain.models.chatList.ChatUserInfo
import com.jp.chatapp.domain.models.personalChat.PersonalChat
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.serialization.json.Json
import org.json.JSONObject


object WebSocketManager {

     private lateinit var socket: Socket

    fun connect(url: String = "http://10.0.2.2:9000") {
            socket = IO.socket(url)
            socket.connect()
            socket.on(Socket.EVENT_CONNECT) {
                println("connected xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")

        }


    }

    fun removeCon(token: String){
        println("hello $token")
        socket.disconnect()
    }
    fun join(token: String, firebaseMessagingToken : String) {
        val json = JSONObject().apply {
            put("token",token)
            put("firebaseNotificationToken",firebaseMessagingToken)
        }
        socket.emit("join", json)
    }

    fun getChatList(token : String){
        socket.emit("chatList",token)
    }
    fun chatList( callBack: (ChatUserInfo) -> Unit){
        println("hello")
        socket.on("chatList"){args->

            println("args ${args[0]}")

            try {

                val json = args[0].toString()
                val item = Json.decodeFromString<ChatUserInfo>(json)
                println(item)
                callBack(item)
            }catch (e :Exception){
            println("error : $e")
            }
        }
    }



    fun sendMessage(token: String, phone: String, msg: String) {
        println(phone)
        val jsonObject = JSONObject().apply {
            put("phone", phone)
            put("token", token)
            put("msg", msg)
        }
        socket.emit("chat", jsonObject)
    }
    fun getPreviousChats(token: String, receiverId: String) {
        val jsonObject = JSONObject().apply {
            put("token", token)
            put("receiverId", receiverId)
        }

        socket.emit("previousChat", jsonObject)
    }
    fun receiveChat(callBack : (PersonalChat)->Unit){
        socket.on("chat") { args ->
            try {
                val json = args[0].toString()
                val item = Json.decodeFromString<PersonalChat>(json)
                callBack(item)
            }catch (e : Exception){
                println(e.localizedMessage)
            }

        }
    }

   fun getUserInfo(token: String, phone : String){

       val json = JSONObject().apply {
           put("token",token)
           put("userId",phone)
       }
       socket.emit("getUserInfo",json)
   }

    fun getProfile(token: String){
        val json = JSONObject().apply {
            put("token",token)
        }
        socket.emit("getProfile",json)
    }
    fun receiverUserInfo(callBack: (ChatUserInfo) -> Unit){
        socket.on("getUserInfo"){args->
           try {
               val json = args[0].toString()
               val item  = Json.decodeFromString<ChatUserInfo>(json)
               callBack(item)
           }catch (e : Exception){

               println(e.localizedMessage)
           }


        }
    }

    fun receiveProfile(callBack: (ChatUserInfo) -> Unit){
        socket.on("getUserInfo"){
            args->
            val json  = args[0].toString()
            val info = Json.decodeFromString<ChatUserInfo>(json)
            callBack(info)
        }
    }


    fun updateProfileImg(file : ByteArray, fileType : String , token: String){
        val json = JSONObject().apply {
            put("img", file)
            put("fileType", fileType)
            put("token", token)
        }
        socket.emit("updateProfileImg",json)
    }


    fun testEnum(){
        socket.emit("mediaChat",MessageStatus.READ)
    }

}