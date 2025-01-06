package GUI;

import javax.swing.*;
import java.awt.*;

abstract class FrameOption {
    protected JFrame frame;

    public FrameOption(String title) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
    }

    public abstract void init();

    protected abstract void show();
}
