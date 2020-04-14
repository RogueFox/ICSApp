package com.example.icsapp

class DataManager {
    fun sendTCMdata(minutes: Int) {
        //TODO: Implement data interface for TCM send
    }
    //TODO: Confirm signature for sendSIMdata(...)
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
        //TODO: Implement data interface for SIM send
    }
    //TODO: Create signature for sendAIMdata(...)
    fun sendAIMdata() {
        //TODO: Implement data interface for AIM send
    }
}