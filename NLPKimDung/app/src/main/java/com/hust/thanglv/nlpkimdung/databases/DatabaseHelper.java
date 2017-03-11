package com.hust.thanglv.nlpkimdung.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.hust.thanglv.nlpkimdung.model.Chapter;
import com.hust.thanglv.nlpkimdung.model.Story;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by user on 3/10/17.
 */

public class DatabaseHelper extends SQLiteAssetHelper {

    public static final String DBNAME = "kimdung.sqlite";
    private static final int DATABASE_VERSION = 1;
    private Story storyModel;
    private Chapter chapterModel;

    public static final String  GET_ALL_STORY= "SELECT stID, stName FROM kimdung";
    public static final String GET_CHAPTER_OF_STORY = "SELECT st_kimdung.stID, st_kimdung.dename, st_kimdung.decontent " +
            "FROM kimdung, st_kimdung WHERE st_kimdung.stID = kimdung.stID and st_kimdung.stID = ";
    private Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null,DATABASE_VERSION );
        this.mContext = context;

    }

    public ArrayList<Story> getListStory() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ArrayList<Story> stories = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(GET_ALL_STORY, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            storyModel = new Story(cursor.getInt(0), cursor.getString(1));
            stories.add(storyModel);
            cursor.moveToNext();
        }
        cursor.close();
        return stories;
    }

    public ArrayList<Chapter> getListChapter(int id){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ArrayList<Chapter> chapters = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(GET_CHAPTER_OF_STORY + String.valueOf(id), null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            chapterModel = new Chapter(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            chapters.add(chapterModel);
            cursor.moveToNext();
        }
        cursor.close();
        return chapters;
    }
}
