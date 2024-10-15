package com.packt.cantata.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.packt.cantata.service.FileService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
	
	private final FileService fileService;
	
	@PostMapping("/FileNums")
    public ResponseEntity<List<Long>> uploadAndGetFileNums(@RequestParam("file") List<MultipartFile> files, @RequestParam("tableName") String tableName, @RequestParam("number") Long number) {
        try {
            List<Long> fileNums = fileService.uploadToCloudAndGetFileNums(files, tableName, number);
            System.out.println("@@@@@@@@@@@tableName  :   "+tableName);
            System.out.println("@@@@@@@@@@@fileNums  :   "+fileNums);
            return ResponseEntity.ok(fileNums);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

}
