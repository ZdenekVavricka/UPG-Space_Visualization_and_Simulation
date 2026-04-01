package galaxy_animation;

import java.awt.*;

/**
 * Třída reprezentující konstanty programu.
 */
public class Constants {

    /* ---- Okna ---- */

    /**
     * Názvy jednotlivých oken programu.
     */
    public static final String applicationName = "Galaxy Animation";
    public static final String informationWindowName = "Information";

    /**
     * Rozměry hlavního okna aplikace
     */
    public static final int windowWidth = 640;
    public static final int windowHeight = 480;




    /* ---- Animace a Animační panel ---- */

    /**
     * Rozměry animačního panelu
     */
    public static final int animationPanelWidth = 800;
    public static final int animationPanelHeight = 600;

    /**
     * Text a velikost textu pro výpis simulačního času
     */
    public static final String textSimulationTime = "Simulation time: ";
    public static final int fontSizeSimulationTime = 15;

    /**
     * Hodnota pro převod z milisekund na sekundy a obráceně
     */
    public static final double secToMilliSec = 1000.0;

    /**
     * Počet běhů while smyčky za sekundu následně upravené počtem planet v simulaci
     */
    public static final double updateDataSimulationRunTime = 10000.0;

    /**
     * Obnovovací čas simulace
     */
    public static final int refreshTime = 16;

    /**
     * Barva vybraného vesmírného objektu
     */
    public static final Color clickedSpaceObjectColor = new Color(94, 208, 203, 255);

    /**
     * Barva trajektorie vesmírného objektu
     */
    public static final Color trajectorySpaceObjectColor = new Color(185, 185, 185, 26);

    /**
     * Velikost textu pro výpis informací o vesmírném objektu
     */
    public static final int fontSizeInformation = 16;

    /**
     * Dimenze simulačního prostoru
     */
    public static final int spaceDimension = 2;

    /**
     * Počet hraničních prvků zobrazení
     */
    public static final int numBorderSpaceObjects = 4;


    /* ---- Vesmírné objekty---- */

    /**
     * Hustota vesmírných objektů
     */
    public static final double spaceObjectsDensity = 1;

    /**
     * Minimální zobrazovaný průměr vesmírných objektů
     */
    public static final double minimumDiameterOfSpaceObject = 5.5  ;

    /**
     * Hodnota pro převod z m/s na km/h a obráceně
     */
    public static final double m_s_to_km_s = 3.6;


    /* ---- Informace a Informační panel ---- */

    /**
     * Rozměry informačního panelu
     */
    public static final int informationPanelWidth = 800;
    public static final int informationPanelHeight = 600;

    /**
     * Rozměry dílčího informačního panelu
     */
    public static final int smallInformationPanelWidth = 800;
    public static final int smallInformationPanelHeight = 50;

    /**
     * Popis grafu
     */
    public static final String chartName = "Velocity of planet: ";

    /**
     * Popis os grafu
     */
    public static final String X_AxisDescription = "t [s]";
    public static final String Y_AxisDescription = "v [km/h]";

    /**
     * Základní jednotky v animaci
     */
    public static final String coordinatesUnit = " m";

    public static final String velocityUnit = " m/s";

    public static final String massUnit = " kg";

    /* ---- Menu panel ---- */

    /**
     * Rozměry informačního panelu
     */
    public static final int menuPanelWidth = 800;
    public static final int menuPanelHeight = 70;

    /**
     * Popis tlačítek na menuPanel
     */
    public static String text_ExportSVG = "Export SVG";
    public static String text_ExportPNG = "Export PNG";
    public static String text_playAndPauseAnimation = "Start/Stop";
    public static String text_AccelerationOfAnimation = ">>";
    public static String text_SlowdownOfAnimation = "<<";


    /* ---- Všeobecné konstanty ---- */

    /**
     * Oddělovač informací v CSV souboru
     */
    public static final String delimiterCSV = ",";

    /**
     * Návratová hodnota pokud není nalezen odpovídající vesmírný objekt po kliknutí
     */
    public static final int nullNumberOutput = -1;

    /**
     * Výpis inofrmace o chybě, která nastala v programu
     */
    public static final String incorrectInputDataFormat = "Incorrect input data format.";
    public static final String incorrectInputParameters = "Incorrect input parameters.";

    /**
     * Návratová hodnota programu pokud nastane nějaká chyba
     */
    public static final int errorExitCode = 1;

    /**
     * Šířka ohraničení
     */
    public static final int borderThickness2 = 2;
    public static final int borderThickness0 = 0;

    /**
     * Šířka mezery
     */
    public static final int padding = 10;
}
