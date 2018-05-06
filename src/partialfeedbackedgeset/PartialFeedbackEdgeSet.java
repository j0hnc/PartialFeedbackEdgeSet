package partialfeedbackedgeset;

import java.util.ArrayList;

/**
 *
 * @author John
 */
public class PartialFeedbackEdgeSet {
    
    public static ArrayList<ArrayList<Arista>> buscarSubgrafos(ArrayList<int[]> listaAdyacencia, int K) {        
        ArrayList<ArrayList<Arista>> listaSubgrafos = new ArrayList<>();
        
        listaAdyacencia.forEach((a) -> {
            ArrayList<Arista> subgrafo = new ArrayList<>();
            for (int i = 0; i < a.length; i++) {
                // Uniendo (1,0) ... (1,0) (1,4) ... (1,0) (1,4) (1,3)
                subgrafo.add(new Arista(i, a.length));
                listaSubgrafos.add(subgrafo);
                
                // Agregando (1,0) ... (1,4) ... (1,3)
                if (i > 0) {
                    ArrayList<Arista> unElemento = new ArrayList<>();
                    unElemento.add(new Arista(i, a[i]));
                    listaSubgrafos.add(unElemento);
                }                
            }            
        });
        
        // Verificar que subgrafos no se repitan, consumen mucho espacio.
        
        // Unir subgrafos
        for (int i = 0; i < listaSubgrafos.size(); i++) {
            for (int j = 0; j < listaSubgrafos.size(); j++) {
                if (i != j) listaSubgrafos.get(i).addAll(listaSubgrafos.get(j));
            }
        }       
                
        return listaSubgrafos;        
    }

    public static void main(String[] args) {
        
        System.out.println("Working on it...");
        
        ArrayList<int[]> grafo = new ArrayList<>();
        
        int[] l0 = {1, 2};
        int[] l1 = {0};
        int[] l2 = {0};
        //int[] l3 = {1, 4, 2};
        //int[] l4 = {3, 0, 1};
        
        grafo.add(l0);
        grafo.add(l1);
        grafo.add(l2);
        //grafo.add(l3);
        //grafo.add(l4);
        
        for (ArrayList<Arista> lista : buscarSubgrafos(grafo, 3)) {
            for (Arista a : lista) {
                System.out.print(a.u + "," + a.v + " ");
            }
            System.out.println("");
        }
        
        System.out.println("Done!");
        
        
    }
    
}
