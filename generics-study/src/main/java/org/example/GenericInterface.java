package org.example;

public class GenericInterface {
    // Generic Interface
    interface Processor<T> {
        void process(T item);
    }

    // Implementation for String
    static class StringProcessor implements Processor<String> {
        @Override
        public void process(String item) {
            System.out.println(item.toUpperCase());
        }
    }

    public static void main(String[] args) {
        Processor<String> processor = new StringProcessor();

        processor.process("hello world");
        processor.process("java generics");
        processor.process("strategy pattern");
    }
}
