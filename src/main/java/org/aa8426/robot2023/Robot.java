// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.aa8426.robot2023;

import org.aa8426.robot2023.commands.Autons;
import org.aa8426.robot2023.subsystems.SwerveModule;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * This code is based on a the zero to autonomous swerve code.
 * 
 * https://www.youtube.com/watch?v=0Xi9yb1IMyA&t=378s
 * 
 */
public class Robot extends TimedRobot {    
    private OperatorInterface operatorInterface;
    private RobotContainer robotContainer;
    private RobotState robotState;    
    private OurShuffleboard shuffleboard;
    private Autons autons;
    
    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        robotState = new RobotState();
        robotContainer = new RobotContainer();
        robotState.setRobotContainer(robotContainer);
        operatorInterface = new OperatorInterface(robotContainer);        
        UsbCamera camera = CameraServer.startAutomaticCapture();
        camera.setFPS(30);
        autons = new Autons(robotContainer, operatorInterface);
        shuffleboard = new OurShuffleboard(robotState, robotContainer, autons, operatorInterface);                
                
    }

    @Override
    public void robotPeriodic() {        

        //GyroLog.update(robotContainer.swerveSubsystem.gyro);
        //PowerLog.update(robotContainer.pdp);
    
        SmartDashboard.putNumber("gyro", robotContainer.swerveSubsystem.gyro.getAngle());
        
        //armSpeed = rubyTab.addPersistent("Arm Speed", 1.0).getEntry();        
        //SmartDashboard.putNumber("time", Timer.getFPGATimestamp());
        //Shuffleboard.update();
        //shuffleboard.update(operatorInterface);
        this.robotContainer.swerveSubsystem.updateSmartDashboard();
        //SmartDashboard.putNumber("moving", robotContainer.swerveSubsystem.odometer.getPoseMeters().getX());
    }
    /** This function is called once each time the robot enters Disabled mode. */


    @Override
    public void disabledInit() {
        CommandScheduler.getInstance().cancelAll();
        robotContainer.armSubsystem.disable();
    }

    @Override
    public void disabledPeriodic() {
    }

    /**
     * This autonomous runs the autonomous command selected by your
     * {@link RobotContainer} class.
     */
    @Override
    public void autonomousInit() {       
        robotContainer.swerveSubsystem.resetEncoders();        
        String autonCommandName = shuffleboard.getAutonName();

        Command command = autons.getCommand(autonCommandName);
        
        command.schedule();
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
        CommandScheduler.getInstance().run();        
    }

    @Override
    public void teleopInit() {        
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        CommandScheduler.getInstance().cancelAll();
        if (!robotContainer.swerveSubsystem.odometryInitialized) {
            robotContainer.swerveSubsystem.resetOdometry(null);
        }
        //robotContainer.swerveSubsystem.setCurrentRobotFieldOrientation(180);
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();
        this.operatorInterface.onTeleopPeriodic();
    }

    SwerveModule testingModule = null;

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
        testingModule = null;
    }
    
    public double angle = 0;

    public void testModuleAngle() {
        SwerveModuleState sms = new SwerveModuleState();
        sms.angle = Rotation2d.fromDegrees(angle);
        sms.speedMetersPerSecond = 0.5;
        testingModule.setDesiredState(sms);    
        SmartDashboard.putNumber("angle", angle);
    }
    
    @Override
    public void testPeriodic() {
        if (this.operatorInterface.drivePad.getStartButtonPressed()) {
            angle = angle + 45;
        }
        if (this.operatorInterface.drivePad.getYButtonPressed()) {
            testingModule = this.robotContainer.swerveSubsystem.frontLeft;
        }
        if (this.operatorInterface.drivePad.getBButtonPressed()) {
            testingModule = this.robotContainer.swerveSubsystem.frontRight;
        }
        if (this.operatorInterface.drivePad.getXButtonPressed()) {
            testingModule = this.robotContainer.swerveSubsystem.backLeft;
        }
        if (this.operatorInterface.drivePad.getAButtonPressed()) {
            testingModule = this.robotContainer.swerveSubsystem.backRight;
        }
        
        if (testingModule == null)
            return;
        
         
        //this.robotContainer.swerveSubsystem.frontRight.turningMotor.set(5);
        //this.robotContainer.swerveSubsystem.frontLeft.driveMotor.set(10);
        //this.robotContainer.swerveSubsystem.backLeft.driveMotor.set(10);
        //this.robotContainer.swerveSubsystem.backRight.driveMotor.set(5);       
    }
}
