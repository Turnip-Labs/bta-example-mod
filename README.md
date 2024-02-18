# Example Mod

Template for making Babric mods for BTA!

**Note: *DO NOT fork this repository unless you want to contribute!***

## Prerequisites
- JDK for Java 17 ([Eclipse Temurin](https://adoptium.net/temurin/releases/) recommended)
- IntelliJ IDEA
- Minecraft Development plugin (Optional, but highly recommended)

## Setup instructions
   

1. Click the `Use this template` button on this repo's page above. Choose `Create a new repository`, you will be redirected to a new page. Enter your repo's name and description, and hit `Create repository`.  
   To get your project, open IntelliJ IDEA and click `Get from VCS`. Select `Repository URL` and enter your repo's url

2. After the project has finished importing, close it and open it again.  
   If that does not work, open the right sidebar with `Gradle` on it, open `Tasks` > `fabric` and run `ideaSyncTask`.

3. Create a new run configuration by going in `Run > Edit Configurations`.  
   Then click on the plus icon and select Gradle. In the `Tasks and Arguments` field enter `build`.  
   Running it will build your finished jar files and put them in `build/libs/`.

4. Lastly, open `File` > `Settings` and head to `Build, Execution, Development` > `Build Tools` > `Gradle`.  
   Make sure `Build and run using` and `Run tests using` is set to `Gradle`.

5. Done! Now, all that's left is to change every mention of `examplemod` and `turniplabs` to your own mod id and mod group, respectively. Happy modding!
