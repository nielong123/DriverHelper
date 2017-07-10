package com.driverhelper.other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ReceiverOBDII extends BroadcastReceiver
{
  public static final String _AcceleratorPedalPosition = "AcceleratorPedalPosition";
  public static final String _AirFlowSensor = "AirFlowSensor";
  public static final String _BarometricPressure = "BarometricPressure";
  public static final String _CalculatedEngineLoad = "CalculatedEngineLoad";
  public static final String _DTCStoredInthisECU = "DTCStoredInthisECU";
  public static final String _EngineCoolantTemperature = "EngineCoolantTemperature";
  public static final String _EngineRPM = "EngineRPM";
  public static final String _EngineRunTime = "EngineRunTime";
  public static final String _EnvironmentTemperature = "EnvironmentTemperature";
  public static final String _FaultVehicleMileage = "FaultVehicleMileage";
  public static final String _FuelPressure = "FuelPressure";
  public static final String _IntakeAirTemperature = "IntakeAirTemperature";
  public static final String _IntakeManifoldPressure = "IntakeManifoldPressure";
  public static final String _LongTermFuelTrimBank1 = "LongTermFuelTrim–Bank1";
  public static final String _MalfunctionIndicatorLampStatus = "MalfunctionIndicatorLampStatus";
  public static final String _MileageFuelFlag = "MileageFuelFlag";
  public static final String _OBDRequirements = "OBDRequirements";
  public static final String _OilMassFuelLevel = "OilMassFuelLevel";
  public static final String _SparkAngleBeforeTDC = "SparkAngleBeforeTDC";
  public static final String _ThrottlePositionSensor = "ThrottlePositionSensor";
  public static final String _TirePressueLB = "TirePressueLB";
  public static final String _TirePressueLF = "TirePressueLF";
  public static final String _TirePressueRB = "TirePressueRB";
  public static final String _TirePressueRF = "TirePressueRF";
  public static final String _TotalFuelConsumption = "TotalFuelConsumption";
  public static final String _TotalMileage = "TotalMileage";
  public static final String _VehicleSpeed = "VehicleSpeed";
  public static final String _VehicleVoltage = "VehicleVoltage";
  Float AcceleratorPedalPosition = Float.valueOf(0.0F);
  Float AirFlowSensor = Float.valueOf(0.0F);
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
  Float LongTermFuelTrimBank1 = Float.valueOf(0.0F);
  int MalfunctionIndicatorLampStatus = 0;
  int MileageFuelFlag = 0;
  String OBDRequirements = "";
  Float OilMassFuelLevel = Float.valueOf(0.0F);
  Float SparkAngleBeforeTDC = Float.valueOf(0.0F);
  Float ThrottlePositionSensor = Float.valueOf(0.0F);
  int TirePressueLB = 0;
  int TirePressueLF = 0;
  int TirePressueRB = 0;
  int TirePressueRF = 0;
  int TotalFuelConsumption = 0;
  int TotalMileage = 0;
  int VehicleSpeed = 0;
  Float VehicleVoltage = Float.valueOf(0.0F);

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (paramIntent.getAction().equals("com.cpsdna.obdport.data"))
    {
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