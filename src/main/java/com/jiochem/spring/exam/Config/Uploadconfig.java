package com.jiochem.spring.exam.Config;

import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Uploadconfig {
    private String location="upload";

    private final List<String> uploadImageTypes = new ArrayList<String>();

        public Uploadconfig() {
            this.uploadImageTypes.add("image/jpeg");
            this.uploadImageTypes.add("image/png");
        }

        public String getLocation(){
            return location;
        }

        public List<String> getUploadImageTypes(){
            return this.uploadImageTypes;
        }

        public void setLocation(String location){
            this.location = location;
        }

}