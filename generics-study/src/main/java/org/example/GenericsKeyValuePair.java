package org.example;

class GenericsKeyValuePair<K, V> {
    private K key;
    private V value;

    public void Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() { return key; }
    public V getValue() { return value; }

//    public static void main(String[] args) {
//        GenericsKeyValuePair<String, Integer> student = new GenericsKeyValuePair<>("Age", 25);
//
//        System.out.println(student.getKey());   // Age
//        System.out.println(student.getValue()); // 25
//    }
}
