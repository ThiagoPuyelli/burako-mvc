package modelo;

import java.io.Serializable;

public class Ficha implements IFicha, Serializable {
  static int ID = 0;
  private int id;
  private int numero;
  private ColorFicha color;

  public Ficha (ColorFicha color, int numero) {
    this.id = ID;
    this.numero = numero;
    this.color = color;
  }

  public ColorFicha getColor () { return this.color; }

  public int getNumero () { return this.numero; }
  public int getId () { return this.id; }
}
