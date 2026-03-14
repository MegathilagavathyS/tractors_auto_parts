# How to Check Firebase Connection & Fix App Crashes

## 🔍 Quick Firebase Connection Check

### 1. **Check Logcat for Firebase Errors**
Run your app and check Logcat for these messages:
- `Firebase initialization successful` ✅
- `Firebase initialization failed` ❌
- Look for `E/Firebase` or `E/GooglePlayServices` errors

### 2. **Verify Firebase Setup**
```bash
# In Android Studio terminal:
adb logcat | grep -i firebase
```

## 🛠️ Firebase Connection Verification Steps

### Step 1: Check `google-services.json`
- Ensure file is in `app/` directory
- Verify package name matches: `com.kumaravadivel.tractorautoparts`
- Check SHA-1 fingerprint in Firebase Console

### Step 2: Verify Dependencies
Your `app/build.gradle.kts` should have:
```kotlin
dependencies {
    implementation("com.google.firebase:firebase-auth-ktx:23.0.0")
    implementation("com.google.firebase:firebase-firestore-ktx:25.0.0")
}
```

### Step 3: Internet Permission
Ensure `AndroidManifest.xml` has:
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## 🚨 Common Crash Causes & Fixes

### 1. **Missing Internet Permission**
```xml
<!-- Add to AndroidManifest.xml -->
<uses-permission android:name="android.permission.INTERNET" />
```

### 2. **Firebase Not Initialized**
✅ **Fixed**: Added `TractorApplication` class with Firebase initialization

### 3. **Missing Google Play Services**
- Test on physical device with Google Play Services
- Or use emulator with Google Play Store image

### 4. **Network Issues**
- Test with internet connection
- Check if Firebase is accessible

## 🧪 Test Firebase Connection

### Add this test code to MainActivity:
```kotlin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// Add to onCreate() in MainActivity
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    // Test Firebase Auth
    val auth = FirebaseAuth.getInstance()
    println("Firebase Auth available: ${auth != null}")
    
    // Test Firestore
    val db = FirebaseFirestore.getInstance()
    println("Firestore available: ${db != null}")
    
    setContent {
        // ... existing code
    }
}
```

## 📱 Firebase Console Setup Checklist

1. **Authentication Enabled**
   - Go to Firebase Console → Authentication
   - Enable "Email/Password" sign-in method

2. **Firestore Database Created**
   - Go to Firebase Console → Firestore Database
   - Create database in "Test mode"
   - Set security rules to allow read/write

3. **App Added Correctly**
   - Package name: `com.kumaravadivel.tractorautoparts`
   - SHA-1 fingerprint registered

## 🔧 Debug Commands

### Check Firebase Configuration:
```bash
# Check if google-services.json exists
ls app/google-services.json

# Check Firebase dependencies
./gradlew app:dependencies | grep firebase
```

### Monitor App Logs:
```bash
# Real-time log monitoring
adb logcat -s "Firebase:*" "GooglePlayServices:*"
```

## ✅ Success Indicators

Your Firebase connection is working if you see:
- ✅ App launches without crashing
- ✅ Login/Sign up screens appear
- ✅ "Firebase initialization successful" in Logcat
- ✅ No Firebase-related errors in Logcat

## 🆘 Still Crashing?

1. **Check Logcat** for the exact error message
2. **Verify internet connection**
3. **Test on different device/emulator**
4. **Clear app data** and reinstall

Run the app now and check Logcat for Firebase initialization messages!
