package galaxy_animation;

import javax.swing.*;

/**
 * Třída reprezentující okno aplikace
 */
public class Window extends JFrame {

    /**
     * Konstruktor okna aplikace
     *
     * @param name   jméno okna aplikace
     * @param width  šířka okna aplikace
     * @param height výška okna aplikace
     * @param panel  Jpanel, který má být zobrazen v daném okně
     */
    public Window(String name, int width, int height, JPanel panel) {
        this.setTitle(name);
        this.setSize(width, height);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
