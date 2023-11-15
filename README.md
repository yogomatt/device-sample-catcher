# Development environment

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

# Continues deployment

## Installing github-release

GitHub provides a repository to publish releases. This task is available via Web interface or API.

To facilitate the integration of Jenkins with GitHub for publishing releases let's use the tool [github-release](https://github.com/github-release/github-release). This tool invokes the GitHub API for the necessary actions when releasing a version is required.

To install github-release, the go compiler is required.

1. Execute the following commands to install go

    > sudo add-apt-repository ppa:longsleep/golang-backports -y

    > sudo apt update

    > sudo apt install golang

    > go version

2. To install github-release use the following command.

    > go install github.com/github-release/github-release@latest

3. Create a global variable $GOPATH and add it to the $PATH

    > nano ~/.bashrc

        export GOPATH=~/go
        export PATH=$PATH:$GOPATH/bin


## Jenkins installation

For the simplest installation, follow the next steps:

1. Download the most recent **Generic Java package(.war)** from the following link [https://www.jenkins.io/download/](https://www.jenkins.io/download/)
2. Create the directory /opt/jenkins
3. Create the directory /opt/jenkins/config
4. Copy the download war **jenkins.war** to the directory /opt/jenkins
5. Create a script file **jenkins_start.sh** with the following content

    > java -Dorg.apache.commons.jelly.tags.fmt.timeZone="America/Guayaquil" -jar jenkins.war --httpPort=9090

6. Add the global variable $JENKINS_HOME

    > nano ~/.bashrc

        export JENKINS_HOME=/opt/jenkins/config


7. To start the Jenkins server execute the created script

    > ./jenkins_start.sh

8. Jenkins is available in the url [http://localhost:9090](http://localhost:9090)
9. Configure jenkins for the first time and create a user (credentials: admin/admin)


## Jenkins credentials for GitHub

Setup the GitHub credentials that Jenkins uses to clone a project.

1. Login to Jenkins
2. Go to Manage Jenkins > Credentials > System > Global credentials (unrestricted)
3. Click on **Add Credentials**
4. Configure the following entries as follow:

    - Kind: Username with password
    - Scope: Global
    - Username: github_email
    - Password: github_password
    - ID: github_yogomatt
    - Description: Optional

5. Save the credentials

## Jenkins Job for deployment of API

The following job setup seeks to clone a github project, build it using Gradle and publish a GitHub release.

1. Login to Jenkins
2. Go to Manage Jenkins > New Item
3. Enter a name for the job(i.e. iot-api-job)
4. Configure the following entries as follow:

    - Discard old builds:
        - Strategy: Log rotation
        - Days to keep builds: 10
        Max # of builds to keep: 1
    - GitHub project
        - Project url: https://github.com/yogomatt/device-sample-catcher/
    - This project is parameterized
        - String Parameter
            - Name: TAG_VERSION_NUMBER
            - Description: To be used after build to indicate the release version number
            - Trim the string: Check
    - Source code management
        - Git
            - Repositories
                - Repository URL: https://github.com/yogomatt/device-sample-catcher.git
                - Credentials: Select the already registered GitHub credentials
            - Branches to build
                - Branch specifier: */dev
    - Build environment
        - Delete workspace before build starts: Check
    
    - Build Steps
        - Add build step: Invoke Gradle script
            - Use Gradle Wrapper: Check
            - Tasks: bootJar
        - Add build step: Execute shell
            - Command:
                > echo "Exporting token to enable github-release tool"
                >
                > export GITHUB_TOKEN=**Use GitHub personal token**
                >
                > export GITHUB_USER="yogomatt"
                >
                > export GITHUB_REPO="device-sample-catcher"
                >
                > #echo "Creating a new release in github"
                >
                > github-release release --user \${GITHUB\_USER} --repo \${GITHUB\_REPO} --name \${TAG\_VERSION\_NUMBER} --description "My first attempt from jenkins!" --tag \${TAG\_VERSION\_NUMBER}
                >
                > #echo waiting for release creation
                >
                > sleep 2
                >
                > #echo "Uploading the artifacts into github"
                >
                > github-release upload --user \${GITHUB\_USER} --repo \${GITHUB\_REPO} --name "\${GITHUB\_REPO}\-\${TAG\_VERSION\_NUMBER}.jar" --tag \${TAG\_VERSION\_NUMBER} --file "\${WORKSPACE}/build/libs/\${GITHUB\_REPO}\-\${TAG\_VERSION\_NUMBER}.jar"


# Raspberry PI

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


## Download release bianries from GitHub via command

wget -P /home/byron/Downloads $(curl -s https://api.github.com/repos/yogomatt/device-sample-catcher/releases/latest | grep "browser_download_url" | awk '{print $2}' | sed 's|[\"\,]*||g')