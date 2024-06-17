package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {
    private Nodo primero;

    private class Nodo {
        T valor;
        Nodo sig;
        Nodo ant;

        Nodo(T v) {valor = v;}

    }

    public ListaEnlazada() {
        primero = null;
    }
    public ListaEnlazada(ListaEnlazada<T> lista) {
        int i = 0;
        while (i<lista.longitud()) {
            T elem = lista.obtener(i);
            agregarAtras(elem);
            i++;
        }
    }

    public int longitud() {
        int len = 0;
        Nodo actual = primero;
        while (actual!=null) {
            actual = actual.sig;
            len++;
        }
        return len;
    }
    

    public void agregarAdelante(T elem) {
        Nodo nuevo = new Nodo(elem);
        nuevo.sig = primero;
        primero = nuevo;

    }


        

    public void agregarAtras(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (primero==null){
            primero = nuevo;
        } else {
            Nodo actual = primero;
            while(actual.sig!=null){
                actual = actual.sig;
            }
            
            actual.sig = nuevo;
            nuevo.ant = actual;
        }
    }

    public T obtener(int i) {
        int j = 0;
        Nodo actual = primero;
        while (j<i) {
            actual = actual.sig;
            j++;
        }
        return actual.valor;
    }

    public void eliminar(int i) {
        int j = 0;
        Nodo previo = primero.ant;
        Nodo actual = primero;
        
        
        if (longitud()==1) {
            primero = null;
        } else {
            while (j<i) {
                previo = actual;
                actual = actual.sig;
                j++;
            }
            if (i == 0) {
                primero = actual.sig;
                primero.ant = null;
            } else if (i < longitud()-1){
                previo.sig = actual.sig;
                actual.sig.ant = previo;
    
            } else {
                previo.sig = actual.sig;
            }
        }
    }

    public void modificarPosicion(int indice, T elem) {
        int j = 0;
        Nodo actual = primero;
        while (j<indice) {
            actual = actual.sig;
            j++;
        }
        actual.valor = elem;
    }

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
