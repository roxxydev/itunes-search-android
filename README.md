# ITunes Search

The project is written in Kotlin following MVI/MVVM design pattern. The app consumes the Search API of ITunes store for searching content like movies, books, podcasts, music, music videos, audiobooks, and TV shows within ITunes Store.

### Design Pattern
The project used MVI and Repository design pattern approach.

#### Libraries
* [Hilt](https://dagger.dev/hilt/) - For dependency injection
* [Room](https://developer.android.com/jetpack/androidx/releases/room) - Used for data caching
* [Retrofit](https://square.github.io/retrofit/) - API http network requests
* [Navigation Component](https://developer.android.com/guide/navigation) - For handling fragment transactions
* [Timber](https://github.com/JakeWharton/timber) - For better logging and handling of logs for crash reporting

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