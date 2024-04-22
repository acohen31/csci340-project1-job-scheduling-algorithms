import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JobObject implements Cloneable{
    protected int arrTime;
    protected int cpuBurst;
    protected int priority;
    protected int exitTime;
    protected int turnAroundTime;
    protected int remainingTime;

    protected int waitingTime;

    public JobObject(int arrTime, int cpuBurst, int priority) {
        this.arrTime = arrTime;
        this.cpuBurst = cpuBurst;
        this.priority = -priority;
        this.remainingTime = cpuBurst;
    }

    public int getArrTime() {
        return arrTime;
    }

    public int getCpuBurst() {
        return cpuBurst;
    }

    public int getPriority() {
        return priority;
    }

    public int getExitTime() {
        return exitTime;
    }

    public void setExitTime(int exitTime) {
        this.exitTime = exitTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    @Override
    public String toString() {
        return String.format("{  arrTime=%d, cpuBurst=%d, priority=%d, exitTime=%d, turnAroundTime=%d, waitingTime=%d   }",
                this.getArrTime(), this.getCpuBurst(), -1 * this.getPriority(), this.getExitTime(), this.getTurnAroundTime(), this.getWaitingTime());
    }

    @Override
    public JobObject clone() {
        try {
            JobObject clone = (JobObject) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

