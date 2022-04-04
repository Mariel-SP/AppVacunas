package com.example.appvacunas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistroActivity : AppCompatActivity() {

    lateinit var cedula:EditText
    lateinit var nombres:EditText
    lateinit var apellidos:EditText
    lateinit var correo:EditText
    lateinit var clave:EditText
    lateinit var btn_registrar:Button
    lateinit var btn_voler: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        cedula = findViewById(R.id.et_cedula)
        nombres = findViewById(R.id.et_nombres)
        apellidos = findViewById(R.id.et_apellidos)
        correo = findViewById(R.id.et_correo)
        clave = findViewById(R.id.et_clave)
        btn_registrar= findViewById(R.id.btn_registrar)
        btn_voler = findViewById(R.id.btn_volverRegistro)

        btn_registrar.setOnClickListener {
            registerNewUser(correo.text.toString(),clave.text.toString(),
                cedula.text.toString(),nombres.text.toString(),apellidos.text.toString())
        }


        btn_voler.setOnClickListener {
            val intent = Intent(this, IngresoActivity::class.java)
            startActivity(intent)
        }
    }

    //Verificar los campos de correo y password
    fun checkCredentials(email: String, password: String, cedula: String,nombres: String,apellidos: String): Boolean {
        if (email.isEmpty() || password.isEmpty() || cedula.isEmpty() || nombres.isEmpty() || apellidos.isEmpty()) {
            Toast.makeText(this, "Llenar los campos", Toast.LENGTH_LONG).show()
            return false
        }else if (!email.contains("@") || email.length < 6) {
            Toast.makeText(this, "Correo inválido", Toast.LENGTH_LONG).show()
            return false
        } else if (cedula.length<9 || cedula.length>10){
            Toast.makeText(this, "Celular inválido", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    //Conexion con firebase para el registro de una nueva cuenta
    fun registerNewUser(email: String, password: String, cedula: String,nombres: String,apellidos: String) {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        if (checkCredentials(email,password,cedula,nombres,apellidos)){
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        //setDatos(user.uid)
                        updateUI(user, user!!.uid)
                        //sendEmailVerification(user)
                    } else {
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }
                }
        }


    }


    //Cargar el siguiente activity para la configuracion del perfil
    fun updateUI(currentUser: FirebaseUser?, uid:String) { //send current user to next activity
        if (currentUser == null) return
        val intent = Intent(this, InicioActivity::class.java)
        guardar(uid, cedula.text.toString(),nombres.text.toString(),apellidos.text.toString())
        startActivity(intent)
        finish()
    }

    private fun guardar(uid: String,cedula: String, nombres: String, apellidos: String){

        val db = Firebase.firestore

        val datos = hashMapOf(
            "cedula" to cedula,
            "nombre" to nombres,
            "apellidos" to apellidos,
        )

        db.collection("usuarios").document(uid).set(datos)
            .addOnSuccessListener {
                Toast.makeText(this,"Usuario registrado con exito", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {

            }
    }

}