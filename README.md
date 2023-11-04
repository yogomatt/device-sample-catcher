# device-sample-catcher

## GitHub authentication

When HTTPS is used for cloning the project, git actions require authentication with personal access tokens (user and password were deprecated to improve security).

To create a personal access token:

1. In the upper-right corner of any page, click your profile photo, then click Settings.
2. Click on Developer Settings
3. Under Personal access tokens click Tokens (classic)
4. Click Generate new token
5. Enter a name
6. Select an expiration period
7. Optionally, enter a description
8. Select the scopes you'd like to grant this token. To use your token to access repositories from the command line, select repo. A token with no assigned scopes can only access public information

Reference: [Managing your personal access tokens](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens)

Once the personal access token is created, copy it and execute the following steps.

1. Go to your Git project directory in the terminal
2. git remote set-url origin https://YOUR_TOKEN_HERE@github.com/your_user/your_project.git

Reference: [Git push from Visual Studio](https://stackoverflow.com/questions/60757334/git-push-from-visual-studio-code-no-anonymous-write-access-authentication-fai)

## JAVA 17 installation in Raspberry PI

Java 17 is required by Spring Boot 3.0 however the Debian repository is not ready to support OpenJDK 17 at the moment of this commit.

In consecuence, let's install Open JDK 17 downloading the tar file from the Adoptium github release repository and install it following the next steps.

1. Check the avaiable releases by browsing [https://github.com/adoptium/temurin17-binaries/releases/download](https://github.com/adoptium/temurin17-binaries/releases/download)
2. Find the appropriate release. For raspberry 4 find the arm (usually this means 32bit) release (to find i=out about the raspberry cpu architecture run lscpu in the raspberry terminal).
3. Execute the following command to download the tar file. Replace the url with the one of the selected release.

> wget https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.8%2B7/OpenJDK17U-jdk_arm_linux_hotspot_17.0.8_7.tar.gz

4. Create a java directory

> sudo mkdir /opt/java

5. Untar the tar file

> sudo tar -zxf OpenJDK17U-jdk_arm_linux_hotspot_17.0.8_7.tar.gz -C /opt/java

6. Install using update-alternatives

> sudo update-alternatives --install /usr/bin/java java /opt/java/jdk-17.0.8+7/bin/java 100
> sudo update-alternatives --install /usr/bin/javac javac /opt/java/jdk-17.0.8+7/bin/javac 100

7. Configure the new JDK

> sudo update-alternatives --config java

8. Check the java version using

> java -version
