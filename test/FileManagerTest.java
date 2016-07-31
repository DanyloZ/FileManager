import com.danylo.fileManager.FileManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class FileManagerTest {
    @Before
    public void init() {
        try {
            File file = new File("test/testsourse/subfolder/subsubfolder");
            file.mkdirs();
            file = new File("test/testsourse/emptyfolder");
            file.mkdir();
            file = new File("test/testsourse/1");
            file.createNewFile();
            file = new File("test/testsourse/subfolder/2");
            file.createNewFile();
            file = new File("test/testsourse/subfolder/3");
            file.createNewFile();
            file = new File("test/testsourse/subfolder/subsubfolder/4");
            file.createNewFile();
            file = new File("test/testsourse/subfolder/subsubfolder/5");
            file.createNewFile();;
            file = new File("test/testdest");
            file.mkdir();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        FileManager.del("test/testsourse");
        FileManager.del("test/testdest");
    }


    @Test
    public void testCalculateFiles() {
        int count = FileManager.calculateFiles("test/testsourse");
        assertEquals(5, count);
    }

    @Test
    public void testCalculateDirs() {
        int count = FileManager.calculateDirs("test/testsourse");
        assertEquals(3, count);
    }

    @Test
    public void testCopy() {
        boolean checkFile = FileManager.copy("test/testsourse/1", "test/testdest");
        boolean checkFolder = FileManager.copy("test/testsourse", "test/testdest");
        assertTrue(checkFile);
        assertTrue(checkFolder);
        assertTrue(new File("test/testdest/1").exists());
        assertTrue(new File("test/testdest/testsourse/emptyfolder").exists());
        assertTrue(new File("test/testdest/testsourse/subfolder/2").exists());
    }

    @Test
    public void testDel() {
        boolean delFile = FileManager.del("test/testsourse/subfolder/2");
        boolean delFolder = FileManager.del("test/testsourse/subfolder/subsubfolder");
        assertTrue(delFile);
        assertTrue(delFolder);
        assertFalse(new File("test/testsourse/subfolder/2").exists());
        assertFalse(new File("test/testsourse/subfolder/subsubfolder").exists());
    }

    @Test
    public void testMove() {
        boolean moveFile = FileManager.move("test/testsourse/1", "test/testdest");
        assertTrue(moveFile);
        assertFalse(new File("test/testsourse/1").exists());
        assertTrue(new File("test/testdest/1").exists());
        boolean moveFolder = FileManager.move("test/testsourse/subfolder", "test/testdest");
        assertTrue(moveFolder);
        assertFalse(new File("test/testsourse/subfolder").exists());
        assertTrue(new File("test/testdest/subfolder/subsubfolder/5").exists());
    }
    @Test
    public void wrongPathTest() {
        int countFiles = FileManager.calculateFiles("wrongpass");
        assertEquals(0, countFiles);
        int countDirs = FileManager.calculateDirs("wrongpass");
        assertEquals(0, countDirs);
    }
}
