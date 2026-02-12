package com.example.animales.UI.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.animales.UI.modelview.AnimalViewModel
import com.example.animales.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: AnimalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = args.animalPosition
        
        viewModel.animales.observe(viewLifecycleOwner) { animales ->
            val animal = animales.getOrNull(position)
            animal?.let {
                binding.tvNombreDetail.text = it.nombre
                binding.tvEspecieDetail.text = "Especie: ${it.especie}"
                binding.tvDescripcionDetail.text = it.descripcion

                Glide.with(this)
                    .load(it.urlImagen)
                    .into(binding.ivAnimalDetail)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
