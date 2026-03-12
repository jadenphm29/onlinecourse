package mylib;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtils {

    // Thay bằng Gmail của bạn
    private static final String FROM_EMAIL = "trungquana123@gmail.com";
    private static final String PASSWORD    = "ivdt cjub fiiy kruv"; 
    // Dùng App Password, không dùng password Gmail thật
    // Tạo App Password: Google Account → Security → 2FA → App passwords

    private static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth",            "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host",            "smtp.gmail.com");
        props.put("mail.smtp.port",            "587");
        props.put("mail.smtp.ssl.trust",       "smtp.gmail.com");

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        });
    }

    public static void sendEmail(String toEmail, String subject, String htmlBody) {
        try {
            Session session = getSession();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, "EduOnline"));
            message.setRecipients(Message.RecipientType.TO,
                                  InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setContent(htmlBody, "text/html; charset=UTF-8");
            Transport.send(message);
            System.out.println("Email sent to: " + toEmail);
        } catch (Exception e) {
            // Không throw — lỗi mail không nên crash app
            System.err.println("Email error: " + e.getMessage());
        }
    }

    // ═══ Template methods ═══

    public static void sendEnrollmentConfirm(String toEmail, String studentName,
                                              String courseName, double fee) {
        String subject = "EduOnline — Enrollment Confirmation";
        String body = buildHtml(
            "Enrollment Received &#128203;",
            "Hi <b>" + studentName + "</b>,",
            "Your enrollment request for <b>" + courseName + "</b> has been received." +
            (fee > 0
                ? "<br>Please complete your payment of <b>" +
                  String.format("%,.0f", fee) + " VND</b> to proceed."
                : "<br>This course is <b>free</b>. Waiting for instructor approval."),
            "#4f46e5"
        );
        sendEmail(toEmail, subject, body);
    }

    public static void sendPaymentConfirm(String toEmail, String studentName,
                                           String courseName, double amount) {
        String subject = "EduOnline — Payment Successful";
        String body = buildHtml(
            "Payment Successful &#128179;",
            "Hi <b>" + studentName + "</b>,",
            "Your payment of <b>" + String.format("%,.0f", amount) +
            " VND</b> for <b>" + courseName + "</b> was successful.<br>" +
            "Your enrollment is now pending instructor approval.",
            "#10b981"
        );
        sendEmail(toEmail, subject, body);
    }

    public static void sendApprovalNotice(String toEmail, String studentName,
                                           String courseName) {
        String subject = "EduOnline — Enrollment Approved &#10003;";
        String body = buildHtml(
            "Enrollment Approved &#10003;",
            "Hi <b>" + studentName + "</b>,",
            "Congratulations! Your enrollment in <b>" + courseName +
            "</b> has been <b style='color:#10b981;'>approved</b>.<br>" +
            "You can now access the course content.",
            "#10b981"
        );
        sendEmail(toEmail, subject, body);
    }

    public static void sendRejectionNotice(String toEmail, String studentName,
                                            String courseName, boolean refunded,
                                            double amount) {
        String subject = "EduOnline — Enrollment Update";
        String refundText = refunded
            ? "<br>Your payment of <b>" + String.format("%,.0f", amount) +
              " VND</b> has been <b style='color:#f59e0b;'>refunded</b> to your wallet."
            : "";
        String body = buildHtml(
            "Enrollment Rejected",
            "Hi <b>" + studentName + "</b>,",
            "Unfortunately, your enrollment in <b>" + courseName +
            "</b> has been <b style='color:#ef4444;'>rejected</b>." +
            refundText,
            "#ef4444"
        );
        sendEmail(toEmail, subject, body);
    }

    // ═══ HTML template ═══
    public static String buildHtml(String title, String greeting,
                                     String content, String color) {
        return "<!DOCTYPE html><html><body style='" +
               "font-family:Arial,sans-serif; background:#0f172a; margin:0; padding:20px;'>" +
               "<div style='max-width:520px; margin:0 auto; background:#1e293b;" +
               "border-radius:12px; overflow:hidden;'>" +
               "<div style='background:" + color + "; padding:24px; text-align:center;'>" +
               "<h2 style='color:#fff; margin:0; font-size:20px;'>" + title + "</h2>" +
               "</div>" +
               "<div style='padding:28px; color:#cbd5e1; line-height:1.7;'>" +
               "<p>" + greeting + "</p>" +
               "<p>" + content + "</p>" +
               "<hr style='border-color:#334155; margin:20px 0;'/>" +
               "<p style='font-size:12px; color:#6b7280;'>EduOnline — Online Learning Platform</p>" +
               "</div></div></body></html>";
    }
}