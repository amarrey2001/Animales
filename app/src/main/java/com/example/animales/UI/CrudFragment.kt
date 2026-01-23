package com.example.animales.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.animales.adapter.AdapterAnimal
import com.example.animales.controller.Controller
import com.example.animales.databinding.FragmentCrudBinding
import com.example.animales.models.Animal
import com.example.animales.objects_models.Animales

/**
 * Fragmento que muestra y gestiona la funcionalidad principal del CRUD de animales.
 *
 * Presenta una lista de animales en un carrusel horizontal (`RecyclerView`)
 * y permite al usuario añadir, editar y eliminar animales.
 */
class CrudFragment : Fragment() {

    private var _binding: FragmentCrudBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: AdapterAnimal
    private lateinit var controller: Controller
    private var listaAnimales: MutableList<Animal> = mutableListOf()

    /**
     * Infla la vista del fragmento y configura el View Binding.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrudBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Se llama inmediatamente después de que `onCreateView` ha retornado, pero antes
     * de que cualquier estado guardado haya sido restaurado en la vista.
     *
     * Aquí se inicializa la lógica principal, como el controlador, la lista de datos,
     * la configuración del RecyclerView y los listeners de los botones.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller = Controller(requireContext())
        listaAnimales = Animales.listaDeAnimales.toMutableList()
        setUpRecyclerView()

        binding.btnAniadir.setOnClickListener {
            val animalVacio = Animal("", "", "", "")
            val dialog = EditDialogFragment(animalVacio) { nuevoAnimal ->
                controller.agregarAnimal(listaAnimales, nuevoAnimal)
                val position = listaAnimales.size - 1
                adapter.notifyItemInserted(position)
                binding.recyclerAnimales.scrollToPosition(position)
            }
            dialog.show(parentFragmentManager, "EditDialog")
        }
    }

    /**
     * Configura el RecyclerView, incluyendo su LayoutManager, el adaptador y un `PagerSnapHelper`
     * para lograr el efecto de carrusel.
     *
     * Define las acciones de borrado y edición que se pasarán al adaptador.
     */
    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerAnimales.layoutManager = layoutManager

        adapter = AdapterAnimal(
            listaDeAnimales = listaAnimales,
            onDelete = { position ->
                val animalAEliminar = listaAnimales[position]
                AlertDialog.Builder(requireContext())
                    .setTitle("Confirmar borrado")
                    .setMessage("¿Estás seguro de que quieres eliminar a ${animalAEliminar.nombre}?")
                    .setPositiveButton("Aceptar") { _, _ ->
                        controller.borrarAnimal(listaAnimales, position)
                        adapter.notifyItemRemoved(position)
                        adapter.notifyItemRangeChanged(position, listaAnimales.size)
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
            },
            onEdit = { animalAEditar ->
                val position = listaAnimales.indexOf(animalAEditar)
                if (position != -1) {
                    val dialog = EditDialogFragment(animalAEditar) { nuevoAnimal ->
                        controller.editarAnimal(listaAnimales, position, nuevoAnimal)
                        adapter.notifyItemChanged(position)
                    }
                    dialog.show(parentFragmentManager, "EditDialog")
                }
            },
            onClick = { position ->
                val action = CrudFragmentDirections.actionCrudFragmentToDetailFragment(position)
                findNavController().navigate(action)
            }
        )

        binding.recyclerAnimales.adapter = adapter

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerAnimales)
    }

    /**
     * Se llama cuando la vista asociada con el fragmento está siendo destruida.
     *
     * Limpia la referencia al `_binding` para evitar fugas de memoria.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
