package UIAbmModel;

/**
 * This class is used to compute the position taking into account zoom, minimum X value,
 * Maximum X value, minimum Y value, maximum Y value
 */
public class DrawingPositionCalculator {

    /**
     * the zoom of the displayed pane
     */
    private static double zoom = 1;

    /**
     * the minimum value for the Node for the X value
     */
    private static double minX = 0;

    /**
     * the maximum value for the Node for the Y value
     */
    private static double maxY = 100;

    /**
     * the minimum value for the Node for the Y value
     */
    private static double minY = 0;

    /**
     * the maximum value for the Node for the X value
     */
    private static double maxX = 100;

    /**
     * translation function for the X regarding the zoom and the minimum X value
     * @param x the theoretical X
     * @return the translated value of X
     */
    public static double getX(double x){
        return (x - minX) / zoom + 20;
    }

    /**
     * translation function for the Y regarding the zoom and the maximum Y value
     * @param y the theoretical y
     * @return the translated value of y
     */
    public static double getY(double y){
        return ( maxY - y ) / zoom + 20;
    }

    /**
     * minimum X value
     * @param minX minimum X value
     */
    public static void setMinX(double minX) {
        DrawingPositionCalculator.minX = minX;
    }

    /**
     * maximum Y value
     * @param maxY maximum Y value
     */
    public static void setMaxY(double maxY) {
        DrawingPositionCalculator.maxY = maxY;
    }

    /**
     * zoom setter
     * @param zoom the new zoom value
     */
    public static void setZoom(double zoom) {
        DrawingPositionCalculator.zoom = zoom;
    }

    /**
     * minimum Y value
     * @param minY minimum Y value
     */
    public static void setMinY(double minY) {
        DrawingPositionCalculator.minY = minY;
    }

    /**
     * zoom getter
     * @return zoom value
     */
    public static double getZoom() {
        return zoom;
    }

    /**
     * scroll pane X size to resize
     * @return scroll pane X size
     */
    public static double getPaneXSize(){
        return (maxX - minX) / zoom + 40;
    }

    /**
     * scroll pane Y size to resize
     * @return scroll pane Y size
     */
    public static double getPaneYSize(){
        return (maxY - minY) / zoom + 40;
    }

    /**
     * maximum X value
     * @param maxX maximum X value
     */
    public static void setMaxX(double maxX) {
        DrawingPositionCalculator.maxX = maxX;
    }
}
