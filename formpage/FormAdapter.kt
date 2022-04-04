package com.servicefinder.pilotonboarding.formpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.servicefinder.pilotonboarding.Fields
import com.servicefinder.pilotonboarding.R

class FormAdapter(): RecyclerView.Adapter<FormAdapter.FormViewHolder>() {
    private val formList = mutableListOf<Fields>()
    inner class FormViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val question = itemView.findViewById<TextView>(R.id.question)
        val answer = itemView.findViewById<EditText>(R.id.answer)
        fun bind(position: Int){
            val data = formList.get(position)
            var string1 = "Q${position}." + data.text
            if(data.mandatory == false){
                string1+="(optional)"
            }
            question.text = string1
        }
    }

    fun setData(listFormData: List<Fields>){
        formList.clear()
        formList.addAll(listFormData)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
       return FormViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_fields_form, parent, false))
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        holder.setIsRecyclable(false)
       holder.bind(position)
    }

    override fun getItemCount(): Int {
        return formList.size
    }

}