package org.example

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.json.Json
import org.example.model.Company
import org.example.model.Company2


data class Result(
    val company: Company,
    val company2: Company2,
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

            var results = mutableListOf<Result>()
            // Gson
            val gson = Gson()
            for (i in 1..maxCount) {
                val startTime: Long = System.currentTimeMillis()
                val company = gson.fromJson(jsonString1, Company::class.java)
                val company2 = gson.fromJson(jsonString2, Company2::class.java)
                val endTime: Long = System.currentTimeMillis()
                results.add(Result(company, company2, startTime, endTime))
            }
            println("gson")
            println("処理時間[ms]：${results.joinToString { it.getTime().toString() }}")

            results = mutableListOf()
            // Jackson
            val jacksonObjectMapper = jacksonObjectMapper()
            for (i in 1..maxCount) {
                val startTime = System.currentTimeMillis()
                var company = jacksonObjectMapper.readValue(jsonString1, Company::class.java)
                var company2 = jacksonObjectMapper.readValue(jsonString2, Company2::class.java)
                val endTime = System.currentTimeMillis()
                results.add(Result(company, company2, startTime, endTime))
            }
            println("jackson")
            println("処理時間[ms]：${results.joinToString { it.getTime().toString() }}")

            results = mutableListOf()
            // Moshi
            val moshi: Moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val jsonAdapter1 = moshi.adapter(Company::class.java)
            val jsonAdapter2 = moshi.adapter(Company2::class.java)
            for (i in 1..maxCount) {
                val startTime = System.currentTimeMillis()
                val company = jsonAdapter1.fromJson(jsonString1)
                val company2 = jsonAdapter2.fromJson(jsonString2)
                val endTime = System.currentTimeMillis()
                results.add(Result(company!!, company2!!, startTime, endTime))
            }
            println("moshi")
            println("処理時間[ms]：${results.joinToString { it.getTime().toString() }}")


            results = mutableListOf()
            // kotlinx.serialization
            val json = Json
            for (i in 1..maxCount) {
                val startTime = System.currentTimeMillis()
                val company = json.decodeFromString(Company.serializer(), jsonString1)
                val company2 = json.decodeFromString(Company2.serializer(), jsonString2)
                val endTime = System.currentTimeMillis()
                results.add(Result(company, company2, startTime, endTime))
            }
            println("kotlinx.serialization")
            println("処理時間[ms]：${results.joinToString { it.getTime().toString() }}")

        }


    }
}
