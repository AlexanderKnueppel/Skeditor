#!/bin/bash

# Let the person running the script know what's going on.
echo "\n\033[1mGenerate new lexer and parser...\033[0m\n"

java -cp ../../../../../../libs/antlr-4.7.1-complete.jar org.antlr.v4.Tool -visitor -package de.tubs.skeditor.contracting.grammar fol.g4

echo "";
echo "Done!";