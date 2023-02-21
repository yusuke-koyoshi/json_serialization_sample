package org.example

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.json.Json
import org.example.model.Company


data class Result(
    val company: Company,
    val startTime: Long,
    val endTime: Long,
) {
    fun getTime() = endTime - startTime
}
class SampleMain {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val maxCount = 10
            val jsonString1 = this::class.java.classLoader.getResource("test1.json").readText()
            val jsonString2 = this::class.java.classLoader.getResource("test2.json").readText()
            val jsonStrings = listOf(jsonString1, jsonString2)

            var results = mutableListOf<Result>()
            // Gson
            val gson = Gson()
            for (i in 1..maxCount) {
                val jsonString = if (i % 2 == 0) jsonStrings[0] else jsonStrings[1]
                val startTime: Long = System.currentTimeMillis()
                val company = gson.fromJson(jsonString, Company::class.java)
                val endTime: Long = System.currentTimeMillis()
                results.add(Result(company, startTime, endTime))
            }
            println("gson")
            println("処理時間[ms]：${results.joinToString { it.getTime().toString() }}")

            results = mutableListOf()
            // Jackson
            val jacksonObjectMapper = jacksonObjectMapper()
            for (i in 1..maxCount) {
                val jsonString = if (i % 2 == 0) jsonStrings[0] else jsonStrings[1]
                val startTime = System.currentTimeMillis()
                var company = jacksonObjectMapper.readValue(jsonString, Company::class.java)
                val endTime = System.currentTimeMillis()
                results.add(Result(company, startTime, endTime))
            }
            println("jackson")
            println("処理時間[ms]：${results.joinToString { it.getTime().toString() }}")

            results = mutableListOf()
            // Moshi
            val moshi: Moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val jsonAdapter: JsonAdapter<Company> = moshi.adapter(Company::class.java)
            for (i in 1..maxCount) {
                val jsonString = if (i % 2 == 0) jsonStrings[0] else jsonStrings[1]
                val startTime = System.currentTimeMillis()
                val company = jsonAdapter.fromJson(jsonString)
                val endTime = System.currentTimeMillis()
                results.add(Result(company!!, startTime, endTime))
            }
            println("moshi")
            println("処理時間[ms]：${results.joinToString { it.getTime().toString() }}")


            results = mutableListOf()
            // kotlinx.serialization
            val json = Json
            for (i in 1..maxCount) {
                val jsonString = if (i % 2 == 0) jsonStrings[0] else jsonStrings[1]
                val startTime = System.currentTimeMillis()
                val company = json.decodeFromString(Company.serializer(), jsonString)
                val endTime = System.currentTimeMillis()
                results.add(Result(company, startTime, endTime))
            }
            println("kotlinx.serialization")
            println("処理時間[ms]：${results.joinToString { it.getTime().toString() }}")

        }


    }
}
