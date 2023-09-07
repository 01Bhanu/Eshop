package com.Bhanu01.eshop.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.Bhanu01.eshop.R
import com.Bhanu01.eshop.adapter.CategoryProductAdapter
import com.Bhanu01.eshop.adapter.ProductAdapter
import com.Bhanu01.eshop.model.AddProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        getProducts(intent.getStringExtra("text"))

    }

    private fun getProducts(Category: String?) {
        val list = ArrayList<AddProductModel>()
        Firebase.firestore.collection("products").whereEqualTo("productCategory",Category)
            .get().addOnSuccessListener {
                list.clear()
                for(doc in  it.documents){

                    var data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
               recyclerView.adapter= CategoryProductAdapter(this,list)
            }
    }

}