package com.daimler.service;

import com.daimler.model.VrcModel;
import com.daimler.ocr.VRCProcessJob;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class VrcRecognizeService {

    public VrcModel parseVrcModel(File pdfFile) throws Exception {

        System.out.println(pdfFile.getName());
        VRCProcessJob.splitImage();

        return VRCProcessJob.ocr();

    }
}
