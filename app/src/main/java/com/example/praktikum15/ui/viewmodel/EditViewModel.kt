package com.example.praktikum15.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikum15.model.Mahasiswa
import com.example.praktikum15.repository.MahasiswaRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditViewModel(
    private val repository: MahasiswaRepository
) : ViewModel() {

    var uiState: FormState by mutableStateOf(FormState.Idle)
        private set

    var uiEvent: InsertUiState by mutableStateOf(InsertUiState())
        private set

    fun loadMahasiswa(nim: String) {
        viewModelScope.launch {
            try {
                val mahasiswa = repository.getMahasiswaById(nim).first()
                uiEvent = uiEvent.copy(
                    insertUiEvent = MahasiswaEvent(
                        nim = mahasiswa.nim,
                        nama = mahasiswa.nama,
                        jenisKelamin = mahasiswa.jenisKelamin,
                        alamat = mahasiswa.alamat,
                        kelas = mahasiswa.kelas,
                        angkatan = mahasiswa.angkatan
                    )
                )
            } catch (e: Exception) {
                uiState = FormState.Error("Gagal memuat data mahasiswa")
            }
        }
    }

    fun updateMahasiswa() {
        if (validateFields()) {
            viewModelScope.launch {
                uiState = FormState.Loading
                try {
                    repository.updateMahasiswa(uiEvent.insertUiEvent.toMhsModel())
                    uiState = FormState.Success("Data berhasil diperbarui")
                } catch (e: Exception) {
                    uiState = FormState.Error("Gagal memperbarui data mahasiswa")
                }
            }
        } else {
            uiState = FormState.Error("Data tidak valid")
        }
    }

    private fun validateFields(): Boolean {
        val event = uiEvent.insertUiEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isNotEmpty()) null else "NIM Tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama Tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin Tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat Tidak boleh kosong",
            kelas = if (event.kelas.isNotEmpty()) null else "Kelas Tidak boleh kosong",
            angkatan = if (event.angkatan.isNotEmpty()) null else "Angkatan Tidak boleh kosong"
        )

        uiEvent = uiEvent.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateState(mahasiswaEvent: MahasiswaEvent) {
        uiEvent = uiEvent.copy(insertUiEvent = mahasiswaEvent)
    }
}
