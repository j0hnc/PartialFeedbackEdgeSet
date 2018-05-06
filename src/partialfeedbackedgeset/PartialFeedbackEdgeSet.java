package partialfeedbackedgeset;

import java.util.ArrayList;

/**
 *
 * @author John
 */
public class PartialFeedbackEdgeSet {
    
    public static ArrayList<ArrayList<Arista>> buscarSubgrafos(ArrayList<int[]> listaAdyacencia, int K) {        
        ArrayList<ArrayList<Arista>> listaSubgrafos = new ArrayList<>();
        
        int numVertice = 0;
        
        for (int[] vertice : listaAdyacencia) {
            ArrayList<Arista> subgrafo = new ArrayList<>();
            for (int i = 0; i < vertice.length; i++) {
                // Agregando (1,0) ... (1,0) (1,4) ... (1,0) (1,4) (1,3)
                subgrafo.add(new Arista(numVertice, vertice[i]));
                ArrayList<Arista> temp = new ArrayList<>(subgrafo);
                if (temp.size() == K) {
                    listaSubgrafos.add(temp);
                }
                // Agregando (1,0) ... (1,4) ... (1,3)
                if (i > 0) {
                    ArrayList<Arista> unElemento = new ArrayList<>();
                    unElemento.add(new Arista(numVertice, vertice[i]));
                    if (unElemento.size() == K) {
                        listaSubgrafos.add(unElemento);
                    }                    
                }                
            }   
            numVertice++;
        }
        
        // Verificar que subgrafos no se repitan, consumen mucho espacio.
        removerRepetidos(listaSubgrafos);
        
        // Unir subgrafos
        int tam = listaSubgrafos.size();
        for (int i = 0; i < tam; i++) {
            for (int j = i + 1; j < tam - 1; j++) {
                ArrayList<Arista> nuevaLista = new ArrayList<>(listaSubgrafos.get(i));
                nuevaLista.addAll(listaSubgrafos.get(j));
                listaSubgrafos.add(nuevaLista);
            }                
        }       
                
        return listaSubgrafos;    
    }
    
    public static ArrayList<ArrayList<Arista>> removerRepetidos(ArrayList<ArrayList<Arista>> lista) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).size() == 1) {
                for (int j = i + 1; j < lista.size() - 1; j++) {
                    if (lista.get(i).size() == 1) {
                        int ui = lista.get(i).get(0).u;
                        int vi = lista.get(i).get(0).v;
                        
                        int uj = lista.get(j).get(0).u;
                        int vj = lista.get(j).get(0).v;
                        if ((ui == uj && vi == vj) || (ui == vj && vi == uj)) {
                            lista.remove(i);
                        }
                    }
                }
            } 
        }
        return lista;
    }
    
    public static void mostrarLista(ArrayList<ArrayList<Arista>> subgrafos) {
        for (ArrayList<Arista> lista : subgrafos) {
            for (Arista a : lista)
                System.out.print(a.u + "," + a.v + " ");
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        
        System.out.println("Working on it...");
        
        ArrayList<int[]> grafo = new ArrayList<>();
        
        int[] l0 = {1, 4};
        int[] l1 = {0, 4, 3, 2};
        int[] l2 = {1, 3};
        int[] l3 = {1, 4, 2};
        int[] l4 = {3, 0, 1};
        
        grafo.add(l0);
        grafo.add(l1);
        grafo.add(l2);
        grafo.add(l3);
        grafo.add(l4);
        
        mostrarLista(buscarSubgrafos(grafo, 3));
        //buscarCircuitos();
        
        System.out.println("Done!");
        
        
    }
    
}
