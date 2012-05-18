package edu.umn.ecology.populus.core;

import java.util.*;


/**
 * <p>Title: This was intended as a wrapper around ResourceBundle, since sometimes ResourceBundle won't
 * find the correct resource.</p>
 * <p>Description: ecological models</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: University of Minnesota</p>
 * @author Lars Roe
 * @version 5.4
 */

public class PopResourceBundle {
   ResourceBundle resHdl;
   String path;

   public PopResourceBundle(String path) {
      this.path = path;
   }

   private synchronized void initRes() {
      if (resHdl == null)
         resHdl = ResourceBundle.getBundle(path);
   }

   public String getString(String key) {
      initRes();
      return resHdl.getString(key);
   }

   public static PopResourceBundle getBundle(String path) {
      return new PopResourceBundle(path);
   }
}

