# Quickstart Guide


## Introduction

Hello and welcome to the quickstart Guide for Soar, a codebase and library designed by the 
2025 Screaming Eagles Programming team. This quickstart, similar to roadrunner, is a full
codebase of everything you need to get a robot or field relative drive and command based autonomous
working as quickly as possible, using an FTCLib structure. 

If you'd like to use an existing codebase, check out the [Soar Library Guide](LibraryGuide.md).



## Installation

!!! note
    By default, this project works under the assumption that a team is using a 3 odometry pod setup and 
    a mecanum drivetrain. While modifications can be made for teams that are using different localization options,
    it is reccomended that you stick to the above configuration for ease of use.

Simply download or clone the <a href="https://google.com" target="_blank" rel="noopener noreferrer">Quickstart</a> 
and open it an IDE of your choice.  

Alternatively, run ```git clone https://github.com/The-Founders-Academy/IntoTheDeep2025Fork``` in a terminal window. 

## MecanumConfigs Constants


To make sure that this codebase works with every type of robot, some information must be provided in the ```MecanumDrive``` and 
```MecanumConstants``` files. 

### Wheel Positions

First, open the ```MecanumConfigs``` file at the path ```TeamCode/src/main/java/org/firstinspires/ftc/teamcode/shared/mecanum/MecanumConfigs.java```

At the top of the file you will see:
```java 
    private Translation2d m_frontLeftPositionMeters = new Translation2d(0.178, 0.168);
    private Translation2d m_frontRightPositionMeters = new Translation2d(0.178, -0.168);
    private Translation2d m_backLeftPositionMeters = new Translation2d(-0.178, 0.168);
    private Translation2d m_backRightPositionMeters = new Translation2d(-0.178, -0.168);
```

Replace the values above with the correct meter values in (x, y) coordinates. 
Measure out from the centerpoint of your robot to calculate, 
ensuring your result is in meters.


```(Put Image Here)```


### Empirical Constants

You will then need to provide values for:
```java
 private double m_maxRobotSpeedMps = 2.5;
 private double m_maxRobotRotationRps = 6.28;
 private double m_metersPerTick = 0.00056; 
```
These are determined through testing. In the ```current/tuning``` directory of the quickstart, find ```forwardDriveTest```. 
This will run all 4 wheels at 100% power. Make sure that they are named correctly in your driver station 
configuration, then hit start and record the time it takes to travel a set distance. Then divide the distance 
travelled by total time to get the max robot speed.


Do the same for rotation using the ```rotationalTest``` located in the same directory. Record the test over
a set amount of time and divide the amount of total rotations completed by the time to get the max
rotation speed

!!! note
    These values give you the maximum value you should set for the speed and rotation constants,
    not necessarily the ideal value. For our 2024-25 season robot, our m_maxRobotSpeedMps value was around 3.2, 
    however setting it to 2.5 gave our driver much more control and worked better for grabbing and
    scoring game pieces.

The ```m_metersPerTick``` value can be set through a formula. To set it through a formula,
find your odometry pod wheel's resolution and radius in meters via the product page, then use the:

 ```2pi * radius * resolution = meters per tick```

formula to get your value. 

Finally, ensure that all of your drive motors are following this naming convention
in your driverstation config file:

```java
    private String m_frontLeftName = "fL";
    private String m_frontRightName = "fR";
    private String m_backLeftName = "bL";
    private String m_backRightName = "bR";
```


## MecanumDrive Constants

### Odometry Pod Configuration

Next, open the ```MecanumDrive``` file, and find: 

```java
    // Mecanum Constants
    public double deadWheelRadiusCentimeters = 2.4;
    public double ticksPerRevolution = 2000.0;
    
    public double trackWidthCentimeters = 36.3;
    double perpendicularOffsetCentimeters = 20.32;
```

Set ```deadWheelRadiusCentimeters``` and ```ticksPerRevolution``` as per the product page
of your odometry pods that you found earlier. Make sure that your deadwheel radius is in
centimeters. 


Then, measure the distance betewen your parallel odometry pods to find ```trackWidthCentimeters```
and the distance from the center point where your parallel odometry pods meet to the center
of your perpendicular odometry pod to find ```perpendicularOffsetCentimeters```.
All your values should be positive. 

```(Put Image Here)```

### Assigning Motor RPM

Finally, at the top of the MecanumDrive constructor, find:

```java
     m_frontLeft = new MotorEx(hardwareMap, m_mecanumConfigs.getFrontLeftName(), Motor.GoBILDA.RPM_312);
     m_frontRight = new MotorEx(hardwareMap, m_mecanumConfigs.getFrontRightName(), Motor.GoBILDA.RPM_312);
     m_backLeft = new MotorEx(hardwareMap, m_mecanumConfigs.getBackLeftName(), Motor.GoBILDA.RPM_312);
     m_backRight = new MotorEx(hardwareMap, m_mecanumConfigs.getBackRightName(), Motor.GoBILDA.RPM_312);
```

and replace ```Motor.GoBILDA.RPM_312``` with the RPM of your drive motors. 

## Tuning PIDs

### Translation Controller 

To start connect to your robot's WiFi network, then open FTCDashboard via the link ```192.168.43.1:8080/dash``` or 
```192.168.49.1:8080/dash``` if you use a phone as a robot controller. 

This will allow you to update the PID values live and see changes immediately without restarting the OpMode, which greatly
helps with tuning. 

Once on FTCDashboard, find the ```tuning``` directory and open the ```TranslationControllerTest``` file. By default, this will
have the robot go 100cm forward when clicking ```a```, and back to the zero position when clicking ```b```.

On FTCDashboard, you should see ```Current Position``` and ```Target Position``` in the telemetry section in the bottom right.
You should see the current position moving between about 100 and 0, as you hit the ```a``` and ```b``` buttons. 

If your current position is undershooting the target position, try slightly increasing the ```Translation P``` value in 
FTCDashboard. You'll find it in the top right, under the ```BaseMecanumParams``` dropdown. Changing the value and hitting enter will
update the value. If you are overshooting, try slightly decreasing the ```Translation P``` value. 

!!! Note
    We use a ```Translation P``` value and a ```Translation I``` value. ```Translation I``` adds some extra stopping force
    to the movement. if you find it's not correcting enough as it reaches the position, try slightly increasing ```Translation I```.
    If you notice weird jerky movements while the robot reaches a position, slightly decrease ```Translation I```. 

### Rotation Controller

Once you have a reasonably accurate translation controller, open the ```RotationControllerTest``` file, and open FTCDashboard.
This is similar to the Translation Controller test, but hitting the ```a``` and ```b``` buttons will rotate the robot between
0 and 180 degrees, instead of moving it. Once again, if your rotation is overshooting reduce ```Rotation P```, if it is 
undershooting increase it. 

Again adjust ```Rotation I``` as needed, decreasing if movements are overly jerky at the end, 
and increasing if it doesn't have enough "kick" or power to go to exactly 180 degrees. 

