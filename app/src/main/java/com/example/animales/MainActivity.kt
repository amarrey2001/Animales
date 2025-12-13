package com.example.animales // Asegúrate de que sea tu paquete

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.animales.UI.EditDialogFragment
import com.example.animales.adapter.AdapterAnimal
import com.example.animales.controller.Controller
import com.example.animales.databinding.ActivityMainBinding
import com.example.animales.models.Animal
import com.example.animales.objects_models.Animales

/**
 * Actividad principal de la aplicación.
 *
 * Muestra una lista horizontal de animales y permite al usuario añadir, editar y eliminar elementos.
 */
class MainActivity : AppCompatActivity() {
    // Objeto de View Binding para acceder a las vistas del layout de forma segura.
    private lateinit var binding: ActivityMainBinding
    // Adaptador para el RecyclerView que gestiona la lista de animales.
    private lateinit var adapter: AdapterAnimal
    // Controlador que maneja la lógica de negocio (CRUD de animales).
    private lateinit var controller: Controller
    // Lista mutable que contiene los animales mostrados en el RecyclerView.
    private var listaAnimales: MutableList<Animal> = mutableListOf()

    /**
     * Método que se llama al crear la actividad.
     *
     * @param savedInstanceState Estado previamente guardado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa el controlador
        controller = Controller(this)

        // Carga la lista inicial de animales
        listaAnimales = Animales.listaDeAnimales.toMutableList()

        // Configura el RecyclerView
        setUpRecyclerView()

        // Configura el botón para añadir un nuevo animal
        binding.btnAniadir.setOnClickListener {
            val animalVacio = Animal("", "", "", "")

            // Muestra un diálogo para editar/crear un animal.
            // Al confirmar, se añade el nuevo animal a la lista.
            val dialog = EditDialogFragment(animalVacio) { nuevoAnimal ->
                controller.agregarAnimal(listaAnimales, nuevoAnimal)
                val position = listaAnimales.size - 1
                adapter.notifyItemInserted(position) // Notifica al adaptador sobre el nuevo elemento

                binding.recyclerAnimales.scrollToPosition(position) // Desplaza la vista al nuevo animal
            }

            dialog.show(supportFragmentManager, "EditDialog")
        }
    }

    /**
     * Configura el RecyclerView, incluyendo el LayoutManager, el adaptador y el SnapHelper.
     */
    private fun setUpRecyclerView() {
        // Configura un LayoutManager horizontal.
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerAnimales.layoutManager = layoutManager

        // Inicializa el adaptador con la lista de animales y las lambdas para editar/eliminar.
        adapter = AdapterAnimal(
            listaDeAnimales = listaAnimales,

            // Lambda que se ejecuta al pulsar el botón de eliminar.
            onDelete = { position ->
                val animalAEliminar = listaAnimales[position]
                AlertDialog.Builder(this)
                    .setTitle("Confirmar borrado")
                    .setMessage("¿Estás seguro de que quieres eliminar a ${animalAEliminar.nombre}?")
                    .setPositiveButton("Aceptar") { _, _ ->
                        controller.borrarAnimal(listaAnimales, position)
                        adapter.notifyItemRemoved(position) // Notifica la eliminación
                        adapter.notifyItemRangeChanged(position, listaAnimales.size) // Actualiza las posiciones
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
            },
            // Lambda que se ejecuta al pulsar el botón de editar.
            onEdit = { animalAEditar ->
                val position = listaAnimales.indexOf(animalAEditar)

                if (position != -1) {
                    // Muestra el diálogo de edición con los datos del animal.
                    val dialog = EditDialogFragment(animalAEditar) { nuevoAnimal ->
                        controller.editarAnimal(listaAnimales, position, nuevoAnimal)
                        adapter.notifyItemChanged(position) // Notifica que el elemento ha cambiado
                    }
                    dialog.show(supportFragmentManager, "EditDialog")
                }
            }
        )

        binding.recyclerAnimales.adapter = adapter

        // Añade un PagerSnapHelper para que el carrusel se centre en un elemento a la vez.
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerAnimales)
    }
}