
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class MemCachedClientWrapper implements Serializable {
    private static final long serialVersionUID = 1L;

    private MemCachedClientWrapper() {
    }

    private static final MemCachedClient MCC = new MemCachedClient();

    static {
        MCC.setTransCoder(new ObjectTransCoder());

        final String[] servers =
        {
            "localhost:11211"
                // , 192.168.1.100:1620
                // , 192.168.1.100:1621
                // , 192.168.1.100:1622
                // , 192.168.1.100:1623
        };
        final Integer[] weights = {
            3
                // , 1
                // , 1
                // , 1
                // , 1
        };

        final SockIOPool pool = SockIOPool.getInstance();
        pool.setServers(servers);
        pool.setWeights(weights);
        pool.setNagle(false);
        pool.setSocketTO(3000);
        pool.setSocketConnectTO(0);
        pool.initialize();
    }

    public static class MemCached<T> implements Serializable {
        private static final String SEPARATOR = ":";
        private static final long serialVersionUID = 1L;

        protected void set(final MemCachedKeys key, final T obj) {
            MCC.set(getKey(key, null), obj);
        }

        protected void set(final MemCachedKeys key, final Integer[] id, final T obj) {
            MCC.set(getKey(key, StringUtils.join(id, SEPARATOR)), obj);
        }

        protected T get(final MemCachedKeys key) {
            return getBean(MCC.get(getKey(key, null)));
        }

        protected T get(final MemCachedKeys key, final Integer[] id) {
            return getBean(MCC.get(getKey(key, StringUtils.join(id, SEPARATOR))));
        }

        protected boolean keyExists(final MemCachedKeys key) {
            return MCC.keyExists(getKey(key, null));
        }

        protected boolean keyExists(final MemCachedKeys key, final Integer[] id) {
            return MCC.keyExists(getKey(key, StringUtils.join(id, SEPARATOR)));
        }

        protected void flush(final MemCachedKeys key) {
            MCC.flushAll(new String[] { getKey(key, null) });
        }

        protected void flush(final MemCachedKeys key, final Integer[] id) {
            MCC.flushAll(new String[] { getKey(key, StringUtils.join(id, SEPARATOR)) });
        }

        private T getBean(final Object object) {
            try {
                @SuppressWarnings("unchecked")
                final T bean = (T) object;
                return bean;
            } catch (final ClassCastException e) {
                return null;
            }
        }

        private String getKey(final MemCachedKeys key, final String ids) {
            return key.toString() + (StringUtils.isBlank(ids) ? "" : ids);
        }
    }

    /* test code. */
    public static void main(final String[] args) {
        final MemCached<Map<Integer, List<String>>> cached =
                new MemCached<Map<Integer, List<String>>>();
        final Map<Integer, List<String>> map = new HashMap<Integer, List<String>>() {
            {
                put(Integer.MIN_VALUE, null);
                put(1, Arrays.asList("hoge", "fuga", "piyo"));
                put(2, Arrays.asList("hogehoge", "fugafuga", "piyopiyo"));
                put(3, Arrays.asList("test"));
                put(Integer.MAX_VALUE, null);
            }
        };
        cached.set(MemCachedKeys.MAIN_PAGE_LIST, map);
        // cached.flush(MemCachedKey.MAIN_PAGE_LIST);
        System.out.println(cached.get(MemCachedKeys.MAIN_PAGE_LIST));
    }
}
