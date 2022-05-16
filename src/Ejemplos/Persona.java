package Ejemplos;

public class Persona{
    private int anioNacimiento;
    private int alturaCm;
    private int anioInscripcion;

    public int getEdadInscripcion(){
        int res;
        res = anioInscripcion- anioNacimiento;
        return res;
    }
    public int getAnioNacimiento(){
        return 80;
    }
    public int getAnioInscripcion(){
        return 1800;
    }
    public int getAltura(){
        return alturaCm;
    }
    public boolean mayorDeEdadInscripcion(){
        int a = 14;
        return (anioInscripcion-anioNacimiento) >= 18;
    }


}