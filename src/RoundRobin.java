import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class RoundRobin {

    public void simulate(List<JobObject> jobs) {
        LinkedList<JobObject> jobQueue = new LinkedList<>(jobs);
        LinkedList<JobObject> readyQueue = new LinkedList<>();
        jobQueue.sort(Comparator.comparingInt(JobObject::getArrTime));
        readyQueue.offerFirst(jobQueue.pollFirst());
        Random random = new Random();
        int quantum = random.nextInt(3) + 1;
        final int throughputCutOff = 100;
        int currTime = readyQueue.peekFirst().getArrTime();
        int totalTurnAroundTime = 0;
        int jobCount = 1;
        int throughput = 0;
        int contextSwitch = 1;

        while(!jobQueue.isEmpty() || !readyQueue.isEmpty()) {
            JobObject currJob = readyQueue.pollFirst();
            int remainingTime;
            if (currTime != 0) currTime += contextSwitch;
            if (currJob != null) {
               remainingTime = currJob.getRemainingTime();
            } else {
               remainingTime =  Integer.MAX_VALUE;
            }
            while(!jobQueue.isEmpty() && currTime + Math.min(quantum, remainingTime)>= jobQueue.peekFirst().getArrTime()) {
                JobObject readyJob = jobQueue.pollFirst();
                readyQueue.offerLast(readyJob);
            }
            if(currJob != null){
                int timeUsed = Math.min(quantum, currJob.getRemainingTime());
                currJob.setRemainingTime(currJob.getRemainingTime() - timeUsed);
                currTime += timeUsed;
                if(currJob.getRemainingTime() > 0) {
                    readyQueue.offerLast(currJob);
                } else {
                    currJob.setExitTime(currTime);
                    int turnAroundTime = currJob.getExitTime() - currJob.getArrTime();
                    totalTurnAroundTime += turnAroundTime;
                    currJob.setTurnAroundTime(turnAroundTime);
                    currJob.setWaitingTime(turnAroundTime - currJob.getCpuBurst());
                    System.out.println("Job #" + jobCount + "\t" + currJob);
                    jobCount++;
                    if(currTime <= throughputCutOff) {
                        throughput++;
                    }
                }
            } else {
                currTime += quantum;
            }
        }
        float averageTurnAroundTime = (float) totalTurnAroundTime/jobs.size();
        System.out.printf("The average turn around time for RoundRobin is: %f\n", averageTurnAroundTime);
        System.out.printf("The throughput, number of jobs entered and completed, for time=%d for RoundRobin is: %d\n", throughputCutOff, throughput);
        System.out.printf("quantum=%d, contextSwitch=%d", quantum, contextSwitch);

    }
}
