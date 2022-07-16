package BinaryWasm.SectionType;

public class Type implements IType {
    private byte nParameters;
    private String signature;
    private byte[] type;
    private int typeLength;
    public Type(String signature){
        this.signature = signature;
        typeLength = signature.length()+1;
        nParameters = (byte)(typeLength-4);
        type = new byte[typeLength];
        type[0] = 0x60;//function
        type[1] = nParameters;
        type[typeLength-2] = 0x01;
        transformParameters();
        transformResult();
    }

    public Type(byte[] parameters, byte[] results){

    }
    @Override
    public int typeLength(){
        return typeLength;
    }
    private void transformResult() {
        type[typeLength-1] = readChar(signature.charAt(signature.length()-1));
    }


    public byte[] getType(){
        return type;
    }
    private void transformParameters(){
        char parameter;
        for (int i = 1; i < signature.length()-2; i++) {
            parameter = signature.charAt(i);
            type[i+1] = readChar(parameter);
        }
    }
    @Override
    public boolean equals(Object obj){
        Type tp = (Type) obj;
        return this.getSignature().equals(tp.getSignature());
    }

    public String getSignature(){
        return signature;
    }

    private byte readChar(char c){
        byte result = 0x00;
        switch (c){
            case 'B': //byte
                break;
            case 'C': //char
                break;
            case 'D': //double
                result = 0x7C;
                break;
            case 'F': //float
                result = 0x7D;
                break;
            case 'I': //int
                result= 0x7F;
                break;
            case 'J': //long
                result = 0x7E;
                break;
            case 'Z': //boolean
                break;
            case '[': //array
                break;
        }
        return result;
    }

    @Override
    public byte[] getBytesElement() {
        return type;
    }
}
