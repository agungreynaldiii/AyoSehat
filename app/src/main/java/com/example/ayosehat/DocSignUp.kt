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


class DocSignUp : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtName: EditText
    private lateinit var edtId: EditText
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doc_sign_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        edtName = findViewById(R.id.edt_name)
        btnSignUp = findViewById(R.id.btnSignUp)
        edtId = findViewById(R.id.idDoc)


        btnSignUp.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val name = edtName.text.toString()
            val id = edtId.text.toString()

            signUp(name, email, password, id)
        }
    }

    private fun signUp(name: String,email: String, password: String, id: String){
        if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()  && id.isNotEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!, id)
                    val intent = Intent(this, DocLogin::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this,"Gagal Daftar 🤡🤡", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Tidak boleh kosong !!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun addUserToDatabase(name: String, email: String, uid: String, id: String){
        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child("dokter").child(uid).setValue(Dokter(name, email, uid, id))
    }


}