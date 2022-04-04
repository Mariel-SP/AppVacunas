package com.example.appvacunas

class User constructor(idUser:String,
                       nombre:String,
                       apellidoP:String,
                       apellidoM:String,
                       genero:String,
                       fechaNacimiento:String,
                       peso:Double,
                       edadGestal:Double,
                       tipoSanguineo:String,
                       correo:String){

    var idUser:String;
    var nombre:String;
    var apellidoP:String;
    var apellidoM:String;
    var genero:String;
    var fechaNacimiento:String;
    var peso:Double;
    var edadGestal:Double;
    var tipoSanguineo:String;
    var correo:String;

    init {
        this.idUser=idUser;
        this.nombre=nombre;
        this.apellidoP=apellidoP;
        this.apellidoM=apellidoM;
        this.genero=genero;
        this.fechaNacimiento=fechaNacimiento;
        this.peso=peso;
        this.edadGestal=edadGestal;
        this.tipoSanguineo=tipoSanguineo;
        this.correo=correo;
    }
}