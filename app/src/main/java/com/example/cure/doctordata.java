package com.example.cure;

public class doctordata
{
    public String dfirstName,dlastName,dpassword,demail;
    public String certificate;
    public String url;

    public doctordata(String s, String toString){}

    public doctordata(String dfirstName, String dlastName, String dpassword, String demail,String certificate,String url) {
        this.dfirstName = dfirstName;
        this.dlastName = dlastName;
        this.dpassword = dpassword;
        this.demail = demail;
        this.certificate=certificate;
        this.url=url;

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

    public String getDpassword() {
        return dpassword;
    }

    public void setDpassword(String dpassword) {
        this.dpassword = dpassword;
    }

    public String getDemail() {
        return demail;
    }

    public void setDemail(String demail) {
        this.demail = demail;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    }
