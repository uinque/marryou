@echo off
echo [INFO] dependency tree.

cd %~dp0
cd ..
call mvn dependency:tree
cd bin
pause