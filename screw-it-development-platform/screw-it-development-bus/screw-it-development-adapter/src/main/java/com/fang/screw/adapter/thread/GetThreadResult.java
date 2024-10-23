package com.fang.screw.adapter.thread;

import java.util.concurrent.*;

/**
 * @FileName GetThreadResult
 * @Description
 * @Author yaoHui
 * @date 2024-10-22
 **/
public class GetThreadResult {

    public static void futureTask() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Callable<Integer>[] tasks = new Callable[10];
        for(int i = 0;i<tasks.length;i++){
            final int index = i;

            tasks[i] = new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    TimeUnit.SECONDS.sleep(index + 1);
                    return (index + 1) * 100;
                }
            };

        }

        FutureTask[] futureTasks = new FutureTask[tasks.length];
        for(int i = 0;i< tasks.length;i++){
            futureTasks[i] = new FutureTask(tasks[i]);
            executorService.submit(futureTasks[i]);
        }

        for(int i = 0;i< tasks.length;i++){
            System.out.println("Result of task" + (i + 1) + ": " + futureTasks[i].get());
        }

        executorService.shutdown();
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        futureTask();

//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        Callable<String> task = new Callable<String>(){
//            @Override
//            public String call() throws Exception {
//                return "Hello World" + Thread.currentThread().getName();
//            }
//        };
//
//        Future[] futures = new Future[10];
//        for (int i = 0;i<10;i++){
//            futures[i] = executorService.submit(task);
//            System.out.println(futures[i].get());
//        }
//
//        for(int i =0;i<10;i++){
////            System.out.println(futures[i].get());
//        }
//
//        executorService.shutdown();

    }

}



