package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    private lateinit var meassageRecyclerview: RecyclerView
    private lateinit var messageBox : EditText
    private lateinit var sentButton : ImageView
    private lateinit var massageadapter: massageadapter
    private lateinit var massageList : ArrayList<massege>
    private lateinit var mDbRef : DatabaseReference

    var reciverRoom : String? = null
    var senderRoom : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name")
        val reciveruid = intent.getStringExtra("uid")
        val senderuid = FirebaseAuth.getInstance().currentUser?.uid
        mDbRef = FirebaseDatabase.getInstance().getReference()

        senderRoom = reciveruid + senderuid
        reciverRoom = senderuid + reciveruid

        supportActionBar?.title = name

        meassageRecyclerview = findViewById(R.id.chatRecyclerview)
        messageBox = findViewById(R.id.mesagebox)
        sentButton = findViewById(R.id.sentBTN)
        massageList = ArrayList()
        massageadapter = massageadapter(this,massageList)

        chatRecyclerview.layoutManager = LinearLayoutManager(this)
        chatRecyclerview.adapter = massageadapter

        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    massageList.clear()
                    for (postSnaphshot in snapshot.children){
                        val message = postSnaphshot.getValue(massege::class.java)
                        massageList.add(message!!)
                    }
                    massageadapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        sentButton.setOnClickListener{
            val massege = messageBox.text.toString()
            val massegeObject = senderuid?.let { it1 -> massege(massege, it1) }

            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(massegeObject).addOnSuccessListener {
                    mDbRef.child("chats").child(reciverRoom!!).child("messages").push()
                }
            messageBox.setText("")
        }
    }
}