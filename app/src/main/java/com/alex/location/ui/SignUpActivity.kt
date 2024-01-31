package com.alex.location.ui

import android.os.Bundle
import android.widget.Toast
import com.alex.location.databinding.ActivitySignUpBinding
import com.alex.location.bean.User
import com.alex.location.ui.base.BaseActivity
import com.google.firebase.database.FirebaseDatabase

/**
 *  Sign Up Page
 */
class SignUpActivity : BaseActivity() {

    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    var dbinstanse = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.login.setOnClickListener {
            var username = binding.username.text;
            var password = binding.password.text;

            if (username == null || username.isEmpty()) {
                Toast.makeText(this@SignUpActivity, "input error", Toast.LENGTH_SHORT).show();
            } else if (password == null || password.isEmpty()) {
                Toast.makeText(this@SignUpActivity, "input error", Toast.LENGTH_SHORT).show();
            } else {
                var user = User(username.toString(), password.toString(), "", "")
                dbinstanse.child("users").child(username.toString()).get().addOnSuccessListener {
                    // User names are not duplicated and can be registered
                    if (it.value == null) {
                        dbinstanse.child("users").child(username.toString()).setValue(user)
                        Toast.makeText(this@SignUpActivity, "sign up success", Toast.LENGTH_SHORT)
                            .show();
                        finish()
                    } else {
                        Toast.makeText(this@SignUpActivity,
                            "sign up error, username exist",
                            Toast.LENGTH_SHORT).show();
                    }
                }.addOnFailureListener {
                    Toast.makeText(this@SignUpActivity, "sign up error", Toast.LENGTH_SHORT).show();
                }
            }
        }
        binding.btnBack.setOnClickListener { finish() }
    }

}