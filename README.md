<p align="center">
  <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.webp" alt="CartAI Logo" width="120"/>
</p>

<h1 align="center">CartAI</h1>

<p align="center">
  <strong>AI-Powered Grocery Shopping Assistant</strong>
</p>

<p align="center">
  <a href="#features">Features</a> •
  <a href="#demo">Demo</a> •
  <a href="#architecture">Architecture</a> •
  <a href="#getting-started">Getting Started</a> •
  <a href="#tech-stack">Tech Stack</a> •
  <a href="#roadmap">Roadmap</a>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=flat-square&logo=android" alt="Platform"/>
  <img src="https://img.shields.io/badge/Kotlin-2.0.21-7F52FF?style=flat-square&logo=kotlin" alt="Kotlin"/>
  <img src="https://img.shields.io/badge/Min%20SDK-24-brightgreen?style=flat-square" alt="Min SDK"/>
  <img src="https://img.shields.io/badge/License-MIT-blue?style=flat-square" alt="License"/>
</p>

---

## Overview

**CartAI** is an intelligent Android application that revolutionizes grocery shopping by combining **natural language processing**, **computer vision**, and **seamless e-commerce integration**. Simply tell the app what you want to cook, scan a handwritten shopping list, or chat naturally about your grocery needs—CartAI handles the rest.

The app bridges the gap between AI-powered assistance and real-world shopping by directly integrating with grocery retailer APIs, allowing users to search products, manage carts, and prepare orders without ever leaving the conversation.

### Why CartAI?

| Traditional Shopping Apps | CartAI |
|---------------------------|--------|
| Manual product search | Natural language: *"Add ingredients for pasta carbonara"* |
| Type each item individually | Scan handwritten lists with AI vision |
| Switch between apps for recipes | AI understands recipes and finds ingredients |
| No quantity intelligence | Smart quantity suggestions based on context |

---

## Features

### 💬 Conversational Shopping
Interact naturally with an AI assistant powered by **Llama 3.1**. Ask for recipe ingredients, add items to cart, or get product recommendations—all through conversation.

```
User: "Add ingredients for making white sauce pasta"
CartAI: Finding ingredients for white sauce pasta...
        ✓ Pasta - $2.99
        ✓ Heavy cream - $4.49
        ✓ Parmesan cheese - $6.99
        ✓ Butter - $3.99
        ✓ Garlic - $0.99
```

### 📸 Smart List Scanning
Capture photos of handwritten or printed shopping lists. The **Llama 3.2 Vision** model extracts items with quantities, even from messy handwriting.

- **OCR-powered extraction** with quantity detection
- **Handwritten and printed** text support
- **Multi-line parsing** for complex lists

### 🛒 Real-Time Cart Management
- **Dual cart system**: Local staging cart + retailer cart sync
- **Quantity controls** with real-time price updates
- **Product images** and descriptions from retailer API
- **One-tap checkout** preparation

### 🔐 Secure OAuth 2.0 Authentication
Industry-standard authentication using **AppAuth** library with PKCE flow for secure API access without exposing credentials.

---

## Architecture

CartAI follows **MVVM (Model-View-ViewModel)** architecture with clean separation of concerns:

```
┌─────────────────────────────────────────────────────────────┐
│                        UI Layer                              │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐          │
│  │ ChatFragment│  │CameraFragment│  │ CartFragment │          │
│  └──────┬──────┘  └──────┬──────┘  └──────┬──────┘          │
│         │                │                │                  │
│  ┌──────▼──────┐  ┌──────▼──────┐  ┌──────▼──────┐          │
│  │ChatViewModel│  │ScanViewModel│  │CartViewModel │          │
│  └──────┬──────┘  └──────┬──────┘  └──────┬──────┘          │
└─────────┼────────────────┼────────────────┼─────────────────┘
          │                │                │
┌─────────▼────────────────▼────────────────▼─────────────────┐
│                     Repository Layer                         │
│  ┌─────────────────┐  ┌─────────────────┐  ┌──────────────┐ │
│  │  AiRepository   │  │ ProductRepository│  │CartRepository│ │
│  └────────┬────────┘  └────────┬────────┘  └──────┬───────┘ │
└───────────┼────────────────────┼──────────────────┼─────────┘
            │                    │                  │
┌───────────▼────────────────────▼──────────────────▼─────────┐
│                      Network Layer                           │
│  ┌─────────────────┐              ┌─────────────────┐       │
│  │ NvidiaNimService│              │ KrogerApiService │       │
│  │  (AI Models)    │              │ (Retailer API)   │       │
│  └─────────────────┘              └─────────────────┘       │
└─────────────────────────────────────────────────────────────┘
```

### Key Components

| Component | Responsibility |
|-----------|---------------|
| `ChatViewModel` | Manages conversation state, message processing, recipe parsing |
| `ScanViewModel` | Handles image capture, AI vision analysis, item extraction |
| `AiRepository` | Abstracts AI service calls (chat + vision) |
| `ProductRepository` | Product search and caching |
| `CartRepository` | Cart state management and retailer sync |
| `KrogerAuthManager` | OAuth 2.0 token lifecycle management |

---

## Tech Stack

### Core
| Technology | Purpose |
|------------|---------|
| **Kotlin 2.0** | Primary language with coroutines for async operations |
| **Android SDK 24+** | Target platform |
| **View Binding** | Type-safe view access |
| **Navigation Component** | Fragment navigation with nav graph |

### AI/ML
| Technology | Purpose |
|------------|---------|
| **NVIDIA NIM API** | AI inference hosting |
| **Llama 3.1 8B Instruct** | Conversational AI and recipe parsing |
| **Llama 3.2 11B Vision** | OCR and image understanding |

### Networking
| Technology | Purpose |
|------------|---------|
| **Retrofit 2.9** | REST API client |
| **OkHttp 4.12** | HTTP client with logging |
| **Gson** | JSON serialization |

### Authentication
| Technology | Purpose |
|------------|---------|
| **AppAuth 0.11** | OAuth 2.0 with PKCE support |

### Media
| Technology | Purpose |
|------------|---------|
| **CameraX 1.3** | Camera capture with lifecycle awareness |
| **Coil 2.6** | Image loading and caching |

### UI
| Technology | Purpose |
|------------|---------|
| **Material Design 3** | Modern Android UI components |
| **RecyclerView** | Efficient list rendering |
| **Splash Screen API** | Modern app launch experience |

---

## Getting Started

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- JDK 11+
- Android device or emulator (API 24+)

### API Keys Required

1. **NVIDIA NIM API Key** - For AI model inference
   - Sign up at [NVIDIA Developer](https://developer.nvidia.com/)
   - Enable Llama 3.1 and Llama 3.2 Vision models

2. **Kroger API Credentials** - For product search and cart
   - Register at [Kroger Developer Portal](https://developer.kroger.com/)
   - Create an application with OAuth 2.0 credentials

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/CartAI.git
   cd CartAI
   ```

2. **Configure API keys**
   
   Create or edit `local.properties` in the project root:
   ```properties
   sdk.dir=/path/to/your/android/sdk
   KROGER_CLIENT_ID="your_kroger_client_id"
   KROGER_CLIENT_SECRET="your_kroger_client_secret"
   NVIDIA_API_KEY="your_nvidia_api_key"
   ```

3. **Build and run**
   ```bash
   ./gradlew installDebug
   ```

   Or open in Android Studio and click **Run**.

### First Launch

1. Launch the app
2. Sign in with your Kroger account via OAuth
3. Start chatting or scanning lists!

---

## API Integration

### AI Services

CartAI uses NVIDIA NIM for AI inference:

```kotlin
// Chat completion for natural language
nimService.chatCompletion(
    apiKey = "Bearer $apiKey",
    request = NimChatRequest(
        model = "meta/llama-3.1-8b-instruct",
        messages = listOf(NimMessage("user", prompt))
    )
)

// Vision completion for list scanning
nimService.visionCompletion(
    apiKey = "Bearer $apiKey",
    request = NimVisionRequest(
        model = "meta/llama-3.2-11b-vision-instruct",
        messages = listOf(/* image + prompt */)
    )
)
```

### Retailer API

The app integrates with grocery retailer APIs for real product data:

- **Product Search**: Find products with pricing by store location
- **Cart Management**: Add/remove items, update quantities
- **Authentication**: OAuth 2.0 with PKCE flow

---

## Project Structure

```
app/src/main/java/com/example/myapplicationeasyaiorder/
├── MainActivity.kt                 # Entry point, navigation setup
├── data/
│   ├── AiRepository.kt            # AI service interface
│   ├── AiRepositoryImpl.kt        # NVIDIA NIM implementation
│   ├── CartRepository.kt          # Cart management
│   ├── KrogerApiService.kt        # Retailer API definition
│   ├── KrogerAuthManager.kt       # OAuth token management
│   ├── LocalCartRepository.kt     # Local cart state
│   ├── NvidiaNimService.kt        # NIM API definition
│   ├── ProductRepository.kt       # Product search
│   └── RetrofitClient.kt          # HTTP client config
├── model/
│   ├── Models.kt                  # Retailer data models
│   ├── NimModels.kt               # AI request/response models
│   └── Resource.kt                # Result wrapper
└── ui/
    ├── EasyOrderViewModelFactory.kt
    ├── camera/
    │   ├── CameraFragment.kt      # Camera UI for scanning
    │   └── ScanViewModel.kt       # Image analysis logic
    ├── cart/
    │   ├── CartAdapter.kt         # Retailer cart items
    │   ├── CartFragment.kt        # Cart display
    │   ├── CartViewModel.kt       # Cart operations
    │   └── LocalCartAdapter.kt    # Staged items display
    ├── chat/
    │   ├── ChatAdapter.kt         # Message display
    │   ├── ChatFragment.kt        # Chat interface
    │   ├── ChatViewModel.kt       # Message processing
    │   └── PendingCartAdapter.kt  # Confirmation dialog
    └── login/
        ├── LoginFragment.kt       # OAuth login UI
        └── LoginViewModel.kt      # Auth state management
```

---

## Roadmap

### Current Integration
- ✅ Kroger API integration

### Future Retailer Integrations

| Retailer | Status | Notes |
|----------|--------|-------|
| **Walmart** | 🔜 Planned | Grocery API integration |
| **Instacart** | 🔜 Planned | Multi-store support |
| **Amazon Fresh** | 🔜 Planned | Prime integration |
| **Target** | 🔜 Planned | Same-day delivery |
| **Whole Foods** | 🔜 Planned | Amazon ecosystem |

### Planned Features

- [ ] **Multi-retailer support** - Compare prices across stores
- [ ] **Shopping list persistence** - Save and reuse lists
- [ ] **Smart reordering** - Predict recurring purchases
- [ ] **Dietary preferences** - Filter by allergies, diet type
- [ ] **Budget tracking** - Set spending limits
- [ ] **Store locator** - Find nearest stores with inventory
- [ ] **Price tracking** - Alert when items go on sale
- [ ] **Family sharing** - Collaborative shopping lists
- [ ] **Wear OS companion** - Quick adds from smartwatch
- [ ] **Widget support** - Home screen quick actions

---

## Contributing

Contributions are welcome! Please read our contributing guidelines before submitting PRs.

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## Acknowledgments

- [NVIDIA NIM](https://developer.nvidia.com/nim) for AI inference infrastructure
- [Meta Llama](https://llama.meta.com/) for open-source LLM models
- [Kroger Developer](https://developer.kroger.com/) for grocery API
- [AppAuth](https://appauth.io/) for OAuth 2.0 implementation

---

<p align="center">
  Made with ❤️ for smarter grocery shopping
</p>
