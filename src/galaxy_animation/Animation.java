package galaxy_animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;

/**
 * Třída realizující aktualizaci a úpravu dat simulace.
 */
public class Animation {

    /**
     * Globální proměnná hlavního okna
     */
    private final Window mainWindow;

    /**
     * Globální proměnná informačního okna
     */
    private Window informationWindow;

    /**
     * Globální proměnná hlavního panel okna aplikace
     */
    private final JPanel mainPanel;

    /**
     * Globální proměnná animačního panelu
     */
    private final AnimationPanel animationPanel;

    private final MenuPanel menuPanel;
    /**
     * Globální proměnná informačního panelu
     */
    private InformationPanel informationPanel;

    /**
     * Globální proměnná určující gravitační konstantu
     */
    public double gravityConstant;

    /**
     * Globální proměnná časového kroku simulace
     */
    public double timeConstant;

    /**
     * Globální proměnná pole vesmírných objektů
     */
    public ArrayList<SpaceObject> spaceObjects = new ArrayList<>();

    /**
     * Globální proměnná zásobníku již odstraněných vesmírných objektů
     */
    public Stack<SpaceObject> deleteSpaceObjects = new Stack<>();

    private Timer timer;

    /**
     * Globální proměnná určující čas počátku simulace
     */
    public long startSimulationTime;

    /**
     * Globální proměnná určující čas simulace v s
     */
    public double simulationTime;

    /**
     * Globální proměnná určující uběhlý reálný čas v ms
     */
    public long realTimeInMs;

    /**
     * Globální proměnná určující uběhlý reálný čas v s
     */
    public double realTime = 0;

    /**
     * Globální proměnné určující čas od poslední aktualizace dat simulace
     */
    public long startUpdateSystem = 0;
    public long endUpdateSystem = 0;

    /**
     * Globální proměnné zajišťující pozastavení animace
     */
    private long stopTime, restartTime;
    private boolean startStopState = false;

    /**
     * Konstruktor animace
     *
     * @param gravityConstant gravitační konstanta
     * @param timeConstant    časový krok simulace
     */
    public Animation(double gravityConstant, double timeConstant) {
        this.gravityConstant = gravityConstant;
        this.timeConstant = timeConstant;

        this.mainPanel = new JPanel();

        this.animationPanel = new AnimationPanel(this);

        mainPanel.setPreferredSize(new Dimension(Constants.animationPanelWidth, Constants.animationPanelHeight + Constants.menuPanelHeight));
        mainPanel.setLayout(new BorderLayout());

        this.menuPanel = new MenuPanel(this);

        mainPanel.add(menuPanel, BorderLayout.SOUTH);
        mainPanel.add(animationPanel, BorderLayout.CENTER);

        this.mainWindow = new Window(Constants.applicationName, Constants.windowWidth, Constants.windowHeight, mainPanel);

        this.mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Metoda pro běh simulace.
     */
    public void runAnimation() {
        startSimulationTime = System.currentTimeMillis();

        /*timer pro aktualizaci simulace*/
        timer = new Timer(Constants.refreshTime, new ActionListener() {
            double velocity_time;
            boolean firstRun = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                long updateTime;

                if (firstRun) {
                    startUpdateSystem = System.currentTimeMillis();
                }

                /*uložení pozice vesmírných objektů*/
                for (int i = 0; i < spaceObjects.size(); i++) {
                    Double[] temp = new Double[Constants.spaceDimension];
                    temp[0] = spaceObjects.get(i).getX();
                    temp[1] = spaceObjects.get(i).getY();

                    spaceObjects.get(i).getPositionData().add(new DataWithTime<>(temp, realTime));

                    if (Objects.requireNonNull(spaceObjects.get(i).getPositionData().peek()).getTime() + 1 < realTime && firstRun) {
                        spaceObjects.get(i).getPositionData().remove();
                    }
                }

                /*uložení rychjlosti vesmírných objektů, každou sekundu reálného času*/
                if (velocity_time + 1 < realTime || !firstRun) {
                    for (int i = 0; i < spaceObjects.size(); i++) {
                        spaceObjects.get(i).getVelocityData().add(new DataWithTime<>(SpaceObject.computeScalarVelocity(spaceObjects.get(i).getVelocity()), realTime));

                        if (Objects.requireNonNull(spaceObjects.get(i).getVelocityData().peek()).getTime() + 30 < realTime) {
                            spaceObjects.get(i).getVelocityData().remove();
                        }
                    }

                    velocity_time = realTime;
                }

                updateTime = startUpdateSystem - endUpdateSystem;

                updateDataSimulation((updateTime / Constants.secToMilliSec) * timeConstant);

                endUpdateSystem = System.currentTimeMillis();

                realTimeInMs = System.currentTimeMillis() - startSimulationTime;

                realTime = realTimeInMs / Constants.secToMilliSec;

                simulationTime = (realTimeInMs / Constants.secToMilliSec) * timeConstant;

                animationPanel.setTime(simulationTime);

                controlCollision();

                animationPanel.repaint();

                if (informationPanel != null) {
                    informationPanel.reloadInformation();
                }

                firstRun = true;
            }
        });

        timer.start();

        /*mouse listener pro realizaci kliknutí myši na vesmírný objekt*/
        animationPanel.addMouseListener(new MouseAdapter() {
            int index;
            SpaceObject spaceObject;

            @Override
            public void mouseClicked(MouseEvent e) {
                int i = animationPanel.clickOnSpaceObject(e.getX(), e.getY());
                if (i != Constants.nullNumberOutput) {
                    if (index != i && !deleteSpaceObjects.contains(spaceObject)) {
                        if (informationWindow != null) {
                            informationPanel = null;
                            informationWindow.dispose();
                        }
                        spaceObjects.get(index).setColor(SpaceObject.determineColor(spaceObjects.get(index).getSpaceObjectType()));
                        animationPanel.repaint();
                    }

                    index = i;
                    spaceObject = spaceObjects.get(index);

                    spaceObjects.get(index).setColor(Constants.clickedSpaceObjectColor);
                    displayInformation(spaceObjects.get(index));
                    animationPanel.repaint();

                } else {
                    if (informationWindow != null) {
                        informationPanel = null;
                        informationWindow.dispose();
                    }

                    if (!deleteSpaceObjects.contains(spaceObject)) {
                        spaceObjects.get(index).setColor(SpaceObject.determineColor(spaceObjects.get(index).getSpaceObjectType()));
                        animationPanel.repaint();
                    }
                }
            }
        });

        /*keyboard listener pro realizaci pozastavení simulace*/
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyChar() == ' ') {
                    startStopAnimation();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Metoda pro aktualizaci dat vesmírných objektů
     *
     * @param time čas od poslední aktualizace systému
     */
    public void updateDataSimulation(double time) {
        double dtMin = timeConstant / (Constants.updateDataSimulationRunTime / spaceObjects.size());

        while (time > 0) {
            double dt = Math.min(time, dtMin);

            for (int i = 0; i < spaceObjects.size(); i++) {
                computeAcceleration(i);
            }

            for (SpaceObject spaceObject : spaceObjects) {
                spaceObject.setVelocityX(spaceObject.getVelocityX() + (0.5 * dt * spaceObject.getAccelerationX()));
                spaceObject.setX(spaceObject.getX() + (dt * spaceObject.getVelocityX()));
                spaceObject.setVelocityX(spaceObject.getVelocityX() + (0.5 * dt * spaceObject.getAccelerationX()));

                spaceObject.setVelocityY(spaceObject.getVelocityY() + (0.5 * dt * spaceObject.getAccelerationY()));
                spaceObject.setY(spaceObject.getY() + (dt * spaceObject.getVelocityY()));
                spaceObject.setVelocityY(spaceObject.getVelocityY() + (0.5 * dt * spaceObject.getAccelerationY()));
            }

            time = time - dt;
        }
    }

    /**
     * Metoda pro výpočet zrychlení vesmírných objektů
     *
     * @param index objektu, pro který má být vypočteno zrychlení
     */
    private void computeAcceleration(int index) {
        double numerator, denominator, distanceX, distanceY;
        double tempX = 0;
        double tempY = 0;

        for (int i = 0; i < spaceObjects.size(); i++) {
            if (index == i) {
                continue;
            }

            distanceX = spaceObjects.get(i).getX() - spaceObjects.get(index).getX();
            distanceY = spaceObjects.get(i).getY() - spaceObjects.get(index).getY();

            denominator = Math.pow(Math.sqrt((distanceX * distanceX) + (distanceY * distanceY)), 3);

            numerator = spaceObjects.get(i).getX() - spaceObjects.get(index).getX();

            tempX += spaceObjects.get(i).getMass() * (numerator / denominator);

            numerator = spaceObjects.get(i).getY() - spaceObjects.get(index).getY();

            tempY += spaceObjects.get(i).getMass() * (numerator / denominator);
        }

        spaceObjects.get(index).setAccelerationX(gravityConstant * tempX);
        spaceObjects.get(index).setAccelerationY(gravityConstant * tempY);
    }

    /**
     * Metoda pro kontrolu kolize vesmírných objektů.
     */
    private void controlCollision() {
        int[] colliedSpaceObjects;
        double temp_X, temp_Y, distance, sumDiameters;

        for (int i = 0; i < spaceObjects.size(); i++) {
            for (int j = 0; j < spaceObjects.size(); j++) {
                if (i == j) {
                    continue;
                }

                colliedSpaceObjects = animationPanel.shapeCollision(i, j);

                temp_X = spaceObjects.get(i).getX() - spaceObjects.get(j).getX();

                temp_Y = spaceObjects.get(i).getY() - spaceObjects.get(j).getY();

                distance = (temp_X * temp_X) + (temp_Y * temp_Y);

                sumDiameters = spaceObjects.get(i).getRadius() + spaceObjects.get(j).getRadius();

                sumDiameters *= sumDiameters;

                if ((colliedSpaceObjects != null) && (distance <= sumDiameters)) {
                    processCollision(colliedSpaceObjects);
                }
            }
        }
    }

    /**
     * Metoda pro zpracování kolize vesmírných objektů.
     *
     * @param colliedSpaceObjects indexi kolidujících vesmírných objektů
     */
    private void processCollision(int[] colliedSpaceObjects) {
        double[] newVelocity = new double[Constants.spaceDimension];
        double newMass, numerator;

        newMass = spaceObjects.get(colliedSpaceObjects[0]).getMass() + spaceObjects.get(colliedSpaceObjects[1]).getMass();

        numerator = spaceObjects.get(colliedSpaceObjects[0]).getVelocityX() * spaceObjects.get(colliedSpaceObjects[0]).getMass() + spaceObjects.get(colliedSpaceObjects[1]).getVelocityX() * spaceObjects.get(colliedSpaceObjects[1]).getMass();

        newVelocity[0] = numerator / newMass;

        numerator = spaceObjects.get(colliedSpaceObjects[0]).getVelocityY() * spaceObjects.get(colliedSpaceObjects[0]).getMass() + spaceObjects.get(colliedSpaceObjects[1]).getVelocityY() * spaceObjects.get(colliedSpaceObjects[1]).getMass();

        newVelocity[1] = numerator / newMass;

        spaceObjects.get(colliedSpaceObjects[0]).setVelocity(newVelocity);
        spaceObjects.get(colliedSpaceObjects[0]).setMass(newMass);

        spaceObjects.get(colliedSpaceObjects[0]).setDiameter(SpaceObject.determineDiameter(newMass));

        deleteSpaceObjects.push(spaceObjects.get(colliedSpaceObjects[1]));

        spaceObjects.remove(colliedSpaceObjects[1]);
    }

    /**
     * Metoda pro zobrazení okna s informacemi o vesmírném objektu.
     *
     * @param spaceObject vesmírný objekt
     */
    private void displayInformation(SpaceObject spaceObject) {
        informationPanel = new InformationPanel(spaceObject);
        informationWindow = new Window(Constants.informationWindowName, Constants.windowWidth, Constants.windowHeight, informationPanel);

        informationWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (spaceObject.getColor() == Constants.clickedSpaceObjectColor) {
                    spaceObject.setColor(SpaceObject.determineColor(spaceObject.getSpaceObjectType()));
                    animationPanel.repaint();
                }
            }
        });

        informationWindow.setResizable(true);

        informationPanel.reloadInformation();
    }

    public void startStopAnimation() {
        if (!startStopState) {
            stopTime = System.currentTimeMillis();
            timer.stop();
            startStopState = true;

        } else {
            timer.restart();
            restartTime = System.currentTimeMillis();
            endUpdateSystem = System.currentTimeMillis();
            startSimulationTime += restartTime - stopTime;
            startStopState = false;
        }
    }

    /**
     * Getter pro zjištění pole vesmírných objektů
     *
     * @return spaceObjects pole vesmírných objektů
     */
    public ArrayList<SpaceObject> getSpaceObjects() {
        return spaceObjects;
    }
}
