package util;
public class Logger {
    public static void log(String message){
        String callerClassName = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
                                .walk(stream -> stream.skip(2)  
                                .findFirst()  
                                .get()
                                .getClassName());
        System.out.println("[" + callerClassName + "] " + message);
    }
}
