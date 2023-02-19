package com.example.cure;

public class manage_doc {

    String demail,dfirstName,dlastName,certificate;

    manage_doc(){}

    public manage_doc(String demail, String dfirstName, String dlastName, String certificate) {
        this.demail = demail;
        this.dfirstName = dfirstName;
        this.dlastName = dlastName;
        this.certificate = certificate;
    }

    public String getDemail() {
        return demail;
    }

    public void setDemail(String demail) {
        this.demail = demail;
    }

    public String getDfirstName() {
        return dfirstName;
    }

    public void setDfirstName(String dfirstName) {
        this.dfirstName = dfirstName;
    }

    public String getDlastName() {
        return dlastName;
    }

    public void setDlastName(String dlastName) {
        this.dlastName = dlastName;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }
}
