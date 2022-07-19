package helperMethods;

public class Constants {
    public static class Projectiles {
        public static final int ARROW = 0;
        public static final int CHAINS = 1;
        public static final int BOMB = 2;

        public static float GetSpeed(int projectileType) {
            switch(projectileType) {
                case ARROW:
                    return 8f;
                case BOMB:
                    return 4f;
                case CHAINS:
                    return 6f;
            }
            return 0;
        }
    }

    public static class Towers {
        public static final int CANNON = 0;
        public static final int ARCHER = 1;
        public static final int WIZARD = 2;

        public static String GetName(int towerType) {
            switch(towerType) {
                case CANNON:
                    return "Cannon";
                case ARCHER:
                    return "Archer";
                case WIZARD:
                    return "Wizard";
            }
            return "";
        }

        public static int GetStartDamage(int towerType) {
            switch(towerType) {
                case CANNON:
                    return 10;
                case ARCHER:
                    return 1;
                case WIZARD:
                    return 2;
            }
            return 0;
        }

        public static float GetDefaultRange(int towerType) {
            switch(towerType) {
                case CANNON:
                    return 100;
                case ARCHER:
                    return 50;
                case WIZARD:
                    return 80;
            }
            return 0;
        }

        public static float GetDefaultCD(int towerType) {
            switch(towerType) {
                case CANNON:
                    return 120;
                case ARCHER:
                    return 25;
                case WIZARD:
                    return 40;
            }
            return 0;
        }
    }

    public static class Direction {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class Enemies {
        public static final int ORC = 0;
        public static final int BAT = 1;
        public static final int KNIGHT = 2;
        public static final int WOLF = 3;

        public static float GetSpeed(int enemyType) {
            switch(enemyType) {
                case ORC:
                    return 0.5f;
                case BAT:
                    return 0.6f;
                case KNIGHT:
                    return 0.3f;
                case WOLF:
                    return 0.75f;
            }
            return 0;
        }

        public static int GetStartHealth(int enemyType) {
            switch(enemyType) {
                case ORC:
                    return 100;
                case BAT:
                    return 60;
                case KNIGHT:
                    return 250;
                case WOLF:
                    return 85;
            }
            return 0;
        }
    }

    public static class Tiles {
        public static final int WATER_TILE = 0;
        public static final int GRASS_TILE = 1;
        public static final int ROAD_TILE = 2;
    }
}
