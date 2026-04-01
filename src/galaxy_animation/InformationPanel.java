package galaxy_animation;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Třída Informačního panelu
 */
public class InformationPanel extends JPanel {

    /**
     * Vesmírný objekt, u kterého budou vypsány základní informace
     */
    private final SpaceObject spaceObject;

    /**
     * Panel grafu rychlosti vesmírného objektu
     */
    public ChartPanel graph;

    /**
     * Globální proměnné pro zobrazení informací o vesmírném objektu
     */
    private final JLabel spaceObjectNameAndMass = new JLabel();
    private final JLabel spaceObjectPosition = new JLabel();
    private final JLabel spaceObjectVelocity = new JLabel();

    /**
     * Konstruktor Informačního panelu
     *
     * @param spaceObject vesmírný objekt, u kterého budou vypsány základní informace
     */
    public InformationPanel(SpaceObject spaceObject) {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(Constants.informationPanelWidth, Constants.informationPanelHeight));
        this.spaceObject = spaceObject;

        this.spaceObjectNameAndMass.setFont(new Font("Arial", Font.BOLD, Constants.fontSizeInformation));

        this.spaceObjectPosition.setFont(new Font("Arial", Font.BOLD, Constants.fontSizeInformation));

        this.spaceObjectVelocity.setFont(new Font("Arial", Font.BOLD, Constants.fontSizeInformation));

        this.spaceObjectNameAndMass.setBorder(new EmptyBorder(Constants.borderThickness0, Constants.borderThickness0, Constants.borderThickness0, Constants.padding));

        this.spaceObjectPosition.setBorder(new EmptyBorder(Constants.borderThickness0, Constants.borderThickness0, Constants.borderThickness0, Constants.padding));

        this.spaceObjectVelocity.setBorder(new EmptyBorder(Constants.borderThickness0, Constants.borderThickness0, Constants.borderThickness0, Constants.padding));

        JPanel information = new JPanel();

        information.setPreferredSize(new Dimension(Constants.smallInformationPanelWidth, Constants.smallInformationPanelHeight));

        information.setBorder(BorderFactory.createMatteBorder(Constants.borderThickness2, Constants.borderThickness0, Constants.borderThickness0, Constants.borderThickness0, Color.DARK_GRAY));

        information.setBackground(Color.white);

        information.add(this.spaceObjectNameAndMass);
        information.add(this.spaceObjectPosition);
        information.add(this.spaceObjectVelocity);

        this.add(information, BorderLayout.SOUTH);

        graph = new ChartPanel(createLineChart());

        this.add(graph, BorderLayout.CENTER);
    }

    /**
     * Metoda pro aktualizaci dat a grafu o daném vesmírném objektu.
     */
    public void reloadInformation() {
        setInformationData();
        graph.setChart(createLineChart());
    }

    /**
     * Metoda pro vypsání o daném vesmírném objektu.
     */
    public void setInformationData() {
        spaceObjectNameAndMass.setText(String.format("<html><style>p {text-align: left;}</style><body><p>Name: %s<br/>Mass: %G %s</p></body></html>", spaceObject.getName(), spaceObject.getMass(), Constants.massUnit));

        spaceObjectPosition.setText(String.format("<html><style>p {text-align: left;}</style><body><p>X: %G %s<br/>Y: %G %s</p></body></html>", spaceObject.getX(), Constants.coordinatesUnit, spaceObject.getY(), Constants.coordinatesUnit));

        spaceObjectVelocity.setText(String.format("<html><style>p {text-align: left;}</style><body><p>velocityX: %G %s<br/>velocityY: %G %s</p></body></html>", spaceObject.getVelocityX(), Constants.velocityUnit, spaceObject.getVelocityY(), Constants.velocityUnit));

    }

    /**
     * Metoda pro vytvoření grafu - knihovna JFreeChart.
     *
     * @return chart graf
     */
    private JFreeChart createLineChart() {
        String chartName = Constants.chartName.concat(spaceObject.getName());

        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries xySeries = new XYSeries(chartName);

        for (DataWithTime<Double> data : spaceObject.getVelocityData()) {
            xySeries.add(data.getTime(), (double) data.getData());
        }

        dataset.addSeries(xySeries);

        JFreeChart chart = ChartFactory.createXYLineChart(chartName, Constants.X_AxisDescription, Constants.Y_AxisDescription, dataset, PlotOrientation.VERTICAL, true, true, false);

        XYPlot plot = chart.getXYPlot();

        plot.setBackgroundPaint(Color.LIGHT_GRAY);

        return chart;
    }
}
