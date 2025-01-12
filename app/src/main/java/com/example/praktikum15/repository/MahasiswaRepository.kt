package com.example.praktikum15.repository

import com.example.praktikum15.model.Mahasiswa
import kotlinx.coroutines.flow.Flow

interface MahasiswaRepository{

    suspend fun getMahasiswa(): Flow<List<Mahasiswa>>

    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)

    suspend fun updateMahasiswa( mahasiswa: Mahasiswa)

    suspend fun deleteMahasiswa(mahasiswa: Mahasiswa)

    suspend fun getMahasiswaById(nim: String): Flow<Mahasiswa>
}