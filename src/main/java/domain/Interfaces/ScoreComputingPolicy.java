package domain.Interfaces;

public  interface ScoreComputingPolicy {

    String getName();

    int getWinPoints();
    int getTiePoints();
    int getLosePoints();
}
