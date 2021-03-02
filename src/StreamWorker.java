import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class StreamWorker implements Runnable, Closeable {

    private final BufferedReader in;    // Входной поток данных
    private final PrintWriter out;      // Выходной поток данных
    // private  - означает, что до этого поля нельзя дотянуться извне, ведь напрямую с ним никто другой кроме данного класса работать не должен
    // final    - означает, что этот объект всегда будет один и тот же (почти то же самое, что и const), т.е. что это финальный объект

    private final List<MessageListener> listeners = new ArrayList<>(); // Зарегистрированные обработчики входящих собщений

    private final Object outputLock = new Object();   // Не обращайте внимания, эти два объекта
    private final Object listenerLock = new Object(); // используются в synchronized-блоках

    public StreamWorker(InputStream input, OutputStream output) {
        this.in = new BufferedReader(new InputStreamReader(input));
        this.out = new PrintWriter(output, true); // true - флаг autoFlush, он приводит к тому, что буфер будет отправляться сразу - на каждое сообщение
    }

    public void addListener(MessageListener listener) {
        this.listeners.add(listener);
    }

    // Это метод, который StreamWorker обязался реализовать в связи с реализацией интерфейса Runnable (т.к. выше написано StreamWorker implements Runnable)
    // В этом методе StreamWorker ждет поступления новых сообщений, и каждое новое сообщение передает обработчику входящих сообщений
    @Override
    public void run() {
        try {
            String s;
            // Пока входящее сообщение не отсутствует - читаем сообщения одно за другим
            while ((s = in.readLine()) != null) {
                // Отдаем полученное сообщение на обработку
                synchronized (listenerLock) {
                    for (MessageListener listener : listeners) {
                        listener.onMessage(s);
                    }
                }
            }
        } catch (SocketException e) {
            if (e.getMessage().equals("Connection reset")) {
                // Если случившаяся исключительная ситуация - разрыв соединения, то вызываем соответствующую обработку события
                synchronized (listenerLock) {
                    for (MessageListener listener : listeners) {
                        listener.onDisconnect();
                    }
                }
            } else {
                // Иначе - просто обрабатываем ошибку
                synchronized (listenerLock) {
                    for (MessageListener listener : listeners) {
                        listener.onException(e);
                    }
                }
            }
        } catch (IOException e) {
            // Провоцируем обработку случившейся исключительной ситуации (например клиент разорвал соединение)
            synchronized (listenerLock) {
                for (MessageListener listener : listeners) {
                    listener.onException(e);
                }
            }
        }
    }

    // Этот метод запускает цикл в методе run(), который будет считывать входящие сообщения и отдавать их на обработку в listeners
    public void start() {
        Thread thread = new Thread(this, "StreamWorker");
        thread.start();
    }

    // Это метод отправки сообщения
    public void sendMessage(String text) {
        synchronized (outputLock) {
            out.println(text);
        }
    }

    // Это метод, реализовать который StreamWorker обязуется в связи с реализацией интерфейса Closeable (т.к. выше написано StreamWorker implements Closeable)
    // Общая идея что Closeable = "закрываемый" как например файл. В нашем случае StreamWorker просто закрывает оба потока данных
    @Override
    public void close() throws IOException {
        in.close();  // Закрываем входной поток
        out.close(); // Закрываем выходной поток
    }
}