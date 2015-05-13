#!/bin/bash
#echo  Building Python Documentation ...
echo  Building Android Documentation 
#javasphinx-apidoc -fo source/android_docs/api/ ../android/EhrLib/src/it/crs4/most/ehrlib/

#PYTHONPATH=../python/src/ make html
echo  Building Most Visualization Documentation ...
make html
