package ElementsWasm.Global;

import ElementsWasm.Export.IExport;

public class Global implements IGlobal {
    private byte[] global;
    private byte value;
    private IExport export;

    private boolean isPublic;
    private byte typeGlobal;
    private byte constantType;
    private byte mutability;



    public Global(byte type, int mutability, String value){
        this.value = (value != null)?value.getBytes()[0]:0x00;
        this.typeGlobal = type;
        this.constantType = infInstrTypeValue(type);
        this.mutability = (byte)mutability;
        global = new byte[]{type,(byte)mutability,constantType,this.value,0x0b};
        isPublic = false;
    }
    @Override
    public boolean isPublic(){
        return isPublic;
    }

    @Override
    public byte[] getBytesElement() {
        return global;
    }
    @Override
    public void setExport(IExport export) {
        this.export = export;
        isPublic = true;
    }
    @Override
    public IExport getExport() {
        return export;
    }

    @Override
    public byte getValue() {
        return value;
    }

    @Override
    public byte getTypeGlobal() {
        return typeGlobal;
    }

    @Override
    public byte getConstantType() {
        return constantType;
    }

    @Override
    public byte getMutability() {
        return mutability;
    }
    private byte infInstrTypeValue(byte type){
        byte result = 0x00;
        switch (type){
            case 0x7C: //double
                result = 0x44;
                break;
            case 0x7D: //float
                result = 0x43;
                break;
            case 0x7F: //int or boolean
                result= 0x41;
                break;
            case 0x7E: //long
                result = 0x42;
                break;
            default:
                break;
        }
        return result;
    }

}
