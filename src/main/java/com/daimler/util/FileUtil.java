package com.daimler.util;

//import com.daimler.ocr.apidebug.model.ocr.CSVColumn;
//import com.daimler.ocr.apidebug.model.ocr.CSVHeader;
//import com.daimler.ocr.apidebug.model.ocr.CSVModel;
//import com.daimler.ocr.apidebug.model.ocr.CSVRow;
//import com.opencsv.CSVReader;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class FileUtil {
    /**
     * get file list in specify folder
     *
     * @param folderPath
     * @return
     */
    public static List<File> getFileList(String folderPath) {
        if (com.daimler.util.StringUtils.isNullOrEmpty(folderPath)) {
            return null;
        }

        File folder = new File(folderPath);
        if (!folder.isDirectory()) {
            return null;
        }

        return Arrays.asList(folder.listFiles());
    }

    /**
     * get file suffix name
     *
     * @param file
     * @return
     */
    public static String getSuffixName(File file) {
        if (file == null || file.isDirectory()) {
            return null;
        }

        String fileName = file.getName();

        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

        return suffix;
    }

    /**
     * get file suffix name
     *
     * @param file
     * @return
     */
    public static String getFileNameWithoutSuffixName(File file) {
        if (file == null || file.isDirectory()) {
            return null;
        }

        String fileName = file.getName();

        fileName = fileName.substring(0, fileName.lastIndexOf("."));

        return fileName;
    }

//    /**
//     * read csv file
//     *
//     * @param filePath
//     * @return
//     */
//    public static CSVModel readCSVFile(String filePath, Boolean hasHeader) {
//        if (StringUtils.isNullOrEmpty(filePath)) {
//            return null;
//        }
//
//        File file = new File(filePath);
//
//        return readCSVFile(file, hasHeader);
//    }


//    /**
//     * read csv file
//     *
//     * @param file
//     * @param hasHeader
//     * @return
//     */
//    public static CSVModel readCSVFile(File file, Boolean hasHeader) {
//        InputStream inputStream = null;
//        List<String[]> content = null;
//
//        CSVModel csvModel = null;
//        try {
//            inputStream = new DataInputStream(new FileInputStream(file));
//            csvModel = readCSVContent(inputStream, hasHeader);
//            csvModel.setFileName(file.getName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//            } catch (Exception e) {
//            }
//        }
//        return csvModel;
//    }

    public static String convertToBase64(File file) {
        if (file == null) {
            return null;
        }

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);

            byte[] buffer = IOUtils.toByteArray(fileInputStream);

            byte[] encodeBase64 = Base64.encodeBase64(buffer);

            String base64EncodeStr = new String(encodeBase64);

            return base64EncodeStr;
        } catch (Exception ex) {
            // TODO: handle exception

        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public static String convertToBase64(byte[] fileBytes) {
        if (fileBytes == null) {
            return null;
        }

        try {
            byte[] encodeBase64 = Base64.encodeBase64(fileBytes);

            String base64EncodeStr = new String(encodeBase64);

            return base64EncodeStr;
        } catch (Exception ex) {
            // TODO: handle exception

        } finally {

        }

        return null;
    }


//    private static CSVModel readCSVContent(InputStream inputStream) {
//        return readCSVContent(inputStream, false);
//    }
//
//    private static CSVModel readCSVContent(InputStream inputStream, Boolean hasHeader) {
//        hasHeader = hasHeader == null ? false : hasHeader;
//        BufferedReader reader = null;
//        CSVReader csvReader = null;
//        CSVModel csvModel = null;
//
//        try {
//            csvModel = new CSVModel();
//            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//            csvReader = new CSVReader(reader, ',');
//
//            String[] _contents;
//            int _index = 0;
//            int _columnCount = 0;
//            CSVRow _row = null;
//
//            HashMap<String, String>  uniqueApplNumbers = new HashMap<String, String>();
//
//            List<CSVColumn> _columns = new ArrayList<>();
//            CSVColumn _tempCSVColumn = null;
//            while ((_contents = csvReader.readNext()) != null) {
//                if (_index == 0) {
//                    for (Integer _cIndex = 0; _cIndex < _contents.length; _cIndex++) {
//                        //if no exist header then column is index else header is header name
//                        _columns.add(new CSVColumn(hasHeader ? _contents[_cIndex] : _cIndex.toString()));
//                    }
//                    csvModel.setColumns(_columns);
//                    csvModel.setColumnCount(_columns.size());
//
//                    if (hasHeader) {
//                        CSVHeader _header = new CSVHeader();
//                        _header.addAll(_columns);
//                        csvModel.setHeader(_header);
//                        _index++;
//                        continue;
//                    }
//                }
//
//                if (_contents.length != _columns.size()) {
//                    _index++;
//                    continue;
//                }
//
//                _row = new CSVRow();
//                boolean rowIsUnique = true;
//                for (int _cIndex = 0; _cIndex < _contents.length; _cIndex++) {
//                    try {
//                        _tempCSVColumn = csvModel.getColumns().get(_cIndex);
//
//                        if(_tempCSVColumn.getName().equalsIgnoreCase("APPL_NUMB"))
//                        {
//                            String keyValue = _contents[_cIndex];
//                            if(uniqueApplNumbers.containsKey(keyValue)){
//                                rowIsUnique = false;
//                                break;
//                            }else{
//                                uniqueApplNumbers.put(keyValue, keyValue);
//                            }
//                        }
//                        _row.add(_tempCSVColumn, _contents[_cIndex]);
//                    } catch (Exception ex) {
//                        //TODO log
//                        //record error log: content row column count is more than header count
//                    }
//                }
//
//                if(rowIsUnique){
//                    csvModel.getRows().add(_row);
//                }
//
//                _index++;
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//                if (reader != null) {
//                    reader.close();
//                }
//                if (csvReader != null) {
//                    csvReader.close();
//                }
//            } catch (Exception e) {
//            }
//        }
//        return csvModel;
//    }

    /**
     * check directory path is exist or not
     *
     * @param dirPath
     * @param isCreate, if this parameter is true, and the directory is not exist. then create one new direcotry
     * @return
     */
    public static Boolean checkDirExist(String dirPath, Boolean isCreate) {
        File file = new File(dirPath);

        if (file.exists()) {
            return true;
        }

        if (isCreate) {
            file.mkdirs();
        }

        return false;
    }

    /**
     * correct file path
     *
     * @param filePath
     * @return
     */
    public static String correctFilePath(String filePath) {
        if (com.daimler.util.StringUtils.isNullOrEmpty(filePath)) {
            return filePath;
        }

        filePath = filePath.replace("\\", "/");

        return filePath;
    }

    /**
     * @param filePath
     * @param content
     */
    public static void writeFile(String filePath, String content) {

        if (com.daimler.util.StringUtils.isNullOrEmpty(filePath) || com.daimler.util.StringUtils.isNullOrEmpty(content)) {
            return;
        }

        filePath = correctFilePath(filePath);

        File file = new File(filePath);

        checkDirExist(file.getParent(), true);

        OutputStreamWriter fw = null;
        BufferedWriter bw = null;

        try {
            fw = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
            bw = new BufferedWriter(fw);
            bw.write(content);
            bw.flush();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }

                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
