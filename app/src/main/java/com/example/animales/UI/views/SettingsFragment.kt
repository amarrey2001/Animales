package com.example.animales.UI.views

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.animales.UI.modelview.UserViewModel
import com.example.animales.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by activityViewModels()

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            userViewModel.updateProfileImage(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observamos los cambios en el ViewModel compartido
        userViewModel.username.observe(viewLifecycleOwner) { name ->
            if (binding.etUsernameSettings.text.toString() != name) {
                binding.etUsernameSettings.setText(name)
            }
        }

        userViewModel.profileImageUri.observe(viewLifecycleOwner) { uri ->
            uri?.let {
                Glide.with(this)
                    .load(it)
                    .circleCrop()
                    .into(binding.imgProfileSettings)
            } ?: run {
                binding.imgProfileSettings.setImageResource(android.R.drawable.ic_menu_gallery)
            }
        }

        binding.imgProfileSettings.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.btnSaveSettings.setOnClickListener {
            val newName = binding.etUsernameSettings.text.toString()
            if (newName.isNotBlank()) {
                userViewModel.updateUsername(newName)
                Toast.makeText(requireContext(), "Cambios guardados correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
