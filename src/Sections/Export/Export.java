package Sections.Export;

import java.nio.charset.StandardCharsets;

public class Export implements IExport {

    private String nameExport;
    private int idElement;

    private int kind;
    private byte[] export;
    public Export(String nameExport, int kind, int idElement ){
        this.nameExport = nameExport;
        this.idElement = idElement;
        this.kind = kind;
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
