package com.rockville.exercise.utils;

import com.rockville.exercise.beans.MetaData;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Service class for data process porpouse
 */
@Service
@Slf4j
public class DataProcessor {

    private final String fileName= "alfagl-result.txt";

    /**
     * Process the MultipartFile to extract the meta-data properties
     * @param file
     * @return
     * @throws IOException
     */
    public List<MetaData> process(MultipartFile file) throws IOException {
        List<MetaData> result = Collections.emptyList();
        if(!file.isEmpty()){
            String temp = "";
            InputStream in = file.getInputStream();
            try(BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in))){
                if (in != null) {
                    while ((temp = reader.readLine()) != null) {
                        if(validMetaDataField(temp)){
                            String[] field = extractMetaDataField(temp);
                            result.add(MetaData.builder()
                                    .name(field[0])
                                    .value(field[1])
                                    .build());
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Valid the meta date field
     * @param line
     * @return
     */
    public boolean validMetaDataField(String line){
        if(line.contains("=") && Strings.isNotEmpty(line)) {
            char[] chars = line.toCharArray();
            return IntStream.range(0, chars.length)
                    .mapToObj(i -> chars[i])
                    .filter(val -> val =='=')
                    .count() == 1L;
        }
        return false;
    }

    /**
     *
     * @param line
     * @return
     */
    public String[] extractMetaDataField(String line){
        String[] field = line.split("=");
        if(field.length < 2){
            log.error("the meta-data field was invalid");
        }
        return field;
    }

    /**
     * Save a list of Metadate objects to the disk
     * @param metaData
     */
    public void saveAll(List<MetaData> metaData){
        try(PrintWriter writer = new PrintWriter(fileName, "UTF-8")){
            metaData.stream().forEach(data -> writer.print("nem:"+data.getName()+", value"+data.getValue()));
        } catch (FileNotFoundException e) {
            log.error("FileNotFoundException", e);
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException", e);
        }

    }

    /**
     * Return the list of Metadata contains on the clean file
     * @return
     */
    public List<MetaData> findAll(){
        List<MetaData> metaDataList = Collections.emptyList();
        try(FileReader fr = new FileReader(fileName); BufferedReader buf = new BufferedReader(fr)) {
            String temp = "";
            while ((temp = buf.readLine()) != null) {
                String[] line = temp.split("=");
                metaDataList.add(MetaData.builder()
                        .name(line[0])
                        .value(line[1])
                        .build());
            }
        } catch (FileNotFoundException e) {
            log.error("FileNotFoundException", e);
        } catch (IOException e) {
            log.error("IOException", e);
        }

        return metaDataList;
    }

}
