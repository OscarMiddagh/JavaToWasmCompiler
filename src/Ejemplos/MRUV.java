package Ejemplos;

class MRUV{
    //Velocidad final sin distancia
    public int vfNd(int vo, int a, int t){
        return vfd(vo,a)+ (a*t);
    }
    public int vfd(int vo, int vf){
        return vo * vf;
    }
    public int vfN(int vo, int a, int t){
        return vo + (a*t);
    }
}