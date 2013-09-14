package de.metalcon.sdd.server;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import de.metalcon.sdd.Detail;
import de.metalcon.sdd.IdDetail;
import de.metalcon.sdd.request.Request;

// LevelDB
import org.iq80.leveldb.*;

import static org.fusesource.leveldbjni.JniDBFactory.*;

public class Server implements ServletContextListener {

    private DB db;

    private BlockingQueue<Request> queue;

    private Worker worker;

    public void start() {
        Options options = new Options();
        options.createIfMissing(true);
        try {
            db = factory.open(new File("/usr/share/sdd"), options);
        } catch (IOException e) {
            // TODO: handle this
            e.printStackTrace();
            throw new RuntimeException();
        }

        queue = new LinkedBlockingQueue<Request>();
        worker = new Worker(queue);
        worker.start();
    }

    public void stop() {
        worker.stop();
        worker.waitForShutdown();
        
        try {
            db.close();
        } catch (IOException e) {
            // TODO: handle this
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    
    public boolean addRequest(Request request) {
        return queue.offer(request);
    }

    public String readEntity(IdDetail entity) {
        if (entity.getDetail() == Detail.NONE) {
            // TODO: handle this 
            throw new RuntimeException();
        }
        return asString(db.get(bytes(entity.toString())));
    }
    
    public void writeEntity(IdDetail entity, String json) {
        if (entity.getDetail() == Detail.NONE) {
            // TODO: handle this
            throw new RuntimeException();
        }
        db.put(bytes(entity.toString()), bytes(json));
    }
    
    public void deleteEntity(IdDetail entity) {
        if (entity.getDetail() == Detail.NONE) {
            // TODO: handle this
            throw new RuntimeException();
        }
        db.delete(bytes(entity.toString()));
    }

    @Override
    public void contextInitialized(ServletContextEvent arg) {
        ServletContext context = arg.getServletContext();
        context.setAttribute("server", this);

        start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg) {
        stop();
    }

}