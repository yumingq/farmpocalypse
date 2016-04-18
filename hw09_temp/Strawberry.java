public class Strawberry extends Plant {
    public static final String IMG_FILE = "strawberry.jpg";
    public int init_x;
    public int init_y;
    public String state;
    public static final int GROWTH_TIME = 6;
    public static final int ROTTING_TIME = 8;
    public static final int COST = 10;
    public static final int PROFIT = 20;
    
    public Strawberry(int pos_x, int pos_y, int court_width, int court_height) {
        super(court_width, court_height, pos_x, pos_y, "growing", GROWTH_TIME, ROTTING_TIME,
              COST, PROFIT, IMG_FILE);
        state = "growing";
        init_x = pos_x;
        init_y = pos_y;
    }
    
}