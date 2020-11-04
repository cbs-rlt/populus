package edu.umn.ecology.populus.plot.plotshapes;

/**
 * Fletching, like the feathers on an arrow:
 * _____
 * \    \
 * /____/
 *
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002, 2015</p>
 * <p>Company: University of Minnesota</p>
 *
 * @author Amos Anderson
 * @version 5.2
 */
public class Fletching extends DirectionalPlotTerminus {

    public Fletching(double dy, double dx) {
        super(false);
        this.setDeltas(dy, dx);
    }

    @Override
    protected float[] getArray(int size) {
        float[] pc = new float[14];
        float l = 3.0f;

        pc[0] = 0.0f;
        pc[1] = 0.0f;
        pc[2] = -1.0f * (float) size;
        pc[3] = (float) size;
        pc[4] = -1.0f * l * (float) size;
        pc[5] = (float) size;
        pc[6] = -2.0f / 3.0f * l * (float) size;
        pc[7] = 0.0f;
        pc[8] = -1.0f * l * (float) size;
        pc[9] = -1.0f * (float) size;
        pc[10] = -1.0f * (float) size;
        pc[11] = -1.0f * (float) size;
        pc[12] = 0.0f;
        pc[13] = 0.0f;
        return pc;
    }

}
