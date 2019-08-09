curl -s "https://get.sdkman.io" | bash

source "/home/zhihaow/.sdkman/bin/sdkman-init.sh"

sdk install gradle 5.5.1

gradle init

gradle build

gradle run
