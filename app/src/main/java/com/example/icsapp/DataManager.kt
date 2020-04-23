package com.example.icsapp

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class DataManager {
    val url = "http://35.243.175.160/add_config"
    val client = OkHttpClient()
    val JSON = "application/json;charset=utf-8".toMediaType()

    fun sendTCMdata(minutes: Int) {
        Log.d("OKHTTP3", "Doing TCM Post Request")
        val actualData = JSONObject()
        actualData.put("timer_length", minutes)
        actualData.put("scheduled_days", 0)
        actualData.put("start_time", 0)
        actualData.put("is_weather", false)
        actualData.put("is_moist", false)
        actualData.put("is_memeified", false)
        actualData.put("moisture_threshold", 0)
        actualData.put("rain_threshold", 0)
        actualData.put("mode_name", "Timer Irrigation Mode")

        val body = actualData.toString().toRequestBody(JSON)
        val request = Request.Builder().url(url).post(body).build()
        val cb = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("OKHTTP3", "Request Failed")
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful){
                    Log.d("OKHTTP3", "Frick yeah")
                    Log.d("OKHTTP3", response.body.toString())
                } else {
                    Log.d("OKHTTP3", "SIKE")
                }
            }
        }
        client.newCall(request).enqueue(cb)
    }
    /*
    weekdays: List of strings representing an entry for the given day
    can be one of the following:
    "Mon", "Tue", "Wed", "Thr", "Fri", "Sat", "Sun"
    startTimes: List of starting times for respective index in weekdays
    entries formatted as "HH:MM:SS" on 24hr clock
    minutes: List of durations for respective index in weekdays (or startTimes)
    entries are duration in seconds
     */
    fun sendSIMdata(weekdays: List<String>, startTimes: List<String>, minutes: List<Int>) {
        Log.d("OKHTTP3", "Doing SIM Post Request")

        print(weekdays)
        print(startTimes)
        print(minutes)

//        val actualData = JSONObject()
//        actualData.put("timer_length", minutes)
//        actualData.put("scheduled_days", 0)
//        actualData.put("start_time", 0)
//        actualData.put("is_weather", false)
//        actualData.put("is_moist", false)
//        actualData.put("is_memeified", false)
//        actualData.put("moisture_threshold", 0)
//        actualData.put("rain_threshold", 0)
//        actualData.put("mode_name", "Timer Irrigation Mode")
        //TODO: Implement data interface for SIM send
    }

}