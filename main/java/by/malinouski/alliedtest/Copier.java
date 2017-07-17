/*
 * Test task for alliedtesting 
 */
package by.malinouski.alliedtest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author makarymalinouski
 */
public class Copier {
    private static final Logger LOGGER = Logger.getLogger(Copier.class.getName());

    /**
     * Produces new file containing sorted integers from fileA and fileB.<br>
     * For example, fileA contains 1 3 4 (separated by new line),
     * fileB contains 2 3 5 9 (separated by new line),
     * then the resulting file contains 1 2 3 3 4 5 9 (sep by new line)
     * @param fileA contains sorted integers separated by new line
     * @param fileB same as fileA
     * @param resultFileName name of the resulting file
     * @return File that contains sorted integers separated by new line
     */
    public File copy(File fileA, File fileB, String resultFileName) {
        File resultFile = new File(resultFileName);
        
        try(Scanner scannerA = new Scanner(fileA);
            Scanner scannerB = new Scanner(fileB);
            BufferedWriter writer = 
                    new BufferedWriter(new FileWriter(resultFile))) {
            
            boolean hasNextA, hasNextB;
            
            if ((hasNextA = scannerA.hasNextInt()) & 
                (hasNextB = scannerB.hasNextInt())) {
                
                int nextA = scannerA.nextInt();
                int nextB = scannerB.nextInt();
                
                while (hasNextA || hasNextB) {
                    if (!hasNextB || (nextA <= nextB && hasNextA)) {
                        
                        writer.write(String.valueOf(nextA) + "\n");
                        hasNextA = scannerA.hasNextInt();
                        nextA = hasNextA ? scannerA.nextInt() : nextA;
                    } else {
                        writer.write(String.valueOf(nextB) + "\n");
                        hasNextB = scannerB.hasNextInt();
                        nextB = hasNextB ? scannerB.nextInt() : nextB;
                    }
                }
            } else if (hasNextA) {
                Files.copy(fileA.toPath(), resultFile.toPath(), 
                            StandardCopyOption.REPLACE_EXISTING);
            } else if (hasNextB) {
                Files.copy(fileB.toPath(), resultFile.toPath(), 
                            StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Exception while working with file", ex);
        }
        
        return resultFile;
    }
    
}
