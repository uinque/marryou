@echo off
echo [INFO] Install jar to local repository.

cd %~dp0
cd ..
call mvn javadoc:jar
cd bin
pause