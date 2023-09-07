package com.Bhanu01.eshop.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.Bhanu01.eshop.databinding.ActivityAddressBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddressActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddressBinding
 private lateinit var preferences :SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddressBinding.inflate(layoutInflater)

        setContentView(binding.root)
      preferences  = this.getSharedPreferences("user", MODE_PRIVATE)

        loadUserInfo()

        binding.proceed.setOnClickListener {
            validateData(
                binding.userName.text.toString(),
                binding.userNumber.text.toString(),
                binding.userVillage.text.toString(),
                binding.userCity.text.toString(),
                binding.userState.text.toString(),
                binding.userPinCode.text.toString()
            )
        }
    }

    private fun validateData(name: String, number: String, village: String, city: String, state: String, pinCode: String) {

        if (number.isEmpty() || state.isEmpty() || name.isEmpty() || village.isEmpty() || city.isEmpty() || pinCode.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }else{

            storeData(village,city,state,pinCode)
        }
    }

    private fun storeData( village: String, city: String, state: String, pinCode: String) {

        val map = hashMapOf<String,Any>()
        map["village"] = village
        map["city"] = city
        map["state"] = state
        map["pinCode"] = pinCode

        Firebase.firestore.collection("user")
            .document(preferences.getString("number","")!!)
            .update(map).addOnSuccessListener {
                startActivity(Intent(this,CheckOutActivity::class.java))

            }
            .addOnFailureListener {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadUserInfo() {

        Firebase.firestore.collection("user")
            .document(preferences.getString("number","")!!)
            .get().addOnSuccessListener {
                binding.userName.setText(it.getString("userName"))
                binding.userNumber.setText(it.getString("userNumber"))
                binding.userVillage.setText(it.getString("village"))
                binding.userCity.setText(it.getString("city"))
                binding.userState.setText(it.getString("state"))
                binding.userPinCode.setText(it.getString("pinCode"))

            }


    }
}