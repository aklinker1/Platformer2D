SET steps=4
ECHO 1/%steps% - Copying Resources.
robocopy ../res ../out/artifacts/Platformer2D_jar/res/ /E
robocopy . ../out/artifacts/Platformer2D_jar/ Debug.bat
robocopy . ../out/artifacts/Platformer2D_jar/ Run.bat
ECHO 2/%steps% - Zipping Game Contents.
SET zipper="C:\Program Files\7-Zip\7z.exe"
cd ../
cd out/artifacts/Platformer2D_jar/
%zipper% a "../../../Platformer2D.zip" "res" "Platformer2D.jar" "Run.bat" "Debug.bat"
ECHO 3/%steps% - Cleaning up.
rd res /s /q
:del Debug.bat
:del Run.bat
del settings.pref
ECHO Finished!