package com.daimler.model;

import lombok.Data;

@Data
public class VrcModel {

    private String fileName;
    private String ownerName_ocrValue;
    private String certificateNumber_ocrValue;
    private String registrationAuthority_ocrValue;
    private String vehicleNumber_ocrValue;
    private String vehicleBrand_ocrValue;
    private String vehicleModel_ocrValue;
    private String vinNumber_ocrValue;
    private String engineNumber_ocrValue;
    private Boolean authorityChop_ocrValue;
    private String vehicleCertificateNumber_ocrValue;
    private String mortgageeName_ocrValue;
    private String organizationCode_ocrValue;
    private String transferRecord_ocrValue;
}
