package com.Bhanu01.eshop.adapter
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.Bhanu01.eshop.R
import com.Bhanu01.eshop.activity.CategoryActivity
import com.Bhanu01.eshop.databinding.LayoutCategoryItemBinding
import com.Bhanu01.eshop.model.CategoryModel

import com.bumptech.glide.Glide

class CategoryAdapter(var context : Context, val list :ArrayList<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    inner class CategoryViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var binding = LayoutCategoryItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(context).inflate(R.layout.layout_category_item,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        holder.binding.textView2.text = list[position].text
        Glide.with(context).load(list[position].img).into(holder.binding.imageView)

        holder.itemView.setOnClickListener {
            val intent = Intent(context , CategoryActivity :: class.java)
            intent.putExtra("text" , list[position].text)
            context.startActivity(intent)

        }

    }
}