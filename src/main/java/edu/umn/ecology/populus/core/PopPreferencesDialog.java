/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
//Title:        Populus

//Version:

//Copyright:    Copyright (c) 1999

//Author:       Lars Roe, under Don Alstad

//Company:      University of Minnesota

//Description:  First Attempt at using Java 1.2

//with Populus
package edu.umn.ecology.populus.core;

import edu.umn.ecology.populus.constants.ColorChooser;
import edu.umn.ecology.populus.eegg.LPuzzle;
import edu.umn.ecology.populus.help.HelpConfigPanel;
import edu.umn.ecology.populus.math.IntegratorPreferences;
import edu.umn.ecology.populus.plot.coloredcells.CellPreferences;
import edu.umn.ecology.populus.visual.ppfield.PopulusParameterField;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

public class PopPreferencesDialog
        extends JDialog {
    /**
     *
     */
    private static final long serialVersionUID = 8202655658889492472L;
    final JButton cellprefB = new JButton();
    final JPanel lookPanel = new JPanel();
    final JButton okButton = new JButton();
    final JLabel TriggerLabel = new JLabel();
    final JButton colorChooserButton = new JButton();
    final ComboBoxModel<String> typeComboBoxModel = new DefaultComboBoxModel<>(new String[]{
            /* res.getString( "None" ), - Let's not allow blank buttons */
            res.getString("Images"),
            res.getString("Text"), res.getString("Text_and_Images")
    });
    final JButton defaultButton = PopulusToolButton.createRestoreDefaultButton();
    final JButton savePrefButton = PopulusToolButton.createSaveButton();
    final JButton loadPrefButton = PopulusToolButton.createOpenButton();
    final JButton aboutPopButton = PopulusToolButton.createAboutButton();

    final JButton cancelButton = new JButton();
    Border border1;
    final ComboBoxModel<String> triggerComboBoxModel = new DefaultComboBoxModel<>(new String[]{
            res.getString("None"), res.getString("Default"),
            res.getString("Always")
    });
    final JPanel okPanel = new JPanel();
    final GridBagLayout gridBagLayout1 = new GridBagLayout();
    final JPanel actionsPanel = new JPanel();
    final JComboBox<String> triggerComboBox = new JComboBox<>();
    final GridBagLayout gridBagLayout2 = new GridBagLayout();
    final JPanel panel1 = new JPanel();
    final JLabel buttonTypeLabel = new JLabel();
    final JButton integratorPreferencesB = new JButton();
    final JComboBox<String> buttonTypeComboBox = new JComboBox<>();
    static final ResourceBundle res = ResourceBundle.getBundle(
            "edu.umn.ecology.populus.core.Res");
    final GridBagLayout gridBagLayout3 = new GridBagLayout();
    final PopulusParameterField throttlePPF = new PopulusParameterField();
    final JTabbedPane tabbedPane = new JTabbedPane();
    final BorderLayout borderLayout1 = new BorderLayout();
    final JToolBar toolBar = new JToolBar();
    final JPanel extraPanel = new JPanel();
    final PopulusParameterField ownerBorderThickness = new PopulusParameterField();
    final GridBagLayout gridBagLayout4 = new GridBagLayout();
    final JTextField directory = new JTextField();
    final JLabel dirLabel = new JLabel();
    final GridBagLayout gridBagLayout5 = new GridBagLayout();
    final JButton trickButton = new JButton();
    final JComboBox<String> terminusType = new JComboBox<>();
    private final JButton btnHelp = new JButton("Help Settings");
    private final JPanel newFeaturesPanel = new JPanel();
    private final JCheckBox chckbxUseNewChart = new JCheckBox("Use JFreeChart library");
    private final JCheckBox chckbxSaveWindowPosition = new JCheckBox("Save window position");
    private final JCheckBox chckbxPostMessage = new JCheckBox("Show Plot Message Dialogs");

    /**
     * @wbp.parser.constructor
     */
    public PopPreferencesDialog(Frame frame, String title) {
        this(frame, title, false);
    }

    public PopPreferencesDialog(Frame frame) {
        this(frame, res.getString("Preferences"), false);
    }

    public PopPreferencesDialog(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
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
        setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
    }

    void integratorPreferencesB_actionPerformed(ActionEvent e) {
        IntegratorPreferences.bringUpIntegratorPreferences();
    }

    void defaultsButton_actionPerformed(ActionEvent e) {
        PopPreferencesStorage.getSingleton().reset();
        updateValues();
    }

    void saveRequested() {
        updatePreferences();
        PopPreferencesStorage.getSingleton().save();
    }

    void aboutButton_actionPerformed(ActionEvent e) {
        JFrame coolthing = new JFrame();
        coolthing.setIconImage(Toolkit.getDefaultToolkit().getImage(
                DesktopWindow.class.getResource("picon.gif")));
        new AboutPopulusDialog(coolthing,
                res.getString("Populus_Is_"), false);
    }

    void cellprefB_actionPerformed(ActionEvent e) {
        CellPreferences.bringUpCellPreferences();
    }

    /**
     * Should not be called
     */
    void useKLGPlotterBox_actionPerformed(ActionEvent e) {
        //Preferences.setUseDefaultPlotter(!useKLGPlotterBox.isSelected());
    }

    /**
     * Save values
     */
    void updatePreferences() {
		/*
        It doesn't make sense to completely remove the buttons
         case 0:
             Preferences.setButtonType( 0 );
             break;
		 */
        switch (buttonTypeComboBox.getSelectedIndex()) {
        case 0:
            PopPreferencesStorage.setButtonType(PopPreferencesStorage.IMAGES);
            break;
        case 1:
            PopPreferencesStorage.setButtonType(PopPreferencesStorage.TEXT);
            break;
        default:
            PopPreferencesStorage.setButtonType(PopPreferencesStorage.IMAGES +
                    PopPreferencesStorage.TEXT);
        }
        switch (this.triggerComboBox.getSelectedIndex()) {
        case 0:
            PopPreferencesStorage.setTriggerType(PopPreferencesStorage.NO_TRIGGER);
            break;
        case 1:
            PopPreferencesStorage.setTriggerType(PopPreferencesStorage.DEFAULT_TRIGGER);
            break;
        default:
            PopPreferencesStorage.setButtonType(PopPreferencesStorage.ALL_TRIGGER);
        }
        if (terminusType.getSelectedItem().equals("Arrow")) {
            PopPreferencesStorage.setTerminusType(PopPreferencesStorage.kARROWTERMINI);
        } else {
            PopPreferencesStorage.setTerminusType(PopPreferencesStorage.kCIRCLETERMINI);
        }
        PopPreferencesStorage.setDelayTime(this.throttlePPF.getInt());
        PopPreferencesStorage.setDirectory(directory.getText());
        PopPreferencesStorage.setOwnershipBorderThickness(ownerBorderThickness.getInt());
        PopPreferencesStorage.setUseJFreeClass(chckbxUseNewChart.isSelected());
        PopPreferencesStorage.setRestoreDesktop(chckbxSaveWindowPosition.isSelected());
        PopPreferencesStorage.setPostMessage(chckbxPostMessage.isSelected());
    }

    void loadButton_actionPerformed(ActionEvent e) {
        PopPreferencesStorage.getSingleton().load();
        updateValues();
    }

    /**
     * Load values
     */
    void updateValues() {
		/*
         case 0:
       typeComboBoxModel.setSelectedItem( typeComboBoxModel.getElementAt( 0 ) );
             break;
		 */
        switch (PopPreferencesStorage.getButtonType()) {
        case PopPreferencesStorage.IMAGES:
            typeComboBoxModel.setSelectedItem(typeComboBoxModel.getElementAt(0));
            break;
        case PopPreferencesStorage.TEXT:
            typeComboBoxModel.setSelectedItem(typeComboBoxModel.getElementAt(1));
            break;
        default:  //(IMAGES + TEXT)
            typeComboBoxModel.setSelectedItem(typeComboBoxModel.getElementAt(2));
        }
        if (PopPreferencesStorage.getTerminusType() == PopPreferencesStorage.kARROWTERMINI) {
            terminusType.setSelectedItem("Arrow");
        } else {
            terminusType.setSelectedItem("Dot");
        }
        if (PopPreferencesStorage.getTriggerType() == PopPreferencesStorage.ALL_TRIGGER) {
            triggerComboBoxModel.setSelectedItem(triggerComboBoxModel.
                    getElementAt(2));
        } else {
            if (PopPreferencesStorage.getTriggerType() == PopPreferencesStorage.DEFAULT_TRIGGER) {
                triggerComboBoxModel.setSelectedItem(triggerComboBoxModel.
                        getElementAt(1));
            } else {
                if (PopPreferencesStorage.getTriggerType() == PopPreferencesStorage.NO_TRIGGER) {
                    triggerComboBoxModel.setSelectedItem(triggerComboBoxModel.
                            getElementAt(0));
                }
            }
        }
        throttlePPF.setCurrentValue(PopPreferencesStorage.getDelayTime());
        directory.setText(PopPreferencesStorage.getDirectory());
        ownerBorderThickness.setCurrentValue(PopPreferencesStorage.
                getOwnershipBorderThickness());
        chckbxUseNewChart.setSelected(PopPreferencesStorage.isUseJFreeClass());
        chckbxSaveWindowPosition.setSelected(PopPreferencesStorage.isRestoreDesktop());
        chckbxPostMessage.setToolTipText("Some plots may show dialogs during plot, such as Genetic Drift. Select this to show those dialogs.");
        chckbxPostMessage.setSelected(PopPreferencesStorage.isPostMessage());
    }

    void colorChooserButton_actionPerformed(ActionEvent e) {
        ColorChooser.bringUpColorDialog();
    }

    // OK
    void okButton_actionPerformed(ActionEvent e) {
        updatePreferences();
        saveRequested();
        //edu.umn.ecology.populus.help.HelpUtilities.updateHelp();
        dispose();
    }

    // Cancel
    void cancelButton_actionPerformed(ActionEvent e) {
        dispose();
    }

    void this_windowClosing(WindowEvent e) {
        dispose();
    }

    static void bringUpDialog(Frame parent) {
        PopPreferencesDialog dialog = new PopPreferencesDialog(parent);
        dialog.setVisible(true);
    }

    private void jbInit() throws Exception {
        throttlePPF.setCurrentValue(20.0);
        throttlePPF.setDefaultValue(20.0);
        throttlePPF.setIncrementAmount(5.0);
        throttlePPF.setIntegersOnly(true);
        throttlePPF.setMaxValue(1000.0);
        throttlePPF.setMinValue(0.0);
        throttlePPF.setParameterName("Plot Speed");
        throttlePPF.setHelpText("This setting is a measure of the wait time between iterations on live graphs,\n" +
                "like in Haploid Hosts and Parasites. A higher setting means a slower plot time.");

        ownerBorderThickness.setCurrentValue(5.0);
        ownerBorderThickness.setDefaultValue(5.0);
        ownerBorderThickness.setIncrementAmount(1.0);
        ownerBorderThickness.setIntegersOnly(true);
        ownerBorderThickness.setMaxValue(20.0);
        ownerBorderThickness.setMinValue(0.0);
        ownerBorderThickness.setParameterName("Top Border Thickness");
        ownerBorderThickness.setHelpText(
                "Sets the thickness of the colored line at the top of model screens.");

        border1 = BorderFactory.createCompoundBorder(BorderFactory.
                        createBevelBorder(BevelBorder.
                                        RAISED, Color.white, Color.white,
                                new java.awt.Color(134, 134, 134),
                                new java.awt.Color(93, 93, 93)),
                BorderFactory.
                        createEmptyBorder(20, 10, 50,
                                10));
        okPanel.setLayout(gridBagLayout3);
        lookPanel.setLayout(gridBagLayout2);
        okButton.setText(res.getString("OK"));
        okButton.addActionListener(new StandardDialog1_okButton_actionAdapter(this));
        cancelButton.setText(res.getString("Cancel"));
        cancelButton.addActionListener(new
                StandardDialog1_cancelButton_actionAdapter(this));
        this.addWindowListener(new StandardDialog1_this_windowAdapter(this));
        panel1.setLayout(gridBagLayout1);
        buttonTypeLabel.setToolTipText("How do you want buttons to look?");
        buttonTypeLabel.setText(res.getString("Button_Look_"));

        buttonTypeComboBox.setModel(typeComboBoxModel);

        loadPrefButton.addActionListener(new
                PreferencesDialog_loadButton_actionAdapter(this));
        actionsPanel.setLayout(gridBagLayout4);
        TriggerLabel.setToolTipText(res.getString("Triggers_specify_when"));
        TriggerLabel.setText(res.getString("Triggers_"));
        triggerComboBox.setToolTipText(res.getString("Triggers_specify_when"));
        triggerComboBox.setModel(triggerComboBoxModel);
        colorChooserButton.setToolTipText(
                "Edit the color scheme used in Populus");
        colorChooserButton.setText(res.getString("Color_Scheme"));
        colorChooserButton.addActionListener(e -> colorChooserButton_actionPerformed(e));
        terminusType.addItem("Arrow");
        terminusType.addItem("Dot");
        aboutPopButton.addActionListener(e -> aboutButton_actionPerformed(e));
        integratorPreferencesB.setText("Integrator Settings");
        integratorPreferencesB.addActionListener(new
                PreferencesDialog_integratorPreferencesB_actionAdapter(this));
        cellprefB.setText("Cell Settings");
        cellprefB.addActionListener(new
                PreferencesDialog_cellprefB_actionAdapter(this));
        defaultButton.addActionListener(new
                PreferencesDialog_defaultsButton_actionAdapter(this));
        savePrefButton.addActionListener(new
                PreferencesDialog_saveButton_actionAdapter(this));
        this.getContentPane().setLayout(borderLayout1);
        loadPrefButton.setToolTipText("Reload Preferences from File");
        savePrefButton.setToolTipText("Save Preferences to Disk");
        ownerBorderThickness.setToolTipText("");
        extraPanel.setLayout(gridBagLayout5);
        directory.setText(PopPreferencesStorage.getDirectory()); //TODO ease of use: We should have a browse button here.
        dirLabel.setToolTipText("Default Directory to Save and Load Models");
        dirLabel.setText("Directory:");
        trickButton.setActionCommand("Trick");
        trickButton.setText("?!");
        trickButton.addActionListener(new
                PreferencesDialog_trickButton_actionAdapter(this));
        this.getContentPane().add(toolBar, java.awt.BorderLayout.NORTH);
        updateValues();
        this.getContentPane().add(panel1, java.awt.BorderLayout.CENTER);
        toolBar.add(savePrefButton);
        toolBar.add(loadPrefButton);
        toolBar.add(defaultButton);
        toolBar.add(aboutPopButton);
        tabbedPane.add(lookPanel, "Look");
        tabbedPane.add(actionsPanel, "Actions");
        panel1.add(tabbedPane, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        actionsPanel.add(throttlePPF,
                new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
                        , GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 5, 0, 5), 0, 0));
        actionsPanel.add(triggerComboBox,
                new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                        , GridBagConstraints.WEST,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        actionsPanel.add(TriggerLabel,
                new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                        , GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        tabbedPane.add(extraPanel, "More");
        okPanel.add(okButton, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.NONE,
                new Insets(5, 5, 5, 5), 0, 0));
        okPanel.add(cancelButton, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(5, 5, 5, 5), 0, 0));
        panel1.add(okPanel, new GridBagConstraints(0, 2, 2, 1, 1.0, 0.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

        GridBagConstraints gbc_btnHelp = new GridBagConstraints();
        gbc_btnHelp.gridwidth = 3;
        gbc_btnHelp.insets = new Insets(0, 0, 5, 0);
        gbc_btnHelp.gridx = 0;
        gbc_btnHelp.gridy = 4;
        btnHelp.addActionListener(e -> HelpConfigPanel.launchWindow());
        extraPanel.add(btnHelp, gbc_btnHelp);
        extraPanel.add(integratorPreferencesB,
                new GridBagConstraints(0, 1, 3, 1, 1.0, 1.0
                        , GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 5, 0), 0, 0));
        extraPanel.add(dirLabel, new GridBagConstraints(0, 3, 2, 1, 0.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 5, 0, 5), 0, 0));
        extraPanel.add(directory, new GridBagConstraints(2, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 5, 0, 0), 0, 0));
        extraPanel.add(trickButton, new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));
        lookPanel.add(buttonTypeLabel, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        lookPanel.add(buttonTypeComboBox,
                new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
                        , GridBagConstraints.WEST,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(0, 0, 0, 0), 0, 0));

        GridBagConstraints gbc_chckbxPostMessage = new GridBagConstraints();
        gbc_chckbxPostMessage.gridwidth = 3;
        gbc_chckbxPostMessage.insets = new Insets(0, 0, 5, 5);
        gbc_chckbxPostMessage.gridx = 0;
        gbc_chckbxPostMessage.gridy = 6;
        lookPanel.add(chckbxPostMessage, gbc_chckbxPostMessage);
        lookPanel.add(cellprefB, new GridBagConstraints(0, 5, 3, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        lookPanel.add(colorChooserButton,
                new GridBagConstraints(0, 4, 3, 1, 0.0, 0.0
                        , GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        lookPanel.add(ownerBorderThickness,
                new GridBagConstraints(0, 3, 3, 1, 1.0, 1.0
                        , GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        lookPanel.add(new JLabel("Trajectory Plot Symbols"), new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        lookPanel.add(terminusType, new GridBagConstraints(2, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));

        tabbedPane.addTab("New", null, newFeaturesPanel, null);
        GridBagLayout gbl_newFeaturesPanel = new GridBagLayout();
        gbl_newFeaturesPanel.columnWidths = new int[]{0, 0, 0, 0};
        gbl_newFeaturesPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
        gbl_newFeaturesPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_newFeaturesPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        newFeaturesPanel.setLayout(gbl_newFeaturesPanel);

        GridBagConstraints gbc_chckbxUseNewChart = new GridBagConstraints();
        gbc_chckbxUseNewChart.insets = new Insets(0, 0, 5, 5);
        gbc_chckbxUseNewChart.gridx = 0;
        gbc_chckbxUseNewChart.gridy = 0;
        newFeaturesPanel.add(chckbxUseNewChart, gbc_chckbxUseNewChart);

        GridBagConstraints gbc_chckbxSaveWindowPosition = new GridBagConstraints();
        gbc_chckbxSaveWindowPosition.insets = new Insets(0, 0, 5, 5);
        gbc_chckbxSaveWindowPosition.gridx = 0;
        gbc_chckbxSaveWindowPosition.gridy = 1;
        newFeaturesPanel.add(chckbxSaveWindowPosition, gbc_chckbxSaveWindowPosition);
    }

    public void trickButton_actionPerformed(ActionEvent e) {
        Window w = this.getOwner();
        try {
            new LPuzzle((Frame) w);
        } catch (Exception exc) {
        }
    }

    @Override
    public void dispose() {
        Component[] comps = toolBar.getComponents();
        for (int i = comps.length - 1; i >= 0; i--) {
            if (comps[i] instanceof PopulusToolButton) {
                ((PopulusToolButton) comps[i]).dispose();
            }
        }
        super.dispose();
    }
}

class PreferencesDialog_trickButton_actionAdapter
        implements ActionListener {
    private final PopPreferencesDialog adaptee;

    PreferencesDialog_trickButton_actionAdapter(PopPreferencesDialog adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.trickButton_actionPerformed(e);
    }
}

class StandardDialog1_okButton_actionAdapter
        implements ActionListener {
    final PopPreferencesDialog adaptee;

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.okButton_actionPerformed(e);
    }

    StandardDialog1_okButton_actionAdapter(PopPreferencesDialog adaptee) {
        this.adaptee = adaptee;
    }
}

class StandardDialog1_cancelButton_actionAdapter
        implements ActionListener {
    final PopPreferencesDialog adaptee;

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.cancelButton_actionPerformed(e);
    }

    StandardDialog1_cancelButton_actionAdapter(PopPreferencesDialog adaptee) {
        this.adaptee = adaptee;
    }
}

class StandardDialog1_this_windowAdapter
        extends WindowAdapter {
    final PopPreferencesDialog adaptee;

    @Override
    public void windowClosing(WindowEvent e) {
        adaptee.this_windowClosing(e);
    }

    StandardDialog1_this_windowAdapter(PopPreferencesDialog adaptee) {
        this.adaptee = adaptee;
    }
}

class PreferencesDialog_defaultsButton_actionAdapter
        implements java.awt.event.
        ActionListener {
    final PopPreferencesDialog adaptee;

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.defaultsButton_actionPerformed(e);
    }

    PreferencesDialog_defaultsButton_actionAdapter(PopPreferencesDialog adaptee) {
        this.adaptee = adaptee;
    }
}

class PreferencesDialog_loadButton_actionAdapter
        implements java.awt.event.
        ActionListener {
    final PopPreferencesDialog adaptee;

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.loadButton_actionPerformed(e);
    }

    PreferencesDialog_loadButton_actionAdapter(PopPreferencesDialog adaptee) {
        this.adaptee = adaptee;
    }
}

class PreferencesDialog_saveButton_actionAdapter
        implements java.awt.event.
        ActionListener {
    final PopPreferencesDialog adaptee;

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.saveRequested();
    }

    PreferencesDialog_saveButton_actionAdapter(PopPreferencesDialog adaptee) {
        this.adaptee = adaptee;
    }
}

class PreferencesDialog_integratorPreferencesB_actionAdapter
        implements java.
        awt.event.ActionListener {
    final PopPreferencesDialog adaptee;

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.integratorPreferencesB_actionPerformed(e);
    }

    PreferencesDialog_integratorPreferencesB_actionAdapter(PopPreferencesDialog
                                                                   adaptee) {
        this.adaptee = adaptee;
    }
}

class PreferencesDialog_cellprefB_actionAdapter
        implements java.awt.event.
        ActionListener {
    final PopPreferencesDialog adaptee;

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.cellprefB_actionPerformed(e);
    }

    PreferencesDialog_cellprefB_actionAdapter(PopPreferencesDialog adaptee) {
        this.adaptee = adaptee;
    }
}
