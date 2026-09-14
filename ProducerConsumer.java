import java.util.LinkedList;
import java.util.Queue;
class Sharedbuffer{
    private Queue<Integer> buffer = new LinkedList<Integer>();
    private final int capacity = 5;
    synchronized public void produce(int item) throws InterruptedException{
        while(buffer.size() == capacity){
            wait();
        }
        buffer.add(item);
        System.out.println("Produce item : "+item);
        notifyAll(); 
    }
    synchronized public void consume() throws InterruptedException{
        while(buffer.size() == 0){
            wait();
        }
        int item = buffer.remove();
        System.out.println("Consume item : "+item);
        notifyAll();
    }
}
class Producer extends Thread{
    Sharedbuffer buffer;

    Producer(Sharedbuffer buffer){
        this.buffer=buffer;
    }
    public void run(){
        for(int i=1;i<=10;i++){
            try {
                buffer.produce(i);
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    }
}
class Consumer extends Thread{
    Sharedbuffer buffer;

    Consumer(Sharedbuffer buffer){
        this.buffer=buffer;
    }
    public void run(){
        for(int i=1;i<=10;i++){
            try {
                buffer.consume();
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    }
}
public class ProducerConsumer {
    public static void main(String args[]){
        Sharedbuffer buffer = new Sharedbuffer();
        Producer p =new Producer(buffer);
        Consumer c = new Consumer(buffer);
        p.start();
        c.start();
    }
}
