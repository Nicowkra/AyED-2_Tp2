package aed;

//Invariante de representación: No se llega por dos claves al mismo nodo, los nodos tienen un solo padre salvo la raíz, y es un árbol.
//No hay nodos que no tengan significado ni hijos.


public class DictTrie<T> {
    private Nodo _raiz;
    private int _altura;
    private int _total;

    private static class Nodo {
        private int cantHijos;
        private Nodo[] hijos;
        private Object datos;
        private Nodo padre;

        public Nodo() {
            this.padre = null;
            this.datos = null;
            this.cantHijos = 0;
            this.hijos = new Nodo[256];

        }

        //cantidad de nodos no nulos en Nodo[] hijos
        public int longitud() {
            return cantHijos;
        }
    }

    //O(1)
    public DictTrie() {
        this._raiz = new Nodo();
        this._altura = 0;
        this._total = 0;
    }

    //O(1)
    public int totalPalabras(){
        return this._total;
    }

    //O(1)
    public int altura(){
        return this._altura;
    }

    //O(1)
    public void agregar(String palabra) {
        // como dato agrega un string vacio para indicar final de palabra
        agregar(palabra, (T) "");
    }

    //O(1)
    public void agregar(String palabra, T data) {
        Nodo actual = this._raiz;
        for (char ch : palabra.toCharArray()) {
            int ind = (int) ch;
            if (actual.hijos[ind] == null) {
                //agrega caracter
                actual.hijos[ind] = new Nodo();
                actual.hijos[ind].padre = actual;
                actual.cantHijos++;
            }

            actual = actual.hijos[ind];
        }

        if (altura()<palabra.length()) {
            this._altura = palabra.length();
        }
        
        //agregar data al nodo del ultimo caracter
        actual.datos = data;
        this._total++;
    }
    
    //O(1)
    public Object obtener (String palabra) {
        Nodo actual = this._raiz;
        for (char ch : palabra.toCharArray()) {
          int ind = (int) ch;
          if (actual.hijos[ind] == null) {
            //null si no lo encuentra
            return null;
          }
          actual = actual.hijos[ind];
        }
        return actual.datos;
    }

    private Object[] auxiliarEleminar(Nodo raiz, String subPalabra) {
        Object[] data = new Object[4];
        data[0] = raiz;
        data[1] = (int) -1;
        data[2] = (int) subPalabra.length();
        data[3] = (Boolean) false;
        
        Nodo nodoActual = raiz;
        //data[0]: nodo padre del hijo a eliminar
        //data[1]: indice hijo
        //data[2]: caracteres recorridos
        //data[3]: si es posible eliminar la rama subyacente del árbol.
                //true: si contiene solo un nodo importante (nodoObjetivo)
                //false: si contiene mas de un nodo importante 

        //nodo es importante si: 
        //contiene un final de palabra distinto a la que sera eliminada
        //contiene mas de un hijo

        for (int i = 0; i < subPalabra.length(); i++) {

            if (nodoActual.longitud()>0) {
                char ch = subPalabra.charAt(i);
                int ind = (int) ch;

                Nodo nodo = nodoActual.hijos[ind];

                if (i==0) {
                    data[1] = ind;
                }

                if (i!=subPalabra.length()-1) {
                    // si 'nodo' no es el nodoObjetivo

                    if (nodo.datos!=null || nodo.longitud()>1) {
                        data[0] = nodo;
                        data[1] = (int) ind;
                        data[2] = (int) i + 1;
                        return data;
                    }
                } else {
                    // si 'nodo' es el nodoObjetivo

                    if (nodo.longitud()>0) {
                        // contiene uno o mas nodos importantes
                        data[0] = nodoActual;
                        data[1] = (int) ind;
                        data[2] = (int) i;

                    } else {
                        //  ultimo nodo en la rama

                        data[0] = raiz;
                        data[2] = (int) i;
                        data[3] = true;
                    }
                    return data;
                }
                nodoActual = nodo;
            }
        }
        return data;
    }

    //O(1)
    public void eliminar(String palabra) {
        Nodo nodoActual = this._raiz;
        Nodo nodoAEliminar;
        Boolean eliminarRama;
        Object[] data;
        Boolean palabraEliminada = false;
        int i = 0;
        while (!palabraEliminada) {

            //data[0]: nodo padre del hijo a eliminar
            //data[1]: indice hijo
            //data[2]: caracteres recorridos
            //data[3]: si es posible eliminar la rama subyacente del árbol.
            data = auxiliarEleminar(nodoActual, palabra.substring(i));

            nodoAEliminar = (Nodo) data[0];
            int indiceNodoHijo = (int) data[1];
            i += (int) data[2];
            eliminarRama = (Boolean) data[3];

            if (nodoActual == nodoAEliminar) {
                
                Nodo nodoObjetivo = nodoActual.hijos[indiceNodoHijo];

                if (i==palabra.length()-1 && !eliminarRama) {
                    //no es posible eliminar la rama (nodos importantes>1)
                    nodoObjetivo.datos = null;
                } else {
                    //la rama tiene que ser eliminada
                    nodoActual.hijos[indiceNodoHijo] = null;
                    nodoActual.cantHijos--;
                }
                palabraEliminada = true;
                this._total--;
            } else {
                //nodo a eliminar se actualizo
                nodoActual = nodoAEliminar;
            }
        }
    }

    //O(n)
    public String[] palabras() {
        String[] arr = new String[_total];
        Nodo nodoActual = _raiz;
        int ultimoCaracter = -1;
        int ultimoHijo = 0;
        String prefijo = "";
        int i = 0;

        //hasta tener la totalidad de las palabras
        while (i < _total) {

            //recorre los nodos de la izquierda primero y lo saltea si ya lo recorrio
            while (nodoActual.longitud()>0) {
                for (int j = 0; j < nodoActual.hijos.length; j++) {
                    Nodo nodo = nodoActual.hijos[j];
                    if (nodo != null && j>ultimoCaracter) {
                        ultimoCaracter = -1;
                        nodoActual = nodo;
                        prefijo = prefijo + (char) j;
                        if (nodo.datos != null) {
                            arr[i] = prefijo;
                            i++;
                        }
                        break;
                        
                    }
                    
                }
            }

            //vuelve a nodo de arriba hasta que tenga mas de un hijo
            if (i < _total) {
                int indChar = prefijo.length()-1;
                ultimoCaracter = (int) prefijo.charAt(indChar);
                prefijo = prefijo.substring(0, indChar);
                nodoActual = nodoActual.padre;
                ultimoHijo = obtenerUltimoHijo(nodoActual);
                
                while (ultimoHijo==ultimoCaracter) {
                    indChar = prefijo.length()-1;
                    ultimoCaracter = (int) prefijo.charAt(indChar);
                    prefijo = prefijo.substring(0, indChar);
                    nodoActual = nodoActual.padre;
                    ultimoHijo = obtenerUltimoHijo(nodoActual);
                }
            }
        }
        return arr;
    }

    private int obtenerUltimoHijo(Nodo raiz) {
        int hijosNoNulos = raiz.longitud()-1;
        int actualHijoNoNulo = 0;
        for (int i = 0; i < raiz.hijos.length; i++) {
            if (raiz.hijos[i] != null) {
                if (hijosNoNulos==actualHijoNoNulo) {
                    return i;
                }
                actualHijoNoNulo++;
            }
        }
        return -2;
    }
}
