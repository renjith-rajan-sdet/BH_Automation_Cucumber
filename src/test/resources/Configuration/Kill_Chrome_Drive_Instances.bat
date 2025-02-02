echo off
::tasklist /fi "imagename eq Chrome.exe" |find ":" > nul
::if errorlevel 1 taskkill /F /IM Chrome.exe
tasklist /fi "imagename eq ChromeDriver.exe" |find ":" > nul
if errorlevel 1 taskkill /F /IM ChromeDriver.exe
exit