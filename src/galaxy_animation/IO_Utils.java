package galaxy_animation;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

/**
 * Třída realizující specifické I/O operace
 */
public class IO_Utils {

    /**
     * Metoda pro načtení vstupních dat, ve specifikovaném formátu.
     *
     * @param string_path cesta ke vstupnímu souboru
     * @return galaxyAnimation definovaná data pro animaci
     * @throws IOException pokud nelze načíst data ze vstupního souboru
     */
    public static Animation loadInputData(String string_path) throws IOException {
        Animation galaxyAnimation;

        BufferedReader reader;
        Path path = Paths.get(string_path);

        String line;
        String[] data;

        String name;
        SpaceObjectType spaceObjectType;
        double x, y, velocityX, velocityY, mass;

        reader = Files.newBufferedReader(path);

        line = reader.readLine();
        data = line.split(Constants.delimiterCSV);

        if (data.length > 2) {
            return null;
        }

        galaxyAnimation = new Animation(Double.parseDouble(data[0]), Double.parseDouble(data[1]));

        while (true) {
            line = reader.readLine();

            if (line == null) {
                break;
            } else {
                data = line.split(Constants.delimiterCSV);

                if (data.length == 7) {
                    name = data[0];
                    spaceObjectType = SpaceObjectType.getSpaceObjectType(data[1].toLowerCase());

                    if (spaceObjectType == null) {
                        return null;
                    }

                    x = Double.parseDouble(data[2]);
                    y = Double.parseDouble(data[3]);
                    velocityX = Double.parseDouble(data[4]);
                    velocityY = Double.parseDouble(data[5]);
                    mass = Double.parseDouble(data[6]);

                    galaxyAnimation.spaceObjects.add(new SpaceObject(name, spaceObjectType, x, y, velocityX, velocityY, mass));

                } else {
                    return null;
                }
            }
        }

        return galaxyAnimation;
    }

    /**
     * Metoda pro kontrolu vstupních parametrů
     *
     * @param args vstupní parametry programu
     * @return true - pokud je vstup programu validní
     * false - pokud je vstup programu nevalidní
     */
    public static boolean controlInputData(String[] args) {
        if (args.length != 1) {
            return false;
        }

        if (!args[0].endsWith(".csv")) {
            return false;
        }

        File file = new File(args[0]);

        if (!file.exists() || file.isDirectory()) {
            return false;
        }

        return true;
    }

    /**
     * Metoda pro výpis do konzole.
     *
     * @param print řetězec znaků, které se mají vypsat do konzole
     */
    public static void print(String print) {
        System.out.println(print);
        /*Možnost dodělat log*/
    }
}
