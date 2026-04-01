package galaxy_animation;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Třída představující vesmírný objekt
 */
public class SpaceObject {
    /**
     * Jméno vesmírného objektu
     */
    public String name;

    /**
     * Typ vesmírného objektu
     */
    public SpaceObjectType spaceObjectType;

    /**
     * Souřadnice vesmírného objektu
     */
    public double[] coordinates = new double[Constants.spaceDimension];

    /**
     * Vektor rychlosti vesmírného objektu
     */
    public double[] velocity = new double[Constants.spaceDimension];

    /**
     * Vektor zrychlení vesmírného objektu
     */
    public double[] acceleration = new double[Constants.spaceDimension];

    /**
     * Hmotnost vesmírného objektu
     */
    public double mass;

    /**
     * Poloměr vesmírného objektu
     */
    public double diameter;

    /**
     * Barva vesmírného objektu
     */
    public Color color;

    /**
     * Tvar vesmírného objektu
     */
    public Shape shape;

    public Queue<DataWithTime<Double>> velocityData = new LinkedList<>();

    public Queue<DataWithTime<Double[]>> positionData = new LinkedList<>();

    /**
     * Konstuktor vesmírného objektu
     *
     * @param name            jméno vesmírného objektu
     * @param spaceObjectType typ vesmírného objektu
     * @param x               X souřadnice vesmírného objektu
     * @param y               Y souřadnice vesmírného objektu
     * @param velocityX       X složka rychlosti vesmírného objektu
     * @param velocityY       Y složka rychlosti vesmírného objektu
     * @param mass            hmotnost vesmírného objektu
     */
    public SpaceObject(String name, SpaceObjectType spaceObjectType, double x, double y, double velocityX, double velocityY, double mass) {
        this.name = name;
        this.spaceObjectType = spaceObjectType;
        this.coordinates[0] = x;
        this.coordinates[1] = y;
        this.velocity[0] = velocityX;
        this.velocity[1] = velocityY;
        this.mass = mass;
        this.diameter = determineDiameter(mass);
        this.color = determineColor(spaceObjectType);
    }

    /**
     * Metoda pro výpočet poloměru vesmírného objektu
     *
     * @param mass hmotnost vesmírného objektu
     * @return poloměr vesmírného objektu
     */
    public static double determineDiameter(double mass) {
        double volume = mass / Constants.spaceObjectsDensity;

        return ((Math.cbrt((3 * volume) / (4 * Math.PI))) * 2);
    }

    /**
     * Metoda pro určení barvy vesmírného objektu
     *
     * @param spaceObjectType typ vesmírného objektu
     * @return color barva vesmírného objektu
     */
    public static Color determineColor(SpaceObjectType spaceObjectType) {
        Color color = null;

        switch (spaceObjectType) {
            case STAR:
                color = Color.ORANGE;
                break;
            case PLANET:
                color = Color.BLUE;
                break;
            case COMET:
                color = Color.RED;
                break;
            case ROCKET:
                color = Color.GREEN;
                break;
            default:
                break;
        }

        return color;
    }

    /**
     * Metoda pro výpočet skaláru rychlosti v km/h.
     *
     * @param velocity rychlost vesmírného objektu
     * @return velocity rychlost v km/h
     */
    public static double computeScalarVelocity(double[] velocity) {
        double scalarVelocity = Math.sqrt((velocity[0] * velocity[0]) + (velocity[1] * velocity[1]));
        return scalarVelocity * Constants.m_s_to_km_s;
    }

    /**
     * Getter pro zjištění jména vesmírného objektu
     *
     * @return name jméno vesmírného objektu
     */
    public String getName() {
        return name;
    }

    /**
     * Getter pro zjištění typu vesmírného objektu
     *
     * @return spaceObjectType typ vesmírného objektu
     */
    public SpaceObjectType getSpaceObjectType() {
        return spaceObjectType;
    }

    /**
     * Getter pro zjištění souřadnic vesmírného objektu
     *
     * @return coordinates souřadnice vesmírného objektu
     */
    public double[] getCoordinates() {
        return coordinates;
    }

    /**
     * Setter pro nastavení souřadnic vesmírného objektu
     *
     * @param coordinates souřadnice vesmírného objektu
     */
    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Getter pro zjištění X souřadnice vesmírného objektu
     *
     * @return X souřadnice vesmírného objektu
     */
    public double getX() {
        return coordinates[0];
    }

    /**
     * Setter pro nastavení X souřadnice vesmírného objektu
     *
     * @param x souřadnice vesmírného objektu
     */
    public void setX(double x) {
        this.coordinates[0] = x;
    }

    /**
     * Getter pro zjištění Y souřadnice vesmírného objektu
     *
     * @return Y souřadnice vesmírného objektu
     */
    public double getY() {
        return coordinates[1];
    }

    /**
     * Setter pro nastavení Y souřadnice vesmírného objektu
     *
     * @param y souřadnice vesmírného objektu
     */
    public void setY(double y) {
        this.coordinates[1] = y;
    }

    /**
     * Getter pro zjištění vektoru rychlosti vesmírného objektu
     *
     * @return velocity vektor rychlosti vesmírného objektu
     */
    public double[] getVelocity() {
        return velocity;
    }

    /**
     * Setter pro nastavení vektoru rychlosti vesmírného objektu
     *
     * @param velocity vektor rychlosti vesmírného objektu
     */
    public void setVelocity(double[] velocity) {
        this.velocity = velocity;
    }

    /**
     * Getter pro zjištění X složky rychlosti vesmírného objektu
     *
     * @return velocityX X složka rychlosti vesmírného objektu
     */
    public double getVelocityX() {
        return velocity[0];
    }

    /**
     * Setter pro nastavení X složky rychlosti vesmírného objektu
     *
     * @param velocityX X složka rychlosti vesmírného objektu
     */
    public void setVelocityX(double velocityX) {
        this.velocity[0] = velocityX;
    }

    /**
     * Getter pro zjištění Y složky rychlosti vesmírného objektu
     *
     * @return velocityY Y složka rychlosti vesmírného objektu
     */
    public double getVelocityY() {
        return velocity[1];
    }

    /**
     * Setter pro nastavení Y složky rychlosti vesmírného objektu
     *
     * @param velocityY Y složka rychlosti vesmírného objektu
     */
    public void setVelocityY(double velocityY) {
        this.velocity[1] = velocityY;
    }

    /**
     * Getter pro zjištění vektoru zrychlení vesmírného objektu
     *
     * @return acceleration vektor zrychlení vesmírného objektu
     */

    public double[] getAcceleration() {
        return acceleration;
    }

    /**
     * Setter pro nastavení vektoru zrychlení vesmírného objektu
     *
     * @param acceleration vektor zrychlení vesmírného objektu
     */
    public void setAcceleration(double[] acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * Getter pro zjištění X složky zrychlení vesmírného objektu
     *
     * @return accelerationX X složka zrychlení vesmírného objektu
     */
    public double getAccelerationX() {
        return acceleration[0];
    }

    /**
     * Setter pro nastavení X složky zrychlení vesmírného objektu
     *
     * @param accelerationX X složka zrychlení vesmírného objektu
     */
    public void setAccelerationX(double accelerationX) {
        this.acceleration[0] = accelerationX;
    }

    /**
     * Getter pro zjištění Y složky zrychlení vesmírného objektu
     *
     * @return accelerationY Y složka zrychlení vesmírného objektu
     */
    public double getAccelerationY() {
        return acceleration[1];
    }

    /**
     * Setter pro nastavení Y složky zrychlení vesmírného objektu
     *
     * @param accelerationY Y složka zrychlení vesmírného objektu
     */
    public void setAccelerationY(double accelerationY) {
        this.acceleration[1] = accelerationY;
    }

    /**
     * Getter pro zjištění hmotnosti vesmírného objektu
     *
     * @return mass hmotnost vesmírného objektu
     */
    public double getMass() {
        return mass;
    }

    /**
     * Setter pro nastavení hmotnosti vesmírného objektu
     *
     * @param mass hmotnost vesmírného objektu
     */
    public void setMass(double mass) {
        this.mass = mass;
    }


    /**
     * Getter pro zjištění průměru vesmírného objektu
     *
     * @return diameter průměru vesmírného objektu
     */
    public double getDiameter() {
        return diameter;
    }

    /**
     * Setter pro nastavení průměru vesmírného objektu
     *
     * @param diameter průměr vesmírného objektu
     */
    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    /**
     * Getter pro zjištění poloměru vesmírného objektu
     *
     * @return diameter poloměru vesmírného objektu
     */
    public double getRadius() {
        return diameter / 2;
    }

    /**
     * Setter pro nastavení barvy vesmírného objektu
     *
     * @param color barva vesmírného objektu
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Getter pro zjištění barvy vesmírného objektu
     *
     * @return color barva vesmírného objektu
     */
    public Color getColor() {
        return color;
    }

    /**
     * Getter pro zjištění tvaru vesmírného objektu
     *
     * @return shape tvar vesmírného objektu
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * Setter pro nastavení tvaru vesmírného objektu
     *
     * @param shape tvar vesmírného objektu
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    /**
     * Getter pro zjištění předchozích rychlostí vesmírného objektu
     *
     * @return velocityData fronta dat s časovám údajem
     */
    public Queue<DataWithTime<Double>> getVelocityData() {
        return velocityData;
    }

    /**
     * Getter pro zjištění předchozích pozic vesmírného objektu
     *
     * @return positionData fronta dat s časovám údajem
     */
    public Queue<DataWithTime<Double[]>> getPositionData() {
        return positionData;
    }
}
