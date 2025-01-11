package com.example.praktikum15.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikum15.model.Mahasiswa
import com.example.praktikum15.repository.MahasiswaRepository
import kotlinx.coroutines.launch

class InsertViewModel(
    private val mhs: MahasiswaRepository
) : ViewModel(){

    var uiEvent: InsertUiState by mutableStateOf(InsertUiState())
        private set

    var uiState: FormState by mutableStateOf(FormState.Idle)
        private set

    // Memperbarui state berdasarkan input pengguna
    fun updateState(mahasiswaEvent: MahasiswaEvent){
        uiEvent = uiEvent.copy(
            insertUiEvent = mahasiswaEvent
        )
    }

    // Validasi data input pengguna
    fun validateFields(): Boolean{
        val event = uiEvent.insertUiEvent
        val errorState= FormErrorState(
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

    fun insertMhs(){

        if (validateFields()){
            viewModelScope.launch{
                uiState = FormState.Loading
                try {
                    mhs.insertMahasiswa(uiEvent.insertUiEvent.toMhsModel())
                    uiState = FormState.Success("Data Berhasil Disimpan")
                } catch (e: Exception){
                    uiState = FormState.Error("Data Gagal Disimpan")
                }
            }
        } else{
            uiState = FormState.Error("Data tidak valid")
        }
    }

    fun resetForm(){
        uiEvent = InsertUiState()
        uiState = FormState.Idle
    }

    fun resetSnackBarMessage(){
        uiState = FormState.Idle
    }
}

sealed class FormState {
    object Idle : FormState()
    object Loading : FormState()
    data class Success(val message: String): FormState()
    data class Error(val message: String): FormState()
}

data class InsertUiState(
    val insertUiEvent: MahasiswaEvent = MahasiswaEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
)

data class FormErrorState(
    val nim: String?= null,
    val nama: String?= null,
    val jenisKelamin: String?= null,
    val alamat: String?= null,
    val kelas: String?= null,
    val angkatan: String?= null,
){
    fun isValid(): Boolean{
        return nim == null && nama == null && jenisKelamin == null &&
                alamat == null && kelas == null && angkatan == null
    }
}

// data class Variabel yang menyimpan data input form
data class MahasiswaEvent(
    val nim: String = "",
    val nama: String = "",
    val jenisKelamin: String = "",
    val alamat: String = "",
    val kelas: String = "",
    val angkatan: String = "",
)

// Menyimpan input form ke dalam entity
fun MahasiswaEvent.toMhsModel(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    jenisKelamin = jenisKelamin,
    alamat = alamat,
    kelas = kelas,
    angkatan = angkatan
)