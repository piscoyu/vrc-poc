package com.daimler.ocr;

import com.daimler.util.FileUtil;
import com.daimler.util.HttpUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;
import java.net.URLEncoder;
import java.util.*;

public class HanvonOCR {
    //String OCRURL = "https://ocr-dr.cn.bg.corpintra.net/ocrft";
    private static final String OCRURL = "http://53.89.255.135:8011/ocrft";
    private static final String OCRURLbackup = "http://53.89.255.58:9000/ocrft";

    private static final String boxDetectionURL = "http://localhost:8080/boxDetection";

    private static final String tableDetectionURL = "http://localhost:8080/tableDetection";

    public static final void main(String []argc) throws Exception {


//        part0_OCR();
//        part1_OCR();
//        sortLine();
//        boxDetection();
//        tableDetection();
    }



    public static void part0_OCR(String imageFolderPath, String fileEndPattern) throws Exception {
        //String imagePath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\raw_0\\";
//        String imagePath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\1.xx\\";
        File path = new File(imageFolderPath);
        String []fileNames = path.list();
        for(int i = 0; i< fileNames.length; i++) {
            // System.out.println(path + fileNames[i]);
            String child = imageFolderPath + fileNames[i];
            if(child.endsWith(fileEndPattern))//"000_0.jpg"
            {
                String content = OCR(child);
                String outputPath = imageFolderPath + fileNames[i].substring(0, fileNames[i].length() - 3) + "json";
                System.out.println(outputPath);
                FileUtil.writeFile(outputPath, content);
                //if (i == 10) break;
            }
        }
    }

    public static void part1_OCR(String imagePath, String fileEndPattern) throws Exception {
        //String imagePath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\raw_1\\";

        File path = new File(imagePath);
        String []fileNames = path.list();
        for(int i = 0; i< fileNames.length; i++) {
            // System.out.println(path + fileNames[i]);
            String child = imagePath + fileNames[i];
            if(child.endsWith(fileEndPattern))//"000_1.jpg"
            {
                String content = OCR(child);
                String outputPath = imagePath + fileNames[i].substring(0, fileNames[i].length() - 3) + "json";
                System.out.println(outputPath);
                FileUtil.writeFile(outputPath, content);
                //if (i == 10) break;
            }
        }
    }

    public static void part2_OCR(String imagePath, String fileEndPattern) throws Exception {
        //String imagePath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\raw_1\\";

        File path = new File(imagePath);
        String []fileNames = path.list();
        for(int i = 0; i< fileNames.length; i++) {
            // System.out.println(path + fileNames[i]);
            String child = imagePath + fileNames[i];
            if(child.endsWith(fileEndPattern))//"000_1.jpg"
            {
                String content = OCR(child);
                String outputPath = imagePath + fileNames[i].substring(0, fileNames[i].length() - 3) + "json";
                System.out.println(outputPath);
                FileUtil.writeFile(outputPath, content);
                //if (i == 10) break;
            }
        }
    }

    public static void part0_BoxDetection(String imageFolderPath, String fileEndPattern) throws Exception {
        //String imagePath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\raw_0\\";
//        String imagePath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\1.xx\\";
        File path = new File(imageFolderPath);
        String []fileNames = path.list();
        for(int i = 0; i< fileNames.length; i++) {
            // System.out.println(path + fileNames[i]);
            String child = imageFolderPath + fileNames[i];
            if(child.endsWith(fileEndPattern))//"000_0.jpg"
            {
                String content = tableDetection(child);
                String outputPath = imageFolderPath + fileNames[i].substring(0, fileNames[i].length() - 4) + "_box.json";
                System.out.println(outputPath);
                FileUtil.writeFile(outputPath, content);
                //if (i == 10) break;
            }
        }
    }

    public static void part1_BoxDetection(String imagePath, String fileEndPattern) throws Exception {
        //String imagePath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\raw_1\\";

        File path = new File(imagePath);
        String []fileNames = path.list();
        for(int i = 0; i< fileNames.length; i++) {
            // System.out.println(path + fileNames[i]);
            String child = imagePath + fileNames[i];
            if(child.endsWith(fileEndPattern))//"000_0.jpg"
            {
                String content = boxDetection(child);
                String outputPath = imagePath + fileNames[i].substring(0, fileNames[i].length() - 4) + "_box.json";
                System.out.println(outputPath);
                FileUtil.writeFile(outputPath, content);
                //if (i == 10) break;
            }
        }
    }

    public static void part2_BoxDetection(String imageFolderPath, String fileEndPattern) throws Exception {
        //String imagePath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\raw_0\\";
//        String imagePath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\1.xx\\";
        File path = new File(imageFolderPath);
        String []fileNames = path.list();
        for(int i = 0; i< fileNames.length; i++) {
            // System.out.println(path + fileNames[i]);
            String child = imageFolderPath + fileNames[i];
            if(child.endsWith(fileEndPattern))//"000_0.jpg"
            {
                String content = tableDetection(child);
                String outputPath = imageFolderPath + fileNames[i].substring(0, fileNames[i].length() - 4) + "_box.json";
                System.out.println(outputPath);
                FileUtil.writeFile(outputPath, content);
                //if (i == 10) break;
            }
        }
    }

    private static String OCR(String imagePath) throws Exception{

        String base64Str = FileUtil.convertToBase64(new File(imagePath));

        base64Str = URLEncoder.encode(base64Str, "utf-8");

        String postData = String.format("base64img=%s", base64Str);

        String result = HttpUtils.post(OCRURLbackup, postData);//, proxyUrl, proxyPort);

//        System.out.println("::" + result);

        JSONObject root = new JSONObject(result);
        int code = root.getInt("code");

        System.out.println("::" + code);

        Map map = mergeLineItem(root);

        String stringJson = new JSONObject(map).toString();

        return stringJson;
    }

    private static String boxDetection(String filePath){

        File imageFile = new File(filePath);
        String result = HttpUtils.post(boxDetectionURL, imageFile, "image/jpeg");
//        System.out.println(result);
        return result;
    }

    private static String tableDetection(String filePath){

        File imageFile = new File(filePath);
        String result = HttpUtils.post(tableDetectionURL, imageFile, "image/jpeg");
//        System.out.println(result);
        return result;
    }


    private static Map mergeLineItem(JSONObject jsonobj) {

        Map results = new HashMap();

        JSONArray array = jsonobj.getJSONObject("result").getJSONArray("lines");
        //Content [] contents = new Content[array.length()];

        ArrayList<Content> list = new ArrayList<Content>();
        for(int i = 0; i < array.length(); i++ ){
            JSONArray charArray = ((JSONObject)array.get(i)).getJSONArray("chars");
            //   System.out.println(array.get(i));
            StringBuffer sb = new StringBuffer();
            for(int j = 0; j < charArray.length(); j++ ){
                String code = ((JSONObject)charArray.get(j)).get("code").toString();
                sb.append(code);
            }

            JSONArray coords = ((JSONObject)array.get(i)).getJSONArray("coords");

            //contents[i] = new Content(sb.toString(), coords);
            //contents.put(obj);
            list.add(new Content(sb.toString(), coords));
        }
        JSONArray contents = sortLineItem(list);
        results.put("lines", contents);
        return results;
    }

    private static void testSortLine(String jsonPath) throws Exception{
        //String jsonPath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\raw_1\\";

        //String jsonLocPath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\1\\";
        File path = new File(jsonPath);
        String []fileNames = path.list();



        for(int i = 0; i< fileNames.length; i++) {
            ArrayList<Content> list = new ArrayList<Content>();
            // System.out.println(path + fileNames[i]);
            Map results = new HashMap();
            String child = jsonPath + fileNames[i];
            if (child.endsWith("000_1.json")) {// && fileNames[i].startsWith("en")

                JSONObject jsonobj1 = new JSONObject(new JSONTokener(new FileReader(new File(child))));
//                System.out.println(child);
                JSONArray array = jsonobj1.getJSONArray("lines");
                for(int j = 0; j<array.length(); j++) {
                    JSONObject obj = array.getJSONObject(j);
                    String line = obj.getString("line");
                    JSONArray coords = obj.getJSONArray("coords");
                    list.add(new Content(line, coords));
                }

                JSONArray arrayList = sortLineItem(list);

                results.put("lines", arrayList);

                String stringJson = new JSONObject(results).toString();
                // + "en"
                String outputPath = jsonPath + fileNames[i].substring(0, fileNames[i].length() - 4) + "json";
                System.out.println(outputPath);
                FileUtil.writeFile(outputPath, stringJson);
            }
//            if(i==10) break;
        }
    }

    private static JSONArray sortLineItem(ArrayList<Content> list){
        JSONArray contents = new JSONArray();
        //sort item by coords
        Collections.sort(list, new Comparator<Content>(){
            public int compare(Content a , Content b)
            {
                //contents.put(obj);
                int centerAX = ( a.getCoords().getInt(0) + a.getCoords().getInt(2) + a.getCoords().getInt(4) + a.getCoords().getInt(6))/4;
                int centerAY = ( a.getCoords().getInt(1) + a.getCoords().getInt(3) + a.getCoords().getInt(5) + a.getCoords().getInt(7))/4;

                int centerBX = ( b.getCoords().getInt(0) + b.getCoords().getInt(2) + b.getCoords().getInt(4) + b.getCoords().getInt(6))/4;
                int centerBY = ( b.getCoords().getInt(1) + b.getCoords().getInt(3) + b.getCoords().getInt(5) + b.getCoords().getInt(7))/4;

                if(Math.abs(centerAY - centerBY) < 30){
                return centerAX < centerBX ? -1: 1;
            }else{
                return centerAY < centerBY ? -1: 1;
            }
        }
        });
        int seq = 0;
        for(Iterator<Content> it = list.iterator(); it.hasNext();){
            JSONObject obj = new JSONObject();
            Content item = it.next();

            obj.put("line", item.getLine());
            obj.put("coords", item.getCoords());
            int centerAX = (item.getCoords().getInt(0)+item.getCoords().getInt(2)+item.getCoords().getInt(4)+item.getCoords().getInt(6))/4;
            int centerAY = (item.getCoords().getInt(1)+item.getCoords().getInt(3)+item.getCoords().getInt(5)+item.getCoords().getInt(7))/4;
            if(seq < 18)
                System.out.println(seq++ + " -- " + item.getLine() + ", (" + centerAX + ", " + centerAY+")");
            contents.put(obj);
        }
        return contents;
    }

    static class Content {
        String line;

        public String getLine() {
            return line;
        }

        public JSONArray getCoords() {
            return coords;
        }

        JSONArray coords;

        public Content(String line, JSONArray coords){
            this.line = line;
            this.coords = coords;
        }
    }

    public static final void part0_Merge(String contentPath, String contentFilePattern, String LocPath, String locFilePattern) throws Exception {
//        String jsonPath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\raw_0\\";
//
//        String jsonLocPath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\0\\";

        int []seq = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};

        VRC_Merge(contentPath, contentFilePattern, LocPath, locFilePattern, seq);
    }

    public static final void part1_Merge(String contentPath, String contentFilePattern, String LocPath, String locFilePattern) throws Exception {
//        String jsonPath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\raw_1\\";
//
//        String jsonLocPath = "C:\\project\\ocrtable\\table-ocr\\vrc_images-raw\\1.1\\1.x\\";

        int []seq = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        VRC_Merge(contentPath, contentFilePattern, LocPath, locFilePattern, seq);
    }

    private static final void VRC_Merge(String jsonPath, String contentFilePattern, String jsonLocPath, String locFilePattern, int []seq) throws Exception {

        File path = new File(jsonPath);
        String []fileNames = path.list();

        //int []seq = new int[]{11, 12};

        Map<Integer, List<Integer>> markedMap = new HashMap<Integer, List<Integer>>();

        for(int i = 0; i< fileNames.length; i++) {
            // System.out.println(path + fileNames[i]);
            String child = jsonPath + fileNames[i];
            String outputPath = jsonPath + fileNames[i];//+ "en"
            if (child.endsWith(contentFilePattern)) {//".json"

                JSONObject jsonobj1 = new JSONObject(new JSONTokener(new FileReader(new File(child))));
                System.out.println(child);
                String locFile = jsonLocPath + fileNames[i].substring(0, fileNames[i].length()-5) + "_box.json";
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


                //for each item in lines array
                for(int j = 0; j<array.length(); j++){
                    JSONObject obj = array.getJSONObject(j);
                    String line = obj.getString("line");
                    JSONArray coords = obj.getJSONArray("coords");
                    int [] contentLoc = new int[]{coords.getInt(0), coords.getInt(1), coords.getInt(2), coords.getInt(3)
                            ,coords.getInt(4), coords.getInt(5), coords.getInt(6), coords.getInt(7)};

                    int XCenterLoc = (contentLoc[0]+contentLoc[2]+contentLoc[4]+contentLoc[6])/4;
                    int YCenterLoc = (contentLoc[1]+contentLoc[3]+contentLoc[5]+contentLoc[7])/4;
                    if(j <= 30) {
                        int count = 0;
                        for(Iterator<int []> it = rects.iterator(); it.hasNext();) {

                            int [] tagloc = it.next();
                            //(tagloc[0], tagloc[1]), (tagloc[2], tagloc[3]), (tagloc[4], tagloc[5]), (tagloc[6], tagloc[7])
                            //
                            if (tagloc[0]<XCenterLoc  && XCenterLoc<tagloc[2] && tagloc[1]<YCenterLoc && YCenterLoc<tagloc[7]) {
                                //System.out.println(line);

                                if(markedMap.get(count) == null) markedMap.put( count, new ArrayList<Integer>());
                                List<Integer> markedItems = markedMap.get(count);
                                markedItems.add(j);
                            }
                            count++;
                        }
                    }
                }

                //remove marked items, add commbined item
                Set keyset = markedMap.keySet();
                List<Integer> allMarkedItems = new ArrayList<Integer>();
                for(Iterator<Integer> it = keyset.iterator(); it.hasNext();){

                    Integer key = it.next();
                    List<Integer> markedItems = markedMap.get(key);
                    if(markedItems.size()>1){
                        System.out.println("----------------------------");
                        StringBuffer sb = new StringBuffer();
                        for(Integer item: markedItems){
                            //System.out.println(array.getJSONObject(item).getString("line"));
                            sb.append(array.getJSONObject(item).getString("line"));
                        }
                        System.out.println(sb.toString());
                        array.getJSONObject(markedItems.get(0)).put("line", sb.toString());
                        markedItems.remove(0);

                        for(Integer item: markedItems){
                            System.out.println("File-lines: " + i + " , " + key + "--" + item);
                            allMarkedItems.add(item);
                        }
                    }
                }
                for(int seqItem = allMarkedItems.size()-1; seqItem >= 0; seqItem--){
                    Integer index = allMarkedItems.get(seqItem);
                    array.remove(index);
                }

                markedMap.clear();
                //if(i==10) break;
                //String child = jsonPath + fileNames[i];
                saveAsFile(outputPath, array);

            }//end if
        }//end-for
    }

    private static void saveAsFile(String outputPath, JSONArray array){
        Map results = new HashMap();
        results.put("lines", array);
        String content = new JSONObject(results).toString();
        FileUtil.writeFile(outputPath, content);
    }
}
