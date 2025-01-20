import java.awt.datatransfer.*;
import javax.swing.*;

public class TransferableIcon implements Transferable{
    public static final DataFlavor ICON_FLAVOR = new DataFlavor(Icon.class, "Icon");
    private final Icon icon;

    public TransferableIcon(Icon icon) {
        this.icon = icon;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{ICON_FLAVOR};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return ICON_FLAVOR.equals(flavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (isDataFlavorSupported(flavor)) {
            return icon;
        }
        return null;
    }
}
