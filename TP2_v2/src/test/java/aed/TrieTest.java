package aed;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
public class TrieTest {
    @Test
    void trie(){
        String[] materia1 = {"docentes algo1,introProgra","122223"};
        String[] materia2 = {"docentes algo2,algoritmos","122223"};
        
        Trie carreraDatos = new Trie();
        carreraDatos.agregar("algo1", materia1);
        carreraDatos.agregar("algo2", materia2);
        
        Trie carreraCompu = new Trie();
        carreraCompu.agregar("intro Progra", materia1);
        carreraCompu.agregar("algoritmos", materia2);

        Trie carreras = new Trie();
        carreras.agregar("123/23", carreraDatos);
        carreras.agregar("compu", carreraCompu);
        
        materia1[1] = "0";
        Trie x =  (Trie) carreras.obtener("123/23");
        ;
        assertEquals(carreraDatos, carreras.obtener("123/23"));
        String[] a = (String[]) x.obtener("algo1");
        assertEquals(materia1, a);
        
        //trie.eliminar("casa");
        //assertEquals(2, trie.totalPalabras());
        //assertEquals(null, trie.obtener("ama"));
    }}