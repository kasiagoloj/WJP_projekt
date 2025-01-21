import java.awt.datatransfer.*;
import javax.swing.*;

public class TransferableIcon implements Transferable {
    public static final DataFlavor ICON_FLAVOR = new DataFlavor(Icon.class, "Icon");
    public static final DataFlavor SOURCE_FLAVOR = new DataFlavor(Source.class, "Source");

    private final Icon icon;
    private final Source source;

    public TransferableIcon(Icon icon, Source source) {
        this.icon = icon;
        this.source = source;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{ICON_FLAVOR, SOURCE_FLAVOR};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return ICON_FLAVOR.equals(flavor) || SOURCE_FLAVOR.equals(flavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (ICON_FLAVOR.equals(flavor)) {
            return icon;
        } else if (SOURCE_FLAVOR.equals(flavor)) {
            return source;
        }
        throw new UnsupportedFlavorException(flavor);
    }

    public Source getSource() {
        return source;
    }
}
