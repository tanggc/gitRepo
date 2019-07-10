package com.tgc.oa;

public class SynchronzedTest implements Runnable {

    static SynchronzedTest st = new SynchronzedTest();
    static int i = 0;
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(st);
        Thread thread2 = new Thread(st);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("i的值："+i);
    }


    @Override
    public void run() {
        synchronized (this){
            System.out.println("当前线程："+Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("运行结束："+Thread.currentThread().getName());
        }


    }
}
