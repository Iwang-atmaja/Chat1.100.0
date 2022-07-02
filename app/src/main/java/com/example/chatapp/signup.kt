package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

    private lateinit var edtname : EditText
    private lateinit var edtEmail : EditText
    private lateinit var edtpassword : EditText
    private lateinit var btnsignup : Button
    private lateinit var mDbRef : DatabaseReference
    private lateinit var mAuth : FirebaseAuth

class signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        mAuth = FirebaseAuth.getInstance()

        edtname = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtpassword = findViewById(R.id.edt_password)
        btnsignup = findViewById(R.id.btnsignup)

        btnsignup.setOnClickListener{
            val name = edtname.text.toString()
            val email = edtEmail.text.toString()
            val password = edtpassword.text.toString()

            Signup(name,email,password)
        }
    }
        private fun Signup(name: String, email:String, password:String){
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        addUsserToDatabase(name,email, mAuth.currentUser?.uid!!)
                        val intent = Intent(this@signup, MainActivity::class.java)
                        finish()
                        startActivity(intent)

                    } else {
                        Toast.makeText(this@signup, "Some Error", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    private fun addUsserToDatabase(name: String, email: String,uid: String){
        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(User(name,email,uid))
    }

}