package com.project.enderman.utils;

import com.project.enderman.entities.FileDTO;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Navigator {

    public static Optional<List<FileDTO>> goInto(File file) {

        //File directory = new File(file.getPath());

        if(file.exists() && file.isDirectory()){

            //Get folder contents
            List<FileDTO> result = new LinkedList<>();
            File[] contents = file.listFiles();

            for(File f : contents) {

                FileDTO fDTO = new FileDTO().setFolder(f.isDirectory())
                        .setName(f.getName())
                        .setPath(f.getPath())
                        .setParentPath(file.getPath());

                result.add(fDTO);

            }

            return Optional.of(result);

        }

        return Optional.empty();
    }

}
