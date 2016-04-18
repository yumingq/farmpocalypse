public class Wheat extends Plant {
    public static final String IMG_FILE = "wheat.jpg";
    public int init_x;
    public int init_y;
    public String state;
    public static final int GROWTH_TIME = 3;
    public static final int ROTTING_TIME = 12;
    public static final int COST = 2;
    public static final int PROFIT = 4;
    
    public Wheat(int pos_x, int pos_y, int court_width, int court_height) {
        super(court_width, court_height, pos_x, pos_y, "growing", GROWTH_TIME, ROTTING_TIME,
              COST, PROFIT, IMG_FILE);
        state = "growing";
        init_x = pos_x;
        init_y = pos_y;
    }
    
}