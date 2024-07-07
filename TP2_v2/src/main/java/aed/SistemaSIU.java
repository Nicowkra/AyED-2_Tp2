package aed;



//Invariante de representación: Todas las variables cumplen sus invariantes de representación respectivos. 




public class SistemaSIU {
    private InfoMateria[] infoMaterias;
    private DictTrie<Carrera> _carreras;
    private DictTrie<Alumno> _alumnos;
    enum CargoDocente{
        AY2,
        AY1,
        JTP,
        PROF
    }

    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias){
       this.infoMaterias = infoMaterias;
       this._carreras = new DictTrie<Carrera>();                //O(1): Las asignaciones tienen un costo de O(1)
       this._alumnos = new DictTrie<Alumno>();                  //O(1)
       
    
       for(int i= 0;i < infoMaterias.length; i += 1){                           //O(Conjunto de materias) 
            Materia nuevaMateria = new Materia();
            ParCarreraMateria[] nombres = (infoMaterias[i].getParesCarreraMateria());
            for (int j= 0;j < nombres.length; j += 1){                           //O(Nm): Conjunto de todos los nombres de una materia
                String nombreCarrera = nombres[j].carrera;
                String nombreMateria = nombres[j].nombreMateria;
                Carrera actual = (Carrera) this._carreras.obtener(nombreCarrera); // O(|c|): Cantidad de caracteres de la carrera
                if (actual == null){                                              //Este if tiene una complejidad de O(|m|), como se puede ver abajo.
                    Carrera nuevaCarrera = new Carrera();                         //O(1)
                    this._carreras.agregar(nombreCarrera,nuevaCarrera);           //O(|m|): Cantidad de caracteres de la materia 
                    nuevaCarrera.crearMateria(nombreMateria,nuevaMateria);        //O(|m|)
                }else{
                    actual.crearMateria(nombreMateria, nuevaMateria);             //O(|m|)
                }                                   
            }
       }
       for(int i = 0; i < libretasUniversitarias.length; i += 1){   //O(E): La cantidad de estudiantes que hay 
        Alumno alumnoNuevo = new Alumno();
        String libreta = libretasUniversitarias[i];
        this._alumnos.agregar(libreta, alumnoNuevo);                //O(1): Porque LU está acotada
       }	    
    }
//En el primer for se recorren todas las materias. En el segundo, se recorren todos los mombres que tiene una materia. Dentro del segundo for hay un if que tiene una complejidad de O(|m).
//Las complejidades de los dos primeros for, de O(M * Nm * (|c| + |m|)), son iguales a O(M * Nm * |c| + |M| * Nm * |m|))
//Separando, por un lado O(M * Nm * |c|) es igual a O(Σ (c ∈ C) |c| * |Mc| ). Esto es así porque lo que se hace es recorrer todas las materias con todos sus nombres, y esto es lo mismo que recorrer todas las carreras y todas las materias de estas.
//Reagrupando, nos queda O(Σ (c ∈ C) |c| * |Mc|) + |M| * Nm * |m|), pero  |M| * Nm * |m| representa la longitud del conjunto de nombres de la materia m multiplicado por la cantidad de caracteres de la materia, y eso multiplicado por la cantidad total de materias.
//Por lo tanto,  |M| * |Nm| * |m| resulta ser lo mismo que (Σ (m ∈ M) Σ (n ∈ Nm) |n|), que es lo que buscabamos.
//Finalmente, se le suma O(E), que es la complejidad del for final. 
//Así, obtenemos una complejidad de O(Σ (c ∈ C) |c| * |Mc| + Σ (m ∈ M) Σ (n ∈ Nm) |n| + E)

    public void inscribir(String estudiante, String carrera, String materia){     
       Carrera carreraActual = (Carrera) this._carreras.obtener(carrera);         // O(|c|): Cantidad de caracteres de la carrera
       Materia materiaActual = carreraActual.obtenerMateria(materia);             // O(|m|): Cantidad de caracteres de la materia 
       Alumno alumnoActual = (Alumno) this._alumnos.obtener(estudiante);          // O(1): La longitud de la LU de los estudiantes está acotada, entonces es O(1) 
       alumnoActual.agregarMateria();                                             // O(|m|): Cantidad de caracteres de la carrera
       materiaActual.inscribir(estudiante);                                       // O(1)

    }   //Sumando todos los tiempos obtenemos una complejidad de O(|c| + |m| + |1| + |m| + |1|), que es igual a O(|c| + |m|)

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        Carrera carreraActual = (Carrera) this._carreras.obtener(carrera);      // O(|c|): Cantidad de caracteres de la carrera
        Materia materiaActual = carreraActual.obtenerMateria(materia);          // O(|m|): Cantidad de caracteres de la materia
       if (cargo == CargoDocente.PROF){
        materiaActual.agregarProfesor();                                        //O(1): como la cantidad de profesores se almacena en un array acotado, la complejidad es de O(1)
       }
       else if (cargo == CargoDocente.JTP){
        materiaActual.agregarJtp();                                            //Lo mismo ocurre para los jtp, Ay1 y Ay2.
       }
       else if (cargo == CargoDocente.AY1){
        materiaActual.agregarAy1();
       }
       else if (cargo == CargoDocente.AY2){
        materiaActual.agregarAy2();
       }
    }  // Sumando todas las complejidades, nos queda O(|c| +|m|)

    public int[] plantelDocente(String materia, String carrera){
        Carrera carreraActual = (Carrera) this._carreras.obtener(carrera);       // O(|c|): Cantidad de caracteres de la carrera
        Materia materiaActual = carreraActual.obtenerMateria(materia);           // O(|m|): Cantidad de caracteres de la materia
        return materiaActual.obtenerDocentes();                                  // O(1) 
    }   // Sumando todas las complejidades, nos queda O(|c| +|m|)

    private ParCarreraMateria[] buscarEnInfoMaterias(String materia, String carrera) {
        ParCarreraMateria[] info = null;

        for (int i = 0; i < infoMaterias.length; i++) {        //O(M): Cantidad de materias que hay        
            ParCarreraMateria[] pares = (infoMaterias[i].getParesCarreraMateria());  // O(1)

            for (int j = 0; j < pares.length; j++) {           //O(Nm)
                    
                if (pares[j].getCarrera()==carrera &&          //O(1)
                    pares[j].getNombreMateria()==materia) {    //O(1) 

                    info = pares;
                    break;
                }
            }
            if (info != null) {
                break;
            }
        }
        return info;
    }  //Esta auxiliar tiene una complejidad de O(M * Nm).

    public void cerrarMateria(String materia, String carrera){
        Carrera carreraActual;
        Materia materiaActual = null;
        ParCarreraMateria[] info = buscarEnInfoMaterias(materia, carrera);   //O(M * Nm)
        for (int i = 0; i < info.length; i++) {                              
            
            carreraActual = (Carrera) _carreras.obtener(info[i].getCarrera());      //O(|c|)

            //lo hace una sola vez
            if (materiaActual == null) {
                materiaActual = (Materia) carreraActual.obtenerMateria(info[i].getNombreMateria());     //O(|m|)
            }   
            carreraActual.cerrarMateria(info[i].getNombreMateria());    //O(|m|)
        }
        String[] libretas = materiaActual.obtenerAlumnos();             //O(Em): La cantidad de alumnos inscriptos en la materia
        for (int i = 0; i < materiaActual.cantidadAlumnos(); i++) {     //O(Em)
            String LU = libretas[i];                                    //O(1)
            Alumno alumnoActual = (Alumno) _alumnos.obtener(LU);        //O(1): LU está acotada
            alumnoActual.dejarMateria();                                //O(1)
        }
    } //Tiene una complejidad de O(|c| + |m| + Em + M * Nm) pero como vimos antes, M * Nm es igual a  Σ (n ∈ Nm) |n|, por lo que queda la complejidad deseada, de:
      //  O(|c| + |m| + Em + Σ (n ∈ Nm) |n|)
    public int inscriptos(String materia, String carrera){
        Carrera carreraActual = (Carrera) this._carreras.obtener(carrera);   //O(|c|): Cantidad de caracteres de la carrera
        Materia materiaActual = carreraActual.obtenerMateria(materia);       //O(|m|): Cantidad de caracteres de la materia
        return materiaActual.cantidadAlumnos();                              //O(1)
        }  // La complejidad es de O(|c| + |m|)

    public boolean excedeCupo(String materia, String carrera){
        Carrera carreraActual = (Carrera) this._carreras.obtener(carrera);  //O(|c|): Cantidad de caracteres de la carrera
        Materia materiaActual = carreraActual.obtenerMateria(materia);      //O(|m|): Cantidad de caracteres de la materia
        return materiaActual.excedeCupo();                                  //O(1) : son todas comparacione **
    }   // La complejidad es de O(|c| + |m|)


    public String[] carreras(){
        return _carreras.palabras();   //O(Σ|c|): Es la sumatoria de las longitudes (en caracteres) de todas las carreras
    } 

    public String[] materias(String carrera){
        Carrera carreraActual = (Carrera) _carreras.obtener(carrera);   //O(|c|): Cantidad de caracteres de la carrera
        return carreraActual.materiasEnCarreras();                      //O(Σ|Mc|) : La longitud (en caracteres) de todas las materias de una carrera
    } // Sumando, nos queda una complejidad de O(|c| + Σ|Mc|)

    public int materiasInscriptas(String estudiante){   
        Alumno alumnoActual = (Alumno) this._alumnos.obtener(estudiante);  //O(1): Porque LU está acotado
        return alumnoActual.cantidadMaterias();                            //O(1)
    } // Queda una complejidad de O(1) 
}
