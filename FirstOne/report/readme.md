exit() 方法:当线程运行完毕后，exit() 方法会将线程对象中的引用变量（如 target、threadLocals 等）全部置为 null，这样这些对象就不再被引用，JVM 的垃圾回收器（GC）就可以回收它们在堆上的内存。
Thread.class类：Java 线程的基本行为和操作类