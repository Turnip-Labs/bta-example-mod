# Example Mod

Template for making Babric mods for BTA!

## Prerequisites
- JDK for Java 17 ([Eclipse Temurin](https://adoptium.net/temurin/releases/) recommended)
- IntelliJ IDEA
- Minecraft Development plugin (Optional, but highly recommended)

## Setup instructions

1. Download or clone this repository and put it somewhere.
```
git clone https://github.com/Turnip-Labs/bta-example-mod.git
```

2. Import the project in IntelliJ IDEA, close it and open it again.


3. Create a new run configuration by going in `Run > Edit Configurations`  
   Then click on the plus icon and select Gradle. In the `Tasks and Arguments` field enter `build`  
   Running it will build your finished jar files and put them in `build/libs/`


4. Open `File > Settings` and head to `Build, Execution, Development > Build Tools > Gradle`  
   Make sure `Build and run using` and `Run tests using` is set to `Gradle`


5. Open `File > Project Structure`, select `Project` and set `Compiler output` to your project's path/out.

6. **Make sure your Client and Server run configurations have `-Dfabric.gameVersion=1.7.7.0` in `VM options`!**

7. Done! Now all that's left is to change every mention of `examplemod` to your own mod id. Happy modding!
