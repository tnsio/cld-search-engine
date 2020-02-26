#!/bin/bash

#Tries to install the only dependency using apt
apt install jdk
mkdir classFiles
javac -d ./classFiles/ -cp "./src" -g src/main/Main.java
