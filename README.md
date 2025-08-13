
# Lump v1.0 — Pro Android (Kotlin, Dark Neon UI)

**Funktionen**: Live‑Linechart + Candlestick, Trade‑Panel (Demo), Chat (Wikipedia‑Lookup), TTS/STT‑Hooks, Dark‑Neon‑Design.  
**CI**: Debug‑APK bei jedem Push; **signierte Release‑APK** bei Tag `v*` (Secrets erforderlich).

## 🚀 Build auf GitHub
1. Repo öffnen → **Add file → Upload files** (ENTPACKTEN Ordnerinhalt ins Repo‑Root)
2. Commit → **Actions** checken
3. **Artifacts**: `Lump-debug-apk` (Debug‑APK)

### 🔐 Release‑Build (signiert)
1. Lokal Keystore erzeugen:
   ```bash
   keytool -genkeypair -v -keystore lump.keystore -alias lump -keyalg RSA -keysize 2048 -validity 3650
   base64 lump.keystore > lump.keystore.base64
   ```
2. Repo‑**Secrets** setzen (Settings → Secrets → Actions):
   - `KEYSTORE_BASE64`
   - `KEY_ALIAS`
   - `KEY_PASSWORD`
   - `STORE_PASSWORD`
3. Tag pushen:
   ```bash
   git tag v1.0.0
   git push origin v1.0.0
   ```
→ GitHub erstellt ein **Release** inkl. **signierter APK**.

## 🛠 Anpassen
- Farben: `res/values/colors.xml`
- Menü: `res/menu/menu_bottom.xml`
- Paket: `app/build.gradle.kts`, `AndroidManifest.xml`
- Logik: `ChatFragment.kt`, `DashboardFragment.kt`, `TradeFragment.kt`

Stand: 2025-08-13
