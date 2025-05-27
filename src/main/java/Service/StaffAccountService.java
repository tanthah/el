package Service;

import dao.AccountDAO;
import dao.AdminDAO;
import Model.Account;
import DTO.LoginResultDTO;


public class StaffAccountService {
    private final AccountDAO accountDAO;
    private final AdminDAO adminDAO;

    // Constructor for dependency injection
    public StaffAccountService() {
        this.accountDAO = new AccountDAO();
        this.adminDAO = new AdminDAO();
    }

    public LoginResultDTO login(String username, String password) {
        LoginResultDTO result = new LoginResultDTO();

        // Kiểm tra đầu vào
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            result.setSuccess(false);
            result.setErrorMessage("Vui lòng cung cấp tên người dùng và mật khẩu.");
            return result;
        }

        // Tìm tài khoản dựa trên username
        Account account = accountDAO.findByUsernameOrEmail(username, null);
        if (account == null || !account.isActive() || !password.equals(account.getPassword())) {
            result.setSuccess(false);
            result.setErrorMessage("Tên người dùng hoặc mật khẩu không hợp lệ.");
            return result;
        }

        // Kiểm tra vai trò
        Long accountId = account.getAccountId();
        try {
            if (accountDAO.isTeacher(accountId)) {
                result.setRole("teacher");
                result.setSuccess(true);
                result.setMessage("Đăng nhập thành công với vai trò giáo viên.");
            } else if (adminDAO.findAdminById(accountId)) {
                result.setRole("admin");
                result.setSuccess(true);
                result.setMessage("Đăng nhập thành công với vai trò quản trị viên.");
            } else {
                result.setSuccess(false);
                result.setErrorMessage("Vai trò hoặc loại tài khoản không hợp lệ.");
                return result;
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("Lỗi khi kiểm tra vai trò: " + e.getMessage());
            return result;
        }

        result.setAccount(account);
        return result;
    }

    
}