package modelo;

import java.util.ArrayList;

public interface IJugador {
  int getId();
  String getNombre();
  void setFichas (ArrayList<IFicha> fichas);
  int getEstadoTurno ();
  void setEstadoTurno(int estadoTurno);
  ArrayList<IFicha> getFichas ();
  IFicha soltarFicha (int f);
  Combinacion combinacion (ArrayList<Integer> posiciones);
}
