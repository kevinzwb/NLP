package segment.byprob.unigram;


/**
 *  邻接链表表示的切分词图
 *  逆向邻接链表
 * @author luogang
 * @2010-3-18
 */

import java.util.Iterator;

public class AdjList {
    private CnTokenLinkedList list[];// AdjList的图的结构
    public int verticesNum;

    /**
     * Constructor: Allocates right amount of space
     */
    public AdjList(int verticesNum) {
        this.verticesNum = verticesNum;
        list = new CnTokenLinkedList[verticesNum];

        // initialize all of the linked lists in the array
        for (int index = 0; index < verticesNum; index++) {
            list[index] = new CnTokenLinkedList();
        }
    }

    /**
     * add an edge to the graph
     */
    public void addEdge(CnToken newEdge) {
        list[newEdge.end].put(newEdge);
    }

    /**
     * Returns an iterator that contains the Edges leading to adjacent nodes
     */
    public Iterator<CnToken> getPrev(int vertex) {
        CnTokenLinkedList ll = list[vertex];
        if (ll == null)
            return null;
        return ll.iterator();
    }

    /**
     * Feel free to make this prettier, if you'd like
     */
    public String toString() {
        StringBuilder temp = new StringBuilder();

        for (int index = 0; index < verticesNum; index++) {
            if (list[index] == null) {
                continue;
            }
            temp.append("node:");
            temp.append(index);
            temp.append(": ");
            //temp.append(list[index].toString());
            Iterator<CnToken> it = list[index].iterator();
            if (it == null) {
                continue;
            }

            while (it.hasNext()) {
                CnToken itr = it.next();

                temp.append(itr.toString());
            }
            temp.append("\n");
        }

        return temp.toString();
    }

    public static void main(String[] args) {
        String sentence="有意见分歧";
        int len = sentence.length();//字符串长度
        AdjList g = new AdjList(len+1);//存储所有被切分的可能的词

        //第一个节点结尾的边
        g.addEdge(new CnToken(0, 1, 1.0, "有"));

        //第二个节点结尾的边
        g.addEdge(new CnToken(0, 2, 1.0, "有意"));
        g.addEdge(new CnToken(1, 2, 1.0, "意"));

        //第三个节点结尾的边
        g.addEdge(new CnToken(1, 3, 1.0, "意见"));
        g.addEdge(new CnToken(2, 3, 1.0, "见"));

        //第四个节点结尾的边
        g.addEdge(new CnToken(3, 4, 1.0, "分"));

        //第五个节点结尾的边
        g.addEdge(new CnToken(3, 5, 1.0, "分岐"));

        System.out.println(g.toString());
    }


}