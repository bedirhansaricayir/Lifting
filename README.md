# Lifting(Under V2 Development)

> Lifting will be a tracking and logging-based app for workouts. This repository is currently under development and will soon be released as V2. The information below applies to V1

Lifting, helps users have a training program, track their progress with graphic analysis, and use a variety of measurement tools.

It's written using 100% Kotlin and Jetpack Compose. It's developed to help you track your personal progress with ease.

[![Google Play](https://github.com/bedirhansaricayir/Lifting/assets/110481044/4fe26a94-4280-4e29-adf9-49f8203d5f9e)](https://play.google.com/store/apps/details?id=com.lifting.app)


|   |  |   |  |  |
| ------------- | ------------- | ------------- | ------------- | ------------- |
| ![Hotpot 0](https://github.com/bedirhansaricayir/Lifting/assets/110481044/ab32ffce-da01-48a4-8947-4b80d6ac6be2)  | ![Hotpot 1](https://github.com/bedirhansaricayir/Lifting/assets/110481044/9dfb057e-5907-4208-b241-5efcadb40986)  | ![Hotpot 2](https://github.com/bedirhansaricayir/Lifting/assets/110481044/10c2643f-ef66-43b3-8636-c3aee22879dc)  | ![Hotpot 3](https://github.com/bedirhansaricayir/Lifting/assets/110481044/f0b6263a-4ba4-4469-b394-abbfcc2c8391)  | ![Hotpot 4](https://github.com/bedirhansaricayir/Lifting/assets/110481044/08c8bd28-d359-4781-9497-7faccb4b2741)  |

## Layers
* Common -> It contains certain components that are useful and stable for the application, independent of other layers.
* Navigation -> The screens present in the application and the navigation and state information triggered by user interaction are kept in this layer.
* DI -> It is a separated layer in the application that aims to eliminate external dependencies and automate these dependencies. The dependencies of all layers are executed through this layer.
* Notification -> Business logic related to setting alarms and sending notifications in the application is kept in this layer.
* Feature -> Each feature in the application creates its own layers for independence, testability and a modular approach.
    - Data -> Here, models are available for the local database, parsers, and data from the remote source.
    - Domain -> The domain layer of the application includes the fundamental and independent components such as the main logic, business rules, and data models. This layer does not communicate with other layers and remains independent from external dependencies.
    - Presentation -> It is the layer to interact with the user. This layer receives user inputs, triggers related actions and displays the results.
      ![mad-arch-overview-ui](https://github.com/bedirhansaricayir/Lifting/assets/110481044/02996945-7594-4903-968e-d493de8e60eb)

# 🛠 Tech Stack & Open Source Libraries
- 100% [Jetpack Compose](https://developer.android.com/jetpack/compose) based.
- [Material3](https://m3.material.io/)
- [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines) -> structured concurrency
- [Kotlin Flow](https://developer.android.com/kotlin/flow) -> reactive datastream
- [Kotlin Parcelize](https://developer.android.com/reference/kotlin/android/os/Parcelable) -> data sharing between screens
- Jetpack Libraries
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) -> Business Logic, UI related data holder and lifecycle aware.
  - [Navigation](https://developer.android.com/jetpack/compose/navigation) -> Navigate between screens.
  - [Compose](https://developer.android.com/jetpack/compose) -> UI
  - [Room](https://developer.android.com/training/data-storage/room) -> Local Database
  - [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android) -> Dependency Injection
  - [DataStore](https://developer.android.com/jetpack/androidx/releases/datastore) -> Store relatively smaller data asynchronously, consistently and transactionally.
  - [Media3](https://developer.android.com/jetpack/androidx/releases/media3) -> Play video

- [Coil](https://coil-kt.github.io/coil/compose/) -> fetches and displays network images.
- [Retrofit & OkHttp3](https://square.github.io/retrofit/) -> Networking
- [Splash API](https://developer.android.com/develop/ui/views/launch/splash-screen) -> Launch screen
- [In-App Update](https://developer.android.com/guide/playcore/in-app-updates) -> managing app update
- [Google Sign-In - OneTap](https://developers.google.com/identity/one-tap/android/overview) -> authentication using google
- [Chart](https://github.com/PhilJay/MPAndroidChart) -> data visualization

- [Firebase](https://firebase.google.com/docs/android/setup)
  - Authentication
  - Firestore -> NoSQL cloud database
  - Storage -> storing photos or videos
  - Remote Config ->  cloud service that lets you change the behavior and appearance  without update.
  - Crashlytics -> real-time crash reporting.
