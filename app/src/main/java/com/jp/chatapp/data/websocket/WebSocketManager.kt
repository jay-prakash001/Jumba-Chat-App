package com.jp.chatapp.data.websocket

import com.jp.chatapp.domain.models.chatList.ChatUserInfo
import com.jp.chatapp.domain.models.personalChat.PersonalChat
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.serialization.json.Json
import org.json.JSONObject


class WebSocketManager {

     lateinit var socket: Socket

    fun connect(url: String = "http://10.0.2.2:9000") {
            socket = IO.socket(url)
            socket.connect()
            socket.on(Socket.EVENT_CONNECT) {
                println("connected xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")

        }


    }

    fun removeCon(token: String){
        println("hello $token")
//        val json = JSONObject().apply {
//            put("token",token)
//        }
//        socket.emit("disconnect")
        socket.disconnect()
    }
    fun join(token: String) {
//        println("token $token")
        socket.emit("join", token)
    }

    fun getChatList(token : String){
        socket.emit("chatList",token)
    }
    fun chatList( callBack: (ChatUserInfo) -> Unit){
//        println("hello")
        socket.on("chatList"){args->
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
//        println("hello")
        println(phone)
        val jsonObject = JSONObject().apply {
            put("phone", phone)
            put("token", token)
            put("msg", msg)
        }
        socket.emit("chat", jsonObject)
    }
    fun getPreviousChats(token: String, receiverId: String) {
//        println("socketManger getchats $token $receiverId")
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

   fun getUserInfo(token: String, userId : String){

       val json = JSONObject().apply {
           put("token",token)
           put("userId",userId)
       }
       socket.emit("getUserInfo",json)
   }

    fun receiverUserInfo(callBack: (ChatUserInfo) -> Unit){
        socket.on("getUserInfo"){args->
           try {
               val json = args[0].toString()
               val item  = Json.decodeFromString<ChatUserInfo>(json)
//            println("User : $item")
               callBack(item)
           }catch (e : Exception){

               println(e.localizedMessage)
           }


        }
    }



}