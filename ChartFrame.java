package collisions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;

public class ChartFrame extends ApplicationFrame {
    private static final int COUNT = 2 * 60;
    private static final int FAST = 100;
    private Timer timer;

    public ChartFrame(final String Title) {
        super(Title);

        DynamicTimeSeriesCollection dataset = new DynamicTimeSeriesCollection(2, COUNT, new Second());
        dataset.setTimeBase(new Second(0, 0, 0, 31, 12, 2019));
        dataset.addSeries(PredkoscKulka1(), 0, "Kulka 1");
        dataset.addSeries(PredkoscKulka2(), 1, "Kulka 2");
        JFreeChart chart = createChart(dataset);
        this.add(new ChartPanel(chart), BorderLayout.CENTER);

        timer = new Timer(FAST, new ActionListener() {
            float[] newData = new float[2];

            public void actionPerformed(ActionEvent e) {
                newData[0] = (float) Math.round(Math.sqrt(AnimationPanel.Kulka1.getVX() * AnimationPanel.Kulka1.getVX()
                        + AnimationPanel.Kulka1.getVY() * AnimationPanel.Kulka1.getVY()));
                newData[1] = (float) Math.round(Math.sqrt(AnimationPanel.Kulka2.getVX() * AnimationPanel.Kulka2.getVX()
                        + AnimationPanel.Kulka2.getVY() * AnimationPanel.Kulka2.getVY()));
                dataset.advanceTime();
                dataset.appendData(newData);
            }
        });
    }

    private float[] PredkoscKulka1() {
        float[] a = new float[COUNT];
        for (int i = 0; i < a.length; i++) {
            a[i] = (float) Math.round(Math.sqrt(AnimationPanel.Kulka1.getVX() * AnimationPanel.Kulka1.getVX()
                    + AnimationPanel.Kulka1.getVY() * AnimationPanel.Kulka1.getVY()));
        }
        return a;
    }

    private float[] PredkoscKulka2() {
        float[] a = new float[COUNT];
        for (int i = 0; i < a.length; i++) {
            a[i] = (float) Math.round(Math.sqrt(AnimationPanel.Kulka2.getVX() * AnimationPanel.Kulka2.getVX()
                    + AnimationPanel.Kulka2.getVY() * AnimationPanel.Kulka2.getVY()));
        }
        return a;
    }

    private JFreeChart createChart(final XYDataset dataset) {
        JFreeChart result = ChartFactory.createTimeSeriesChart("", "Time", "V (m/s)", dataset, true, true, false);
        XYPlot plot = result.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        // LegendTitle legend = result.getLegend();
        // legend.setPosition(RectangleEdge.RIGHT);
        result.getLegend().setFrame(BlockBorder.NONE);
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);

        return result;
    }

    public void Start() {
        timer.start();
    }
}