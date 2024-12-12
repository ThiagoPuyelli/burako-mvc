package modelo;

import java.io.Serializable;

public class Ficha implements IFicha, Serializable {
  private int numero;
  private ColorFicha color;

  public Ficha (ColorFicha color, int numero) {
    this.numero = numero;
    this.color = color;
  }

  public ColorFicha getColor () { return this.color; }

  public int getNumero () { return this.numero; }
}
