#!/bin/sh

if [ "$NEXUS_PASSWORD" = "" ]; then
  echo "NEXUS_PASSWORD not defined"
  exit 0
fi
set -e
mvn -DaltDeploymentRepository=drivenow.nexus::default::https://nexus.drivenow.com.au/repository/thirdparty -DskipTests deploy -f bom
env JAVA_TOOL_OPTIONS='-Duser.language=en -Duser.country=US' gradle assemble build sA -x test
env JAVA_TOOL_OPTIONS='-Duser.language=en -Duser.country=US' gradle publish -x test -PmavenRepositoryUsername=deployment -PmavenRepositoryPassword="${NEXUS_PASSWORD}" -PmavenRepositoryUrl=https://nexus.drivenow.com.au/repository/thirdparty
