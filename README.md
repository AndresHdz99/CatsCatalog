# 🐱 CatsCatalogApp

<div align="center">
  <img src="https://img.shields.io/badge/Kotlin-Multiplatform-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin Multiplatform">
  <img src="https://img.shields.io/badge/Jetpack-Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose">
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android">
  <img src="https://img.shields.io/badge/Desktop-JVM-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Desktop JVM">
</div>

<br>

A multiplatform application built with **Kotlin Multiplatform** and **Jetpack Compose Multiplatform**, allowing users to browse a catalog of cats using the public API from TheCatAPI. The app includes authentication, detailed information for each cat, and a personalized favorites system per user.

**Compatible with Android and Desktop (JVM).**

---

## 📦 Tech Stack

<table>
  <tr>
    <td align="center" width="200">
      <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white" alt="Kotlin"><br>
      <strong>Kotlin Multiplatform</strong><br>
      <em>(KMP)</em>
    </td>
    <td align="center" width="200">
      <img src="https://img.shields.io/badge/Compose-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white" alt="Compose"><br>
      <strong>Jetpack Compose Multiplatform</strong>
    </td>
    <td align="center" width="200">
      <img src="https://img.shields.io/badge/Ktor-087CFA?style=flat-square&logo=ktor&logoColor=white" alt="Ktor"><br>
      <strong>Ktor Client</strong><br>
      <em>for REST API consumption</em>
    </td>
  </tr>
  <tr>
    <td align="center">
      <img src="https://img.shields.io/badge/Realm-39477F?style=flat-square&logo=realm&logoColor=white" alt="Realm"><br>
      <strong>Realm Kotlin SDK</strong><br>
      <em>local multiplatform database</em>
    </td>
    <td align="center">
      <img src="https://img.shields.io/badge/Koin-FF6B00?style=flat-square&logo=koin&logoColor=white" alt="Koin"><br>
      <strong>Koin</strong><br>
      <em>dependency injection for KMP</em>
    </td>
    <td align="center">
      <img src="https://img.shields.io/badge/TheCatAPI-FF6B6B?style=flat-square&logo=cat&logoColor=white" alt="TheCatAPI"><br>
      <strong>TheCatAPI</strong><br>
      <em>free cat images and metadata API</em>
    </td>
  </tr>
</table>

---

## 📐 Architecture

The project is structured following the **MVVM (Model-View-ViewModel)** pattern combined with a simplified **Clean Architecture** approach to ensure separation of concerns and scalable, testable code.

```
┌─────────────────────────────────────────────────────────────┐
│                        UI Layer                             │
│     Jetpack Compose Multiplatform Screens                  │
└─────────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────────┐
│                    ViewModel Layer                          │
│              MVVM Pattern Implementation                    │
└─────────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────────┐
│                   Repository Layer                          │
│              Clean Architecture Principles                  │
└─────────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────────┐
│                    Data Sources                             │
│  ┌─────────────────┐           ┌─────────────────┐          │
│  │  Remote (Ktor)  │           │  Local (Realm)  │          │
│  │   TheCatAPI     │           │   Database      │          │
│  └─────────────────┘           └─────────────────┘          │
└─────────────────────────────────────────────────────────────┘
```

---

## 📊 Design Decisions

- **Jetpack Compose Multiplatform** for a shared declarative UI layer across Android and Desktop
- **Ktor Client** for seamless asynchronous multiplatform HTTP requests
- **Realm Kotlin SDK** as a lightweight, reactive local storage for both Android and Desktop
- **Koin Multiplatform** for simple, multiplatform-friendly dependency injection
- **MVVM with Clean Architecture** principles to decouple UI and business logic
- **TheCatAPI** chosen as a reliable free resource for images and cat data under public, fair use terms

---

## 📌 Assumptions & Constraints

> **⚠️ Important Notes:**

- Unauthenticated users cannot save favorites
- Favorites are stored locally per user on each device (no cloud synchronization)
- The API key is expected to be provided by the developer/user and not hardcoded within the repository for security
- Each platform (Android and Desktop) maintains its own local Realm database instance

### 🚫 Current Scope Limitations

**To keep the initial scope clear, this version does not include:**
- JWT token-based session management
- User registration workflows (local simulated login system only)

---

## 📱 Screenshots

### Android
<div align="center">
  <img src="https://github.com/user-attachments/assets/55f56c7e-25bf-43c7-a7e1-2d3546efb452" width="250" alt="Android Screen 1">
  <img src="https://github.com/user-attachments/assets/764b9776-53e0-49f6-b4a5-0c8d493d996c" width="250" alt="Android Screen 2">
  <img src="https://github.com/user-attachments/assets/45bf0bd4-5251-4010-8582-c59c476a702f" width="250" alt="Android Screen 3">
</div>

### Desktop
<div align="center">
  <img src="https://github.com/user-attachments/assets/67253554-afdb-4b6f-a3d4-c919d88bd7bb" width="400" alt="Desktop Screen 1">
  <img src="https://github.com/user-attachments/assets/d1ceba14-3b59-47e2-a2ab-4eac7cf187e3" width="400" alt="Desktop Screen 2">
</div>

<div align="center">
  <img src="https://github.com/user-attachments/assets/ed377926-cffa-423b-9fb5-3458adaa4d3c" width="400" alt="Desktop Screen 3">
  <img src="https://github.com/user-attachments/assets/441b9732-3250-4663-8f16-64f4852fe619" width="400" alt="Desktop Screen 4">
</div>

---

## 📜 Credits and Licensing

### 🐱 TheCatAPI
**TheCatAPI** © TheCatAPI.com

All rights to images and data belong to TheCatAPI and their respective owners. The service is used solely for educational and demonstrative purposes under their free use policy.

### 🛠️ Technology Credits
**JetBrains** for Kotlin Multiplatform and Compose Multiplatform.

---

## 🧪 Test Users

For testing purposes, you can use the following credentials:

```
Username: admin
Password: admin123

Username: user123
Password: user123
```

---

<div align="center">
  <h3>Made with ❤️ and Kotlin Multiplatform</h3>
</div>
