package modelo;

import java.util.ArrayList;

public interface IJugador {
  int getId();
  String getNombre();
  void setFichas (ArrayList<Ficha> fichas);
  int getEstadoTurno ();
  void setEstadoTurno(int estadoTurno);
  ArrayList<Ficha> getFichas ();
  Ficha soltarFicha (int f);
  Combinacion combinacion (ArrayList<Integer> posiciones);
}
