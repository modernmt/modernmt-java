```
# prepare gpg private key
gpg --import modernmt.asc
gpg --edit-key E0F9CC42 # set trust to 5
gpg --export-secret-keys E0F9CC42 > ~/.gnupg/modernmt.gpg
```
```
# environment variables
export ORG_GRADLE_PROJECT_mavenCentralUsername=modernmt
export ORG_GRADLE_PROJECT_mavenCentralPassword=SONATYPE_PASSWORD_GOES_HERE
```
`./gradlew build`\
`./gradlew publish -Psigning.keyId=E0F9CC42 -Psigning.secretKeyRingFile=$HOME/.gnupg/modernmt.gpg -Psigning.password=$(cat ~/.gnupg/mmt_passphrase)`\
`./gradlew closeAndReleaseRepository`
