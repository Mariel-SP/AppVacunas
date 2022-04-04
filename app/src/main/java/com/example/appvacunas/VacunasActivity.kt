package com.example.appvacunas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appvacunas.controlador.JavaMailAPI
import com.example.appvacunas.controlador.VacunasDisponiblesAdapter
import com.example.appvacunas.modelo.VacunasModelo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VacunasActivity : AppCompatActivity(), VacunasDisponiblesAdapter.onClickLister {

    lateinit var recyclerView: RecyclerView
    lateinit var centrosLista: MutableList<VacunasModelo>
    lateinit var adapter: VacunasDisponiblesAdapter
    val database = Firebase.firestore
    lateinit var auth: FirebaseAuth
    lateinit var centro:String
    lateinit var btn_voler:ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vacunas)

        recyclerView = findViewById(R.id.rv_vacunas)
        btn_voler = findViewById(R.id.btn_volverVacuna)

        auth = FirebaseAuth.getInstance()

        val bundle = intent.extras
        val dato = bundle?.getString("id")
        centro = bundle!!.getString("centro")!!
        conexionFirestore(dato.toString())

        btn_voler.setOnClickListener {
            val intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
        }

    }

    private fun conexionFirestore(document:String) {
        //val database = Firebase.firestore
        centrosLista = mutableListOf()

        database.collection("clinicas").document(document).collection("vacunas").get().addOnSuccessListener { document ->
            centrosLista.clear()
            for (getdatos in document) {
                val nombre = getdatos.getString("nombre")
                val informacion = getdatos.getString("informacion")
                val id = getdatos.id
                val testDatos = VacunasModelo(nombre!!,informacion!!,id)
                centrosLista.add(testDatos)
            }
            adapter = VacunasDisponiblesAdapter(centrosLista,this)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }.addOnFailureListener { exception ->

        }
    }

    override fun solicitarVacuna(id: String, nombre: String) {
        val datos = hashMapOf(
                "identificador" to id,
                "nombre" to nombre,
                "clinica" to centro,
                "aprobado" to false
        )

        database.collection("usuarios").document(auth.uid.toString()).collection("solicitud").document(id).set(datos)
                .addOnSuccessListener {
                    //Toast.makeText(this,"Solicitud registrada con exito", Toast.LENGTH_LONG).show()
                }.addOnFailureListener {

                }

        sendMail(id,nombre)
    }

    private fun sendMail(id: String, nombre: String) {
        //Coreo al que se va enviar
        val mail = "davinsaca@yahoo.com"

        val message: String =
                "Identificador de vacuna: " + id + "<br>" +
                "Nombre de la vacuna: " + nombre + "<br>" +
                "Nombre del solicitante: " + nombreShared()+" "+apellidoShared() + "<br>"+
                "Numero de cedula: " + cedulaShared() + "<br>"
        val subject = "EcuaVacunas"

        //Send Mail
        val javaMailAPI = JavaMailAPI(this, mail, subject, message)
        javaMailAPI.execute()
    }

    fun nombreShared(): String {
        val pref = applicationContext.getSharedPreferences("datosUser", MODE_PRIVATE)
        return pref.getString("nombre", "default")!!
    }

    fun apellidoShared(): String {
        val pref = applicationContext.getSharedPreferences("datosUser", MODE_PRIVATE)
        return pref.getString("apellidos", "default")!!
    }

    fun cedulaShared(): String {
        val pref = applicationContext.getSharedPreferences("datosUser", MODE_PRIVATE)
        return pref.getString("cedula", "default")!!
    }

}