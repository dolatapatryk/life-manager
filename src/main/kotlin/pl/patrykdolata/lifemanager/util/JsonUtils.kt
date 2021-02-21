package pl.patrykdolata.lifemanager.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

object JsonUtils {
    private val objectMapper = ObjectMapper().registerModule(KotlinModule())

    fun asJsonString(obj: Any?): String? {
        return objectMapper.writeValueAsString(obj)
    }

    fun <T> fromJson(json: String, klazz: Class<T>): T? {
        return objectMapper.readValue(json, klazz)
    }
}
