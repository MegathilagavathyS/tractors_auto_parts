# How to Run the Kumaravadivel Tractor Auto Parts App

## Prerequisites
- Android Studio (Hedgehog 2023.1.1 or later)
- JDK 8 or higher
- Android SDK with API 24+ installed

## Step-by-Step Instructions

### 1. Open the Project in Android Studio
1. Launch Android Studio
2. Click "Open" (or "Open an Existing Project")
3. Navigate to `c:\Users\Admin\Desktop\PROJECT\CON_PROJ`
4. Select the folder and click "OK"

### 2. Wait for Gradle Sync
1. Android Studio will automatically start syncing the project
2. Wait for the sync to complete (this may take a few minutes)
3. If sync fails, click "Try Again" or check your internet connection

### 3. Set Up Android Virtual Device (AVD)
If you don't have an emulator set up:

1. **Open Device Manager:**
   - Click the "Device Manager" icon in the toolbar (phone icon)
   - Or go to `Tools` → `Device Manager`

2. **Create New Device:**
   - Click "Create device"
   - Choose a phone model (e.g., Pixel 6)
   - Click "Next"

3. **Select System Image:**
   - Choose an Android version (API 30+ recommended)
   - If not downloaded, click the download arrow next to your chosen version
   - Wait for download to complete
   - Click "Next"

4. **Finish Setup:**
   - Give your AVD a name
   - Click "Finish"

### 4. Run the Application

#### Option A: Using Emulator
1. In Android Studio, select your emulator from the dropdown menu in the toolbar
2. Click the green "Run" button (▶️) or press `Shift + F10`
3. Wait for the emulator to start and the app to install

#### Option B: Using Physical Device
1. Enable Developer Options on your Android device:
   - Go to `Settings` → `About Phone`
   - Tap "Build Number" 7 times
   - Go back to `Settings` → `System` → `Developer Options`
   - Enable "USB Debugging"

2. Connect your device:
   - Connect your phone to computer via USB
   - Allow USB debugging when prompted
   - Select your device from the dropdown menu in Android Studio
   - Click the green "Run" button (▶️)

### 5. Test the App

Once the app launches, you can:

1. **Login Screen:**
   - Enter any phone/email and password
   - Click "Login" to proceed

2. **Home Dashboard:**
   - Navigate through different options
   - Try "Select Tractor Model" first

3. **Tractor Selection:**
   - Select a brand (Mahindra, John Deere, etc.)
   - Select a model
   - Click "Continue"

4. **Browse Parts:**
   - Search for parts using the search bar
   - Tap on any part to view details
   - Check availability status

5. **Dealer Information:**
   - View dealer locations and contact details

## Troubleshooting

### Common Issues:

**Gradle Sync Fails:**
- Check internet connection
- Try `File` → `Sync Project with Gradle Files`
- Restart Android Studio

**Emulator Won't Start:**
- Check if Windows Hyper-V is enabled
- Try creating a new AVD with different settings
- Make sure your PC supports virtualization

**App Won't Install:**
- Check if you have enough storage on device/emulator
- Try cleaning the project: `Build` → `Clean Project`
- Rebuild: `Build` → `Rebuild Project`

**Build Errors:**
- Check if all dependencies are downloaded
- Try `File` → `Invalidate Caches and Restart`

### Quick Commands (if needed):

**Clean and Rebuild:**
```
Build → Clean Project
Build → Rebuild Project
```

**Sync Gradle:**
```
File → Sync Project with Gradle Files
```

**Invalidate Caches:**
```
File → Invalidate Caches and Restart
```

## Project Structure Reference

The main files you might want to explore:
- `app/src/main/java/com/kumaravadivel/tractorautoparts/MainActivity.kt` - Main entry point
- `app/src/main/java/com/kumaravadivel/tractorautoparts/ui/screens/` - All screen implementations
- `app/src/main/java/com/kumaravadivel/tractorautoparts/data/MockData.kt` - Sample data

## Expected Behavior

The app should:
- Load quickly on modern devices
- Show smooth animations between screens
- Display all mock data correctly
- Allow searching and navigation
- Work on both portrait and landscape orientations

Enjoy exploring the Kumaravadivel Tractor Auto Parts app!
