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
    it is reccomended that you stick to the above configuration for ease of use

Simply download or clone the <a href="https://google.com" target="_blank" rel="noopener noreferrer">Quickstart</a> 
and open it an IDE of your choice.  

Alternatively, run ```git clone https://github.com/The-Founders-Academy/IntoTheDeep2025Fork``` in a terminal window. 

## Mecanum Drive Constants


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
travelled by time to get the empirical max robot speed.


Do the same for rotation using the ```rotationalTest``` located in the same directory. Record the test over
a set amount of time and divide the amount of total rotations completed by the time to get the max
rotation speed

!!! note
    These values give you the maximum value you should set for the speed and rotation constants,
    not necessarily the ideal value. For our 2024-25 season robot, our m_maxRobotSpeedMps value was around 3.2, 
    however setting it to 2.5 gave our driver much more control and worked better for the game.

The ```m_metersPerTick``` value can be set either empirically or through a formula. To set it through a formula,
find your odometry pod wheel's resolution and radius in meters, then use the

 ```2pi * radius * resolution = meters per tick```

formula to get your value. 

