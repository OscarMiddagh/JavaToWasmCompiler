package Sections.Export;

public class SectionExport implements ISectionExport {

    private byte[] bytesSection;
    private byte[] aux;
    public SectionExport() {
        bytesSection = new byte[]{0x07,0x01,0x00};
    }
    @Override
    public byte[] getSection(){
        return bytesSection;
    }



    @Override
    public void addExport(IExport export) {
        byte[] bytesExport = export.getBytesElement();
        bytesSection[1] = (byte)(bytesExport.length + bytesSection[1]);
        bytesSection[2] = (byte)(bytesSection[2]+1);
        aux = bytesSection;
        bytesSection = new byte[aux.length+bytesExport.length ];
        System.arraycopy(aux,0,bytesSection,0,aux.length);
        System.arraycopy(bytesExport,0,bytesSection,aux.length,bytesExport.length);
    }

    @Override
    public boolean containsElement(IExport export) {
        return false;
    }

    @Override
    public int indexOfElement(IExport export) {
        return 0;
    }
}
