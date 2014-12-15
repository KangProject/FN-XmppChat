package com.fn.reunion.app.db;

import java.text.MessageFormat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fn.reunion.app.db.tables.FriendTable;
import com.fn.reunion.app.db.tables.MessageTable;
import com.fn.reunion.app.db.tables.UserTable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String KEY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS {0} ({1})";
    public static final String KEY_DROP_TABLE = "DROP TABLE IF EXISTS {0}";

    private Context context;
    private static final int CURRENT_DB_VERSION = 1;
    private static final String DB_NAME = "FN.db";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, CURRENT_DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createUserTable(db);
        createFriendTable(db);
        createMessageTable(db);
    }

    private void createUserTable(SQLiteDatabase db) {
        StringBuilder userTableFields = new StringBuilder();
        userTableFields
                .append(UserTable.Cols.ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(UserTable.Cols.USER_ID).append(" INTEGER, ")
                .append(UserTable.Cols.FULL_NAME).append(" TEXT, ")
                .append(UserTable.Cols.EMAIL).append(" TEXT, ")
                .append(UserTable.Cols.LOGIN).append(" TEXT, ")
                .append(UserTable.Cols.PHONE).append(" TEXT, ")
                .append(UserTable.Cols.WEB_SITE).append(" TEXT, ")
                .append(UserTable.Cols.CUSTOM_DATA).append(" TEXT, ")
                .append(UserTable.Cols.LAST_REQUEST_AT).append(" TEXT, ")
                .append(UserTable.Cols.EXTERNAL_ID).append(" TEXT, ")
                .append(UserTable.Cols.FACEBOOK_ID).append(" INTEGER, ")
                .append(UserTable.Cols.TWITTER_ID).append(" INTEGER, ")
                .append(UserTable.Cols.BLOB_ID).append(" INTEGER, ")
                .append(UserTable.Cols.AVATAR_URL).append(" TEXT, ")
                .append(UserTable.Cols.STATUS).append(" TEXT, ")
                .append(UserTable.Cols.IS_ONLINE).append(" INTEGER");
        createTable(db, UserTable.TABLE_NAME, userTableFields.toString());
    }

    private void createFriendTable(SQLiteDatabase db) {
    	
        StringBuilder friendTableFields = new StringBuilder();
        
        friendTableFields
                .append(FriendTable.Cols.ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(FriendTable.Cols.USER_ID).append(" INTEGER, ")
                .append(FriendTable.Cols.IS_STATUS_ASK).append(" INTEGER, ")
                .append(FriendTable.Cols.IS_REQUESTED_FRIEND).append(" INTEGER, ")
                .append(FriendTable.Cols.RELATION_STATUS_ID).append(" INTEGER, FOREIGN KEY (")
                .append(FriendTable.Cols.USER_ID).append(") REFERENCES ")
                .append(UserTable.TABLE_NAME).append(" (")
                .append(UserTable.Cols.USER_ID).append("), FOREIGN KEY (")
                .append(FriendTable.Cols.RELATION_STATUS_ID).append(") REFERENCES ");
        
        createTable(db, FriendTable.TABLE_NAME, friendTableFields.toString());
    }

    private void createMessageTable(SQLiteDatabase db) {
        StringBuilder messageTableFields = new StringBuilder();
        messageTableFields
                .append(MessageTable.Cols.ID).append(" TEXT PRIMARY KEY, ")
                .append(MessageTable.Cols.DIALOG_ID).append(" INTEGER, ")
                .append(MessageTable.Cols.PACKET_ID).append(" TEXT, ")
                .append(MessageTable.Cols.SENDER_ID).append(" INTEGER, ")
                .append(MessageTable.Cols.BODY).append(" TEXT, ")
                .append(MessageTable.Cols.TIME).append(" LONG, ")
                .append(MessageTable.Cols.ATTACH_FILE_ID).append(" TEXT, ")
                .append(MessageTable.Cols.IS_READ).append(" INTEGER, ")
                .append(MessageTable.Cols.IS_DELIVERED).append(" INTEGER");
        createTable(db, MessageTable.TABLE_NAME, messageTableFields.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTable(db, UserTable.TABLE_NAME);
        dropTable(db, FriendTable.TABLE_NAME);
        dropTable(db, MessageTable.TABLE_NAME);
        onCreate(db);
    }

    public void dropTable(SQLiteDatabase db, String name) {
        String query = MessageFormat.format(DatabaseHelper.KEY_DROP_TABLE, name);
        db.execSQL(query);
    }

    public void createTable(SQLiteDatabase db, String name, String fields) {
        String query = MessageFormat.format(DatabaseHelper.KEY_CREATE_TABLE, name, fields);
        db.execSQL(query);
    }
}
