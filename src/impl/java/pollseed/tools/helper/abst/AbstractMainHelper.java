package pollseed.tools.helper.abst;

import pollseed.tools.helper.interfaces.CommandPrinter;
import pollseed.tools.helper.interfaces.Runner;

/**
 * {@code main} メソッドのためのヘルパークラス
 *
 */
public abstract class AbstractMainHelper implements Runner, CommandPrinter {

    /**
     * 主処理を書きます.
     */
    protected abstract void run();

}
