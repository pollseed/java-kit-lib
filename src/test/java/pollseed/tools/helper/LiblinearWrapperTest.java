package liblinearWrapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import printer.CommandPrinter;
import de.bwaldvogel.liblinear.Feature;
import de.bwaldvogel.liblinear.FeatureNode;
import de.bwaldvogel.liblinear.Parameter;
import de.bwaldvogel.liblinear.Problem;
import de.bwaldvogel.liblinear.SolverType;

public class LiblinearWrapperTest implements CommandPrinter {
    private static final int SOLVER_TYPE_COUNT;
    static {
        SOLVER_TYPE_COUNT = SolverType.values().length;
    }

    @Test
    public void test_predict() {
        final LiblinearWrapper lw = new LiblinearWrapper();
        final Problem problem = createProblem();
        final Feature[] instance = createInstance();

        int i = 0;
        for (final SolverType type : SolverType.values()) {
            lnLine(type);
            lw.predict(problem, createParameter(type), instance);
            i++;
        }
        assertEquals(SOLVER_TYPE_COUNT, i);
    }

    /**
     * インスタンスを生成する.
     * 
     * @return {@link Feature[]}
     */
    private Feature[] createInstance() {
        final Feature[] instance = { new FeatureNode(1, 4), new FeatureNode(2, 2) };
        return instance;
    }

    /**
     * パラメタを生成する.
     * 
     * @param solver
     *            {@link SolverType}
     * @return {@link Parameter}
     */
    private Parameter createParameter(final SolverType solver) {
        final double C = 1.0;
        final double eps = 0.01;

        final Parameter param = new Parameter(solver, C, eps);
        return param;
    }

    /**
     * プロブレムを生成する.
     * 
     * @return {@link Problem}
     */
    private Problem createProblem() {
        final Problem p = new Problem();
        p.l = 4;
        p.n = 6;
        p.y = new double[] { 1, 2, 1, 2, 3 };
        Feature[][] fs = new Feature[][] {
            { new FeatureNode(2, 0.1), new FeatureNode(3, 0.2), new FeatureNode(6, 1),
                new FeatureNode(1, 1) },
            { new FeatureNode(1, 0.4), new FeatureNode(6, 1), new FeatureNode(2, 1) },
            { new FeatureNode(2, 0.1), new FeatureNode(4, 1.4), new FeatureNode(5, 0.5),
                new FeatureNode(6, 1), new FeatureNode(1, 1) },
            { new FeatureNode(1, 0.1), new FeatureNode(2, -0.2), new FeatureNode(3, 0.1),
                new FeatureNode(4, 1.1), new FeatureNode(5, 0.1), new FeatureNode(6, 1) }
        };
        LiblinearWrapper.sorted(fs);
        p.x = fs;
        return p;
    }
}
