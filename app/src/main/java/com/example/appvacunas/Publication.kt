package com.example.appvacunas

class Publication constructor(
    titulo:String,
    fecha:String,
    img:String,
    publicacion:String
) {
    var titulo:String;
    var fecha:String;
    var img:String;
    var publicacion:String;

    init {
        this.titulo=titulo;
        this.fecha=fecha;
        this.img=img;
        this.publicacion=publicacion;
    }
}