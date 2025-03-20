

# QRScanner 

QRScanner application built using Java, following the MVVM architectural pattern. The project integrates ZXing QR decoder and Hilt Dagger for dependency injection and Room Database for local storage.

[Download APK from here](https://drive.google.com/file/d/1QgdU3BcDoHqOyROrh_wMjA1kaCF2KZ5j/view?usp=sharing)


<img src="https://github.com/user-attachments/assets/9eb0b198-0d68-4d6e-8690-86667f531028" width="300">
<img src="https://github.com/user-attachments/assets/91d80d71-17d0-4b44-be57-d6c6d1f7de1a" width="300">
<img src="https://github.com/user-attachments/assets/4350f528-f8bb-45fb-868c-1b8a9a69db37" width="300">


## Architecture 🗼

- MVVM Pattern


**For more information you can
check [Guide to app architecture](https://developer.android.com/jetpack/guide?gclid=CjwKCAiA_omPBhBBEiwAcg7smXcfbEYneoLKFD_4Tyw0OgVQkpZL_XIr5TPXT0mncuQhgDIBBvLhbBoCEx0QAvD_BwE&gclsrc=aw.ds#mobile-app-ux)**

![Architecture](https://miro.medium.com/v2/resize:fit:720/format:webp/1*Qby1SHSjmFEJT_ycbcpysQ.png)

## 🛠️ Tech Stack
- **Java** - Primary programming language.
- **ZXing** - QR Decoder
- **Hilt Dagger** - Dependency injection framework.
- **Room Database** - Local storage solution.
- **LiveData & ViewModel** - Managing UI-related data lifecycle.
- **Espresso** - UI testing framework for Android.



## Package Structure 🗂

    .
    .
    .
    ├── Data
    |    ├── cache            # Room Database
    |    |
    |    ├── repositories     # Repositories implementation
    |    |
    |    └── di               # Hilt Dependency Injection
    |
    |
    ├── Domain
    |    ├── repository       # Repository interface
    |    |
    |    ├── models           # domain models classes
    |    |
    |    └── usecases         # App UseCases
    | 
    ├── UI                    
    |    ├── activities       # App Activites
    |    |               
    |    ├── adapters         # Recyclerview Adapters
    |    | 
    |    ├── customviews      # Custom Views
    |    |
    |    ├── dialogs          # Dialogs
    |    |
    |    └── viewmodels       # Activites ViewModels
    |
    ├── util                  # ZXingScanner & ClipboardUtil
    |
    └── APP.kt                # @HiltAndroidApp

## Contribute 🤝

If you want to contribute to this app, you're always welcome!

