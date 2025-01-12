package com.example.praktikum15.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.praktikum15.ui.viewmodel.EditViewModel
import com.example.praktikum15.ui.viewmodel.FormState
import com.example.praktikum15.ui.viewmodel.MahasiswaEvent
import com.example.praktikum15.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMhsView(
    nim: String,
    onBack: () -> Unit,
    onUpdateSuccess: () -> Unit,
    viewModel: EditViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val uiEvent = viewModel.uiEvent
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(nim) {
        viewModel.loadMahasiswa(nim) // Load data mahasiswa berdasarkan NIM
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is FormState.Success -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(uiState.message)
                }
                onUpdateSuccess() // Navigasi setelah update berhasil
            }
            is FormState.Error -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(uiState.message)
                }
            }
            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Mahasiswa") },
                navigationIcon = {
                    Button(onClick = onBack) {
                        Text("Kembali")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        EditBodyMhs(
            uiState = uiEvent.insertUiEvent,
            onValueChange = { updatedEvent ->
                viewModel.updateState(updatedEvent)
            },
            onClick = {
                viewModel.updateMahasiswa()
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        )
    }
}

@Composable
fun EditBodyMhs(
    uiState: MahasiswaEvent,
    onValueChange: (MahasiswaEvent) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.nama,
            onValueChange = {
                onValueChange(uiState.copy(nama = it))
            },
            label = { Text("Nama") },
            placeholder = { Text("Masukkan nama") }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.alamat,
            onValueChange = {
                onValueChange(uiState.copy(alamat = it))
            },
            label = { Text("Alamat") },
            placeholder = { Text("Masukkan alamat") }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.angkatan,
            onValueChange = {
                onValueChange(uiState.copy(angkatan = it))
            },
            label = { Text("Angkatan") },
            placeholder = { Text("Masukkan angkatan") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = true
        ) {
            Text("Update")
        }
    }
}
