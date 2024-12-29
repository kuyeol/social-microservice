import java.util.Objects;

public abstract class AbstractBuilder {


    abstract static class Builder<T extends Builder<T>> {

        abstract AbstractBuilder build();

        public T setName(String name) {
            Objects.requireNonNull(name, "Name must not be null");
            return self();
        }


        protected abstract T self();
    }

    AbstractBuilder(Builder<?> builder) {

    }


}
