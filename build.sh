#!/bin/sh

if [ "$NEXUS_PASSWORD" = "" ]; then
  echo "NEXUS_PASSWORD not defined"
  exit 0
fi
set -e
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
export JAVA_TOOL_OPTIONS='-Duser.language=en -Duser.country=US'
java --version
mvn -DaltDeploymentRepository=drivenow.nexus::default::https://nexus.drivenow.com.au/repository/thirdparty -DskipTests deploy -f bom
env gradle clean assemble build sA -x test
env gradle publish -x test -PmavenRepositoryUsername=deployment -PmavenRepositoryPassword="${NEXUS_PASSWORD}" -PmavenRepositoryUrl=https://nexus.drivenow.com.au/repository/thirdparty
