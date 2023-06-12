package com.example.ayosehat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth
import java.time.Instant

class DokterAdapter(val context: Context, val userList: ArrayList<Dokter>):
    RecyclerView.Adapter<DokterAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.doc_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val currentUser = userList[position]

        holder.textName.text = currentUser.name
        holder.textSp.text = currentUser.spesialis
        holder.textEx.text = currentUser.pengalaman
        holder.textGrad.text = currentUser.lulusan

        holder.itemView.setOnClickListener {
            val intent = Intent(context,ChatActivty::class.java)

            intent.putExtra("name", currentUser.name)
            intent.putExtra("uid",currentUser.uid)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName = itemView.findViewById<TextView>(R.id.txt_name)
        val textSp = itemView.findViewById<TextView>(R.id.txt_spesialis)
        val textEx = itemView.findViewById<TextView>(R.id.txt_pengalaman)
        val textGrad = itemView.findViewById<TextView>(R.id.txt_lulusan)

    }
}