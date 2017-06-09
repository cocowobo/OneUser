package com.peipao8.vehiclelock.SqlDb.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.peipao8.vehiclelock.SqlDb.dbModel.RunningEntry;
import com.peipao8.vehiclelock.SqlDb.dbModel.RunningNodeVOEntry;


/** 建立库表类*/
public class DbHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    public static final int DATABASE_VERSION = 1;
    public static final  String DATABASE_NAME = "account.db";
    private static final String TEXT_TYPE     = " TEXT";                  //text类型
    private static final String NUMERIC_TYPE  = " NUMERIC";            //数据类型
    private static final String COMMA_SEP     = ",";                      //逗号

    /** 创建跑步Running总表语句 */
    private static final String SQL_CREATE_RUNNING =
            "CREATE TABLE " +   RunningEntry.TABLE_NAME + "(" +   RunningEntry._ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT," +   RunningEntry.runningId
                    + TEXT_TYPE + COMMA_SEP +   RunningEntry.runnerId + TEXT_TYPE + COMMA_SEP
                    +   RunningEntry.calorieCount + TEXT_TYPE + COMMA_SEP
                    +   RunningEntry.kilometerCount + TEXT_TYPE + COMMA_SEP
                    +   RunningEntry.durationCount + TEXT_TYPE + COMMA_SEP
                    +   RunningEntry.highRunningPace + TEXT_TYPE + COMMA_SEP
                    +   RunningEntry.lowRunningPace + TEXT_TYPE + COMMA_SEP
                    +   RunningEntry.state + TEXT_TYPE + COMMA_SEP
                    +   RunningEntry.startTime + TEXT_TYPE + COMMA_SEP
                    +   RunningEntry.endTime + TEXT_TYPE + COMMA_SEP
                    +   RunningEntry.runType + TEXT_TYPE + COMMA_SEP
                    +   RunningEntry.activityId + TEXT_TYPE + COMMA_SEP
                    +   RunningEntry.isUploadingStart + NUMERIC_TYPE
                    +   RunningEntry.isUploadingEnd + NUMERIC_TYPE + ")";
    /** 创建跑步RunningNodeVO节点表语句 */
    private static final String SQL_CREATE_RUNNINGNODEVO = "CREATE TABLE "
            +    RunningNodeVOEntry.TABLE_NAME + "("
            +    RunningNodeVOEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            +    RunningNodeVOEntry.speed + TEXT_TYPE + COMMA_SEP
            +    RunningNodeVOEntry.calorie + TEXT_TYPE + COMMA_SEP
            +    RunningNodeVOEntry.kilometer + TEXT_TYPE + COMMA_SEP
            +    RunningNodeVOEntry.runningPace + TEXT_TYPE + COMMA_SEP
            +    RunningNodeVOEntry.nowTime + TEXT_TYPE + COMMA_SEP
            +    RunningNodeVOEntry.lat + TEXT_TYPE + COMMA_SEP
            +    RunningNodeVOEntry.lag + TEXT_TYPE + COMMA_SEP
            +    RunningNodeVOEntry.duration + TEXT_TYPE + COMMA_SEP
            +    RunningNodeVOEntry.color + TEXT_TYPE + COMMA_SEP
            +    RunningNodeVOEntry.kilometerNode + NUMERIC_TYPE + COMMA_SEP
            +    RunningNodeVOEntry.runningId + TEXT_TYPE + COMMA_SEP
            +    RunningNodeVOEntry.isUploading + NUMERIC_TYPE + ")";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(SQL_CREATE_RUNNING);
        db.execSQL(SQL_CREATE_RUNNINGNODEVO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
