package com.jp.chatapp.data.network.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.ANDROID
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import kotlinx.serialization.json.Json

object HttpClient {
    val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        isLenient = true

    }
    val client by lazy {
        HttpClient(CIO) {
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.ANDROID
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer(json)
            }

        }


    }
}