package com.rockville.exercise.controllers;

import com.rockville.exercise.beans.MetaData;
import com.rockville.exercise.utils.DataProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
public class DataUploaderController {

    @Autowired
    private DataProcessor processor;

    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file){
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        if(extension.equalsIgnoreCase("txt")){
            List<MetaData> metaData;
            try {
                metaData = processor.process(file);
                processor.saveAll(metaData);
            } catch (IOException e) {
                log.error("Something was wrong when file processing", e);
                return "errormsg";
            }

            return "success";
        }else{
            return "errormsg";
        }
    }

    @RequestMapping(value = "/showdata")
    public String upload(Model model){
        model.addAttribute("dataList",processor.findAll());
        return "showdata";
    }
}
