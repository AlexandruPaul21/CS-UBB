package factory;

import container.Container;
import container.Strategy;

public interface Factory {
    public Container createContainer(Strategy strategy);
}
