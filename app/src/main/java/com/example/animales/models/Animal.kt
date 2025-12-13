package com.example.animales.models

/**
 * Representa un animal con sus propiedades básicas.
 *
 * @property nombre El nombre común del animal.
 * @property especie El nombre científico o especie del animal.
 * @property descripcion Una breve descripción del animal.
 * @property urlImagen La URL de la imagen que representa al animal.
 */
data class Animal(
    val nombre: String,
    val especie: String,
    val descripcion: String,
    val urlImagen: String
)
