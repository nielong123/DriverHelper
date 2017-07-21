package com.driverhelper.other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ReceiverOBDII extends BroadcastReceiver {
    public static final String _AcceleratorPedalPosition = "AcceleratorPedalPosition";    //加速器踏板位置
    public static final String _AirFlowSensor = "AirFlowSensor";                    //空气流量传感器
    public static final String _BarometricPressure = "BarometricPressure";        //气压
    public static final String _CalculatedEngineLoad = "CalculatedEngineLoad";    //计算发动机负荷
    public static final String _DTCStoredInthisECU = "DTCStoredInthisECU";      //ECU中保存的DTC
    public static final String _EngineCoolantTemperature = "EngineCoolantTemperature";  //发动机冷却液温度
    public static final String _EngineRPM = "EngineRPM";        //引擎转速
    public static final String _EngineRunTime = "EngineRunTime";        //引擎运行时间
    public static final String _EnvironmentTemperature = "EnvironmentTemperature";    //环境温度
    public static final String _FaultVehicleMileage = "FaultVehicleMileage";      //故障车辆的行驶里程
    public static final String _FuelPressure = "FuelPressure";        //燃油压力
    public static final String _IntakeAirTemperature = "IntakeAirTemperature";      //进气温度
    public static final String _IntakeManifoldPressure = "IntakeManifoldPressure";    //进气歧管压力
    public static final String _LongTermFuelTrimBank1 = "LongTermFuelTrim–Bank1";     //修正量库存  ???
    public static final String _MalfunctionIndicatorLampStatus = "MalfunctionIndicatorLampStatus";    //故障指示灯状态
    public static final String _MileageFuelFlag = "MileageFuelFlag";      //里程
    public static final String _OBDRequirements = "OBDRequirements";    //ODB要求
    public static final String _OilMassFuelLevel = "OilMassFuelLevel";    //油量
    public static final String _SparkAngleBeforeTDC = "SparkAngleBeforeTDC";    //点火提前量  ???
    public static final String _ThrottlePositionSensor = "ThrottlePositionSensor";      //油门位置感知器
    public static final String _TirePressueLB = "TirePressueLB";
    public static final String _TirePressueLF = "TirePressueLF";
    public static final String _TirePressueRB = "TirePressueRB";
    public static final String _TirePressueRF = "TirePressueRF";
    public static final String _TotalFuelConsumption = "TotalFuelConsumption";          //总油耗
    public static final String _TotalMileage = "TotalMileage";        //总公里数
    public static final String _VehicleSpeed = "VehicleSpeed";      //车速
    public static final String _VehicleVoltage = "VehicleVoltage";      //车辆电压
    Float AcceleratorPedalPosition = 0.0f;
    Float AirFlowSensor = 0.0f;
    int BarometricPressure = 0;
    int CalculatedEngineLoad = 0;
    int DTCStoredInthisECU = 0;
    int EngineCoolantTemperature = 0;
    int EngineRPM = 0;
    int EngineRunTime = 0;
    int EnvironmentTemperature = 0;
    int FaultVehicleMileage = 0;
    int FuelPressure = 0;
    int IntakeAirTemperature = 0;
    int IntakeManifoldPressure = 0;
    Float LongTermFuelTrimBank1 = 0.0F;
    int MalfunctionIndicatorLampStatus = 0;
    int MileageFuelFlag = 0;
    String OBDRequirements = "";
    Float OilMassFuelLevel = 0.0F;
    Float SparkAngleBeforeTDC = 0.0F;
    Float ThrottlePositionSensor = 0.0F;
    int TirePressueLB = 0;
    int TirePressueLF = 0;
    int TirePressueRB = 0;
    int TirePressueRF = 0;
    int TotalFuelConsumption = 0;
    int TotalMileage = 0;
    int VehicleSpeed = 0;
    Float VehicleVoltage = 0.0F;

    public void onReceive(Context paramContext, Intent paramIntent) {
        if (paramIntent.getAction().equals("com.cpsdna.obdport.data")) {
            Bundle localBundle = paramIntent.getExtras();
            this.MalfunctionIndicatorLampStatus = localBundle.getInt("MalfunctionIndicatorLampStatus");
            this.DTCStoredInthisECU = localBundle.getInt("DTCStoredInthisECU");
            this.OBDRequirements = localBundle.getString("OBDRequirements");
            this.VehicleVoltage = Float.valueOf(localBundle.getFloat("VehicleVoltage"));
            this.EngineRPM = localBundle.getInt("EngineRPM");
            this.VehicleSpeed = localBundle.getInt("VehicleSpeed");
            this.IntakeAirTemperature = localBundle.getInt("IntakeAirTemperature");
            this.EngineCoolantTemperature = localBundle.getInt("EngineCoolantTemperature");
            this.EnvironmentTemperature = localBundle.getInt("EnvironmentTemperature");
            this.IntakeManifoldPressure = localBundle.getInt("IntakeManifoldPressure");
            this.FuelPressure = localBundle.getInt("FuelPressure");
            this.BarometricPressure = localBundle.getInt("BarometricPressure");
            this.AirFlowSensor = Float.valueOf(localBundle.getFloat("AirFlowSensor"));
            this.ThrottlePositionSensor = Float.valueOf(localBundle.getFloat("ThrottlePositionSensor"));
            this.AcceleratorPedalPosition = Float.valueOf(localBundle.getFloat("AcceleratorPedalPosition"));
            this.EngineRunTime = localBundle.getInt("EngineRunTime");
            this.FaultVehicleMileage = localBundle.getInt("FaultVehicleMileage");
            this.OilMassFuelLevel = Float.valueOf(localBundle.getFloat("OilMassFuelLevel"));
            this.CalculatedEngineLoad = localBundle.getInt("CalculatedEngineLoad");
            this.LongTermFuelTrimBank1 = Float.valueOf(localBundle.getFloat("LongTermFuelTrim–Bank1"));
            this.SparkAngleBeforeTDC = Float.valueOf(localBundle.getFloat("SparkAngleBeforeTDC"));
            this.TirePressueLF = localBundle.getInt("TirePressueLF");
            this.TirePressueRF = localBundle.getInt("TirePressueRF");
            this.TirePressueLB = localBundle.getInt("TirePressueLB");
            this.TirePressueRB = localBundle.getInt("TirePressueRB");
            this.MileageFuelFlag = localBundle.getInt("MileageFuelFlag");
            this.TotalMileage = localBundle.getInt("TotalMileage");
            this.TotalFuelConsumption = localBundle.getInt("TotalFuelConsumption");
        }
    }
}

/* Location:           E:\反编译助手\classes_dex2jar.jar
 * Qualified Name:     com.rongshen.e_course.EXSUNTerminal.ReceiverOBDII
 * JD-Core Version:    0.6.2
 */