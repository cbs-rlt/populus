// This snippet creates a new class extending JFrame whose content pane is a
// JDesktopPane.  Use it together with the InternalFrame snippet to create
// the structure of an MDI (multiple document interface) application.
// Instructions:
// 1. Create a DesktopPane snippet. Be sure to specify the "Name of Internal
//    Frame" parameter.  The snippet has a main method, so is usually placed
//    in a new, empty project.
// 2. Create an InternalFrame snippet, specifying the same class name.
// 3. You can now compile and run the project. Use the File menu to open new
//    internal frames.
//Title:
//Version:
//Copyright:
//Author:
//Company:
//Description:

package edu.umn.ecology.populus.core;

import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.fileio.Logging;
import edu.umn.ecology.populus.help.HelpUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * this class is quite messy, but possibly justifiably so because it is difficult
 * to permanently delete something that could very easily become useful, either as
 * a guide or use again.
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002, 2015</p>
 * <p>Company: University of Minnesota</p>
 *
 * @author Lars Roe, Amos Anderson
 * @version 5.2
 */
public class DesktopWindow extends JFrame implements ModelListener {
    /**
     *
     */
    private static final long serialVersionUID = -1729866105545816729L;
    public static DesktopWindow defaultWindow; //TODO - this should be private, and we'd have a public getter
    public static final String BACKGROUND_IMAGE_FILE = "Populus6.1SplashScreen.png";
    final BorderLayout borderLayout1 = new BorderLayout();

    /**
     * When <code>lock</code> is null, there is no lock.
     * Otherwise, the Model it points to is the locked model.
     */
    Model lock = null;
    final JPanel switcherPanel = new JPanel();
    final NewModelAction newModelAction = new NewModelAction(this);
    final JDesktopPane desktopPane = new JDesktopPane();
    final JLabel imagePanel = new JLabel();
    final JToolBar toolBar = new JToolBar();
    JButton newButton;

    //Action variables
    final ExitAction exitAction = new ExitAction(this);
    JButton helpButton;

    //Core ("Useful") variables
    final Vector<Model> models = new Vector<>(1, 1);
    JButton optionsButton;
    JPanel backgroundPanel = new JPanel();
    JButton quitButton;
    final GridBagLayout gridBagLayout1 = new GridBagLayout();
    ButtonGroup bg1 = new ButtonGroup();
    boolean imageBackgroundIsShowing = true;

    /*2 menu variables*/
    final JPopupMenu topLevelMenu = new JPopupMenu();
    ModelPacket[] mps = null;

    final CardLayout cardLayout1 = new CardLayout(0, 0);
    static final ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.core.Res");

    public void jbInit() throws Exception, Error {
        defaultWindow = this;
        this.getContentPane().setLayout(borderLayout1);
        System.err.print(".");
        desktopPane.putClientProperty("JDesktopPane.dragMode", "outline");
        newButton = PopulusToolButton.createNewButton();
        helpButton = PopulusToolButton.createMainHelpButton();
        optionsButton = PopulusToolButton.createPreferencesButton();
        //install help
        HelpUtilities.createHelp().installComponentHelp(helpButton, "Main");
        quitButton = PopulusToolButton.createQuitButton();
        quitButton.addActionListener(exitAction);
        newButton.addActionListener(newModelAction);
        optionsButton.addActionListener(this::optionsButton_actionPerformed);
        System.err.print(".");

        backgroundPanel.setLayout(gridBagLayout1);
        setPopulusBackground();
        switcherPanel.setLayout(cardLayout1);
        this.setEnabled(true);
        toolBar.setFloatable(false);
        toolBar.add(newButton);
        toolBar.add(optionsButton);
        toolBar.add(helpButton);
        toolBar.add(quitButton);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        this.getContentPane().add(switcherPanel, BorderLayout.CENTER);
        switcherPanel.add(backgroundPanel, "backgroundPanel");
        backgroundPanel.add(imagePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        switcherPanel.add(desktopPane, "desktopPane");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(DesktopWindow.class.getResource("picon.gif")));

        // Creates the top-level models, which right now is only Interaction Engine.
        // The code is basically identical to loadMenu, but that method only accepts JMenu, not JPopupMenu
        mps = PopPreferencesStorage.getModelPackets(PopPreferencesStorage.TOP_PACKETS);
        for (ModelPacket mp : mps) {
            MenuAction ma = new MenuAction(mp.getModelName(), mp.getModelClass()) {
                private static final long serialVersionUID = 6448584135280919807L;

                @Override
                public void actionPerformed(ActionEvent e) {
                    loadModel(model);
                }
            };
            topLevelMenu.add(ma);
        }

        JMenuItem jmi = topLevelMenu.add(res.getString("Load_Model_From_File_"));
        jmi.addActionListener(e -> newModelFromModelChooser(Model.load()));

        topLevelMenu.add(loadMenu(PopPreferencesStorage.getModelPackets(PopPreferencesStorage.SINGLE_PACKETS), "Single-Species Dynamics:"), 0);

        JMenu jm = loadMenu(PopPreferencesStorage.getModelPackets(PopPreferencesStorage.MULTI_PACKETS), "Multi-Species Dynamics:");
        jm.add(loadMenu(PopPreferencesStorage.getModelPackets(PopPreferencesStorage.RC_PACKETS), "Resource Competition:"), 1);
        jm.add(loadMenu(PopPreferencesStorage.getModelPackets(PopPreferencesStorage.DISCRETE_PACKETS), "Discrete Predator-Prey Models:"), 2);
        //just added 7/20 3->4
        //jm.add(loadMenu(PopPreferences.getModelPackets(PopPreferences.HOST_PACKETS),"Host-Parasite Models:"),3);
        JMenu aid = loadMenu(PopPreferencesStorage.getModelPackets(PopPreferencesStorage.HOST_PACKETS), "Host-Parasite Models:");
        aid.add(loadMenu(PopPreferencesStorage.getModelPackets(PopPreferencesStorage.AIDS_PACKETS), "AIDS Models:"), 6);
        topLevelMenu.add(jm, 1);
        jm.add(aid, 1);
        topLevelMenu.add(loadMenu(PopPreferencesStorage.getModelPackets(PopPreferencesStorage.MENDEL_PACKETS), "Mendelian Genetics:"), 2);
        topLevelMenu.add(loadMenu(PopPreferencesStorage.getModelPackets(PopPreferencesStorage.SELECTION_PACKETS), "Natural Selection:"), 3);
        topLevelMenu.add(loadMenu(PopPreferencesStorage.getModelPackets(PopPreferencesStorage.QUANT_PACKETS), "Quantitative-Genetic Models:"), 4);
        topLevelMenu.add(loadMenu(PopPreferencesStorage.getModelPackets(PopPreferencesStorage.SPATIAL_PACKETS), "Spatial Models:"), 5);

		/*
        Alternate look -- JMenuBar
		JMenuBar jmbar = new JMenuBar();
		JMenu jm1 = new JMenu("File");
		JMenuItem jm1a = new JMenuItem("New...");
		JMenuItem jm1b = new JMenuItem("Open...");
		jm1.add(jm1a);
		jm1.add(jm1b);
		jmbar.add(jm1);
		jmbar.add(loadMenu(PopPreferences.getModelPackets(PopPreferences.SINGLE_PACKETS),"Single-Species Dynamics:"),1);
		this.setJMenuBar(jmbar);
		 */


        System.err.print(".\n");
        sizeScreen(this);
        this.setTitle(res.getString("Populus"));
        this.setVisible(true);
    }

    /**
     * Size the window to full size, making sure to account for the insets
     */
    private void sizeScreen(JFrame frame) {
        frame.setSize(PopPreferencesStorage.getDesktopScreenSize(frame));
        frame.setLocation(PopPreferencesStorage.getDesktopLocation(frame));
    }

    public DesktopWindow() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK & AWTEvent.ACTION_EVENT_MASK);

        try {
            jbInit();
        } catch (Exception e) {
            Logging.log(e);
        }
    }

    /**
     * This is called when a model changes. <BR>
     * If one model already declared it is sending stuff to output,
     * and hasn't signalled it is done, a lock is active. If any other
     * model tries to put stuff to output, a CannotChangeModelException
     * is thrown.
     */

    @Override
    public synchronized void modelChanged(ModelEvent e) throws CannotChangeModelException {
        int eventType = e.getType();
        Model changedModel = e.getModel();

        //If event is sending to output, then
        //check lock: if locked then throw a CannotChangeModelException
        //exception, and possibly show the error to user
        if (eventType == ModelEvent.OUTPUT_UPDATE_BEGIN) {
            //already locked?
            if (lock != null) {
                //cannot send to output
                throw new CannotChangeModelException(CannotChangeModelException.OTHER_MODEL_RUNNING);
            } else {
                //Set lock if sending to output - possibly have status bar or SplashScreen
                setLock(changedModel);
            }
        }
        //Remove lock if done sending to output
        if (eventType == ModelEvent.OUTPUT_UPDATE_END) {
            setLock(null);
        }

        //If model is killed, then delete all references to it
        if (e.getType() == ModelEvent.KILL) {
            changedModel.removeModelListener(this);
            models.remove(changedModel);
        }
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            exit();
        }
    }

    void exit() {
        //TODO - this should be cleaner...
        Logging.log("CLOSING POPULUS WINDOW " + this.getSize() + " & " + this.getLocation());
        PopPreferencesStorage.getSingleton().save();
        Logging.cleanup();
        System.exit(0);
    }

    void help() {
        //new HelpDialog();
        //new NotSupportedDialog(this);
    }

    /**
     * this method sets what the background looks like when Populus is first started up.
     * Modify this for difference colors etc.
     */
    void setPopulusBackground() {
        imagePanel.setBackground(Color.white);
        //imagePanel.setBackground(new Color(102,102,109));
        //imagePanel.setBackground(Color.black);
        imagePanel.setHorizontalAlignment(SwingConstants.CENTER);
        imagePanel.setHorizontalTextPosition(SwingConstants.CENTER);
        //backgroundPanel.setBackground( Color.white );
        //backgroundPanel.setBackground(new Color(0,0,152));
        backgroundPanel.setBackground(new Color(0, 0, 206));
        //backgroundPanel.setBackground(Color.black);
        imagePanel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(DesktopWindow.class.getResource(BACKGROUND_IMAGE_FILE))));
    }

    void newModel(ActionEvent e) {
        topLevelMenu.show(newButton, 0, 30);
    }

    /**
     * this method will add all the ModelPacket elements to a JMenu, which it returns.
     * this is used to build the menu.
     *
     * @param mp
     * @param name
     * @return
     */
    JMenu loadMenu(ModelPacket[] mp, String name) {
        JMenu jm = new JMenu(name);
        MenuAction ma;
        /**
         *
         */
        for (ModelPacket modelPacket : mp) {
            ma = new MenuAction(modelPacket.getModelName(), modelPacket.getModelClass()) {
                /**
                 *
                 */
                private static final long serialVersionUID = 6448584135280919807L;

                @Override
                public void actionPerformed(ActionEvent e) {
                    loadModel(model);
                }
            };
            jm.add(ma);
        }
        return jm;
    }

    void bringAllToFront() {
        //check all windows created, and put them all to front
        this.toFront();
    }

    void imagesAndText() {
        PopPreferencesStorage.setButtonType(PopPreferencesStorage.TEXT | PopPreferencesStorage.IMAGES);
    }

    void aboutMenuItem_actionPerformed() {
        new AboutPopulusDialog(this);
    }

    void genericSetModel(Model model) {
        if (model == null) {
            return;
        }
        removeImagePanel();
        model.setDesktopPane(desktopPane);
        model.selectInputWindow();
        model.addModelListener(this);
        models.add(model);
    }

    void newModelFromModelChooser(Model model) {
        genericSetModel(model);
    }

    /**
     * Checkbox is clicked so that title screen should be removed.
     */
    void seeAgainBox_actionPerformed(ActionEvent e) {
		/*
   if (this.seeAgainBox.isSelected()) {
   removeImagePanel();
   }
		 */
    }

    void blankToolbar() {
        PopPreferencesStorage.setButtonType(0);
    }

    void optionsButton_actionPerformed(ActionEvent e) {
        PopPreferencesDialog.bringUpDialog(this);
    }

    void imagesOnly() {
        PopPreferencesStorage.setButtonType(PopPreferencesStorage.IMAGES);
    }

    /**
     * Removes the background screen that shows "Populus ..."
     * Also sets a boolean variable so it won't repeat the process
     * if called a second time.
     */

    void removeImagePanel() {
        if (imageBackgroundIsShowing) {

            //Do I need to make sure the image is destroyed?
            cardLayout1.last(this.switcherPanel);
            this.switcherPanel.remove(backgroundPanel);
            backgroundPanel = null;
            imageBackgroundIsShowing = false;
        }
    }

    private void loadModel(Class<? extends Model> model) {
        try {
            Model m = model.getConstructor(new Class[]{}).newInstance(new Object[]{});
            ColorScheme.addModel(m);
            newModelFromModelChooser(m);
        } catch (Exception e) {
            Logging.log(e);
        }
    }

    private synchronized void setLock(Model m) {
        lock = m;
        if (m == null) {
            //End lock
            lock = m;
            //this.statusBar.setText(" ");
            //this.statusBar.revalidate();
        } else {
            //Begin lock
            lock = null;
            //this.statusBar.setText(res.getString("Updating_"));
            //this.statusBar.revalidate();
        }
    }
}

class ExitAction extends AbstractAction {
    /**
     *
     */
    private static final long serialVersionUID = 1035661466491673963L;
    final DesktopWindow dw;

    @Override
    public void actionPerformed(ActionEvent event) {
        dw.exit();
    }

    ExitAction(DesktopWindow w) {
        this.dw = w;
    }
}

abstract class MenuAction extends AbstractAction {
    /**
     *
     */
    private static final long serialVersionUID = 5106840855809036163L;
    final Class<? extends Model> model;

    MenuAction(String text, Class<? extends Model> modelToRun) {
        super(text);
        model = modelToRun;
    }
}

class NewModelAction implements java.awt.event.ActionListener {
    final DesktopWindow adaptee;

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.newModel(e);
    }

    NewModelAction(DesktopWindow adaptee) {
        this.adaptee = adaptee;
    }
}
