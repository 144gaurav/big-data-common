package spark.consumer;

public interface IntervalStreamSupplierConsumerListner {

    public void onStart();
    public void onClose();
    public void onStartLoop();
    public void onEndLoop();
}
