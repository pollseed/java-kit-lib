package liblinearWrapper;

import java.util.Comparator;

import de.bwaldvogel.liblinear.Feature;

public class ComparatorFeature implements Comparator<Feature> {
    @Override
    public int compare(Feature o1, Feature o2) {
        return o1.getIndex() - o2.getIndex();
    }
}
