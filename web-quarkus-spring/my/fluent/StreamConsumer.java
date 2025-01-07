package fluent;

import java.util.function.Consumer;

public class StreamConsumer {

   private String n;

    private StreamConsumer() {
    }

    public StreamConsumer first(String f) {
        System.out.println(f+1);
        this.n = f;
        return this;
    }

    public StreamConsumer second(String f) {
        System.out.println(f+2);
        return this;
    }

    public StreamConsumer last(String f) {
        System.out.println(f+3);
        return this;
    }

    public static void run(final Consumer<StreamConsumer> consumer) {
        final StreamConsumer streamConsumer = new StreamConsumer();

        consumer.accept(streamConsumer);
        System.out.println();
        streamConsumer.second(streamConsumer.n);
        streamConsumer.last(streamConsumer.n);


    }
}
