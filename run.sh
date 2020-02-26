#!/bin/bash

if [[ "$1" == -t ]]; then
    java -cp classFiles main.Main ./documente_problema/doc*.txt
else
    java -cp classFiles main.Main $*
fi
