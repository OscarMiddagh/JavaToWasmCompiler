package ElementsWasm.Export;
public class Export implements IExport {
    private byte[] export;
    private String nameExport;
    private int kind;
    private int idElement;
    public Export(String nameExport, int kind, int idElement ){
        this.nameExport = nameExport;
        this.kind = kind;
        this.idElement = idElement;
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

    @Override
    public String getNameExport() {
        return nameExport;
    }

    @Override
    public int getKind() {
        return kind;
    }

    @Override
    public int getIdElement() {
        return idElement;
    }
}
