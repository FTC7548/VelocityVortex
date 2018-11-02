package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Demo Bot")
public class TeleOp extends OpMode {

    public DcMotor  DRIVE_LF,
                    DRIVE_LB,
                    DRIVE_RF,
                    DRIVE_RB,
                    INTAKE,
                    LIFT,
                    TRIGGER,
                    FLYWHEEL;

    public double flywheelPower = 0.5;

    public void init() {
        DRIVE_LF = hardwareMap.dcMotor.get("drivelf");
        DRIVE_RF = hardwareMap.dcMotor.get("driverf");
        DRIVE_RF.setDirection(DcMotor.Direction.REVERSE);
        DRIVE_LB = hardwareMap.dcMotor.get("drivelb");
        DRIVE_RB = hardwareMap.dcMotor.get("driverb");
        DRIVE_RB.setDirection(DcMotor.Direction.REVERSE);

        DRIVE_LF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        DRIVE_RF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        DRIVE_RB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        DRIVE_LB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        INTAKE = hardwareMap.dcMotor.get("intake");
        LIFT = hardwareMap.dcMotor.get("lift");
        TRIGGER = hardwareMap.dcMotor.get("trigger");
        FLYWHEEL = hardwareMap.dcMotor.get("flywheel");
    }

    public void loop() {
        drive();
        lift();
    }

    public void drive() {
        double pwr = gamepad1.left_stick_y; // -1 to 1
        double turn = gamepad1.right_stick_x; // -1 to 1

        double left = pwr + turn;
        double right = pwr - turn;

        DRIVE_LF.setPower(left);
        DRIVE_LB.setPower(left);
        DRIVE_RF.setPower(right);
        DRIVE_RB.setPower(right);
    }

    private boolean aPressed = false;
    private boolean flywheelToggle = false;
    private boolean dpadPressed = false;

    public void lift() {

        if (gamepad1.right_trigger > 0.5) {
            TRIGGER.setPower(1);
        } else {
            TRIGGER.setPower(0);
        }
        
        if (gamepad1.x) {
            INTAKE.setPower(1);
            LIFT.setPower(1);
            TRIGGER.setPower(-1);
        } else {
            INTAKE.setPower(0);
            LIFT.setPower(0);
            TRIGGER.setPower(0);
        }

        if (gamepad1.a) {
            if (!aPressed) {
                toggleFlywheel();
                aPressed = true;
            }
        } else {
            aPressed = false;
        }

        if (gamepad1.dpad_up || gamepad1.dpad_down) {
            if (!dpadPressed) {
                updatePower();
                dpadPressed = true;
            }
        } else {
            dpadPressed = false;
        }


    }

    public void toggleFlywheel() {
        if (flywheelToggle) {
            FLYWHEEL.setPower(0);
        } else {
            FLYWHEEL.setPower(flywheelPower);
        }
    }

    public void updatePower() {
        if (gamepad1.dpad_up) {
            flywheelPower = Range.clip(flywheelPower + 0.1, 0, 1);
            FLYWHEEL.setPower(flywheelPower);
        } else if (gamepad1.dpad_down) {
            flywheelPower = Range.clip(flywheelPower - 0.1, 0, 1);
            FLYWHEEL.setPower(flywheelPower);
        }
    }


}
