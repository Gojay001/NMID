import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

public class MyThreadPoolTest {
	public static void main(String[] args) throws InterruptedException {
        MyThreadPool excutor = new MyThreadPool(5);
        for (int i = 0; i < 20; i++) {
        	Task task = new Task(i);
            excutor.execute(task);
            System.out.println("�̳߳����߳���Ŀ��" + excutor.coreSize
            		+ "\t�����еȴ�ִ�е�������Ŀ��" + excutor.queue.size());
        }
       excutor.shutdown();
    }
}

class MyThreadPool implements Executor {

   private volatile boolean RUNNING = true;
   private boolean shutdown = false;

    //�������񶼷Ŷ����У��ù����߳�������
    BlockingQueue<Runnable> queue = null;
    private final HashSet<Worker> workers = new HashSet<Worker>();
    private final List<Thread> threadList = new ArrayList<Thread>(5);
    //�����߳���
    int poolSize = 0;
    //�����߳����������˶��ٸ������̣߳�
    int coreSize = 0;

    public MyThreadPool(int poolSize){
        this.poolSize = poolSize;
        queue = new LinkedBlockingQueue<Runnable>(poolSize);
    }

    @Override
    public void execute(Runnable command) {
        if (command == null) {
        	throw new NullPointerException();
        }
        if(coreSize < poolSize) {
            addThread(command);
        } else {
            try {
                queue.put(command);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addThread(Runnable command){
        coreSize++;
        Worker worker = new Worker(command);
        workers.add(worker);
        Thread t = new Thread(worker);
        threadList.add(t);
        try {
            t.start();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void shutdown() {
        RUNNING = false;
        if(!workers.isEmpty()){
            for (Worker worker : workers){
                worker.interruptIfIdle();
            }
        }
        shutdown = true;
        Thread.currentThread().interrupt();
    }
   
    /**
     * �����߳�����Ϊ�ڲ���
     *��дrun()������ȡ������ִ���߳�
     */
    class  Worker implements Runnable{

        public Worker(Runnable runnable){
            queue.offer(runnable);
        }

        @Override
        public void run() {
            while (true && RUNNING){
                if(shutdown == true){
                    Thread.interrupted();
                }
                Task task = null;
                try {
                    task = getTask();
                    task.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public Task getTask() throws InterruptedException {
            return (Task)queue.take();
        }

        public void interruptIfIdle() {
            for (Thread thread :threadList) {
                thread.interrupt();
            }
        }
    }
}

/**
 * �����ࣺ��ӡ��Ϣ
 *��дrun()����
 */
class Task implements Runnable {
	private int taskNum;
	
	public Task(int taskNum) {
		this.taskNum = taskNum;
	}

	@Override
	public void run() {
		try {
			System.out.println("��ʼִ�У�task" + taskNum);
			Thread.sleep(300);
			System.out.println("task" + taskNum + "ִ�����");
		} catch (InterruptedException e) {
			System.out.println(Thread.currentThread().getName() + "�Ѿ��ж�");
		}	
	}
}