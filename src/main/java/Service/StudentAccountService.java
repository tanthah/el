package Service;

import dao.AccountDAO;
import Model.Account;
import DTO.LoginResultDTO;
import DTO.PasswordResetResultDTO;
import Model.PasswordResetToken;
import Model.Student;
import Util.EmailUtil;
import dao.PasswordResetTokenDAO;
import jakarta.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.UUID;
import org.mindrot.jbcrypt.BCrypt;

public class StudentAccountService {
    private final AccountDAO accountDAO;
    private PasswordResetTokenDAO tokenDAO;

    // Constructor for dependency injection
    public StudentAccountService() {
        this.accountDAO = new AccountDAO();
        tokenDAO = new PasswordResetTokenDAO();
    }

    public boolean registerStudent(String username, String password, String email, String phone) {
        try {
            // Kiểm tra username hoặc email đã tồn tại
            Account existingAccount = accountDAO.findByUsernameOrEmail(username, email);
            if (existingAccount != null) {
                return false;
            }

            // Mã hóa mật khẩu
            String hashedPassword = hashPassword(password);

            // Tạo đối tượng Student
            Student newStudent = new Student(username, hashedPassword, email, phone, null, true);

            // Lưu tài khoản
            return accountDAO.saveAccount(newStudent);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
  public boolean login(String username, String password, Account[] accountHolder) {
        try {
            // Tìm tài khoản dựa trên username
            Account account = accountDAO.findByUsernameOrEmail(username, null);
            if (account == null || !account.isActive()) {
                return false;
            }

            // So sánh mật khẩu plaintext
            if (!password.equals(account.getPassword())) {
                return false;
            }

            // Lưu tài khoản vào mảng để servlet sử dụng
            accountHolder[0] = account;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

   private String hashPassword(String password) {
        // Giả sử sử dụng BCrypt hoặc hàm mã hóa khác
        
        return password; // Thay bằng hàm mã hóa thực tế
    }

    public PasswordResetResultDTO requestPasswordReset(String email, String baseUrl) {
        PasswordResetResultDTO result = new PasswordResetResultDTO();

        // Kiểm tra email
        if (email == null || email.trim().isEmpty()) {
            result.setSuccess(false);
            result.setErrorMessage("Vui lòng nhập email.");
            return result;
        }

        // Kiểm tra email tồn tại
        Account account = accountDAO.findByUsernameOrEmail(null, email);
        if (account == null) {
            result.setSuccess(false);
            result.setErrorMessage("Email không tồn tại.");
            return result;
        }

        try {
            // Tạo token
            String token = UUID.randomUUID().toString();
            LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);
            tokenDAO.saveToken(email, token, expiryDate);

            // Gửi email
            String resetLink = baseUrl + "/reset-password?token=" + token;
            EmailUtil.sendEmail(email, "Đặt Lại Mật Khẩu",
                    "Nhấp vào liên kết sau để đặt lại mật khẩu của bạn:\n" + resetLink +
                            "\nLiên kết này có hiệu lực trong 1 giờ.");

            result.setSuccess(true);
            result.setMessage("Liên kết đặt lại mật khẩu đã được gửi đến email của bạn.");
        } catch (MessagingException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorMessage("Đã xảy ra lỗi khi gửi email. Vui lòng thử lại.");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorMessage("Đã xảy ra lỗi hệ thống. Vui lòng thử lại.");
        }

        return result;
    }

    public PasswordResetResultDTO validateResetToken(String token) {
        PasswordResetResultDTO result = new PasswordResetResultDTO();

        // Kiểm tra token
        if (token == null || token.trim().isEmpty()) {
            result.setSuccess(false);
            result.setErrorMessage("Liên kết không hợp lệ.");
            return result;
        }

        // Tìm token
        PasswordResetToken prt = tokenDAO.findToken(token);
        if (prt == null || prt.getExpiryDate().isBefore(LocalDateTime.now())) {
            result.setSuccess(false);
            result.setErrorMessage("Liên kết đặt lại mật khẩu không hợp lệ hoặc đã hết hạn.");
            return result;
        }

        result.setSuccess(true);
        result.setTokenValid(true);
        result.setMessage("Token hợp lệ.");
        return result;
    }

    public PasswordResetResultDTO resetPassword(String token, String password, String confirmPassword) {
        PasswordResetResultDTO result = new PasswordResetResultDTO();

        // Kiểm tra token
        PasswordResetToken prt = tokenDAO.findToken(token);
        if (prt == null || prt.getExpiryDate().isBefore(LocalDateTime.now())) {
            result.setSuccess(false);
            result.setErrorMessage("Liên kết đặt lại mật khẩu không hợp lệ hoặc đã hết hạn.");
            return result;
        }

        // Kiểm tra mật khẩu
        if (password == null || password.trim().length() < 6) {
            result.setSuccess(false);
            result.setErrorMessage("Mật khẩu phải có ít nhất 6 ký tự.");
            result.setTokenValid(true);
            return result;
        }

        if (!password.equals(confirmPassword)) {
            result.setSuccess(false);
            result.setErrorMessage("Xác nhận mật khẩu không khớp.");
            result.setTokenValid(true);
            return result;
        }

        try {
            // Cập nhật mật khẩu
            Account account = accountDAO.findByUsernameOrEmail(null, prt.getEmail());
            if (account == null) {
                result.setSuccess(false);
                result.setErrorMessage("Tài khoản không tồn tại.");
                return result;
            }

            // Lưu plaintext tạm thời (như hiện tại)
            account.setPassword(password);
            boolean updated = accountDAO.updateAccount(account);
            if (!updated) {
                result.setSuccess(false);
                result.setErrorMessage("Cập nhật mật khẩu thất bại. Vui lòng thử lại.");
                result.setTokenValid(true);
                return result;
            }

            // Xóa token
            tokenDAO.deleteToken(token);

            result.setSuccess(true);
            result.setMessage("Mật khẩu đã được đặt lại thành công. Vui lòng đăng nhập.");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorMessage("Đã xảy ra lỗi hệ thống. Vui lòng thử lại.");
        }

        return result;
    }
    
}