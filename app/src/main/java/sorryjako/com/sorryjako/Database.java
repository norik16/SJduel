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

        // Zeman
        addInformations("Kunda sem, kunda tam", "Z",1,db);
        addInformations("Adolf Hitler byl gentleman", "Z",1,db);
        addInformations("Mno…", "Z",2,db);
        addInformations("Já kouřím všude", "Z",1,db);
        addInformations("Pražská kavárna", "Z",2,db);
        addInformations("Novináři jsou pitomci, hnůj a fekálie", "Z",1,db);
        addInformations("Víte, co znamená pussy? V angličtině?", "Z",1,db);
        addInformations("Novináři neuvažují reálně, jsou ireální", "Z",2,db);
        addInformations("Když mi chcípla koza, ať chcípne i sousedovi", "Z",1,db);
        addInformations("Obdivuji impotenty píšící učebnice erotiky", "Z",1,db);
        addInformations("Teď jsme chtěl říct kurev, ale já nejsem Schvarzenberg", "Z",2,db);
        addInformations("Existuje nedemokratická cesta jménem \"Kalašnikov\"", "Z",1,db);
        addInformations("Děkuji všem co mně nepomohli", "Z",2,db);
        addInformations("Doufám, že jsem zase urazil přiměřený počet lidí", "Z",1,db);
        addInformations("Já kouřím i v atomové elektrárně, tak proč bych nekouřil v bílém domě", "Z",1,db);
        addInformations("Já jsem inspirován panem Schwarzenbergem, který v každé druhé větě říká hovno", "Z",2,db);
        addInformations("Já mám mobilní telefon značky Aligátor, VRRRR!!", "Z",1,db);
        addInformations("Zabývám se pouze věcmi, které mají hodnotu přesahující miliardu Kč", "Z",2,db);
        addInformations("Já Vám tady nebudu dělat striptýz", "Z",1,db);
        addInformations("Já, jak víte, běžně vulgarismy nepoužívám", "Z",1,db);
        addInformations("Ještě v pátek jsem měl 39, bohužel ne promile, ale celsia", "Z",1,db);
        addInformations("Málem jsem dopadl jako Fidel Castro", "Z",2,db);
        addInformations("Kunda", "Z",1,db);
        addInformations("No, budu slavit dobrý jihomoravským vínem", "Z",2,db);
        addInformations("Po vyhynutí blbouna nejapného je nejpitomějším tvorem na Zemi český novinář", "Z",1,db);
        addInformations("Pokud chcete házet vajíčka, házejte i rajčata a občas přihoďte nějakou tu klobásku", "Z",1,db);
        addInformations("Pokud jde o SEX, jsem spíše verbální erotik", "Z",1,db);
        addInformations("Takže, pane řediteli, kde máme popelníček?", "Z",1,db);
        addInformations("Když to umí i babička na italské tržnici, tak proč by to neuměl nikdo jiný?", "Z",2,db);
        addInformations("Jste informovaný jako průměrný český novinář, takže špatně", "Z",1,db);
        addInformations("Můj vztah k zelenině je ve skrze pozitivní", "Z",2,db);
        addInformations("Becherovka je příjemný prožitek, ať už v deset ráno, nebo večer", "Z",1,db);
        addInformations("Prezident je v české politice něco jako oleandr, já jsem ale masožravá rostlina", "Z",1,db);
        addInformations("ODS stáhla ocas mezi nohy, byla podělaná až za ušima", "Z",2,db);
        addInformations("Skutečný chlap nepotřebuje prostitutky", "Z",1,db);
        addInformations("Necítím ani potřebu buď se udělat, nebo oddělat", "Z",2,db);
        addInformations("Jestliže si prostitutka udělá nový make-up, ani neomládne, ani nepřestane být prostitutkou", "Z",2,db);
        addInformations("To je moje jachta, stála mě 2000 Kč", "Z",1,db);
        addInformations("Kdo jde příliš doprava, ocitne se vlevo", "Z",2,db);
        addInformations("Učenej z nebe nespadl, ale tady jako by blbce shazovali", "Z",1,db);
        addInformations("Šíří se fámy, že mám velmi rád becherovku a pivo. Obě jsou pravdivé", "Z",1,db);
        addInformations("Slovenská piva jsou dobrá leda tak na proplachování zubních protéz", "Z",1,db);
        addInformations("Zastávám konzervativní názor, že ministr obrany má vědět, jak vypadá tank", "Z",1,db);
        addInformations("Nechal bych zmlátit všechny fotbalové fanoušky bez ohledu na jejich původ", "Z",2,db);
        addInformations("Adolf Hitler byl abstinent, nekuřák a vegetarián. A prohrál válku", "Z",1,db);
        addInformations("Softwarové kuličky", "Z",2,db);
        addInformations("Vláda zkurvila služební zákon", "Z",2,db);
        addInformations("Smrt abstinentům a vegetariánům", "Z",1,db);
        addInformations("V Americe mě vítalo 16 tisíc lidí a tady mi čtyři blbečci mávají červenými kartami", "Z",2,db);


        //Babiš
        addInformations("Tak určitě", "B",2,db);
        addInformations("Sorry jako", "B",1,db);
        addInformations("Kecy v kleci", "B",1,db);
        addInformations("Já do politiky nechtěl", "B",1,db);
        addInformations("Mám střet zájmů, ale nezneužívám ho", "B",1,db);
        addInformations("My se z té demokracie jednou poděláme", "B",1,db);
        addInformations("Já nelžu, nekradu, nikomu nejdu na ruku", "B",1,db);
        addInformations("Ale pohoda", "B",2,db);
        addInformations("Já vám pindíka ukazovat nebudu", "B",1,db);
        addInformations("Vrtěti psem", "B",2,db);
        addInformations("Vemte si koblihu!", "B",2,db);
        addInformations("Kalousek? Symbol korupce!", "B",1,db);
        addInformations("Platí 11. Kalouskovo přikázání: Neprokážeš", "B",1,db);
        addInformations("Čapák", "B",2,db);
        addInformations("Já jsem jen teoretický miliardář", "B",2,db);
        addInformations("Já to odmítám!!!!", "B",2,db);
        addInformations("Co mi to tu podsouváte?", "B",1,db);
        addInformations("Sobotku bych nezaměstnali ani na recepci", "B",1,db);
        addInformations("Čau lidi!", "B",2,db);
        addInformations("Pan premiér tomu nerozumí, protože nikdy nepracoval", "B",2,db);
        addInformations("Ano, bude líp", "B",1,db);
        addInformations("Nejsme jako politici. Makáme!", "B",1,db);
        addInformations("Vy jste mě zplodili!", "B",1,db);
        addInformations("Nikdo mě nemůže zkorumpovat", "B",1,db);
        addInformations("Kurva jako", "B",1,db);
        addInformations("Je to chyba účetní, ona to dělala chudinka poprvé", "B",1,db);
        addInformations("Jsme schopný národ, jen nás řídí nemehla", "B",1,db);
        addInformations("Pokud se jedná o finance, tak bych se obětoval", "B",1,db);
        addInformations("Ale jak jste říkal, že jsem špatný politik. Já se k tomu hlásím. Jsem špatný politik.", "B",2,db);
        addInformations("Marxová je kráva, co chlastá", "B",1,db);
        addInformations("Sobotka je největší svině jakou jsem potkal", "B",1,db);
        addInformations("Premiér je blbeček", "B",1,db);
        addInformations("Moji zaměstnanci jsou debilové", "B",2,db);
        addInformations("Kariérista zkurvený", "B",2,db);
        addInformations("A pak je tu ještě jeden kokot", "B",1,db);
        addInformations("Zaorálka pošlu doprdele, je to debil", "B",2,db);
        addInformations("Tady je to napsaný, dvacet let jeho zlodějin", "B",2,db);
        addInformations("Já kdybych měl za sebou to co on, tak lezu kanálama", "B",1,db);
        addInformations("Nejsem oligarcha a žádná média nevlastním", "B",1,db);
        addInformations("Ta ODS, všechno od nás kopíruje", "B",2,db);
        addInformations("Poslední koblihy! Proč máte to kyselé jablko od zkorumpované ODS?", "B",1,db);
        addInformations("Já Vám dám drobák", "B",2,db);
        addInformations("Vy někomu sdělujete svoje příjmy?", "B",1,db);
        addInformations("Vaše otázky jsou jasné tomu, že tomu vůbec nerozumíte", "B",1,db);
        addInformations("Je to spiknutí", "B",1,db);
        addInformations("Je to kampaň", "B",1,db);
        addInformations("Je to provokace", "B",1,db);
        addInformations("Je to účelovka", "B",2,db);
        addInformations("Politika je prostě svinstvo", "B",2,db);
        addInformations("Stát by se měl chovat jako rodinná firma", "B",2,db);
        addInformations("Náš stát byl vybudován jako sabotáž", "B",2,db);
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
