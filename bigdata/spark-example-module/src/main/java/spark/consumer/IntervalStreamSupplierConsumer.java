package spark.consumer;

import supplier.Supplier;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public abstract class IntervalStreamSupplierConsumer<T> {
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private final Optional<Integer> loopInterval;
    private final Supplier<Stream<T>> inputStreamSupplier;
    private IntervalStreamSupplierConsumerListner listner;

    public IntervalStreamSupplierConsumer(Optional<Integer> loopInterval, Supplier<Stream<T>> inputStreamSupplier) {
        this.loopInterval = loopInterval;
        this.inputStreamSupplier = inputStreamSupplier;
        listner = new DefaultListner();
    }

    public abstract void process(T task);

    public void run(){
        isRunning.set(true);
        listner.onStart();
        try {
            while (isRunning()){
                loop();
            }
        }finally {
            listner.onClose();
        }
    }

    public void setListner(IntervalStreamSupplierConsumerListner listner) {
        this.listner = listner;
    }

    private void loop(){
        listner.onStartLoop();
        inputStreamSupplier.get().forEach(this::process);
        listner.onEndLoop();
        sleep();
    }

    private void sleep(){
        loopInterval.ifPresent(interval -> {
            try {
                Thread.sleep(interval);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        });
    }

    protected boolean isRunning(){
        return isRunning.get();
    }



    private class DefaultListner implements IntervalStreamSupplierConsumerListner{

        @Override
        public void onStart() {
            System.out.println("onStart");
        }

        @Override
        public void onClose() {
            System.out.println("onClose");

        }

        @Override
        public void onStartLoop() {
            System.out.println("onStartLoop");

        }

        @Override
        public void onEndLoop() {
            System.out.println("onEndLoop");

        }
    }
}
