package com.selflearning.blogging.bloggingapplicationapi.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService implements com.selflearning.blogging.bloggingapplicationapi.services.FileService
{
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException
    {
        //File name
        String name = file.getOriginalFilename();

        //random name for the generated file
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

        //Full path
        String filePath = path + File.separator + fileName;

        //Create a folder with the given path if not created
        File file1 = new File(path);
        if(!file1.exists())
        {
            file1.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException
    {
        String fullPath = path + File.separator + fileName;
        return new FileInputStream(fullPath);
    }
}
