package ru.ifmo.rain.krivopaltsev.concurrent;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.Function;

public class ParallelMapperImpl implements ParallelMapper {
    private final Queue<Runnable> tasks = new ArrayDeque<>();
    private final List<Thread> workers = new ArrayList<>();

    private class Result<T> {
        private List<T> res;
        private int count = 0;

        Result(int size) {
            res = new ArrayList<>(Collections.nCopies(size, null));
        }

        void addRes(int pos, T data) {
            res.set(pos, data);
            synchronized (this) {
                count++;
                if (count == res.size()) {
                    notify();
                }
            }
        }

        synchronized List<T> getRes() throws InterruptedException {
            while (count < res.size()) {
                wait();
            }
            return res;
        }
    }

    private void threadTask() throws InterruptedException {
        Runnable task;
        synchronized (tasks) {
            while (tasks.isEmpty()) {
                tasks.wait();
            }
            task = tasks.poll();
            tasks.notify();
        }
        task.run();
    }

    private void createNewThread() {
        Thread thread = new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    threadTask();
                }
            } catch (InterruptedException ignored) {
            } finally {
                Thread.currentThread().interrupt();
            }
        });
        workers.add(thread);
        thread.start();
    }

    /**
     * Create new threads
     *
     * @param threads count new threads
     */
    public ParallelMapperImpl(int threads) throws InterruptedException {
        if (threads <= 0) {
            throw new InterruptedException("Invalid number of threads");
        }
        for (int i = 0; i < threads; i++) {
            createNewThread();
        }
    }

    private void addTask(Runnable task) {
        synchronized (tasks) {
            tasks.add(task);
            tasks.notify();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T, R> List<R> map(Function<? super T, ? extends R> function, List<? extends T> list) throws InterruptedException {
        Result<R> res = new Result<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            final int ind = i;
            addTask(() -> res.addRes(ind, function.apply(list.get(ind))));
        }
        return res.getRes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        workers.forEach(Thread::interrupt);
        workers.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException ignored) {
            }
        });
    }
}
