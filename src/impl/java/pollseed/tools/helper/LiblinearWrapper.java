package liblinearWrapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import de.bwaldvogel.liblinear.Feature;
import de.bwaldvogel.liblinear.Linear;
import de.bwaldvogel.liblinear.Model;
import de.bwaldvogel.liblinear.Parameter;
import de.bwaldvogel.liblinear.Problem;

public class LiblinearWrapper {
    /**
     * {@link Linear#predict(Model, Feature[])}
     * 
     * @param problem
     *            {@link Problem}
     * @param parameter
     *            {@link Parameter}
     * @param instance
     *            {@link Feature}[]
     */
    public void predict(final Problem problem, final Parameter parameter, final Feature[] instance) {
        Model model = Linear.train(problem, parameter);
        final File file = new File("model");
        try {
            model.save(file);
            model = Model.load(file);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        Linear.predict(model, instance);
    }

    /**
     * 与えられた2次元配列 {@code Feature} を {@link Feature#getIndex()} の昇順にそれぞれソート処理する.<br>
     * 
     * @param fs
     *            {@link Feature}[][]
     * @return {@link Feature}[][]
     */
    public static final void sorted(final Feature[][] fs) {
        for (final Feature[] f : fs) {
            Collections.sort(Arrays.asList(f), new ComparatorFeature());
        }
    }
}
