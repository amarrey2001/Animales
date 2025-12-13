package com.example.animales.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animales.databinding.ItemAnimalBinding
import com.example.animales.models.Animal

/**
 * Adaptador para el RecyclerView que muestra la lista de animales.
 *
 * @param listaDeAnimales La lista de animales a mostrar.
 * @param onDelete Lambda que se ejecuta cuando se pulsa el botón de eliminar. Recibe la posición del elemento.
 * @param onEdit Lambda que se ejecuta cuando se pulsa el botón de editar. Recibe el objeto Animal a editar.
 */
class AdapterAnimal (
    private val listaDeAnimales: List<Animal>,
    private val onDelete: (Int) -> Unit,
    private val onEdit: (Animal) -> Unit

) : RecyclerView.Adapter<ViewHAnimal>() {

    /**
     * Crea y devuelve un ViewHolder para un elemento de la lista.
     *
     * @param parent El ViewGroup en el que se inflará la nueva vista.
     * @param viewType El tipo de vista del nuevo elemento.
     * @return Un nuevo [ViewHAnimal] que contiene la vista para un elemento.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHAnimal{
        val binding = ItemAnimalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHAnimal(binding)
    }

    /**
     * Devuelve el número total de elementos en la lista.
     */
    override fun getItemCount(): Int {
        return listaDeAnimales.size
    }

    /**
     * Vincula los datos de un animal en una posición específica con el ViewHolder.
     *
     * @param holder El ViewHolder que debe ser actualizado.
     * @param position La posición del elemento en la lista.
     */
    override fun onBindViewHolder(holder: ViewHAnimal, position: Int) {
        val animal = listaDeAnimales[position]
        // Renderiza los datos del animal en la vista
        holder.render(animal)
        // Configura el listener para el botón de editar
        holder.binding.btnEdit.setOnClickListener {
            onEdit(animal)
        }
        // Configura el listener para el botón de eliminar
        holder.binding.btnDelete.setOnClickListener {
            onDelete(position)
        }
    }
}