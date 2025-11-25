package com.example.animales // Asegúrate de que sea tu paquete

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.animales.adapter.AdapterAnimal
import com.example.animales.controller.Controller
import com.example.animales.databinding.ActivityMainBinding
import com.example.animales.models.Animal
import com.example.animales.objects_models.Animales

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AdapterAnimal
    private lateinit var controller: Controller
    private var listaAnimales: MutableList<Animal> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller = Controller(this)

        listaAnimales = Animales.listaDeAnimales.toMutableList()

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerAnimales.layoutManager = layoutManager

        adapter = AdapterAnimal(
            listaDeAnimales = listaAnimales,

            onDelete = { position ->
                                controller.borrarAnimal(listaAnimales,position)
                                adapter.notifyItemRemoved(position)
                                adapter.notifyItemRangeChanged(position, listaAnimales.size)

                       },
            onEdit = { animal -> controller.editarAnimal(animal) }
        )

        binding.recyclerAnimales.adapter = adapter


        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerAnimales)
    }
}