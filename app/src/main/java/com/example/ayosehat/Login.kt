package com.example.ayosehat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Login : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnlogin: Button
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnlogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)

        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        btnlogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            login(email, password)
        }
    }

    private fun login(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val currentUserUid = mAuth.currentUser?.uid
                    if (currentUserUid != null) {
                        mDbRef.child("user").child(currentUserUid)
                            .addListenerForSingleValueEvent(object :
                                ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val role = snapshot.child("role").getValue(String::class.java)
                                    if (role!!.lowercase() == "dokter") {
                                        val intent = Intent(this@Login, DocActivity::class.java)
                                        startActivity(intent)
                                    } else {
                                        val intent = Intent(this@Login, MainActivity::class.java)
                                        startActivity(intent)
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {

                                }
                            })
                    }
                }else{
                    Toast.makeText(this, "Gagal Login !!", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Tidak Boleh Kosong !!", Toast.LENGTH_SHORT).show()

        }
    }


//    override fun onStart() {
//        super.onStart()
//        if (mAuth.currentUser != null) {
//            val currentUserUid = mAuth.currentUser?.uid
//            mDbRef.child("user").child(currentUserUid!!)
//                .addListenerForSingleValueEvent(object :
//                    ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        val role = snapshot.child("role").getValue(String::class.java)
//                        if (role!!.lowercase() == "dokter") {
//                            val intent = Intent(this@Login, DocActivity::class.java)
//                            startActivity(intent)
//                        } else {
//                            val intent = Intent(this@Login, MainActivity::class.java)
//                            startActivity(intent)
//                        }
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//
//                    }
//                })
//        }
//    }
}


//            val intent = Intent(this, MainActivity::class.java)
//            finish()
//            startActivity(intent)
