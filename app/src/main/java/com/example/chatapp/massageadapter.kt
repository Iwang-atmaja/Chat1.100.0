package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class massageadapter(val context: Context, val massageList: ArrayList<massege>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECIVE = 1
    val ITEM_SENT = 2


    class SentViewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sentMassage = itemView.findViewById<TextView>(R.id.Text_send)
    }

    class ReciveViewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val reciveMassage = itemView.findViewById<TextView>(R.id.Text_recive)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1){
            val view: View = LayoutInflater.from(context).inflate(R.layout.recive, parent, false)
            return ReciveViewholder(view)
        }else{
            val view: View = LayoutInflater.from(context).inflate(R.layout.send, parent, false)
            return SentViewholder(view)
        }
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = massageList[position]
        if (holder.javaClass == SentViewholder::class.java){


            val viewholder = holder as SentViewholder
            holder.sentMassage.text =  currentMessage.Message
        }else{
            val viewholder = holder as ReciveViewholder
            holder.reciveMassage.text = currentMessage.Message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val curretmassege = massageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(curretmassege.senderid)){
            return ITEM_SENT
        }else{
            return ITEM_RECIVE
        }
    }

    override fun getItemCount(): Int {
        return massageList.size
    }


}