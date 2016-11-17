package edu.matc.loops.enitity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Tim on 11/10/2016.
 */
@Entity
@Table(name = "loop_info")
public class LoopInfoObj {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id")
    private int id;
    @Column(name = "x_size")
    private int xSize;
    @Column(name = "y_size")
    private int ySize;
    @Column(name = "num_loops")
    private int numLoops;
    @Column(name = "fail_count")
    private int failCount;
    @Column(name = "allow_double_back")
    private boolean allowDoubleBack;
    @Column(name = "allow_same_coordinates")
    private boolean allowSameCoordinates;
    @Column(name = "allow_through_start")
    private boolean allowThroughStart;
    @Column(name = "variable_leg_size")
    private boolean variableLegSize;

    public LoopInfoObj(int id, int xSize, int ySize, int numLoops, int failCount, boolean allowDoubleBack, boolean allowSameCoordinates, boolean allowThroughStart, boolean variableLegSize) {
        this.id = id;
        this.xSize = xSize;
        this.ySize = ySize;
        this.numLoops = numLoops;
        this.failCount = failCount;
        this.allowDoubleBack = allowDoubleBack;
        this.allowSameCoordinates = allowSameCoordinates;
        this.allowThroughStart = allowThroughStart;
        this.variableLegSize = variableLegSize;
    }

    public LoopInfoObj() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getxSize() {
        return xSize;
    }

    public void setxSize(int xSize) {
        this.xSize = xSize;
    }

    public int getySize() {
        return ySize;
    }

    public void setySize(int ySize) {
        this.ySize = ySize;
    }

    public int getNumLoops() {
        return numLoops;
    }

    public void setNumLoops(int numLoops) {
        this.numLoops = numLoops;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public boolean isAllowDoubleBack() {
        return allowDoubleBack;
    }

    public void setAllowDoubleBack(boolean allowDoubleBack) {
        this.allowDoubleBack = allowDoubleBack;
    }

    public boolean isAllowSameCoordinates() {
        return allowSameCoordinates;
    }

    public void setAllowSameCoordinates(boolean allowSameCoordinates) {
        this.allowSameCoordinates = allowSameCoordinates;
    }

    public boolean isAllowThroughStart() {
        return allowThroughStart;
    }

    public void setAllowThroughStart(boolean allowThroughStart) {
        this.allowThroughStart = allowThroughStart;
    }

    public boolean isVariableLegSize() {
        return variableLegSize;
    }

    public void setVariableLegSize(boolean variableLegSize) {
        this.variableLegSize = variableLegSize;
    }

    @Override
    public String toString() {
        return "LoopInfoObj{" +
                "xSize=" + xSize +
                ", ySize=" + ySize +
                ", numLoops=" + numLoops +
                ", failCount=" + failCount +
                ", allowDoubleBack=" + allowDoubleBack +
                ", allowSameCoordinates=" + allowSameCoordinates +
                ", variableLegSize=" + variableLegSize +
                '}';
    }
}
