# 🌤️ WeatherTrack

WeatherTrack is a simple Android app built with Java and MVVM architecture that allows users to track daily weather statistics for their city. The app fetches current weather data from a mock/static API, stores it locally using Room every 6 hours, and displays weekly temperature trends in a clean and user-friendly UI.

---

## 🚀 Features

- ✅ **Fetch Weather**
  - Retrieves temperature, humidity, and condition from a mock API (or static JSON).
  - Saves fetched data into the local Room database with a timestamp.

- 🔄 **Auto Background Sync**
  - Uses WorkManager to schedule automatic weather fetches every 6 hours.
  - Manual refresh button for immediate updates.

- 📊 **Weekly Summary Screen**
  - Graph or list view of temperature changes over the past 7 days.
  - Tap a day to see detailed weather info.

- 🧱 **App Architecture**
  - Built with Java + MVVM (Model-View-ViewModel).
  - Clean separation between UI, ViewModel, Repository, and Data Source layers.

- 🛡️ **Error Handling**
  - Friendly messages shown when:
    - No internet connection
    - API failures
    - Local database issues

---


## 🧰 Tech Stack

- Java
- Android Room (for local database)
- WorkManager (for background syncing)
- MVVM Architecture
- LiveData + ViewModel
- RecyclerView
- Mock JSON or static weather API

---

## 🛠️ Getting Started

### Prerequisites

- Android Studio
- Java SDK 8+
- Android SDK

### Run the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/whorishabhverma/Flam-assignment/
   cd WeatherTrack

2. Open it in Android Studio.

3. Sync Gradle and Run on Emulator or Physical Device.




