package partialfeedbackedgeset;

import java.util.ArrayList;
import java.util.Stack;

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
            for (Arista a : lista) {
                System.out.print(a.u + "," + a.v + " ");
            }
            System.out.println("");
        }
    }

    public static void buscarCircuitos(ArrayList<int[]> listaAdyacencia, int L) {
        ArrayList<ArrayList<Arista>> circuitos = new ArrayList<>();
        // Agregar circuitos encontrados desde cada vertice.
        for (int i = 0; i < listaAdyacencia.size(); i++) {
            circuitos.addAll(buscarCircuitosVertice(listaAdyacencia, i));
        }
        mostrarLista(circuitos);
    }

    public static ArrayList<ArrayList<Arista>> buscarCircuitosVertice(ArrayList<int[]> listaAdy, int nodoInicial) {

        ArrayList<ArrayList<Arista>> circuitos = new ArrayList<>();
        ArrayList<Arista> circuito = new ArrayList<>();
        int[] ady = listaAdy.get(nodoInicial); // *** CORREGIR: En vez de 0 poner nodoInicial ***

        Stack<Movimiento> pila = new Stack<>(); // Pila para bactracking
        Movimiento actual = new Movimiento(0, 0);
        pila.add(actual);

        // 0: 1, 4
        // 1: 0, 2, 3, 4
        // 2: 1, 3
        // 3: 1, 2, 4
        // 4: 1, 3, 0
        // Mientras haya movimientos posibles
        while (!pila.isEmpty()) {
            if (actual.vMov < ady.length) { // Si tiene mas movs. posibles
                int sigVertice = ady[actual.vMov];
                Movimiento temp = new Movimiento(sigVertice, 0);
                if (movimientoValido(actual, temp, pila, listaAdy)) {
                    circuito.add(new Arista(actual.verticeActual, temp.verticeActual));
                    for (Arista a : circuito) {
                        System.out.print(a.u + ", " + a.v);
                    }
                    System.out.println("");
                    ady = listaAdy.get(sigVertice); // Se obtienen los adyacentes del siguiente.
                    actual = temp;
                    pila.add(actual);
                    // Si el mov. es valido y el nodo siguiente es el Inicial es un circuito, agregar.
                    if (actual.verticeSig == nodoInicial) {
                        circuitos.add(circuito);
                    }
                } else { // Intentar siguiente arista, sin bactracking                    
                    actual.vMov += 1;
                }
            } else {
                // backtracking, no tiene mas aristas/adyacentes
                actual = pila.pop();
                ady = listaAdy.get(actual.verticeActual);
            }
        }

        if (circuitos.isEmpty()) {
            System.out.println("No se encontraron circuitos");
        }

        return circuitos;
    }

    public static Boolean movimientoValido(Movimiento actual, Movimiento temp, Stack<Movimiento> pila, ArrayList<int[]> listaAdy) {
        // Verificar que no se regrese: (1,0) no regresar a (0,1)
        if (temp.verticeActual == pila.peek().verticeActual) {
            return false;
        }
        for (Movimiento m : pila) {
            // Para que no repita aristas, (u,v) == (v,u)
            int u1 = actual.verticeActual;
            int v1 = temp.verticeActual;
            int u2 = m.verticeActual;
            int v2;
            if (m.vMov < listaAdy.get(m.verticeActual).length) {
                v2 = listaAdy.get(m.verticeActual)[m.vMov];
            } else {
                v2 = listaAdy.get(m.verticeActual)[m.vMov - 1];
            }
            if ((u1 == u2 && v1 == v2) || (u1 == v2 && v1 == u2)) {
                return false;
            }
        }
        return true;
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

        System.out.println("\nSubgrafos: ");
        mostrarLista(buscarSubgrafos(grafo, 3));
        System.out.println("\nCircuitos: ");
        buscarCircuitos(grafo, 4);

        System.out.println("Done!");

    }

}
