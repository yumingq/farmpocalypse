/*
 *This class is for high score tracking objects- mostly so I can customize the sorting. 
 */

public class Scores implements Comparable {
        private String name;
        private String score;

        public Scores(String name, String score) {
            this.name = name;
            this.score = score;
        }

        public String getScore() {
            return score;
        }

        public String getName() {
            return name;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (!(getClass() == obj.getClass())) {
                return false;
            }     
            Scores that = (Scores) obj;
            if (name != that.name) {
                return false;
            } 
            if (score != that.score) {
                return false;
            }
            return true;
        }

        @Override
        public int compareTo(Object arg0) {
            Scores that = (Scores) arg0;
            int thatScore = Integer.parseInt(that.score);
            int thisScore = Integer.parseInt(score);
            if (thatScore > thisScore) {
                return 1;
            } else if (thatScore < thisScore) {
                return -1;
            } else {
                return 0;
            }
        }
        
    }