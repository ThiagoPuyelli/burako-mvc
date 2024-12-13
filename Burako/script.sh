javac -cp lib/LibreriaRMIMVC.jar -d out/test/Test  src/*/*/*/*.java src/*.java src/*/*.java src/*/*/*.java

java -cp out/test/Test:lib/LibreriaRMIMVC.jar Servidor/AppServidor
