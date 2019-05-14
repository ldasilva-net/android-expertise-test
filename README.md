# Liber Da Silva - Android expertise example

[![ldasilva.net logo](https://assets.ldasilva.net/img/logo.png)](https://ldasilva.net)

[Spanish version](README-es.md)

Example app with latest development standards in Android SDK and Kotlin

# Description
- List movies by Most Popular, Top rated and Upcoming categories using The Movie DB database.
- Movie details and full description.
- Online movie search engine.
- The whole app and its search engine works in online and offline mode. It always works online but if it detects no connection it automatically consumes the local information.

# Technologies and techniques
- [MVVM] - MVVM architecture with Repository as ***"single source of truth"***.
- [Kotlin] - Written entirely in this language.
- [Kodein] - Dependencie injection for effective layers separation and total independence of components.
- [Kotlin Coroutines] - Asynchronous tasks management.
- [LiveData] - For communication between components through the Observer pattern.
- [Navigation] - For navigation between views.
- [Room] - For data persistence.
- [Glide] - An image loading and caching library.
- [Groupie] - Manage complex RecyclerView layouts.
- [Retrofit] y [GSON] - For TheMovieDB API consume.

# Application layers
[![Layers](https://s17-us2.startpage.com/cgi-bin/serveimage?url=https:%2F%2Fcdn-images-1.medium.com%2Fmax%2F1200%2F1*Tt_OwtZJ993YzswuRyPQiA.png&sp=66100abec54b20aa81cadfed15aaf880)](https://ldasilva.net)

#### Detailed explanation and responsibility of relevant classes
-  **Views (ui package):** `MainActivity` render the app and initialize Navigator. The main Fragments (in list package) will be called when the different categories are selected. `MostPopularFragment`, `TopRatedFragment` and `UpcomingFragment` inherit from` MovieListFragment`, abstract class that solves all movie lists logic. The detail view is `MovieDetailFragment`. Finally it's important to note `ScopedFragment` as Helper for handling the coroutines scopes to avoid problems with the life cycles of a Fragments.
-  **ViewModels (ui package):** `MovieDetailViewModel` and `MovieListViewModel` classes.
-  **Repository (data.repository package):** `MovieRepository` is the interface and `MovieRepositoryImpl` its implementation.
-  **Model (data.db package):** SQLite database managed with Room library. The DB configuration class is `ExpertiseTestDatabase` and `MovieEntry` is the movie entity. `MovieDao` interface has the methods and its database scripts representation.
-  **Remote Data Source (data.network package):** `TheMovieDbApiService` has the endpoints config. `MovieDataSource` is the data source interface and `MovieDataSourceImpld` its implementation.

## ToDo
- Add unit tests.
- Add tv shows.

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