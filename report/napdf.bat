@echo off
REM Sprawdź, czy Inkscape jest w PATH
where inkscape >nul 2>nul
if %errorlevel% neq 0 (
    echo Inkscape nie jest w PATH. Upewnij się, że jest zainstalowany i dodany do zmiennych środowiskowych.
    pause
    exit /b
)

pushd "%~dp0../plots"

REM Iteracja przez wszystkie pliki svg
for %%f in (*.svg) do (
    echo Konwertowanie %%f na %%~nf.pdf...
    inkscape "%%f" --export-filename="%%~nf.pdf"
    if %errorlevel% neq 0 (
        echo Wystąpił błąd podczas konwertowania %%f.
    )
)

popd
echo Zakończono konwersję plików SVG na PDF.
pause
