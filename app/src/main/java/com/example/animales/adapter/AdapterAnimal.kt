package com.example.animales.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animales.databinding.ItemAnimalBinding
import com.example.animales.models.Animal

class AdapterAnimal (
    private val listaDeAnimales: List<Animal>,
    private val onDelete: (Int) -> Unit,
    private val onEdit: (Animal) -> Unit

) : RecyclerView.Adapter<ViewHAnimal>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHAnimal{
        val binding = ItemAnimalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHAnimal(binding)
    }

    override fun getItemCount(): Int {
        return listaDeAnimales.size
    }

    override fun onBindViewHolder(holder: ViewHAnimal, position: Int) {
        val animal = listaDeAnimales[position]
        holder.render(animal)
        holder.binding.btnEdit.setOnClickListener {
            onEdit(animal)
        }
        holder.binding.btnDelete.setOnClickListener {
            onDelete(position)
        }
    }


}