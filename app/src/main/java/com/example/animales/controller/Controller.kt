package com.example.animales.controller

import android.content.Context
import android.widget.Toast
import com.example.animales.models.Animal

/**
 * Controlador que gestiona la lógica de negocio para las operaciones con animales.
 *
 * @param context El contexto de la aplicación, usado para mostrar Toasts.
 */
class Controller(private val context: Context) {

    /**
     * Elimina un animal de la lista en una posición específica.
     *
     * @param listaAnimales La lista de la que se eliminará el animal.
     * @param position La posición del animal a eliminar.
     */
    fun borrarAnimal(listaAnimales: MutableList<Animal>, position: Int) {
        val animal = listaAnimales[position]
        listaAnimales.removeAt(position)
        Toast.makeText(context, "Borrado: ${animal.nombre}", Toast.LENGTH_SHORT).show()
    }

    /**
     * Edita un animal en una posición específica de la lista.
     *
     * @param listaAnimales La lista en la que se editará el animal.
     * @param position La posición del animal a editar.
     * @param animalEditado El animal con los datos actualizados.
     */
    fun editarAnimal(listaAnimales: MutableList<Animal>, position: Int, animalEditado: Animal) {
        // Actualizamos la lista en esa posición
        listaAnimales[position] = animalEditado
        Toast.makeText(context, "Editado correctamente", Toast.LENGTH_SHORT).show()
    }

    /**
     * Agrega un nuevo animal a la lista.
     *
     * @param listaAnimales La lista a la que se agregará el nuevo animal.
     * @param nuevoAnimal El nuevo animal a agregar.
     */
    fun agregarAnimal(listaAnimales: MutableList<Animal>, nuevoAnimal: Animal) {
        listaAnimales.add(nuevoAnimal)
        Toast.makeText(context, "Añadido: ${nuevoAnimal.nombre}", Toast.LENGTH_SHORT).show()
    }
}