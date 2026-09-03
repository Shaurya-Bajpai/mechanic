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
- MVVM Architecture
- Retrofit (REST API)
- Gson (JSON serialization)
- MockAPI (Backend service)

## Dependency Management
This project uses **manual dependency injection** with ViewModelFactory pattern:
- `HomeViewModelFactory`: Creates HomeViewModel with injected MechanicRepository
- `MechanicDetailsViewModelFactory`: Creates MechanicDetailsViewModel with injected repository
- `RetrofitInstance`: Singleton pattern for Retrofit API instance
- `MechanicRepository`: Centralized data access layer
- ViewModels are created in Compose Screens using factory pattern

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
- Each ViewModel uses `StateFlow` for reactive state management
- Created via ViewModelFactory for dependency injection

#### Data Layer
- **MechanicRepository**: Single source of truth for data access
- **MechanicApi**: Retrofit service interface for API calls
- **RetrofitInstance**: Singleton providing configured Retrofit instance
- **Models**: Mechanic, ServiceRequest, UI state models
- Network operations run on coroutine scope for non-blocking operations

#### Dependency Injection Pattern
- **ViewModelFactory Pattern**: Custom factory classes (HomeViewModelFactory, MechanicDetailsViewModelFactory)
- **Singleton Pattern**: RetrofitInstance provides single Retrofit instance
- **Constructor Injection**: ViewModels receive dependencies through constructor

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
├── screens/
│   ├── home/
│   │   ├── HomeScreen.kt
│   │   ├── HomeViewModel.kt
│   │   ├── HomeViewModelFactory.kt
│   │   └── HomeUiState.kt
│   ├── details/
│   │   ├── MechanicDetailsScreen.kt
│   │   ├── MechanicDetailsViewModel.kt
│   │   ├── MechanicDetailsViewModelFactory.kt
│   │   └── MechanicDetailsUiState.kt
│   ├── request/
│   │   ├── RequestServiceScreen.kt
│   │   ├── RequestServiceViewModel.kt
│   │   └── RequestServiceUiState.kt
│   ├── confirmation/
│   │   └── ConfirmationScreen.kt
│   └── components/
│       ├── LoadingView.kt
│       ├── ErrorView.kt
│       └── MechanicCard.kt
├── data/
│   ├── model/
│   │   ├── Mechanic.kt
│   │   ├── ServiceRequest.kt
│   │   ├── HomeUiState.kt
│   │   ├── MechanicDetailsUiState.kt
│   │   └── RequestServiceUiState.kt
│   ├── remote/
│   │   ├── MechanicApi.kt        (Retrofit interface)
│   │   └── RetrofitInstance.kt   (Singleton Retrofit)
│   └── repository/
│       └── MechanicRepository.kt  (Data access layer)
├── navigation/
│   └── AppNavigation.kt          (Navigation graph & routing)
├── ui/theme/
│   ├── Color.kt
│   ├── Type.kt
│   └── Theme.kt
└── MainActivity.kt
```

## Key Implementation Details

### ViewModelFactory Pattern
- **HomeViewModelFactory**: Injects MechanicRepository into HomeViewModel
- **MechanicDetailsViewModelFactory**: Injects MechanicRepository into MechanicDetailsViewModel
- Factory is passed to `viewModel()` composable in Screens

### State Management
- Each screen has corresponding ViewModel with `StateFlow`
- UI observes state changes via `collectAsState()`
- Three main UI states: Loading, Success (with data), Error (with message)

### Navigation Flow
- Home Screen → Mechanics List
- Details Screen → Individual Mechanic Information
- Request Service Screen → Service Form with validation
- Confirmation Screen → Success message

## Build & Run

### Prerequisites
- Android Studio Giraffe or later
- Android SDK 24+ (minimum)
- Gradle 9.5.0

### Build Commands
```bash
# Build debug APK
./gradlew build

# Build release APK
./gradlew assembleRelease

# Run on emulator/device
./gradlew installDebug
```

### Lint Warnings
The project may show some lint warnings about Android SDK usage. These are not critical and can be ignored for development purposes.

## Status
✅ **Project Successfully Built**
- Builds without errors
- All dependencies resolved
- Ready for testing and deployment
