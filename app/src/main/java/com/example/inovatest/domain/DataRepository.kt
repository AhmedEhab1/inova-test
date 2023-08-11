package com.example.inovatest.domain

import android.content.Context
import com.example.inovatest.AppModule
import com.example.inovatest.R
import org.json.JSONObject
import javax.inject.Inject


class DataRepository @Inject constructor() {
    fun fetchData(): DataModel {
        val inputStream = AppModule.appContext?.resources?.openRawResource(R.raw.json_data)
        val json = inputStream?.bufferedReader().use { it?.readText() }
        val jsonObject = JSONObject(json)
        return DataModel(jsonObject.getString("title"), jsonObject.getString("content"))
    }
}
