# Base Clean Android [![](https://jitpack.io/v/yaroslavsudnik/base-clean-android.svg)](https://jitpack.io/#yaroslavsudnik/base-clean-android)

Base classes for Android projects. Inspired by [The Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) + [MVVM](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) + States (redux🤷‍♂️).<br/>
Open to suggestions 👋. Let's [discuss](https://github.com/yaroslavsudnik/base-clean-android/issues/new/choose)!

## Using in projects
Published to [JitPack](https://jitpack.io/#yaroslavsudnik/base-clean-android)

### Gradle
🌈 [App dependencies:](/app/build.gradle)
```groovy
implementation project(path: ':data')
implementation 'com.github.yaroslavsudnik.base-clean-android:presentation:<version>'
```
    
📡 [Data dependencies:](/data/build.gradle)
```groovy
api project(path: ':domain')
```
    
🎛 [Domain dependencies:](/domain_layer/build.gradle)
```groovy
api 'com.github.yaroslavsudnik.base-clean-android:domain:<version>'
```

Add it in your root build.gradle at the end of repositories:

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```
---
### [Maven & other variants](https://jitpack.io/#yaroslavsudnik/base-clean-android)
