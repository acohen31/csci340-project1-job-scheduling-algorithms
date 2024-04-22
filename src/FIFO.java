import java.util.*;

public class FIFO {
    public void simulate(List<JobObject> jobs) {
        Queue<JobObject> allJobs = new LinkedList<>(jobs);
        final int throughputCutOff = 100;
        int throughput = 0;
        int currTime = 0;
        int totalTurnAroundTime = 0;
        int jobCount = 1;

        while(!allJobs.isEmpty()) {
            JobObject currJob = allJobs.poll();

            if(currJob.getArrTime() > currTime) {
                currTime = currJob.getArrTime();
            }

            currTime += currJob.getCpuBurst();
            int turnAroundTime = currTime - currJob.getArrTime();
            totalTurnAroundTime += turnAroundTime;
            currJob.setExitTime(currTime);
            currJob.setTurnAroundTime(turnAroundTime);
            currJob.setWaitingTime(turnAroundTime - currJob.getCpuBurst());
            System.out.println("Job #" + jobCount + "\t" + currJob);
            jobCount++;
            if(currTime <= throughputCutOff) {
                throughput++;
            }
        }
        float averageTurnAroundTime = (float) totalTurnAroundTime/jobs.size();
        System.out.printf("The average turn around time for FIFO is: %f\n", averageTurnAroundTime);
        System.out.printf("The throughput for time=%d for FIFO is: %d", throughputCutOff, throughput);
    }
}
