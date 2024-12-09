javac -cp lib/LibreriaRMIMVC.jar -d out/test/Test src/Servidor/AppServidor.java src/modelo/* src/Services/*

java -cp out/test/Test:lib/LibreriaRMIMVC.jar Servidor/AppServidor
