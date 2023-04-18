package com.jpa.intra;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class imginitTest {
    String rootPath = System.getProperty("user.dir"); //현재 프로젝트 경로 위치를 수정하더라도 그에 따라 바뀜
    @Test
    void Test(){

        String currentPath = "\\src\\main\\resources\\mem_img\\" + 1 + ".jpg"; //그뒤 나머지 경로


            Path testPath = Paths.get(File.separatorChar + "mem_img", File.separatorChar + "1.jpg");
            Path testPath2 = Paths.get(rootPath + currentPath);

            File file = new File(testPath2.toString());
            System.out.println(file.exists());
            Resource resource = new InputStreamResource( testPath.getClass().getResourceAsStream(file.getAbsolutePath()));

            System.out.println(resource);
            System.out.println(resource.getFilename());
            System.out.println("===================================================");
            System.out.println("===================================================");



        String filePath = rootPath + currentPath;



    }

}