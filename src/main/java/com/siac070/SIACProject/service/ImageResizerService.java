package com.siac070.SIACProject.service;

import net.coobird.thumbnailator.Thumbnails;
import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public class ImageResizerService {

    // Método para redimensionar una imagen
    public void resizeImage(File inputFile, File outputFile, double quality, Integer width, Integer height) throws IOException {
        Thumbnails.Builder<File> builder = Thumbnails.of(inputFile).outputQuality(quality);

        if(width != null && height != null){
            builder.size(width, height);
        }else{
            builder.scale(1.0);
        } 
        builder.keepAspectRatio(true);
        builder.toFile(outputFile);
    }

}
