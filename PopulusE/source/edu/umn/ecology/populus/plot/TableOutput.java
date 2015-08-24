package edu.umn.ecology.populus.plot;

import edu.umn.ecology.populus.constants.OutputTypes;
import edu.umn.ecology.populus.resultwindow.*;
import edu.umn.ecology.populus.visual.*;
import java.awt.*;
import javax.swing.*;

/**
 * this method has been "personalized" to the ASPG model, but if needed, it could be
 * generalized.
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002, 2015</p>
 * <p>Company: University of Minnesota</p>
 * @version 5.2
 */
public class TableOutput extends OutputPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = -1964107062861738333L;
TableInterface info;
   JScrollPane scrollPane = new JScrollPane();
   JPanel jPanel1 = new JPanel();
   BorderLayout borderLayout2 = new BorderLayout();
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   Matrix matrixPanel = new Matrix();
   JPanel jPanel2 = new JPanel();
   GridLayout gridLayout1 = new GridLayout();

   /** Call this when updating, not initializing (not for first time) */
   public void updateData(BasicPlotInfo bpi) {
      JLabel tempLabel;
      double[][] matrix;
      info = (TableInterface) (bpi.getSpecial());
      matrix = info.getMatrix();
      matrixPanel.setTopLabels(info.getMatrixLabels());
      matrixPanel.setMatrixData(matrix, info.getXStart(), info.getXEnd(), info.getYStart(), info.getYEnd());
      //matrixPanel.setTopLabelsVisible(false);
      gridLayout1.setColumns(1);
      gridLayout1.setRows(info.getTitle().length);
      jPanel2.removeAll();
      for (int i = 0; i < info.getTitle().length; i++) {
         tempLabel = new JLabel(info.getTitle()[i]);
         tempLabel.setHorizontalAlignment(SwingConstants.CENTER);
         //tempLabel.setBackground(Color.white);
         jPanel2.add(tempLabel);
      }
      this.revalidate();
   }

   public TableOutput(BasicPlotInfo bpi) {
      setType(OutputTypes.kOther);
      try  {
         jbInit();
         updateData(bpi);
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }

   private void jbInit() throws Exception {
      this.setLayout(gridBagLayout1);
      scrollPane.getViewport().setBackground(Color.white);
      scrollPane.setBorder(null);
      jPanel1.setLayout(borderLayout2);
      jPanel1.setBackground(Color.white);
      this.setBackground(Color.white);
      jPanel2.setLayout(gridLayout1);
      jPanel2.setBackground(Color.white);
      this.add(jPanel1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 5, 5));
      jPanel1.add(scrollPane, BorderLayout.CENTER);
      this.add(jPanel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      scrollPane.getViewport().add(matrixPanel, null);
   }
}