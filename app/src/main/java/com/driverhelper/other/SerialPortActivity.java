/*
 * Copyright 2009 Cedric Priscal
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package com.driverhelper.other;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.driverhelper.app.MyApplication;
import com.jaydenxiao.common.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import qingwei.kong.serialportlibrary.SerialPort;

public abstract class SerialPortActivity extends BaseActivity {

    protected MyApplication mApplication;
    protected SerialPort obdSerialPort;
    protected SerialPort icReaderSerialPort;

    protected OutputStream obdOutputStream, icReaderOutputStream;
    private InputStream obdInputStream, icReaderInputStream;


    private ObdReadThread obdReadThread;                //obd的读数据线程
    private IcReaderReadThread icReaderReadThread;          //ic读卡器的读数据线程

    HandlerThread sendingObdHandlerThread = new HandlerThread("sendingObdHandlerThread");

    {
        sendingObdHandlerThread.start();
    }

    HandlerThread sendingIcReaderHandlerThread = new HandlerThread("sendingHandlerThread");

    {
        sendingIcReaderHandlerThread.start();
    }

    protected Handler sendingObdHandler = new Handler(sendingObdHandlerThread.getLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (obdOutputStream != null) {
                try {
                    obdOutputStream.write((byte[]) msg.obj);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    protected Handler sendingIcReaderHandler = new Handler(sendingIcReaderHandlerThread.getLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (icReaderOutputStream != null) {
                try {
                    icReaderOutputStream.write((byte[]) msg.obj);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private class ObdReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                int size;
                try {
                    byte[] buffer = new byte[64];
                    if (obdInputStream == null) return;
                    size = obdInputStream.read(buffer);
                    if (size > 0) {
                        onOBDDataReceived(buffer, size);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class IcReaderReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                int size;
                try {
                    byte[] buffer = new byte[64];
                    if (icReaderInputStream == null) return;
                    size = icReaderInputStream.read(buffer);
                    if (size > 0) {
                        onIcReaderDataReceived(buffer, size);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    private void DisplayError(String resourceId) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Error");
        b.setMessage(resourceId);
        b.setPositiveButton("OK", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SerialPortActivity.this.finish();
            }
        });
        b.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = MyApplication.getInstance();
        try {
            /*** obd ***/
            obdSerialPort = mApplication.getObdSerialPort();
            obdOutputStream = obdSerialPort.getOutputStream();
            obdInputStream = obdSerialPort.getInputStream();

			/* Create a receiving thread */
            obdReadThread = new ObdReadThread();
            obdReadThread.start();

            /*** icReader  ***/
            icReaderSerialPort = mApplication.getIcReaderSerialPort();
            icReaderOutputStream = icReaderSerialPort.getOutputStream();
            icReaderInputStream = icReaderSerialPort.getInputStream();

//			/* Create a receiving thread */
            icReaderReadThread = new IcReaderReadThread();
            icReaderReadThread.start();
        } catch (SecurityException e) {
            // You do not have read/write permission to the serial port.
            DisplayError("You do not have read/write permission to the serial port.");
        } catch (IOException e) {
            // The serial port can not be opened for an unknown reason.
            DisplayError("The serial port can not be opened for an unknown reason.");
        } catch (InvalidParameterException e) {
            // Please configure your serial port first.
            DisplayError("Please configure your serial port first.");
        }
    }

    protected abstract void onOBDDataReceived(final byte[] buffer, final int length);

    protected abstract void onIcReaderDataReceived(final byte[] buffer, final int size);

    @Override
    protected void onDestroy() {
        if (obdReadThread != null)
            obdReadThread.interrupt();
        mApplication.closeObdSerialPort();
        obdSerialPort = null;
        if (null != sendingObdHandler) {
            sendingObdHandlerThread.quit();
        }

        if (icReaderReadThread != null)
            icReaderReadThread.interrupt();
        mApplication.closeIcReaderSerialPort();
        icReaderSerialPort = null;
        if (null != icReaderReadThread) {
            sendingIcReaderHandlerThread.quit();
        }
        super.onDestroy();
    }
}
