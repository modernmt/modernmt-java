#!/bin/bash

VERSION="$1"

if [[ ! "$VERSION" =~ ^[0-9]+\.[0-9]+\.[0-9]+$ ]]; then
	echo "Invalid version number"
	exit 1
fi

build_match="version = "
build_ver="version = '${VERSION}'"
sed -i -E "/$build_match/s/.*/$build_ver/" modernmt/build.gradle

prop_match="VERSION_NAME="
prop_ver="VERSION_NAME=${VERSION}"
sed -i -E "/$prop_match/s/.*/$prop_ver/" gradle.properties

src_file="modernmt/src/main/java/com/modernmt/ModernMT.java"

header_match="        this\(apiKey, platform, "
header_ver="        this\(apiKey, platform, \"${VERSION}\"\);"
sed -i -E "/$header_match/s/.*/$header_ver/" $src_file
