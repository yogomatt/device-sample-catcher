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