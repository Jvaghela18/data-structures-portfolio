package mario;

import edu.rutgers.cs112.Comparable112;

public class Player extends Comparable112<Player> {
  
    private String name; 
    private int stars; 
    private int coins;
 
    public Player(String name, int stars, int coins){
        this.name = name;
        this.stars = stars;
        this.coins = coins;
    }
   
    public String getName(){
        return this.name;
    }
    
    public int getStars(){
        return this.stars;
    } 
         
    public int getCoins(){
        return this.coins;
    } 

    @Override
    public int compareTo(Player arg0) { 
        // Ignores name, compares by stars then coins
        if (this.getStars() < arg0.getStars()) {
            return -1;
        }
        else if (this.getStars() > arg0.getStars()) {
            return 1;
        } else { 
            if (this.getCoins() < arg0.getCoins()) {
                return -1;
            } else if (this.getCoins() > arg0.getCoins()) {
                return 1;
            } else {
                return 0;
            }
        } 
    }

    @Override
    public boolean equals(Object arg0) {  
        return arg0 instanceof Player p && p.getName().equals(this.getName()) && p.getStars() == this.getStars() && p.getCoins() == this.getCoins();
    }
    @Override
    public String toString() { 
        return String.format("Player{name=%s, stars=%d, coins=%d}", this.getName(), this.getStars(), this.getCoins());
    }   

}
