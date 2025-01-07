import java.util.Objects;

public abstract class AbstractBuilder {

    private final String name;

    abstract static class   Builder<T extends Builder<T>> {

        abstract  AbstractBuilder create();


        private String name;

        public  Builder<T> name(String name) {
            this.name = name;
            return this;
        }

        public T setName(String name) {
            Objects.requireNonNull(name, "Name must not be null");
            return self();
        }


        protected abstract T self();
    }

    protected AbstractBuilder(Builder<?> builder) {
        this.name = builder.name;
    }


}
