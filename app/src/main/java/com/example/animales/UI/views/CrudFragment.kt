package com.example.animales.UI.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.animales.UI.adapter.AdapterAnimal
import com.example.animales.UI.modelview.AnimalViewModel
import com.example.animales.databinding.FragmentCrudBinding
import com.example.animales.domain.models.Animal
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragmento que muestra y gestiona la funcionalidad principal del CRUD de animales.
 *
 * Presenta una lista de animales en un carrusel horizontal (`RecyclerView`)
 * y permite al usuario añadir, editar y eliminar animales.
 */
@AndroidEntryPoint
class CrudFragment : Fragment() {

    private var _binding: FragmentCrudBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: AdapterAnimal
    private val viewModel: AnimalViewModel by viewModels()

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

        setUpRecyclerView()

        viewModel.animales.observe(viewLifecycleOwner) { animales ->
            adapter.updateList(animales)
        }

        binding.btnAniadir.setOnClickListener {
            val animalVacio = Animal("", "", "", "")
            val dialog = EditDialogFragment(animalVacio) { nuevoAnimal ->
                viewModel.addAnimal(nuevoAnimal)
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
            listaDeAnimales = mutableListOf(),
            onDelete = { position ->
                val animalAEliminar = viewModel.animales.value?.getOrNull(position)
                if (animalAEliminar != null) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Confirmar borrado")
                        .setMessage("¿Estás seguro de que quieres eliminar a ${animalAEliminar.nombre}?")
                        .setPositiveButton("Aceptar") { _, _ ->
                            viewModel.deleteAnimal(position)
                        }
                        .setNegativeButton("Cancelar", null)
                        .show()
                }
            },
            onEdit = { animalAEditar ->
                val position = viewModel.animales.value?.indexOf(animalAEditar) ?: -1
                if (position != -1) {
                    val dialog = EditDialogFragment(animalAEditar) { nuevoAnimal ->
                        viewModel.updateAnimal(position, nuevoAnimal)
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
