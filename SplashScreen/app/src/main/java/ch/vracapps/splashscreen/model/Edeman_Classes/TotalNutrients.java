
package ch.vracapps.splashscreen.model.Edeman_Classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "ENERC_KCAL",
    "FAT",
    "FASAT",
    "FATRN",
    "FAMS",
    "FAPU",
    "CHOCDF",
    "FIBTG",
    "SUGAR",
    "SUGAR.added",
    "PROCNT",
    "CHOLE",
    "NA",
    "CA",
    "MG",
    "K",
    "FE",
    "ZN",
    "P",
    "VITA_RAE",
    "VITC",
    "THIA",
    "RIBF",
    "NIA",
    "VITB6A",
    "FOLDFE",
    "FOLFD",
    "FOLAC",
    "VITB12",
    "VITD",
    "TOCPHA",
    "VITK1"
})
public class TotalNutrients implements Parcelable {

    @JsonProperty("ENERC_KCAL")
    private ENERCKCAL eNERCKCAL;
    @JsonProperty("FAT")
    private FAT fAT;
    @JsonProperty("FASAT")
    private FASAT fASAT;
    @JsonProperty("FATRN")
    private FATRN fATRN;
    @JsonProperty("FAMS")
    private FAMS fAMS;
    @JsonProperty("FAPU")
    private FAPU fAPU;
    @JsonProperty("CHOCDF")
    private CHOCDF cHOCDF;
    @JsonProperty("FIBTG")
    private FIBTG fIBTG;
    @JsonProperty("SUGAR")
    private SUGAR sUGAR;
    @JsonProperty("SUGAR.added")
    private SUGARAdded sUGARAdded;
    @JsonProperty("PROCNT")
    private PROCNT pROCNT;
    @JsonProperty("CHOLE")
    private CHOLE cHOLE;
    @JsonProperty("NA")
    private NA nA;
    @JsonProperty("CA")
    private CA cA;
    @JsonProperty("MG")
    private MG mG;
    @JsonProperty("K")
    private K k;
    @JsonProperty("FE")
    private FE fE;
    @JsonProperty("ZN")
    private ZN zN;
    @JsonProperty("P")
    private P p;
    @JsonProperty("VITA_RAE")
    private VITARAE vITARAE;
    @JsonProperty("VITC")
    private VITC vITC;
    @JsonProperty("THIA")
    private THIA tHIA;
    @JsonProperty("RIBF")
    private RIBF rIBF;
    @JsonProperty("NIA")
    private NIA nIA;
    @JsonProperty("VITB6A")
    private VITB6A vITB6A;
    @JsonProperty("FOLDFE")
    private FOLDFE fOLDFE;
    @JsonProperty("FOLFD")
    private FOLFD fOLFD;
    @JsonProperty("FOLAC")
    private FOLAC fOLAC;
    @JsonProperty("VITB12")
    private VITB12 vITB12;
    @JsonProperty("VITD")
    private VITD vITD;
    @JsonProperty("TOCPHA")
    private TOCPHA tOCPHA;
    @JsonProperty("VITK1")
    private VITK1 vITK1;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("ENERC_KCAL")
    public ENERCKCAL getENERCKCAL() {
        return eNERCKCAL;
    }

    @JsonProperty("ENERC_KCAL")
    public void setENERCKCAL(ENERCKCAL eNERCKCAL) {
        this.eNERCKCAL = eNERCKCAL;
    }

    @JsonProperty("FAT")
    public FAT getFAT() {
        return fAT;
    }

    @JsonProperty("FAT")
    public void setFAT(FAT fAT) {
        this.fAT = fAT;
    }

    @JsonProperty("FASAT")
    public FASAT getFASAT() {
        return fASAT;
    }

    @JsonProperty("FASAT")
    public void setFASAT(FASAT fASAT) {
        this.fASAT = fASAT;
    }

    @JsonProperty("FATRN")
    public FATRN getFATRN() {
        return fATRN;
    }

    @JsonProperty("FATRN")
    public void setFATRN(FATRN fATRN) {
        this.fATRN = fATRN;
    }

    @JsonProperty("FAMS")
    public FAMS getFAMS() {
        return fAMS;
    }

    @JsonProperty("FAMS")
    public void setFAMS(FAMS fAMS) {
        this.fAMS = fAMS;
    }

    @JsonProperty("FAPU")
    public FAPU getFAPU() {
        return fAPU;
    }

    @JsonProperty("FAPU")
    public void setFAPU(FAPU fAPU) {
        this.fAPU = fAPU;
    }

    @JsonProperty("CHOCDF")
    public CHOCDF getCHOCDF() {
        return cHOCDF;
    }

    @JsonProperty("CHOCDF")
    public void setCHOCDF(CHOCDF cHOCDF) {
        this.cHOCDF = cHOCDF;
    }

    @JsonProperty("FIBTG")
    public FIBTG getFIBTG() {
        return fIBTG;
    }

    @JsonProperty("FIBTG")
    public void setFIBTG(FIBTG fIBTG) {
        this.fIBTG = fIBTG;
    }

    @JsonProperty("SUGAR")
    public SUGAR getSUGAR() {
        return sUGAR;
    }

    @JsonProperty("SUGAR")
    public void setSUGAR(SUGAR sUGAR) {
        this.sUGAR = sUGAR;
    }

    @JsonProperty("SUGAR.added")
    public SUGARAdded getSUGARAdded() {
        return sUGARAdded;
    }

    @JsonProperty("SUGAR.added")
    public void setSUGARAdded(SUGARAdded sUGARAdded) {
        this.sUGARAdded = sUGARAdded;
    }

    @JsonProperty("PROCNT")
    public PROCNT getPROCNT() {
        return pROCNT;
    }

    @JsonProperty("PROCNT")
    public void setPROCNT(PROCNT pROCNT) {
        this.pROCNT = pROCNT;
    }

    @JsonProperty("CHOLE")
    public CHOLE getCHOLE() {
        return cHOLE;
    }

    @JsonProperty("CHOLE")
    public void setCHOLE(CHOLE cHOLE) {
        this.cHOLE = cHOLE;
    }

    @JsonProperty("NA")
    public NA getNA() {
        return nA;
    }

    @JsonProperty("NA")
    public void setNA(NA nA) {
        this.nA = nA;
    }

    @JsonProperty("CA")
    public CA getCA() {
        return cA;
    }

    @JsonProperty("CA")
    public void setCA(CA cA) {
        this.cA = cA;
    }

    @JsonProperty("MG")
    public MG getMG() {
        return mG;
    }

    @JsonProperty("MG")
    public void setMG(MG mG) {
        this.mG = mG;
    }

    @JsonProperty("K")
    public K getK() {
        return k;
    }

    @JsonProperty("K")
    public void setK(K k) {
        this.k = k;
    }

    @JsonProperty("FE")
    public FE getFE() {
        return fE;
    }

    @JsonProperty("FE")
    public void setFE(FE fE) {
        this.fE = fE;
    }

    @JsonProperty("ZN")
    public ZN getZN() {
        return zN;
    }

    @JsonProperty("ZN")
    public void setZN(ZN zN) {
        this.zN = zN;
    }

    @JsonProperty("P")
    public P getP() {
        return p;
    }

    @JsonProperty("P")
    public void setP(P p) {
        this.p = p;
    }

    @JsonProperty("VITA_RAE")
    public VITARAE getVITARAE() {
        return vITARAE;
    }

    @JsonProperty("VITA_RAE")
    public void setVITARAE(VITARAE vITARAE) {
        this.vITARAE = vITARAE;
    }

    @JsonProperty("VITC")
    public VITC getVITC() {
        return vITC;
    }

    @JsonProperty("VITC")
    public void setVITC(VITC vITC) {
        this.vITC = vITC;
    }

    @JsonProperty("THIA")
    public THIA getTHIA() {
        return tHIA;
    }

    @JsonProperty("THIA")
    public void setTHIA(THIA tHIA) {
        this.tHIA = tHIA;
    }

    @JsonProperty("RIBF")
    public RIBF getRIBF() {
        return rIBF;
    }

    @JsonProperty("RIBF")
    public void setRIBF(RIBF rIBF) {
        this.rIBF = rIBF;
    }

    @JsonProperty("NIA")
    public NIA getNIA() {
        return nIA;
    }

    @JsonProperty("NIA")
    public void setNIA(NIA nIA) {
        this.nIA = nIA;
    }

    @JsonProperty("VITB6A")
    public VITB6A getVITB6A() {
        return vITB6A;
    }

    @JsonProperty("VITB6A")
    public void setVITB6A(VITB6A vITB6A) {
        this.vITB6A = vITB6A;
    }

    @JsonProperty("FOLDFE")
    public FOLDFE getFOLDFE() {
        return fOLDFE;
    }

    @JsonProperty("FOLDFE")
    public void setFOLDFE(FOLDFE fOLDFE) {
        this.fOLDFE = fOLDFE;
    }

    @JsonProperty("FOLFD")
    public FOLFD getFOLFD() {
        return fOLFD;
    }

    @JsonProperty("FOLFD")
    public void setFOLFD(FOLFD fOLFD) {
        this.fOLFD = fOLFD;
    }

    @JsonProperty("FOLAC")
    public FOLAC getFOLAC() {
        return fOLAC;
    }

    @JsonProperty("FOLAC")
    public void setFOLAC(FOLAC fOLAC) {
        this.fOLAC = fOLAC;
    }

    @JsonProperty("VITB12")
    public VITB12 getVITB12() {
        return vITB12;
    }

    @JsonProperty("VITB12")
    public void setVITB12(VITB12 vITB12) {
        this.vITB12 = vITB12;
    }

    @JsonProperty("VITD")
    public VITD getVITD() {
        return vITD;
    }

    @JsonProperty("VITD")
    public void setVITD(VITD vITD) {
        this.vITD = vITD;
    }

    @JsonProperty("TOCPHA")
    public TOCPHA getTOCPHA() {
        return tOCPHA;
    }

    @JsonProperty("TOCPHA")
    public void setTOCPHA(TOCPHA tOCPHA) {
        this.tOCPHA = tOCPHA;
    }

    @JsonProperty("VITK1")
    public VITK1 getVITK1() {
        return vITK1;
    }

    @JsonProperty("VITK1")
    public void setVITK1(VITK1 vITK1) {
        this.vITK1 = vITK1;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


    protected TotalNutrients(Parcel in) {
        eNERCKCAL = (ENERCKCAL) in.readValue(ENERCKCAL.class.getClassLoader());
        fAT = (FAT) in.readValue(FAT.class.getClassLoader());
        fASAT = (FASAT) in.readValue(FASAT.class.getClassLoader());
        fATRN = (FATRN) in.readValue(FATRN.class.getClassLoader());
        fAMS = (FAMS) in.readValue(FAMS.class.getClassLoader());
        fAPU = (FAPU) in.readValue(FAPU.class.getClassLoader());
        cHOCDF = (CHOCDF) in.readValue(CHOCDF.class.getClassLoader());
        fIBTG = (FIBTG) in.readValue(FIBTG.class.getClassLoader());
        sUGAR = (SUGAR) in.readValue(SUGAR.class.getClassLoader());
        sUGARAdded = (SUGARAdded) in.readValue(SUGARAdded.class.getClassLoader());
        pROCNT = (PROCNT) in.readValue(PROCNT.class.getClassLoader());
        cHOLE = (CHOLE) in.readValue(CHOLE.class.getClassLoader());
        nA = (NA) in.readValue(NA.class.getClassLoader());
        cA = (CA) in.readValue(CA.class.getClassLoader());
        mG = (MG) in.readValue(MG.class.getClassLoader());
        k = (K) in.readValue(K.class.getClassLoader());
        fE = (FE) in.readValue(FE.class.getClassLoader());
        zN = (ZN) in.readValue(ZN.class.getClassLoader());
        p = (P) in.readValue(P.class.getClassLoader());
        vITARAE = (VITARAE) in.readValue(VITARAE.class.getClassLoader());
        vITC = (VITC) in.readValue(VITC.class.getClassLoader());
        tHIA = (THIA) in.readValue(THIA.class.getClassLoader());
        rIBF = (RIBF) in.readValue(RIBF.class.getClassLoader());
        nIA = (NIA) in.readValue(NIA.class.getClassLoader());
        vITB6A = (VITB6A) in.readValue(VITB6A.class.getClassLoader());
        fOLDFE = (FOLDFE) in.readValue(FOLDFE.class.getClassLoader());
        fOLFD = (FOLFD) in.readValue(FOLFD.class.getClassLoader());
        fOLAC = (FOLAC) in.readValue(FOLAC.class.getClassLoader());
        vITB12 = (VITB12) in.readValue(VITB12.class.getClassLoader());
        vITD = (VITD) in.readValue(VITD.class.getClassLoader());
        tOCPHA = (TOCPHA) in.readValue(TOCPHA.class.getClassLoader());
        vITK1 = (VITK1) in.readValue(VITK1.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(eNERCKCAL);
        dest.writeValue(fAT);
        dest.writeValue(fASAT);
        dest.writeValue(fATRN);
        dest.writeValue(fAMS);
        dest.writeValue(fAPU);
        dest.writeValue(cHOCDF);
        dest.writeValue(fIBTG);
        dest.writeValue(sUGAR);
        dest.writeValue(sUGARAdded);
        dest.writeValue(pROCNT);
        dest.writeValue(cHOLE);
        dest.writeValue(nA);
        dest.writeValue(cA);
        dest.writeValue(mG);
        dest.writeValue(k);
        dest.writeValue(fE);
        dest.writeValue(zN);
        dest.writeValue(p);
        dest.writeValue(vITARAE);
        dest.writeValue(vITC);
        dest.writeValue(tHIA);
        dest.writeValue(rIBF);
        dest.writeValue(nIA);
        dest.writeValue(vITB6A);
        dest.writeValue(fOLDFE);
        dest.writeValue(fOLFD);
        dest.writeValue(fOLAC);
        dest.writeValue(vITB12);
        dest.writeValue(vITD);
        dest.writeValue(tOCPHA);
        dest.writeValue(vITK1);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TotalNutrients> CREATOR = new Parcelable.Creator<TotalNutrients>() {
        @Override
        public TotalNutrients createFromParcel(Parcel in) {
            return new TotalNutrients(in);
        }

        @Override
        public TotalNutrients[] newArray(int size) {
            return new TotalNutrients[size];
        }
    };
}
