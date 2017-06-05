package sorryjako.com.sorryjako;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Veronika and Ron on 29.11.2016.
 */

public class Database extends SQLiteOpenHelper {


    public static final String ID = "id";
    public static final String LINE = "line";
    public static final String PERSON = "person";
    public static final String SCORE = "score";
    public static final String TABLE_NAME = "quiz_lines";
    private static final String DATABASE_NAME = "quiz.db";

    private static final String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME+"("+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + LINE+" TEXT, " + PERSON+" TEXT, " + SCORE + " INT );";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("Database/onCreate", "started...");
        db.execSQL(CREATE_QUERY);
        Log.e("DATABASE OPERATIONS", "Table created...");
        //Here add informations to database
        addInformations("Sorry jako","B",1,db);
        addInformations("Kecy v kleci","B",1,db);
        addInformations("Já do politiky nechtěl","B",1,db);
        addInformations("My se z té demokracie jednou poděláme","B",1,db);
        addInformations("Já nelžu, nekradu","B",1,db);
        addInformations("Ale pohoda","B",2,db);
        addInformations("Kalousek, vrchol korupce","B",1,db);
        addInformations("Já vám pindíka ukazovat nebudu","B",2,db);
        addInformations("Vrtěti psem","B",2,db);
        addInformations("Vemte si koblihu","B",1,db);

        addInformations("Kunda sem, kunda tam","Z",1,db);
        addInformations("Adolf Hitler byl gentleman","Z",1,db);
        addInformations("Mno...","Z",2,db);
        addInformations("Já kouřím všude","Z",2,db);
        addInformations("Pražská kavárna","Z",1,db);
        addInformations("Novináři jsou pitomci, hnůj a fekálie","Z",1,db);
        addInformations("Víte co znamená pussy?","Z",1,db);
        addInformations("Novináři neuvažují reálně, jsou ireální","Z",2,db);
        addInformations("Když mi chcípla koza, ať chcípně i sousedovi","Z",1,db);
        addInformations("Obdivuji impotenty píšící učebnice erotiky","Z",1,db);
    }

    public void addInformations(String line, String person, Integer score, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LINE, line);
        contentValues.put(PERSON, person);
        contentValues.put(SCORE, score);
        db.insert(TABLE_NAME, null, contentValues);
        Log.e("DATABASE OPERATIONS", "One row inserted..");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getInformations(SQLiteDatabase db, Integer id) {
        Log.e("Database/getInformation", "entering...");
        Cursor cursor;
        String[] selections = {id.toString()};
        String[] projections = {Database.LINE, Database.PERSON, Database.SCORE};
        cursor = db.query(Database.TABLE_NAME, projections, "id LIKE ?", selections, null, null, null);

        return cursor;
    }
}
