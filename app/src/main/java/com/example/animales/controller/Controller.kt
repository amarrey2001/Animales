package com.example.animales.controller

import android.content.Context
import android.widget.Toast
import com.example.animales.models.Animal

class Controller(private val context: Context) {
    fun borrarAnimal (listaAnimales: MutableList<Animal>, position: Int) {
        val animal = listaAnimales[position]
        listaAnimales.removeAt(position)
        Toast.makeText(context, "Borrado: ${animal.nombre}", Toast.LENGTH_SHORT).show()
    }
    fun editarAnimal(animal: Animal){
        Toast.makeText(context, "Editando a ${animal.nombre}", Toast.LENGTH_SHORT).show()
    }
}