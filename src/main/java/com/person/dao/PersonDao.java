package com.person.dao;

import com.person.model.Person;
import com.person.model.Requests;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDao {
    public static String addPerson(Person person){
        if(person.getBirthYear() < 2000 && (person.getGender().equals("f") || person.getGender().equals("m"))){
            String SQL = "insert into person (name, gender, birthYear, created_date) values(?,?,?, now()) ";
                try(Connection conn = DbConnection.connect();
                    PreparedStatement stmt = conn.prepareStatement(SQL)){
                stmt.setString(1, person.getName());
                stmt.setString(2, person.getGender());
                stmt.setLong(3, person.getBirthYear());
                stmt.executeUpdate();

                addRequest(person, "Creating");
                return person.msg();
            }
                catch (
            SQLException e) {
                e.printStackTrace();
            }
        }
        return "error";
    }

    public static void addRequest(Person person, String request) {
        String SQL = "insert into requests (requestName, name, birth_year, gender, requestDateTime) values(?,?,?,?, now()) ";
        try(Connection conn = DbConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(SQL)){
            stmt.setString(1,request);
            stmt.setString(2, person.getName());
            stmt.setLong(3, person.getBirthYear()  == null ? 0 : 0); // Выдаёт ошибку, если оставить просто null
            stmt.setString(4, person.getGender());
            stmt.executeUpdate();
        }
        catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Requests> getAllRequests(){
        List<Requests> requestsList = new ArrayList<>();
        String SQL = "select * from requests";
        try(Connection conn = DbConnection.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL)){
            while (rs.next()){
                Requests requests = new Requests(
                        rs.getLong("id"),
                        rs.getString("requestName"),
                        rs.getString("name"),
                        rs.getLong("birth_year"),
                        rs.getString("gender"),
                        rs.getDate("requestDateTime")
                );
                requestsList.add(requests);
            }
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
        Person nullPerson = new Person();
        addRequest(nullPerson, "Get All Requests");
        return requestsList;
    }


    public static List<Person> searchPerson(String find) throws SQLException {
        String SQL = null;
        String searching = "";
        List<Person> personList = new ArrayList<>();
        Integer findYear = null;

        if (find.equals("m") || find.equals("f")) {
            SQL = "select * from person where gender = ?";
            searching = "gender";
        }
        else if (Integer.parseInt(find) < 2000 && Integer.parseInt(find) > 1800){
            SQL = "select * from person where birthYear = ?";
            searching = "birthYear";
            findYear = Integer.parseInt(find);
        }
        else {
            SQL = "select * from person where name = ?";
            searching = "name";
        }
            try (Connection conn = DbConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(SQL)) {
                if(searching.equals("birthYear")){
                    stmt.setInt(1, findYear);
                }else
                stmt.setString(1, find);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Person person = new Person(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("gender"),
                                rs.getLong("birthYear"),
                                rs.getDate("created_Date")
                        );
                        personList.add(person);
                        addRequest(person, "Search person by " + searching);
                    }
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
                return personList;
            }
    }

    public static Boolean deleteRequestById(int id){
        String SQL = "delete from requests where id = ?";
        try(Connection conn = DbConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(SQL)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return false;
    }
}
