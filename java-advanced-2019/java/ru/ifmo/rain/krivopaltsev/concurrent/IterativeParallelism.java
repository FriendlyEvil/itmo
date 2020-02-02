package ru.ifmo.rain.krivopaltsev.concurrent;

import info.kgeorgiy.java.advanced.concurrent.ListIP;
import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IterativeParallelism implements ListIP {
    private ParallelMapper mapper;

    /**
     *  Constructor from ParallelMapper object.
     *
     * @param mapper instance ParallelMapper
     */
    public IterativeParallelism(ParallelMapper mapper) {
        this.mapper = mapper;
    }

    /**
     *  Empty constructor. It create new thread from new task.
     *
     */
    public IterativeParallelism() {
        mapper = null;
    }


    private <T, R> R baseTask(int threads, List<? extends T> values,
                              Function<? super Stream<? extends T>, ? extends R> task,
                              Function<? super Stream<? extends R>, ? extends R> mapAns)
            throws InterruptedException {
        if (threads <= 0) {
            throw new InterruptedException("Invalid number of threads");
        }
        threads = Math.max(1, Math.min(threads, values.size()));
        List<Stream<? extends T>> smallTask = new ArrayList<>();
        int size = values.size() / threads;

        int rest = values.size() % threads;
        for (int i = 0, r = 0; i < threads; i++) {
            final int l = r;
            r = l + size + (rest-- > 0 ? 1 : 0);
            smallTask.add(values.subList(l, r).stream());
        }

        List<R> res;
        if (mapper == null) {
            final List<Thread> workers = new ArrayList<>();
            res = new ArrayList<>(Collections.nCopies(threads, null));
            for (int i = 0; i < threads; i++) {
                int pos = i;
                Thread thread = new Thread(() -> res.set(pos, task.apply(smallTask.get(pos))));
                workers.add(thread);
                thread.start();
            }
            for (Thread thread : workers) {
                thread.join();
            }
        } else {
            res = mapper.map(task, smallTask);
        }
        return mapAns.apply(res.stream());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String join(int threads, List<?> values) throws InterruptedException {
        return baseTask(threads, values,
                stream -> stream.map(Object::toString).collect(Collectors.joining()),
                stream -> stream.collect(Collectors.joining())
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> List<T> filter(int threads, List<? extends T> values, Predicate<? super T> predicate) throws InterruptedException {
        return baseTask(threads, values,
                stream -> stream.filter(predicate).collect(Collectors.toList()),
                stream -> stream.flatMap(Collection::stream).collect(Collectors.toList())
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T, U> List<U> map(int threads, List<? extends T> values, Function<? super T, ? extends U> f) throws InterruptedException {
        return baseTask(threads, values,
                stream -> stream.map(f).collect(Collectors.toList()),
                stream -> stream.flatMap(Collection::stream).collect(Collectors.toList())
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T maximum(int threads, List<? extends T> values, Comparator<? super T> comparator) throws InterruptedException {
        if (values.isEmpty()) {
            throw new IllegalArgumentException("Unable to handle empty list");
        }
        final Function<Stream<? extends T>, ? extends T> streamMax = stream -> stream.max(comparator).get();
        return baseTask(threads, values, streamMax, streamMax);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T minimum(int threads, List<? extends T> values, Comparator<? super T> comparator) throws InterruptedException {
        return maximum(threads, values, Collections.reverseOrder(comparator));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public <T> boolean all(int threads, List<? extends T> values, Predicate<? super T> predicate) throws InterruptedException {
        return baseTask(threads, values,
                stream -> stream.allMatch(predicate),
                stream -> stream.allMatch(Boolean::booleanValue)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> boolean any(int threads, List<? extends T> values, Predicate<? super T> predicate) throws InterruptedException {
        return !all(threads, values, elem -> !predicate.test(elem));
    }
}
