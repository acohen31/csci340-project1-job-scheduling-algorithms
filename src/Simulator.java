import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Simulator {
    public static void main(String[] args) {
        List<JobObject> jobs = JobGenerator.generateJobs(25);
        SJF sjf = new SJF();
        FIFO fifo = new FIFO();
        SRT srt = new SRT();
        Priority priority = new Priority();
        RoundRobin roundRobin = new RoundRobin();
        System.out.println("\n------------------------ RUNNING FIFO -----------------------\n");
        fifo.simulate(JobGenerator.cloneJobs(jobs));
        System.out.println("\n------------------------ RUNNING SJF ------------------------\n");
        sjf.simulate(JobGenerator.cloneJobs(jobs));
        System.out.println("\n------------------------ RUNNING SRT ------------------------\n");
        srt.simulate(JobGenerator.cloneJobs(jobs));
        System.out.println("\n------------------------ RUNNING Priority -------------------\n");
        priority.simulate(JobGenerator.cloneJobs(jobs));
        System.out.println("\n------------------------ RUNNING RoundRobin -----------------\n");
        roundRobin.simulate(JobGenerator.cloneJobs(jobs));
    }
}
