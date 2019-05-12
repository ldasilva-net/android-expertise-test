# Liber Da Silva - Android expertise example

[![ldasilva.net logo](https://assets.ldasilva.net/img/logo.png)](https://ldasilva.net)

Aplicación de demostración de conocimientos en desarrollo Android SDK. 

# Funcionalidad
- Lista peliculas por categorías Most Popular, Top rated y Upcoming utilizando la base de datos de The Movie DB.
- Al seleccionar una película se podrá visualizar su detalle y descripción completa.
- Posee un buscador online de péliculas.
- Toda la aplicación y su buscador funcionan tanto online como offline. Esto lo identifica automáticamente, es decir, funciona siempre online y si detecta que no hay conexión automaticamente consume la información de forma local.

# Técnicas y tecnologías
- [MVVM] - Arquitectura MVVM con Repository como ***"single source of truth"***.
- [Kotlin] - Aplicación escrita integramente en este lenguaje.
- [Kodein] - Inyección de dependencias para la separación efectiva entre capas e independencia total de componentes.
- [Kotlin Coroutines] - Manejo de tareas asíncronas.
- [LiveData] - Para comunicación entre componentes a través del patrón Observer.
- [Navigation] - Para navegación entre vistas.
- [Room] - Para persistencia de datos.
- [Glide] - Manejo de descarga y cache de imágenes.
- [Groupie] - Para el manejo de ListViews.
- [Retrofit] y [GSON] - Para comunicación con la API de The Movie DB.

# Capas de aplicación

[![ldasilva.net logo](https://s17-us2.startpage.com/cgi-bin/serveimage?url=https:%2F%2Fcdn-images-1.medium.com%2Fmax%2F1200%2F1*Tt_OwtZJ993YzswuRyPQiA.png&sp=66100abec54b20aa81cadfed15aaf880)](https://ldasilva.net)

#### Explicación detallada y la responsabilidad de cada clase relevante:

-  **Vistas (paquete ui):** A partir de `MainActivity`, se renderiza la aplicación e inicializa el Navigator. Los Fragments principales (dentro del paquete list) se llamarán al seleccionar los distintos menúes. `MostPopularFragment`, `TopRatedFragment` y `UpcomingFragment` heredan de `MovieListFragment`, clase abstracta que resuelve toda la lógica de los 3. La vista de detalle es `MovieDetailFragment`. Por último es importante nombrar a `ScopedFragment`, de quien heredan todos los Fragments, como Helper para el manejo de los coroutines scopes para evitar problemas con los ciclos de vida de un Fragment.
-  **ViewModel (paquete ui):** Las clases son `MovieDetailViewModel` y `MovieListViewModel`.
-  **Repository (paquete data.repository):** Interfaz `MovieRepository` y su implementación en clase `MovieRepositoryImpl`.
-  **Model (paquete data.db):** Base de datos administrada con Room. Las clases de configuración son `ExpertiseTestDatabase` y la clase de entidad `MovieEntry`. Métodos de acceso y grabado en interfaz `MovieDao`.
-  **Remote Data Source (paquete data.network):** Configuración de endpoints en la interfaz `TheMovieDbApiService` y a manejo través de la Interfaz `MovieDataSource` y su implementación en clase `MovieDataSourceImpl`.

# Preguntas de entrevista

**¿En qué consiste el principio de responsabilidad única? ¿Cuál es su propósito?**
> Consiste en crear arquitecturas donde cada componentes tenga una única responsabilidad sobre una parte de la funcionalidad del sistema. Separando bien las capas de la aplicación y atomizando las clases a una única función, tendremos código facil de leer, testear y mantener.

**¿Qué características tiene, según su opinión, un “buen” código o código limpio?**
> Un buen código tiene que ser sencillo de interpretar y seguir. Los nombres de las clases, propiedades, métodos y funciones tienen que hacer clara referencia a lo que son y hacen. La capa de paquetes y la organización de los archivos debe ser clara y estructurada. Las clases deben ser atómicas y el criterio acercarse lo mas posible al principio de reponsabilidad única. Los métodos por ende no pueden ser kilométricos, mejor que crear algo grande y comentado es seccionar cada funcionalidad en funciones con nombres claros. 
> Un buen código entonces, tiene que tener la claridad tal que cualquier desarrollador pueda entenderlo, continuarlo y mantenerlo.


## ToDo
- Crear tests unitarios.
- Readme en inglés.
- Incluir programas de televisión.

License
----

MIT

[MVVM]: <https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel>
[Kotlin]: <https://kotlinlang.org/>
[Kodein]: <http://kodein.org/Kodein-DI/>
[Kotlin Coroutines]: <https://kotlinlang.org/docs/reference/coroutines-overview.html>
[LiveData]: <https://developer.android.com/topic/libraries/architecture/livedata>
[Navigation]: <https://developer.android.com/guide/navigation>
[Room]: <https://developer.android.com/topic/libraries/architecture/room>
[Glide]: <https://bumptech.github.io/glide/>
[Groupie]: <https://github.com/lisawray/groupie>
[Retrofit]: <https://square.github.io/retrofit/>
[GSON]: <https://github.com/google/gson>