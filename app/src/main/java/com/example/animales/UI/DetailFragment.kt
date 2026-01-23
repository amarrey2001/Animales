package com.example.animales.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.animales.databinding.FragmentDetailBinding
import com.example.animales.objects_models.Animales

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()

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
        val animal = Animales.listaDeAnimales[position]

        binding.tvNombreDetail.text = animal.nombre
        binding.tvEspecieDetail.text = "Especie: ${animal.especie}"
        binding.tvDescripcionDetail.text = animal.descripcion

        
        Glide.with(this)
            .load(animal.urlImagen)
            .into(binding.ivAnimalDetail)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
