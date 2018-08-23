# migi-xor
Decode / encode mp3 for the Storymania Hörbox

## compile
with gradle
### gradle build
To compile the classes. A jar is created, but can not be run.
### gradle run
To run the compiled classes.
### gradle jar
To create a fat jar, which can be run with '''java -jar build/libs/*-fat.jar'''
## usage
'''gradle jar'''
'''java -jar build/libs/*-fat.jar [FileName]'''
where '''[FileName]''' can be:
* can be a file. This file will be converted, automatically from .mp3 to .smp and vice versa.
* omitted. In this case all files and folders in the current directory are searched. Matching files will be converted.
* can be a folder. In this case all files and folders in this folder will be searched. Matching files will be converted.
