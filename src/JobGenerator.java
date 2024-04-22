import java.util.*;
import java.util.Random;
import java.util.ArrayList;
public class JobGenerator {

    public static List<JobObject> generateJobs(int numJobs) {
        List<JobObject> jobs = new ArrayList<>();
        Random random = new Random();

        for(int i = 0; i < numJobs; i++){
            int arrTime = random.nextInt(250) + 1;
            int cpuBurst = random.nextInt(15) + 2;
            int priority = random.nextInt(5) + 1;
            JobObject job = new JobObject(arrTime, cpuBurst, priority);
            jobs.add(job);
        }

        jobs.sort(Comparator.comparingInt(JobObject::getArrTime));
        return jobs;
    }

    public static List<JobObject> cloneJobs(List<JobObject> jobs) {
        List<JobObject> clonedJobs = new ArrayList<>();
        for(JobObject job : jobs) {
            clonedJobs.add(job.clone());
        }
        return clonedJobs;
    }
}
