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
import edu.umn.ecology.populus.constants.*;
import edu.umn.ecology.populus.fileio.Logging;
import edu.umn.ecology.populus.help.HelpUtilities;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.lang.reflect.*;
import java.net.URI;
import java.util.*;
import javax.swing.*;

/**
 * this class is quite messy, but possibly justifiably so because it is difficult
 * to permanently delete something that could very easily become useful, either as
 * a guide or use again.
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: University of Minnesota</p>
 * @author Lars Roe, Amos Anderson
 * @version 5.2
 */
public class DesktopWindow extends JFrame implements ModelListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1729866105545816729L;
	public static DesktopWindow defaultWindow;
	public static final String BACKGROUND_IMAGE_FILE = "Populus5.5SplashScreen.png";
	BorderLayout borderLayout1 = new BorderLayout();

	/**
	 * When <code>lock</code> is null, there is no lock.
	 * Otherwise, the Model it points to is the locked model.
	 */
	Model lock = null;
	JPanel switcherPanel = new JPanel();
	NewModelAction newModelAction = new NewModelAction( this );
	JDesktopPane desktopPane = new JDesktopPane();
	JLabel imagePanel = new JLabel();
	JToolBar toolBar = new JToolBar();
	JButton newButton;

	//Action variables
	ExitAction exitAction = new ExitAction( this );
	JButton helpButton;

	//Core ("Useful") variables
	Vector<Model> models = new Vector<Model>( 1, 1 );
	JButton optionsButton;
	JPanel backgroundPanel = new JPanel();
	JButton quitButton;
	GridBagLayout gridBagLayout1 = new GridBagLayout();
	ButtonGroup bg1 = new ButtonGroup();
	boolean imageBackgroundIsShowing = true;

	/*2 menu variables*/
	JPopupMenu topLevelMenu = new JPopupMenu();
	ModelPacket[] mps = null;

	CardLayout cardLayout1 = new CardLayout( 0, 0 );
	static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.core.Res" );

	public void jbInit() throws Exception, Error {
		defaultWindow = this;
		this.getContentPane().setLayout( borderLayout1 );
		System.out.print( "." );
		desktopPane.putClientProperty( "JDesktopPane.dragMode", "outline" );
		newButton = PopulusToolButton.createNewButton();
		helpButton = PopulusToolButton.createMainHelpButton();
		optionsButton = PopulusToolButton.createPreferencesButton();
		//install help
		HelpUtilities.createHelp().installComponentHelp( helpButton, "Main" );
		quitButton = PopulusToolButton.createQuitButton();
		quitButton.addActionListener( exitAction );
		newButton.addActionListener( newModelAction );
		optionsButton.addActionListener( new java.awt.event.ActionListener()  {

			public void actionPerformed( ActionEvent e ) {
				optionsButton_actionPerformed( e );
			}
		} );
		System.out.print( "." );

		backgroundPanel.setLayout( gridBagLayout1 );
		setPopulusBackground();
		switcherPanel.setLayout( cardLayout1 );
		this.setEnabled( true );
		toolBar.setFloatable( false );
		toolBar.add( newButton );
		toolBar.add( optionsButton );
		toolBar.add( helpButton );
		toolBar.add( quitButton );
		this.getContentPane().add( toolBar, BorderLayout.NORTH );
		this.getContentPane().add( switcherPanel, BorderLayout.CENTER );
		switcherPanel.add( backgroundPanel, "backgroundPanel" );
		backgroundPanel.add( imagePanel, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		switcherPanel.add( desktopPane, "desktopPane" );
		this.setIconImage( Toolkit.getDefaultToolkit().getImage( DesktopWindow.class.getResource( "picon.gif" ) ) );

		MenuAction ma;
		mps = PopPreferences.getModelPackets(PopPreferences.TOP_PACKETS);
		for( int i = 0;i < mps.length;i++ ) {
			ma = new MenuAction( mps[i].getModelName(), mps[i].getModelClass() )  {

				/**
				 * 
				 */
				private static final long serialVersionUID = -2658804970844768852L;

				public void actionPerformed( ActionEvent e ) {
					loadModel( model );
				}
			};
			topLevelMenu.add( ma );
		}

		JMenuItem jmi = topLevelMenu.add( res.getString( "Load_Model_From_File_" ) );
		jmi.addActionListener( new java.awt.event.ActionListener()  {

			public void actionPerformed( ActionEvent e ) {
				newModelFromModelChooser( Model.load() );
			}
		} );

		topLevelMenu.add(loadMenu(PopPreferences.getModelPackets(PopPreferences.SINGLE_PACKETS),"Single-Species Dynamics:"),0);

		JMenu jm = loadMenu(PopPreferences.getModelPackets(PopPreferences.MULTI_PACKETS),"Multi-Species Dynamics:");
		jm.add(loadMenu(PopPreferences.getModelPackets(PopPreferences.RC_PACKETS),"Resource Competition:"),1);
		jm.add(loadMenu(PopPreferences.getModelPackets(PopPreferences.DISCRETE_PACKETS),"Discrete Predator-Prey Models:"),2);
		//just added 7/20 3->4
		//jm.add(loadMenu(PopPreferences.getModelPackets(PopPreferences.HOST_PACKETS),"Host-Parasite Models:"),3);
		JMenu aid = loadMenu(PopPreferences.getModelPackets(PopPreferences.HOST_PACKETS),"Host-Parasite Models:");
		aid.add(loadMenu(PopPreferences.getModelPackets(PopPreferences.AIDS_PACKETS),"AIDS Models:"),6);
		topLevelMenu.add( jm, 1 );
		jm.add(aid,1);
		topLevelMenu.add(loadMenu(PopPreferences.getModelPackets(PopPreferences.MENDEL_PACKETS),"Mendelian Genetics:"),2);
		topLevelMenu.add(loadMenu(PopPreferences.getModelPackets(PopPreferences.SELECTION_PACKETS),"Natural Selection:"), 3 );
		topLevelMenu.add(loadMenu(PopPreferences.getModelPackets(PopPreferences.QUANT_PACKETS),"Quantitative-Genetic Models:"),4);
		topLevelMenu.add(loadMenu(PopPreferences.getModelPackets(PopPreferences.SPATIAL_PACKETS),"Spatial Models:"),5);

		System.out.print( ".\n" );
		sizeScreen(this);
		this.setTitle( res.getString( "Populus" ) );
		this.setVisible( true );
	}

	/** Size the window to full size, making sure to account for the insets */
	private static void sizeScreen(JFrame frame) {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		GraphicsConfiguration config = frame.getGraphicsConfiguration();
		Insets insets = kit.getScreenInsets(config);
		screenSize.width -= (insets.left + insets.right);
		screenSize.height -= (insets.top + insets.bottom);
		frame.setSize(screenSize);
		frame.setLocation(insets.left, insets.top);
	}

	public DesktopWindow() {
		//enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		enableEvents( AWTEvent.WINDOW_EVENT_MASK & AWTEvent.ACTION_EVENT_MASK );

		try {
			jbInit();
		}
		catch(Error e){
			e.printStackTrace();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

	/**
	 * This is called when a model changes. <BR>
	 * If one model already declared it is sending stuff to output,
	 * and hasn't signalled it is done, a lock is active. If any other
	 * model tries to put stuff to output, a CannotChangeModelException
	 * is thrown.
	 */

	public synchronized void modelChanged( ModelEvent e ) throws CannotChangeModelException {
		int eventType = e.getType();
		Model changedModel = e.getModel();

		//If event is sending to output, then
		//check lock: if locked then throw a CannotChangeModelException
		//exception, and possibly show the error to user
		if( eventType == ModelEvent.OUTPUT_UPDATE_BEGIN ) {
			//already locked?
			if( lock != null ) {
				//cannot send to output
				throw new CannotChangeModelException( CannotChangeModelException.OTHER_MODEL_RUNNING );
			}
			else {
				//Set lock if sending to output - possibly have status bar or SplashScreen
				setLock( changedModel );
			}
		}
		//Remove lock if done sending to output
		if( eventType == ModelEvent.OUTPUT_UPDATE_END ) {
			setLock( null );
		}

		//If model is killed, then delete all references to it
		if( e.getType() == ModelEvent.KILL ) {
			changedModel.removeModelListener( this );
			models.remove( changedModel );
		}
	}

	protected void processWindowEvent( WindowEvent e ) {
		super.processWindowEvent( e );
		if( e.getID() == WindowEvent.WINDOW_CLOSING ) {
			exit();
		}
	}

	void exit() {
		//TODO - this should be cleaner...
		PopPreferences.getSingleton().save();
		Logging.cleanup();
		System.exit( 0 );
	}

	void help() {
		//new HelpDialog();
		//new NotSupportedDialog(this);
	}

	/**
	 * this method sets what the background looks like when Populus is first started up.
	 * Modify this for difference colors etc.
	 */
	void setPopulusBackground(){ 
		imagePanel.setBackground( Color.white );
		//imagePanel.setBackground(new Color(102,102,109));
		//imagePanel.setBackground(Color.black);
		imagePanel.setHorizontalAlignment( SwingConstants.CENTER );
		imagePanel.setHorizontalTextPosition( SwingConstants.CENTER );
		//backgroundPanel.setBackground( Color.white );
		//backgroundPanel.setBackground(new Color(0,0,152));
		backgroundPanel.setBackground(new Color(0,0,206));
		//backgroundPanel.setBackground(Color.black);
		imagePanel.setIcon( new ImageIcon( Toolkit.getDefaultToolkit().getImage( DesktopWindow.class.getResource( BACKGROUND_IMAGE_FILE ) ) ) );
	}

	void newModel( ActionEvent e ) {
		topLevelMenu.show( newButton, 0, 30 );
	}

	/**
	 * this method will add all the ModelPacket elements to a JMenu, which it returns.
	 * this is used to build the menu.
	 * @param mp
	 * @param name
	 * @return
	 */
	JMenu loadMenu(ModelPacket[] mp, String name){
		JMenu jm = new JMenu(name);
		MenuAction ma;
		for( int i = 0;i < mp.length;i++ ) {
			ma = new MenuAction( mp[i].getModelName(), mp[i].getModelClass() )  {

				/**
				 * 
				 */
				private static final long serialVersionUID = 6448584135280919807L;

				public void actionPerformed( ActionEvent e ) {
					loadModel( model );
				}
			};
			jm.add( ma );
		}
		return jm;
	}

	void bringAllToFront() {
		//check all windows created, and put them all to front
		this.toFront();
	}

	void imagesAndText() {
		PopPreferences.setButtonType( PopPreferences.TEXT | PopPreferences.IMAGES );
	}

	void aboutMenuItem_actionPerformed() {
		new AboutPopulusDialog( this );
	}

	void genericSetModel( Model model ) {
		if( model == null ) {
			return ;
		}
		removeImagePanel();
		model.setDesktopPane( desktopPane );
		model.selectInputWindow();
		model.addModelListener( this );
		models.add( model );
	}

	void newModelFromModelChooser( Model model ) {
		genericSetModel( model );
	}

	void topLevel_actionPerformed( ActionEvent e ) {
		String command = e.getActionCommand();
		for( int i = 0;i < mps.length;i++ ) {
			if( mps[i].getModelName() == command ) {
				loadModel( mps[i].getModelClass() );
				return ;

				//break;
			}
		}
		if( command == res.getString( "Load_Model_From_File_" ) ) {
			newModelFromModelChooser( Model.load() );
		}
	}

	/**
	 * Checkbox is clicked so that title screen should be removed.
	 */
	void seeAgainBox_actionPerformed( ActionEvent e ) {
		/*
   if (this.seeAgainBox.isSelected()) {
   removeImagePanel();
   }
		 */
	}

	void blankToolbar() {
		PopPreferences.setButtonType( 0 );
	}

	void optionsButton_actionPerformed( ActionEvent e ) {
		PreferencesDialog.bringUpDialog( this );
	}

	void imagesOnly() {
		PopPreferences.setButtonType( PopPreferences.IMAGES );
	}

	/**
	 * Removes the background screen that shows "Populus ..."
	 * Also sets a boolean variable so it won't repeat the process
	 * if called a second time.
	 */

	void removeImagePanel() {
		if( imageBackgroundIsShowing ) {

			//Do I need to make sure the image is destroyed?
			cardLayout1.last( this.switcherPanel );
			this.switcherPanel.remove( backgroundPanel );
			backgroundPanel = null;
			imageBackgroundIsShowing = false;
		}
	}

	private void loadModel( Class c ) {
		try {
			java.lang.reflect.Constructor cxr;
			cxr = (java.lang.reflect.Constructor)c.getConstructor( null );
			Model m = (Model)c.getConstructor( null ).newInstance( null );
			ColorScheme.addModel( m );
			newModelFromModelChooser( m );
		}
		catch( InvocationTargetException exception ) {
			System.err.println( "ITException" );
			System.err.println( exception );
			System.err.println( "Error message:" );
			System.err.println( exception.getTargetException() );
			exception.printStackTrace();
		}
		catch( Exception e2 ) {
			System.err.println( "Failed to create model" );
			System.err.println( e2 );
		}
	}

	private synchronized void setLock( Model m ) {
		lock = m;
		if( m == null ) {
			//End lock
			lock = m;
			//this.statusBar.setText(" ");
			//this.statusBar.revalidate();
		}
		else {
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
	DesktopWindow dw;

	public void actionPerformed( ActionEvent event ) {
		dw.exit();
	}

	ExitAction( DesktopWindow w ) {
		this.dw = w;
	}
}

abstract class MenuAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5106840855809036163L;
	final Class model;

	MenuAction( String text, Class modelToRun ) {
		super( text );
		model = modelToRun;
	}
}

class NewModelAction implements java.awt.event.ActionListener {
	DesktopWindow adaptee;

	public void actionPerformed( ActionEvent e ) {
		adaptee.newModel( e );
	}

	NewModelAction( DesktopWindow adaptee ) {
		this.adaptee = adaptee;
	}
}
