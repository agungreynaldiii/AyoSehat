package com.example.ayosehat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<Dokter>
    private lateinit var userListFinal: ArrayList<Dokter>
    private lateinit var adapter: DokterAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.apply {
            title = "List Dokter"
        }

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        userList = ArrayList()
        userListFinal = ArrayList()
        adapter = DokterAdapter(this,userListFinal)

        userRecyclerView = findViewById(R.id.userRecycleView)

        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter

        mDbRef.child("dokter").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postSnapshot in snapshot.children) {
                    val currentDokter = postSnapshot.getValue(Dokter::class.java)
                    if (mAuth.currentUser?.uid != currentDokter?.uid) {
                        userList.add(currentDokter!!)
                    }
                }

                userListFinal.clear()
                userListFinal.addAll(userList)
                adapter.notifyDataSetChanged()

//                for (a in 0 until userList.size) {
//                    if (userList[a].role!!.lowercase() == "dokter") {
//                        userListFinal.add(userList[a])
//                    }
//                }
            }


            override fun onCancelled(error: DatabaseError) {

            }

        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                mAuth.signOut()
                val intent = Intent(this, Login::class.java)
                finish()
                startActivity(intent)
                return true
            }
            R.id.Reload -> {
                finish()
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}