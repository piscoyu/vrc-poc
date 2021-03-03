package com.daimler.ocr;

import com.daimler.util.Logger;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class PDF2JPG {

    private static final String pdfimagesCommand = "D:\\tools\\poppler-0.68.0\\bin\\pdfimages";
    //C:\project\ocrtable\VRC\testdata
    public static final void  main(String []argc) throws Exception{

//        String pdfPath = "C:\\project\\ocrtable\\VRC\\PDFdata\\";
//
//        String jpgPath = "C:\\project\\ocrtable\\VRC\\JPGdata\\";
//        String finalJPGdata = "C:\\project\\ocrtable\\VRC\\FinalJPGdata\\";
//
//        PDF2JPGConvert(pdfPath, jpgPath);
//
//        JPGSplit(jpgPath, finalJPGdata);
    }


    public static void PDF2JPGConvert(String pdfPath, String destPath) throws IOException {
//        String path = "C:/project/ocrtable/VRC/VRCSample/";
        File parent = new File(pdfPath);
        if(parent.exists()){

            String []fileNames = parent.list();
            for(int i = 0; i< fileNames.length; i++ ) {
                // System.out.println(path + fileNames[i]);
                String child = pdfPath + fileNames[i];
                if(child.endsWith(".pdf")){
                    String command = pdfimagesCommand + " -p  -j \"" + child + "\" vrc" + i;
                    System.out.println(command);

                    executeCommand(command, destPath);
                }
            }
        }
    }

    public static void JPGSplit(String jpgPath, String destPath) throws IOException {

        File parent = new File(jpgPath);
        if(parent.exists()){

            String []fileNames = parent.list();
            for(int i = 0; i< fileNames.length; i++ ) {
                // System.out.println(path + fileNames[i]);
                String child = jpgPath + fileNames[i];
                if(child.endsWith(".jpg")){

                    splitImage(child, destPath, fileNames[i]);
                }
            }
        }
    }

    public static void executeCommand(final String command, String workdir) throws IOException {
        final File root = new File(workdir);
        final Process p = Runtime.getRuntime().exec(command, null, root);
        //管道sh -c ps -ef | grep -v grep
        //new ProcessBuilder("sh", "-c", "ps -ef | grep -v grep");
//		ProcessBuilder bulider = new ProcessBuilder(command);
//		Process p = bulider.start();
        Reader input = new InputStreamReader(p.getInputStream());
        Reader errors = new InputStreamReader(p.getErrorStream());

        for (String line : IOUtils.readLines(input)) {
            if (line.startsWith("[ERROR]")) {
                Logger.error(line);
            } else if (line.startsWith("[WARNING]")) {
                Logger.warn(line);
            } else {
                Logger.info(line);
            }
        }

        for (String line : IOUtils.readLines(errors)) {
            if (line.startsWith("[ERROR]")) {
                Logger.error(line);
            } else if (line.startsWith("[WARNING]")) {
                Logger.warn(line);
            } else {
                Logger.info(line);
            }
        }

        p.getOutputStream().close();

        try {
            if (p.waitFor() != 0) {
                Logger.warn("The command '" + command + "' did not complete successfully");
            }
        } catch (final InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }



    public static void splitImage(String imagePath, String destPath, String fileName) throws IOException{
        File file = new File(imagePath); // I have bear.jpg in my working directory
        FileInputStream fis = new FileInputStream(file);
        BufferedImage image = ImageIO.read(fis); //reading the image file

        int rows = 2; //You should decide the values for rows and cols variables
        int cols = 1;
        int chunks = rows * cols;

        int chunkWidth = image.getWidth() / cols; // determines the chunk width and height
        int chunkHeight = image.getHeight() / rows;
        int count = 0;
        BufferedImage imgs[] = new BufferedImage[chunks]; //Image array to hold image chunks
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                //Initialize the image array with image chunks
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

                // draws the image chunk
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        }
        System.out.println("Splitting done");

        //writing mini images into image files
        for (int i = 0; i < imgs.length; i++) {
            ImageIO.write(imgs[i], "jpg", new File( destPath + fileName.substring(0, fileName.length()-4)  +"_" + i + ".jpg"));
        }
        System.out.println("Mini images created");
    }
}
