package com.example.appvacunas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appvacunas.controlador.MisVacunasAdapter
import com.example.appvacunas.controlador.VacunasDisponiblesAdapter
import com.example.appvacunas.modelo.VacunasModelo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HistorialVacunasActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var centrosLista: MutableList<VacunasModelo>
    lateinit var adapter: MisVacunasAdapter
    val database = Firebase.firestore
    lateinit var auth: FirebaseAuth
    lateinit var btn_inicio:ImageButton
    lateinit var btn_salir:ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_vacunas)
        recyclerView = findViewById(R.id.rv_MisVacunas)
        btn_inicio = findViewById(R.id.btn_inicio)
        btn_salir = findViewById(R.id.btn_salir)
        auth = FirebaseAuth.getInstance()

        btn_inicio.setOnClickListener {
            val intent = Intent(this,InicioActivity::class.java)
            startActivity(intent)
        }

        btn_salir.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this, IngresoActivity::class.java)
            startActivity(intent)
        }


        conexionFirestore()

    }

    private fun conexionFirestore() {
        //val database = Firebase.firestore
        centrosLista = mutableListOf()

        database.collection("usuarios").document(auth.uid.toString()).collection("solicitud").get().addOnSuccessListener { document ->
            centrosLista.clear()
            for (getdatos in document) {

                val nombre = getdatos.getString("clinica")
                val informacion = getdatos.getString("nombre")
                val aprobado = getdatos.getBoolean("aprobado")
                val id = getdatos.id

                if (aprobado==true){
                    val testDatos = VacunasModelo(nombre!!,informacion!!,id)
                    centrosLista.add(testDatos)
                }

            }
            adapter = MisVacunasAdapter(centrosLista)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }.addOnFailureListener { exception ->

        }
    }
}