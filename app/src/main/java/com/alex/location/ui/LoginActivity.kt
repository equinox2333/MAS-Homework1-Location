package com.alex.location.ui

import android.content.Intent
import android.os.Bundle
import android.text.method.TextKeyListener.Capitalize
import android.widget.Toast
import com.alex.location.bean.ApiBean
import com.alex.location.databinding.ActivityLoginBinding
import com.alex.location.bean.User
import com.alex.location.ui.base.BaseActivity
import com.google.firebase.database.FirebaseDatabase

/**
 *  Login Page
 */
class LoginActivity : BaseActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    var dbinstanse = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        dbinstanse.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var newuser = snapshot.getValue(User::class.java);
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//        })
        binding.signup.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.login.setOnClickListener {
            var username = binding.username.text;
            var password = binding.password.text;

            if (username == null || username.isEmpty()) {
                Toast.makeText(this@LoginActivity, "input error", Toast.LENGTH_SHORT).show();
            } else if (password == null || password.isEmpty()) {
                Toast.makeText(this@LoginActivity, "input error", Toast.LENGTH_SHORT).show();
            } else {
                dbinstanse.child("users").child(username.toString()).get().addOnSuccessListener {
                    // 用户名不存在
                    if (it.value == null) {
                        Toast.makeText(this@LoginActivity, "user not exist", Toast.LENGTH_SHORT)
                            .show();
                    } else {
                        var serveruser = it.getValue(User::class.java);
                        if (serveruser?.password.equals(password.toString())) {
                            Toast.makeText(this@LoginActivity, "login success", Toast.LENGTH_SHORT)
                                .show();
                            App.currentUser = serveruser;
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "password error", Toast.LENGTH_SHORT)
                                .show();
                        }
                    }
                }.addOnFailureListener {
                    Toast.makeText(this@LoginActivity, "net error", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}