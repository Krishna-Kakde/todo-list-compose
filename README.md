# 📝 To-Do List App with Progress Tracker

A simple Android to-do list app built with Jetpack Compose, featuring local data persistence and a visual progress tracker. This is my first end-to-end Android project, built after completing the Android Basics with Compose course.

## ✨ Features

- Add, edit, and delete tasks
- Visual progress tracker showing task completion percentage
- Tasks displayed in a scrollable list (LazyColumn)
- Data persists locally using Room database — tasks survive app restarts
- Asynchronous database operations using Kotlin Coroutines
- DAO layer covered with JUnit unit tests

## 🛠️ Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Architecture:** MVVM
- **Local Storage:** Room
- **Async:** Kotlin Coroutines
- **Testing:** JUnit



## 🏗️ Architecture Overview

```
UI (Compose) → ViewModel → Repository → Room DAO → SQLite (local DB)
```

- **UI Layer:** Composable screens observe state from the ViewModel
- **ViewModel:** Holds UI state and exposes functions for adding/updating/deleting tasks
- **Room:** Handles local persistence via a DAO with suspend functions
- **Coroutines:** Keep database calls off the main thread for a smooth UI

## 🧪 Testing

The DAO layer is tested with JUnit to verify core database operations (insert, update, delete, and fetch) behave correctly, independent of the UI.

## 🚀 What's Next

This is the first in a series of Android projects I'm building on my way to becoming internship-ready. Stay tuned for updates.

## 📄 License

This project is open source and available for learning purposes.
