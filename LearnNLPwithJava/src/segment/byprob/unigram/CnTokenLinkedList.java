package segment.byprob.unigram;


import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * 单词类的线性列表类
 *
 * @author luogang
 * @2010-3-18
 */
public class CnTokenLinkedList implements Iterable<CnToken> {
    public static class Node {
        public CnToken item;
        public Node next;

        public Node(CnToken data, Node next) {
            item = data;
            this.next = next;
        }
    }

    private Node head;

    public CnTokenLinkedList() {
        head = null;
    }

    public void put(CnToken item) {
        head = new Node(item, head);
    }

    public Node getHead() {
        return head;
    }

    public int size() {
        int count = 0;

        Node t = head;
        while (t != null) {
            count++;
            t = t.next;
        }

        return count;
    }

    public Iterator<CnToken> iterator() {
        return new LinkIterator(head);
    }

    private class LinkIterator implements Iterator<CnToken> {
        Node itr;

        public LinkIterator(Node begin) {
            //System.out.println("LinkIterator");
            itr = begin;
        }

        public boolean hasNext() {
            return itr != null;
        }

        public CnToken next() {
            if (itr == null) {
                throw new NoSuchElementException();
            }
            //System.out.println("LinkIterator next");
            CnToken cur = itr.item;  //当前位置存储的值
            itr = itr.next;
            return cur;
        }

        public void remove() {
            // itr.remove();
        }
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        Node currentNode = head;

        while (currentNode != null) {
            buf.append(currentNode.item.toString());
            buf.append('\t');
            currentNode = currentNode.next;
        }

        return buf.toString();
    }

    public static void main(String[] args) {
        testTokenList();
        testItr();
    }

    public static void testTokenList(){
        CnToken t1 = new CnToken(2, 3, 2.0, "见");
        CnToken t2 = new CnToken(1, 3, 3.0, "意见");
        CnTokenLinkedList tokenList = new CnTokenLinkedList();
        tokenList.put(t1);
        tokenList.put(t2);
        for(CnToken t:tokenList){
            System.out.println(t);
        }
    }

    public static void testItr(){
        CnToken t1 = new CnToken(2, 3, 2.0, "见");
        CnToken t2 = new CnToken(1, 3, 3.0, "意见");
        CnTokenLinkedList tokenList = new CnTokenLinkedList();
        tokenList.put(t1);
        tokenList.put(t2);

        Iterator<CnToken> itr = tokenList.iterator();
        System.out.println(itr.hasNext());
        System.out.println(itr.next());
        System.out.println(itr.hasNext());
        System.out.println(itr.next());
    }



}