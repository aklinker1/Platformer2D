SET steps=4
ECHO 1/%steps% - Copying Resources.
robocopy ../res ../out/artifacts/Platformer2D_jar/res/ /E
robocopy . ../out/artifacts/Platformer2D_jar/ Debug.bat
ECHO 2/%steps% - Zipping Game Contents.
SET zipper="C:\Program Files\7-Zip\7z.exe"
cd ../out/artifacts/Platformer2D_jar/
del "../../../Platformer2D.zip"
%zipper% a "../../../Platformer2D.zip" "res" "Platformer2D.jar" "../../../out/zip/Debug.sh" "../../../out/zip/README.txt"
ECHO 3/%steps% - Cleaning up.
rd res /s /q
ECHO Finished!