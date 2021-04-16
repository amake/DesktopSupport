# DesktopSupport Library for Java

This library bridges the Apple Java Extensions APIs and the JEP 272 desktop APIs
added in Java 9. It is intended for use by applications that want to support
running on both Java 8 and Java 11.

- [Apple Java Extensions](https://developer.apple.com/library/archive/samplecode/AppleJavaExtensions/Introduction/Intro.html)
- [Apple Java Extensions Javadoc](https://coderanch.com/how-to/javadoc/appledoc/api/)
- [JEP 272: Platform-Specific Desktop Features](https://openjdk.java.net/jeps/272)

## Implementation

This library wraps a subset of the `com.apple.eawt.Application` API and
delegates to whichever actual implementation is available: Apple or Java 9+, or
a dummy no-op implementation if neither.

Thus it is safe to call the API anywhere, without checking the platform
first. Note, however, that listeners and handlers will never be called under the
no-op implementation, so critical logic must not be placed there.

| Java version | Platform | Implementation     |
|--------------|----------|----------------    |
| Java 8       | macOS    | `com.apple.eawt.*` |
| Java 8       | Other    | No-op              |
| Java 11      | Any      | `java.awt.*`       |

### Coverage

- Non-deprecated methods on `com.apple.eawt.Application`
- Methods on `com.apple.eawt.FullScreenUtilities`

## Requirements

Java 8+ is required to run.

Java 11 is required in order to build.

## Usage

Add the dependency:

```groovy
implementation 'org.madlonkay:desktopsupport:+'
```

Call methods on `DesktopSupport.getSupport()`:

```java
DesktopSupport.getSupport().disableSuddenTermination();
DesktopSupport.getSupport().setAboutHandler(e -> {
    // handle About event
});
```

The API is meant to mostly match the ones it wraps, but it is not quite a
drop-in replacement:

- Event objects that have no methods or payloads (e.g. `AboutEvent`) are just
  `Object`
- `FullScreenUtilities` (Apple) and `Taskbar`/`GraphicsEnvironment`/etc. (Java
  9) methods are on `IDesktopSupport` with everything else rather than in a
  separate class

## License

Apache 2.0
