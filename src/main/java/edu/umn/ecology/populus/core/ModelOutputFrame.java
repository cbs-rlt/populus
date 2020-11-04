/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.core;

import edu.umn.ecology.populus.constants.ColorChooser;
import edu.umn.ecology.populus.constants.MenuOptions;
import edu.umn.ecology.populus.constants.OutputTypes;
import edu.umn.ecology.populus.plot.coloredcells.CellPreferences;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

/**
 * This class describes what all output frames look like. i.e. defines the
 * special buttons etc.
 */

public class ModelOutputFrame extends PopInternalFrame {
    /**
     *
     */
    private static final long serialVersionUID = -5467950895300033315L;
    final ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.core.Res");
    final BorderLayout mainBorderLayout = new BorderLayout();
    final JButton closeButton = PopulusToolButton.createCloseButton();
    final transient Model model;

    //JButton optionsButton = PopulusToolButton.createPreferencesButton();//is there a reason to use PreferencesButton?
    final JButton optionsButton = PopulusToolButton.createOptionButton();
    final JButton iterateButton = PopulusToolButton.createIterateButton();
    final JButton switchButton = PopulusToolButton.createSwitchButton();
    final JButton helpButton = PopulusToolButton.createHelpButton();
    final JPopupMenu popupMenu1 = new JPopupMenu();
    final JButton saveButton = PopulusToolButton.createSaveButton();
    final JButton printButton = PopulusToolButton.createPrintButton();
    final JCheckBoxMenuItem jb = new JCheckBoxMenuItem("Don't Overlap Labels");

    public void jbInit() throws Exception {
        this.setIconifiable(true);
        this.setMaximizable(true);
        this.setResizable(true);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addInternalFrameListener(new InternalFrameAdapter() {

            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                closeRequested();
            }
        });
        this.getContentPane().setLayout(mainBorderLayout);
        saveButton.addActionListener(e -> save());
        closeButton.addActionListener(e -> closeRequested());
        printButton.addActionListener(e -> printButton_actionPerformed(e));
        iterateButton.addActionListener(e -> outputButton_actionPerformed(e));
        switchButton.addActionListener(e -> switchOutput(e));

		/*
      ContainerListener cl;
      this.addComponentListener(cl);
      //addComponentListener(null);
		 */
        //optionsButton.setText( res.getString( "Options" ) );
        optionsButton.addActionListener(e -> optionsButton_actionPerformed(e));
        saveButton.setText(res.getString("File"));
        toolBar.setFloatable(false);
        jb.addActionListener(e -> jb_actionPerformed(e));
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        toolBar.add(optionsButton, null);
        toolBar.add(saveButton, null);
        toolBar.add(helpButton, null);
        toolBar.add(printButton, null);

        //toolBar.add(optionsButton, null);
        if (model.isRepeatable()) {
            toolBar.add(iterateButton, null);
        }
        if (model.isSwitchable()) {
            toolBar.add(switchButton, null);
        }
        toolBar.add(closeButton, null);
        popupMenu1.add(new AbstractAction(res.getString("Coarser_Grid"), new ImageIcon(ModelOutputFrame.class.getResource("CoarserGrid.gif"))) { //menu 0

            /**
             *
             */
            private static final long serialVersionUID = 1426050157067073191L;

            @Override
            public void actionPerformed(ActionEvent e) {
                model.getOptions(MenuOptions.kCoarserGrid);
            }
        });
        popupMenu1.add(new AbstractAction(res.getString("Finer_Grid"), new ImageIcon(ModelOutputFrame.class.getResource("FinerGrid2.gif"))) { //menu 1

            /**
             *
             */
            private static final long serialVersionUID = 2014811406137600764L;

            @Override
            public void actionPerformed(ActionEvent e) {
                model.getOptions(MenuOptions.kFinerGrid);
            }
        });
        popupMenu1.add(new AbstractAction(res.getString("Clear_Grid"), new ImageIcon(ModelOutputFrame.class.getResource("ClearGrid.gif"))) { //menu 2

            /**
             *
             */
            private static final long serialVersionUID = -1508833670814269844L;

            @Override
            public void actionPerformed(ActionEvent e) {
                model.getOptions(MenuOptions.kClearGrid);
            }
        });
        popupMenu1.add(new AbstractAction("Increase Size", new ImageIcon(ModelOutputFrame.class.getResource("MagnifyPlus.gif"))) { //menu 3

            /**
             *
             */
            private static final long serialVersionUID = 6804542152933324952L;

            @Override
            public void actionPerformed(ActionEvent e) {
                model.getOptions(MenuOptions.kZoomIn);
            }
        });
        popupMenu1.add(new AbstractAction("Decrease Size", new ImageIcon(ModelOutputFrame.class.getResource("MagnifyMinus.gif"))) { //menu 4

            /**
             *
             */
            private static final long serialVersionUID = 5069074713837250864L;

            @Override
            public void actionPerformed(ActionEvent e) {

                model.getOptions(MenuOptions.kZoomOut);
            }
        });
        popupMenu1.add(new AbstractAction(res.getString("Reset_Graph"), new ImageIcon(ModelOutputFrame.class.getResource("Reset.gif"))) { //menu 5

            /**
             *
             */
            private static final long serialVersionUID = 4971301659510072258L;

            @Override
            public void actionPerformed(ActionEvent e) {
                model.getOptions(MenuOptions.kReset);
            }
        });
        popupMenu1.add(new AbstractAction(res.getString("Display_Chart_Option"), new ImageIcon(ModelOutputFrame.class.getResource("Frame.gif"))) { //menu 6

            /**
             *
             */
            private static final long serialVersionUID = 1167248552186198939L;

            @Override
            public void actionPerformed(ActionEvent e) {
                model.getOptions(MenuOptions.kOptionScreen);
            }
        });
        popupMenu1.add(new AbstractAction(res.getString("Edit_Color_Scheme"), new ImageIcon(ModelOutputFrame.class.getResource("Paint.gif"))) { //menu 7

            /**
             *
             */
            private static final long serialVersionUID = -2158595495950248352L;

            @Override
            public void actionPerformed(ActionEvent e) {
                ColorChooser.bringUpColorDialog();
            }
        });
        popupMenu1.add(new AbstractAction("Cell Settings", new ImageIcon(ModelOutputFrame.class.getResource("Sheet.gif"))) { //menu 8

            /**
             *
             */
            private static final long serialVersionUID = -8142252449979647774L;

            @Override
            public void actionPerformed(ActionEvent e) {
                CellPreferences.bringUpCellPreferences();
            }
        });
		/*
      popupMenu1.add( new AbstractAction( "Squeeze Labels" )  { //menu 9
         boolean areSqueezed = true;
         public void actionPerformed( ActionEvent e ) {
            areSqueezed = !areSqueezed;
            if(areSqueezed)
               ;//change the text here
            else
               ;//change the text here
            model.getOptions( MenuOptions.kSqueezeLabels );
         }
      } );
		 */
        jb.setSelected(true);
        popupMenu1.add(jb);
    }

    public ModelOutputFrame(Model model) {
        super();
        setModelColor(model.getModelColor());
        this.model = model;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void updateTitle() {
        this.setTitle(model.getThisModelOutputName() + res.getString("_Output"));
    }

    void save() {
        model.save();
    }

    @Override
    Component getHelpComponent() {
        return helpButton;
    }

    void switchOutput(ActionEvent e) {
        model.switchOutput();
    }

    void outputButton_actionPerformed(ActionEvent e) {
        model.updateOutput(true);
    }

    void printButton_actionPerformed(ActionEvent e) {
        model.printOutputGraphics();
    }

    void optionsButton_actionPerformed(ActionEvent e) {
        updateMenu();
        if (model.getType() != OutputTypes.kOther)
            popupMenu1.show(optionsButton, 0, 30);
    }

	/*
   void outputRequested() {
   model.updateOutput();
   }
	 */

    void closeRequested() {
        model.closeOutput();
    }

    /**
     * this method sets the visibility of the various menu options for the different graph types.
     */
    void updateMenu() {
        int type = model.getType();
        switch (type) {
            case OutputTypes.k3D:
            case OutputTypes.kDeFi:
                popupMenu1.getComponent(0).setVisible(true);
                popupMenu1.getComponent(1).setVisible(true);
                popupMenu1.getComponent(2).setVisible(true);
                popupMenu1.getComponent(3).setVisible(true);
                popupMenu1.getComponent(4).setVisible(true);
                popupMenu1.getComponent(5).setVisible(true);
                popupMenu1.getComponent(6).setVisible(false);
                popupMenu1.getComponent(7).setVisible(true);
                popupMenu1.getComponent(8).setVisible(false);
                popupMenu1.getComponent(9).setVisible(type == OutputTypes.k3D);
                break;
            case OutputTypes.k2D:
                popupMenu1.getComponent(0).setVisible(true);
                popupMenu1.getComponent(1).setVisible(true);
                popupMenu1.getComponent(2).setVisible(true);
                popupMenu1.getComponent(3).setVisible(false);
                popupMenu1.getComponent(4).setVisible(false);
                popupMenu1.getComponent(5).setVisible(true);
                popupMenu1.getComponent(6).setVisible(true);
                popupMenu1.getComponent(7).setVisible(true);
                popupMenu1.getComponent(8).setVisible(false);
                popupMenu1.getComponent(9).setVisible(false);
                break;
            case OutputTypes.kCell:
                popupMenu1.getComponent(0).setVisible(false);
                popupMenu1.getComponent(1).setVisible(false);
                popupMenu1.getComponent(2).setVisible(false);
                popupMenu1.getComponent(3).setVisible(true);
                popupMenu1.getComponent(4).setVisible(true);
                popupMenu1.getComponent(5).setVisible(true);
                popupMenu1.getComponent(6).setVisible(false);
                popupMenu1.getComponent(7).setVisible(false);
                popupMenu1.getComponent(8).setVisible(true);
                popupMenu1.getComponent(9).setVisible(false);
                break;
            default:
                break;
        }
    }

    void pictureButton_actionPerformed(ActionEvent e) {
        model.save();
    }

    void jb_actionPerformed(ActionEvent e) {
        model.getOptions(MenuOptions.kSqueezeLabels);
    }
}