#!/bin/sh

export PATH_TO_FX=/home/gueno/Téléchargements/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib

java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml -jar target/AbmVis-1.0-SNAPSHOT-jar-with-dependencies.jar
#java -jar target/AbmVis-1.0-SNAPSHOT-jar-with-dependencies.jar
