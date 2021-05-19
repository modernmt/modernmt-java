#!/bin/bash

VERSION="$1"

if [[ ! "$VERSION" =~ ^[0-9]+\.[0-9]+\.[0-9]+$ ]]; then
	echo "Invalid version number"
	exit 1
fi

gradle_match="version = "
gradle_ver="version = '${VERSION}'"
sed -i -E "/$gradle_match/s/.*/$gradle_ver/" modernmt/build.gradle

src_file="modernmt/src/main/java/com/modernmt/ModernMT.java"

header_match="        this\(apiKey, platform, "
header_ver="        this\(apiKey, platform, \"${VERSION}\"\);"
sed -i -E "/$header_match/s/.*/$header_ver/" $src_file
