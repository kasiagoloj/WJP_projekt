import java.awt.datatransfer.*;
import javax.swing.*;

/**klasa umożliwiająca kupowanie poprez przeciąganie z panelu buy na mapę
 * implementuje interfejs Transferable*/
public class TransferableIcon implements Transferable {

    /** Typ danych - ikona */
    public static final DataFlavor ICON_FLAVOR = new DataFlavor(Icon.class, "Icon");

    /** Typ danych - źródło ikony. */
    public static final DataFlavor SOURCE_FLAVOR = new DataFlavor(Source.class, "Source");

    private final Icon icon;
    private final Source source;

    /** konstruktor klasy */
    public TransferableIcon(Icon icon, Source source) {
        this.icon = icon;
        this.source = source;
    }

    /** tablica obsługiwanych typów danych */
    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{ICON_FLAVOR, SOURCE_FLAVOR};
    }

    /** czy typ danych jest obługiwany */
    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return ICON_FLAVOR.equals(flavor) || SOURCE_FLAVOR.equals(flavor);
    }

    /** zwraza dane dla podanego typu */
    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (ICON_FLAVOR.equals(flavor)) {
            return icon;
        } else if (SOURCE_FLAVOR.equals(flavor)) {
            return source;
        }
        throw new UnsupportedFlavorException(flavor);
    }

    /** zwraca źródło */
    public Source getSource() {
        return source;
    }
}
