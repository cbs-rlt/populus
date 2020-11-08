/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.core;

import edu.umn.ecology.populus.constants.ColorSaver;
import edu.umn.ecology.populus.constants.ValuesToSave;
import edu.umn.ecology.populus.fileio.Logging;
import edu.umn.ecology.populus.help.OpenPDFMethod;

import javax.swing.*;
import java.awt.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.*;

//Title:        Populus
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Lars Roe
//Company:      University of Minnesota
//Description:  Populus


public final class PopPreferencesStorage {
    ///////////////////////
    // CONSTANTS DEFINED //
    ///////////////////////

    //Button Type Constants, default values
    /**
     * Button Type
     */
    public static final int IMAGES = 1;
    /**
     * Button Type
     */
    public static final int TEXT = 2;
    /**
     * Button Type
     */
    public static final int INVALID = -1;
    /**
     * Button Type
     */
    public static final int DEFAULT_BUTTON_TYPE = IMAGES + TEXT;
    //BORDER CONSTANT
    public static final int DEFAULT_BORDER_THICKNESS = 5;
    public static final long DEFAULT_DELAY = 20;
    /**
     * TERMINUS_TYPE
     */
    public static final int kARROWTERMINI = 1;
    /**
     * TERMINUS_TYPE
     */
    public static final int kCIRCLETERMINI = 2;
    public static final int kDEFAULTTERMINI = kCIRCLETERMINI;
    //Trigger Type Constants
    /**
     * Trigger Type
     */
    public static final int NO_TRIGGER = 210;
    /**
     * Trigger Type
     */
    public static final int DEFAULT_TRIGGER = 211;
    /**
     * Trigger Type
     */
    public static final int ALL_TRIGGER = 212;

    public static final Color DEFAULT_TABLE_EDIT_COLOR = Color.yellow;
    public static final Color DEFAULT_TABLE_UNEDIT_COLOR = Color.white;

    public static final String DEFAULT_HELP_FILE = "";
    public static final String DEFAULT_HELP_LANG = "English";

    //TODO SAFE private static final String DEFAULT_DIRECTORY = System.getProperty( "user.home", "." );
    private static final String DEFAULT_DIRECTORY = "";
    private static final boolean DEFAULT_RESTORE_DESKTOP = false;
    private static final boolean DEFAULT_USE_JFREECHART = true;
    private static final boolean DEFAULT_POST_MESSAGE = true;

    private static final Integer BUTTON_TYPE = 100;
    private static final Integer DIRECTORY = 101;
    private static final Integer DELAY_TIME = 102;
    private static final Integer TRIGGER_TYPE = 103;
    private static final Integer USE_AWT_FILEDIALOG = 104;
    private static final Integer COLOR_SAVER = 105;
    private static final Integer VALUE_SAVER = 106;
    private static final Integer BORDER_THICKNESS = 107;
    private static final Integer TERMINUS_TYPE = 108;
    private static final Integer TABLE_EDIT_COLOR = 109;
    private static final Integer TABLE_UNEDIT_COLOR = 110;
    private static final Integer HELP_FILE_LOCATION = 111;
    private static final Integer HELP_FILE_LANGUAGE = 112;
    private static final Integer OPEN_PDF_METHOD = 113;
    private static final Integer OPEN_PDF_COMMAND = 114;
    private static final Integer RESTORE_DESKTOP = 115;
    private static final Integer DESKTOP_SIZE = 116;
    private static final Integer DESKTOP_LOCATION = 117;
    private static final Integer USE_JFREECHART = 118;
    private static final Integer POST_MESSAGE = 119;

    public static final Integer TOP_PACKETS = 7;
    public static final Integer SINGLE_PACKETS = 8;
    public static final Integer MULTI_PACKETS = 9;
    public static final Integer DISCRETE_PACKETS = 10;
    public static final Integer MENDEL_PACKETS = 11;
    public static final Integer SELECTION_PACKETS = 12;
    public static final Integer SPATIAL_PACKETS = 13;
    public static final Integer HOST_PACKETS = 14;
    public static final Integer RC_PACKETS = 15;
    public static final Integer QUANT_PACKETS = 16;
    public static final Integer AIDS_PACKETS = 17;

    //Reference to singleton
    private static PopPreferencesStorage singleton = null;

    //Instance data
    private final ArrayList<PopulusToolButton> buttons;
    private Hashtable<Integer, ModelPacket[]> packetTable; //List of Populus models, arranged in groups
    private Hashtable<Integer, Object> table; //Miscellaneous preference data
    private static String preferencesFile = null;

    private PopPreferencesStorage() {
        buttons = new ArrayList<>();
        initializeMenuPackets();
        reset(true);
        subLoad(true);
    }

    public static synchronized void removeButton(PopulusToolButton b) {
        getSingleton().buttons.remove(b);
    }

    /**
     * Get the singleton instance of PopPreferences.
     * Note that we assume that make sure to only create one singleton if it
     * doesn't already exist, but there isn't any other synchronization code
     * elsewhere when we modify it, since we assume it's all done in one
     * thread.
     *
     * @return PopPreferences
     */
    public synchronized static PopPreferencesStorage getSingleton() {
        if (singleton == null)
            singleton = new PopPreferencesStorage();
        return singleton;
    }

    /**
     * Lets PopulusToolButtons get notified when needed
     */
    public static void addButton(PopulusToolButton b) {
        getSingleton().buttons.add(b);
    }

    // GETTERS
    //"safe" means that we will use the default value if not found
    //TODO: catch cast exception
    //TODO: Log if we use default value, since this shouldn't happen.
    private <T> T safeLookup(Integer key, T defaultValue) {
        @SuppressWarnings("unchecked")
        T val = (T) table.get(key);
        return (null == val) ? defaultValue : val;
    }
    //TODO - can we put this into the above function?  This should be automatic with unboxing in Java 1.5
	/*
	private int safeLookup(Integer key, int defaultValue) {
		Integer i = (Integer) table.get(key);
		return (i == null) ? defaultValue : i.intValue();
	}
	private long safeLookup(Integer key, long defaultValue) {
		Long l = (Long) table.get(key);
		return (l == null) ? defaultValue : l.longValue();
	}
	 */

    public static int getButtonType() {
        return getSingleton().safeLookup(BUTTON_TYPE, DEFAULT_BUTTON_TYPE);
    }

    public static int getTriggerType() {
        return getSingleton().safeLookup(TRIGGER_TYPE, DEFAULT_TRIGGER);
    }

    public static String getDirectory() {
        return getSingleton().safeLookup(DIRECTORY, DEFAULT_DIRECTORY);
    }

    public static long getDelayTime() {
        return getSingleton().safeLookup(DELAY_TIME, DEFAULT_DELAY);
    }

    public static int getOwnershipBorderThickness() {
        return getSingleton().safeLookup(BORDER_THICKNESS, DEFAULT_BORDER_THICKNESS);
    }

    public static int getTerminusType() {
        return getSingleton().safeLookup(TERMINUS_TYPE, kDEFAULTTERMINI);
    }

    public static Color getTableEditColor() {
        return getSingleton().safeLookup(TABLE_EDIT_COLOR, DEFAULT_TABLE_EDIT_COLOR);
    }

    public static Color getTableUneditColor() {
        return getSingleton().safeLookup(TABLE_UNEDIT_COLOR, DEFAULT_TABLE_UNEDIT_COLOR);
    }

    /**
     * True - use JFreeClass; False - use KLG Chart
     */
    public static boolean isUseJFreeClass() {
        return getSingleton().safeLookup(USE_JFREECHART, DEFAULT_USE_JFREECHART);
    }

    /* HELP PREFERENCES */
    public static String getHelpFileLocation() {
        return getSingleton().safeLookup(HELP_FILE_LOCATION, DEFAULT_HELP_FILE);
    }

    public static String getHelpLang() {
        return getSingleton().safeLookup(HELP_FILE_LANGUAGE, DEFAULT_HELP_LANG);
    }

    public static int getOpenPDFMethod() {
        return getSingleton().safeLookup(OPEN_PDF_METHOD, OpenPDFMethod.DEFAULT_METHOD.getOpenMethod());
    }

    public static String getOpenPDFCommand() {
        return getSingleton().safeLookup(OPEN_PDF_COMMAND, OpenPDFMethod.DEFAULT_METHOD.getExecStr());
    }

    public static OpenPDFMethod getOpenPDFObject() {
        return new OpenPDFMethod(getOpenPDFMethod(), getOpenPDFCommand());
    }

    /**
     * This is for saving a MODEL, not the global preferences file
     */
    public static boolean getUseXMLSave() {
        //TODO: Actually make this an exposed feature that is saved in the table.
        //TODO - XML loading doesn't seem to work. The values seem to be getting saved
        //       correctly, though by inspecting the XML File.
        //       It would be great to get this working, as it could fix the problem of
        //		loading a model from another operating system.
        return false;
    }

    public static ModelPacket[] getModelPackets(Integer i) {
        return (getSingleton().packetTable.get(i));
    }

    public static boolean isUseAWTFileDialog() {
        return (Boolean) getSingleton().table.get(USE_AWT_FILEDIALOG);
    }

    public static boolean isRestoreDesktop() {
        return (Boolean) getSingleton().table.get(RESTORE_DESKTOP);
    }

    public static boolean isPostMessage() {
        return (Boolean) getSingleton().table.get(POST_MESSAGE);
    }

    public static Dimension getDesktopScreenSize(JFrame frame) {
        Dimension screenSize;
        Toolkit kit = Toolkit.getDefaultToolkit();
        screenSize = kit.getScreenSize();
        GraphicsConfiguration config = frame.getGraphicsConfiguration();
        Insets insets = kit.getScreenInsets(config);
        screenSize.width -= (insets.left + insets.right);
        screenSize.height -= (insets.top + insets.bottom);

        if (isRestoreDesktop()) {
            try {
                Integer[] dims = (Integer[]) getSingleton().table.get(DESKTOP_SIZE);
                if (dims[0] > 0 && dims[1] > 0) {
                    screenSize = new Dimension(dims[0], dims[1]);
                }
            } catch (Exception e) {
                Logging.log("Couldn't get desktop screen size: " + e);
            }
        }

        return screenSize;
    }

    public static Point getDesktopLocation(JFrame frame) {
        Point location;
        Toolkit kit = Toolkit.getDefaultToolkit();
        GraphicsConfiguration config = frame.getGraphicsConfiguration();
        Insets insets = kit.getScreenInsets(config);
        location = new Point(insets.left, insets.top);


        //LOGGING ONLY!
        {
            //Look at multiple monitors here.
            // Test if each monitor will support my app's window
            // Iterate through each monitor and see what size each is
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] gs = ge.getScreenDevices();
            ge.getDefaultScreenDevice();
            for (int i = 0; i < gs.length; i++) {
                DisplayMode dm = gs[i].getDisplayMode();
                Logging.log("Display Mode #" + i + "= " + dm.getWidth() + " x " + dm.getHeight() + " name=" + gs[i].getIDstring());
            }

        }

        if (isRestoreDesktop()) {
            Logging.log("Retrieving the window coordinates from last session");
            try {
                Integer[] dims = (Integer[]) getSingleton().table.get(DESKTOP_LOCATION);
                if (dims[0] != 0 || dims[1] != 0) {
                    location = new Point(dims[0], dims[1]);
                }
            } catch (Exception e) {
                Logging.log("Couldn't get desktop location: " + e);
            }
        }
        return location;
    }

    //TODO: Use javax.jnlp.FileSaveService:
    // http://docs.oracle.com/javase/1.5.0/docs/guide/javaws/jnlp/javax/jnlp/FileSaveService.html
    public static String getPreferencesFile() {
        if (null == preferencesFile) {
            try {
                preferencesFile = System.getProperty("user.home") + System.getProperty("file.separator");
            } catch (Exception e) {
                Logging.log("Could not load preferences");
                Logging.log(e);
            }
            preferencesFile += "userpref.po";
        }
        return preferencesFile;
    }


    //SETTERS
    public static void setButtonType(int newType) {
        getSingleton().table.put(BUTTON_TYPE, newType);

        //tell buttons to update look.
        getSingleton().notifyButtons();
    }

    public static void setDelayTime(long newTime) {
        getSingleton().table.put(DELAY_TIME, newTime);
    }

    public static void setUseAWTFileDialog(boolean newVal) {
        getSingleton().table.put(USE_AWT_FILEDIALOG, newVal);
    }

    public static void setDirectory(String newFile) {
        if (new File(newFile).isDirectory())
            getSingleton().table.put(DIRECTORY, newFile);
    }

    public static void setOwnershipBorderThickness(int newThickness) {
        getSingleton().table.put(BORDER_THICKNESS, newThickness);
    }

    public static void setTriggerType(int newVal) {
        getSingleton().table.put(TRIGGER_TYPE, newVal);
    }

    public static void setTerminusType(int newVal) {
        getSingleton().table.put(TERMINUS_TYPE, newVal);
    }

    public static void setHelpFileLocation(String newVal) {
        getSingleton().table.put(HELP_FILE_LOCATION, newVal);
    }

    public static void setHelpLanguage(String newVal) {
        getSingleton().table.put(HELP_FILE_LANGUAGE, newVal);
    }

    public static void setOpenPDFMethod(int openMethod) {
        getSingleton().table.put(OPEN_PDF_METHOD, openMethod);
    }

    public static void setOpenPDFCommand(String execStr) {
        getSingleton().table.put(OPEN_PDF_COMMAND, execStr);
    }

    public static void setOpenPDFObject(OpenPDFMethod opm) {
        setOpenPDFMethod(opm.getOpenMethod());
        setOpenPDFCommand(opm.getExecStr());
    }


    public static void setPreferencesFile(String filename) {
        preferencesFile = filename;
    }

    public static void setUseJFreeClass(boolean newVal) {
        getSingleton().table.put(USE_JFREECHART, newVal);
    }

    public static void setRestoreDesktop(boolean newVal) {
        getSingleton().table.put(RESTORE_DESKTOP, newVal);
    }

    public static void setPostMessage(boolean newVal) {
        getSingleton().table.put(POST_MESSAGE, newVal);
    }

    /**
     * Initializes the menus, or packetTable.
     * You need to modify this to add or remove a model from the menus.
     */
    private void initializeMenuPackets() {
        ModelPacket[] topModels, singleModels, multiModels, rcModels,
                discreteModels, hostModels, aidsModels, mendelianModels, selectionPopModels,
                quantitativeModels, spatialModels;

        topModels = new ModelPacket[]{
                new ModelPacket(edu.umn.ecology.populus.model.ie.IEModel.class),
        };

        singleModels = new ModelPacket[]{
                new ModelPacket(edu.umn.ecology.populus.model.dig.DIGModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.lpg.LPGModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.aspg.ASPGModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.sspg.SSPGModel.class),
        };

        multiModels = new ModelPacket[]{
                new ModelPacket(edu.umn.ecology.populus.model.lvc.LVCModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.lvpptl.LVPPTLModel.class),
        };

        rcModels = new ModelPacket[]{
                new ModelPacket(edu.umn.ecology.populus.model.rc.RCModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.rct.RCTModel.class),
        };

        discreteModels = new ModelPacket[]{
                new ModelPacket(edu.umn.ecology.populus.model.appdnb.APPDNBModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.nbss.NBSSModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.appdfr.APPDFRModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.appdnrs.APPDNRSModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.appdi.APPDIModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.appdtpr.APPDTPRModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.ihpi.IHPIModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.pp.PPModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.cp.CPModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.hph.HPHModel.class),

        };

        hostModels = new ModelPacket[]{
                new ModelPacket(edu.umn.ecology.populus.model.imd.IMDModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.md.MDModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.eov.EOVModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.bp.BPModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.tp.TPModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.hhap.HHAPModel.class),
        };
        aidsModels = new ModelPacket[]{
                new ModelPacket(edu.umn.ecology.populus.model.aidsbasic.AIDSBASICModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.aids.AIDSModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.aidst.AIDSTModel.class),
        };
        mendelianModels = new ModelPacket[]{
                new ModelPacket(edu.umn.ecology.populus.model.gdamcm.GDModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.inbreeding.InbreedingModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.ps.PSModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.das.DASModel.class)
        };

        selectionPopModels = new ModelPacket[]{
                new ModelPacket(edu.umn.ecology.populus.model.woozle.WoozleModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.sdal.SDALModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.soamal.SOAMALModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.sotl.SOTLModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.soasll.SOASLLModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.sam.SAMModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.interdgs.INTERDGSModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.intradgs.INTRADGSModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.kham.KHAMModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.hmss.HMSSModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.fdsdm.FDSDMModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.fdsess.FDSESSModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.ddsgv.DDSGVModel.class),
        };

        quantitativeModels = new ModelPacket[]{
                new ModelPacket(edu.umn.ecology.populus.model.paqg.PAQGModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.herit.HERITModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.dsoqc.DSOQCModel.class)
        };

        spatialModels = new ModelPacket[]{
                new ModelPacket(edu.umn.ecology.populus.model.prm.PRMModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.sgfac.SGFACModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.mnp.MNPModel.class),
                new ModelPacket(edu.umn.ecology.populus.model.sd.SDModel.class),
        };

        packetTable = new Hashtable<>();
        packetTable.put(TOP_PACKETS, topModels);
        packetTable.put(SINGLE_PACKETS, singleModels);
        packetTable.put(MULTI_PACKETS, multiModels);
        packetTable.put(DISCRETE_PACKETS, discreteModels);
        packetTable.put(MENDEL_PACKETS, mendelianModels);
        packetTable.put(SELECTION_PACKETS, selectionPopModels);
        packetTable.put(SPATIAL_PACKETS, spatialModels);
        packetTable.put(QUANT_PACKETS, quantitativeModels);
        packetTable.put(HOST_PACKETS, hostModels);
        packetTable.put(RC_PACKETS, rcModels);
        packetTable.put(AIDS_PACKETS, aidsModels);
    }

    /**
     * Saves the Hashtable to file.
     */
    public synchronized void save() {
        //TODO:  How does ColorSaver and ValueSaver work?  It looks like we just save default
        //values here and the user's real color preferences do not get saved (or loaded).
        //But this isn't high priority to fix.
        table.put(COLOR_SAVER, new ColorSaver());
        table.put(VALUE_SAVER, new ValuesToSave());

        if (isRestoreDesktop()) {
            Dimension dSize = DesktopWindow.defaultWindow.getSize();
            Point pLocation = DesktopWindow.defaultWindow.getLocation();
            table.put(DESKTOP_SIZE, new Integer[]{dSize.width, dSize.height});
            table.put(DESKTOP_LOCATION, new Integer[]{pLocation.x, pLocation.y});
        }

        try {
            XMLEncoder e = new XMLEncoder(
                    new BufferedOutputStream(
                            new FileOutputStream(getPreferencesFile())));
            //Write a simple string header so that someone looking at the file could make sense of it.
            e.writeObject("This is a configuration file for Populus.");
            //Write all of the values.
            e.writeObject(table);
            e.flush();
            e.close();
            Logging.log("Saved preferences to " + getPreferencesFile());
        } catch (Exception e) {
            Logging.log("Unable to save file to " + getPreferencesFile() + ": " + e);
        }
    }

    /**
     * Loads the Hashtable from file
     * <p>
     * TODO:  Does this get called??
     */
    public synchronized void load() {
        subLoad(false);
    }

    /**
     * Loads the Hashtable from file, called by load().
     *
     * @param isInit When true, we don't compare with the existing data to see if we need to update
     *               anything, e.g. the button look.
     *               <p>
     *               TODO: use javax.jnlp.FileOpenService here or javax.jnlp.PersistenceService
     *               http://docs.oracle.com/javase/1.5.0/docs/guide/javaws/jnlp/javax/jnlp/FileOpenService.html
     */
    private void subLoad(boolean isInit) {
        try {
            int oldButtonType = INVALID, newButtonType = INVALID;
            if (!isInit) {
                oldButtonType = (table != null) ? getButtonType() : INVALID;
            }

            Hashtable<Integer, Object> tableOverrides; //Override values that we loaded
            try {
                //Try to use XML first
                Logging.log("Trying to use XML to read preferences");
                XMLDecoder d = new XMLDecoder(new BufferedInputStream(
                        new FileInputStream(getPreferencesFile())));
                Object o = d.readObject();
                if (o instanceof String) {
                    //Ignore description String of file, if it exists.
                    o = d.readObject();
                }
                tableOverrides = (Hashtable<Integer, Object>) o;
                d.close();
                Logging.log("Got preferences using XML format");
            } catch (Exception e) {
                //XML read failed, now try binary.
                Logging.log("Trying to use XML to read preferences, as XML failed");
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getPreferencesFile()));
                tableOverrides = (Hashtable<Integer, Object>) ois.readObject();
                ois.close();
                Logging.log("Loaded preferences using binary format");
            }
            Iterator<Map.Entry<Integer, Object>> it = tableOverrides.entrySet().iterator();
            while (it.hasNext()) {
                //For each override element, only replace if the key is in the original table and the values are the same type
                while (it.hasNext()) {
                    Map.Entry<Integer, Object> pair = it.next();
                    int k = pair.getKey();
                    Object v = pair.getValue();
                    if (table.containsKey(k)) {
                        if (table.get(k).getClass().equals(v.getClass())) {
                            String oldVal = table.get(k).toString();
                            String newVal = v.toString();
                            boolean isSameVal = table.get(k).equals(v);
                            if (v instanceof Object[]) {
                                //Arrays are annoying in Java, need to do special actions since the default toString() and equals() suck.
                                isSameVal = Arrays.equals((Object[]) v, (Object[]) table.get(k));
                                oldVal = Arrays.deepToString((Object[]) table.get(k));
                                newVal = Arrays.deepToString((Object[]) v);
                            }

                            if (isSameVal) {
                                //Same value, no need to override...
                                Logging.log("Preferences: Preserving key " + k + " with value " + oldVal, Logging.kInfo);
                            } else {
                                Logging.log("Preferences: Overriding key " + k + " from " + oldVal + " to " + newVal, Logging.kInfo);
                                table.put(k, v);
                            }
                        } else {
                            Logging.log("Preferences: Ignoring mismatched type of key " + k + " :  " + table.get(k).getClass() + " != " + v.getClass(), Logging.kInfo);
                        }
                    }
                }
            }

            if (!isInit) {
                //check if button type changed (if so, tell buttons)
                newButtonType = getButtonType();
                if ((oldButtonType != newButtonType) && (oldButtonType != INVALID)) {
                    notifyButtons();
                }
            }
            ColorSaver cs = (ColorSaver) table.get(COLOR_SAVER);
            if (cs != null) {
                cs.setColors();
            }
            ValuesToSave vs = (ValuesToSave) table.get(VALUE_SAVER);
            if (vs != null) {
                vs.setValues();
            }
        } catch (Exception e) {
            Logging.log("Failed to get Populus Preferences, will use default values.");
            Logging.log(e);
            reset(isInit);
        }
    }

    public void reset() {
        reset(false);
    }

    /**
     * Initiates the Hashtable and adds the standard set of values
     *
     * @param isInit When true, we don't compare with the existing data to see if we need to update
     *               anything, e.g. the button look.
     */
    protected synchronized void reset(boolean isInit) {
        int oldButtonType, newButtonType;
        oldButtonType = (table != null && !isInit) ? getButtonType() : INVALID;
        table = new Hashtable<>();
        table.put(BUTTON_TYPE, DEFAULT_BUTTON_TYPE);
        table.put(DIRECTORY, DEFAULT_DIRECTORY);
        table.put(DELAY_TIME, DEFAULT_DELAY);
        table.put(TRIGGER_TYPE, DEFAULT_TRIGGER);
        table.put(USE_AWT_FILEDIALOG, Boolean.FALSE);
        ValuesToSave.setDefaults();
        ColorSaver.setDefaults();
        table.put(COLOR_SAVER, new ColorSaver());
        table.put(VALUE_SAVER, new ValuesToSave());
        table.put(BORDER_THICKNESS, 5);
        table.put(TABLE_EDIT_COLOR, Color.yellow);
        table.put(TABLE_UNEDIT_COLOR, Color.white);
        table.put(TERMINUS_TYPE, kDEFAULTTERMINI);
        table.put(HELP_FILE_LOCATION, DEFAULT_HELP_FILE);
        table.put(HELP_FILE_LANGUAGE, DEFAULT_HELP_LANG);
        table.put(OPEN_PDF_METHOD, OpenPDFMethod.DEFAULT_METHOD.getOpenMethod());
        table.put(OPEN_PDF_COMMAND, OpenPDFMethod.DEFAULT_METHOD.getExecStr());
        table.put(RESTORE_DESKTOP, DEFAULT_RESTORE_DESKTOP);
        table.put(DESKTOP_SIZE, new Integer[]{0, 0});
        table.put(DESKTOP_LOCATION, new Integer[]{0, 0});
        table.put(USE_JFREECHART, DEFAULT_USE_JFREECHART);
        table.put(POST_MESSAGE, DEFAULT_POST_MESSAGE);

        //check if button type changed (if so, tell buttons)
        if (!isInit) {
            newButtonType = getButtonType();
            if ((oldButtonType != newButtonType) && (oldButtonType != INVALID)) {
                notifyButtons();
            }
        }
    }

    /**
     * Tells buttons that the look has changed.
     * TODO efficiency - This has a memory leak, since we always
     * keep a reference of all the buttons -- maybe
     * even when their model is long gone.
     */
    private void notifyButtons() {
        buttons.stream().forEach(button -> button.setLook());

		/*
		PopulusToolButton button;
		Enumeration<PopulusToolButton> e = buttons.elements();
		//edu.umn.ecology.populus.fileio.Logging.log("There are " + buttons.size() + " buttons."); //Lars
		while( e.hasMoreElements() ) {
			button = e.nextElement();
			button.setLook();
		}

		 */
    }
}
