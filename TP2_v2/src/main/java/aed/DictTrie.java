package aed;

import java.util.ArrayList;

public class DictTrie<T> {
    private final static int VALORES_ASCII = 256;
    private Nodo _raiz;
    private int _tamaño;

    private class Nodo {
        private ArrayList<Nodo> _siguientes;
        private T _val;

        public Nodo() {
            this._siguientes = new ArrayList<Nodo>();
            this._val = null;

            for(int i = 0; i < VALORES_ASCII; i++) {
                this._siguientes.add(null);
            }
        }


}

}
