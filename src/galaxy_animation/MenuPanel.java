package galaxy_animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Třída reprezentující Menu Panel
 */
public class MenuPanel extends JPanel {

    /**
     * Globální proměnná animace
     */
    private final Animation animation;

    /**
     * Globální proměnná tlačítka pro pozastavení animace
     */
    private final JButton playAndPauseAnimation;

    /**
     * Globální proměnná tlačítka pro zrychlení animace
     */
    private final JButton accelerationOfAnimation;

    /**
     * Globální proměnná tlačítka pro zpomalení animace
     */
    private final JButton slowdownOfAnimation;

    /**
     * Globální proměnná tlačítka pro export stavu animace do formátu SVG
     */
    private final JButton exportSVG;

    /**
     * Globální proměnná tlačítka pro export stavu animace do formátu PNG
     */
    private final JButton exportPNG;

    /**
     * Konsturktor Menu panelu
     *
     * @param animation instance Animace
     */
    public MenuPanel(Animation animation) {
        this.animation = animation;

        this.setPreferredSize(new Dimension(Constants.menuPanelWidth, Constants.menuPanelHeight));
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createMatteBorder(Constants.borderThickness2, Constants.borderThickness0, Constants.borderThickness0, Constants.borderThickness0, Color.DARK_GRAY));

        JPanel temp_1 = new JPanel();
        JPanel temp_2 = new JPanel();

        this.playAndPauseAnimation = new JButton(Constants.text_playAndPauseAnimation);
        this.accelerationOfAnimation = new JButton(Constants.text_AccelerationOfAnimation);
        this.slowdownOfAnimation = new JButton(Constants.text_SlowdownOfAnimation);
        this.exportSVG = new JButton(Constants.text_ExportSVG);
        this.exportPNG = new JButton(Constants.text_ExportPNG);

        setActionListeners();

        temp_1.add(this.slowdownOfAnimation);
        temp_1.add(this.playAndPauseAnimation);
        temp_1.add(this.accelerationOfAnimation);

        temp_2.add(this.exportSVG);
        temp_2.add(this.exportPNG);

        this.add(temp_1, BorderLayout.CENTER);
        this.add(temp_2, BorderLayout.SOUTH);
    }

    /**
     * Metoda pro nastavení ActionListeners pro ovládací tlačítka
     */
    private void setActionListeners() {
        this.playAndPauseAnimation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animation.startStopAnimation();
            }
        });

        this.accelerationOfAnimation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        this.slowdownOfAnimation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        this.exportSVG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        this.exportPNG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
