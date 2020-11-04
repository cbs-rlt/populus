/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.math;

import edu.umn.ecology.populus.constants.RungeKuttaDefaults;
import edu.umn.ecology.populus.core.DesktopWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class IntegratorPreferences extends JDialog {
    /**
     *
     */
    private static final long serialVersionUID = 959285401462290615L;
    final JPanel mainPanel = new JPanel();
    final GridBagLayout gridBagLayout1 = new GridBagLayout();
    final JTextField epsSetting = new JTextField();
    final JLabel jLabel4 = new JLabel();
    final JButton cancelB = new JButton();
    final JLabel jLabel1 = new JLabel();
    final JLabel jLabel2 = new JLabel();
    final JLabel jLabel3 = new JLabel();
    final JTextField maxPointsSetting = new JTextField();
    final JTextField ssMinDurSetting = new JTextField();
    final JTextField ssPrecisionSetting = new JTextField();
    final JButton setB = new JButton();
    static IntegratorPreferences keepOnlyOneOpen;
    final JTextField hSetting = new JTextField();
    final JLabel jLabel5 = new JLabel();
    final JComboBox intMethodCB = new JComboBox();
    final JLabel jLabel6 = new JLabel();

    public IntegratorPreferences(Frame frame, String title, boolean b) {
        super(frame, title, b);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        pack();

        //Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }

    public static void bringUpIntegratorPreferences() {
        if (IntegratorPreferences.keepOnlyOneOpen == null) {
            Frame coolthing = new Frame();
            coolthing.setIconImage(Toolkit.getDefaultToolkit().getImage(DesktopWindow.class.getResource("picon.gif")));
            IntegratorPreferences.keepOnlyOneOpen = new IntegratorPreferences(coolthing, "Integrator Preferences", false);
        }
        IntegratorPreferences.keepOnlyOneOpen.init();
        IntegratorPreferences.keepOnlyOneOpen.setVisible(true);
    }

    void jbInit() {
        mainPanel.setLayout(gridBagLayout1);
        jLabel1.setToolTipText("Precision of integration");
        jLabel1.setText("\u03B5 :");
        jLabel2.setToolTipText("The integrator will only calculate this number of data points");
        jLabel2.setText("Max points:");
        jLabel3.setToolTipText("higher usually means shorter lines");
        jLabel3.setText("Steady-state precision:");
        epsSetting.setPreferredSize(new Dimension(50, 21));
        maxPointsSetting.setPreferredSize(new Dimension(50, 21));
        ssPrecisionSetting.setPreferredSize(new Dimension(50, 21));
        setB.setToolTipText("");
        setB.setText("OK");
        setB.addActionListener(new IntegratorPreferences_setB_actionAdapter(this));
        cancelB.setText("Cancel");
        cancelB.addActionListener(new IntegratorPreferences_cancelB_actionAdapter(this));
        ssMinDurSetting.setPreferredSize(new Dimension(50, 21));
        ssMinDurSetting.setToolTipText("");
        jLabel4.setToolTipText("s-s will run at least this long");
        jLabel4.setText("Steady-state min duration:");
        jLabel5.setToolTipText("This is the step size the integrator will use");
        jLabel5.setText("Integration Step h:");
        hSetting.setPreferredSize(new Dimension(50, 21));
        intMethodCB.addItem("Euler");
        intMethodCB.addItem("Midpoint");
        intMethodCB.addItem("RK4");
        intMethodCB.addItem("RK4 w/ QC");
        //      intMethodCB.addItem("Discrete");
        jLabel6.setToolTipText("Select the integration algorithm to use");
        jLabel6.setText("Integration Method:");
        intMethodCB.addActionListener(e -> intMethodCB_actionPerformed(e));
        mainPanel.add(epsSetting, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 0, 5, 10), 0, 0));
        mainPanel.add(maxPointsSetting, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 5, 10), 0, 0));
        mainPanel.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        mainPanel.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        mainPanel.add(jLabel3, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
        mainPanel.add(ssMinDurSetting, new GridBagConstraints(1, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 5, 10), 0, 0));
        mainPanel.add(jLabel4, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        mainPanel.add(ssPrecisionSetting, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 5, 10), 0, 0));
        mainPanel.add(hSetting, new GridBagConstraints(1, 4, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 5, 10), 0, 0));
        mainPanel.add(jLabel5, new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(mainPanel, null);
        mainPanel.add(intMethodCB, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
        mainPanel.add(jLabel6, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
        mainPanel.add(setB, new GridBagConstraints(0, 6, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 10, 0), 0, 0));
        mainPanel.add(cancelB, new GridBagConstraints(1, 6, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 10, 0), 0, 0));
    }

    void setB_actionPerformed(ActionEvent e) {
        RungeKuttaDefaults.dEPS = Double.parseDouble(epsSetting.getText());
        RungeKuttaDefaults.dMaxiter = Integer.parseInt(maxPointsSetting.getText());
        RungeKuttaDefaults.dSSERR = Double.parseDouble(ssPrecisionSetting.getText());
        RungeKuttaDefaults.dSSMinDur = Double.parseDouble(ssMinDurSetting.getText());
        RungeKuttaDefaults.h = Double.parseDouble(hSetting.getText());
        RungeKuttaDefaults.mode = intMethodCB.getSelectedIndex();
        this.setVisible(false);
    }

    void cancelB_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }

    void init() {
        epsSetting.setText(String.valueOf(RungeKuttaDefaults.dEPS));
        maxPointsSetting.setText(String.valueOf(RungeKuttaDefaults.dMaxiter));
        ssPrecisionSetting.setText(String.valueOf(RungeKuttaDefaults.dSSERR));
        ssMinDurSetting.setText(String.valueOf(RungeKuttaDefaults.dSSMinDur));
        hSetting.setText(String.valueOf(RungeKuttaDefaults.h));
        intMethodCB.setSelectedIndex(RungeKuttaDefaults.mode);
    }

    void intMethodCB_actionPerformed(ActionEvent e) {
    }

}

class IntegratorPreferences_setB_actionAdapter implements java.awt.event.ActionListener {
    final IntegratorPreferences adaptee;

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.setB_actionPerformed(e);
    }

    IntegratorPreferences_setB_actionAdapter(IntegratorPreferences adaptee) {
        this.adaptee = adaptee;
    }
}

class IntegratorPreferences_cancelB_actionAdapter implements java.awt.event.ActionListener {
    final IntegratorPreferences adaptee;

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.cancelB_actionPerformed(e);
    }

    IntegratorPreferences_cancelB_actionAdapter(IntegratorPreferences adaptee) {
        this.adaptee = adaptee;
    }
}
