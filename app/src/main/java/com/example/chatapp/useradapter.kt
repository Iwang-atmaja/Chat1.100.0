package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class useradapter(val context: Context, val userList: ArrayList<User>):
    RecyclerView.Adapter<useradapter.userViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewholder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return userViewholder(view)
    }

    override fun onBindViewHolder(holder: userViewholder, position: Int) {
        val currentuser = userList[position]
        holder.textName.text = currentuser.name
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class userViewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textName = itemView.findViewById<TextView>(R.id.text_name)
    }

}