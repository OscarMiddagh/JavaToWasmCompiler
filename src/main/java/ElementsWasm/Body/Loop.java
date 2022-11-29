package ElementsWasm.Body;


public class Loop implements IBlock {
    private int ini;
    private int end;
    public Loop(int ini, int end){
        this.ini = ini;
        this.end = end;
    }
    @Override
    public boolean equals(Object obj){
        Block bl = (Block) obj;
        return this.end== bl.getEnd() && this.ini == bl.getIni();
    }

    @Override
    public int getIni() {
        return this.ini;
    }

    @Override
    public int getEnd() {
        return this.end;
    }
}
