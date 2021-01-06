package edu.umn.ecology.populus.fileio;

import edu.umn.ecology.populus.PopRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.util.Vector;

/**
 * <p>Title: Populus</p>
 * <p>Description: ecological models</p>
 * <p>Copyright: Copyright (c) 2006, 2015</p>
 * <p>Company: University of Minnesota</p>
 *
 * @author Lars Roe
 * @version 5.4
 * <p>
 * This is not a very clever class, but was meant to be extensible in case
 * we wanted to redirect the output elsewhere.
 */

public class Logging {
    private static Logger LOG = LoggerFactory.getLogger(PopRun.class);

    //TODO: Turn these into enum. OR, just use the external Logger definitions.
    public static final int kInfo = 0; //Normal log
    public static final int kWarn = 10; //Strange, but recoverable
    public static final int kErr = 20; //Generally fatal-ish


    public static void log(String msg) {
        log(msg, kInfo);
    }

    public static void log(String msg, int severity) {
        switch(severity) {
            case kErr:
                LOG.error(msg);
                break;
            case kInfo:
                LOG.info(msg);
                break;
            case kWarn:
                LOG.warn(msg);
        }
    }

    public static void log(Exception e) {
        log(e, "Exception thrown");
    }

    public static void log(Exception e, String msg) {
        LOG.error(msg, e);
    }
}
