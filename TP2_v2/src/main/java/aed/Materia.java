package aed;

//Invariante de representación: el array _docentes tiene en todas las posiciones un valor mayor o igual a 0, y su suma es la cantidad de docentes que hay en la materia. En el array _alumnado todos sus elementos tienen una longitud acotada y su suma es la cantidad de alumnos que hay en la materia.
//La cantidad de alumnos que hay en una materia está acotada por: 100 estudiante por cada JTP, 20 estudiantes por cada Ay1 y 30 por cada Ay2.
public class Materia {
    private int[] _docentes;
    private DictTrie<String> _alumnado;
    private ParCarreraMateria[] _nombres;                       //nombres de la materia en las distintas carreras presente
    public Carrera[] _carrerasAsociadas;                        //lista de carreras en las que esta presente materia
    
    public Materia(ParCarreraMateria[] nombres){
        this._carrerasAsociadas = new Carrera[nombres.length];  //inicializa con la cantidad de carreras que esta presente
        this._nombres = nombres;                                //guarda los nombres de infoMateria
        this._docentes = new int[4];                            //O(1)
        this._alumnado = new DictTrie<String>();                //O(1)
    }       
    public void inscribir (String libreta){             //O(1) porque libreta está acotado
        this._alumnado.agregar(libreta);
    }
    public ParCarreraMateria[] obtenerNombres(){        //O(1)
        return this._nombres;
    }
    public Carrera[] obtenerCarrerasAsociadas(){        //O(1)
        return this._carrerasAsociadas;
    }
    public int cantidadAlumnos(){                       //O(1)
        return this._alumnado.totalPalabras();
    }
    public void agregarProfesor(){                      //O(1)
        this._docentes[0] = this._docentes[0]+1;
    }
    public void agregarJtp(){                           //O(1)
        this._docentes[1] = this._docentes[1]+1;
    }
    public void agregarAy1(){                           //O(1)
        this._docentes[2] = this._docentes[2]+1;
    }
    public void agregarAy2(){                           //O(1)
        this._docentes[3] = this._docentes[3]+1;
    }
    public int[] obtenerDocentes(){                     //O(1)
        return this._docentes;
    }
    public String[] obtenerAlumnos(){                   //O(1)
        return this._alumnado.palabras();
    }
    public boolean excedeCupo(){                        //O(1)
        int cantAlumnos = cantidadAlumnos();
        boolean excedeProfesores = cantAlumnos > 250 * this._docentes[0];
        boolean excedeJtp = cantAlumnos > 100 * this._docentes[1];
        boolean excedeAy1 = cantAlumnos > 20 * this._docentes[2];
        boolean excedeAy2 = cantAlumnos > 30 * this._docentes[3];

        return excedeProfesores || excedeJtp || excedeAy1 || excedeAy2;
    }
}