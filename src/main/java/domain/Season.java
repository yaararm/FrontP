package domain;

import java.util.List;

/**
 * created by Tomer
 */
public class Season {
    //Fields
    private String name;
    private int year;
    //Connections
    private List<League> leagues;
    private ScoreComputingPolicy scorePolicy;
    private AssignPolicy  assignPolicy;

    private int w=0;
}
