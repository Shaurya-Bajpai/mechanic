# Instant Mechanic

## Features
- Browse mechanics
- Search mechanics
- View mechanic details
- Request vehicle service
- Form validation
- Confirmation screen

## Tech Stack
- Kotlin
- Jetpack Compose
- MVVM
- Retrofit
- Gson
- MockAPI
- Hilt (Dependency Injection)

## Dependency Injection
This project uses **Hilt** for dependency injection:
- `NetworkModule`: Provides Retrofit instance and MechanicApi
- `RepositoryModule`: Provides MechanicRepository singleton
- `@HiltAndroidApp`: Application class for Hilt initialization
- `@HiltViewModel`: ViewModel injection in HomeViewModel, MechanicDetailsViewModel, RequestServiceViewModel
- `hiltViewModel()`: Compose integration for ViewModel creation

## API
GET /mechanic

## Architecture
UI → ViewModel → Repository → Retrofit → MockAPI

## How to Run
1. Clone repository
2. Open in Android Studio
3. Sync Gradle
4. Run application

## Project Structure
```
app/src/main/java/com/example/mechanic/
├── di/
│   ├── NetworkModule.kt       (Retrofit & API setup)
│   └── RepositoryModule.kt    (Repository provision)
├── screens/
│   ├── home/
│   ├── details/
│   ├── request/
│   └── components/
├── data/
│   ├── model/
│   ├── remote/
│   └── repository/
├── navigation/
├── ui/
└── MechanicApplication.kt      (Hilt @HiltAndroidApp)
```
