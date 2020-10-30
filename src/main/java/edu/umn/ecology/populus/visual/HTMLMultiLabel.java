package edu.umn.ecology.populus.visual;
import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: Populus</p>
 * <p>Description: ecological models</p>
 * <p>Copyright: Copyright (c) 2005, 2015</p>
 * <p>Company: University of Minnesota</p>
 * @author Lars Roe
 * @version 5.4
 * This should be combined with MultiLabel
 */

public class HTMLMultiLabel extends JPanel implements HTMLConstants {
	/**
	 *
	 */
	private static final long serialVersionUID = 4078264764755231151L;
	private static final boolean kUseTrueHTML = false;
	private static final String kDefaultString = "*LABEL*";

	private int direction;
	private HTMLLabel[] labels = null;
	private TrueHTMLLabel[] tlabels = null;
	private GridBagLayout layout;
	private String[] text = null;

	public HTMLMultiLabel() {
		init();
	}

	public HTMLMultiLabel( String[] text ) {
		init();
		setText(text);
	}

	public HTMLMultiLabel( String text ) {
		init();
		setText(text);
	}

	protected void init() {
		layout = new GridBagLayout();
		this.setLayout(layout);
		this.setText(new String[] {kDefaultString});
	}


	public void setDirection( int newDirection ) {
		this.direction = newDirection;
		for (int i = 0; i < labels.length; i++)
			labels[i].setDirection(newDirection);
		setText(text);
	}

	public void setDefaultFont( Font newDefaultFont ) {
		for (int i = 0; i < labels.length; i++)
			labels[i].setDefaultFont(newDefaultFont);
	}

	public void setDefaultColor(Color c) {
		for (int i = 0; i < labels.length; i++)
			labels[i].setDefaultColor(c);
	}

	public void setText( String[] newText ) {
		int dir;
		boolean hasHTMLLabels;
		GridBagConstraints gdbc;

		if (newText == null || newText.length < 1)
			return; //newText = new String[] (kDefaultString);

		this.text = newText;
		if (labels != null && labels[0] != null) {
			dir = labels[0].getDirection();
			hasHTMLLabels = labels[0].getHasHTMLLabels();
		}
		else {
			dir = HTMLConstants.NORMAL;
			hasHTMLLabels = true;
		}

		if (kUseTrueHTML)
			tlabels = new TrueHTMLLabel[newText.length];
		else
			labels = new HTMLLabel[newText.length];
		for (int i = 0; i < labels.length; i++) {
			if (kUseTrueHTML) {
				tlabels[i] = new TrueHTMLLabel();
				tlabels[i].setText(newText[i]);
				tlabels[i].setDirection(dir);
				tlabels[i].setHasHTMLLabels(hasHTMLLabels);
				tlabels[i].setBackground(new Color(0, true));
			} else {
				labels[i] = new HTMLLabel();
				labels[i].setText(newText[i]);
				labels[i].setDirection(dir);
				labels[i].setHasHTMLLabels(hasHTMLLabels);
				labels[i].setBackground(new Color(0, true));
			}
		}

		this.removeAll();
		gdbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);
		for (int i = 0; i < labels.length; i++) {
			switch (direction) {
			case NORMAL:
				gdbc.gridy = i;
				break;
			case UPSIDEDOWN:
				gdbc.gridy = labels.length - 1 - i;
				break;
			case DOWN_TO_UP:
				gdbc.gridx = i;
				break;
			case UP_TO_DOWN:
				gdbc.gridx = labels.length - 1 - i;
				break;
			}
			this.add(labels[i], gdbc);
		}
	}

	public void setText( String newText ) {
		setText(new String[] {newText});
	}
}



