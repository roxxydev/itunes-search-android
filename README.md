# ITunes Search

The project is written in Kotlin following MVI/MVVM design pattern. The app consumes the Search API of ITunes store for searching content like movies, books, podcasts, music, music videos, audiobooks, and TV shows within ITunes Store.

<img src="/output/app-preview.gif" width="30%"/>

![Download](/output/itunes-search-androd_1.0.0.apk?raw=true "")

### Design Pattern
The project used MVI and Repository design pattern approach. State in app is defined by user's action which is called intent _(not the android Intent class)_ which the ViewModel will get and decide the state to be reflected to the View.

#### Libraries
* [Hilt](https://dagger.dev/hilt/) - For dependency injection and lessens setup required with Dagger.
* [Room](https://developer.android.com/jetpack/androidx/releases/room) - Used for data caching as this is included already in android Jetpack.
* [Retrofit](https://square.github.io/retrofit/) - API http network requests.
* [OkHttp](https://square.github.io/okhttp/) - Use OkHttp for logging interceptor of http request for debugging purposes.
* [Moshi](https://github.com/square/moshi) - For serialization from JSON. Used Moshi instead of GSON as Moshi is much faster than GSON and smaller than GSON which means produced apk build is smaller.
* [Navigation Component](https://developer.android.com/guide/navigation) - For handling fragments and simplify app's navigation between screens.
* [Timber](https://github.com/JakeWharton/timber) - For better logging and handling of logs for crash reporting.
* [Glide](https://github.com/bumptech/glide) - For fast and efficient image loading to views.
* [Material Design](https://material.io/) - For implementing Google's material design ui across views in the app.

#### Setup
Run the following command to build the project
```
./gradlew build
```

#### Installation
Installing apk to device can be done with the following commands. Note that debug apk is used in the commands.
- via Gradle
```
./gradlew installDebug
```
- via adb tool
```
adb install build/outputs/apk/debug/app-debug.apk
```

#### Linting
Lint issues or warnings can be checked by running
```
./gradlew lint
```