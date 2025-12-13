# Proyecto Animales

Este es un sencillo proyecto de Android que muestra una lista de animales en un carrusel horizontal. Permite al usuario añadir, editar y eliminar animales de la lista.

## Características

*   **Visualización de animales**: Muestra una lista de animales con su nombre, nombre científico, tipo y hábitat en tarjetas individuales.
*   **Carrusel horizontal**: La lista de animales se presenta en un `RecyclerView` horizontal con un efecto de paginación (`PagerSnapHelper`), permitiendo ver un animal a la vez.
*   **Añadir animales**: Un botón flotante permite añadir un nuevo animal a través de un cuadro de diálogo.
*   **Editar animales**: Cada tarjeta de animal tiene un botón para editar su información en el mismo cuadro de diálogo.
*   **Eliminar animales**: Cada tarjeta de animal tiene un botón para eliminarlo de la lista.

## Estructura del Proyecto

El proyecto sigue una arquitectura básica con las siguientes partes principales:

*   **`MainActivity.kt`**: La actividad principal que aloja el `RecyclerView` y maneja la lógica de la interfaz de usuario.
*   **`AdapterAnimal.kt`**: Adaptador para el `RecyclerView` que se encarga de mostrar cada animal y manejar los eventos de clic en los botones de editar y eliminar.
*   **`EditDialogFragment.kt`**: Un `DialogFragment` que proporciona un formulario para crear o editar la información de un animal.
*   **`Controller.kt`**: Una clase que gestiona la lógica de negocio (añadir, editar, borrar animales de la lista).
*   **`Animal.kt`**: El modelo de datos que representa a un animal.
*   **`item_animal.xml`**: El layout para cada elemento individual en el `RecyclerView`.
*   **`activity_main.xml`**: El layout principal de la aplicación.

## Tecnologías y Librerías

*   **Kotlin**: Lenguaje de programación principal.
*   **View Binding**: Para acceder a las vistas de forma segura.
*   **RecyclerView**: Para mostrar la lista de animales de forma eficiente.
*   **CardView**: Para dar a cada elemento de la lista un aspecto de tarjeta.
*   **Material Components**: Para los componentes de la interfaz de usuario, como el botón flotante.
*   **DialogFragment**: Para crear los diálogos de edición/creación.
