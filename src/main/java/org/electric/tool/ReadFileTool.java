package org.electric.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadFileTool {
    private ArrayList<HashMap<String,String>> picInfo;
    public void clear(){
        picInfo.clear();
    }
    public ReadFileTool(){
        picInfo = new ArrayList<>();
    }
    public void readPic(File file){
        if(file.isDirectory()){
            File[] files=file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    readPic(files[i]);
                }else {
                    if(files[i].toString().equals("labels")){
                        continue;
                    }

                    String suffix = files[i].toString().substring(files[i].toString().lastIndexOf(".") + 1);
                    if(suffix.equals("jpg") || suffix.equals("png") || suffix.equals("jepg")){
                        HashMap<String,String> temp = new HashMap<>();
                        temp.put("url",files[i].getAbsolutePath());
                        temp.put("name",files[i].getName());
                        picInfo.add(temp);
                    }

                }
            }
        }
    }

    public ArrayList<HashMap<String, String>> getPicInfo() {
        return picInfo;
    }
}
