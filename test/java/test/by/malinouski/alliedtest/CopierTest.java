/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.by.malinouski.alliedtest;

import by.malinouski.alliedtest.Copier;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;

/**
 *
 * @author makarymalinouski
 */
public class CopierTest {
    
    public CopierTest() {
    }
    
    public static Copier copier;
    public static Path fromA;
    public static Path fromB;
    public static Path resultFile;
    public static BufferedWriter writerA;
    public static BufferedWriter writerB;
    
    
    @BeforeClass
    public static void setUpClass() throws IOException {
        copier = new Copier();
    }
    
    @AfterClass
    public static void teadDownClass() throws IOException {
        writerA.close();
        writerB.close();
    }
    
    @Before
    public void setUp() throws IOException {
        fromA = Files.createTempFile("temp", "txt");
        fromB = Files.createTempFile("temp2", "txt");
        resultFile = Files.createTempFile("temp3", "txt");
        writerA = Files.newBufferedWriter(fromA);
        writerB = Files.newBufferedWriter(fromB);

    }
    
    @After
    public void tearDown() throws IOException {
        Files.delete(fromA);
        Files.delete(fromB);
        Files.delete(resultFile);
    }
    
    @Test
    public void testCopyEmpty() throws IOException {
        copier.copy(fromA.toFile(), fromB.toFile(), resultFile.toString());
        
        assertTrue(Files.readAllLines(resultFile).isEmpty());
    }
    
    @Test
    public void testCopyOneNumberEach() throws IOException {
        writerA.write("1");
        writerB.write("2");
        writerA.flush();
        writerB.flush();
        copier.copy(fromA.toFile(), fromB.toFile(), resultFile.toString());
        List<String> lines = Files.readAllLines(resultFile);
        assertEquals(Arrays.asList("1", "2"), lines);
    }
    
    @Test
    public void testCopyMore() throws IOException {
        writerA.write("1\n2\n4");
        writerB.write("2");
        writerA.flush();
        writerB.flush();
        copier.copy(fromA.toFile(), fromB.toFile(), resultFile.toString());
        List<String> lines = Files.readAllLines(resultFile);
        assertEquals(Arrays.asList("1", "2", "2", "4"), lines);
    }
    
    @Test
    public void testCopyMany() throws IOException {
        writerA.write("1\n2\n4\n12\n13\n15\n25");
        writerB.write("2\n3\n4\n6\n8\n25");
        writerA.flush();
        writerB.flush();
        copier.copy(fromA.toFile(), fromB.toFile(), resultFile.toString());
        List<String> lines = Files.readAllLines(resultFile);
        assertEquals(Arrays.asList("1", "2", "2", "3", "4", "4", "6", "8", 
                "12", "13", "15", "25", "25"), lines);
    }
   
}
