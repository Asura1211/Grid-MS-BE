package org.electric.tool;

import java.io.File;

public class DeleteTool {

    public void DeleteTool(File file){
        if (file.isDirectory()) {
            File[] files=file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    DeleteTool(files[i]);
                }else {
                    if(files[i].toString().equals("labels")){
                        continue;
                    }
                    files[i].delete();
                }
            }
        }
        file.delete();
    }
    public void DeleteToolNotIncludingDirectory(File file){
        if (file.isDirectory()) {
            File[] files=file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    DeleteTool(files[i]);
                }else {
                    files[i].delete();
                }
            }
        }

    }
}

