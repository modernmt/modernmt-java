```
# environment variables
export ORG_GRADLE_PROJECT_mavenCentralUsername=modernmt
export ORG_GRADLE_PROJECT_mavenCentralPassword=SONATYPE_PASSWORD_GOES_HERE
```
`./gradlew build`\
`./gradlew publish -Psigning.keyId=E0F9CC42 -Psigning.secretKeyRingFile=$HOME/.gnupg/modernmt.gpg -Psigning.password=$(cat ~/.gnupg/mmt_passphrase)`\
`./gradlew closeAndReleaseRepository`