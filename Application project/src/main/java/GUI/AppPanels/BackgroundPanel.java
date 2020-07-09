package GUI.AppPanels;
import GUI.Images.ImageContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class BackgroundPanel extends JPanel implements LayoutRefresher {

    /** Constructor */
    public BackgroundPanel(String imageName, Dimension originalFrameSize) {
        super();
        this.imageName = imageName;
        this.originalFrameSize = originalFrameSize;
        frameSize = originalFrameSize;
        oldFrameSize = originalFrameSize;
    }

    /** Methods */
    private BufferedImage getBackgroundImage(Dimension dimension) {
        return ImageContainer.getResizedImage(imageName, dimension);
    }

    protected double getScaleRatio(){
        double widthRatio = (double) frameSize.width/originalFrameSize.width;
        double heightRatio = (double) frameSize.height/originalFrameSize.height;
        if (widthRatio != 1.0 && heightRatio != 1.0) {
            if (widthRatio > 1.0 && heightRatio > 1.0) {
                return Math.min(widthRatio, heightRatio);
            }
            else if (widthRatio < 1.0 && heightRatio < 1.0){
                return Math.max(widthRatio, heightRatio);
            }
            else if (widthRatio > 1.0 && heightRatio < 1.0){
                return heightRatio;
            }
            else
                return widthRatio;
        }
        return 1.0;
    }

    protected void updateOldFrameSize(Dimension dimension){
        oldFrameSize = dimension;
    }

    protected int countAdditionalSize(Container container, LayoutManager layoutManager){

        if (getScaleRatio() > 1.0)
            return ((int) (layoutManager.minimumLayoutSize(container).height*getScaleRatio())) - layoutManager.minimumLayoutSize(container).height;
        else
            return 0;
    }

    protected void drawNextPanel(JFrame mainFrame, JPanel nextPanel){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainFrame.getContentPane().removeAll();
                mainFrame.add(nextPanel);
                mainFrame.validate();
            }
        });
    }


    /** Overridden methods */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        frameSize = this.getSize();
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.drawImage(getBackgroundImage(frameSize), 0, 0, this);
        refreshLayout(frameSize,oldFrameSize);
        updateOldFrameSize(frameSize);
    }

    /** Fields */
    private final String imageName;
    protected Dimension frameSize;
    protected Dimension originalFrameSize;
    protected Dimension oldFrameSize;
    protected Dimension preferredButtonSize = new Dimension(300, 40);
}
