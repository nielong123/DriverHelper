package com.usblibrary;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.usblibrary.ukey.OTG_KEY;
import com.usblibrary.ukey.Tool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UsbMainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String ACTION_USB_PERMISSION = "com.usb.OTG_DEMO";

    public int threadCnt;

    private static final int MAX_LINES = 12;
    private static final int CNT_LINES = 11;

    private static final int PS_NO_FINGER = 0x02;
    private static final int PS_OK = 0x00;
    private static int CHAR_BUFFER_A = 0x01;
    private static int CHAR_BUFFER_B = 0x02;
    private final static int DEV_ADDR = 0xffffffff;
    private static int fingerCnt = 1;
    private static int IMAGE_X = 256;
    private static int IMAGE_Y = 288;

    public int thread_i = 0;
    public int thread_sum = 0;
    private UsbManager mUsbManager;
    private UsbDevice mDevice;
    private PendingIntent mPermissionIntent;

    boolean globalControl = true;

    private OTG_KEY msyUsbKey;
    int mhKey = 0;
    int mhCon = 0;
    private TextView mResponseTextView;
    public TextView mImputTextView;
    private Button mOpen;
    private Button mClose;      //停止识别
    private Button mSearch;
    private Button mClear;
    private Button mDevMsg;
    private Button mUpImage;
    private Button mEnroll;
    private Button mStop;
    private Button mUpChar;
    private Button mDownChar;
    private Button close;       //关闭页面

    String imagePath = "finger.bmp";
    byte[] fingerBuf = new byte[IMAGE_X * IMAGE_Y];
    // private EditText mLongEdit;
    ProgressBar bar = null;
    boolean ifChecked = false;

    // private EditText mEditText;
    ImageView fingerView = null;

    byte mbAppHand[] = new byte[1];
    byte mbConHand[] = new byte[1];
    boolean bIsOpen = false;
    byte[] g_TempData = new byte[512];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usbmain);

        mOpen = (Button) findViewById(R.id.BOpen);
        mOpen.setOnClickListener(this);

        mClose = (Button) findViewById(R.id.BClose);
        mClose.setOnClickListener(this);

        mDevMsg = (Button) findViewById(R.id.devMsg);
        mDevMsg.setOnClickListener(this);

        mUpImage = (Button) findViewById(R.id.upImage);
        mUpImage.setOnClickListener(this);

        bar = (ProgressBar) findViewById(R.id.bar);
        fingerView = (ImageView) findViewById(R.id.fingerImage);

        mEnroll = (Button) findViewById(R.id.BTEnroll);
        mEnroll.setOnClickListener(this);

        mSearch = (Button) findViewById(R.id.BSearch);
        mSearch.setOnClickListener(this);

        mClear = (Button) findViewById(R.id.BTClear);
        mClear.setOnClickListener(this);

        mStop = (Button) findViewById(R.id.BStop);
        mStop.setOnClickListener(this);

        mUpChar = (Button) findViewById(R.id.upChar);
        mUpChar.setOnClickListener(this);

        mDownChar = (Button) findViewById(R.id.downChar);
        mDownChar.setOnClickListener(this);

        close = (Button) findViewById(R.id.close);
        close.setOnClickListener(this);

        mImputTextView = (TextView) findViewById(R.id.imputTextView);
        mResponseTextView = (TextView) findViewById(R.id.TVLog);
        mResponseTextView.setMovementMethod(new ScrollingMovementMethod());
        // mResponseTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
        // mResponseTextView.setLines(17);
        mResponseTextView.setMaxLines(MAX_LINES);
        mResponseTextView.setText("");

        // mEditText = (EditText)findViewById(R.id.ETSend);
        // mEditText.addTextChangedListener(mTextWatcher);

        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);// �����������
        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(
                ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED);

        openState(true);
        registerReceiver(mUsbReceiver, filter);
        openUSB();
    }

    // pause
    protected void onPause() {
        super.onPause();
        globalControl = false;
        ctlStatus(true);
    }


    private void openUSB() {
        boolean requested = false;

        for (UsbDevice device : mUsbManager.getDeviceList().values()) {
            if (0x2109 == device.getVendorId()
                    && 0x7638 == device.getProductId()) {
                mDevice = device;
                mUsbManager.requestPermission(mDevice, mPermissionIntent);
                requested = true;
                logMsg("find this usb device vID:0x2109");
                break;
            }
            if (0x0453 == device.getVendorId()
                    && 0x9005 == device.getProductId()) {
                mDevice = device;
                mUsbManager.requestPermission(mDevice, mPermissionIntent);
                requested = true;
                logMsg("find this usb device vID:0x0453");
                break;
            }
        }

        if (requested) {
            if (!mUsbManager.hasPermission(mDevice)) {
                logMsg("no Permission!");
                return;
            }
            try {
                msyUsbKey = null;
                msyUsbKey = new OTG_KEY(mUsbManager, mDevice);
                int key[] = new int[1];
                int ret = msyUsbKey.UsbOpen();
                if (ret == OTG_KEY.DEVICE_SUCCESS) {
                    mhKey = key[0];
                    logMsg("open device success hkey :"
                            + Tool.int2HexStr(mhKey));
                } else {
                    logMsg("open device fail errocde :" + ret);// Tool.int2HexStr(ret));
                }

            } catch (Exception e) {
                logMsg("Exception: => " + e.toString());
                return;
            }
            int ret;
            if ((ret = msyUsbKey.PSword()) != 0) {
                logMsg("Verify password failed" + ret);
                // logMsg("my return:"+ msyUsbKey.synoprintf());
                return;
            }
            // logMsg("��֤�ɹ�");
        } else {
            logMsg("can't find this device!");
            return;
        }
        // ��ȡ�豸��Ϣ
        int[] indexMax = new int[1];
        int[] len = new int[1];
        byte[] index = new byte[256];
        if (0 != getUserContent(indexMax, index, len)) {
            logMsg("Get the device info failed");
            return;
        }
        if (len[0] == 0) {
            logMsg("The starting Num=" + indexMax[0] + "fingerprint");
            fingerCnt = 0;
        } else {
            indexMax[0]++;
            logMsg("The starting Num =" + indexMax[0] + "fingerprint");
            fingerCnt = indexMax[0];
        }
        openState(false);
    }

    private void closeUSB() {
        try {
            // uiState(true);
            logMsg("The device has been closed");
            openState(true);
            msyUsbKey.CloseCard(mhKey);

        } catch (Exception e) {
            logMsg("Exception: => " + e.toString());
            return;
        }
    }

    public void onClick(View v) {

        if (v == mOpen) {
            openUSB();
            return;
        }
        if (v == mClose) {
            closeUSB();
            return;
        }
        if (v == mEnroll) { // finger

            // logMsg("�������ָ");
            // bar.setVisibility(View.VISIBLE);
            // bar.setProgress(0);
            // handler.post(mGetFingerThread);

            if (fingerCnt >= 256) {
                logMsg("Fingerprint library is empty");
                return;
            }
            ctlStatus(true);
            globalControl = true;
            bar.setVisibility(View.VISIBLE);
            bar.setProgress(0);
            ImputAsyncTask asyncTask = new ImputAsyncTask();
            asyncTask.execute(1);
            return;
        }
        if (v == mSearch) // ����ָ��
        {
            globalControl = true;
            ctlStatus(true);
            SearchAsyncTask asyncTask_search = new SearchAsyncTask();
            asyncTask_search.execute(1);
            return;
        }
        if (v == mClear) // ���ָ�ƿ�
        {
            if (PS_OK != msyUsbKey.PSEmpty(DEV_ADDR)) {
                logMsg("Clear all fingerprint template datas failed");
            }
            fingerCnt = 0;
            logMsg("Clear all fingerprint template datas successfully");
            return;
        }
        if (v == mDevMsg) {
            int[] indexMax = new int[1];
            int[] len = new int[1];
            byte[] index = new byte[256];
            if (0 != getUserContent(indexMax, index, len)) {
                logMsg("Get the device info failed");
                return;
            }

            logMsg("The device have " + len[0] + " fingerprint datas");

            // byte[] temp = new byte[len[0]];
            int i;
            logMsg("info:");
            for (i = 0; i < len[0]; i++) {
                logMsg("id:" + index[i]);
            }
            if (len[0] != 0) {
                logMsg("Max ID��" + indexMax[0]);
            }
            logMsg("Get the devie info successfully");
            return;
        }
        if (v == mUpImage) // �ϴ�ͼ��
        {
            int ret;
            ctlStatus(true);
            globalControl = true;
            UpAsyncTask asyncTask_up = new UpAsyncTask();
            asyncTask_up.execute(1);
            return;
        }
        if (v == mStop) {
            globalControl = false;
            ctlStatus(false);
            return;
        }
        if (v == mUpChar) {
            int pageId = 0;
            if (0 != msyUsbKey.loadChar(CHAR_BUFFER_A, pageId)) {
                logMsg("loadChar failed");
                return;
            }
            if (0 == msyUsbKey.upChar(CHAR_BUFFER_A, g_TempData, 512)) {
                // Log.i( TAG2,
                // "g_TempData upChar = "+bytesToHexString(g_TempData));
                logMsg("Upload fingerprint feature successfully");
            } else {
                logMsg("Upload fingerprint feature failed");
            }
            return;
        }
        if (v == mDownChar) {
            // the 8 is test value
            int pageId = 8;
            // Log.i( TAG2,
            // "g_TempData downChar = "+bytesToHexString(g_TempData));
            if (0 == msyUsbKey.downChar(CHAR_BUFFER_A, g_TempData, 512)) {
                logMsg("Down the fingerprint feature successfully");
            } else {
                logMsg("Download the fingerprint feature failed");
                return;
            }
            try {
                // pageId�����������¼��һ����,���ID����������������洢����Ȼ����������
                if (msyUsbKey.storeChar(CHAR_BUFFER_A, pageId) != PS_OK) {
                    logMsg("Stroe the fingerprint failed");
                    return;
                } else {
                    logMsg("Store the fingerprint successfully");
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return;
        }
        if (v == close) {
            this.finish();
            closeUSB();
            return;
        }
    }

    // ����usb�Ĳ����Ϣ
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        // �յ���Ϣ
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                UsbDevice device = (UsbDevice) intent
                        .getParcelableExtra(UsbManager.EXTRA_DEVICE);
                if (device != null) {
                    // call method to set up device communication
                }
                logMsg("Add:  DeviceName:  " + device.getDeviceName()
                        + "  DeviceProtocol: " + device.getDeviceProtocol()
                        + "\n");

            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                UsbDevice device = intent
                        .getParcelableExtra(UsbManager.EXTRA_DEVICE);
                logMsg("Del: DeviceName:  " + device.getDeviceName()
                        + "  DeviceProtocol: " + device.getDeviceProtocol()
                        + "\n");
                openState(true);
                if (null != msyUsbKey) {
                    msyUsbKey.CloseCard(mhKey);
                    msyUsbKey = null;
                }
            }
        }
    };

    public synchronized void logMsg(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String oldMsg = mResponseTextView.getText().toString();
                mResponseTextView.setText(oldMsg + "\n" + msg);
                if (mResponseTextView.getLineCount() > CNT_LINES) {
                    // Toast.makeText(getApplicationContext(),
                    // "LINE:"+mResponseTextView.getLineCount()+" CNT",
                    // Toast.LENGTH_SHORT).show();
                    mResponseTextView.scrollTo(0,
                            (mResponseTextView.getLineCount() - CNT_LINES)
                                    * mResponseTextView.getLineHeight() + 5);
                }
            }
        });

//        new myAsyncTask().execute();
    }

    /*
     * private final TextWatcher mTextWatcher = new TextWatcher() { public void
     * beforeTextChanged(CharSequence s, int start, int count, int after) { }
     *
     * public void onTextChanged(CharSequence s, int start, int before, int
     * count) { }
     *
     * public void afterTextChanged(Editable s) { if (s.length() > 0) { int pos
     * = s.length() - 1; char c = s.charAt(pos); if ( !( c >= '0'&&c <='9'|| c
     * >= 'a'&&c <='f' || c >= 'A'&&c <='F')) { s.delete(pos,pos+1);
     * Toast.makeText(MainActivity.this,
     * "Error letter.",Toast.LENGTH_SHORT).show(); } } } };
     */
    @Override
    protected void onDestroy() {
        unregisterReceiver(mUsbReceiver);
        super.onDestroy();
    }

    private class myAsyncTask extends AsyncTask<Void, Void, Void> {

        protected void onPostExecute(final Void result) {
            super.onPostExecute(result);
            if (mResponseTextView.getLineCount() > CNT_LINES) {
                // Toast.makeText(getApplicationContext(),
                // "LINE:"+mResponseTextView.getLineCount()+" CNT",
                // Toast.LENGTH_SHORT).show();
                mResponseTextView.scrollTo(0,
                        (mResponseTextView.getLineCount() - CNT_LINES)
                                * mResponseTextView.getLineHeight() + 5);
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            return null;
        }
    }

    // imput finger thread
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            bar.setProgress(msg.arg1);
            if (0 == msg.arg1) {
                handler.post(mGetFingerThread);
            } else if (50 == msg.arg1) {
                logMsg("Get the first fingerprint successfully");
                logMsg("Please put the second fingerprint");

                int ret;
                if ((ret = msyUsbKey.PSUpImage(DEV_ADDR, fingerBuf)) != 0) {
                    logMsg("Upload image failure:" + ret);
                }
                // logMsg("�ϴ�ͼ��ɹ�");
                if ((ret = WriteBmp(fingerBuf)) != 0) {
                    logMsg("Get bmp failure" + ret);
                }
                // logMsg("ͼ��д��ɹ�");
                String localName = "finger.bmp";
                FileInputStream localStream = null;
                try {
                    localStream = openFileInput(localName);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    try {
                        localStream.close();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    return;
                }
                Bitmap bitmap = BitmapFactory.decodeStream(localStream);
                fingerView.setImageBitmap(bitmap);

                try {
                    localStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                handler.post(mGetFingerThread);
            } else if (100 == msg.arg1) {
                logMsg("Get the second fingerprint successful");
                logMsg("Store the fingerprint successfully");
            }
        }
    };

    public class UpAsyncTask extends AsyncTask<Integer, String, Integer> {
        @Override
        protected Integer doInBackground(Integer... params) {
            int ret = 0;
            while (true) {
                if (globalControl == false) {
                    return -1;
                }
                while (msyUsbKey.PSGetImage(DEV_ADDR) != PS_NO_FINGER) {
                    if (globalControl == false) {
                        return -1;
                    }
                    try {
                        Thread.sleep(20);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
                while (msyUsbKey.PSGetImage(DEV_ADDR) == PS_NO_FINGER) {
                    if (globalControl == false) {
                        return -1;
                    }
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }

                if ((ret = msyUsbKey.PSUpImage(DEV_ADDR, fingerBuf)) != 0) {
                    publishProgress("Upload image failure:" + ret);
                    continue;
                }
                // logMsg("�ϴ�ͼ��ɹ�");
                if ((ret = WriteBmp(fingerBuf)) != 0) {
                    publishProgress("Get bmp failure:" + ret);
                    continue;
                }
                // logMsg("ͼ��д��ɹ�");
                publishProgress("OK");
            }
            // TODO Auto-generated method stub
        }

        // �߳̽���
        protected void onPostExecute(Integer result) {
            return;
        }

        // �߳̿�ʼ
        protected void onPreExecute() {
            logMsg("��ʾͼƬ,���ڴ�����������ָ");
            return;
        }

        // �߳��м�״̬
        protected void onProgressUpdate(String... values) {
            if (values[0].equals("OK")) {
                String localName = "finger.bmp";
                FileInputStream localStream = null;
                try {
                    localStream = openFileInput(localName);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    try {
                        localStream.close();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    return;
                }
                Bitmap bitmap = BitmapFactory.decodeStream(localStream);
                fingerView.setImageBitmap(bitmap);
                try {
                    localStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return;
            }
            logMsg(values[0]);
            mImputTextView.setText(values[0]);
            // Toast.makeText(getApplicationContext(), values[0],
            // Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public class SearchAsyncTask extends AsyncTask<Integer, String, Integer> {
        @Override
        protected Integer doInBackground(Integer... params) {
            // TODO Auto-generated method stub
            int ret;
            int[] fingerId = new int[1];
            while (true) {
                if (globalControl == false) {
                    return -1;
                }
                while (msyUsbKey.PSGetImage(DEV_ADDR) == PS_NO_FINGER) {
                    if (globalControl == false) {
                        return -1;
                    }
                    try {
                        Thread.sleep(20);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
                if ((ret = msyUsbKey.PSUpImage(DEV_ADDR, fingerBuf)) != 0) {
                    // publishProgress("�ϴ�ͼ��ʧ��:"+ret);
                    continue;
                }
                // logMsg("�ϴ�ͼ��ɹ�");
                if ((ret = WriteBmp(fingerBuf)) != 0) {
                    // publishProgress("����bmp�ļ�ʧ��:"+ret);
                    continue;
                }
                // logMsg("ͼ��д��ɹ�");
                publishProgress("OK");

                if (msyUsbKey.PSGenChar(DEV_ADDR, CHAR_BUFFER_A) != PS_OK) {
                    // publishProgress("��������ʧ��");
                    continue;
                }
                if (PS_OK != msyUsbKey.PSSearch(DEV_ADDR, CHAR_BUFFER_A, 0, 10,
                        fingerId)) {
                    publishProgress("û���ҵ���ָ��");
                    continue;
                }
                publishProgress("�ɹ���������ָ��,ID===>" + fingerId[0]);
            }
        }

        // �߳̽���
        protected void onPostExecute(Integer result) {
            return;
        }

        // �߳̿�ʼ
        protected void onPreExecute() {
            logMsg("����ָ��=>��ʼ,�������ָ");
            return;
        }

        // �߳��м�״̬
        protected void onProgressUpdate(String... values) {
            if (values[0].equals("OK")) {
                String localName = "finger.bmp";
                FileInputStream localStream = null;
                try {
                    localStream = openFileInput(localName);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    try {
                        localStream.close();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    return;
                }
                Bitmap bitmap = BitmapFactory.decodeStream(localStream);
                fingerView.setImageBitmap(bitmap);
                try {
                    localStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return;
            }
            logMsg(values[0]);
            mImputTextView.setText(values[0]);
            // Toast.makeText(getApplicationContext(), values[0],
            // Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public class ImputAsyncTask extends AsyncTask<Integer, String, Integer> {
        byte[][] fingerFeature = new byte[6][512];
        int Progress = 0;

        @Override
        protected Integer doInBackground(Integer... params) {
            // TODO Auto-generated method stub
            int cnt = 1;
            int ret;
            while (true) {
                if (globalControl == false) {
                    return -1;
                }
                while (msyUsbKey.PSGetImage(DEV_ADDR) != PS_NO_FINGER) {
                    if (globalControl == false) {
                        return -1;
                    }
                    try {
                        Thread.sleep(20);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
                while (msyUsbKey.PSGetImage(DEV_ADDR) == PS_NO_FINGER) {
                    if (globalControl == false) {
                        return -1;
                    }
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }

                if ((ret = msyUsbKey.PSUpImage(DEV_ADDR, fingerBuf)) != 0) {
                    publishProgress("�ϴ�ͼ��ʧ��:" + ret);
                    continue;
                }
                // logMsg("�ϴ�ͼ��ɹ�");
                if ((ret = WriteBmp(fingerBuf)) != 0) {
                    publishProgress("Built bmp paper failed:" + ret);
                    continue;
                }
                // logMsg("ͼ��д��ɹ�");
                publishProgress("OK");

                // ����ģ��
                if (cnt == 1) {
                    if ((ret = msyUsbKey.PSGenChar(DEV_ADDR, CHAR_BUFFER_A)) != PS_OK) {
                        publishProgress("Fingerprint 1 get feature failed:"
                                + ret);
                        return -1;
                    } else {
                        publishProgress("Pls put your finger again");
                        // publishProgress("����ģ��ʧ��");
                    }
                }
                if (cnt == 2) {
                    if ((ret = msyUsbKey.PSGenChar(DEV_ADDR, CHAR_BUFFER_B)) != PS_OK) {
                        publishProgress("Fingerprint 2 get feature failed:"
                                + ret);
                        continue;
                    } else {
                        // publishProgress("����ģ��ʧ��");
                    }
                    if (msyUsbKey.PSRegModule(DEV_ADDR) != PS_OK) {
                        bar.setProgress(0);
                        thread_i = 0;
                        thread_sum = 0;
                        publishProgress("Get fingerprint template failed,please try again");
                        // handler.removeCallbacks(mGetFingerThread);
                        return -1;
                    }
                    // publishProgress("����ģ��ɹ�");
                    // need to fix the 1
                    if (fingerCnt >= 256) {
                        publishProgress("Finger template stored,please delete the fingerprint ingormation");
                    }
                    if (msyUsbKey.PSStoreChar(DEV_ADDR, 1, fingerCnt) != PS_OK) {
                        bar.setProgress(0);
                        thread_i = 0;
                        thread_sum = 0;
                        publishProgress("Store the fingerprint failure,please try again");
                        // handler.removeCallbacks(mGetFingerThread);
                        return -1;
                    }
                    publishProgress("Enroll fingerprint success,=====>ID:"
                            + fingerCnt);
                    return 0;
                }
                cnt++;
            }
            // publishProgress("ָ��¼��ɹ�");
        }

        // �߳̽���
        protected void onPostExecute(Integer result) {
            globalControl = false;
            ctlStatus(false);
            if (0 == result) {
                if (fingerCnt > 256) {
                    logMsg("Fingerprint storage is fulled,please delete the fingerprint datas");
                    return;
                }
                // logMsg("¼��ָ�Ƴɹ�");
                // logMsg("fingerFeature[0]:"+Base64.encodeToString(fingerFeature[0],
                // Base64.DEFAULT ));
                fingerCnt++;
                bar.setProgress(100);
                return;
            } else {
                bar.setProgress(0);
                logMsg("Entry fingerprint failure��pls try again");
                return;
            }
        }

        // �߳̿�ʼ
        protected void onPreExecute() {
            logMsg("Enroll fingerprint=>Please put your finger");
            mImputTextView
                    .setText("Enroll fingerprint=>Please put your finger");
            return;
        }

        // �߳��м�״̬
        protected void onProgressUpdate(String... values) {
            if (values[0].equals("OK")) {
                String localName = "finger.bmp";
                FileInputStream localStream = null;
                try {
                    localStream = openFileInput(localName);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    try {
                        localStream.close();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    return;
                }
                Bitmap bitmap = BitmapFactory.decodeStream(localStream);
                fingerView.setImageBitmap(bitmap);
                Progress += 50;
                bar.setProgress(Progress);
                try {
                    localStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return;
            }
            logMsg(values[0]);
            // Toast.makeText(getApplicationContext(), values[0],
            // Toast.LENGTH_SHORT).show();
            return;
        }
    }

    Runnable mGetFingerThread = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            Message msg = handler.obtainMessage();
            int ret;
            if (thread_i == 0) {
                msg.arg1 = 0;
                handler.sendMessage(msg);
            } else if (thread_i == 1) {
                while (msyUsbKey.PSGetImage(DEV_ADDR) == PS_NO_FINGER) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
                try {
                    Thread.sleep(30);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                if ((ret = msyUsbKey.PSGenChar(DEV_ADDR, CHAR_BUFFER_A)) != PS_OK) {
                    logMsg("Get fingerprint feature failed!  Number = 1 " + ret);
                } else {
                    logMsg("Get fingerprint feature successful !  Number = 1 ");
                }
                thread_sum += 50;
                msg.arg1 = thread_sum;
                handler.sendMessage(msg);
            } else if (thread_i == 2) {
                while (true) {
                    if (msyUsbKey.PSGetImage(DEV_ADDR) != PS_NO_FINGER) {
                        break;
                    }
                    try {
                        Thread.sleep(30);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
                logMsg("Please put your finger again");

                while (msyUsbKey.PSGetImage(DEV_ADDR) == PS_NO_FINGER) {
                    try {
                        Thread.sleep(30);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
                logMsg("Get the second fingerprint successfully");
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                if (msyUsbKey.PSGenChar(DEV_ADDR, CHAR_BUFFER_B) != PS_OK) {
                    bar.setProgress(0);
                    thread_i = 0;
                    thread_sum = 0;
                    logMsg("Get the second fingerprint feature failed,pls try again");
                    // handler.removeCallbacks(mGetFingerThread);
                    return;
                } else {
                    logMsg("Get the second fingerprint feature successfully");
                }
                thread_sum += 50;
                msg.arg1 = thread_sum;
                // handler.sendMessage(msg);
                if (msyUsbKey.PSRegModule(DEV_ADDR) != PS_OK) {
                    bar.setProgress(0);
                    thread_i = 0;
                    thread_sum = 0;
                    logMsg("Syntheic fingerprint template failed,pls try again");
                    // handler.removeCallbacks(mGetFingerThread);
                    return;
                }
                // logMsg("����ģ��ɹ�");
                // need to fix the 1
                if (fingerCnt >= 256) {
                    logMsg("The fingerprint template storage,pls delete the part of the fingerprint datas");
                }
                if (msyUsbKey.PSStoreChar(DEV_ADDR, 1, fingerCnt) != PS_OK) {
                    bar.setProgress(0);
                    thread_i = 0;
                    thread_sum = 0;
                    logMsg("Strage the fingerprint data failure,pls try again");
                    // handler.removeCallbacks(mGetFingerThread);
                    return;
                }
                logMsg("Enroll fingerprint successful,=====>ID:" + fingerCnt);
                fingerCnt++;
                bar.setProgress(thread_sum);
                thread_i = 0;
                thread_sum = 0;
                // handler.removeCallbacks(mGetFingerThread);
            }
            thread_i++;
        }
    };

    public int getUserContent(int[] max, byte[] fingerid, int[] len) {
        byte[] UserContent = new byte[32];
        byte bt, b;
        int ret = 0;
        int i;
        int iBase;
        int iIndex = 0;
        int iIndexOffset;
        int[] indexFinger = new int[256];
        int j = 0;
        int indexMax = 0;
        ret = msyUsbKey.PSReadIndexTable(DEV_ADDR, 0, UserContent);
        if (ret != 0) {
            return -1;
        }

        for (i = 0; i < 32; i++) {
            bt = UserContent[i];
            iBase = i * 8;
            if (bt == (byte) 0x00) {
                continue;
            }

            for (b = (byte) 0x01, iIndexOffset = 0; iIndexOffset < 8; b = (byte) (b << 1), iIndexOffset++) {
                if (0 == (bt & b)) {
                    continue;
                }
                iIndex = iBase + iIndexOffset;
                indexFinger[j] = iIndex;
                j++;
                if (iIndex > indexMax) {
                    indexMax = iIndex;
                }
            }
        }
        max[0] = indexMax;
        len[0] = j;

        for (i = 0; i < j; i++) {
            fingerid[i] = (byte) indexFinger[i];
        }

        return 0;
    }

    public int WriteBmp(byte[] imput) {
        String fileName = "finger.bmp";
        FileOutputStream fout = null;
        try {
            fout = openFileOutput(fileName, MODE_PRIVATE);
        } catch (FileNotFoundException e2) {
            // TODO Auto-generated catch block
            return -100;
        }
        byte[] temp_head = {0x42, 0x4d,// file type
                // 0x36,0x6c,0x01,0x00, //file size***
                0x0, 0x0, 0x0, 0x00, // file size***
                0x00, 0x00, // reserved
                0x00, 0x00,// reserved
                0x36, 0x4, 0x00, 0x00,// head byte***
                // infoheader
                0x28, 0x00, 0x00, 0x00,// struct size

                // 0x00,0x01,0x00,0x00,//map width***
                0x00, 0x00, 0x0, 0x00,// map width***
                // 0x68,0x01,0x00,0x00,//map height***
                0x00, 0x00, 0x00, 0x00,// map height***

                0x01, 0x00,// must be 1
                0x08, 0x00,// color count
                0x00, 0x00, 0x00, 0x00, // compression
                // 0x00,0x68,0x01,0x00,//data size***
                0x00, 0x00, 0x00, 0x00,// data size***
                0x00, 0x00, 0x00, 0x00, // dpix
                0x00, 0x00, 0x00, 0x00, // dpiy
                0x00, 0x00, 0x00, 0x00,// color used
                0x00, 0x00, 0x00, 0x00,// color important
        };
        byte[] head = new byte[1078];
        byte[] newbmp = new byte[1078 + IMAGE_X * IMAGE_Y];
        System.arraycopy(temp_head, 0, head, 0, temp_head.length);

        int i, j;
        long num;
        num = IMAGE_X;
        head[18] = (byte) (num & 0xFF);
        num = num >> 8;
        head[19] = (byte) (num & 0xFF);
        num = num >> 8;
        head[20] = (byte) (num & 0xFF);
        num = num >> 8;
        head[21] = (byte) (num & 0xFF);

        num = IMAGE_Y;
        head[22] = (byte) (num & 0xFF);
        num = num >> 8;
        head[23] = (byte) (num & 0xFF);
        num = num >> 8;
        head[24] = (byte) (num & 0xFF);
        num = num >> 8;
        head[25] = (byte) (num & 0xFF);

        j = 0;
        for (i = 54; i < 1078; i = i + 4) {
            head[i] = head[i + 1] = head[i + 2] = (byte) j;
            head[i + 3] = 0;
            j++;
        }
        System.arraycopy(head, 0, newbmp, 0, head.length);
        System.arraycopy(imput, 0, newbmp, 1078, IMAGE_X * IMAGE_Y);

        try {
            fout.write(newbmp);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            return -101;
        }
        try {
            fout.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return -102;
        }
        return 0;
    }

    private void openState(boolean state) {
        mClose.setEnabled(!state);
        mEnroll.setEnabled(!state);
        mDevMsg.setEnabled(!state);
        mUpImage.setEnabled(!state);
        mClear.setEnabled(!state);
        mSearch.setEnabled(!state);
        mStop.setEnabled(!state);
        mUpChar.setEnabled(!state);
        mDownChar.setEnabled(!state);
    }

    private void ctlStatus(boolean state) {
        mClose.setEnabled(!state);
        mEnroll.setEnabled(!state);
        mDevMsg.setEnabled(!state);
        mUpImage.setEnabled(!state);
        mClear.setEnabled(!state);
        // mOpen.setEnabled(!state);
        mSearch.setEnabled(!state);
        mUpChar.setEnabled(!state);
        mDownChar.setEnabled(!state);
    }
}
