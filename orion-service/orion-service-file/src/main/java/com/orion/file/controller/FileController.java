package com.orion.file.controller;

import com.orion.file.FastDFSFile;
import com.orion.util.FastDFSClient;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/file")
public class FileController {

    @PostMapping("upload")
    public String upload(@RequestParam("file")MultipartFile file) {
        try {
            FastDFSFile fastDFSFile = new FastDFSFile(
                    file.getOriginalFilename(),
                    file.getBytes(),
                    StringUtils.getFilenameExtension(file.getOriginalFilename()));
            String[] upload=FastDFSClient.upload(fastDFSFile);
            return FastDFSClient.getTrackerUrl()+"/"+upload[0]+"/"+upload[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // !!!!!!!!!!!
    @PostMapping("delete")
    public boolean delete(String[] storage) {
        return FastDFSClient.deleteFile(storage[0], storage[1]);
    }
}
