package com.example.praktikum15.ui.navigation

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

object DestinasiHome : DestinasiNavigasi {
    override val route: String = "home"
    override val titleRes: String = "Home"
}

object DestinasiInsert : DestinasiNavigasi {
    override val route: String = "insert"
    override val titleRes: String = "Insert"
}

object DestinasiEdit : DestinasiNavigasi {
    override val route: String = "edit/{nim}"
    override val titleRes: String = "Edit Mahasiswa"
}