package com.daimler.ocr;

import com.daimler.model.VrcModel;
import com.daimler.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContentExtraction {

    public static final void main(String argc[]) throws Exception{
        String contentPath = "余庆/居民身份/32514312";
        String locPath = "登记机关江西省记安";
        System.out.println(locPath.replaceAll("[2.]*登记机[关]*",""));

        String substring = contentPath.substring(0, contentPath.indexOf("/"));

        System.out.println(contentPath.substring(contentPath.lastIndexOf("/")+1));
        System.out.println(substring);
//        VRC0Extract(contentPath, locPath, "", vrcModel);
//        VRC1ExtractEx(contentPath, locPath, "", vrcModel);
//        VRC2Extract(contentPath, locPath, "", vrcModel);
    }



    public static final void VRC0Extract(String contentPath, String locPath, String fileEndPattern, VrcModel vrcModel) throws Exception {
//        String contentPath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\raw_0\\";
//
//        String locPath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\0\\";
        File path = new File(contentPath);
        String []fileNames = path.list();
        int []seq = new int[]{0, 1, 2, 3, 4,5,6, 7, 8};
        for(int i = 0; i< fileNames.length; i++) {
            // System.out.println(path + fileNames[i]);
            String child = contentPath + fileNames[i];
            if (child.endsWith(fileEndPattern)) {//"000_0.json"

                JSONObject jsonobj1 = new JSONObject(new JSONTokener(new FileReader(new File(child))));
                System.out.println(child);
                String locFile = locPath + fileNames[i].substring(0, fileNames[i].length()-5) + "_box.json";
                JSONObject jsonLoc = new JSONObject(new JSONTokener(new FileReader(new File(locFile))));
                JSONArray locArray = jsonLoc.getJSONArray("rects");

                List<int []> rects = new ArrayList<int []>();
                for(int item: seq){
                    JSONArray intlocs = locArray.getJSONArray(item);
                    int [] loc = new int[]{intlocs.getInt(0), intlocs.getInt(1), intlocs.getInt(2), intlocs.getInt(3)
                            ,intlocs.getInt(4), intlocs.getInt(5), intlocs.getInt(6), intlocs.getInt(7)};
//                    System.out.println(intlocs.getInt(0)+" , " +intlocs.getInt(1));
                    rects.add(loc);
                }

                JSONArray array = jsonobj1.getJSONArray("lines");
                for(int j = 0; j<array.length(); j++){
                    JSONObject obj = array.getJSONObject(j);
                    String line = obj.getString("line");
                    JSONArray coords = obj.getJSONArray("coords");
                    int [] contentLoc = new int[]{coords.getInt(0), coords.getInt(1), coords.getInt(2), coords.getInt(3)
                            ,coords.getInt(4), coords.getInt(5), coords.getInt(6), coords.getInt(7)};

                    int XCenterLoc = (contentLoc[0]+contentLoc[2]+contentLoc[4]+contentLoc[6])/4;
                    int YCenterLoc = (contentLoc[1]+contentLoc[3]+contentLoc[5]+contentLoc[7])/4;
                    if(j <= 20) {
                        for(Iterator<int []> it = rects.iterator(); it.hasNext();) {
                            int [] tagloc = it.next();
                            //(tagloc[0], tagloc[1]), (tagloc[2], tagloc[3]), (tagloc[4], tagloc[5]), (tagloc[6], tagloc[7])
                            //
                            if (tagloc[0]<XCenterLoc  && XCenterLoc<tagloc[2] && tagloc[1]<YCenterLoc && YCenterLoc<tagloc[7]) {
                                //System.out.println(line);
                                //break;
                                if(line.contains("居民身份")) {
                                    vrcModel.setOwnerName_ocrValue(line.substring(0,line.indexOf("/")));
                                    vrcModel.setCertificateNumber_ocrValue(line.substring(line.lastIndexOf("/") +1 ));
                                }else if(line.contains("警察") || line.contains("交通")) {
                                    vrcModel.setRegistrationAuthority_ocrValue(line.replaceAll("[2.]*登记机[关]*",""));
                                }else if(line.matches("[^\\u4e00-\\u9fa5][A-Z0-9]+")) {
                                    vrcModel.setVehicleNumber_ocrValue(line);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static final void VRC1ExtractEx(String contentPath, String locPath, String fileEndPattern, VrcModel vrcModel) throws Exception {
//        String contentPath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\raw_1\\";
//
//        String locPath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\1.x\\";
        File path = new File(contentPath);
        String []fileNames = path.list();
        int []seq = new int[]{1,  3,  5,  9,  13};
        for(int i = 0; i< fileNames.length; i++) {
            // System.out.println(path + fileNames[i]);
            String child = contentPath + fileNames[i];
            if (child.endsWith(fileEndPattern)) {// "000_1.json"

                JSONObject content = new JSONObject(new JSONTokener(new FileReader(new File(child))));
                System.out.println(child);
                String locFile = locPath + fileNames[i].substring(0, fileNames[i].length()-5) + "_box.json";
                JSONObject jsonLoc = new JSONObject(new JSONTokener(new FileReader(new File(locFile))));
                JSONArray locArray = jsonLoc.getJSONArray("rects");


                List<int []> rectLocs = new ArrayList<int []>();
                for(int item: seq){
                    JSONArray intlocs = locArray.getJSONArray(item);
                    int [] loc = new int[]{intlocs.getInt(0), intlocs.getInt(1), intlocs.getInt(2), intlocs.getInt(3)
                            ,intlocs.getInt(4), intlocs.getInt(5), intlocs.getInt(6), intlocs.getInt(7)};
//                    System.out.println(intlocs.getInt(0)+" , " +intlocs.getInt(1));
                    rectLocs.add(loc);
                }

                JSONArray contentArray = content.getJSONArray("lines");
                for(int j = 0; j<contentArray.length(); j++){
                    JSONObject obj = contentArray.getJSONObject(j);
                    String line = obj.getString("line");
                    JSONArray lineCoords = obj.getJSONArray("coords");
                    int [] lineLoc = new int[]{lineCoords.getInt(0), lineCoords.getInt(1), lineCoords.getInt(2), lineCoords.getInt(3)
                            ,lineCoords.getInt(4), lineCoords.getInt(5), lineCoords.getInt(6), lineCoords.getInt(7)};

                    int XCenterLoc = (lineLoc[0]+lineLoc[2]+lineLoc[4]+lineLoc[6])/4;
                    int YCenterLoc = (lineLoc[1]+lineLoc[3]+lineLoc[5]+lineLoc[7])/4;
                    int x0 = lineLoc[0], x1 = lineLoc[2];
                    int y0 = lineLoc[1], y1 = lineLoc[7];
                    if(j <= 30) {
                        int boxSeq = 0;
                        for(Iterator<int []> it = rectLocs.iterator(); it.hasNext();) {
                            int [] tagloc = it.next();
                            //(tagloc[0], tagloc[1]), (tagloc[2], tagloc[3]), (tagloc[4], tagloc[5]), (tagloc[6], tagloc[7])
                            int rx0 = tagloc[0], rx1 = tagloc[2];
                            int ry0 = tagloc[1], ry1 = tagloc[7];
                            if(rx0-10 < x0 && x1 <rx1 && y0 > ry0-10 && y1 < ry1+20 ){
                            //if (tagloc[0]<XCenterLoc  && XCenterLoc<tagloc[2] && tagloc[1]<YCenterLoc && YCenterLoc<tagloc[7]) {
                                System.out.println(boxSeq + "-" + line);// + " -- " + coords.toString());
                                //break;
                                if(boxSeq == 1) {
                                    vrcModel.setVehicleBrand_ocrValue(line);
                                }else if(boxSeq == 2) {
                                    vrcModel.setVehicleModel_ocrValue(line);
                                }else if(boxSeq == 3) {
                                    vrcModel.setVinNumber_ocrValue(line);
                                }else if(boxSeq == 4) {
                                    vrcModel.setEngineNumber_ocrValue(line);
                                }
                            }
                            boxSeq++;
                        }
                    }
                }
            }
        }
    }


    public static final void VRC1Extract(String contentPath, String locPath, String fileEndPattern) throws Exception {
//        String contentPath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\raw_1\\";
//
//        String locPath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\1.x\\";
        File path = new File(contentPath);
        String []fileNames = path.list();
        int []seq = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        for(int i = 0; i< fileNames.length; i++) {
            // System.out.println(path + fileNames[i]);
            String child = contentPath + fileNames[i];
            if (child.endsWith(fileEndPattern)) {// "000_1.json"

                JSONObject jsonobj1 = new JSONObject(new JSONTokener(new FileReader(new File(child))));
                System.out.println(child);
                String locFile = locPath + fileNames[i].substring(0, fileNames[i].length()-5) + "_box.json";
                JSONObject jsonLoc = new JSONObject(new JSONTokener(new FileReader(new File(locFile))));
                JSONArray locArray = jsonLoc.getJSONArray("rects");


                List<int []> rects = new ArrayList<int []>();
                for(int item: seq){
                    JSONArray intlocs = locArray.getJSONArray(item);
                    int [] loc = new int[]{intlocs.getInt(0), intlocs.getInt(1), intlocs.getInt(2), intlocs.getInt(3)
                    ,intlocs.getInt(4), intlocs.getInt(5), intlocs.getInt(6), intlocs.getInt(7)};
//                    System.out.println(intlocs.getInt(0)+" , " +intlocs.getInt(1));
                    rects.add(loc);
                }

                JSONArray array = jsonobj1.getJSONArray("lines");
                for(int j = 0; j<array.length(); j++){
                    JSONObject obj = array.getJSONObject(j);
                    String line = obj.getString("line");
                    JSONArray coords = obj.getJSONArray("coords");
                    int [] contentLoc = new int[]{coords.getInt(0), coords.getInt(1), coords.getInt(2), coords.getInt(3)
                            ,coords.getInt(4), coords.getInt(5), coords.getInt(6), coords.getInt(7)};

                    int XCenterLoc = (contentLoc[0]+contentLoc[2]+contentLoc[4]+contentLoc[6])/4;
                    int YCenterLoc = (contentLoc[1]+contentLoc[3]+contentLoc[5]+contentLoc[7])/4;
                    if(j <= 30) {
                        for(Iterator<int []> it = rects.iterator(); it.hasNext();) {
                            int [] tagloc = it.next();
                            //(tagloc[0], tagloc[1]), (tagloc[2], tagloc[3]), (tagloc[4], tagloc[5]), (tagloc[6], tagloc[7])
                            //
                            if (tagloc[0]<XCenterLoc  && XCenterLoc<tagloc[2] && tagloc[1]<YCenterLoc && YCenterLoc<tagloc[7]) {
                                System.out.println(line);// + " -- " + coords.toString());
                                //break;
                            }
                        }
                    }
                }
            }
        }
    }

    public static final void VRC2Extract(String contentPath, String locPath, String fileEndPattern, VrcModel vrcModel) throws Exception {
//        String contentPath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\raw_0\\";
//
//        String locPath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\0\\";
        File path = new File(contentPath);
        String []fileNames = path.list();
        int []seq = new int[]{0, 1, 2, 3, 4};
        for(int i = 0; i< fileNames.length; i++) {
            // System.out.println(path + fileNames[i]);
            String child = contentPath + fileNames[i];
            if (child.endsWith(fileEndPattern)) {//"000_0.json"

                JSONObject jsonobj1 = new JSONObject(new JSONTokener(new FileReader(new File(child))));
                System.out.println(child);
                String locFile = locPath + fileNames[i].substring(0, fileNames[i].length()-5) + "_box.json";
                JSONObject jsonLoc = new JSONObject(new JSONTokener(new FileReader(new File(locFile))));
                JSONArray locArray = jsonLoc.getJSONArray("rects");

                List<int []> rects = new ArrayList<int []>();
                for(int item: seq){
                    JSONArray intlocs = locArray.getJSONArray(item);
                    int [] loc = new int[]{intlocs.getInt(0), intlocs.getInt(1), intlocs.getInt(2), intlocs.getInt(3)
                            ,intlocs.getInt(4), intlocs.getInt(5), intlocs.getInt(6), intlocs.getInt(7)};
//                    System.out.println(intlocs.getInt(0)+" , " +intlocs.getInt(1));
                    rects.add(loc);
                }

                JSONArray array = jsonobj1.getJSONArray("lines");
                for(int j = 0; j<array.length(); j++){
                    JSONObject obj = array.getJSONObject(j);
                    String line = obj.getString("line");
                    JSONArray coords = obj.getJSONArray("coords");
                    int [] contentLoc = new int[]{coords.getInt(0), coords.getInt(1), coords.getInt(2), coords.getInt(3)
                            ,coords.getInt(4), coords.getInt(5), coords.getInt(6), coords.getInt(7)};

                    int XCenterLoc = (contentLoc[0]+contentLoc[2]+contentLoc[4]+contentLoc[6])/4;
                    int YCenterLoc = (contentLoc[1]+contentLoc[3]+contentLoc[5]+contentLoc[7])/4;
                    if(j <= 20) {
                        for(Iterator<int []> it = rects.iterator(); it.hasNext();) {
                            int [] tagloc = it.next();
                            //(tagloc[0], tagloc[1]), (tagloc[2], tagloc[3]), (tagloc[4], tagloc[5]), (tagloc[6], tagloc[7])
                            //
                            if (tagloc[0]<XCenterLoc  && XCenterLoc<tagloc[2] && tagloc[1]<YCenterLoc && YCenterLoc<tagloc[7]) {

                                //break;
                            }
                        }
                    }
                }
            }
        }
    }


}
