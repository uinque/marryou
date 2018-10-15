@echo off
echo [INFO] Clean jar to local repository.

cd %~dp0
cd ..
call mvn -Dmaven.test.skip=true clean package install assembly:assembly -U
cd bin
pause
