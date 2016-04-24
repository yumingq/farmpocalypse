public class Scores {
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
        
    }