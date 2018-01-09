package de.kramhal.sipgate.scli

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.web.client.RestTemplate

class RestfulClient {
    val restTemplate: RestTemplate = RestTemplate()

    data class Post(
            @JsonProperty("userId") val userId: Int,
            @JsonProperty("id") val id: Int,
            @JsonProperty("title") val title: String,
            @JsonProperty("body") val body: String)

    fun getEntity() {
        ObjectMapper().registerKotlinModule()
        val getUrl = "https://jsonplaceholder.typicode.com/posts/1"
        val answer = restTemplate.getForEntity(getUrl, Post::class.java).body?.toString()
        println("Response : ${answer ?: "NULL"}")
    }
}
