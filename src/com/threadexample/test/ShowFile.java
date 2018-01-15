package com.threadexample.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by araksgyulumyan
 * Date - 1/12/18
 * Time - 6:02 PM
 */

class ShowFile {
    public static void main(String args[]) {
        int i;
        String a1 = "/Users/araksgyulumyan/Desktop/test";
        String a2 = "/Users/araksgyulumyan/Desktop/test1";

//        FileInputStream fin = null;
//        FileOutputStream fout = null;



        // First, confirm that a filename has been specified.
//        System.out.println(args);

        // The following code opens a file, reads characters until EOF
        // is encountered, and then closes the file via a finally block.
        try (FileInputStream fin = new FileInputStream(a1); FileOutputStream fout = new FileOutputStream(a2);
        FileInputStream fin2 = new FileInputStream(a2)) {
//            fin = new FileInputStream(args[0]);
//            fout = new FileOutputStream(args[1]);
            do {
                i = fin.read();
                if (i != -1) {
                    fout.write(i);
                    fin2.read();
                    System.out.print((char) i);
                }
            }
            while (i != -1);
        }

        catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
        }

        catch (IOException e) {
            System.out.println("An I/O Error Occurred");
        }

//        finally {
//            // Close file in all cases.
//            try {
//                if (fin != null) {
//                    fin.close();
//                }
//            }
//
//            catch (IOException e) {
//                System.out.println("Error Closing File");
//            }
//        }
    }
}
