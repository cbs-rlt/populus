package edu.umn.ecology.populus.fileio;
import java.io.*;

public class Res extends java.util.ListResourceBundle implements Serializable {
   static final Object[][] contents = new String[][] {
       {
         "No_description", "No description"
      },  {
         "Files_of_type_", "Files of type:"
      },  {
         "picture_jpg", "picture.jpg"
      },  {
         "Save_picture_in_JPEG", "Save picture in JPEG format"
      },  {
         "Error_Saving_File", "Error Saving File"
      },  {
         "Could_Not_Save_File", "Could Not Save File"
      }
   };

   public Object[][] getContents() {
      return contents;
   }
}
