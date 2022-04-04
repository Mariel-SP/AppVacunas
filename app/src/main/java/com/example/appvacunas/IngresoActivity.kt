package com.example.appvacunas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class IngresoActivity : AppCompatActivity() {

    lateinit var btnGoogleLogin: ImageButton
    lateinit var button: ImageButton
    lateinit var btn_ingresar:Button
    lateinit var et_correo:EditText
    lateinit var et_clave:EditText
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val RC_SIGN_IN = 123
    lateinit var auth: FirebaseAuth
    lateinit var tv_registrar:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGoogleLogin = findViewById(R.id.btnLoginGoogle)
        button = findViewById(R.id.btnLoginFacebook)
        btn_ingresar = findViewById(R.id.btn_ingresar)
        tv_registrar = findViewById(R.id.tv_crear_cuenta)
        et_correo = findViewById(R.id.editEmail)
        et_clave = findViewById(R.id.editPass)
        auth = FirebaseAuth.getInstance()

        /*initGoogleClient()

        btnGoogleLogin.setOnClickListener {
            loginWithGoogle()
        }*/

        tv_registrar.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        btn_ingresar.setOnClickListener {
            loginUser(et_correo.text.toString(),et_clave.text.toString())
        }

        button.setOnClickListener {
            val intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
        }

    }

    //Verificar si ya hay una cuenta que haya iniciado sesion previamente
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    //Ingresar con correo
    private fun checkCredentials(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Llenar los campos", Toast.LENGTH_LONG).show()
            return false
        }else if (!email.contains("@") || email.length < 6) {
            Toast.makeText(this, "Verificar que el correo contenga @", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    //Conexion con firebase para validar si la cuenta existe en caso de que si verifica que el correo y la clave concidadan
    private fun loginUser(email: String, password: String) {

        if (checkCredentials(email, password)) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        updateUI(user)
                        /*if (!user.isEmailVerified) {
                            Toast.makeText(activity, "En la espera de confirmacion del correo", Toast.LENGTH_SHORT).show()
                        } else {
                            updateUI(user)
                        }*/
                    } else {
                        Toast.makeText(this, "Correo o contraseña no valido", Toast.LENGTH_SHORT).show()
                    }
                }

        }

    }


    /*Registro con google
    fun initGoogleClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    fun loginWithGoogle() {
        mGoogleSignInClient.signOut()
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                //Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                //Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }

    }

    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)

                } else {
                    Toast.makeText(
                        applicationContext,
                        "No se pudo iniciar sesion",
                        Toast.LENGTH_LONG
                    ).show()

                }

            }
    }*/

    fun updateUI(currentUser: FirebaseUser?) { //send current user to next activity
        if (currentUser == null) return
        //Toast.makeText(this,"Registrado con exito",Toast.LENGTH_LONG).show()
        val intent = Intent(this, InicioActivity::class.java)
        startActivity(intent)
        finish()
    }
}