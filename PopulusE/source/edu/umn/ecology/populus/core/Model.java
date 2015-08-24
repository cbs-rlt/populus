/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.core;
import edu.umn.ecology.populus.constants.*;
import edu.umn.ecology.populus.edwin.*;
import edu.umn.ecology.populus.help.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.resultwindow.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import edu.umn.ecology.populus.fileio.Logging;
import java.awt.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.*;

import javax.swing.*;

public abstract class Model extends Object implements ParameterFieldListener,ModelPanelListener,OutputPanelListener,Externalizable, Serializable {
	protected transient Vector<ModelListener> listeners;
	protected ModelOutputFrame outputFrame = null;
	private static final long serialVersionUID = -1289515988237952007L;

	//instance variables that are expected to be modified by model subclasses
	private ModelPanel modelInput = null;
	private int standardHeight;
	private Color modelColor;
	private Rectangle originalBounds;
	private boolean positionBoolean;

	/** Transient so that it won't be written during serialization */
	private transient JDesktopPane desktopPane;
	private OutputPanel modelOutput = null;

	//protected static String modelName = "Unnamed Model";

	//protected static Object modelHelpText = "No help available";

	//basic instance variables that shouldn't be worried about
	private int replotTriggers = 0;
	private ModelInputFrame inputFrame = null;

	/**Used for changing where windows show up*/
	private static Alternator alternator = new Alternator();
	private static int colorNumber = 0;

	public OutputPanel getModelOutput() {
		return this.modelOutput;
	}

	public boolean get3D() {
		if( modelOutput != null ) {
			return ( modelOutput.getType() == OutputTypes.k3D );
		}
		else {
			return false;
		}
	}

	public int getReplotTriggers() {
		return this.replotTriggers;
	}

	public void setReplotTriggers( int newValue ) {
		this.replotTriggers = newValue;
	}

	public ModelPanel getModelInput() {
		return this.modelInput;
	}

	/**
	 * If possible, try to make this model's input frame selected
	 */
	public void selectInputWindow() {
		try {
			inputFrame.setSelected( true );
		}
		catch( java.beans.PropertyVetoException pve ) {

		}
	}

	/**
	 * This returns help for the specific model. It could be of type String.
	 * But later on, it could be a JComponent, or something similar.
	 * TODO - what is this??
	 */
	public Object getModelHelpText() {
		return "NONE";
	}

	//TODO - What is this???
	public void help() {
		//new HelpDialog(this);
	}

	public void setDesktopPane( JDesktopPane pane ) {

		//although excessive, be sure to check old pane was null...
		pane.add( inputFrame, new Integer( 1 ) );
		inputFrame.setVisible( true );
		outputFrame.setVisible( false );
		inputFrame.pack();
		pane.add( outputFrame, new Integer( 1 ) );
		desktopPane = pane;

		//This part here sets up the placement of the windows
		Rectangle r = desktopPane.getBounds();
		Rectangle s = inputFrame.getBounds();

		int xstart, ystart;
		xstart = s.width;
		if( ( xstart * 2 ) > r.width ) {
			xstart = r.width / 2;
		}

		//Get the next position of frames - top or bottom
		positionBoolean = alternator.getNextPosition();
		if( positionBoolean ) {

			//Top. Note that the inputFrame is automatically in upper-left corner
			outputFrame.setBounds( r.x + xstart, r.y, r.width - xstart, r.height / 2 );
		}
		else {

			//Bottom
			ystart = r.height - s.height;
			inputFrame.setLocation( r.x, r.y + ystart );
			outputFrame.setBounds( r.x + xstart, r.y + r.height / 2, r.width - xstart, r.height / 2 );
		}
		originalBounds = outputFrame.getBounds();
		standardHeight = outputFrame.getHeight();
	}

	public void modelPanelChanged( ModelPanelEvent e ) {
		if (e.getType() == ModelPanelEvent.REPACK) {
			inputFrame.pack();
		}
		else {
			switch (PopPreferencesStorage.getTriggerType()) {
			case PopPreferencesStorage.ALL_TRIGGER:
				updateOutput();
				break;
			case PopPreferencesStorage.DEFAULT_TRIGGER:
				if( e.isMandatory() || ( ( e.getType() & ( (ModelPanel)e.getSource() ).getTriggers() ) != 0 ) ) {
					updateOutput();
				}
				break;
			case PopPreferencesStorage.NO_TRIGGER:
				break;
			}
		}
	}

	public void setModelInput( ModelPanel newValue ) {
		if( modelInput != null ) {
			inputFrame.remove( modelInput );
			modelInput.destroy();
		}
		this.modelInput = newValue;
		inputFrame.getContentPane().add( modelInput );
		inputFrame.revalidate();
		modelInput.addModelPanelListener( this );
	}

	public void updateOutput(boolean isIterate){
		modelInput.willIterate(isIterate);
		updateOutput();
	}
	
	/**
	 * Transfers data from input screen to output screen. This should not be changed.
	 * Only modify simpleUpdateOutput, if necessary to do so.
	 */
	public void updateOutput() {
		try {

			//This makes sure the DesktopWindow "consents" to the update.

			//The DesktopWindow will throw an exception if it is not ready to process

			// an output update.
			fireModelEvent( new ModelEvent( this, ModelEvent.OUTPUT_UPDATE_BEGIN ) );
		}
		catch( CannotChangeModelException e ) {
			return ;
		}

		//ensure outputwindow is visible

		//this has been overridden, this must be taken care of if called
		if( !outputFrame.isVisible() ) {


			//outputFrame.setVisible(true);
		}
		try {
			simpleUpdateOutput();
		}
		catch( BadUserException bue ) {
			setModelOutput( new BadUserOutputPanel( bue ) );
			outputFrame.setVisible(true);
			//return ;
		}
		outputFrame.updateTitle();
		try {
			fireModelEvent( new ModelEvent( this, ModelEvent.OUTPUT_UPDATE_END ) );
		}
		catch( CannotChangeModelException e ) {


			//Don't know why an exception would need to be thrown.
		}
	}

	/**
	 * Returns a String which will be displayed on the title of the internal output frame
	 */

	public String getThisModelOutputName() {
		String retVal = this.getModelInput().getOutputGraphName();
		if( retVal == null ) {
			return getThisModelName();
		}
		else {
			return retVal;
		}
	}

	public void setModelOutput( OutputPanel newValue ) {
		boolean newModel = false;
		if( modelOutput != null ) {
			newModel = true;
			outputFrame.getContentPane().remove( modelOutput );
			modelOutput.destroy();
		}
		this.modelOutput = newValue;

		//3d graphs like a little more screen-space
		if( modelOutput.getType() == OutputTypes.k3D ) {
			outputFrame.setBounds( outputFrame.getX(), 0, outputFrame.getWidth(), standardHeight + 200 );
		} else {
			if( newModel ) {
				outputFrame.setBounds( originalBounds );
			}
		}
		outputFrame.getContentPane().add( modelOutput );
		outputFrame.revalidate();
		modelOutput.addOutputPanelListener( this );
	}

	public void writeExternal( ObjectOutput oo ) throws IOException {
		oo.writeObject( this.modelInput );
	}
	public void readExternal( ObjectInput oi ) throws ClassNotFoundException,IOException {
		this.basicInitModel();
		this.setModelInput( (ModelPanel)oi.readObject() );
	}


	public int getType() {
		if( modelOutput != null ) {
			return modelOutput.getType();
		}
		return -1;
	}

	public Model() {
		basicInitModel();
	}

	public void removeModelListener( ModelListener l ) {
		listeners.removeElement( l );
	}

	public java.awt.Color getModelColor() {
		return modelColor;
	}

	/**
	 * If this triggers an output update, it will call updateOutput
	 * @param e edu.umn.ecology.populus.visual.ppfield.ParameterFieldEvent
	 */

	public void parameterFieldChanged( edu.umn.ecology.populus.visual.ppfield.ParameterFieldEvent e ) {

		//Is it significant?
		if( ( e.getType() & this.getReplotTriggers() ) != 0 ) {

			//Let output update
			updateOutput();
		}
	}

	public void setModelColor( java.awt.Color newModelColor ) {
		modelColor = newModelColor;
	}

	public void outputChangeRequested( OutputPanelEvent e ) {

	}

	public String getThisModelName() {
		String s;
		try {
			s = (String)this.getClass().getMethod( "getModelName", new Class[0] ).invoke( null, null );
		}
		catch( Exception e ) {
			s = "ERROR: " + e;
		}
		return s;
	}

	/**
	 * Returns a String which will be displayed on the title of the internal input frame
	 */

	public String getThisModelInputName() {
		return getThisModelName();
	}

	// EVENT

	// METHODS

	public void addModelListener( ModelListener l ) {
		listeners.addElement( l );
	}

	/*
	 * BASIC GETTERS AND SETTERS
	 */

	public static String getModelName() {
		return "NONE";
	}

	/**
	 * Returns a String resource (file). Expects a filename for argument
	 */

	public static String getStringResource( java.net.URL filename ) {
		String str;
		try {
			ObjectInputStream ois;
			ois = new ObjectInputStream( new FileInputStream( filename.getFile() ) );
			str = (String)ois.readObject();
			ois.close();
		}
		catch( Exception e ) {
			str = "Help file could not load.\n\n" + " *Error Type= " + e.getClass();
		}
		return str;
	}

	//
	//The next methods are all loading methods
	//

	/**
	 * Recovers a <code>Model</code> by using serialization.
	 * <code>saveModelBinary()</code> produces a file for which to do this.
	 * This probably won't be overridden in inherited classes. Use
	 * <code>readExternal</code> to change to the loading implementation.
	 */
	public static final Model load() {
		Model retValue = null;
		try {
			String filename = edu.umn.ecology.populus.fileio.IOUtility.getFileName( "model1.po", "Load Model", FileDialog.LOAD );
			if( filename != null ) {
				if (PopPreferencesStorage.getUseXMLSave()) {
					XMLDecoder d = new XMLDecoder(new BufferedInputStream(
							new FileInputStream(filename)));
					retValue = (Model)d.readObject();
					d.close();
				} else {
					ObjectInputStream ois = new ObjectInputStream( new FileInputStream( filename ) );
					retValue = (Model)ois.readObject();
					ois.close();
				}
			}
		}
		catch( FileNotFoundException e ) {
			JOptionPane.showMessageDialog( DesktopWindow.defaultWindow, "File not found", "Error", JOptionPane.ERROR_MESSAGE );
			Logging.log(e);
		}
		catch( IOException e ) {
			String msg;
			if( e instanceof InvalidClassException ) {
				msg = "Could not load file: " + "File is not recognized as a saved model.\n" + "Be sure the file was not created with a different version of Populus.";
			}
			else {
				msg = "Could not load file: IOError";
			}
			JOptionPane.showMessageDialog( DesktopWindow.defaultWindow, msg, "Error", JOptionPane.ERROR_MESSAGE );
			Logging.log(e);
		}
		catch( ClassNotFoundException e ) {
			JOptionPane.showMessageDialog( DesktopWindow.defaultWindow, "Error reading file; Possibly bad file format", "Error", JOptionPane.ERROR_MESSAGE );
			Logging.log(e);
		}
		return retValue;
	}

	//*/

	/**
	 * This is called by updateOutput() to do the essentials. (updateOutput
	 * does all of the other stuff to get ready for the actual output).
	 * NOTE: Should this be declared abstract, to force the inherited models to override?
	 */
	protected void simpleUpdateOutput() {

	}

	/**
	 * Closing the input window has been requested (by input frame)
	 */

	protected void closeInput() {
		delete();
	}

	/**
	 * Closing the output window has been requested (by output frame)
	 */

	protected void closeOutput() {
		outputFrame.setVisible( false );

		//do anything else? save memory? maybe later...
	}

	/**
	 * Saves output in excel format.
	 * Not implemented yet.
	 */

	protected void saveOutputExcel() {
		System.err.println( "Not Implemented Yet" );
	}

	/**
     used if the model requires lengthy calculations but has 2 different types
     of outputs (i.e. 2D and cellular). will change the output without having
     to recalculate everything
	 */

	protected boolean isSwitchable() {
		return false;
	}

	/**
     The overridden method will want to make the outputFrame visible
     This will return a ParamInfo class.
	 */

	protected abstract ParamInfo getData();

	protected void getOptions( int command ) {
		modelOutput.showOptions( command );
	}

	protected String getHelpId() {
		return "top";
	}


	/**
	 * Call this to easily notify an event
	 */

	protected void fireModelEvent( ModelEvent event ) throws CannotChangeModelException {
		Enumeration<ModelListener> enm = listeners.elements();
		while( enm.hasMoreElements() ) {
			( (ModelListener)enm.nextElement() ).modelChanged( event );
		}
	}

	/**
	 * This returns true iff this Model is capable of producing different
	 * outputs for the same input parameters. For example, Genetic Drift Model
	 * would return true, whereas most other Models would return false.
	 * This default method always returns false.
	 * Essentially, the only thing it does is puts an "update" button on the
	 * output screen, where it has the same functionality as the one on the input
	 * screen.
	 */

	protected boolean isRepeatable() {
		return false;
	}

	/**
	 * This attempts to delete the model.<br>
	 * Call this to <em>request<em> (not guarantee) a delete, because it will notify its listeners,
	 *  who may throw an exception to prevent the deletion.
	 */

	protected void delete() {
		try {
			this.fireModelEvent( new ModelEvent( this, ModelEvent.KILL ) );
			ColorScheme.removeModel( this );
		}
		catch( CannotChangeModelException e ) {

			//DesktopWindow refused Model to close
			return ;
		}
		listeners = null;
		alternator.modelDeleted( positionBoolean );

		//let inherited versions of delete take care of the modelInput/modelOutput

		/*
      if (modelInput != null) {
      //destroy modelInput
      ;
      }
      if (modelOutput != null) {
      //destroy modelOutput
      ;
      }
		 */
		try {
			inputFrame.setVisible( false );
			desktopPane.remove( inputFrame );
			inputFrame.dispose();
		}
		catch( NullPointerException npe ) {

		}
		try {
			outputFrame.setVisible( false );
			modelOutput.destroy();
			desktopPane.remove( outputFrame );
			outputFrame.dispose();
		}
		catch( NullPointerException npe ) {

		}
	}

	protected void switchOutput() {

	}

	/**
	 * Right now, it doesn't do anything. It should be overwritten
	 * if useful info wants to be passed in.
	 */

	protected void saveOutputText( OutputStreamWriter osw ) throws IOException {
		osw.write( "Write not available for this model" );
	}

	/**
	 * Prints what is in the input screen to a printer
	 */

	void printInputGraphics() {
		Frame f = DesktopWindow.defaultWindow;
		edu.umn.ecology.populus.visual.PrintUtilities.printComponent( modelInput, f );
	}

	/**
	 * Prints what is in the output screen to a printer
	 */

	void printOutputGraphics() {
		Frame f = DesktopWindow.defaultWindow;
		edu.umn.ecology.populus.visual.PrintUtilities.printComponent( modelOutput, f );
	}

	//////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////

	// I/O METHODS:                                             //

	// SAVING TO FILE, PRINTING, LOADING FROM FILE              //

	//////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////

	//

	//The next methods are all for particular saving methods

	//

	/** Saves the model */

	void save() {
		switch( SaveDialog.getSaveChoice( false ) ) {
		case SaveDialog.CANCELLED:
			break;

		case SaveDialog.MODEL_SETTINGS:
			saveModelBinary();
			break;

		case SaveDialog.OUTPUT_TEXT:
			saveOutputText();
			break;

		case SaveDialog.OUTPUT_JPEG:
			saveOutputGraphics();
			break;

		case SaveDialog.OUTPUT_EXCEL:
			saveOutputExcel();
			break;

		case SaveDialog.INPUT_JPEG:
			saveInputGraphics();default:

				// ??
		}
	}


	/** Saves the Output graphics of the model in JPEG format.*/
	private void saveOutputGraphics() {
		edu.umn.ecology.populus.fileio.GraphicsSaver.saveJPEG( modelOutput, "modelout.jpg", DesktopWindow.defaultWindow );
	}

	/** Saves the Output graphics of the model in JPEG format.  */
	private void saveInputGraphics() {
		edu.umn.ecology.populus.fileio.GraphicsSaver.saveJPEG( inputFrame, "modelin.jpg", DesktopWindow.defaultWindow );
	}

	/**
	 * Saves model so that it can later be read in and used and before
	 *  (serialization).  There should be no need to override this. Use writeExternal
	 * to override reading implementations.
	 */

	private void saveModelBinary() {
		try {
			ObjectOutputStream oos;
			String filename = edu.umn.ecology.populus.fileio.IOUtility.getFileName( "model", ".po", "Save Model", FileDialog.SAVE );
			if( filename != null ) {
				
				if (PopPreferencesStorage.getUseXMLSave()) {
					XMLEncoder e = new XMLEncoder(
		                          	new BufferedOutputStream(
		                              new FileOutputStream(filename)));
					e.writeObject(this);
					e.flush();
					e.close();
				} else {
					oos = new ObjectOutputStream( new FileOutputStream( filename ) );
					oos.writeObject( this );
					oos.flush();
					oos.close();
				}
			}
		}
		catch( IOException e ) {
			e.printStackTrace(System.out);
			JOptionPane.showMessageDialog( DesktopWindow.defaultWindow, "Could not write file.", "Error", JOptionPane.ERROR_MESSAGE );
			//System.out.print();
		}
	}
	/**
	 * Save output to a text file.
	 * Do not try to override this! Instead, use saveOutputText(OutputStreamWriter).
	 */
	private void saveOutputText() {
		try {
			OutputStreamWriter osw;
			String filename = edu.umn.ecology.populus.fileio.IOUtility.getFileName( "model", ".txt", "Save Model", FileDialog.SAVE );
			if( filename != null ) {
				osw = new OutputStreamWriter( new FileOutputStream( filename ) );
				saveOutputText( osw );
				osw.flush();
				osw.close();
			}
		}
		catch( IOException e ) {
			JOptionPane.showMessageDialog( DesktopWindow.defaultWindow, "Could not write file", "Error", JOptionPane.ERROR_MESSAGE );
		}
	}

	private void basicInitModel() {
		setModelColor( Utilities.getColor( colorNumber++ ) );
		inputFrame = new ModelInputFrame( this );
		outputFrame = new ModelOutputFrame( this );
		listeners = new Vector<ModelListener>( 1, 1 );

		//install help
		HelpUtilities hu = HelpUtilities.createHelp();
		//Rectangle r = desktopPane.getBounds();
		hu.installComponentHelp( inputFrame.getHelpComponent(), getHelpId() );
		hu.installComponentHelp( outputFrame.getHelpComponent(), getHelpId() );

		//Lars 30 May 2012 - why would a help id change for a component!? Disabling for now...
		//      ((JButton)inputFrame.getHelpComponent()).addActionListener(new java.awt.event.ActionListener(){
		//         /**
		//           This little method here updates the help id associated with the help button whenever the button
		//           is pressed. this happens before the HelpBroker does anything, so this method adds dynamic help id
		//           changing.
		//           
		//         */
		//         public void actionPerformed(java.awt.event.ActionEvent e){
		//            Rectangle r = desktopPane.getBounds();
		//            //HelpUtilities.setSize((int)(r.width*0.85d),(int)(r.height*0.85d));
		//            //updateInputHelpId();
		//      }});
	}

	/** Tries to place new models in available space */
	static class Alternator {
		private int bottom = 0;
		private int top = 0;

		public void modelDeleted( boolean type ) {
			if( type ) {
				top--;
			}
			else {
				bottom--;
			}
		}

		/**
		 * Returns true for top, false for bottom
		 */

		public boolean getNextPosition() {
			if( top <= bottom ) {
				top++;
				return true;
			}
			else {
				bottom++;
				return false;
			}
		}
	}

	/** currently unimplemented */

	static Model getLastSelectedModel() {
		return null;
	}
}
