package ch.vracapps.splashscreen.database;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ch.vracapps.splashscreen.model.Edeman_Classes.Ingredient;
import ch.vracapps.splashscreen.model.Edeman_Classes.Recipe;
import ch.vracapps.splashscreen.model.Shop;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "bdd_vracapps.sqlite";
    public static final String DBLOCATION = "/data/data/ch.vracapps.splashscreen/databases/";

    private Context mContext;
    private SQLiteDatabase mDatabases;

    public DatabaseHelper(Context context){
        super(context,DBNAME,null,1);
        this.mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void openDatabase(){
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if(mDatabases !=null && mDatabases.isOpen()){
            return;
        }
        mDatabases = SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase(){
        if(mDatabases!=null){
            mDatabases.close();
        }
    }

    public List<Shop> getListShopMaps() {
        Shop shop = null;
        List<Shop> shopMapstList = new ArrayList<>();
        String strServices="";
        String strHoraire="";

        openDatabase();
        Cursor cursor = mDatabases.rawQuery("SELECT tbl_Magasins.idMagasin, tbl_Magasins.nomMagasin, tbl_Adresses.longitude, tbl_Adresses.latitude,tbl_Magasins.url_image,tbl_Magasins.siteWeb\n" +
                "FROM tbl_Adresses INNER JOIN (tbl_Adresses_Localites INNER JOIN tbl_Magasins ON tbl_Adresses_Localites.idAdressesLocalite = tbl_Magasins.id_Adresses_Localites) ON tbl_Adresses.idAdresse = tbl_Adresses_Localites.id_tbl_Adresses;",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            Cursor cursorservice = mDatabases.rawQuery("SELECT tbl_Magasins.idMagasin, tbl_Services.nomService\n" +
                    "FROM tbl_Services INNER JOIN (tbl_Magasins INNER JOIN tbl_Magasins_Services ON tbl_Magasins.idMagasin = tbl_Magasins_Services.id_Magasins) ON tbl_Services.idService = tbl_Magasins_Services.id_Services\n" +
                            "WHERE (((tbl_Magasins.idMagasin)="+cursor.getInt(0)+"));",null);
            cursorservice.moveToFirst();
            while (!cursorservice.isAfterLast()){
                strServices += cursorservice.getString(1)+" ";
                cursorservice.moveToNext();
            }

            Cursor cursorHoraire = mDatabases.rawQuery("SELECT tbl_Magasins.idMagasin, tbl_Horaires.heureDebut, tbl_Horaires.heureFin, tbl_Horaires.jour\n" +
                    "FROM tbl_Horaires INNER JOIN (tbl_Magasins INNER JOIN tbl_Magasins_Horaires ON tbl_Magasins.idMagasin = tbl_Magasins_Horaires.id_tbl_Magasins) ON tbl_Horaires.idHoraire = tbl_Magasins_Horaires.id_tbl_Horaires\n" +
                    "WHERE (((tbl_Magasins.idMagasin)="+cursor.getInt(0)+"));",null);
            cursorHoraire.moveToFirst();
            while (!cursorHoraire.isAfterLast()){
                strHoraire+=cursorHoraire.getString(3)+" : "+cursorHoraire.getString(1).split(" ")[1]+" "+cursorHoraire.getString(2).split(" ")[1]+"\n";
                cursorHoraire.moveToNext();
            }
            shop= new Shop(cursor.getInt(0),cursor.getString(1),cursor.getDouble(2),cursor.getDouble(3),strServices,strHoraire,cursor.getString(4), cursor.getString(5));
            shopMapstList.add(shop);
            strHoraire="";
            strServices="";
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return shopMapstList;
    }


    public boolean setInsertRecipe(Recipe recipe, Date date){
        boolean bMenusExist = false;

        openDatabase();

        Cursor cursor = mDatabases.rawQuery("SELECT tbl_Menus.uri,tbl_Menus.id FROM tbl_Menus;",null);
        cursor.moveToFirst();
            for (int i=0;i<cursor.getCount();i++){
                if(recipe.getUri().equals(cursor.getString(0))){
                    bMenusExist = true;
                    if(!getDateExist(date)){
                        //set date
                        setDateTable(date,cursor.getInt(1));
                        setIngredientTable(recipe,cursor.getInt(1));
                        setLabelSanteTable(recipe,cursor.getInt(1));
                        setLabelDietTable(recipe,cursor.getInt(1));
                    }

                }
                cursor.moveToNext();
            }

        if(!bMenusExist){
            //Insert Recipe object in database
            int idMenus = setMenuTable(recipe);
            setDateTable(date,idMenus);
            setIngredientTable(recipe,idMenus);
            setLabelSanteTable(recipe,idMenus);
            setLabelDietTable(recipe,idMenus);
        }

        mDatabases.close();

        return false;
    }

    private void setDateTable(Date date, int idMenus){
        Cursor cursor = mDatabases.rawQuery("SELECT id,dateHeure FROM tbl_Dates WHERE dateHeure = \""+dateFormat(date)+"\";",null);
        cursor.moveToFirst();
        if(cursor.getCount()!=0){
                setMenuDate(idMenus,cursor.getInt(0));
        }else{
            String sqlSetDate = ("INSERT INTO tbl_Dates (dateHeure) VALUES ('"+dateFormat(date)+"');");
            mDatabases.execSQL(sqlSetDate);
            Cursor cursorDate = mDatabases.rawQuery("SELECT MAX(id) FROM tbl_Dates;",null);
            cursorDate.moveToFirst();
            setMenuDate(idMenus,cursorDate.getInt(0));
        }



    }
    private void setMenuDate (int idMenus,int idDates){
        String sqlSetMenuDate = ("INSERT INTO tbl_Menus_Dates (id_Menus,id_Dates) VALUES ("+idMenus+","+idDates+");");
        mDatabases.execSQL(sqlSetMenuDate);
    }

    private boolean getDateExist (Date date){
        boolean bDatesExist = false;
        Cursor cursorDateExist = mDatabases.rawQuery("SELECT id, dateHeure FROM tbl_Dates;",null);
        cursorDateExist.moveToFirst();
        for (int ix=0;ix<cursorDateExist.getCount();ix++){
            if(date.toString().equals(cursorDateExist.getString(1))){
                bDatesExist=true;
            }
            cursorDateExist.moveToNext();
        }
        return bDatesExist;
    }

    private int setMenuTable (Recipe recipe){
        String sql = ("INSERT INTO tbl_Menus (label,image,urlSource,avertissements,calorie,grammesTotal,uri,nbPersonnes) " +
                "VALUES (\""+recipe.getLabel().replace("\"","\\\"")+"\",'"+recipe.getImage()+"',\""+recipe.getSource()+"\",\""+recipe.getCautions()+"\","+recipe.getCalories()+","+recipe.getTotalWeight()+",'"+recipe.getUri()+"',"+recipe.getYield()+")");
        mDatabases.execSQL(sql);
        Cursor cursor = mDatabases.rawQuery("SELECT MAX(id) FROM tbl_Menus;",null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    private void setIngredientTable(Recipe recipe, int idMenus){
        Cursor cursor = mDatabases.rawQuery("SELECT label,id FROM tbl_Ingredients;",null);

        for (int i = 0; i <recipe.getIngredients().size();i++) {
            boolean bool = false;
            cursor.moveToFirst();
            Ingredient ingredient = recipe.getIngredients().get(i);
            for (int ix = 0; ix < cursor.getCount(); ix++) {

                if (recipe.getIngredients().get(i).getText().equals(cursor.getString(0))) {
                    bool = true;
                    String sqlMenusIngredients = ("INSERT INTO tbl_Menus_Ingredients (id_Menus, id_Ingredients,resume,quantite,mesure,grammes,list) " +
                            "VALUES (" + idMenus + "," + cursor.getInt(1) + ",'" + ingredient.getText() + "'," + ingredient.getQuantity() + ",'" + ingredient.getMeasure() + "'," + ingredient.getWeight() + ",'False');");
                    mDatabases.execSQL(sqlMenusIngredients);
                }
                cursor.moveToNext();

            }
            if (!bool) {
                String sql = ("INSERT INTO tbl_Ingredients (label) VALUES ('" + recipe.getIngredients().get(i).getFood().toLowerCase() + "');");
                mDatabases.execSQL(sql);

                Cursor cursorMenusIngredients = mDatabases.rawQuery("SELECT MAX(id) FROM tbl_Ingredients;", null);
                cursorMenusIngredients.moveToFirst();

                String sqlMenusIngredients = ("INSERT INTO tbl_Menus_Ingredients (id_Menus, id_Ingredients,resume,quantite,mesure,grammes,list) " +
                        "VALUES (" + idMenus + "," + cursorMenusIngredients.getInt(0) + ",'" + ingredient.getText() + "'," + ingredient.getQuantity() + ",'" + ingredient.getMeasure() + "'," + ingredient.getWeight() + ",'False');");
                mDatabases.execSQL(sqlMenusIngredients);
            }
        }

        }

    private void setLabelSanteTable(Recipe recipe, int idMenus){
        boolean bool = false;
        Cursor cursor = mDatabases.rawQuery("SELECT label,id FROM tbl_LabelsSante;",null);

        for (int i = 0; i <recipe.getHealthLabels().size();i++){
            cursor.moveToFirst();
            for (int ix=0;ix<cursor.getCount();ix++){
                if(recipe.getHealthLabels().get(i).equals(cursor.getString(0)) || cursor.getCount() == 0){
                    bool=true;

                    String sqlMenusLabelsSante = ("INSERT INTO tbl_Menus_LabelsSante (id_Menus, id_LabelsSante) VALUES ("+idMenus+","+cursor.getInt(1)+");");
                    mDatabases.execSQL(sqlMenusLabelsSante);
                }
                cursor.moveToNext();
            }
            if(!bool){
                String sql = ("INSERT INTO tbl_LabelsSante (label) VALUES ('"+recipe.getHealthLabels().get(i)+"');");
                mDatabases.execSQL(sql);

                Cursor cursorMenusLabelsSante = mDatabases.rawQuery("SELECT MAX(id) FROM tbl_LabelsSante;",null);
                cursorMenusLabelsSante.moveToFirst();

                String sqlMenusLabelsSante = ("INSERT INTO tbl_Menus_LabelsSante (id_Menus, id_LabelsSante) VALUES ("+idMenus+","+cursorMenusLabelsSante.getInt(0)+");");
                mDatabases.execSQL(sqlMenusLabelsSante);
            }
        }
    }

    private void setLabelDietTable(Recipe recipe, int idMenus){
        boolean bool = false;
        Cursor cursor = mDatabases.rawQuery("SELECT label,id FROM tbl_LabelsDiet;",null);

        for (int i = 0; i <recipe.getDietLabels().size();i++){
            cursor.moveToFirst();
            for (int ix=0;ix<cursor.getCount();ix++){
                if(recipe.getDietLabels().get(i).equals(cursor.getString(0)) || cursor.getCount() == 0){
                    bool=true;

                    String sqlMenusLabelsDiet = ("INSERT INTO tbl_Menus_LabelsDiet (id_Menus, id_LabelsDiet) VALUES ("+idMenus+","+cursor.getInt(1)+");");
                    mDatabases.execSQL(sqlMenusLabelsDiet);
                }
                cursor.moveToNext();
            }
            if(!bool){
                String sql = ("INSERT INTO tbl_LabelsDiet (label) VALUES ('"+recipe.getDietLabels().get(i)+"');");
                mDatabases.execSQL(sql);

                Cursor cursorMenusLabelsDiet = mDatabases.rawQuery("SELECT MAX(id) FROM tbl_LabelsDiet;",null);
                cursorMenusLabelsDiet.moveToFirst();

                String sqlMenusLabelsDiet = ("INSERT INTO tbl_Menus_LabelsDiet (id_Menus, id_LabelsDiet) VALUES ("+idMenus+","+cursorMenusLabelsDiet.getInt(0)+");");
                mDatabases.execSQL(sqlMenusLabelsDiet);
            }
        }
    }

    private String dateFormat(Date date) {
        String myFormat = "yyyy-MM-dd HH:mm:ss"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        Log.d("dateFormat: ",sdf.format(date));
        return sdf.format(date);
    }


    public ArrayList<Recipe> getRecipes() {
        ArrayList<Recipe> mRecipe = new ArrayList<>();
        openDatabase();

        Cursor cursor = mDatabases.rawQuery("SELECT id, label, image, urlSource, avertissements, calorie, grammesTotal, uri, nbPersonnes FROM tbl_Menus;",null);
        cursor.moveToFirst();
        for (int i=0;i<cursor.getCount();i++){
            Recipe recipe = new Recipe();

            recipe.setLabel(cursor.getString(1));
            recipe.setImage(cursor.getString(2));
            recipe.setUrl(cursor.getString(3));
            //recipe.setCautions(cursor.getString(4));
            recipe.setCalories(cursor.getDouble(5));
            recipe.setTotalWeight(cursor.getDouble(6));
            recipe.setUri(cursor.getString(7));
            recipe.setYield(cursor.getDouble(8));

            //set ingredients
            Cursor cursorIngredients = mDatabases.rawQuery("SELECT tbl_Menus_Ingredients.resume, tbl_Menus_Ingredients.quantite, tbl_Menus_Ingredients.mesure, tbl_Menus_Ingredients.grammes, tbl_Ingredients.label\n" +
                    "FROM tbl_Menus INNER JOIN (tbl_Ingredients INNER JOIN tbl_Menus_Ingredients ON tbl_Ingredients.id = tbl_Menus_Ingredients.id_Ingredients) ON tbl_Menus.id = tbl_Menus_Ingredients.id_Menus\n" +
                    "WHERE (((tbl_Menus.id)="+cursor.getInt(0)+"));",null);
            cursorIngredients.moveToFirst();
            ArrayList<Ingredient> mIngredient = new ArrayList<>();
            for(int ix=0;ix<cursorIngredients.getCount();ix++){
                Ingredient ingredient = new Ingredient();
                ingredient.setText(cursorIngredients.getString(0));
                ingredient.setQuantity(cursorIngredients.getDouble(1));
                ingredient.setMeasure(cursorIngredients.getString(2));
                ingredient.setWeight(cursorIngredients.getDouble(3));
                ingredient.setFood(cursorIngredients.getString(4));
                mIngredient.add(ingredient);
                cursorIngredients.moveToNext();
            }
            recipe.setIngredients(mIngredient);

            //set labelsDiet
            Cursor cursorDiet = mDatabases.rawQuery("SELECT tbl_LabelsDiet.label\n" +
                    "FROM tbl_Menus INNER JOIN (tbl_LabelsDiet INNER JOIN tbl_Menus_LabelsDiet ON tbl_LabelsDiet.id = tbl_Menus_LabelsDiet.id_LabelsDiet) ON tbl_Menus.id = tbl_Menus_LabelsDiet.id_Menus\n" +
                    "WHERE (((tbl_Menus.id)="+cursor.getInt(0)+"));",null);
            cursorDiet.moveToFirst();
            ArrayList<String> mDiet = new ArrayList<>();
            for (int iy=0;iy<cursorDiet.getCount();iy++){
                mDiet.add(cursorDiet.getString(0));
                cursorDiet.moveToNext();
            }
            recipe.setDietLabels(mDiet);

            //set labelsSante
            Cursor cursorHealth = mDatabases.rawQuery("SELECT tbl_LabelsSante.label\n" +
                    "FROM tbl_Menus INNER JOIN (tbl_LabelsSante INNER JOIN tbl_Menus_LabelsSante ON tbl_LabelsSante.id = tbl_Menus_LabelsSante.id_LabelsSante) ON tbl_Menus.id = tbl_Menus_LabelsSante.id_Menus\n" +
                    "WHERE (((tbl_Menus.id)="+cursor.getInt(0)+"));",null);
            cursorHealth.moveToFirst();
            ArrayList<String> mHealth = new ArrayList<>();
            for (int iz=0;iz<cursorHealth.getCount();iz++){
                mHealth.add(cursorHealth.getString(0));
                cursorHealth.moveToNext();
            }
            recipe.setHealthLabels(mHealth);

            mRecipe.add(recipe);

            cursor.moveToNext();
        }
        return mRecipe;
    }

    public ArrayList<Date> getDateRecipe() throws Exception{
        ArrayList<Date> mDate = new ArrayList<>();
        openDatabase();

        Cursor cursor = mDatabases.rawQuery("SELECT id FROM tbl_Menus;",null);
        cursor.moveToFirst();
        for (int i=0;i<cursor.getCount();i++){

            Cursor cursorDate = mDatabases.rawQuery("SELECT tbl_Dates.dateHeure\n" +
                    "FROM tbl_Menus INNER JOIN (tbl_Dates INNER JOIN tbl_Menus_Dates ON tbl_Dates.id = tbl_Menus_Dates.id_Dates) ON tbl_Menus.id = tbl_Menus_Dates.id_Menus WHERE (((tbl_Menus.id)="+cursor.getInt(0)+"));",null);
            cursorDate.moveToFirst();

            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cursorDate.getString(0));
            Log.d("getDateRecipe",date.toString());
            mDate.add(date);


            cursor.moveToNext();
        }

        return mDate;
    }

    public ArrayList<Ingredient> getShoppingList(){
        ArrayList<Ingredient> mIngredient = new ArrayList<>();

        openDatabase();

        Cursor cursor = mDatabases.rawQuery("SELECT tbl_Ingredients.label, Sum(tbl_Menus_Ingredients.grammes) AS SommeDegrammes, Sum(tbl_Menus_Ingredients.quantite) AS SommeDequantite, tbl_Menus_Ingredients.list\n" +
                "FROM tbl_Ingredients INNER JOIN tbl_Menus_Ingredients ON tbl_Ingredients.id = tbl_Menus_Ingredients.id_Ingredients\n" +
                "GROUP BY tbl_Ingredients.label, tbl_Menus_Ingredients.list\n" +
                "HAVING (((tbl_Menus_Ingredients.list)='False'))\n" +
                "ORDER BY tbl_Ingredients.label;",null);
        cursor.moveToFirst();
        for (int i=0;i<cursor.getCount();i++){
            Ingredient ingredient = new Ingredient();
            ingredient.setFood(cursor.getString(0));
            ingredient.setWeight(cursor.getDouble(1));
            ingredient.setQuantity(cursor.getDouble(2));

            mIngredient.add(ingredient);
            cursor.moveToNext();
        }
        return  mIngredient;
    }

    public void setShoppingList(Ingredient ingredient) {
        openDatabase();

        Cursor cursor = mDatabases.rawQuery("SELECT tbl_Menus_Ingredients.id_Menus, tbl_Menus_Ingredients.id_Ingredients\n" +
                "FROM tbl_Menus INNER JOIN (tbl_Ingredients INNER JOIN tbl_Menus_Ingredients ON tbl_Ingredients.id = tbl_Menus_Ingredients.id_Ingredients) ON tbl_Menus.id = tbl_Menus_Ingredients.id_Menus\n" +
                "WHERE (((tbl_Ingredients.label)=\""+ingredient.getFood()+"\") AND ((tbl_Menus_Ingredients.list)=\"False\"));",null);
        cursor.moveToFirst();

        for (int i=0;i<cursor.getCount();i++){
            String sql = ("UPDATE tbl_Menus_Ingredients SET list=\"True\" WHERE tbl_Menus_Ingredients.id_Menus = "+cursor.getInt(0)+" AND tbl_Menus_Ingredients.id_Ingredients = "+cursor.getInt(1)+";");
            mDatabases.execSQL(sql);
            Log.d("setShoppingList : ",sql);
            cursor.moveToNext();
        }
    }
}
