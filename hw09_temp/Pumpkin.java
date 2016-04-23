public class Pumpkin extends Plant {
    public static final String IMG_FILE = "pumpkin.jpg";
    public static final String GROWING_IMG = "pumpkin_green.jpg";
    public int init_x;
    public int init_y;
    public String state;
    public static final int GROWTH_TIME = 10;
    public static final int ROTTING_TIME = 6;
    public static final int COST = 30;
    public static final int PROFIT = 90;
    
    public Pumpkin(int pos_x, int pos_y, int court_width, int court_height) {
        super(court_width, court_height, pos_x, pos_y, "growing", GROWTH_TIME, ROTTING_TIME,
              COST, PROFIT, IMG_FILE, GROWING_IMG);
        state = "growing";
        init_x = pos_x;
        init_y = pos_y;
    }
    
}