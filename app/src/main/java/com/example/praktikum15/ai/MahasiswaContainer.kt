package com.example.praktikum15.ai

import com.example.praktikum15.repository.MahasiswaRepository
import com.example.praktikum15.repository.NetworkMahasiswaRepository
import com.google.firebase.firestore.FirebaseFirestore

interface AppContainer {
    val mahasiswaRepository : MahasiswaRepository
}

class MahasiswaContainer : AppContainer{
    private val firebase: FirebaseFirestore = FirebaseFirestore.getInstance()
    override val mahasiswaRepository: MahasiswaRepository by lazy {
        NetworkMahasiswaRepository(firebase)
    }
}