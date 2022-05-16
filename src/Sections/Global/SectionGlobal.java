package Sections.Global;

public class SectionGlobal implements ISectionGlobal {

    private byte[] bytesSection;
    private byte[] aux;
    public SectionGlobal() {
        bytesSection = new byte[]{0x06,0x01,0x00};
    }
    @Override
    public byte[] getSection(){
        return bytesSection;
    }
    @Override
    public void addGlobal(IGlobal global) {
        byte[] bytesExport = global.getBytesElement();
        bytesSection[1] = (byte)(bytesExport.length + bytesSection[1]);
        bytesSection[2] = (byte)(bytesSection[2]+1);
        aux = bytesSection;
        bytesSection = new byte[aux.length+bytesExport.length ];
        System.arraycopy(aux,0,bytesSection,0,aux.length);
        System.arraycopy(bytesExport,0,bytesSection,aux.length,bytesExport.length);
    }
}
