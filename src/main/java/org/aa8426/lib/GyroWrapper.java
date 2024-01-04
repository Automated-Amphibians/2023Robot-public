package org.aa8426.lib;

import com.ctre.phoenix.sensors.PigeonIMUConfiguration;
import com.ctre.phoenix.sensors.WPI_PigeonIMU;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.SPI;

public class GyroWrapper {
    
    public final AHRS gyro_x = new AHRS(SPI.Port.kMXP);
    public final WPI_PigeonIMU gyro = new WPI_PigeonIMU(15);

    public GyroWrapper() {
        //this.gyro.configFactoryDefault();
        //PigeonIMUConfiguration settings = new PigeonIMUConfiguration();
        //gyro.configAllSettings(settings);                
    }

    public void reset() {
        gyro.reset();
    }

    public double getAngle() {
        //return gyro.getAngle();
        return -gyro.getYaw();
    }

    public Rotation2d getRotation2d() {
        return gyro.getRotation2d();
    }

    public void setAngleAdjustment(double angle) {
        //gyro.setAngleAdjustment(angle);
    }

    public void configFactoryDefault() {
        //gyro.configFactoryDefault();
    }

    public void configAllSettings(PigeonIMUConfiguration settings) {
        //gyro.configAllSettings(settings);
    }

    public double getPitch() {
        return gyro.getPitch();
    }

    public double getYaw() {
        //return gyro.getYaw();
        return gyro.getAngle();
    }
}
