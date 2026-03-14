# Firebase Backend Integration Setup

## 🚀 Firebase Project Configuration

Your Android project now has Firebase backend integration with the following features:

### ✅ Completed Features

1. **Firebase Authentication**
   - Sign Up functionality with email/password
   - Login functionality with email/password
   - User profile management
   - Authentication state management

2. **Cloud Firestore Database**
   - Auto Parts collection
   - Tractor Brands collection
   - Dealers collection
   - Real-time data fetching

3. **Firebase Storage** (configured for images)

## 📱 New Screens Added

### Sign Up Screen (`SignUpScreen.kt`)
- Full name, email, password fields
- Password confirmation
- Firebase authentication integration
- Navigation to Login screen

### Updated Login Screen
- Email/password authentication
- Navigation to Sign Up screen
- Error handling and validation

## 🔧 Firebase Services Used

### Authentication
```kotlin
// Firebase Auth Manager
class FirebaseAuthManager {
    suspend fun signUp(email: String, password: String, name: String): Result<FirebaseUser>
    suspend fun signIn(email: String, password: String): Result<FirebaseUser>
    fun signOut()
    fun isUserLoggedIn(): Boolean
}
```

### Firestore Database
```kotlin
// Firestore Manager
class FirestoreManager {
    suspend fun getTractorBrands(): Result<List<TractorBrand>>
    suspend fun getAutoParts(brandId: String?): Result<List<AutoPart>>
    suspend fun searchAutoParts(query: String): Result<List<AutoPart>>
    suspend fun getDealers(): Result<List<Dealer>>
}
```

## 🗄️ Database Structure

### Collections
1. **tractor_brands**
   - id, name, models[]

2. **auto_parts**
   - id, name, description, price, isAvailable, stockQuantity, compatibleModels[], brandId, imageUrl

3. **dealers**
   - id, name, location, phoneNumber, email, address

## 📋 Required Firebase Console Setup

### 1. Authentication Setup
- Go to Firebase Console → Authentication
- Enable Email/Password sign-in method
- Configure email templates (optional)

### 2. Firestore Database Setup
- Go to Firebase Console → Firestore Database
- Create database in Test mode (for development)
- Set up security rules:
```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

### 3. Storage Setup (for images)
- Go to Firebase Console → Storage
- Enable storage
- Set up security rules for image access

## 🚀 How to Initialize Sample Data

Use the `FirebaseDataInitializer` class to populate your database with sample data:

```kotlin
// In your MainActivity or a setup screen
val initializer = FirebaseDataInitializer()
lifecycleScope.launch {
    initializer.initializeSampleData()
}
```

## 🔐 Security Rules (Production)

For production, use stricter security rules:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Users can only read/write their own documents
    match /users/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
    
    // Public read access for parts and brands
    match /auto_parts/{partId} {
      allow read: if true;
      allow write: if request.auth != null && request.auth.token.admin == true;
    }
    
    match /tractor_brands/{brandId} {
      allow read: if true;
      allow write: if request.auth != null && request.auth.token.admin == true;
    }
    
    match /dealers/{dealerId} {
      allow read: if true;
      allow write: if request.auth != null && request.auth.token.admin == true;
    }
  }
}
```

## 📱 Usage Instructions

1. **Run the app** - The app will start with the Login screen
2. **Sign Up** - Create a new account with email/password
3. **Login** - Use your credentials to sign in
4. **Browse Parts** - View auto parts from Firestore database
5. **Search** - Use the search functionality to find parts

## 🔧 Dependencies Added

```gradle
// Firebase Authentication
implementation("com.google.firebase:firebase-auth-ktx:23.0.0")

// Cloud Firestore
implementation("com.google.firebase:firebase-firestore-ktx:25.0.0")
```

## 🎯 Next Steps

1. Set up Firebase project in console
2. Enable Authentication methods
3. Create Firestore database
4. Initialize sample data
5. Test authentication flow
6. Add image upload functionality (optional)

## 🐛 Troubleshooting

### Common Issues
- **Authentication failed**: Check if Email/Password is enabled in Firebase Console
- **Firestore permissions**: Verify security rules allow read/write access
- **Network issues**: Ensure internet connectivity
- **Build errors**: Run `./gradlew clean build` to resolve dependency issues

### Debug Tips
- Use Firebase Console to verify data
- Check Logcat for Firebase error messages
- Test with Test mode security rules initially
