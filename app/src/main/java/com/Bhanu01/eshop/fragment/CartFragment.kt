package com.Bhanu01.eshop.fragment
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.Bhanu01.eshop.activity.AddressActivity
import com.Bhanu01.eshop.activity.CategoryActivity
import com.Bhanu01.eshop.adapter.CartAdapter
import com.Bhanu01.eshop.databinding.FragmentCartBinding
import com.Bhanu01.eshop.roomdb.AppDatabase
import com.Bhanu01.eshop.roomdb.ProductModel

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var list : ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentCartBinding.inflate(layoutInflater)
        val preference = requireContext().getSharedPreferences("info" , AppCompatActivity.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart" , false)
        editor.apply()

        val dao = AppDatabase.getInstance(requireContext()).productDao()

        list = ArrayList()

        dao .getAllProducts().observe(requireActivity()){
            binding.cartRecycler.adapter = CartAdapter(requireContext(), it)


            list.clear()
            for ( data in it ) {
                list.add( data.productId)
            }

           totalCost(it)

        }
        return binding.root
    }

    private fun totalCost(data: List<ProductModel>?) {
        var total = 0

        for (item in data!!) {
            val productSpValue = item.productSp?.toIntOrNull() ?: 0
            total += productSpValue

        }

        binding.textView12.text = "Total item in cart is : ${data.size}"
        binding.textView13.text = "Total Cost : $total"

        binding.checkout.setOnClickListener {
            val intent = Intent(context, AddressActivity::class.java)
            intent.putExtra("totalCost", total)
            intent.putExtra("productIds", list)
            startActivity(intent)
        }
    }

}





