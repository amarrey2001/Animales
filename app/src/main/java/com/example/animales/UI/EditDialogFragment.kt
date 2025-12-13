package com.example.animales.UI

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.animales.R
import com.example.animales.models.Animal

/**
 * Un DialogFragment que muestra un formulario para editar o crear un animal.
 *
 * @param animal El animal a editar. Si se está creando uno nuevo, se pasa un objeto [Animal] vacío.
 * @param onGuardar Una función lambda que se ejecuta al pulsar el botón de guardar.
 *                  Recibe el [Animal] con los datos actualizados.
 */
class EditDialogFragment(private val animal: Animal, private val onGuardar: (Animal) -> Unit) : DialogFragment() {

    /**
     * Crea y configura el diálogo de alerta que se mostrará.
     *
     * @param savedInstanceState Estado previamente guardado del fragmento.
     * @return El [Dialog] creado.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.edit_animal, null)

        // Referencias a los EditText del layout.
        val etNombre = view.findViewById<EditText>(R.id.etNombre)
        val etEspecie = view.findViewById<EditText>(R.id.etEspecie)
        val etDescripcion = view.findViewById<EditText>(R.id.etDescripcion)
        val etUrl = view.findViewById<EditText>(R.id.etUrlImagen)

        // Rellena los campos con los datos del animal si se está editando.
        etNombre.setText(animal.nombre)
        etEspecie.setText(animal.especie)
        etDescripcion.setText(animal.descripcion)
        etUrl.setText(animal.urlImagen)

        builder.setView(view)
            // Cambia el título del diálogo dependiendo de si se crea o edita un animal.
            .setTitle(if (animal.nombre.isEmpty()) "Nuevo Animal" else "Editar Animal")
            // Configura el botón de guardar.
            .setPositiveButton("Guardar") { dialog, id ->
                // Crea un nuevo objeto Animal con los datos de los EditText.
                val nuevoAnimal = Animal(
                    etNombre.text.toString(),
                    etEspecie.text.toString(),
                    etDescripcion.text.toString(),
                    etUrl.text.toString()
                )

                // Ejecuta la función lambda pasada en el constructor.
                onGuardar(nuevoAnimal)
            }
            // Configura el botón de cancelar.
            .setNegativeButton("Cancelar") { dialog, id ->
                dialog.cancel()
            }
        return builder.create()
    }
}