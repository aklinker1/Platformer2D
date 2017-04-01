SET zipper="C:\Program Files\7-Zip\7z.exe"
cd ../
del Platformer2D.zip
cd out/artifacts/Platformer2D_jar/
%zipper% a "../../../Platformer2D.zip" "../../../res" "Platformer2D.jar" "../../../out/zip/Debug.sh" "../../../out/zip/README.txt" "../../../out/zip/bin"
ECHO 3/%steps% - Cleaning up.
ECHO Finished!