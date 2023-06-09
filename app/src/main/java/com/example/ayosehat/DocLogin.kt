package com.example.ayosehat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class DocLogin : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtId: EditText
    private lateinit var btnlogin: Button
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doc_login)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        edtEmail = findViewById(R.id.docedt_email)
        edtPassword = findViewById(R.id.docedt_password)
        btnlogin = findViewById(R.id.docbtnLogin)
        btnSignUp = findViewById(R.id.docbtnSignUp)
        edtId = findViewById(R.id.doc_id)

        btnSignUp.setOnClickListener {
            val intent = Intent(this, DocSignUp::class.java)
            startActivity(intent)
        }

        btnlogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val id = edtId.text.toString()

            login(email, password, id)
        }
    }

    private fun login(email: String, password: String, id: String) {
        if (email.isNotEmpty() && password.isNotEmpty() && id.isNotEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { signInTask ->
                if (signInTask.isSuccessful) {
                    val currentUser = mAuth.currentUser
                    if (currentUser != null) {
                        val uid = currentUser.uid

                        // Melakukan pemeriksaan ID di database Firebase
                        mDbRef.child("dokter").child(uid).get().addOnCompleteListener { idCheckTask ->
                            if (idCheckTask.isSuccessful) {
                                val dokterSnapshot = idCheckTask.result
                                if (dokterSnapshot != null && dokterSnapshot.exists()) {
                                    val dokterId = dokterSnapshot.child("id").value as? String
                                    if (dokterId == id) {
                                        // ID sesuai, dokter berhasil masuk
                                        val intent = Intent(this@DocLogin, DocActivity::class.java)
                                        startActivity(intent)
                                    } else {
                                        // ID tidak sesuai
                                        Toast.makeText(this, "ID tidak valid", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    // Data dokter tidak ditemukan di Firebase Database
                                    Toast.makeText(this, "Data dokter tidak ditemukan", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                // Error saat memeriksa ID
                                Toast.makeText(this, "Terjadi kesalahan saat memeriksa ID", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        // Pengguna tidak ditemukan
                        Toast.makeText(this, "Pengguna tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Gagal login
                    Toast.makeText(this, "Gagal Login 😭😭", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Tidak Boleh Kosong !!", Toast.LENGTH_SHORT).show()
        }
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


