package galaxy_animation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * Třída představující animační panel - zobrazovací prostor simulace
 */
public class AnimationPanel extends JPanel {
    /**
     * Globální proměnná simulačního času
     */
    public double time;

    /**
     * Globální proměnná animace
     */
    public Animation animation;

    /**
     * Globální proměnná změny měřítka zobrazení
     */
    public double scale;

    /**
     * Globální proměnná vektoru posunu
     */
    public double[] translate = new double[Constants.spaceDimension];

    /**
     * Konstuktor animačního panelu
     *
     * @param animation instance Animace
     */
    public AnimationPanel(Animation animation) {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(Constants.animationPanelWidth, Constants.animationPanelHeight));
        this.animation = animation;
    }

    /**
     * Metoda pro vykreslování vesmírných objektů
     *
     * @param g grafický kontext
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        AffineTransform originTransform = g2.getTransform();

        generateScaleAndTranslate();

        g2.translate(translate[0], translate[1]);

        for (int i = 0; i < animation.getSpaceObjects().size(); i++) {
            drawTrajectory(g2, animation.getSpaceObjects().get(i));
        }

        for (int i = 0; i < animation.getSpaceObjects().size(); i++) {
            switch (animation.getSpaceObjects().get(i).getSpaceObjectType()) {
                case STAR:
                case PLANET:
                case COMET:
                    drawCircularSpaceObject(g2, animation.getSpaceObjects().get(i));
                    break;
                case ROCKET:
                    drawRocket(g2, animation.getSpaceObjects().get(i));
                default:
                    break;
            }
        }

        g2.setTransform(originTransform);

        printSimulationTime(g2);

        g2.setTransform(originTransform);

    }

    /**
     * Metoda pro výpočet zvětšení a posunu vesmírných objektů
     */
    public void generateScaleAndTranslate() {
        double temp, newDiameter, tempScale, tempCorrectionScale;

        double centerX = this.getWidth() / 2.0;
        double centerY = this.getHeight() / 2.0;

        double min_X = animation.getSpaceObjects().get(0).getX() - animation.getSpaceObjects().get(0).getRadius();
        double max_X = animation.getSpaceObjects().get(0).getX() + animation.getSpaceObjects().get(0).getRadius();
        double min_Y = animation.getSpaceObjects().get(0).getY() - animation.getSpaceObjects().get(0).getRadius();
        double max_Y = animation.getSpaceObjects().get(0).getY() + animation.getSpaceObjects().get(0).getRadius();

        SpaceObject[] borderSpaceObjects = new SpaceObject[Constants.numBorderSpaceObjects];

        for (int i = 0; i < borderSpaceObjects.length; i++) {
            borderSpaceObjects[i] = animation.getSpaceObjects().get(0);
        }

        /*Vyhledání krajních objketů simulace*/
        for (int i = 1; i < animation.getSpaceObjects().size(); i++) {
            temp = animation.getSpaceObjects().get(i).getX() - animation.getSpaceObjects().get(i).getRadius();

            if (temp < min_X) {
                min_X = temp;
                borderSpaceObjects[0] = animation.getSpaceObjects().get(i);
            }

            temp = animation.getSpaceObjects().get(i).getX() + animation.getSpaceObjects().get(i).getRadius();

            if (temp > max_X) {
                max_X = temp;
                borderSpaceObjects[1] = animation.getSpaceObjects().get(i);
            }

            temp = animation.getSpaceObjects().get(i).getY() - animation.getSpaceObjects().get(i).getRadius();

            if (temp < min_Y) {
                min_Y = temp;
                borderSpaceObjects[2] = animation.getSpaceObjects().get(i);
            }

            temp = animation.getSpaceObjects().get(i).getY() + animation.getSpaceObjects().get(i).getRadius();

            if (temp > max_Y) {
                max_Y = temp;
                borderSpaceObjects[3] = animation.getSpaceObjects().get(i);
            }
        }

        Area boundingBox = new Area();

        /*zjištění měřítka simulace*/
        for (int i = 0; i < borderSpaceObjects.length; i++) {
            Shape spaceObject = createSpaceObject(borderSpaceObjects[i].getX(), borderSpaceObjects[i].getY(),
                    borderSpaceObjects[i].getDiameter(), borderSpaceObjects[i].getDiameter());

            Area spaceObjectArea = new Area(spaceObject);

            boundingBox.add(spaceObjectArea);
        }

        tempScale = getScale(boundingBox.getBounds2D().getWidth(), boundingBox.getBounds2D().getHeight());

        boundingBox.reset();

        /*úprava vykreslení vesmírných objektů pomocí měřítka*/
        for (int i = 0; i < borderSpaceObjects.length; i++) {
            newDiameter = Math.max(borderSpaceObjects[i].getDiameter() * tempScale, Constants.minimumDiameterOfSpaceObject);

            Shape spaceObject = createSpaceObject(borderSpaceObjects[i].getX() * tempScale,
                    borderSpaceObjects[i].getY() * tempScale, newDiameter, newDiameter);
            borderSpaceObjects[i].setShape(spaceObject);

            Area spaceObjectArea = new Area(spaceObject);

            boundingBox.add(spaceObjectArea);
        }

        tempCorrectionScale = getScale(boundingBox.getBounds2D().getWidth(), boundingBox.getBounds2D().getHeight());

        this.scale = tempScale * tempCorrectionScale;

        boundingBox.reset();

        /*případná korekce měřítka vykreslení vesmírných objektů*/
        if (tempCorrectionScale != 0) {
            for (int i = 0; i < borderSpaceObjects.length; i++) {
                Shape spaceObject;

                if (borderSpaceObjects[i].getShape().getBounds2D().getWidth() == Constants.minimumDiameterOfSpaceObject) {
                    spaceObject = createSpaceObject(borderSpaceObjects[i].getX() * scale,
                            borderSpaceObjects[i].getY() * scale,
                            Constants.minimumDiameterOfSpaceObject, Constants.minimumDiameterOfSpaceObject);
                } else {
                    spaceObject = createSpaceObject(borderSpaceObjects[i].getX() * scale,
                            borderSpaceObjects[i].getY() * scale,
                            borderSpaceObjects[i].getDiameter() * scale,
                            borderSpaceObjects[i].getDiameter() * scale);
                }

                Area spaceObjectArea = new Area(spaceObject);

                boundingBox.add(spaceObjectArea);
            }
        }

        tempCorrectionScale = getScale(boundingBox.getBounds2D().getWidth(), boundingBox.getBounds2D().getHeight());

        translate[0] = getNewCenterTranslate(boundingBox.getBounds2D().getCenterX(), centerX, tempCorrectionScale);
        translate[1] = getNewCenterTranslate(boundingBox.getBounds2D().getCenterY(), centerY, tempCorrectionScale);
    }

    /**
     * Metoda pro vykreslení Planety, Slunce a Komety
     *
     * @param g2          grafický kontext
     * @param spaceObject vesmírný objekt
     */
    public void drawCircularSpaceObject(Graphics2D g2, SpaceObject spaceObject) {
        double newDiameter = Math.max(spaceObject.getDiameter() * scale, Constants.minimumDiameterOfSpaceObject);

        Ellipse2D circularSpaceObject = new Ellipse2D.Double(centerOfShape(spaceObject.getX() * scale, newDiameter),
                centerOfShape(spaceObject.getY() * scale, newDiameter), newDiameter, newDiameter);

        spaceObject.setShape(circularSpaceObject);

        g2.setColor(spaceObject.getColor());
        g2.fill(circularSpaceObject);
    }

    /**
     * Metoda pro vykreslení Rakety
     *
     * @param g2          grafický kontext
     * @param spaceObject vesmírný objekt - raketa
     */
    public void drawRocket(Graphics2D g2, SpaceObject spaceObject) {

    }

    /**
     * Metoda pro vykreslení trajektorie vesmírného objektu
     *
     * @param g2          grafický kontext
     * @param spaceObject vesmírný objekt
     */
    public void drawTrajectory(Graphics2D g2, SpaceObject spaceObject) {
        double newDiameter = Math.max(spaceObject.getDiameter() * scale, Constants.minimumDiameterOfSpaceObject);

        double alpha = newDiameter / (spaceObject.getPositionData().size() + 1);

        newDiameter -= alpha * spaceObject.getPositionData().size();

        for (DataWithTime<Double[]> data : spaceObject.getPositionData()) {
            Ellipse2D circularSpaceObject = new Ellipse2D.Double(centerOfShape(data.getData()[0] * scale, newDiameter),
                    centerOfShape(data.getData()[1] * scale, newDiameter), newDiameter, newDiameter);

            spaceObject.setShape(circularSpaceObject);

            g2.setColor(Constants.trajectorySpaceObjectColor);
            g2.fill(circularSpaceObject);

            newDiameter += alpha;
        }
    }

    /**
     * Metoda pro vytvoření tvaru vesmírného objektu
     *
     * @param x      X souřadnice vesmírného objektu
     * @param y      Y souřadnice vesmírného objektu
     * @param width  šířka vesmírného objektu
     * @param height výška vesmírného objektu
     * @return Ellipse2D ellipsa
     */
    public Shape createSpaceObject(double x, double y, double width, double height) {
        return new Ellipse2D.Double(centerOfShape(x, width), centerOfShape(y, height), width, height);
    }

    /**
     * Metoda pro výpis simulačního času
     *
     * @param g2 grafický kontext
     */
    public void printSimulationTime(Graphics2D g2) {
        int width;

        g2.setColor(Color.BLACK);

        g2.setFont(new Font("Arial", Font.BOLD, Constants.fontSizeSimulationTime));

        String text = Constants.textSimulationTime.concat(String.format("%G", time).concat(" s"));

        width = g2.getFontMetrics().stringWidth(text);

        g2.drawString(text, this.getWidth() - width, Constants.fontSizeSimulationTime);
    }

    /**
     * Metoda pro kontrolu zdali uživatel klikl na některý vesmírný objekt
     *
     * @param x X souřadnice kliknutí
     * @param y Y souřadnice kliknutí
     * @return index v poli vesmíných objektů
     * -1 pokud uživatel neklikl na některý vesmírný objekt
     */
    public int clickOnSpaceObject(int x, int y) {
        for (int i = 0; i < animation.getSpaceObjects().size(); i++) {
            if (animation.getSpaceObjects().get(i).getShape().contains((x - translate[0]), (y - translate[1]))) {
                return i;
            }
        }

        return Constants.nullNumberOutput;
    }

    /**
     * Metoda vyhodnocující kolizi vesmírných objektů
     *
     * @param firstObject  první vesmírný objekt
     * @param secondObject druhý vesmírný objekt
     * @return pole kolidujících vesmírných objektů
     */
    public int[] shapeCollision(int firstObject, int secondObject) {
        int[] colliedObjects = new int[2];

        if (animation.getSpaceObjects().get(firstObject).getShape().intersects(animation.getSpaceObjects().get(secondObject).getShape().getBounds2D())) {
            if (animation.getSpaceObjects().get(firstObject).getMass() >= animation.getSpaceObjects().get(secondObject).getMass()) {
                colliedObjects[0] = firstObject;
                colliedObjects[1] = secondObject;
            } else {
                colliedObjects[0] = secondObject;
                colliedObjects[1] = firstObject;
            }

            return colliedObjects;
        } else {
            return null;
        }
    }


    /**
     * Metoda pro určení středu vesmírného objektu
     *
     * @param position  souřadnice vesmírného objektu
     * @param dimension rozměry vesmírného objetku
     * @return souřadnice středu vesmírného objektu
     */
    private double centerOfShape(double position, double dimension) {
        return (position - (dimension / 2.0));
    }

    /**
     * Metoda pro určení poměru zvětšení vesmírných objektů
     *
     * @param objectWidth  šířka oblasti vesmírných objektů
     * @param objectHeight výška oblasti vesmírných objektů
     * @return scale hodnota zvětšení vesmírných objektů
     */
    private double getScale(double objectWidth, double objectHeight) {
        double scaleX = this.getWidth() / objectWidth;
        double scaleY = this.getHeight() / objectHeight;

        return Math.min(scaleX, scaleY);
    }

    /**
     * Metoda pro určení posunu grafického kontextu tak aby se střed AnimationPanelu a oblasti vesmírných objektů překrývali
     *
     * @param start_position střed oblasti vesmírných objektů
     * @param end_position   střed AnimationPanelu
     * @param scale          poměr zvětšení vesmírných objektů
     * @return translate vzdálenost posunu oblasti vesmírných objektů
     */
    private double getNewCenterTranslate(double start_position, double end_position, double scale) {
        return ((end_position - (start_position * scale)) / scale);
    }

    /**
     * Setter pro nastavení simulačního času
     *
     * @param time simulační čas
     */
    public void setTime(double time) {
        this.time = time;
    }
}
