import java.util.*;
public class SRT {
    public void simulate(List<JobObject> jobs) {
        // I use a Priority Queue for the rare case that the first couple processes start at the same time
        PriorityQueue<JobObject> allJobs = new PriorityQueue<>(Comparator.comparingInt(JobObject::getArrTime).thenComparingInt(JobObject::getRemainingTime));
        PriorityQueue<JobObject> currJobs = new PriorityQueue<>(Comparator.comparingInt(JobObject::getRemainingTime).thenComparingInt(JobObject::getArrTime));
        for(JobObject job : jobs) {
            allJobs.offer(job);
        }
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
            currTime = Math.max(currTime, currJob.getArrTime());

            JobObject preemptingJob = null;
            while(!allJobs.isEmpty() && currTime + currJob.getRemainingTime() >= allJobs.peek().getArrTime()) {
                JobObject nextJob = allJobs.poll();
                currJobs.offer(nextJob);
                int timeUsed = nextJob.getArrTime() - currTime;
                currJob.setRemainingTime(currJob.getRemainingTime() - timeUsed);
                currTime += timeUsed;
                if(currJob.getRemainingTime() > nextJob.getCpuBurst()){
                    preemptingJob = nextJob;
                    break;
                }
            }
            if(preemptingJob != null){ // job was preempted
                currJobs.offer(currJob);
            } else { // the job finished
                currTime += currJob.getRemainingTime();
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
        }
        float averageTurnAroundTime = (float) totalTurnAroundTime/jobs.size();
        System.out.printf("The average turn around time for SRT is: %f\n", averageTurnAroundTime);
        System.out.printf("The throughput for time=%d for SRT is: %d", throughputCutOff, throughput);
    }
}
