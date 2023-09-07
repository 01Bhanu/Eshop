package com.Bhanu01.eshop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.Bhanu01.eshop.activity.ProductDetailActivity
import com.Bhanu01.eshop.databinding.ItemCategoryProductLayoutBinding
import com.Bhanu01.eshop.databinding.LayoutProductItemBinding
import com.Bhanu01.eshop.model.AddProductModel
import com.bumptech.glide.Glide

class CategoryProductAdapter (val context: Context, val list : ArrayList<AddProductModel>)
    : RecyclerView.Adapter<CategoryProductAdapter.CategoryProductViewHolder>(){

    inner class CategoryProductViewHolder(val binding : ItemCategoryProductLayoutBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryProductViewHolder {
        val binding= ItemCategoryProductLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return CategoryProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: CategoryProductViewHolder, position: Int) {
       Glide.with(context).load(list[position].productImg).into(holder.binding.imageView3)

        holder.binding.textView5.text = list[position].productName
        holder.binding.textView6.text = list[position].productSp

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity :: class.java)
            intent.putExtra("id" , list[position].productId)
            context.startActivity(intent)
        }
    }

}