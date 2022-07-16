package BinaryWasm.SectionExport;
public class Export implements IExport {
    private byte[] export;
    public Export(String nameExport, int kind, int idElement ){
        int nameLength = nameExport.length();
        export = new byte[nameLength+3];
        export[0] = (byte)(nameLength);
        System.arraycopy(nameExport.getBytes(),0, export,1,nameLength);
        export[nameLength+1] = (byte)(kind);
        export[nameLength+2] = (byte)(idElement);
    }

    @Override
    public byte[] getBytesElement() {
        return export;
    }
}
