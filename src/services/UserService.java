package services;

import business.UserBusiness;
import dao.UserDAO;
import model.User;
import utils.Global.CurrentUser;

public class UserService {
    private UserDAO _userDAO = new UserDAO();
    private UserBusiness _userBusiness = new UserBusiness();

    public void insertUser(User user) {
        try {
            String encryptPassword = _userBusiness.encryptPassword(user.getPassword());
            user.setPassword(encryptPassword);
            _userDAO.insert(user);
        } catch (Exception ex) {
            throw ex;
        }

    }

    public User updateUser(User user) {
        try {
            if (!CurrentUser.getAccessToken().isBlank()) {
                String encryptPassword = _userBusiness.encryptPassword(user.getPassword());
                user.setPassword(encryptPassword);
                return _userDAO.update(user);
            } else {
                throw new IllegalStateException("Usuário não autenticado.");
            }

        } catch (Exception ex) {
            throw ex;
        }
    }

    public void deleteUser(String Id) {
        try {
            if (!CurrentUser.getAccessToken().isBlank()) {
                _userDAO.delete(Id);
            } else {
                throw new IllegalStateException("Usuário não autenticado.");
            }

        } catch (Exception ex) {
            throw ex;
        }
    }

    public User getUserById(String Id) {
        try {
            if (!CurrentUser.getAccessToken().isBlank()) {
                return _userDAO.getByID(Id);
            } else {
                throw new IllegalStateException("Usuário não autenticado.");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    public User getUserBets(User user) {
        try {
            if (!CurrentUser.getAccessToken().isBlank()) {
                return _userDAO.getUserBets(user);
            } else {
                throw new IllegalStateException("Usuário não autenticado.");
            }

        } catch (Exception ex) {
            throw ex;
        }
    }

    public void depositMoneyUserById(String Id, Double value) {
        try {
            if (!CurrentUser.getAccessToken().isBlank()) {
                _userDAO.depositMoney(Id, value);
            } else {
                throw new IllegalStateException("Usuário não autenticado.");
            }

        } catch (Exception ex) {
            throw ex;
        }
    }

    public void sacMoneyUserById(String Id, Double value) {
        try {
            if (!CurrentUser.getAccessToken().isBlank()) {
                _userDAO.sacMoney(Id, value);
            } else {
                throw new IllegalStateException("Usuário não autenticado.");
            }

        } catch (Exception ex) {
            throw ex;
        }
    }

    public boolean login(String email, String password) {
        try {
            User user = _userDAO.getByEmail(email);
            if (user != null && _userBusiness.checkPassword(password, user.getPassword())) {
                CurrentUser.setUsername(user.getName());
                CurrentUser.setEmail(user.getEmail());
                CurrentUser.setBalance(user.getBalance());
                CurrentUser.setId(user.getId());
                CurrentUser.setAccessToken(_userBusiness.generateToken(user.getEmail()));
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            throw ex;
        }
    }
}
