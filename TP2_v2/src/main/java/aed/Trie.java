package aed;

public class Trie<T>{
    private NodoTrie _raiz;
    private int _altura;
    private int _total;
    
    
    private class NodoTrie {
        //lista enlazada
        ListaEnlazada<NodoTrie> hijos;
        //Iterador<NodoTrie> iterador;


        //por ahora no usamos padre
        //NodoTrie padre;
        char valor;
        T datos;
        
        NodoTrie(char v){
            this.valor = v;
            this.datos = null;
            
            //por ahora no usamos padre
            //this.padre = null;
            this.hijos = new ListaEnlazada<>();
        }
    }

    public Trie() {
        this._raiz = new NodoTrie('\0');
        this._altura = 0;
        this._total = 0;
    }

    public int totalPalabras(){
        return _total;
    }

    public int altura(){
        return _altura;
    }

    public void agregar (String palabra, T data){
        NodoTrie nodoActual = _raiz;
        NodoTrie nuevoNodo;

        for (int i = 0; i < palabra.length(); i++) {
            char caracter = palabra.charAt(i);
            nuevoNodo = new NodoTrie(caracter);

            
            //por ahora no usamos padre
            //nuevoNodo.padre = nodoActual;
            Boolean nodoAgregado = false;
            int cantidadHijosNodoActual = nodoActual.hijos.longitud();

            if (cantidadHijosNodoActual==0) {
                nodoActual.hijos.agregarAdelante(nuevoNodo);
                nodoActual = nuevoNodo;

            } else {

                for (int j = 0; j < cantidadHijosNodoActual; j++) {
                    NodoTrie nodoHijo = nodoActual.hijos.obtener(j);
                    

                    if (caracter<nodoHijo.valor && j==0) {
                        nodoActual.hijos.agregarAdelante(nuevoNodo);
                        nodoAgregado = true;

                    } else if (caracter<nodoHijo.valor) {
                        nodoActual.hijos.agregarAntesDeNodo(j, nuevoNodo);
                        nodoAgregado = true;

                    } else if (caracter==nodoHijo.valor) {
                        nuevoNodo = nodoHijo;
                        nodoAgregado = true;

                    } else if (j==cantidadHijosNodoActual-1) {
                        nodoActual.hijos.agregarAtras(nuevoNodo);
                        nodoAgregado = true;
                    }

                    if (nodoAgregado) {
                        nodoActual = nuevoNodo;
                        break;

                    }
                }
            }
        }
        if (altura()<palabra.length()) {
            _altura = palabra.length();
        }

        //agregar data al nodo del ultimo caracter
        nodoActual.datos = data;
        _total++;
    }

    public T obtener (String palabra){
        NodoTrie nodoActual;
        nodoActual = _raiz;

        for (int i = 0; i < palabra.length(); i++) {
            if (nodoActual.hijos.longitud()>0) {
                for (int j = 0; j < nodoActual.hijos.longitud(); j++) {
                    NodoTrie nodo = nodoActual.hijos.obtener(j);
                    if (nodo.valor==palabra.charAt(i)){
                        nodoActual = nodo;
                        break;
                    }
                }
            } else {
                return null;
            }
        }
        return nodoActual.datos;
    }

    private Object[] hijoAEliminar(NodoTrie raiz, String subPalabra) {
        Object[] data = new Object[4];
        data[0] = raiz;
        data[1] = (int) -1;
        data[2] = (int) subPalabra.length();
        data[3] = (Boolean) false;
        
        NodoTrie nodoActual = raiz;
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

            if (nodoActual.hijos.longitud()>0) {

                for (int j = 0; j < nodoActual.hijos.longitud(); j++) {
                    NodoTrie nodo = nodoActual.hijos.obtener(j);
                    if (i==0) {
                        data[1] = j;
                    }

                    if (nodo.valor==subPalabra.charAt(i)){

                        if (i!=subPalabra.length()-1) {
                            // si 'nodo' no es el nodoObjetivo

                            if (nodo.datos!=null || nodoActual.hijos.longitud()>1) {
                                data[0] = nodo;
                                data[1] = (int) j;
                                data[2] = (int) i + 1;
                                return data;
                            }
                        } else {
                            // si 'nodo' es el nodoObjetivo

                            if (nodo.hijos.longitud()>0) {
                                // contiene uno o mas nodos importantes
                                data[0] = nodoActual;

                            } else {
                                //  ultimo nodo en la rama

                                data[0] = raiz;
                                data[3] = true;
                            }
                            data[1] = (int) j;
                            data[2] = (int) i + 1;
                            return data;
                        }
                        nodoActual = nodo;
                    }
                }
            }
        }
        return data;
    }

    public void eliminar (String palabra){
        NodoTrie nodoActual = _raiz;
        NodoTrie nodoAEliminar;
        Boolean eliminarRama;
        Object[] data;
        int i = 0;
        while (i<palabra.length()) {

            //recorre la rama en busca de nodos importantes que no sean el nodoObjetivo
            data = hijoAEliminar(nodoActual, palabra.substring(i));

            nodoAEliminar = (NodoTrie) data[0];
            int indiceNodoHijo = (int) data[1];
            i += (int) data[2];
            eliminarRama = (Boolean) data[3];
            //data[0]: nodo padre del posible hijo a eliminar
            //data[1]: indice hijo
            //data[2]: caracteres recorridos
            //data[3]: si es posible eliminar la rama subyacente del árbol.


            if (nodoActual == nodoAEliminar) {
                NodoTrie nodoObjetivo = nodoActual.hijos.obtener(indiceNodoHijo);

                
                if (i==palabra.length() && !eliminarRama) {
                    //no es posible eliminar la rama (nodos importantes>1)
                    nodoObjetivo.datos = null;
                } else {
                    //la rama tiene que ser eliminada
                    nodoActual.hijos.eliminar(indiceNodoHijo);
                }
                _total--;
            } else {
                //nodo a eliminar se actualizo
                nodoActual = nodoAEliminar;
            }
        }
    }
}