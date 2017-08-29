package com.wasu.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aislli on 2017/8/29 0029.
 */

public class AidlService extends Service {
    String TAG = "AidlService";

    private ArrayList<Student> mStudents;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mStudentManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mStudents = new ArrayList<>();
        Student student = new Student();
        student.setName("Mary");
        student.setAge(20);
        mStudents.add(student);
    }

    private StudentManager.Stub mStudentManager = new StudentManager.Stub() {

        @Override
        public List<Student> getStudents() throws RemoteException {
            Log.e(TAG, "list:" + mStudents);
            return mStudents;
        }

        @Override
        public Student addStudentIn(Student student) throws RemoteException {
            if (null == student) {
                return null;
            }
            mStudents.add(student);
            //In修饰符，数据只能从客户端流向服务端，服务端修改传过来的对象的属性后，client端的student对象的属性并不会同步改变
            student.setName("serverIn");
            Log.e(TAG, "addStudentIn:" + mStudents);
            return student;
        }

        @Override
        public Student addStudentOut(Student student) throws RemoteException {
            if (null == student) {
                return null;
            }
            mStudents.add(student);
            //out修饰符，数据只能从服务端流向客户端，客户端主动传过来了对象，但是服务端只能收到一个空的student，服务端修改了这个student对象的属性后，客户端的student对象的属性会同步变化
            student.setName("serverOut");
            Log.e(TAG, "addStudentOut:" + mStudents);
            return student;
        }

        @Override
        public Student addStudentInOut(Student student) throws RemoteException {
            if (null == student) {
                return null;
            }
            mStudents.add(student);
            //inout修饰符，数据可双向通信，任意一端修改对象属性后，对端都会同步修改
            student.setName("serverInOut");
            Log.e(TAG, "addStudentInOut:" + mStudents);
            return student;
        }
    };
}
