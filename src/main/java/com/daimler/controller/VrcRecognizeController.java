package com.daimler.controller;

import com.alibaba.fastjson.JSONObject;
import com.daimler.model.VrcModel;
import com.daimler.service.VrcRecognizeService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/vrc/recognition")
public class VrcRecognizeController {

    public static final String pdfPath = "C:/Users/xiao/Desktop/test/pdf/";

    @Autowired
    private VrcRecognizeService service;

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity parseSaleContractSupple(
            @RequestParam(value = "file") List<MultipartFile> files) {

        if (files.isEmpty()) {
            JSONObject msg = new JSONObject();
            msg.put("error", "Parameter 'file' is missing");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }

        try {
            MultipartFile file = files.get(0);
            VrcModel vrcModel = service.parseVrcModel(multiPart2file(file));
            vrcModel.setFileName(file.getOriginalFilename());
            return ResponseEntity.ok().body(vrcModel);
        } catch (Exception e) {
            JSONObject msg = new JSONObject();
            msg.put("error", e.getMessage());
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }finally {
            File tmpFile = new File(pdfPath + files.get(0).getOriginalFilename());
            tmpFile.delete();
        }
    }


    private File  multiPart2file (MultipartFile multipartFile) {

        //文件上传前的名称
        String fileName = multipartFile.getOriginalFilename();
        File file = new File(pdfPath + fileName);
        OutputStream out = null;
        try{
            //获取文件流，以文件流的方式输出到新文件
//    InputStream in = multipartFile.getInputStream();
            out = new FileOutputStream(file);
            byte[] ss = multipartFile.getBytes();
            for(int i = 0; i < ss.length; i++){
                out.write(ss[i]);
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return file;
    }

}
