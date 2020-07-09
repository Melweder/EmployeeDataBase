package GUI.AppPanels;

import java.awt.*;

public interface LayoutRefresher  {

    public void setupLayout();

    default public void refreshLayout(Dimension frameSize, Dimension oldFrameSize){
        if ((oldFrameSize.height != frameSize.height) || (oldFrameSize.width != frameSize.width)) {
            setupLayout();
        }
    }
}
