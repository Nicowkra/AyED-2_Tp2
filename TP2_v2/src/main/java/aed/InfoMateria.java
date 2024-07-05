package aed;
//Invariante de representación: No puede haber una secuencia vacía, como mínimo tiene que haber un par.
public class InfoMateria{

    private ParCarreraMateria[] paresCarreraMateria;

    public InfoMateria(ParCarreraMateria[] paresCarreraMateria){
        this.paresCarreraMateria = paresCarreraMateria;
    }   //O(1)

    public ParCarreraMateria[] getParesCarreraMateria() {
        return this.paresCarreraMateria;
    }
}
//O(1)