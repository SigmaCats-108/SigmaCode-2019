package frc.team108.inputs;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

public class NavX
{
    public AHRS AHRS = new AHRS(SPI.Port.kMXP);
    public double yaw, angle, pitch, roll, actualYaw, b;

    public void setAngle()
    {
        yaw = AHRS.getYaw();
        angle = AHRS.getAngle();
        pitch = AHRS.getPitch();
        roll = AHRS.getRoll();
        setActualYaw();
    }

    public void resetAngle()
    {
        AHRS.zeroYaw();
    }

    public void testAngle()
    {
        System.out.println("Yaw: " + yaw + "Yaw (Unclamped): " + angle + " Pitch: " + pitch + " Roll: " + roll);
    }

    public void setActualYaw()
    {
        if(yaw < 0)
        {
            b = Math.abs(yaw);
            actualYaw = 360 - b;
        }
        else
        {
            actualYaw = yaw;
        }
    }
}