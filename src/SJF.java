import java.util.*;

public class SJF {
    public void simulate(List<JobObject> jobs) {
        PriorityQueue<JobObject> allJobs = new PriorityQueue<>(Comparator.comparingInt(JobObject::getArrTime).thenComparingInt(JobObject::getCpuBurst));
        PriorityQueue<JobObject> currJobs = new PriorityQueue<>(Comparator.comparingInt(JobObject::getCpuBurst).thenComparingInt(JobObject::getArrTime));
        for(JobObject job : jobs) {
            allJobs.offer(job);
        }
        currJobs.offer(allJobs.poll());
        final int throughputCutOff = 100;
        int currTime = 0;
        int totalTurnAroundTime = 0;
        int jobCount = 1;
        int throughput = 0;

        while(!allJobs.isEmpty() || !currJobs.isEmpty()) {
            if(currJobs.isEmpty()) {
                currJobs.offer(allJobs.poll());
            }
            JobObject currJob = currJobs.poll();
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

            while(!allJobs.isEmpty() && currTime >= allJobs.peek().getArrTime()) {
                currJobs.offer(allJobs.poll());
            }
        }
        float averageTurnAroundTime = (float) totalTurnAroundTime/jobs.size();
        System.out.printf("The average turn around time for SJF is: %f\n", averageTurnAroundTime);
        System.out.printf("The throughput for time=%d SJF is: %d", throughputCutOff, throughput);
    }
}
