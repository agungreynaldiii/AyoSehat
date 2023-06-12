package com.example.ayosehat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DocActivity : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var userListFinal: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_doc)
//
//        mAuth = FirebaseAuth.getInstance()
//        mDbRef = FirebaseDatabase.getInstance().reference
//
//        userList = ArrayList()
//        userListFinal = ArrayList()
//        adapter = UserAdapter(this, userListFinal)
//
//        userRecyclerView = findViewById(R.id.userRecycleView)
//
//        userRecyclerView.layoutManager = LinearLayoutManager(this)
//        userRecyclerView.adapter = adapter
//        mDbRef.child("user").addValueEventListener(object: ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                userList.clear()
//                for(postSnapshot in snapshot.children){
//                    val currentUser = postSnapshot.getValue(User::class.java)
//                    if(mAuth.currentUser?.uid != currentUser?.uid){
//                        userList.add(currentUser!!)
//                    }
//                }
//                userListFinal.clear()
//                userListFinal.addAll(userList)
//                adapter.notifyDataSetChanged()
//
//            }
//
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doc)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        userList = ArrayList()
        userListFinal = ArrayList()
        adapter = UserAdapter(this,userListFinal)

        userRecyclerView = findViewById(R.id.userRecycleView)

        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter

        mDbRef.child("user").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                userListFinal.clear()

                for(postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)
                    if(mAuth.currentUser?.uid != currentUser?.uid){
                        userList.add(currentUser!!)
                    }
                }

                mDbRef.child("chats").addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onDataChange(chatsSnapshot: DataSnapshot) {
                        userListFinal.clear()
                        for (user in userList) {
                            val chatKey = user.uid + mAuth.currentUser?.uid
                            if (chatsSnapshot.hasChild(chatKey)) {
                                userListFinal.add(user)
                            }
                        }
                        adapter.notifyDataSetChanged()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle error
                    }
                })

            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
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