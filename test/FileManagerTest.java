import org.junit.Test;
import com.danylo.fileManager.FileManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileManagerTest {

    @Test
    public void testCalculateFiles() {
        int count = FileManager.calculateFiles("c:/temp");
        assertEquals(38, count);
    }

    @Test
    public void testCalculateDirs() {
        int count = FileManager.calculateDirs("c:/temp");
        assertEquals(4, count);
    }

    @Test
    public void testCopy() {
        boolean checkFile = FileManager.copy("c:/temp/src/1.txt", "c:/temp/dest");
        boolean checkFolder = FileManager.copy("c:/temp/src", "c:/temp/dest");
        assertTrue(checkFile);
        assertTrue(checkFolder);
    }

    @Test
    public void testDel() {
        boolean delFile = FileManager.del("c:/temp/test.txt");
        boolean delFolder = FileManager.del("c:/temp/del");
        assertTrue(delFile);
        assertTrue(delFolder);

    }

    @Test
    public void testMove() {
        boolean moveFile = FileManager.move("c:/temp/test1.txt", "c:/temp/dest");
        boolean moveFolder = FileManager.move("c:/temp/del", "c:/temp/dest");
        assertTrue(moveFile);
        assertTrue(moveFolder);
    }
}
