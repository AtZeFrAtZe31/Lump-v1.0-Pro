# Android Signing & Secrets

This project uses environment-based signing for tagged releases (v*).

Required GitHub Secrets:
- KEYSTORE_BASE64: base64 (no line breaks) of your release keystore, e.g. `base64 -w0 release.keystore > keystore.b64`.
- KEY_ALIAS: key alias inside the keystore.
- KEY_PASSWORD: password for the key.
- STORE_PASSWORD: password for the keystore.

In `app/build.gradle[.kts]` ensure:
```groovy
android {
  signingConfigs {
    release {
      storeFile file('release.keystore')
      storePassword System.getenv('STORE_PASSWORD')
      keyAlias System.getenv('KEY_ALIAS')
      keyPassword System.getenv('KEY_PASSWORD')
    }
  }
  buildTypes {
    release {
      signingConfig signingConfigs.release
      minifyEnabled false // enable with proper proguard rules if desired
      shrinkResources false
    }
  }
}
```
The workflow restores `release.keystore` only during the signed release job (tag builds).