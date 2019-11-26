
package ch.vracapps.splashscreen.model.Edeman_Classes;

import android.annotation.SuppressLint;
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

@SuppressLint("ParcelCreator")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "uri",
    "label",
    "image",
    "source",
    "url",
    "shareAs",
    "yield",
    "dietLabels",
    "healthLabels",
    "cautions",
    "ingredientLines",
    "ingredients",
    "calories",
    "totalWeight",
    "totalNutrients",
    "totalDaily",
    "digest"
})
public class Recipe implements Parcelable {

    @JsonProperty("uri")
    private String uri;
    @JsonProperty("label")
    private String label;
    @JsonProperty("image")
    private String image;
    @JsonProperty("source")
    private String source;
    @JsonProperty("url")
    private String url;
    @JsonProperty("shareAs")
    private String shareAs;
    @JsonProperty("yield")
    private Double yield;
    @JsonProperty("dietLabels")
    private List<String> dietLabels = null;
    @JsonProperty("healthLabels")
    private List<String> healthLabels = null;
    @JsonProperty("cautions")
    private List<String> cautions = null;
    @JsonProperty("ingredientLines")
    private List<String> ingredientLines = null;
    @JsonProperty("ingredients")
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();
    @JsonProperty("calories")
    private Double calories;
    @JsonProperty("totalWeight")
    private Double totalWeight;
    @JsonProperty("totalNutrients")
    private TotalNutrients totalNutrients;
    @JsonProperty("totalDaily")
    private TotalDaily totalDaily;
    @JsonProperty("digest")
    private List<Digest> digest = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Recipe() {

    }

    protected Recipe(Parcel in) {
        uri = in.readString();
        label = in.readString();
        image = in.readString();
        source = in.readString();
        url = in.readString();
        shareAs = in.readString();
        yield = in.readByte() == 0x00 ? null : in.readDouble();
        if (in.readByte() == 0x01) {
            dietLabels = new ArrayList<String>();
            in.readList(dietLabels, String.class.getClassLoader());
        } else {
            dietLabels = null;
        }
        if (in.readByte() == 0x01) {
            healthLabels = new ArrayList<String>();
            in.readList(healthLabels, String.class.getClassLoader());
        } else {
            healthLabels = null;
        }
        if (in.readByte() == 0x01) {
            cautions = new ArrayList<String>();
            in.readList(cautions, String.class.getClassLoader());
        } else {
            cautions = null;
        }
        if (in.readByte() == 0x01) {
            ingredientLines = new ArrayList<String>();
            in.readList(ingredientLines, String.class.getClassLoader());
        } else {
            ingredientLines = null;
        }
        if (in.readByte() == 0x01) {
            ingredients = new ArrayList<Ingredient>();
            in.readList(ingredients, Ingredient.class.getClassLoader());
        } else {
            ingredients = null;
        }
        calories = in.readByte() == 0x00 ? null : in.readDouble();
        totalWeight = in.readByte() == 0x00 ? null : in.readDouble();
        totalNutrients = (TotalNutrients) in.readValue(TotalNutrients.class.getClassLoader());
        totalDaily = (TotalDaily) in.readValue(TotalDaily.class.getClassLoader());
        if (in.readByte() == 0x01) {
            digest = new ArrayList<Digest>();
            in.readList(digest, Digest.class.getClassLoader());
        } else {
            digest = null;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uri);
        dest.writeString(label);
        dest.writeString(image);
        dest.writeString(source);
        dest.writeString(url);
        dest.writeString(shareAs);
        if (yield == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(yield);
        }
        if (dietLabels == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(dietLabels);
        }
        if (healthLabels == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(healthLabels);
        }
        if (cautions == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(cautions);
        }
        if (ingredientLines == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ingredientLines);
        }
        if (ingredients == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ingredients);
        }
        if (calories == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(calories);
        }
        if (totalWeight == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(totalWeight);
        }
        dest.writeValue(totalNutrients);
        dest.writeValue(totalDaily);
        if (digest == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(digest);
        }
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @JsonProperty("uri")
    public String getUri() {
        return uri;
    }

    @JsonProperty("uri")
    public void setUri(String uri) {
        this.uri = uri;
    }

    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    @JsonProperty("image")
    public void setImage(String image) {
        this.image = image;
    }

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("shareAs")
    public String getShareAs() {
        return shareAs;
    }

    @JsonProperty("shareAs")
    public void setShareAs(String shareAs) {
        this.shareAs = shareAs;
    }

    @JsonProperty("yield")
    public Double getYield() {
        return yield;
    }

    @JsonProperty("yield")
    public void setYield(Double yield) {
        this.yield = yield;
    }

    @JsonProperty("dietLabels")
    public List<String> getDietLabels() {
        return dietLabels;
    }

    @JsonProperty("dietLabels")
    public void setDietLabels(List<String> dietLabels) {
        this.dietLabels = dietLabels;
    }

    @JsonProperty("healthLabels")
    public List<String> getHealthLabels() {
        return healthLabels;
    }

    @JsonProperty("healthLabels")
    public void setHealthLabels(List<String> healthLabels) {
        this.healthLabels = healthLabels;
    }

    @JsonProperty("cautions")
    public List<String> getCautions() {
        return cautions;
    }

    @JsonProperty("cautions")
    public void setCautions(ArrayList<String> cautions) {
        this.cautions = cautions;
    }

    @JsonProperty("ingredientLines")
    public List<String> getIngredientLines() {
        return ingredientLines;
    }

    @JsonProperty("ingredientLines")
    public void setIngredientLines(List<String> ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    @JsonProperty("ingredients")
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @JsonProperty("ingredients")
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @JsonProperty("calories")
    public Double getCalories() {
        return calories;
    }

    @JsonProperty("calories")
    public void setCalories(Double calories) {
        this.calories = calories;
    }

    @JsonProperty("totalWeight")
    public Double getTotalWeight() {
        return totalWeight;
    }

    @JsonProperty("totalWeight")
    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    @JsonProperty("totalNutrients")
    public TotalNutrients getTotalNutrients() {
        return totalNutrients;
    }

    @JsonProperty("totalNutrients")
    public void setTotalNutrients(TotalNutrients totalNutrients) {
        this.totalNutrients = totalNutrients;
    }

    @JsonProperty("totalDaily")
    public TotalDaily getTotalDaily() {
        return totalDaily;
    }

    @JsonProperty("totalDaily")
    public void setTotalDaily(TotalDaily totalDaily) {
        this.totalDaily = totalDaily;
    }

    @JsonProperty("digest")
    public List<Digest> getDigest() {
        return digest;
    }

    @JsonProperty("digest")
    public void setDigest(List<Digest> digest) {
        this.digest = digest;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
