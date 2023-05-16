package com.fo4ik.entities.service;

import com.fo4ik.entities.Task;
import com.fo4ik.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseUtil {

    private static SessionFactory sessionFactory;

    public static ArrayList<Task>  tasks = new ArrayList<>();

    public static void init() {
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public static void saveUser(User user) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static User getUserById(long id) {
        User user = null;
        try (Session session = getSession()) {
            user = session.get(User.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static void updateUser(User user) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void saveTask(Task task) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.save(task);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void updateTask(Task task) {
        //TODO update task
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.update(task);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void setArrayTasks(){
        Session session = getSession();
        try{
            tasks = (ArrayList<Task>) session.createQuery("FROM Task").list();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* public static String getRandomTaskForAdult(boolean isForAdult) {
        // Получение текущей сессии
        Session session = getSession();
        try {
            // Начало транзакции
            session.beginTransaction();

            // Получение всех задач в зависимости от флага isForAdult
            String queryString = "FROM Task";
            if (isForAdult) {
                // Поиск любой задачи, независимо от значения forAdult
                queryString = "FROM Task";
            } else {
                // Поиск задач с forAdult равным false
                queryString = "FROM Task WHERE forAdult = false";
            }

            Query<Task> query = session.createQuery(queryString, Task.class);
            List<Task> tasks = query.list();

            // Генерация случайного индекса
            Random random = new Random();
            int randomIndex = random.nextInt(tasks.size());

            // Получение случайной задачи
            Task randomTask = tasks.get(randomIndex);
            String randomTaskDescription = randomTask.getTask() + "\n" + randomTask.getId();

            // Фиксация транзакции
            session.getTransaction().commit();

            return randomTaskDescription;
        } catch (Exception e) {
            // Обработка исключения
            e.printStackTrace();

            // Откат транзакции в случае ошибки
            session.getTransaction().rollback();

            return null;
        } finally {
            // Закрытие сессии Hibernate
            session.close();
            //sessionFactory.close();
        }
    }*/

    public static String getRandomTaskForAdult(boolean isForAdult) {

        Random random = new Random();
        int randomIndex = random.nextInt(tasks.size());
        if(isForAdult = true) {

            String task = tasks.get(randomIndex).getTask();
            tasks.remove(randomIndex);
            return task;

        }
//        if(isForAdult = false) {
//            if(tasks.get(randomIndex).isForAdult() == isForAdult) {
//                String task = tasks.get(randomIndex).getTask();
//                tasks.remove(randomIndex);
//                return task;
//            }
//        } else {
//            return getRandomTaskForAdult(isForAdult);
//        }
        return null;
    }

    public static Task getTask(long id) {
        Session session = getSession();
        try {
            session.beginTransaction();
            Query<Task> query = session.createQuery("FROM Task WHERE id = :id", Task.class);
            query.setParameter("id", id);
            List<Task> tasks = query.list();
            if (tasks.size() > 0) {
                return tasks.get(0);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            //sessionFactory.close();
        }
        return null;
    }

    public static void deleteTask(long id) {
        Session session = getSession();
        try {
            session.beginTransaction();
            Query<Task> query = session.createQuery("FROM Task WHERE id = :id", Task.class);
            query.setParameter("id", id);
            List<Task> tasks = query.list();
            if (tasks.size() > 0) {
                session.remove(tasks.get(0));
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            //sessionFactory.close();
        }
    }

}
