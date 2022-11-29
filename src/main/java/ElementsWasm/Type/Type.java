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
        boolean res = true;
        Type tp = (Type) obj;
        byte[] paramObj = tp.getParameters();
        byte[] resObj = tp.getResults();
        if(paramObj.length!=parameters.length || resObj.length!=results.length){
            res = false;
        }
        for (int i = 0; i < paramObj.length && res; i++) {
            if(paramObj[i]!=parameters[i]){
                res = false;
            }
        }
        for (int j = 0; j < resObj.length && res; j++) {
            if(resObj[j]!=results[j]){
                res = false;
            }
        }
        return res;
    }

    @Override
    public byte[] getBytesElement() {
        return type;
    }
}
