package com.example.extremesport.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*

class DataSource {
    private val client = HttpClient {
        install(ContentNegotiation) {
            gson()
        }
    }

    suspend fun getSunrise(): String {
        val apiLink = "https://gw-uio.intark.uh-it.no/in2000/weatherapi/sunrise/3.0/sun?lat=59.933333&lon=10.716667&date=2022-12-18&offset=+01:00"
        val sunrise: String = client.get(apiLink) {
            headers {
                append("X-Gravitee-API-Key","b0285355-9b7b-44ea-a2f0-2fadb945792d")
            }
        }.body()
        println(sunrise)
        return sunrise
    }
}