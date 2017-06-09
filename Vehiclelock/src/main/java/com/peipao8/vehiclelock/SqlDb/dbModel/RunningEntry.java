package com.peipao8.vehiclelock.SqlDb.dbModel;

import android.provider.BaseColumns;

/**
 * Created by ooo on 2016/3/2.
 */

public class RunningEntry implements BaseColumns {
    public static final String TABLE_NAME      = "Running";
    public static final String runningId       = "runningId";
    public static final String runnerId        = "runnerId";
    public static final String calorieCount    = "calorieCount";
    public static final String kilometerCount  = "kilometerCount";
    public static final String durationCount   = "durationCount";
    public static final String highRunningPace = "highRunningPace";
    public static final String lowRunningPace  = "lowRunningPace";
    public static final String state           = "state";
    public static final String startTime       = "startTime";
    public static final String endTime         = "endTime";
    public static final String runType         = "runType";
    public static final String activityId      = "activityId";
    public static final String isUploadingStart     = "isUploadingStart";
    public static final String isUploadingEnd     = "isUploadingEnd";
}
