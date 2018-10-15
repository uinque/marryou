@echo off
echo [INFO] Clean jar to local repository.

cd %~dp0
cd ..
call mvn clean
cd bin
pause