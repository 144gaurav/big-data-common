package accumulator;

import org.apache.spark.util.AccumulatorV2;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccumulatorHolder<K> implements Serializable {

    private final Map<K, AccumulatorV2<?,?>> accumulatorRegister;

    public AccumulatorHolder(boolean concurrent){
        accumulatorRegister = (concurrent) ? new ConcurrentHashMap<>() : new HashMap<>();
    }

    public void add(K key, AccumulatorV2<?, ?> value){
        accumulatorRegister.put(key,value);
    }

    public <T> T getAccumulator(K key, Class<T> valueType){
        try {
            return (T) accumulatorRegister.get(key);
        }catch (Exception e){
            System.out.println("Error to retrieve");
            return null;
        }
    }

    public Map<K, AccumulatorV2<?,?>> getAccumulatorsRegister(){
        return accumulatorRegister;
    }
}

