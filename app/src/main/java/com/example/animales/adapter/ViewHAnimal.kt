package com.example.animales.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animales.databinding.ItemAnimalBinding
import com.example.animales.models.Animal

/**
 * ViewHolder para un elemento de la lista de animales.
 *
 * Se encarga de vincular los datos de un [Animal] con las vistas del layout [ItemAnimalBinding].
 *
 * @param binding El objeto de View Binding para el layout del item.
 */
class ViewHAnimal (val binding: ItemAnimalBinding) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Rellena las vistas del layout con los datos de un animal.
     *
     * @param animal El objeto [Animal] que contiene los datos a mostrar.
     */
    fun render(animal: Animal) {
        // Asigna los datos del animal a los TextViews correspondientes.
        binding.txtNombre.text = animal.nombre
        binding.txtEspecie.text = animal.especie
        binding.txtDescripcion.text = animal.descripcion

        // Carga la imagen desde la URL usando Glide.
        Glide.with(binding.imageView.context).load(animal.urlImagen).into(binding.imageView)
    }
}
