package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {
    private Nodo primero;
    private Nodo ultimo;
    private int _longitud;

    private class Nodo {
        T valor;
        Nodo sig;
        Nodo ant;
        Nodo(T v) {valor = v;}
    }

    //O(1)
    public ListaEnlazada() {
        this.primero = null;
        this.ultimo = null;
        this._longitud = 0;
    }
    
    //O(n)
    public ListaEnlazada(ListaEnlazada<T> lista) {
        int i = 0;
        while (i<lista.longitud()) {
            T elem = lista.obtener(i);
            agregarAtras(elem);
            i++;
        }
    }

    //O(1)
    public int longitud() {
        return _longitud;
    }
    

    // O(1)
    public void agregarAdelante(T elem) {
        Nodo nuevo = new Nodo(elem);

        //no hay nodos
        if (longitud()==0) {
            ultimo = nuevo;
            primero = nuevo;

        } else {
            nuevo.sig = primero;
            primero.ant = nuevo;
            primero = nuevo;
        }
        _longitud++;
    }

    //O(1) + obtenerNodo O(n/2)
    public void agregarAntesDeNodo(int i,T elem) {

        //i==0: agregar antes del primero = agregar adelante
        if (i==0) {
            agregarAdelante(elem);
            return;
        }

        Nodo nuevo = new Nodo(elem);
        Nodo derecho = obtenerNodo(i);
        Nodo izquierdo = derecho.ant;

        nuevo.sig = derecho;
        nuevo.ant = izquierdo;
        
        izquierdo.sig = nuevo;
        derecho.ant = nuevo;

        _longitud++;
    }

    // O(1)
    public void agregarAtras(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (longitud()==0){
            primero = nuevo;
            ultimo = nuevo;
        } else {
            Nodo ultimoViejo = ultimo;
            ultimoViejo.sig = nuevo;
            nuevo.ant = ultimoViejo;
            ultimo = nuevo;
        }
        _longitud++;
    }

    //O(n/2)
    public Nodo obtenerNodo(int i) {
        Nodo actual;

        //indice a buscar mas cerca del primero que del ultimo
        if (i<((longitud()-1)/2)) {
            actual = primero;
            int j = 0;
            while (j<i) {
                actual = actual.sig;
                j++;
            }
        
        //indice a buscar mas cerca del ultimo que del primero
        } else {
            actual = ultimo;
            int j = longitud()-1;
            while (i<j) {
                actual = actual.ant;
                j--;
            }
        }
        return actual;
    }

    //O(n/2)
    public T obtener(int i) {
        Nodo actual;

        //indice a buscar mas cerca del primero que del ultimo
        if (i<=((longitud()-1)/2)) {
            actual = primero;
            int j = 0;
            while (j<i) {
                actual = actual.sig;
                j++;
            }
        
        //indice a buscar mas cerca del ultimo que del primero
        } else {
            actual = ultimo;
            int j = longitud()-1;
            while (i<j) {
                actual = actual.ant;
                j--;
            }
        }
        return actual.valor;
    }

    //O(n/2)
    public void eliminar(int i) {
        
        //nodo unico
        if (longitud()==1) {
            primero = null;
            ultimo = null;
        } else {

            Nodo eliminar = obtenerNodo(i);

            //no existe nodo izquierdo (eliminar es el primer nodo)
            if (eliminar.ant==null) {
                Nodo derecho = eliminar.sig;
                derecho.ant = null;
                primero = derecho;

            //no existe nodo derecho (eliminar es el ultimo nodo)
            } else if (eliminar.sig!=null) {
                Nodo izquierdo = eliminar.ant;
                izquierdo.sig = null;
                ultimo = izquierdo;

            //el nodo eliminar esta por el medio
            } else {
                Nodo derecho = eliminar.sig;
                Nodo izquierdo = eliminar.ant;
                derecho.ant = izquierdo;
                izquierdo.sig = derecho;
            }
        }
    }

    //O(n/2)
    public void modificarPosicion(int indice, T elem) {
        Nodo nodo = obtenerNodo(indice);
        nodo.valor = elem;
    }

    //O(n)
    public ListaEnlazada<T> copiar() {
        ListaEnlazada<T> copia = new ListaEnlazada<>();
        int i = 0;
        while (i<longitud()) {
            T elem = obtener(i);
            copia.agregarAtras(elem);
            i++;
        }
        return copia;
    }

    
    //O(n)
    @Override
    public String toString() {
        String str = "[";
        int i=0;
        
        while (i<longitud()) {
            if (i==longitud()-1) {
                str = str.concat(obtener(i)+"]");    
            } else {
                str = str.concat(obtener(i)+", ");
            }
            i++;
        }

        return str;
    }

    private class ListaIterador implements Iterador<T> {
        private int iter;
        private Nodo nodoIter = primero;
        ListaIterador() {iter = 0;}

        
        public boolean haySiguiente() {
            return iter<longitud();
        }
        
        public boolean hayAnterior() {
            return iter>0;
        }

        public T siguiente() {
            if (iter!=0){
                nodoIter = nodoIter.sig;
            } else {
                nodoIter = primero;
            }
            iter++;
            return nodoIter.valor;
        }
        

        public T anterior() {
            T valor = nodoIter.valor;
            nodoIter = nodoIter.ant;
            iter--;
            return valor;
        }
    }

    public Iterador<T> iterador() {
        return new ListaIterador();
    }


    
}