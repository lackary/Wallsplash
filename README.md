# Wallsplash

Wallsplash is a simple demo application base on the Android MVVM architecture. This App Fetch data from network and render data via Paging3

## Screenshot
<p align="center">
<img src="/preview/preview.gif" width="40%"/>
</p>

## MAD Score
<p align="center">
<img src="/MAD/summary.png"/>
<img src="/MAD/jetpack.png"/>
<img src="/MAD/kotlin.png"/>
</p>


## Tech Stack and Open Source Libraries
- Target SDK level 30
- Minimum SDK level 28
- Kotlin base
- Coroutine for asynchronous
- Dragger2 for dependency injection
- Android Jetpack
  - Navigation -  Build and structure your in-app UI, handle deep links, and navigate between screens.
  - Hilt - Extend the functionality of Dagger Hilt to enable dependency injection of certain classes from the androidx libraries.
  - Paging3 - Load data in pages, and present it in a RecyclerView.
  - lifecycle - Build lifecycle-aware components that can adjust behavior based on the current lifecycle state of an activity or fragment.
- Architecture
  - MVVM Architecture (View -> View Model -> Model)
  - Repository pattern
- 3rd party library
  - OKHttp3 & Retrofit2
  - Glide
  - Timber

## Open API
Wallsplash used the Unsplash API for constructing the RESTful API
https://unsplash.com/developers


  
