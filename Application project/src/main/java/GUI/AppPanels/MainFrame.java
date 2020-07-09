package GUI.AppPanels;

import javax.swing.*;
import java.awt.*;

public class MainFrame  {
    public MainFrame() {
        JFrame mainFrame = new JFrame("Baza danych pracowników") ;
        mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);
        mainFrame.setMinimumSize(minimumSize);
        mainFrame.setSize(preferredSize);

        mainFrame.setLocation(getFrameCenterPoint(mainFrame.getSize()));

        mainFrame.add(new LoginPanel(mainFrame, getOriginalPaneSize()));
        mainFrame.setVisible(true);

    }

    private Point getFrameCenterPoint(Dimension dimension) {
        return new Point(graphicsEnvironment.getCenterPoint().x-dimension.width/2,  graphicsEnvironment.getCenterPoint().y-dimension.height/2);
    }
    private Dimension findPreferredSize() {
        return new Dimension(graphicsEnvironment.getMaximumWindowBounds().width/2, graphicsEnvironment.getMaximumWindowBounds().height/2);
    }
    private Dimension findOriginalSize() {
        return new Dimension(Math.max(preferredSize.width, minimumSize.width), Math.max(preferredSize.height, minimumSize.height));
    }

    public Dimension getMinimumSize() {
        return minimumSize;
    }

    public Dimension getOriginalSize() {
        return originalSize;
    }
    public Dimension getPreferredSize() {
        return preferredSize;
    }

    public Dimension getOriginalPaneSize(){
        Dimension paneSize;
        paneSize = originalSize;
        paneSize.width -= 16;
        paneSize.height -= 39;
        return paneSize;
    }


    private final GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final Dimension minimumSize = new Dimension(800, 600);
    private final Dimension preferredSize = findPreferredSize();
    private final Dimension originalSize = findOriginalSize();


}
