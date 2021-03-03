package com.daimler.ocr;

import com.daimler.model.VrcModel;
import com.daimler.util.Logger;

import java.io.File;
import java.io.IOException;

public class VRCProcessJob {

    public void boxDetection(){
        String filePath = "C:\\project\\ocrtable\\table-ocr\\test\\vrc15-000_1.jpg";
    }
    /*
        String pdfPath = "";
        String imagePath = "";
        String OCRJSONPath = "";
        String boxJSONPath = "";
    */
    public static final String pdfPath = "C:/Users/xiao/Desktop/test/pdf/";
    public static final String jpgdata_subdir = "JPGdata";
    public static final String finalJPG_subdir = "FinalJPGdata";
    public static String jpgdataPath = null;
    public static String finalJPGdataPath = null;

    private static void setupPath() throws Exception{

        File file_pdfPath = new File(pdfPath);

        if(file_pdfPath.exists()){
            jpgdataPath = file_pdfPath.getParent() +"/"+jpgdata_subdir+"/";
            finalJPGdataPath = file_pdfPath.getParent() +"/"+finalJPG_subdir+"/";
            File file_jpgdataPath = new File(jpgdataPath);
            if(! file_jpgdataPath.exists()){
                file_jpgdataPath.mkdirs();
            }
            File file_finalJPGPath = new File(finalJPGdataPath);
            file_finalJPGPath.mkdirs();

            Logger.info("JPG Path and final JPG Path are created!");
        }else{
            throw new Exception("PDF directory does not exist!");
        }
    }

    public static final void  main(String []argc) throws Exception{

        setupPath();

        splitImage();

        ocr();
//        HanvonOCR.part2_BoxDetection(finalJPGdataPath, "001_0.jpg");
//        HanvonOCR.part2_OCR(finalJPGdataPath, "001_0.jpg");
//        ContentExtraction.VRC2Extract(finalJPGdataPath, finalJPGdataPath, "001_0.json");
//        System.out.println("Hello World!");
    }

    public static final void  ocr() throws Exception{

        BoxDetection();
        //ocrmerge();
        //extract();
    }

    public static final void  BoxDetection() throws Exception{
        HanvonOCR.part0_BoxDetection(finalJPGdataPath, "000_0.jpg");
        HanvonOCR.part1_BoxDetection(finalJPGdataPath, "000_1.jpg");
        HanvonOCR.part2_BoxDetection(finalJPGdataPath, "001_0.jpg");
    }

    public static final void  ocrmerge() throws Exception{
        HanvonOCR.part0_OCR(finalJPGdataPath, "000_0.jpg");
        HanvonOCR.part1_OCR(finalJPGdataPath, "000_1.jpg");
        HanvonOCR.part2_OCR(finalJPGdataPath, "001_0.jpg");

        HanvonOCR.part0_Merge(finalJPGdataPath, "000_0.json", finalJPGdataPath, "000_0_box.json");
        HanvonOCR.part1_Merge(finalJPGdataPath, "000_1.json", finalJPGdataPath, "000_1_box.json");
    }

    public static final VrcModel extract() throws Exception{
        VrcModel vrcModel = new VrcModel();
        ContentExtraction.VRC0Extract(finalJPGdataPath, finalJPGdataPath, "000_0.json",vrcModel);
        ContentExtraction.VRC1ExtractEx(finalJPGdataPath, finalJPGdataPath, "000_1.json",vrcModel);

        ContentExtraction.VRC2Extract(finalJPGdataPath, finalJPGdataPath, "001_0.json",vrcModel);

        return vrcModel;
    }

    public static void splitImage() throws IOException {

        PDF2JPG.PDF2JPGConvert(pdfPath, jpgdataPath);

        PDF2JPG.JPGSplit(jpgdataPath, finalJPGdataPath);
    }
}
