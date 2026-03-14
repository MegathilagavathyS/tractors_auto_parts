# Firebase Project Setup Instructions

## 🔍 Current Status

Your `google-services.json` has been updated with the correct Firebase project configuration:

- **Project ID**: `tractorparts-aa23a`
- **Package Name**: `com.kumaravadivel.tractorautoparts` ✅
- **API Key**: Updated correctly

## 🚀 Next Steps - Firebase Console Setup

### 1. Go to Firebase Console
```
https://console.firebase.google.com/
```

### 2. Select Your Project
- Choose project: `tractorparts-aa23a`
- If project doesn't exist, create it with this ID

### 3. Enable Required Services

#### A. Enable Authentication
1. Go to **Authentication** → **Sign-in method**
2. Enable **Email/Password** provider
3. Click **Save**

#### B. Create Firestore Database
1. Go to **Firestore Database**
2. Click **Create database**
3. Choose **Start in test mode** (for development)
4. Select a location (choose closest to your users)
5. Click **Create**

#### C. Set Security Rules (Test Mode)
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

### 4. Add Android App to Firebase
If app not showing in Firebase Console:

1. Go to **Project Settings** → **Your apps**
2. Click **Add app** → **Android**
3. Package name: `com.kumaravadivel.tractorautoparts`
4. Download new `google-services.json` (should match current one)
5. Replace existing file if different

### 5. Verify Configuration
Check that these match:
- ✅ Package name: `com.kumaravadivel.tractorautoparts`
- ✅ Project ID: `tractorparts-aa23a`
- ✅ SHA-1 fingerprint (if using release builds)

## 🔧 Build and Test

```bash
# Clean and rebuild
./gradlew clean
./gradlew assembleDebug

# Install and test
adb install app/build/outputs/apk/debug/app-debug.apk
```

## 📱 Testing Firebase Connection

After setup, check Logcat for:
```
D/FirebaseTest: Firebase Auth available: true
D/FirebaseTest: Firestore available: true
D/FirebaseTest: Firebase connection test completed successfully
```

## ⚠️ Troubleshooting

### If Firebase still not connected:

1. **Check Internet Permission**
   ```xml
   <uses-permission android:name="android.permission.INTERNET" />
   ```

2. **Verify google-services.json Location**
   - Must be in `app/` directory
   - File name exactly: `google-services.json`

3. **Check Gradle Sync**
   - Click **Sync Project** in Android Studio
   - Check for any Firebase-related errors

4. **Test on Real Device**
   - Emulator might have Google Play Services issues
   - Physical device recommended for testing

5. **Clear and Rebuild**
   ```bash
   ./gradlew clean
   ./gradlew build
   ```

## ✅ Success Indicators

You'll know Firebase is working when:
- App launches without crash
- Login/Sign up screens appear
- Firebase test messages appear in Logcat
- Can create users in Firebase Console
- Data appears in Firestore database

## 🎯 Final Verification

1. Run the app
2. Try to create a new account
3. Check Firebase Console → Authentication → Users
4. User should appear there
5. Navigate to parts list
6. Check Firebase Console → Firestore → Data

Your Firebase backend is now properly configured! 🎉
