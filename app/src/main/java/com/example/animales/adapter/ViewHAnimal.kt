package com.example.animales.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animales.databinding.ItemAnimalBinding
import com.example.animales.models.Animal

class ViewHAnimal (val binding: ItemAnimalBinding) : RecyclerView.ViewHolder(binding.root) {
    fun render(animal: Animal) {
        binding.txtNombre.text = animal.nombre
        binding.txtEspecie.text = animal.especie
        binding.txtDescripcion.text = animal.descripcion

        Glide.with(binding.imageView.context).load(animal.urlImagen).into(binding.imageView)


    }
}
