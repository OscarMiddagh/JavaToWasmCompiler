class MRUV{
    //Velocidad final sin distancia
    private int vfNd(int vo, int a, int t){
        int res;
        res = vo + (a*t);
        return res;
    }
    private int vfd(int a, int t){
        int res;
        res = (a*t);
        return res;
    }
}