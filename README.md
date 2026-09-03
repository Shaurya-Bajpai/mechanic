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

### Detailed Architecture

#### Presentation Layer (UI)
- **Composable Screens**: HomeScreen, MechanicDetailsScreen, RequestServiceScreen
- **Navigation**: AppNavigation using Jetpack Navigation Compose for handling routing
- **State Management**: UI state flows managed through StateFlow in ViewModels

#### ViewModel Layer
- **HomeViewModel**: Manages home screen state and mechanic list loading
- **MechanicDetailsViewModel**: Loads and manages individual mechanic details
- **RequestServiceViewModel**: Handles service request form state and validation
- Uses Hilt for dependency injection with `@HiltViewModel`

#### Data Layer
- **MechanicRepository**: Single source of truth for data access
- **MechanicApi**: Retrofit service interface for API calls
- **Models**: Mechanic, ServiceRequest, UI state models
- Network operations run on coroutine scope for non-blocking operations

#### Dependency Injection (Hilt)
- **NetworkModule**: Provides Retrofit singleton with base URL
- **RepositoryModule**: Provides repository with injected API dependency
- **MechanicApplication**: Hilt entry point with @HiltAndroidApp annotation

## API/Data Details

### Base URL
```
https://6a97eb4c7160beda22927f9b.mockapi.io/
```

### Endpoints

#### GET /mechanic
Returns list of all available mechanics

**Response Model:**
```json
{
  "id": "1",
  "name": "John Doe",
  "rating": 4.5,
  "phone": "9876543210",
  "address": "123 Main St, City",
  "services": "Engine Service, Brake Service, Suspension",
  "workingHours": "9 AM - 6 PM",
  "isOpen": true
}
```

### Data Models
- **Mechanic**: Contains id, name, rating, phone, address, services, workingHours, isOpen
- **ServiceRequest**: Stores customer name, phone, vehicle number, service type, problem description
- **HomeUiState**: Loading, mechanics list, error message
- **MechanicDetailsUiState**: Loading, mechanic object, error message
- **RequestServiceUiState**: Form fields, error message, submission status

## Assumptions & Additional Features

### Design Assumptions
1. **Single API Source**: App uses MockAPI for all data with no local database caching
2. **Real-time Data**: No offline capability - requires internet connection for all operations
3. **Form Submission**: Service requests are validated locally but not persisted to backend (can be extended)
4. **Search Feature**: Search mechanics by name functionality integrated in HomeScreen
5. **Rating Display**: Ratings are displayed as star ratings (★) from API data

### Additional Features Implemented
1. **Form Validation**: Comprehensive validation for all service request fields
2. **Error Handling**: User-friendly error messages and retry mechanisms
3. **Loading States**: Loading indicators during API calls and data fetches
4. **Service Grouping**: Services displayed as comma-separated list with parsing
5. **Open/Closed Status**: Visual indicator for mechanic availability
6. **Initials Display**: Mechanic avatar shows first letters of name

### Future Enhancement Opportunities
1. Add local database (Room) for offline caching of mechanic data
2. Implement service request backend persistence
3. Add user authentication and profile management
4. Implement real search with filtering and sorting
5. Add notification system for service request updates
6. Add image support for mechanic profiles
7. Integrate real payment gateway for service payments
8. Add review and rating system for mechanics

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
