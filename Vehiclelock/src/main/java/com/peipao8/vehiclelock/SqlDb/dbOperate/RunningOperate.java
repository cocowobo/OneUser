package com.peipao8.vehiclelock.SqlDb.dbOperate;

import android.content.ContentValues;
import android.database.Cursor;

import com.peipao8.vehiclelock.App;
import com.peipao8.vehiclelock.LYangCode.utils.UiUtils;
import com.peipao8.vehiclelock.SqlDb.dbModel.RunningEntry;
import com.peipao8.vehiclelock.SqlDb.dbModel.RunningNodeVOEntry;
import com.peipao8.vehiclelock.model.runData.Running;
import com.peipao8.vehiclelock.model.runData.RunningVO;

import java.util.ArrayList;
import java.util.List;

/** 跑步信息本地存储数据库操作类 */
public class RunningOperate {

    //======================================================================
    //-----------------------------------插入Running-----------------------------------
    /** 向本地数据库表（Running）中插入数据 */
    public void insertRunning(final String runningId, final Running running) {
        UiUtils.getThreadPool ().execute ( new Runnable () {
            @Override
            public void run() {
                _insertRunning ( runningId, running );
            }
        } );
    }
    private void _insertRunning(   String runningId, Running running) {
        ContentValues values = new ContentValues ();
        values.put ( RunningEntry.runningId, runningId );
        values.put ( RunningEntry.runnerId, running.runnerId );
        values.put ( RunningEntry.calorieCount, running.calorieCount + "" );
        values.put ( RunningEntry.kilometerCount, running.kilometerCount + "" );
        values.put ( RunningEntry.durationCount, running.durationCount + "");
        values.put ( RunningEntry.highRunningPace, running.highRunningPace );
        values.put ( RunningEntry.lowRunningPace, running.lowRunningPace );
        values.put ( RunningEntry.state, running.state );
        values.put ( RunningEntry.startTime, running.startTime );
        values.put ( RunningEntry.endTime, running.endTime );
        values.put ( RunningEntry.runType, running.runType );
        values.put ( RunningEntry.activityId, running.activityId );
        values.put ( RunningEntry.isUploadingStart, false );
        values.put ( RunningEntry.isUploadingEnd, false );
        App.getSQLdb ().insert ( RunningEntry.TABLE_NAME, null, values );
    }

    //======================================================================
    //-----------------------------------插入RunningNodeVO-----------------------------------
    /** 向本地数据库表（RunningNodeVO）中插入数据  @param bl   是否为遍历所有节点添加*/
    public void insertRunningNodeVO(final String runningId, final Running running, final boolean isForeach) {
        UiUtils.getThreadPool ().execute ( new Runnable () {
            @Override
            public void run() {
                _insertRunningNodeVO( runningId, running, isForeach);
            }
        } );
    }
    private void _insertRunningNodeVO(   String runningId, Running running, boolean isForeach) {
        if (isForeach) {
            for (int i = 0; i < running.runningVO.size (); i++) {
                ContentValues values = new ContentValues ();
                values.put ( RunningNodeVOEntry.speed, running.runningVO.get ( i ).speed + "" );
                values.put ( RunningNodeVOEntry.calorie, running.runningVO.get ( i ).calorie + "" );
                values.put ( RunningNodeVOEntry.kilometer, running.runningVO.get ( i ).kilometer + "" );
                values.put ( RunningNodeVOEntry.runningPace, running.runningVO.get ( i ).runningPace+ "" );
                values.put ( RunningNodeVOEntry.nowTime, running.runningVO.get ( i ).nowTime );
                values.put ( RunningNodeVOEntry.lat, running.runningVO.get ( i ).lat );
                values.put ( RunningNodeVOEntry.lag, running.runningVO.get ( i ).lag );
                values.put ( RunningNodeVOEntry.duration, running.runningVO.get ( i ).duration);
                values.put ( RunningNodeVOEntry.color, running.runningVO.get ( i ).color );
                values.put ( RunningNodeVOEntry.kilometerNode, running.runningVO.get ( i ).kilometerNode );
                values.put ( RunningNodeVOEntry.isUploading, false );
                values.put ( RunningNodeVOEntry.runningId, runningId );
                App.getSQLdb (). insert ( RunningNodeVOEntry.TABLE_NAME, null, values );
            }
        } else {
            ContentValues values = new ContentValues ();
            int index = running.runningVO.size () - 1;
            values.put ( RunningNodeVOEntry.speed, running.runningVO.get ( index ).speed + "" );
            values.put ( RunningNodeVOEntry.calorie, running.runningVO.get ( index ).calorie + "" );
            values.put ( RunningNodeVOEntry.kilometer, running.runningVO.get ( index ).kilometer + "" );
            values.put ( RunningNodeVOEntry.runningPace, running.runningVO.get ( index ).runningPace + "");
            values.put ( RunningNodeVOEntry.nowTime, running.runningVO.get ( index ).nowTime );
            values.put ( RunningNodeVOEntry.lat, running.runningVO.get ( index ).lat );
            values.put ( RunningNodeVOEntry.lag, running.runningVO.get ( index ).lag );
            values.put ( RunningNodeVOEntry.duration, running.runningVO.get ( index ).duration);
            values.put ( RunningNodeVOEntry.color, running.runningVO.get ( index ).color );
            values.put ( RunningNodeVOEntry.kilometerNode, running.runningVO.get ( index ).kilometerNode );
            values.put ( RunningNodeVOEntry.isUploading, false );
            values.put ( RunningNodeVOEntry.runningId, runningId );
              App.getSQLdb (). insert ( RunningNodeVOEntry.TABLE_NAME, null, values );
        }
    }

    //======================================================================
    //-----------------------------------更新Running-----------------------------------
    /**  更新本地数据库表（Running）中的数据 isPartUp是否是更新每一条数据 */
    public void updateRunning(final String runningId, final Running running, final boolean isPartUp) {
        UiUtils.getThreadPool ().execute ( new Runnable () {
            @Override
            public void run() {
                _updateRunning (  runningId, running, isPartUp );
            }
        } );
    }
    private void _updateRunning(   String runningId, Running running, boolean isPartUp) {
        if (isPartUp) {
            ContentValues values = new ContentValues ();
            values.put ( RunningEntry.calorieCount, running.calorieCount + "" );
            values.put ( RunningEntry.kilometerCount, running.kilometerCount + "" );
            values.put ( RunningEntry.durationCount, running.durationCount+ "" );
            values.put ( RunningEntry.highRunningPace, running.highRunningPace );
            values.put ( RunningEntry.lowRunningPace, running.lowRunningPace );
            values.put ( RunningEntry.state, running.state );
            values.put ( RunningEntry.startTime, running.startTime );
            values.put ( RunningEntry.endTime, running.endTime );
            String selection = RunningEntry.runningId + " = '" + runningId + "'";
            App.getSQLdb (). update ( RunningEntry.TABLE_NAME, values, selection, null );
        } else {
            ContentValues values = new ContentValues ();
            values.put ( RunningEntry.calorieCount, running.calorieCount + "" );
            values.put ( RunningEntry.kilometerCount, running.kilometerCount + "" );
            values.put ( RunningEntry.durationCount, running.durationCount+ "" );
            String selection = RunningEntry.runningId + " = '" + runningId + "'";
            App.getSQLdb (). update ( RunningEntry.TABLE_NAME, values, selection, null );
        }
    }


    /** 根据runningID更改本地数据库表（Running）中isUploadingEnd字段状态 */
    public void updateRunningIsUploadingEnd(final String runningId ) {
        UiUtils.getThreadPool ().execute ( new Runnable () {
            @Override
            public void run() {
                _updateRunningWithIsUploadingEnd (  runningId );
            }
        } );
    }
    private void _updateRunningWithIsUploadingEnd(   String runningId) {
        ContentValues values = new ContentValues ();
        values.put ( RunningEntry.isUploadingEnd, true );
        String selection = RunningEntry.runningId + " = '" + runningId + "'";
        App.getSQLdb (). update ( RunningEntry.TABLE_NAME, values, selection, null );
    }


    /** 根据老runningID更改本地数据库表（Running）老runningID为新runningID字段值，同时改起点上传为true */
    public void updateRunningRunningId(final String oldRunningId, final String newRunningId) {
        UiUtils.getThreadPool ().execute ( new Runnable () {
            @Override
            public void run() {
                _updateRunningWithRunningId (  oldRunningId, newRunningId );
            }
        } );
    }
    private void _updateRunningWithRunningId(   String oldRunningId, String newRunningId) {
        ContentValues values = new ContentValues ();
        values.put ( RunningEntry.runningId, newRunningId );
        values.put ( RunningEntry.isUploadingStart, 1 );
        String selection = RunningEntry.runningId + " = '" + oldRunningId + "'";
        App.getSQLdb (). update ( RunningEntry.TABLE_NAME, values, selection, null );
    }


    //======================================================================
    //-----------------------------------更新RunningNodeVO-----------------------------------
    /** 更新本地数据库表（RunningNodeVO）中所有的节点的isUploading值 */
    public void updateRunningNodeVOAllIsUploading(final String runningId) {
        UiUtils.getThreadPool ().execute ( new Runnable () {
            @Override
            public void run() {
                _updateRunningNodeVOAllIsUploading (  runningId );
            }
        } );

    }
    private void _updateRunningNodeVOAllIsUploading(   String runningId) {
        ContentValues values = new ContentValues ();
        values.put ( RunningNodeVOEntry.isUploading, 1 );
        String selection = RunningNodeVOEntry.runningId + " = '" + runningId + "'";
        App.getSQLdb (). update ( RunningNodeVOEntry.TABLE_NAME, values, selection, null );
    }

    /** 更新本地数据库表（RunningNodeVO）中部分的节点的isUploading值 */
    public void updateRunningNodeVOPartIsUploading(final ArrayList<RunningVO> partNodeList) {
        UiUtils.getThreadPool ().execute ( new Runnable () {
            @Override
            public void run() {
                _updateRunningNodeVOPartIsUploading ( partNodeList );
            }
        } );

    }
    private void _updateRunningNodeVOPartIsUploading( ArrayList<RunningVO> partNodeList) {
        for (RunningVO runningVO : partNodeList) {
            ContentValues values = new ContentValues ();
            values.put ( RunningNodeVOEntry.isUploading, 1 );
            String selection = RunningNodeVOEntry.nowTime + " = '" + runningVO.nowTime + "'";
            App.getSQLdb (). update ( RunningNodeVOEntry.TABLE_NAME, values, selection, null );
        }

    }


    /** 根据runningID更改本地数据库表（RunningNodeVO）中runningID字段值 */
    public void updateRunningNodeVORunningId(final String oldRunningId, final String newRunningId) {
        UiUtils.getThreadPool ().execute ( new Runnable () {
            @Override
            public void run() {
                _updateRunningNodeVORunningId (  oldRunningId, newRunningId );
            }
        } );
    }
    private void _updateRunningNodeVORunningId(   String oldRunningId, String newRunningId) {
        ContentValues values = new ContentValues ();
        values.put ( RunningNodeVOEntry.runningId, newRunningId );
        String selection = RunningNodeVOEntry.runningId + " = '" + oldRunningId + "'";
        App.getSQLdb (). update ( RunningNodeVOEntry.TABLE_NAME, values, selection, null );
    }


//    /** 根据runningID更改本地数据库表（RunningNodeVO）中isUploading字段状态与runningID字段值 */
//    public void updataRunningNodeVORunningIdAndIsUploading(final String oldRunningId, final String newRunningId) {
//        UiUtils.getThreadPool ().execute ( new Runnable () {
//            @Override
//            public void run() {
//                _updataRunningNodeVORunningIdAndIsUploading (  oldRunningId, newRunningId );
//            }
//        } );
//
//    }
//    private void _updataRunningNodeVORunningIdAndIsUploading(   String oldRunningId, String newRunningId) {
//        ContentValues values = new ContentValues ();
//        values.put ( RunningNodeVOEntry.runningId, newRunningId );
//        values.put ( RunningNodeVOEntry.isUploading, true );
//        String selection = RunningNodeVOEntry.runningId + " = '" + oldRunningId + "'";
//        App.getSQLdb (). update ( RunningNodeVOEntry.TABLE_NAME, values, selection, null );
//    }

    //======================================================================
    //-----------------------------------查询Running-----------------------------------

    //======================================================================
    //-----------------------------------查询Running-----------------------------------
    /** 根据runningID查询本地数据库表（Running）中的所有数据，返回所有批次跑步记录的总表对象集合 */
    public List<Running> queryRunningByRunningId(String runningId) {
        List<Running> runningList = _queryRunningByIsUploadingOrRunningId ( runningId ,1);
        return runningList;
    }
    /** 根据isUploadingStart为false查询本地数据库表（Running）中的数据：起点未上传至服务器的数据 */
    public List<Running> queryRunningByIsUploadingStart() {
        List<Running> runningList = _queryRunningByIsUploadingOrRunningId ( "",2 );
        return runningList;
    }
    /** 根据isUploadingStart为true但是isUploadingEnd为false查询本地数据库表（Running）中的数据：用户上传不完整的数据 */
    public List<Running> queryRunningByIsUploadingStartAndEnd( ) {
        List<Running> runnings = _queryRunningByIsUploadingOrRunningId("",3);
        return runnings;
    }
    private List<Running> _queryRunningByIsUploadingOrRunningId(String runningId ,int flag) {
        List<Running> runningList = new ArrayList<> ();
        //需要查询的数据列
        String[] projection = {RunningEntry._ID, RunningEntry.runnerId, RunningEntry.calorieCount, RunningEntry
            .kilometerCount, RunningEntry.durationCount, RunningEntry.highRunningPace, RunningEntry.lowRunningPace,
            RunningEntry.state, RunningEntry.startTime, RunningEntry.endTime, RunningEntry.runType, RunningEntry
            .activityId, RunningEntry.runningId};
        //排序
        String sortOrder = RunningEntry.startTime + " DESC";
        //查询条件
        String selection = "";
        if (flag == 1) {
            selection = RunningEntry.runningId + " = '" + runningId + "'";
        } else if (flag == 2) {
            selection = RunningEntry.isUploadingStart + " = " + 0;
        }else if (flag == 3) {
            selection = RunningEntry.isUploadingStart + " = " + 1 +" AND "+ RunningEntry.isUploadingEnd + " = " + 0 ;
        }else if (flag == 4) {
        }
        //查询条件值
        Cursor cursor =   App.getSQLdb (). query ( RunningEntry.TABLE_NAME,  // The table to query
            projection,                               // The columns to return
            selection,                                // The columns for the WHERE clause
            null,                            // The values for the WHERE clause
            null,                                     // don't group the rows
            null,                                     // don't filter by row groups
            sortOrder                                 // The sort order
        );
        while (cursor.moveToNext ()) {
            Running running = new Running ();
            running.runnerId = cursor.getLong ( cursor.getColumnIndex ( RunningEntry.runnerId ) );
            running.calorieCount = cursor.getFloat ( cursor.getColumnIndex ( RunningEntry.calorieCount ) );
            running.kilometerCount = cursor.getFloat ( cursor.getColumnIndex ( RunningEntry.kilometerCount ) );
            running.durationCount = cursor.getString ( cursor.getColumnIndex ( RunningEntry.durationCount ) );
            running.highRunningPace = cursor.getFloat ( cursor.getColumnIndex ( RunningEntry.highRunningPace ) );
            running.lowRunningPace = cursor.getFloat ( cursor.getColumnIndex ( RunningEntry.lowRunningPace ) );
            running.state = cursor.getInt ( cursor.getColumnIndex ( RunningEntry.state ) );
            running.startTime = cursor.getString ( cursor.getColumnIndex ( RunningEntry.startTime ) );
            running.endTime = cursor.getString ( cursor.getColumnIndex ( RunningEntry.endTime ) );
            running.runType = cursor.getInt ( cursor.getColumnIndex ( RunningEntry.runType ) );
            running.activityId = cursor.getLong ( cursor.getColumnIndex ( RunningEntry.activityId ) );
            running.runningId = cursor.getLong ( cursor.getColumnIndex ( RunningEntry.runningId ) );
            runningList.add ( running );
        }
        return runningList;
    }

    //======================================================================
    //-----------------------------------查询RunningNodeVO-----------------------------------
    /** 根据runningID查询未上传或者是已上传至服务器中的节点数据 */
    public List<RunningVO> queryRunningNodeNoUpdata( String runningId, int isUploading) {
        List<RunningVO> runningVOList = _queryRunningNodeNoUpdata (  runningId, isUploading );
        return runningVOList;
    }
    private List<RunningVO> _queryRunningNodeNoUpdata( String runningId, int isUploading) {
        List<RunningVO> runningVOList = new ArrayList<> ();
        //需要查询的数据列
        String[] projection = {RunningNodeVOEntry._ID, RunningNodeVOEntry.speed, RunningNodeVOEntry.calorie,
            RunningNodeVOEntry.kilometer, RunningNodeVOEntry.runningPace, RunningNodeVOEntry.nowTime,
            RunningNodeVOEntry.lat, RunningNodeVOEntry.lag, RunningNodeVOEntry.duration, RunningNodeVOEntry.color,
            RunningNodeVOEntry.kilometerNode, RunningNodeVOEntry.runningId};
        //排序
        String sortOrder = RunningNodeVOEntry.nowTime + " DESC";
        //查询条件
        String selection = RunningNodeVOEntry.runningId + " = '" + runningId + "' and " +
            RunningNodeVOEntry.isUploading + " = " + isUploading;
        //查询条件值
        Cursor cursor =   App.getSQLdb (). query ( RunningNodeVOEntry.TABLE_NAME,  // The table to query
            projection,                               // The columns to return
            selection,                                // The columns for the WHERE clause
            null,                            // The values for the WHERE clause
            null,                                     // don't group the rows
            null,                                     // don't filter by row groups
            sortOrder                                 // The sort order
        );
        while (cursor.moveToNext ()) {
            RunningVO runningVO = new RunningVO ();
            runningVO.speed = cursor.getFloat ( cursor.getColumnIndex ( RunningNodeVOEntry.speed ) );
            runningVO.calorie = cursor.getFloat ( cursor.getColumnIndex ( RunningNodeVOEntry.calorie ) );
            runningVO.kilometer = cursor.getFloat ( cursor.getColumnIndex ( RunningNodeVOEntry.kilometer ) );
            runningVO.runningPace = cursor.getFloat ( cursor.getColumnIndex (  RunningNodeVOEntry.runningPace ) );
            runningVO.nowTime = cursor.getString ( cursor.getColumnIndex ( RunningNodeVOEntry.nowTime ) );
            runningVO.lat = cursor.getString ( cursor.getColumnIndex ( RunningNodeVOEntry.lat ) );
            runningVO.lag = cursor.getString ( cursor.getColumnIndex ( RunningNodeVOEntry.lag ) );
            runningVO.duration = cursor.getString ( cursor.getColumnIndex ( RunningNodeVOEntry.duration ) );
            runningVO.color = cursor.getString ( cursor.getColumnIndex ( RunningNodeVOEntry.color ) );
            runningVO.kilometerNode = cursor.getInt ( cursor.getColumnIndex (  RunningNodeVOEntry.kilometerNode ) ) > 0;
            runningVO.runningId = cursor.getLong ( cursor.getColumnIndex ( RunningNodeVOEntry.runningId ) );
            runningVOList.add ( runningVO );
        }
        return runningVOList;
    }

    /** 查询所有未上传或者是已上传至服务器中的节点数据 */
    public List<RunningVO> queryRunningNodeVOByisUploading(int isUploading ) {
        List<RunningVO> runningVOList = _queryRunningNodeVOByisUploading ( isUploading );
        return runningVOList;
    }
    private List<RunningVO> _queryRunningNodeVOByisUploading(int isUploading) {
        List<RunningVO> runningVOList = new ArrayList<> ();
        //需要查询的数据列
        String[] projection = {RunningNodeVOEntry._ID, RunningNodeVOEntry.speed, RunningNodeVOEntry.calorie,
            RunningNodeVOEntry.kilometer, RunningNodeVOEntry.runningPace, RunningNodeVOEntry.nowTime,
            RunningNodeVOEntry.lat, RunningNodeVOEntry.lag, RunningNodeVOEntry.duration, RunningNodeVOEntry.color,
            RunningNodeVOEntry.kilometerNode, RunningNodeVOEntry.runningId};
        //排序
        String sortOrder = RunningNodeVOEntry.nowTime + " DESC";
        //查询条件
        String selection = RunningNodeVOEntry.isUploading + " = " + isUploading;
        //查询条件值
        Cursor cursor =   App.getSQLdb (). query ( RunningNodeVOEntry.TABLE_NAME,  // The table to query
            projection,                               // The columns to return
            selection,                                // The columns for the WHERE clause
            null,                            // The values for the WHERE clause
            null,                                     // don't group the rows
            null,                                     // don't filter by row groups
            sortOrder                                 // The sort order
        );
        while (cursor.moveToNext ()) {
            RunningVO runningVO = new RunningVO ();
            runningVO.speed = cursor.getFloat ( cursor.getColumnIndex ( RunningNodeVOEntry.speed ) );
            runningVO.calorie = cursor.getFloat ( cursor.getColumnIndex ( RunningNodeVOEntry.calorie ) );
            runningVO.kilometer = cursor.getFloat ( cursor.getColumnIndex ( RunningNodeVOEntry.kilometer ) );
            runningVO.runningPace = cursor.getFloat ( cursor.getColumnIndex ( RunningNodeVOEntry.runningPace ) );
            runningVO.nowTime = cursor.getString ( cursor.getColumnIndex ( RunningNodeVOEntry.nowTime ) );
            runningVO.lat = cursor.getString ( cursor.getColumnIndex ( RunningNodeVOEntry.lat ) );
            runningVO.lag = cursor.getString ( cursor.getColumnIndex ( RunningNodeVOEntry.lag ) );
            runningVO.duration = cursor.getString ( cursor.getColumnIndex ( RunningNodeVOEntry.duration ) );
            runningVO.color = cursor.getString ( cursor.getColumnIndex ( RunningNodeVOEntry.color ) );
            runningVO.kilometerNode = cursor.getInt ( cursor.getColumnIndex ( RunningNodeVOEntry.kilometerNode ) ) > 0;
            runningVO.runningId = cursor.getLong ( cursor.getColumnIndex ( RunningNodeVOEntry.runningId ) );
            runningVOList.add ( runningVO );
        }
        return runningVOList;
    }

    /** 根据runningID查询本地数据库表（RunningNodeVO）中的数据 */
    public List<RunningVO> queryRunningNodeVOByRunningId( String runningId) {
        List<RunningVO> runningVOList = _queryRunningNodeVOByRunningId (  runningId );
        return runningVOList;
    }
    private List<RunningVO> _queryRunningNodeVOByRunningId(   String runningId) {
        List<RunningVO> runningVOList = new ArrayList<> ();
        //需要查询的数据列
        String[] projection = {RunningNodeVOEntry._ID, RunningNodeVOEntry.speed, RunningNodeVOEntry.calorie,
            RunningNodeVOEntry.kilometer, RunningNodeVOEntry.runningPace, RunningNodeVOEntry.nowTime,
            RunningNodeVOEntry.lat, RunningNodeVOEntry.lag, RunningNodeVOEntry.duration, RunningNodeVOEntry.color,
            RunningNodeVOEntry.kilometerNode, RunningNodeVOEntry.runningId};
        //排序
        String sortOrder = RunningNodeVOEntry.nowTime + " DESC";
        //查询条件
        String selection = RunningNodeVOEntry.runningId + " = '" + runningId + "'";
        //查询条件值
        //        String[] selectionArgs = {String.valueOf(runningId)};
        Cursor cursor =   App.getSQLdb (). query ( RunningNodeVOEntry.TABLE_NAME,  // The table to query
            projection,                               // The columns to return
            selection,                                // The columns for the WHERE clause
            null,                            // The values for the WHERE clause
            null,                                     // don't group the rows
            null,                                     // don't filter by row groups
            sortOrder                                 // The sort order
        );
        while (cursor.moveToNext ()) {
            RunningVO runningVO = new RunningVO ();
            runningVO.speed = cursor.getFloat ( cursor.getColumnIndex ( RunningNodeVOEntry.speed ) );
            runningVO.calorie = cursor.getFloat ( cursor.getColumnIndex ( RunningNodeVOEntry.calorie ) );
            runningVO.kilometer = cursor.getFloat ( cursor.getColumnIndex ( RunningNodeVOEntry.kilometer ) );
            runningVO.runningPace = cursor.getFloat ( cursor.getColumnIndex (  RunningNodeVOEntry.runningPace ) );
            runningVO.nowTime = cursor.getString ( cursor.getColumnIndex ( RunningNodeVOEntry.nowTime ) );
            runningVO.lat = cursor.getString ( cursor.getColumnIndex ( RunningNodeVOEntry.lat ) );
            runningVO.lag = cursor.getString ( cursor.getColumnIndex ( RunningNodeVOEntry.lag ) );
            runningVO.duration = cursor.getString ( cursor.getColumnIndex ( RunningNodeVOEntry.duration ) );
            runningVO.color = cursor.getString ( cursor.getColumnIndex ( RunningNodeVOEntry.color ) );
            runningVO.kilometerNode = cursor.getInt ( cursor.getColumnIndex (  RunningNodeVOEntry.kilometerNode ) ) > 0;
            runningVO.runningId = cursor.getLong ( cursor.getColumnIndex ( RunningNodeVOEntry.runningId ) );
            runningVOList.add ( runningVO );
        }
        return runningVOList;
    }

}
