package mario;

import java.util.Arrays;

import edu.rutgers.cs112.Comparable112;

public class Racer extends Comparable112<Racer> {
    
    private String name; 
    private int [] lapTime;
     
    public Racer(String name, int [] lapTime){
        this.name = name;
        this.lapTime = lapTime;
    }
     
    public String getName(){
        return this.name;
    } 
      
    public int [] getLapTime(){
        return this.lapTime;
    }

    public double getAverage() {
        double sum = 0;
        for (int time : lapTime) {
            sum += time;
        }
        return sum / lapTime.length;
    }

    @Override
    public int compareTo(Racer arg0) {
        int[] time = this.getLapTime();
        int[] opp = arg0.getLapTime();
        return Double.compare(((double)Arrays.stream(time).sum()/(3.0)), ((double)Arrays.stream(opp).sum()/3));
    }

    @Override
    public boolean equals(Object arg0) {
        return arg0 instanceof Racer r && r.getName().equals(this.getName()) && Arrays.equals(r.getLapTime(), this.getLapTime());
    }

    @Override
    public String toString() {
        return "Racer{" +
                "name='" + name + '\'' +
                ", lapTime=" + Arrays.toString(lapTime) +
                '}';
    }
}