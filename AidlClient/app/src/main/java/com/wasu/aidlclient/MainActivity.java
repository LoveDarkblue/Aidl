package com.wasu.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.wasu.aidlserver.Student;
import com.wasu.aidlserver.StudentManager;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";

    private StudentManager mStudentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void addStudentIn(View v){
        Student student = new Student();
        student.setName("ClientIn");
        student.setAge(20);
        try {
            //注意，in、out等修饰符影响的是传入的对象student，并不是返回值，返回值student1和server端返回的一致，不会受到影响
            Student student1 = mStudentManager.addStudentIn(student);
            Log.e(TAG, student.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void addStudentOut(View v){
        Student student = new Student();
        student.setName("ClientOut");
        student.setAge(21);
        try {
            Student student1 = mStudentManager.addStudentOut(student);
            Log.e(TAG, student.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void addStudentInOut(View v){
        Student student = new Student();
        student.setName("ClientInOut");
        student.setAge(22);
        try {
            Student student1 = mStudentManager.addStudentInOut(student);
            Log.e(TAG, student.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
    }

    private void connect() {
        Intent intent = new Intent();
        intent.setAction("com.wasu.aidlserver.aidl");
        intent.setPackage("com.wasu.aidlserver");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mStudentManager = StudentManager.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
