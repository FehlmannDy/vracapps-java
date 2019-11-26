
package ch.vracapps.splashscreen.model.Edeman_Classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "label",
    "tag",
    "schemaOrgTag",
    "total",
    "hasRDI",
    "daily",
    "unit",
    "sub"
})
public class Digest implements Parcelable {

    @JsonProperty("label")
    private String label;
    @JsonProperty("tag")
    private String tag;
    @JsonProperty("schemaOrgTag")
    private Object schemaOrgTag;
    @JsonProperty("total")
    private Double total;
    @JsonProperty("hasRDI")
    private Boolean hasRDI;
    @JsonProperty("daily")
    private Double daily;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("sub")
    private List<Sub> sub = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Digest() {

    }

    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    @JsonProperty("tag")
    public String getTag() {
        return tag;
    }

    @JsonProperty("tag")
    public void setTag(String tag) {
        this.tag = tag;
    }

    @JsonProperty("schemaOrgTag")
    public Object getSchemaOrgTag() {
        return schemaOrgTag;
    }

    @JsonProperty("schemaOrgTag")
    public void setSchemaOrgTag(Object schemaOrgTag) {
        this.schemaOrgTag = schemaOrgTag;
    }

    @JsonProperty("total")
    public Double getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Double total) {
        this.total = total;
    }

    @JsonProperty("hasRDI")
    public Boolean getHasRDI() {
        return hasRDI;
    }

    @JsonProperty("hasRDI")
    public void setHasRDI(Boolean hasRDI) {
        this.hasRDI = hasRDI;
    }

    @JsonProperty("daily")
    public Double getDaily() {
        return daily;
    }

    @JsonProperty("daily")
    public void setDaily(Double daily) {
        this.daily = daily;
    }

    @JsonProperty("unit")
    public String getUnit() {
        return unit;
    }

    @JsonProperty("unit")
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @JsonProperty("sub")
    public List<Sub> getSub() {
        return sub;
    }

    @JsonProperty("sub")
    public void setSub(List<Sub> sub) {
        this.sub = sub;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


    public Digest(Parcel in) {
        label = in.readString();
        tag = in.readString();
        schemaOrgTag = (Object) in.readValue(Object.class.getClassLoader());
        total = in.readByte() == 0x00 ? null : in.readDouble();
        byte hasRDIVal = in.readByte();
        hasRDI = hasRDIVal == 0x02 ? null : hasRDIVal != 0x00;
        daily = in.readByte() == 0x00 ? null : in.readDouble();
        unit = in.readString();
        if (in.readByte() == 0x01) {
            sub = new ArrayList<Sub>();
            in.readList(sub, Sub.class.getClassLoader());
        } else {
            sub = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(label);
        dest.writeString(tag);
        dest.writeValue(schemaOrgTag);
        if (total == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(total);
        }
        if (hasRDI == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (hasRDI ? 0x01 : 0x00));
        }
        if (daily == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(daily);
        }
        dest.writeString(unit);
        if (sub == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(sub);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Digest> CREATOR = new Parcelable.Creator<Digest>() {
        @Override
        public Digest createFromParcel(Parcel in) {
            return new Digest(in);
        }

        @Override
        public Digest[] newArray(int size) {
            return new Digest[size];
        }
    };
}
