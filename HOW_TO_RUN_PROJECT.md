# 🚀 How to Run Your Android Project

## 📱 Method 1: Android Studio (Recommended)

### Step 1: Open Project in Android Studio
```
1. Launch Android Studio
2. Click "Open an Existing Project"
3. Navigate to: c:/Users/Priyadharshini/AndroidStudioProjects/CON_PROJ (1)/CON_PROJ
4. Click "OK"
```

### Step 2: Sync Project
```
1. Wait for Gradle sync to complete
2. If needed, click "Sync Project with Gradle Files" (🔄 icon)
3. Verify no red error indicators
```

### Step 3: Select Device/Emulator
```
1. Look at the toolbar (top of Android Studio)
2. Click the device dropdown menu
3. Choose one of:
   - Connected Android device (via USB)
   - Android Emulator (virtual device)
   - Create new emulator if none available
```

### Step 4: Run the App
```
1. Click the green "Run 'app'" button (▶️) in toolbar
2. OR press: Shift + F10
3. OR right-click app folder → Run 'app'
```

## 🖥️ Method 2: Command Line (ADB)

### Step 1: Build APK
```bash
cd "c:/Users/Priyadharshini/AndroidStudioProjects/CON_PROJ (1)/CON_PROJ"
./gradlew assembleDebug
```

### Step 2: Connect Device
```bash
# Check connected devices
adb devices

# If no devices, enable USB debugging on your phone:
# 1. Settings → About Phone → Tap "Build Number" 7 times
# 2. Settings → Developer Options → Enable "USB Debugging"
# 3. Connect phone via USB and allow debugging
```

### Step 3: Install APK
```bash
# Install the built APK
adb install app/build/outputs/apk/debug/app-debug.apk

# If already installed, use -r to reinstall
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### Step 4: Launch App
```bash
# Launch the app directly
adb shell am start -n com.kumaravadivel.tractorautoparts/.MainActivity

# OR find and launch by package name
adb shell monkey -p com.kumaravadivel.tractorautoparts -c android.intent.category.LAUNCHER 1
```

## 📱 Method 3: Android Emulator

### Step 1: Open AVD Manager
```
1. In Android Studio: Tools → AVD Manager
2. OR Tools → Device Manager
```

### Step 2: Create Emulator (if needed)
```
1. Click "Create Virtual Device"
2. Choose device model (e.g., Pixel 6)
3. Select system image (e.g., Android 13/14)
4. Download if not available
5. Finish and create emulator
```

### Step 3: Launch Emulator
```
1. In AVD Manager, click the "Play" (▶️) button next to your emulator
2. Wait for emulator to start (can take 1-2 minutes)
3. Emulator will appear as a device in Android Studio
```

### Step 4: Run App
```
1. Select the running emulator from device dropdown
2. Click "Run 'app'" (▶️) button
3. App will install and launch on emulator
```

## 🔧 Method 4: Install APK Directly

### Step 1: Locate APK
```
File: app/build/outputs/apk/debug/app-debug.apk
Path: c:/Users/Priyadharshini/AndroidStudioProjects/CON_PROJ (1)/CON_PROJ/app/build/outputs/apk/debug/app-debug.apk
```

### Step 2: Transfer to Phone
```
Option 1: USB Cable
- Connect phone to computer
- Copy APK file to phone storage
- Use file manager to open and install

Option 2: Cloud Storage
- Upload APK to Google Drive/Dropbox
- Access from phone and install

Option 3: Email
- Email APK to yourself
- Open email on phone and install
```

### Step 3: Enable Installation
```
On your Android phone:
1. Settings → Security & Privacy
2. Enable "Install from Unknown Sources" or "Allow from this source"
3. Grant permission for your file manager/source
```

## 📊 Quick Start Commands

### For Development (Android Studio):
```bash
# 1. Open project in Android Studio
# 2. Wait for sync
# 3. Select device/emulator
# 4. Click Run (▶️)
```

### For Testing (Command Line):
```bash
cd "c:/Users/Priyadharshini/AndroidStudioProjects/CON_PROJ (1)/CON_PROJ"
./gradlew assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.kumaravadivel.tractorautoparts/.MainActivity
```

## 🚨 Troubleshooting

### Issue 1: Gradle Sync Fails
```bash
# Clean and rebuild
./gradlew clean
./gradlew build --refresh-dependencies
```

### Issue 2: Device Not Found
```bash
# Check USB debugging
adb devices

# Restart ADB server
adb kill-server
adb start-server
```

### Issue 3: Emulator Slow
```
1. Enable hardware acceleration in BIOS
2. Use Android Studio's performance settings
3. Allocate more RAM to emulator (AVD Manager → Edit → Advanced Settings)
```

### Issue 4: Installation Failed
```bash
# Uninstall previous version
adb uninstall com.kumaravadivel.tractorautoparts

# Then reinstall
adb install app/build/outputs/apk/debug/app-debug.apk
```

## 🎯 Recommended Workflow

### For Daily Development:
1. **Android Studio** - Best for debugging and hot reload
2. **Physical Device** - Faster than emulator
3. **USB Debugging** - Quick deployment

### For Testing:
1. **Multiple Devices** - Test on different screen sizes
2. **Emulator** - Test different Android versions
3. **APK Distribution** - Share with testers

## 📱 App Features to Test

Once running, test these features:
- ✅ **Login Screen** - Email/password input
- ✅ **Signup Screen** - New user registration  
- ✅ **Home Screen** - Navigation to other screens
- ✅ **Tractor Selection** - Brand/model selection
- ✅ **Auto Parts List** - Browse parts catalog
- ✅ **Part Details** - View individual part info
- ✅ **Dealer Information** - Contact details
- ✅ **Firebase Backend** - Data synchronization
- ✅ **Navigation** - Smooth screen transitions
- ✅ **Thread Safety** - No crashes

## 🚀 Ready to Run!

Your Tractor Auto Parts app is **ready to run** with:
- ✅ **Fixed navigation crashes**
- ✅ **Thread-safe operations**
- ✅ **Firebase backend connected**
- ✅ **16KB page size compatible**
- ✅ **Latest Gradle versions**

**Choose your preferred method above and start testing!** 🎉
