package todo_list.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import todo_list.model.Item;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;

@Component
public class TodoDao {
    private static final Logger logger = Logger.getLogger(TodoDao.class);
    private final BasicDataSource pool = new BasicDataSource();
    private final StandardServiceRegistry registry =
            new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();


    private TodoDao() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("./src/main/resources/db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            Class.forName(cfg.getProperty("driver"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        pool.setDriverClassName(cfg.getProperty("driver"));
        pool.setUrl(cfg.getProperty("url"));
        pool.setUsername(cfg.getProperty("username"));
        pool.setPassword(cfg.getProperty("password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    public Item add(Item item) throws SQLException {
        Session session = sf.openSession();
        session.beginTransaction();
        try {
            session.save(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("error: ", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return item;
    }

    public boolean replace(Integer id, Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        boolean flag = false;
        try {
            if (session.contains(item.getDescription())) {
                session.merge(item);
                session.getTransaction().commit();
                flag = true;
            }
        } catch (Exception e) {
            logger.error("error: ", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return flag;
    }

    public boolean delete(Integer id) {
        Session session = sf.openSession();
        session.beginTransaction();
        boolean flag = false;
        try {
            Item item = new Item();
            item = findById(id);

            session.delete(item);
            session.getTransaction().commit();
            flag = true;

        } catch (Exception e) {
            logger.error("error: ", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return flag;
    }

    public List<Item> findAll() {
        Session session = sf.openSession();
        List<Item> list = new ArrayList<>();
        try {
            session.beginTransaction();
//          HQL request

//          Query query = session.createQuery("from itemstable");
//            list = query.list();

//          Criteria request

            Criteria criteria = session.createCriteria(Item.class, "itemstable");
            list = criteria.list();
        } catch (Exception e) {
            logger.error("error: ", e);
        } finally {
            session.close();
        }

        return list;
    }

    public List<Item> findByName(String key) {
        Session session = sf.openSession();
        List<Item> list = new ArrayList<>();
        try {
            session.beginTransaction();
//          HQL request
//          Query query = session.createQuery("from Item where name = ?");
//            query.getParameter(1, key);
//            list = query.list();
//          Criteria request
            Criteria criteria = session.createCriteria(Item.class, "itemstable");
            criteria.add(Restrictions.eq("itemstable.name", key));
            list = criteria.list();
        } catch (Exception e) {
            logger.error("error: ", e);
        } finally {
            session.close();
        }

        return list;
    }

    public Item findById(Integer id) {
        Session session = sf.openSession();
        Item item;
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Item.class, "itemstable");
        criteria.add(Restrictions.eq("itemstable.id", id));
        item = (Item) criteria.list().get(0);
        session.close();
        return item;
    }

    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    public static void main(String[] args) throws Exception {
        TodoDao tracker = new TodoDao();
        List<Item> list1 = List.of(
                new Item("item1"),
                new Item("item2"));

        for (Item i : list1) {
            tracker.add(i);
        }
        tracker.findAll().forEach(System.out::println);

//        System.out.println(tracker.findById(1));
//        System.out.println(tracker.replace(18, new Item("item3 new")));
//        tracker.findByName("item1").forEach(System.out::println);
//        System.out.println(tracker.delete(35));

    }
}
