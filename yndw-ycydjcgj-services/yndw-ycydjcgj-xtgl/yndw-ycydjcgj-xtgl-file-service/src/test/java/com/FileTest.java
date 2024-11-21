package com;

import com.yndw.dvp.XtglFileAppRun;
import com.yndw.dvp.xtgl.file.storage.FileStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Create By Carlos
 * 2020/11/2
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XtglFileAppRun.class)
public class FileTest {
    @Autowired
    private FileStorage fileStorage;
    @Test
    public void test(){
        System.out.println(fileStorage.getDownloadUrl("1311198567066521601.json","Dockerfile"));
    }

}
