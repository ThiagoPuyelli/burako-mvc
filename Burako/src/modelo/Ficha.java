package modelo;

import java.io.Serial;
import java.io.Serializable;

public class Ficha implements IFicha, Serializable {
  @Serial
  private static final long serialVersionUID = 8012057219940348254L;
  private int numero;
  private ColorFicha color;

  public Ficha (ColorFicha color, int numero) {
    this.numero = numero;
    this.color = color;
  }

  public ColorFicha getColor () { return this.color; }

  public int getNumero () { return this.numero; }
}
