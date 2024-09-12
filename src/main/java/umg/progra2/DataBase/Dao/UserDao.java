package umg.progra2.DataBase.Dao;

import umg.progra2.DataBase.Model.User;
import umg.progra2.DataBase.db.DatabaseConnection;

import java.sql.*;

public class UserDao {

    public boolean deleteUserById(String email) throws SQLException {
        String query = "DELETE FROM  tb_usuarios WHERE idusuario = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }




    public boolean updateUser(User user) throws SQLException {
        String query = "UPDATE tb_usuarios SET carne = ?, nombre = ?, correo = ?, seccion = ?, telegramid = ?, activo = ? WHERE idusuario = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getCarne());
            statement.setString(2, user.getNombre());
            statement.setString(3, user.getCorreo());
            statement.setString(4, user.getSeccion());
            statement.setLong(5, user.getTelegramid());
            statement.setString(6, user.getActivo());
            statement.setInt(7, user.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isEmailDuplicated(String email) {
        String query = "SELECT COUNT(*) FROM tb_usuarios WHERE correo = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Devuelve true si el correo ya existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // El correo no está duplicado
    }


    public void insertUser(User user) throws SQLException {
        String query = "INSERT INTO tb_usuarios (carne, nombre, correo, seccion) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getCarne());
            statement.setString(2, user.getNombre());
            statement.setString(3, user.getCorreo());
            statement.setString(4, user.getSeccion());
            statement.executeUpdate();
        }
    }

    public User getUserByCarne(String Carne) throws SQLException {
        String query = "SELECT * FROM tb_usuarios WHERE carne = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, Carne);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("idusuario"));
                user.setCarne(resultSet.getString("carne"));
                user.setNombre(resultSet.getString("nombre"));
                user.setCorreo(resultSet.getString("correo"));
                user.setSeccion(resultSet.getString("seccion"));
                user.setTelegramid(resultSet.getLong("telegramid"));
                user.setActivo(resultSet.getString("activo"));
                return user;
            }
        }
        return null;
    }
}