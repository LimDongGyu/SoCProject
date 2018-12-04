package koreatech.cse.domain.rest;

import java.sql.Date;

public class Water {
    private String springID;
    private String address1;
    private String address2;
    private int latitude;
    private int longitude;
    private Date firstdate;
    private int averageuser;
    private Date testeddate;
    private String testresult;
    private String failresult;
    private String callnumber;
    private String institute;
    private Date datadate;
    private int instt_code;
    private String instt_name;
    private int s_list;
    private int s_page;

    public String getSpringID() {
        return springID;
    }

    public void setSpringID(String springID) {
        this.springID = springID;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public Date getFirstdate() {
        return firstdate;
    }

    public void setFirstdate(Date firstdate) {
        this.firstdate = firstdate;
    }

    public int getAverageuser() {
        return averageuser;
    }

    public void setAverageuser(int averageuser) {
        this.averageuser = averageuser;
    }

    public Date getTesteddate() {
        return testeddate;
    }

    public void setTesteddate(Date testeddate) {
        this.testeddate = testeddate;
    }

    public String getTestresult() {
        return testresult;
    }

    public void setTestresult(String testresult) {
        this.testresult = testresult;
    }

    public String getFailresult() {
        return failresult;
    }

    public void setFailresult(String failresult) {
        this.failresult = failresult;
    }

    public String getCallnumber() {
        return callnumber;
    }

    public void setCallnumber(String callnumber) {
        this.callnumber = callnumber;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public Date getDatadate() {
        return datadate;
    }

    public void setDatadate(Date datadate) {
        this.datadate = datadate;
    }

    public int getInstt_code() {
        return instt_code;
    }

    public void setInstt_code(int instt_code) {
        this.instt_code = instt_code;
    }

    public String getInstt_name() {
        return instt_name;
    }

    public void setInstt_name(String instt_name) {
        this.instt_name = instt_name;
    }

    public int getS_list() {
        return s_list;
    }

    public void setS_list(int s_list) {
        this.s_list = s_list;
    }

    public int getS_page() {
        return s_page;
    }

    public void setS_page(int s_page) {
        this.s_page = s_page;
    }

    @Override
    public String toString() {
        return "Water{" +
                "springID='" + springID + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", firstdate=" + firstdate +
                ", averageuser=" + averageuser +
                ", testeddate=" + testeddate +
                ", testresult='" + testresult + '\'' +
                ", failresult='" + failresult + '\'' +
                ", callnumber='" + callnumber + '\'' +
                ", institute='" + institute + '\'' +
                ", datadate=" + datadate +
                ", instt_code=" + instt_code +
                ", instt_name='" + instt_name + '\'' +
                ", s_list=" + s_list +
                ", s_page=" + s_page +
                '}';
    }
}
