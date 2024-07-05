package aed;

//Invariante de representación: el array _docentes tiene en todas las posiciones un valor mayor o igual a 0, y su suma es la cantidad de docentes que hay en la materia. En el array _alumnado todos sus elementos tienen una longitud acotada y su suma es la cantidad de alumnos que hay en la materia.
//La cantidad de alumnos que hay en una materia está acotada por: 100 estudiante por cada JTP, 20 estudiantes por cada Ay1 y 30 por cada Ay2.
public class Materia {
    private int[] _docentes;
    private DictTrie<String> _alumnado;
    
    public Materia(){
        this._docentes = new int[4];
        this._alumnado = new DictTrie<String>();
    }
    public void inscribir (String libreta){       //O(1) porque libreta está acotado
        this._alumnado.agregar(libreta);
    }
    public int cantidadAlumnos(){                 //O(1)
        return this._alumnado.size();
    }
    public void agregarProfesor(){                //O(1)
        this._docentes[0] = this._docentes[0]+1;
    }
    public void agregarJtp(){                     //O(1)
        this._docentes[1] = this._docentes[1]+1;
    }
    public void agregarAy1(){                     //O(1)
        this._docentes[2] = this._docentes[2]+1;
    }
    public void agregarAy2(){                     //O(1)
        this._docentes[3] = this._docentes[3]+1;
    }
    public int[] obtenerDocentes(){               //O(1)
        return this._docentes;
    }
    public ArrayList<String> obtenerAlumnos(){    //O(1)
        return this._alumnado;
    }
    public boolean excedeCupo(){                  //O(1)
        int cantAlumnos = this._alumnado.size();
        boolean excedeProfesores = cantAlumnos > 250 * this._docentes[0];
        boolean excedeJtp = cantAlumnos > 100 * this._docentes[1];
        boolean excedeAy1 = cantAlumnos > 20 * this._docentes[2];
        boolean excedeAy2 = cantAlumnos > 30 * this._docentes[3];

        return excedeProfesores || excedeJtp || excedeAy1 || excedeAy2;
    }
}