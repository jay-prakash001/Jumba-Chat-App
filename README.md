# Jumba Chat App

Jumba Chat is a real-time chat application built with **Jetpack Compose**, **Firebase**, **MongoDB**, and **Socket.io**. It enables users to communicate seamlessly with an intuitive UI, secure authentication, and dynamic user lists.

## Features

- **Real-time Chat** using **Socket.io**
- **Dynamic User List** (Updated via Socket.io)
- **Previous Conversations Display**
- **MVVM Architecture** for clean code structure
- **Jetpack Compose UI** with Material 3 Design
- **MongoDB Backend** for storing chat history
- **Offline Support** (Room)

## Screens

1. **Login and Registration Screen** – Authenticate users with their phone number.

2. **Users List Screen** – Displays all users with their chat history.
3. **Chat Screen** – One-on-one messaging with real-time updates.
4. **Profile Screen** –See profiles of users and update the self profile (bio and profile image).
3. **Contact Screen** – Add contacts using room db in local database with their profile images.
## Tech Stack

### Frontend (Android)
- **Jetpack Compose** – UI framework
- **Room Database** – Local storage (optional for caching messages)
- **Coroutine & Flow** – Asynchronous operations
- **Koin** – Dependency injection

### Backend
- **MongoDB** – Database for storing user information and chat history
- **Node.js & Express.js** – API server
- **Firebase cloud messaging**  - For realtime Notifications.
- **Socket.io** – Real-time communication


### Prerequisites
- Android Studio (latest version)
- Firebase Project with Authentication enabled
- MongoDB and Node.js installed

### Steps to Run
1. **Clone the repository**
   ```sh
   git clone https://github.com/jay-prakash001/Jumba-Chat-App
   
   ```
2. **Setup Firebase**
   - Add `google-services.json` to the `app/` directory.
3. **Run Android App**
   - Open the project in Android Studio
   - Sync dependencies and build the project
   - Run on an emulator or physical device
4. **Run Backend Server**
   - Navigate to the server folder
   - Install dependencies: `npm install`
   - Start the server: `node server.js`

## Future Enhancements
- Group Chats
- Message Reactions & Typing Indicators
- Voice & Video Calls

## Contribution
Contributions are welcome! Feel free to fork, create issues, and submit pull requests.



## Screenshots
![Login Screen](https://raw.githubusercontent.com/jay-prakash001/Jumba-Chat-App/refs/heads/main/app/src/main/java/com/jp/chatapp/presentation/screens/screenshots/Screenshot%20(27).png)

![Register Screen](https://raw.githubusercontent.com/jay-prakash001/Jumba-Chat-App/refs/heads/main/app/src/main/java/com/jp/chatapp/presentation/screens/screenshots/Screenshot%20(28).png)

![Successful Login](https://raw.githubusercontent.com/jay-prakash001/Jumba-Chat-App/refs/heads/main/app/src/main/java/com/jp/chatapp/presentation/screens/screenshots/Screenshot%20(31).png)

![Chat List Screen](https://raw.githubusercontent.com/jay-prakash001/Jumba-Chat-App/refs/heads/main/app/src/main/java/com/jp/chatapp/presentation/screens/screenshots/Screenshot%20(24).png)

![Empty Chat Screen](https://raw.githubusercontent.com/jay-prakash001/Jumba-Chat-App/refs/heads/main/app/src/main/java/com/jp/chatapp/presentation/screens/screenshots/Screenshot%20(29).png)

![Notification Permission](https://raw.githubusercontent.com/jay-prakash001/Jumba-Chat-App/refs/heads/main/app/src/main/java/com/jp/chatapp/presentation/screens/screenshots/Screenshot%20(30).png)

![Chating](https://raw.githubusercontent.com/jay-prakash001/Jumba-Chat-App/refs/heads/main/app/src/main/java/com/jp/chatapp/presentation/screens/screenshots/Screenshot%20(32).png)

![Chat Screen](https://raw.githubusercontent.com/jay-prakash001/Jumba-Chat-App/refs/heads/main/app/src/main/java/com/jp/chatapp/presentation/screens/screenshots/Screenshot%20(35).png)

![Named Notification](https://raw.githubusercontent.com/jay-prakash001/Jumba-Chat-App/refs/heads/main/app/src/main/java/com/jp/chatapp/presentation/screens/screenshots/Screenshot%20(34).png)


![Profile Image](https://raw.githubusercontent.com/jay-prakash001/Jumba-Chat-App/refs/heads/main/app/src/main/java/com/jp/chatapp/presentation/screens/screenshots/Screenshot%20(23).png)

![Profile Screen](https://raw.githubusercontent.com/jay-prakash001/Jumba-Chat-App/refs/heads/main/app/src/main/java/com/jp/chatapp/presentation/screens/screenshots/Screenshot%20(25).png)

![Choose Profile Image](https://raw.githubusercontent.com/jay-prakash001/Jumba-Chat-App/refs/heads/main/app/src/main/java/com/jp/chatapp/presentation/screens/screenshots/Screenshot%20(26).png)
