package com.Bhanu01.eshop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.Bhanu01.eshop.MainActivity
import com.Bhanu01.eshop.R
import com.Bhanu01.eshop.databinding.ActivityProductDetailBinding
import com.Bhanu01.eshop.roomdb.AppDatabase
import com.Bhanu01.eshop.roomdb.ProductDao
import com.Bhanu01.eshop.roomdb.ProductModel
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductDetailBinding.inflate(layoutInflater)

        getProductDetails(intent.getStringExtra("id"))

        setContentView(binding.root)
    }

    private fun getProductDetails(productId: String?) {

        Firebase.firestore.collection("products")
            .document(productId!!).get().addOnSuccessListener {
                val list = it.get("productImages") as ArrayList<String>
                val name =   it.getString("productName")
                val productSp =    it.getString("productSp")
                val productdescription =   it.getString("productDescription")

                 binding.textView7.text = name
                 binding.textView8.text = productSp
                 binding.textView9.text = productdescription


                val slidelist = ArrayList<SlideModel>()
                for(data in list){
                    slidelist.add(SlideModel(data,ScaleTypes.CENTER_INSIDE))
                }


                cartAction(productId , name , productSp, it.getString("productImg"))

                binding.imageSlider.setImageList(slidelist)

            }.addOnFailureListener {

            Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()

            }

    }

    private fun cartAction(productId: String, name: String?, productSp: String?, coverImg: String?) {

        val productDao = AppDatabase.getInstance(this).productDao()

        if(productDao.isExit(productId)!= null){
           binding . textView10.text = "Go To Cart"

        }else{
            binding . textView10.text = "Add To Cart"
        }

        binding.textView10.setOnClickListener {

            if(productDao.isExit(productId)!= null){
                openCart()

            }else{
               addToCart(productDao, productId ,name ,  productSp , coverImg)

            }
        }

    }

    private fun addToCart(
        productDao: ProductDao,
        productId: String,
        name: String?,
        productSp: String?,
        coverImg: String?
    ){
        val data = ProductModel( productId , name , coverImg , productSp)
        lifecycleScope.launch (Dispatchers.IO){
            productDao.insertProduct(data)
            binding . textView10.text = "Go To Cart"
        }



    }

    private fun openCart() {
        val preference = this.getSharedPreferences("info" , MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart" , true)
        editor.apply()

        startActivity(Intent(this , MainActivity :: class.java))
        finish()
    }
}