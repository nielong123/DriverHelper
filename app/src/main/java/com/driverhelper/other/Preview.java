package com.driverhelper.other;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class Preview extends ViewGroup
        implements SurfaceHolder.Callback, Camera.PreviewCallback
{
    public Boolean CamStatChk = true;
    long ChkCount = 0L;
    private final String TAG = "Preview";
    boolean faceDetectionRunning = false;
    Camera mCamera;
    SurfaceHolder mHolder;
    Camera.Size mPreviewSize;
    List<Camera.Size> mSupportedPreviewSizes;
    SurfaceView mSurfaceView;

    public Preview(Context paramContext, SurfaceView paramSurfaceView)
    {
        super(paramContext);
        this.mSurfaceView = paramSurfaceView;
        this.mHolder = this.mSurfaceView.getHolder();
        this.mHolder.addCallback(this);
        this.mHolder.setType(3);
    }

    private void checkSupportedPictureSizeAtPreviewSize(List<Camera.Size> paramList)
    {
        List localList = this.mCamera.getParameters().getSupportedPreviewSizes();
        int i = -1 + paramList.size();
        if (i >= 0)
        {
            Camera.Size localSize1 = (Camera.Size)paramList.get(i);
            double d = localSize1.width / localSize1.height;
            for (int j = -1 + localList.size(); ; j--)
            {
                int k = 0;
                if (j >= 0)
                {
                    Camera.Size localSize2 = (Camera.Size)localList.get(j);
                    if (Math.abs(d - localSize2.width / localSize2.height) < 0.05D)
                        k = 1;
                }
                else
                {
                    if (k == 0)
                        paramList.remove(i);
                    i--;
                    break;
                }
            }
        }
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> paramList, int paramInt1, int paramInt2)
    {
        double d1 = paramInt1 / paramInt2;
        Object localObject;
        if (paramList == null)
            localObject = null;
        while (true)
        {
            localObject = null;
            double d2 = 1.797d;
            Iterator localIterator1 = paramList.iterator();
            while (localIterator1.hasNext())
            {
                Camera.Size localSize2 = (Camera.Size)localIterator1.next();
                if ((Math.abs(localSize2.width / localSize2.height - d1) <= 0.1D) && (Math.abs(localSize2.height - paramInt2) < d2))
                {
                    localObject = localSize2;
                    d2 = Math.abs(localSize2.height - paramInt2);
                }
            }
            if (localObject == null)
            {
                double d3 = 1.797D;
                Iterator localIterator2 = paramList.iterator();
                while (localIterator2.hasNext())
                {
                    Camera.Size localSize1 = (Camera.Size)localIterator2.next();
                    if (Math.abs(localSize1.height - paramInt2) < d3)
                    {
                        localObject = localSize1;
                        d3 = Math.abs(localSize1.height - paramInt2);
                    }
                }
            }
        }
    }

    public List<Camera.Size> getSupportedPictureSizes()
    {
        if (this.mCamera == null)
            return null;
        List localList = this.mCamera.getParameters().getSupportedPictureSizes();
        checkSupportedPictureSizeAtPreviewSize(localList);
        return localList;
    }

    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
        View localView;
        int i;
        int j;
        int k;
        int m;
        if ((paramBoolean) && (getChildCount() > 0))
        {
            localView = getChildAt(0);
            i = paramInt3 - paramInt1;
            j = paramInt4 - paramInt2;
            k = i;
            m = j;
            if (this.mPreviewSize != null)
            {
                k = this.mPreviewSize.width;
                m = this.mPreviewSize.height;
            }
            if (i * m > j * k)
            {
                int i1 = k * j / m;
                localView.layout((i - i1) / 2, 0, (i + i1) / 2, j);
            }
        }
        else
        {
            return;
        }
        int n = m * i / k;
        localView.layout(0, (j - n) / 2, i, (j + n) / 2);
    }

    protected void onMeasure(int paramInt1, int paramInt2)
    {
        int i = resolveSize(getSuggestedMinimumWidth(), paramInt1);
        int j = resolveSize(getSuggestedMinimumHeight(), paramInt2);
        setMeasuredDimension(i, j);
        if (this.mSupportedPreviewSizes != null)
            this.mPreviewSize = getOptimalPreviewSize(this.mSupportedPreviewSizes, i, j);
    }

    public void onPreviewFrame(byte[] paramArrayOfByte, Camera paramCamera)
    {
        long l = 1L + this.ChkCount;
        this.ChkCount = l;
        if (l > 30L)
        {
            this.ChkCount = 0L;
            int i = 0;
            int j = 0;
            int k = 0;
            while (i < 2 * (paramArrayOfByte.length / 3))
            {
                if ((short)(0xFF & paramArrayOfByte[i]) <= 64)
                    k++;
                i += 300;
                j++;
            }
            if (k * 100 / j > 65)
                this.CamStatChk = Boolean.valueOf(false);
        }
        else
        {
            return;
        }
        this.CamStatChk = Boolean.valueOf(true);
    }

    public void setCamera(Camera paramCamera)
    {
        if (this.mCamera != null)
        {
            this.mCamera.stopPreview();
            this.mCamera.release();
            this.mCamera = null;
        }
        this.mCamera = paramCamera;
        if (this.mCamera != null)
        {
            this.mSupportedPreviewSizes = getSupportedPictureSizes();
            requestLayout();
            Camera.Parameters localParameters = this.mCamera.getParameters();
            if (localParameters.getSupportedFocusModes().contains("auto"))
            {
                localParameters.setFocusMode("auto");
                this.mCamera.setParameters(localParameters);
            }
        }
        try
        {
            this.mCamera.setPreviewDisplay(this.mHolder);
            this.mCamera.startPreview();
            return;
        }
        catch (IOException localIOException)
        {
            while (true)
                localIOException.printStackTrace();
        }
    }

    public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
    {
        if (this.mCamera != null)
        {
            Camera.Parameters localParameters = this.mCamera.getParameters();
            List localList = localParameters.getSupportedPreviewSizes();
            Camera.Size localSize = (Camera.Size)localList.get(0);
            for (int i = 0; i < localList.size(); i++)
                if (((Camera.Size)localList.get(i)).width > localSize.width)
                    localSize = (Camera.Size)localList.get(i);
            localParameters.setPreviewSize(localSize.width, localSize.height);
            this.mCamera.startPreview();
        }
    }

    public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
    {
        Log.d("@@@", "surfaceCreated");
        try
        {
            if (this.mCamera != null)
                this.mCamera.setPreviewDisplay(paramSurfaceHolder);
            return;
        }
        catch (IOException localIOException)
        {
            Log.e("Preview", "IOException caused by setPreviewDisplay()", localIOException);
        }
    }

    public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
    {
        if (this.mCamera != null)
            this.mCamera.stopPreview();
    }
}

/* Location:           E:\反编译助手\classes_dex2jar.jar
 * Qualified Name:     com.rongshen.e_course.EXSUNTerminal.Preview
 * JD-Core Version:    0.6.2
 */