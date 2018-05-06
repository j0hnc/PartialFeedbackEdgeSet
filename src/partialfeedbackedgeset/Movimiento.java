package partialfeedbackedgeset;

/**
 *
 * @author John
 */
public class Movimiento {
    
    public int verticeActual, verticeSig;
    public int vMov; // En que vertice de sus adyacentes se encuentra
    
    public Movimiento(int verticeActual, int vMov) {
        this.verticeActual = verticeActual;
        this.vMov = vMov;
        //verticeSig = ady[vMov];
    }
}
