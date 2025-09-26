package com.sks.theyellowtable.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface APIClient {
    suspend fun get(
        endpoint: String,
        parameters: Map<String, String> = emptyMap<String, String>()
    ): HttpResponse
}

class APIClientImpl: APIClient {

    private val baseURLString = "https://raw.githubusercontent.com/"

    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation.Plugin) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }, contentType = ContentType("text", "plain")
            )
        }
    }

    override suspend fun get(endpoint: String, parameters: Map<String, String>): HttpResponse {
        return httpClient.get("$baseURLString$endpoint")
    }
}