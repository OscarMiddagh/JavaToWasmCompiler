package ElementsWasm.Type;

public class Type implements IType {
    private byte[] type;
    private byte[] parameters;
    private byte[] results;

    public Type(byte[] parameters, byte[] results){
        this.parameters = parameters;
        this.results = results;

        type = new byte[parameters.length+results.length+3];
        type[0] = 0x60;
        type[1] = (byte)parameters.length;
        System.arraycopy(parameters,0,type,2,parameters.length);
        type[parameters.length+2] = (byte)results.length;
        System.arraycopy(results,0,type,parameters.length+3,results.length);
    }
    @Override
    public int typeLength(){
        return type.length;
    }

    @Override
    public byte[] getParameters() {
        return parameters;
    }

    @Override
    public byte[] getResults() {
        return results;
    }

    @Override
    public boolean equals(Object obj){
        Type tp = (Type) obj;
        return this.getBytesElement().equals(tp.getBytesElement());
    }

    @Override
    public byte[] getBytesElement() {
        return type;
    }
}
