package com.example.ayosehat

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

//, AdapterView.OnItemSelectedListener
class SignUp : AppCompatActivity(){

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtName: EditText
    private lateinit var edtRole: EditText
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    private lateinit var linearLayouts : LinearLayout
    private lateinit var spinner : Spinner

//    private var txtRoles ="Dokter"
//
//    var roles = arrayOf("Dokter", "User")
//    val NEW_SPINNER_ID = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        edtName = findViewById(R.id.edt_name)
        btnSignUp = findViewById(R.id.btnSignUp)
//        edtRole = findViewById(R.id.edt_role)

//        linearLayouts = findViewById(R.id.parentLayout)
//        spinner = findViewById(R.id.spinnerRole)
//        var aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//
//        with(spinner)
//        {
//            adapter = aa
//            setSelection(0, false)
//            onItemSelectedListener = this@SignUp
//            prompt = "Select your favourite language"
//            gravity = Gravity.CENTER
//
//        }

//        val spinner = Spinner(this)
//        spinner.id = NEW_SPINNER_ID
//
//        val ll = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//
//        ll.setMargins(10, 40, 10, 10)
//        linearLayouts.addView(spinner)

//        aa = ArrayAdapter(this, R.layout.item_spinner_role, roles)
//        aa.setDropDownViewResource(R.layout.item_spinner_role)
//
//        with(spinner)
//        {
//            adapter = aa
//            setSelection(0, false)
//            onItemSelectedListener = this@SignUp
//            prompt = "Select your Role"
//            setPopupBackgroundResource(androidx.appcompat.R.color.material_grey_600)
//
//        }

        btnSignUp.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val name = edtName.text.toString()

            signUp(name, email, password)
        }
    }

    private fun signUp(name: String,email: String, password: String){
        if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
           mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                    val intent = Intent(this, Login::class.java)
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

    private fun addUserToDatabase(name: String, email: String, uid: String){
        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child("user").child(uid).setValue(User(name, email, uid))
    }

//    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {
//        when (view?.id) {
//            1 -> txtRoles = roles[position]
//            else -> {
//                txtRoles = roles[position]
//            }
//        }
//
//    }
//
//    override fun onNothingSelected(position: AdapterView<*>?) {
//        Toast.makeText(this, "Nothing Selected", Toast.LENGTH_SHORT).show()
//    }
//
//    private fun showToast(context: Context = applicationContext, message: String, duration: Int = Toast.LENGTH_LONG) {
//        Toast.makeText(context, message, duration).show()
//    }

}