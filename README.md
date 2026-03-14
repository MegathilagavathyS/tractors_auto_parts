# Kumaravadivel Tractor Auto Parts Mobile App

A modern Android mobile application frontend built with Kotlin and Jetpack Compose for displaying tractor auto parts information and availability management.

## Features

### 📱 Screens Implemented

1. **Login Screen**
    - Phone number/email and password input fields
    - Material 3 components with clean UI
    - Loading states and validation

2. **Home Screen (Dashboard)**
    - Top App Bar with company name
    - Card-based navigation options:
        - Select Tractor Model
        - View Auto Parts
        - Dealer Information
        - Profile

3. **Tractor Selection Screen**
    - ExposedDropdownMenu for tractor brands
    - Model selection based on selected brand
    - Continue button with validation

4. **Auto Parts List Screen**
    - LazyColumn for efficient scrolling
    - Search functionality for filtering parts
    - Part cards showing:
        - Part name and description
        - Price in Indian Rupees
        - Availability status badges
        - Stock quantity

5. **Part Details Screen**
    - Large part image placeholder
    - Comprehensive part information
    - Compatible tractor models list
    - Price and stock status display

6. **Dealer Information Screen**
    - LazyColumn showing dealer details
    - Dealer name, location, and contact information
    - Call button (UI only)

## 🛠 Technology Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Material Design**: Material 3
- **Navigation**: Navigation Compose
- **Image Loading**: Coil
- **Architecture**: MVVM pattern
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

## 📁 Project Structure

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog | 2023.1.1 or later
- JDK 8 or higher
- Android SDK API 24+

### Installation

1. Clone the repository
2. Open the project in Android Studio
3. Sync the project with Gradle files
4. Run the app on an emulator or physical device

## 🎯 Key Features

### Mock Data
The app includes comprehensive mock data for:
- **Tractor Brands**: Mahindra, John Deere, Swaraj, TAFE
- **Auto Parts**: 8 different spare parts with prices and availability
- **Dealers**: 4 dealer locations across Tamil Nadu

### User Experience
- Clean and modern Material 3 design
- Smooth navigation between screens
- Search functionality for parts
- Responsive card-based layouts
- Loading states and user feedback

### Data Display
- Parts pricing in Indian Rupees (₹)
- Availability status badges
- Stock quantity information
- Compatible tractor model information
- Dealer contact details

## 📱 Screen Flow

1. **Login** → **Home Dashboard**
2. **Home** → **Tractor Selection** → **Parts List** → **Part Details**
3. **Home** → **Parts List** → **Part Details**
4. **Home** → **Dealer Information**

## 🎨 Design Guidelines

- Follows Material 3 design principles
- Consistent color scheme and typography
- Accessibility-friendly components
- Responsive layouts for different screen sizes

## 🔧 Dependencies

- `androidx.compose:*` - Jetpack Compose libraries
- `androidx.navigation:navigation-compose` - Navigation
- `androidx.lifecycle:lifecycle-viewmodel-compose` - ViewModel
- `io.coil-kt:coil-compose` - Image loading
- `androidx.material3:material3` - Material 3 components

## 📝 Notes

- Frontend-only application focused on information display
- No online ordering or payment functionality
- Uses mock data for demonstration purposes
- Call buttons are UI-only
- Images use placeholders when URLs are not provided

## 🤝 Contributing

This project is a demonstration of modern Android development practices using Jetpack Compose and Material 3 design principles.

## 📄 License

This project is for demonstration purposes only.