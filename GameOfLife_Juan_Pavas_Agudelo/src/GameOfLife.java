//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class GameOfLife {
    private final int WIDTH;
    private final int HEIGHT;
    private int[][] GRID;

    public GameOfLife(int width, int height, String population) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.GRID = new int[height][width];

        String[] rows = population.split("#");
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].length(); j++) {
                GRID[i][j] = rows[i].charAt(j) - '0';
            }
        }
    }

    public void printGrid(int generation) {
        System.out.println("GENERATION " + generation + ":");
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(GRID[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int countLivingNeighbors(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue;
                int r = (row + i + HEIGHT) % HEIGHT;
                int c = (col + j + WIDTH) % WIDTH;
                count += GRID[r][c];
            }
        }
        return count;
    }

    public void updateGeneration() {
        int[][] newGrid = new int[HEIGHT][WIDTH];

        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                int livingNeighbors = countLivingNeighbors(row, col);

                if (GRID[row][col] == 1 && (livingNeighbors < 2 || livingNeighbors > 3)) {
                    newGrid[row][col] = 0;
                } else if (GRID[row][col] == 0 && livingNeighbors == 3) {
                    newGrid[row][col] = 1;
                } else {
                    newGrid[row][col] = GRID[row][col];
                }
            }
        }

        GRID = newGrid;
    }

    public static void main(String[] args) {
        String width = null;
        String height = null;
        String generations = null;
        String speed = null;
        String population = null;

        for (String arg : args) {
            String[] parts = arg.split("=");
            switch (parts[0]) {
                case "w":
                    width = parts[1];
                    break;
                case "h":
                    height = parts[1];
                    break;
                case "g":
                    generations = parts[1];
                    break;
                case "s":
                    speed = parts[1];
                    break;
                case "p":
                    population = parts[1].replaceAll("^\"|\"$", "");
                    break;
            }
        }

        System.out.println("width = [" + (isValidWidth(width) ? width : "Invalido") + "]");
        System.out.println("height = [" + (isValidHeight(height) ? height : "Invalido") + "]");
        System.out.println("generations = [" + (isValidGenerations(generations) ? generations : "No presente") + "]");
        System.out.println("speed = [" + (isValidSpeed(speed) ? speed : "No presente") + "]");
        System.out.println("population = [" + (population != null ? population : "No presente") + "]");

        if (isValidWidth(width) && isValidHeight(height) && isValidGenerations(generations) && isValidSpeed(speed) && isValidPopulation(population)) {
            GameOfLife game = new GameOfLife(Integer.parseInt(width), Integer.parseInt(height), population);
            for (int i = 0; i < Integer.parseInt(generations); i++) {
                game.printGrid(i);
                game.updateGeneration();
            }
        }
    }

    private static boolean isValidWidth(String width) {
        return "10".equals(width) || "20".equals(width) || "40".equals(width) || "80".equals(width);
    }

    private static boolean isValidHeight(String height) {
        return "10".equals(height) || "20".equals(height) || "40".equals(height) || "60".equals(height);
    }

    private static boolean isValidGenerations(String generations) {
        try {
            int g = Integer.parseInt(generations);
            return g > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isValidSpeed(String speed) {
        try {
            int s = Integer.parseInt(speed);
            return s >= 250 && s <= 1000;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isValidPopulation(String population) {
        return population.matches("^[01#]+$");
    }
}
