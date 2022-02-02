package com.gelvides.jdbc_education.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.Arrays;

@Slf4j
@Component
public class SqlScriptReader {

    public String getSQLScript(String filePath){
        StringBuilder builder = new StringBuilder();
        try(FileReader reader = new FileReader(filePath)){
            char buff[] = new char[256];
            int c;
            while((c = reader.read(buff))>0){
                if(c<256) {
                    builder.append(Arrays.copyOf(buff, c));
                } else
                    builder.append(buff);
            }
            log.info("SQL файл " + filePath + " успешно прочитан");
            return builder.toString();
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return null;
    }
}
