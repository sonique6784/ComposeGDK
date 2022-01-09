# ComposeGDK
The goal of Compose Game Development Kit, is to provide a Multiplatform Game SDK based on Compose. It is a 2D Game Engine ideal to build old-school games like Platform, Puzzle or Fight Games.

## Disclaimer
This is not a Google or JetBrains project, this is not supported by any company (yet). Given the experimental nature of this project, I can't guarantee much support at this stage. I hope we can build a community so that this become a strong library.

## Install
Compose GDK requires Java 11 or above. It can be installed with homebrew (MacOS)
```
$ brew install java11
$ sudo ln -sfn /usr/local/opt/openjdk@11/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-11.jdk
```
Verify the version is correct
```
$ /usr/libexec/java_home
$ java -version
```

then run for MacOS 💻:
```
$ ./gradlew run
```

then run for Android 🤖:

```
$ ./gradlew installDebug
```

## Roadmap 🗺
- [ 20%] Scroll and parallax
- [ 20%] Basic character/object movement (forward, backward, jump)
- [ 30%] Collision support
- [ 40%] Keyboard support
- [ 10%] Score system
- [ 10%] Touch Controller support (Touch screen joystick)
- [  0%] Performance and optimisations
- [  0%] Sound support
- [  0%] Animated image (GIF-like)
- [  0%] Play/pause
- [  0%] Save mechanism
- [  0%] Multi-player (2 people with 1 keyboard, MacOS only)
- [  0%] Game Controller support (XBox, PS4+)
- [  0%] Multi-player (with multiple Game Controller)
- [  0%] Network
- [  0%] Multi-player over Network
- [  0%] Mouse support
- [  0%] Accelerometer support


# Main principles
## CGDKObject (GameObject)
the simplest object is CGDKObject, it has position and size, this can be extended to place more complex object on the screen, such as ImageCGDKObject.

## GameCanvas
The GameObjects are placed on the Game Canvas

## Animation / Scroll
Both Horizontal and Vertical scroll have been implemented, they support speed which can be use to create a parallax effect

## Collision detector
The collision detector is very simple at this stage, it help to determine if 2 objects has entered in collision, this is useful to prevent object going too far, determine if the hero has encourter an ennemie or to implement gravity.

## Controllers
- A Simple Keyboard controller that have 4 directions is implemented
- A Draft of Touch and Joystick Virtual Controller (for touch screen) is implemented

## Score
A basic score controller has been implemented