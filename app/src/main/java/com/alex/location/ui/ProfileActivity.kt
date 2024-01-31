package com.alex.location.ui

import android.os.Bundle
import android.widget.Toast
import com.alex.location.databinding.ActivityProfileBinding
import com.alex.location.ui.base.BaseActivity
import com.google.firebase.database.FirebaseDatabase

/**
 *  Profile Page
 */
class ProfileActivity : BaseActivity() {

    private val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }
    var dbinstanse = FirebaseDatabase.getInstance().reference

    var i: Int = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        i = intent.getIntExtra("mode", 0);
        changeColor()
        if (App.currentUser?.email?.isNotEmpty() == true) {
            binding.currentemail.text = "current email : " + App.currentUser?.email
        }
        // Click Event
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.bind.setOnClickListener {
            var email = binding.email.text;
            if (email == null || email.isEmpty() || !email.contains("@")) {
                Toast.makeText(this@ProfileActivity, "input error", Toast.LENGTH_SHORT).show();
            } else {
                dbinstanse.child("users").child(App.currentUser?.username.toString()).child("email")
                    .setValue(email.toString())
                Toast.makeText(this@ProfileActivity, "success", Toast.LENGTH_SHORT).show();
                App.currentUser?.email = email.toString()
                finish()
            }
        }
    }

    fun changeColor() {
        if (i % 2 == 0) {
            binding.layoutall.setBackgroundColor(resources.getColor(android.R.color.black))
            binding.bind.setTextColor(resources.getColor(android.R.color.black))
        } else {
            binding.layoutall.setBackgroundColor(resources.getColor(android.R.color.white))
            binding.bind.setTextColor(resources.getColor(android.R.color.white))
        }
    }


}