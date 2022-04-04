package com.example.appvacunas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appvacunas.controlador.CentroDeSaludAdapter
import com.example.appvacunas.modelo.CentrosDeSaludModelo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class InicioActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var centrosLista: MutableList<CentrosDeSaludModelo>
    lateinit var adapter: CentroDeSaludAdapter
    lateinit var btn_misVacunas:ImageButton
    lateinit var btn_salir:ImageButton
    val database = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        recyclerView = findViewById(R.id.rvInicio)
        btn_misVacunas = findViewById(R.id.btn_mis_vacunas)
        btn_salir = findViewById(R.id.btn_salir)

        btn_misVacunas.setOnClickListener {
            val intent = Intent(this,HistorialVacunasActivity::class.java)
            startActivity(intent)
        }

        btn_salir.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this, IngresoActivity::class.java)
            startActivity(intent)
        }

        conexionFirestore()
        getDatosUser()
    }




    private fun conexionFirestore() {

        centrosLista = mutableListOf()

        database.collection("clinicas").get().addOnSuccessListener { document ->
            centrosLista.clear()
            for (getdatos in document) {
                val nombre = getdatos.getString("nombre")
                val descripcion = getdatos.getString("descripcion")
                val foto = getdatos.getString("foto")
                val id = getdatos.id
                //val testDatos = CentrosDeSaludModelo(nombre!!,descripcion!!,foto!!,id)
                val testDatos = CentrosDeSaludModelo(nombre!!,"",id,foto!!)
                centrosLista.add(testDatos)
            }
            adapter = CentroDeSaludAdapter(centrosLista) {
                val extras = Bundle()
                extras.putString("id", it.id)
                extras.putString("centro", it.nombre)
                val intent = Intent(this, VacunasActivity::class.java)
                intent.putExtras(extras)
                startActivity(intent)
                //Toast.makeText(this,"xde "+it.id, Toast.LENGTH_LONG).show()


            }
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }.addOnFailureListener { exception ->

        }
    }

    //Obtener datos del usuario
    private fun getDatosUser() {
        //val db = Firebase.firestore
        val user = Firebase.auth.currentUser

        val pref = applicationContext.getSharedPreferences("datosUser", MODE_PRIVATE)
        val editor = pref.edit()

        database.collection("usuarios").document(user!!.uid).get()
                .addOnSuccessListener { getdatos ->
                    val nombre = getdatos.getString("nombre")
                    val apellido = getdatos.getString("apellidos")
                    val cedula = getdatos.getString("cedula")

                    editor.putString("uid",user.uid)
                    editor.putString("nombre", nombre)
                    editor.putString("apellidos", apellido)
                    editor.putString("cedula", cedula)
                    editor.apply()

                }.addOnFailureListener { exception ->

                }
    }


}