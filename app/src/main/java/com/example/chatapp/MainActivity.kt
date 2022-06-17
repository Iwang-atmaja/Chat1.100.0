package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userList : ArrayList<User>
    private lateinit var adapter: useradapter
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        mDbref = FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        adapter = useradapter(this,userList)

        userRecyclerview = findViewById(R.id.userRecyclerview)

        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.adapter = adapter

        mDbref.child("user").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postSnapshot in snapshot.children){
                    val currentuser = postSnapshot.getValue(User::class.java)
                    if(mAuth.currentUser?.uid != currentuser?.uid) {
                        userList.add(currentuser!!)
                    }
                }
                adapter.notifyDataSetChanged()
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
        if (item.itemId == R.id.Logout){
            mAuth.signOut()
            val intent = Intent(this@MainActivity,Login::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }

}