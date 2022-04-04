package com.example.appvacunas

class Vacunas constructor(
    nombre:String,
    esquema:String,
    marca:String
) {
    var nombre:String
    var esquema:String
    var marca:String

    init {
        this.nombre=nombre
        this.esquema=esquema
        this.marca=marca
    }
}