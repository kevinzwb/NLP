package segment.bymatch.forward;

public interface ChineseSpliter {

    /**
     * 对给定文本进行中文分词
     * @param text 给定的文本
     * @return 分词完毕的词数组
     */
    public String[] split(String text);
}
