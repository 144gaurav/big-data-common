package supplier;

import java.util.Objects;

public interface CallChain extends Runnable{

    default CallChain andBranch(CallChain after){
        Objects.requireNonNull(after);
        return () -> {
            this.run();
            after.run();
        };
    }
}
