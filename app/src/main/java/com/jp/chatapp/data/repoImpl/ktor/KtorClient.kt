package com.jp.chatapp.data.repoImpl.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

object KtorClient {
    val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        isLenient = true
    }

    val httpClient = HttpClient(Android) {
        install(HttpTimeout) {
            requestTimeoutMillis = 15000L
            connectTimeoutMillis = 15000L
            socketTimeoutMillis = 15000L
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println("Log : $message")
                }
            }
        }

        install(JsonFeature){
            serializer = KotlinxSerializer(json)
        }

        defaultRequest {
            contentType(
                ContentType.Application.Json
            )
            accept(ContentType.Application.Json)
        }
    }
}