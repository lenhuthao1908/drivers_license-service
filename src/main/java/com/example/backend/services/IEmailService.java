package com.example.backend.services;

import com.example.backend.entities.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class IEmailService implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    public void sendReminderEmailsCreateProfile(Profile profile, LocalDate date) {
        String subject = "Thông báo: Bạn đã nộp hồ sơ thành công";
        String text = String.format(
                "Bạn đã hoàn thành việc đăng ký sát hạch với các thông tin dưới đây, vui lòng chờ đến khi hồ sơ được xét duyệt.%n <br/>" +
                        "Họ và tên: %s%n <br/>" +
                        "Ngày sinh: %s%n <br/>" +
                        "Giới tính: %s%n <br/>" +
                        "Căn cước công dân: %s%n <br/>" +
                        "Số điện thoại: %s%n <br/>" +
                        "Email: %s%n <br/>" +
                        "Ảnh: <a href=\"%s\">Click send image</a>%n <br/>" +
                        "File: <a href=\"%s\">Click send file</a>%n <br/>" +
                        "Dân tộc: %s%n <br/>" +
                        "Tôn giáo: %s%n <br/>" +
                        "Tỉnh/Thành phố: %s%n <br/>" +
                        "Quận/Huyện: %s%n <br/>" +
                        "Xã/Phường: %s%n <br/>" +
                        "Đợt sát hạch: %s%n <br/>" +
                        "Ngày sát hạch: %s <br/>" +
                        "Ghi chú: %s%n <br/>",

                profile.getName(),
                profile.getDateofbirth(),
                profile.getSex(),
                profile.getIdcard(),
                profile.getPhone(),
                profile.getEmail(),
                profile.getImage(),
                profile.getFile(),
                profile.getNationId().getNationName(),
                profile.getReligionId().getReligionName(),
                profile.getProvince(),
                profile.getDistrict(),
                profile.getWards(),
                profile.getExaminationsId().getExaminationsName(),
                profile.getExaminationsId().getExaminationsDate(),
                profile.getNote()
        );

        sendEmail(profile.getEmail(), subject, text);
    }

    public void sendReminderEmails(Profile profile) {
        LocalDate reminderDate = profile.getExaminationsId().getExaminationsDate().minusDays(1);

        if (isTodayOneDayBefore(reminderDate)) {
                String subject = "Nhắc nhở: Bạn có một kỳ sát hạch vào ngày mai!";
                String text = String.format(
                        "Đừng quên rằng bạn có kỳ sát hạch vào ngày mai nhé!%n <br/> " +
                                "Thông tin đợt sat hạch: %n <br/>" +
                                "Tên đợt sát hạch: %s%n <br/>" +
                                "Ngày sát hạch: %s%n <br/>" +
                                "Nội dung đợt sát hạch: %s%n <br/>",
                        profile.getExaminationsId().getExaminationsName(),
                        profile.getExaminationsId().getExaminationsDate(),
                        profile.getExaminationsId().getExaminationsDescription()
                );
                sendEmail(profile.getEmail(), subject, text);
        }
    }

    @Override
    public void sendReminderEmailsNotCreateProfile(Profile profile, LocalDate eventDate) {
        String subject = "Thông báo: Bạn không thể đăng ký sát hạch lái xe!";
        String text = String.format(
                "Bạn không thể đăng ký tham gia đợt sát hạch bởi vì bạn chưa đủ 18 tuổi!.%n <br/>"
        );

        sendEmail(profile.getEmail(), subject, text);
    }

    @Override
    public void sendReminderEmailsReverseProfile(Profile item, LocalDate eventDate) {
        String subject = "Thông báo: Bạn đã đăng ký thành công, hồ sơ của bạn vẫn còn thời hạn bảo lưu";
        String text = "Bạn có thể theo dõi trên website của trung tâm để biết thêm thông tin về đợt sát hạch.";

        sendEmail(item.getEmail(), subject, text);
    }

    @Override
    public void sendReminderEmailsNotApprove(String email, LocalDate evenDate) {
        String subject = "Thông báo: Hồ sơ của bạn không được duyệt!";
        String text = "Hồ sơ của bạn chưa đủ điều kiện để tham gia sát hạch. Hẹn gặp bạn vào lần sau!";

        sendEmail(email, subject, text);
    }

    @Override
    public void sendReminderEmailsApprove(String email, LocalDate evenDate) {
        String subject = "Thông báo: Hồ sơ của bạn đã được duyệt!";
        String text = "Bạn có thể theo dõi trên website của trung tâm để biết thêm thông tin về đợt sát hạch.";

        sendEmail(email, subject, text);
    }

    private boolean isTodayOneDayBefore(LocalDate reminderDate) {
        return LocalDate.now().isEqual(reminderDate);
    }



    @Override
    public void sendEmail(String to, String subject, String message) {
        MimeMessagePreparator result = mimeMessage -> {
            MimeMessageHelper msg = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(message, true);
        };
        javaMailSender.send(result);
    }
}

