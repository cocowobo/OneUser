package com.peipao8.vehiclelock.LYangCode.utils.log;

import com.peipao8.vehiclelock.App;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogWriter {

    private   Writer mWriter;

    private   SimpleDateFormat df;

    private static boolean isDebug = App.isDebug;

    private static final String TAG = "=====LogWriter" ;

    private LogWriter(String file_path) throws IOException {
        File mFile = new File(file_path);
        mWriter = new BufferedWriter (new FileWriter (file_path), 2048);
        df = new SimpleDateFormat ("[yy-MM-dd hh:mm:ss]: ");
    }

    /**这个是不走单例的*/
    public static LogWriter openMore(String file_path) throws IOException {
        LogWriter mLogWriter = new LogWriter(file_path);
        return mLogWriter;
    }
    public void close() {
        try{
            mWriter.close();
        }catch (IOException e) {
            LYangLogUtil.e ( TAG, "close--> e=" + e );   // 异常输出-----
        }
    }
    public void print(String log) {
        try {
            if (isDebug) {
                mWriter.write ( df.format ( new Date () ) );
                mWriter.write ( log );
                mWriter.write ( "\n" );
                mWriter.flush ();
            }
        } catch (IOException e) {
            LYangLogUtil.e ( TAG, "print--> e=" + e );   // 异常输出-----
        }
    }

    public void print(Class cls, String log)  { //如果还想看是在哪个类里可以用这个方法
        try {
            if (isDebug) {
                mWriter.write(df.format(new Date ()));
                mWriter.write(cls.getSimpleName() + " ");
                mWriter.write(log);
                mWriter.write("\n");
                mWriter.flush();
            }
        } catch (IOException e) {
            LYangLogUtil.e ( TAG, "print--> e=" + e );   // 异常输出-----
        }
    }

}  