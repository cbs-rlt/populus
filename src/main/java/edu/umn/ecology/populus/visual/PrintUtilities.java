/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.visual;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 * A simple utility class that lets you very simply print
 * an arbitrary component. Just pass the component to the
 * PrintUtilities.printComponent. The component you want to
 * print doesn't need a print method and doesn't have to
 * implement any interface or do anything special at all.
 * <p>
 * If you are going to be printing many times, it is marginally more
 * efficient to first do the following:
 * <PRE>
 * PrintUtilities printHelper = new PrintUtilities(theComponent);
 * </PRE>
 * then later do printHelper.print(). But this is a very tiny
 * difference, so in most cases just do the simpler
 * PrintUtilities.printComponent(componentToBePrinted).
 * <p>
 * 7/99 Marty Hall, http://www.apl.jhu.edu/~hall/java/
 * May be freely used or adapted.
 */

public class PrintUtilities implements Printable {
    private Component componentToBePrinted;

    public PrintUtilities(Component componentToBePrinted, Frame f) {
        this.componentToBePrinted = componentToBePrinted;
    }

    public void print() {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(this);
        if (printJob.printDialog()) {
            try {
                printJob.print();
            } catch (PrinterException pe) {
                System.err.println("Error printing: " + pe);
            }
        }
    }

    /**
     * Required method to implement java.awt.Printable
     * Because the width of the component is often larger than the width of the
     * piece of paper, the component is scaled down. Landscape printing would be a
     * better option, but it doesn't seem to be working.
     */

    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
        if (pageIndex > 0) {
            return (NO_SUCH_PAGE);
        } else {
            Graphics2D g2d = (Graphics2D) g;
            if (componentToBePrinted.getWidth() > pageFormat.getImageableWidth()) {
                double r = pageFormat.getImageableWidth() / componentToBePrinted.getWidth();

				/*
            Paper p = new Paper();
            p.setImageableArea(0,0,pageFormat.getWidth(), pageFormat.getHeight());
            pageFormat.setPaper(p);
            pageFormat.setOrientation(PageFormat.LANDSCAPE);
				 */
                g2d.scale(r, r);
                g2d.translate(pageFormat.getImageableX() + 50, pageFormat.getImageableY() + 100);
            } else {
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            }
            disableDoubleBuffering(componentToBePrinted);
            componentToBePrinted.paint(g2d);
            enableDoubleBuffering(componentToBePrinted);
            return (PAGE_EXISTS);
        }
    }

    /**
     * The speed and quality of printing suffers dramatically if
     * any of the containers have double buffering turned on.
     * So this turns if off globally.
     */

    public static void disableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(false);
    }

    /**
     * Re-enables double buffering globally.
     */

    public static void enableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(true);
    }

    public static void printComponent(Component c, Frame f) {
        new PrintUtilities(c, f).print();
    }
}
