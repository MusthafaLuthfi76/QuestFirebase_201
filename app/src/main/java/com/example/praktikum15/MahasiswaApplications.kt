package com.example.praktikum15

import android.app.Application
import com.example.praktikum15.ai.AppContainer
import com.example.praktikum15.ai.MahasiswaContainer

class MahasiswaApplications:Application() {
    lateinit var container: AppContainer
    override fun onCreate(){
        super.onCreate()
        container = MahasiswaContainer()
    }
}