package darshita.com.parentteacherinteraction;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import darshita.com.parentteacherinteraction.aws.UserIdpassDO;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Osbs";
    private static final String ID="ID";
    private static final String TABLE_USER="USER";
    private static final String PHONENUMBER="PHONENUMBER";
    private static final String ROLE="ROLE";

    static DatabaseHandler db;
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static DatabaseHandler getInstance(Context context) {
        db = new DatabaseHandler(context);
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
                + ID + " INTEGER PRIMARY KEY," + PHONENUMBER + " Text,"+ROLE+" Text"+")";

        db.execSQL(CREATE_TABLE_USER);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        onCreate(db);
    }

    public void addUser(UserIdpassDO userDataDO)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PHONENUMBER,userDataDO.getMobileNumber());
        values.put(ROLE,userDataDO.getCategory());
        db.insert(TABLE_USER, null, values);
        db.close();
    }
    public void deleteUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();
    }
    public UserIdpassDO getUserData()
    {
       UserIdpassDO userDataDO=null;
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

               userDataDO=new UserIdpassDO();
                userDataDO.setMobileNumber(cursor.getString(1));
                userDataDO.setCategory(cursor.getString(2));
        }
        cursor.close();
        db.close();
        return userDataDO;
    }




}
