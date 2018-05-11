package partialfeedbackedgeset;

/**
 *
 * @author John
 */
public class Movimiento {
    
    public int verticeActual;
    public int verticeSig;
    public int vMov; // Arista: (verticeActual, vMov)
    
    public Movimiento(int verticeActual, int vMov) {
        this.verticeActual = verticeActual;
        this.vMov = vMov;
    }
}
