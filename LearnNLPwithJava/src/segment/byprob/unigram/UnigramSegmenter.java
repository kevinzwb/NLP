package segment.byprob.unigram;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;


public class UnigramSegmenter {
    String[] bestPrevWords; // 最佳前驱词
    double[] prob;

    public AdjList getLattice(String sentence){
        TernarySearchTrie dict = TernarySearchTrie.getInstance(); // 得到词典
        int sLen;
        sLen = sentence.length();// 字符串长度
        AdjList g = new AdjList(sLen + 1);// 存储所有被切分的可能的词
        int j;
        ArrayList<WordEntry> sucWords = new ArrayList<WordEntry>();

        //形成切分词图
        for (int i = 0; i < sLen;) {
            boolean match = dict.matchAll(sentence, i, sucWords);// 到词典中查询

            if (match)// 已经匹配上
            {
                for (WordEntry word : sucWords) {
                    // System.out.println(word);
                    j = i + word.term.length();
                    double logProb = Math.log(word.freq) - Math.log(dict.n);
                    //把词放入词图
                    g.addEdge(new CnToken(i, j, logProb, word.term));
                }
                i++;
            } else {
                j = i + 1;
                double logProb = Math.log(1) - Math.log(dict.n);
                //把单字放入词图
                g.addEdge(new CnToken(i, j, logProb, sentence.substring(i, j)));
                i = j;
            }
        }
        return g;
    }

    // 计算节点i的最佳前驱节点
    void getBestPrev(AdjList g, int i) {
        double maxProb = Double.NEGATIVE_INFINITY;
        String bestPrevWord = null; // 候选最佳前驱词

        Iterator<CnToken> it = g.getPrev(i);// 得到前驱词集合，从中挑选最佳前趋词
        while (it.hasNext()) {
            CnToken itr = it.next();
            double nodeProb = prob[itr.start] + itr.logProb;// 候选节点概率
            if (nodeProb > maxProb)// 概率最大的算作最佳前趋
            {
                bestPrevWord = itr.termText;
                maxProb = nodeProb;
            }
        }
        prob[i] = maxProb;// 节点概率
        bestPrevWords[i] = bestPrevWord; // 最佳前驱词
        // System.out.println("node "+i + " best prev is"+ maxID);
    }

    // 计算出最大概率的数组
    public Deque<String> maxProb(AdjList g) {
        bestPrevWords = new String[g.verticesNum];// 最佳前驱词数组
        prob = new double[g.verticesNum]; // 节点概率
        prob[0] = 0;// 节点0的初始概率是1,取log后是0

        // 按节点求最佳前驱
        for (int index = 1; index < g.verticesNum; index++) {
            // 求出最大前驱
            getBestPrev(g, index);
        }

        return bestPath(g);
    }

    // 根据最佳前驱节点数组回溯求解词序列
    public Deque<String> bestPath(AdjList g) {
        Deque<String> path = new ArrayDeque<String>(); // 最佳词序列
        int i = g.verticesNum - 1;
        while (bestPrevWords[i] != null) {
            System.out.println("节点"+i+"的最佳前驱词:"+bestPrevWords[i]);
            path.push(bestPrevWords[i]); // 压栈
            i = i - bestPrevWords[i].length();
        }

        return path;
    }

    public Deque<String> split(String sentence) {
        TernarySearchTrie dict = TernarySearchTrie.getInstance(); // 得到词典
        int sLen;
        sLen = sentence.length();// 字符串长度
        AdjList g = new AdjList(sLen + 1);// 存储所有被切分的可能的词
        int j;

        ArrayList<WordEntry> sucWords = new ArrayList<WordEntry>();

        //形成切分词图
        for (int i = 0; i < sLen;) {
            boolean match = dict.matchAll(sentence, i, sucWords);// 到词典中查询

            if (match)// 已经匹配上
            {
                for (WordEntry word : sucWords) {
                    // System.out.println(word);
                    j = i + word.term.length();
                    double logProb = Math.log(word.freq) - Math.log(dict.n);
                    //把词放入词图
                    g.addEdge(new CnToken(i, j, logProb, word.term));
                }
                i++;
            } else {
                j = i + 1;
                double logProb = Math.log(1) - Math.log(dict.n);
                //把单字放入词图
                g.addEdge(new CnToken(i, j, logProb, sentence.substring(i, j)));
                i = j;
            }
        }
        //System.out.println(g.toString());
        return maxProb(g);
    }

    public static void main(String[] args) throws IOException {
        testSegmentToy();
//        testSegmentDemo();

    }

    public static void testSegmentDemo() {
        String sentence = "有意见分歧";
        //"中国成立了";// "大学生活动中心";
        UnigramSegmenter seg = new UnigramSegmenter();
        Deque<String> path = seg.split(sentence);

        //输出结果
        System.out.println("切分结果 ");
        for (String word : path) {
            System.out.print(word+" / ");
        }
    }

    public static void testSegmentToy() {
        String sentence = "有意见分歧"; //待切分句子

        int[] prevNode = new int[6]; // 最佳前驱节点
        prevNode[1] = 0;
        prevNode[2] = 0;
        prevNode[3] = 1;
        prevNode[4] = 3;
        prevNode[5] = 3;

        ArrayDeque<Integer> path = new ArrayDeque<Integer>();
        // 通过回溯发现最佳切分路径
        for (int i = 5; i > 0; i = prevNode[i]) { // 从右向左找最佳前驱节点
            path.addFirst(i);
        }

        //输出结果
        int start = 0;
        for (Integer end : path) {
            System.out.print(sentence.substring(start, end) + "/ ");
            start = end;
        }
        System.out.print("\n");
    }

}
