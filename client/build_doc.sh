#!/bin/bash
#echo  Building Python Documentation ...
echo  Building Android Documentation 
#javasphinx-apidoc -fo source/android_docs/api/ ../client/android/src/src/org/crs4/most/visualization/

#PYTHONPATH=../python/src/ make html
echo  Building Most Report Documentation ...
make html
